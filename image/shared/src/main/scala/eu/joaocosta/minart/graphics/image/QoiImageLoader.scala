package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._

object QoiImageLoader extends ImageLoader {

  private val supportedFormats = Set("qoif")

  // Binary helpers
  private def wrapAround(b: Int): Int                = if (b >= 0) b % 256 else 256 + b
  private def load2Bits(b: Int, bias: Int = 2): Int  = (b & 0x03) - bias
  private def load4Bits(b: Int, bias: Int = 8): Int  = (b & 0x0f) - bias
  private def load6Bits(b: Int, bias: Int = 32): Int = (b & 0x3f) - bias
  private def load32Bits(b: Array[Int]): Int =
    (b(0) << 24) | (b(1) << 16) | (b(2) << 8) | b(3)
  private def hashColor(r: Int, g: Int, b: Int, a: Int): Int = (r * 3 + g * 5 + b * 7 + a * 11) % 64

  // Data formats
  case class Header(
      magic: String,
      width: Long,
      height: Long,
      channels: Byte,
      colorspace: Byte
  )
  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = {
      val data      = bytes.take(14).toArray
      val remaining = bytes.drop(14)
      if (data.size != 14) Left(s"Invalid header size: ${data.size}")
      else {
        lazy val magic = new String(data.take(4).map(_.toByte))
        for {
          validatedMagic <- Either.cond(
            supportedFormats(magic),
            magic,
            s"Unsupported format: $magic"
          )
        } yield Header(
          magic = validatedMagic,
          width = load32Bits(data.slice(4, 8)),
          height = load32Bits(data.slice(8, 12)),
          channels = bytes(12).toByte,
          colorspace = bytes(13).toByte
        ) -> remaining
      }
    }
  }

  sealed trait Op
  object Op {
    case class OpRgb(red: Int, green: Int, blue: Int)              extends Op
    case class OpRgba(red: Int, green: Int, blue: Int, alpha: Int) extends Op
    case class OpIndex(index: Int)                                 extends Op
    case class OpDiff(dr: Int, dg: Int, db: Int)                   extends Op
    case class OpLuma(dg: Int, drdg: Int, dbdg: Int) extends Op {
      val dr = drdg + dg
      val db = dbdg + dg
    }
    case class OpRun(run: Int) extends Op

    def fromBytes(bytes: LazyList[Int]): ParseResult[Op] = {
      if (bytes.isEmpty) Left("Can't read op from empty bytes")
      else {
        val tag = bytes.head
        (tag & 0xc0, tag & 0x3f) match {
          case (0xc0, 0x3e) =>
            val data = bytes.slice(1, 4).toArray
            Either.cond(data.size == 3, OpRgb(data(0), data(1), data(2)) -> bytes.drop(4), "Not enough data for OP_RGB")
          case (0xc0, 0x3f) =>
            val data = bytes.slice(1, 5).toArray
            Either.cond(
              data.size == 4,
              OpRgba(data(0), data(1), data(2), data(3)) -> bytes.drop(5),
              "Not enough data for OP_RGBA"
            )
          case (0x00, index) =>
            Right(OpIndex(index) -> bytes.tail)
          case (0x40, diffs) =>
            Right(OpDiff(load2Bits(diffs >> 4), load2Bits(diffs >> 2), load2Bits(diffs)) -> bytes.tail)
          case (0x80, dg) =>
            val data = bytes.slice(1, 2).toArray
            Either.cond(
              data.size == 1,
              OpLuma(load6Bits(dg), load4Bits(data(0) >> 4), load4Bits(data(0))) -> bytes.drop(2),
              "Not enough data for OP_LUMA"
            )
          case (0xc0, run) =>
            Right(OpRun(run + 1) -> bytes.tail)
        }
      }
    }

    def loadOps(bytes: LazyList[Int]): LazyList[Either[String, Op]] =
      if (bytes.isEmpty) LazyList.empty
      else
        fromBytes(bytes) match {
          case Left(error)            => LazyList(Left(error))
          case Right((op, remaining)) => Right(op) #:: loadOps(remaining)
        }
  }

  // Internal data structures
  case class QoiColor(r: Int, g: Int, b: Int, a: Int) {
    def toMinartColor = Color(r, g, b)
    def hash          = hashColor(r, g, b, a)
  }

  case class State(imageAcc: List[QoiColor] = Nil, colorMap: Vector[QoiColor] = Vector.fill(64)(QoiColor(0, 0, 0, 0))) {
    lazy val previousColor = imageAcc.headOption.getOrElse(QoiColor(0, 0, 0, 255))

    def addColor(color: QoiColor): State = {
      State(color :: imageAcc, colorMap.updated(color.hash, color))
    }

    def next(chunk: Op): State = {
      import Op._
      chunk match {
        case OpRgb(red, green, blue) =>
          val color = QoiColor(red, green, blue, previousColor.a)
          addColor(color)
        case OpRgba(red, green, blue, alpha) =>
          val color = QoiColor(red, green, blue, alpha)
          addColor(color)
        case OpIndex(index) =>
          State(colorMap(index) :: imageAcc, colorMap)
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
          State(List.fill(run)(previousColor) ++ imageAcc, colorMap)
      }
    }
  }

  def asSurface(ops: LazyList[Either[String, Op]], header: Header): Either[String, RamSurface] = {
    ops
      .foldLeft[Either[String, State]](Right(State())) { case (eitherState, eitherOp) =>
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
    Header.fromBytes(stream).flatMap { case (header, data) =>
      asSurface(Op.loadOps(data), header)
    }
  }
}
