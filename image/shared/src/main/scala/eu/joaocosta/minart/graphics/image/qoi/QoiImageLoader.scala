package eu.joaocosta.minart.graphics.image.qoi

import java.io.InputStream

import scala.collection.compat.immutable.LazyList

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for QOI files.
  */
trait QoiImageLoader[F[_]] extends ImageLoader {
  val byteReader: ByteReader[F]

  import QoiImageFormat._
  import QoiImageLoader._
  import byteReader._

  // Binary helpers
  private def wrapAround(b: Int): Int                = if (b >= 0) b % 256 else 256 + b
  private def load2Bits(b: Int, bias: Int = 2): Int  = (b & 0x03) - bias
  private def load4Bits(b: Int, bias: Int = 8): Int  = (b & 0x0f) - bias
  private def load6Bits(b: Int, bias: Int = 32): Int = (b & 0x3f) - bias

  // Op loading
  private val opFromBytes: ParseState[String, Op] = {
    import Op._
    readByte
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
          State.pure(OpDiff(load2Bits(diffs >> 4), load2Bits(diffs >> 2), load2Bits(diffs)))
        case (0x80, dg) =>
          readByte.collect(
            { case Some(byte) => OpLuma(load6Bits(dg), load4Bits(byte >> 4), load4Bits(byte)) },
            _ => "Not enough data for OP_LUMA"
          )
        case (0xc0, run) =>
          State.pure(OpRun(run + 1))
      }
  }

  private def loadOps(bytes: F[Int]): LazyList[Either[String, Op]] =
    if (isEmpty(bytes)) LazyList.empty
    else
      opFromBytes.run(bytes) match {
        case Left(error)            => LazyList(Left(error))
        case Right((remaining, op)) => Right(op) #:: loadOps(remaining)
      }

  // State iteration
  private def nextState(state: QoiState, chunk: Op): QoiState = {
    import Op._
    chunk match {
      case OpRgb(red, green, blue) =>
        val color = QoiColor(red, green, blue, state.previousColor.a)
        state.addColor(color)
      case OpRgba(red, green, blue, alpha) =>
        val color = QoiColor(red, green, blue, alpha)
        state.addColor(color)
      case OpIndex(index) =>
        QoiState(state.colorMap(index) :: state.imageAcc, state.colorMap)
      case OpDiff(dr, dg, db) =>
        val color = QoiColor(
          wrapAround(state.previousColor.r + dr),
          wrapAround(state.previousColor.g + dg),
          wrapAround(state.previousColor.b + db),
          state.previousColor.a
        )
        state.addColor(color)
      case luma: OpLuma =>
        val color = QoiColor(
          wrapAround(state.previousColor.r + luma.dr),
          wrapAround(state.previousColor.g + luma.dg),
          wrapAround(state.previousColor.b + luma.db),
          state.previousColor.a
        )
        state.addColor(color)
      case OpRun(run) =>
        QoiState(List.fill(run)(state.previousColor) ++ state.imageAcc, state.colorMap)
    }
  }

  // Image reconstruction
  private def asSurface(ops: LazyList[Either[String, Op]], header: Header): Either[String, RamSurface] = {
    ops
      .foldLeft[Either[String, QoiState]](Right(QoiState())) { case (eitherState, eitherOp) =>
        for {
          state <- eitherState.right
          op    <- eitherOp.right
        } yield nextState(state, op)
      }
      .right
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

  def loadHeader(bytes: F[Int]): ParseResult[Header] = {
    (
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

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).right.flatMap { case (data, header) =>
      asSurface(loadOps(data), header)
    }
  }
}

object QoiImageLoader {
  private final case class QoiColor(r: Int, g: Int, b: Int, a: Int) {
    def toMinartColor = Color(r, g, b)
    def hash          = (r * 3 + g * 5 + b * 7 + a * 11) % 64
  }

  private final case class QoiState(
      imageAcc: List[QoiColor] = Nil,
      colorMap: Vector[QoiColor] = Vector.fill(64)(QoiColor(0, 0, 0, 0))
  ) {
    lazy val previousColor = imageAcc.headOption.getOrElse(QoiColor(0, 0, 0, 255))

    def addColor(color: QoiColor): QoiState = {
      QoiState(color :: imageAcc, colorMap.updated(color.hash, color))
    }
  }
}
