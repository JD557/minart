package eu.joaocosta.minart.graphics.image.qoi

import java.io.InputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.internal._

/** Image reader for QOI files.
  */
trait QoiImageReader extends ImageReader {
  import QoiImageFormat._
  import QoiImageReader._
  import ByteReader._

  // Binary helpers
  private def wrapAround(b: Int): Int                = b & 0x0ff
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

  private def loadOps(bytes: CustomInputStream): Iterator[Either[String, Op]] = new Iterator[Either[String, Op]] {
    var currBytes = bytes
    def hasNext   = !ByteReader.isEmpty(currBytes)
    def next(): Either[String, Op] =
      opFromBytes.run(currBytes) match {
        case Left(error) => Left(error)
        case Right((remaining, op)) =>
          currBytes = remaining
          Right(op)
      }
  }

  // Image reconstruction
  private def asSurface(ops: Iterator[Either[String, Op]], header: Header): Either[String, RamSurface] = {
    ops
      .foldLeft[Either[String, QoiState]](Right(QoiState())) { case (eitherState, eitherOp) =>
        for {
          state <- eitherState
          op    <- eitherOp
        } yield nextState(state, op)
      }
      .flatMap { finalState =>
        val expectedPixels = (header.width * header.height).toInt
        Either.cond(
          finalState.imageAcc.size >= expectedPixels,
          new RamSurface(
            finalState.imageAcc.reverseIterator
              .take(expectedPixels)
              .map(_.minartColor)
              .grouped(header.width.toInt)
              .map(_.toArray)
              .toVector
          ),
          s"Invalid number of pixels! Got ${finalState.imageAcc.size}, expected ${expectedPixels}"
        )
      }
  }

  private def loadHeader(bytes: CustomInputStream): ParseResult[Header] = {
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

  final def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).flatMap { case (data, header) =>
      asSurface(loadOps(data), header)
    }
  }
}

object QoiImageReader {

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
