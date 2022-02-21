package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec
import scala.collection.compat.immutable.LazyList

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for QOI files.
  */
object QoiImageLoader extends ImageLoader {

  private val supportedFormats = Set("qoif")

  // Binary helpers
  def wrapAround(b: Int): Int                        = if (b >= 0) b % 256 else 256 + b
  def load2Bits(b: Int, bias: Int = 2): Int          = (b & 0x03) - bias
  def load4Bits(b: Int, bias: Int = 8): Int          = (b & 0x0f) - bias
  def load6Bits(b: Int, bias: Int = 32): Int         = (b & 0x3f) - bias
  def hashColor(r: Int, g: Int, b: Int, a: Int): Int = (r * 3 + g * 5 + b * 7 + a * 11) % 64

  // Data formats
  case class Header(
      magic: String,
      width: Long,
      height: Long,
      channels: Byte,
      colorspace: Byte
  )
  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = (
      for {
        magic    <- readString(4).validate(supportedFormats, m => s"Unsupported format: $m")
        width    <- readBENumber(4)
        height   <- readBENumber(4)
        channels <- readByte.collect({ case Some(byte) => byte.toByte }, _ => "Incomplete header: no channel byte")
        colorspace <- readByte.collect(
          { case Some(byte) => byte.toByte },
          _ => "Incomplete header: no color space byte"
        )
      } yield Header(
        magic,
        width,
        height,
        channels,
        colorspace
      )
    ).run(bytes)
  }

  sealed trait Op
  object Op {
    final case class OpRgb(red: Int, green: Int, blue: Int)              extends Op
    final case class OpRgba(red: Int, green: Int, blue: Int, alpha: Int) extends Op
    final case class OpIndex(index: Int)                                 extends Op
    final case class OpDiff(dr: Int, dg: Int, db: Int)                   extends Op
    final case class OpLuma(dg: Int, drdg: Int, dbdg: Int) extends Op {
      val dr = drdg + dg
      val db = dbdg + dg
    }
    final case class OpRun(run: Int) extends Op

    val fromBytes: ParseState[String, Op] = readByte
      .collect(
        { case Some(tag) =>
          (tag & 0xc0, tag & 0x3f)
        },
        _ => "Corrupted file, expected a Op but got nothing"
      )
      .flatMap {
        case (0xc0, 0x3e) =>
          readBytes(3)
            .validate(_.size == 3, _ => "Not enough data for OP_RGB")
            .map(data => OpRgb(data(0), data(1), data(2)))
        case (0xc0, 0x3f) =>
          readBytes(4)
            .validate(_.size == 4, _ => "Not enough data for OP_RGBA")
            .map(data => OpRgba(data(0), data(1), data(2), data(3)))
        case (0x00, index) =>
          State.pure(OpIndex(index))
        case (0x40, diffs) =>
          // FIXME scala native doesn't like load2Bits here
          val dr = ((diffs >> 4) & 0x03) - 2
          val dg = ((diffs >> 2) & 0x03) - 2
          val db = (diffs & 0x03) - 2
          State.pure(OpDiff(dr, dg, db))
        case (0x80, dg) =>
          readByte.collect(
            { case Some(byte) => OpLuma(load6Bits(dg), load4Bits(byte >> 4), load4Bits(byte)) },
            _ => "Not enough data for OP_LUMA"
          )
        case (0xc0, run) =>
          State.pure(OpRun(run + 1))
      }

    def loadOps(bytes: LazyList[Int]): LazyList[Either[String, Op]] =
      if (bytes.isEmpty) LazyList.empty
      else
        fromBytes.run(bytes) match {
          case Left(error)            => LazyList(Left(error))
          case Right((remaining, op)) => Right(op) #:: loadOps(remaining)
        }
  }

  // Internal data structures
  case class QoiColor(r: Int, g: Int, b: Int, a: Int) {
    def toMinartColor = Color(r, g, b)
    def hash          = hashColor(r, g, b, a)
  }

  case class QoiState(
      imageAcc: List[QoiColor] = Nil,
      colorMap: Vector[QoiColor] = Vector.fill(64)(QoiColor(0, 0, 0, 0))
  ) {
    lazy val previousColor = imageAcc.headOption.getOrElse(QoiColor(0, 0, 0, 255))

    def addColor(color: QoiColor): QoiState = {
      QoiState(color :: imageAcc, colorMap.updated(color.hash, color))
    }

    def next(chunk: Op): QoiState = {
      import Op._
      chunk match {
        case OpRgb(red, green, blue) =>
          val color = QoiColor(red, green, blue, previousColor.a)
          addColor(color)
        case OpRgba(red, green, blue, alpha) =>
          val color = QoiColor(red, green, blue, alpha)
          addColor(color)
        case OpIndex(index) =>
          QoiState(colorMap(index) :: imageAcc, colorMap)
        case OpDiff(dr, dg, db) =>
          val color = QoiColor(
            wrapAround(previousColor.r + dr),
            wrapAround(previousColor.g + dg),
            wrapAround(previousColor.b + db),
            previousColor.a
          )
          addColor(color)
        case luma: OpLuma =>
          val color = QoiColor(
            wrapAround(previousColor.r + luma.dr),
            wrapAround(previousColor.g + luma.dg),
            wrapAround(previousColor.b + luma.db),
            previousColor.a
          )
          addColor(color)
        case OpRun(run) =>
          QoiState(List.fill(run)(previousColor) ++ imageAcc, colorMap)
      }
    }
  }

  def asSurface(ops: LazyList[Either[String, Op]], header: Header): Either[String, RamSurface] = {
    ops
      .foldLeft[Either[String, QoiState]](Right(QoiState())) { case (eitherState, eitherOp) =>
        for {
          state <- eitherState
          op    <- eitherOp
        } yield state.next(op)
      }
      .flatMap { finalState =>
        val flatPixels     = finalState.imageAcc.reverse
        val expectedPixels = (header.width * header.height).toInt
        Either.cond(
          flatPixels.size >= expectedPixels,
          new RamSurface(
            flatPixels.take(expectedPixels).map(_.toMinartColor).grouped(header.width.toInt).map(_.toArray).toVector
          ),
          s"Invalid number of pixels! Got ${flatPixels.size}, expected ${expectedPixels}"
        )
      }
  }

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val stream: LazyList[Int] = LazyList.continually(is.read()).takeWhile(_ != -1)
    Header.fromBytes(stream).flatMap { case (data, header) =>
      asSurface(Op.loadOps(data), header)
    }
  }
}
