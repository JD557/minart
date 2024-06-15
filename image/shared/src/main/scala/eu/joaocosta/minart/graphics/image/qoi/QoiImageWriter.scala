package eu.joaocosta.minart.graphics.image.qoi

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image writer for QOI files.
  */
trait QoiImageWriter extends ImageWriter {
  import ByteWriter.*

  private def storeHeader(surface: Surface): ByteStreamState[String] = {
    (for {
      _ <- writeString("qoif")
      _ <- writeBENumber(surface.width, 4)
      _ <- writeBENumber(surface.height, 4)
      _ <- writeByte(3) // channels
      _ <- writeByte(0) // linear colorspace
    } yield ())
  }

  private def toOps(surface: Surface): List[Op] =
    surface
      .getPixels()
      .iterator
      .flatten
      .foldLeft(QoiImageWriter.QoiState()) { case (state, color) => state.addMinartColor(color) }
      .opAcc
      .reverse

  private def writeOp(op: Op): ByteStreamState[String] = op match {
    case Op.OpRgb(r, g, b)     => writeBytes(Vector(254, r, g, b))
    case Op.OpRgba(r, g, b, a) => writeBytes(Vector(255, r, g, b, a))
    case Op.OpIndex(index) =>
      if (index < 0 || index > 63) State.error(s"Invalid index: $index")
      else writeByte(index)
    case Op.OpRun(run) =>
      if (run < 1 || run > 62) State.error(s"Invalid run size: $run")
      else writeByte((run - 1) | 0xc0)
    case _ =>
      State.error("Unsupported Op")
  }

  @tailrec
  private def storeOps(ops: List[Op], acc: ByteStreamState[String] = emptyStream): ByteStreamState[String] = ops match {
    case Nil => acc
    case op :: xs =>
      val nextStream = (for {
        _ <- acc
        _ <- writeOp(op)
      } yield ())
      storeOps(ops.tail, nextStream)
  }

  private val storeTrail: ByteStreamState[String] = writeBytes(Vector(0, 0, 0, 0, 0, 0, 0, 1))

  final def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      _ <- storeOps(toOps(surface))
      _ <- storeTrail
    } yield ()
    toOutputStream(state, os)
  }
}

object QoiImageWriter {
  private final case class QoiState(
      opAcc: List[Op] = Nil,
      previousColor: Option[QoiColor] = None,
      colorMap: Vector[QoiColor] = Vector.fill(64)(QoiColor(0, 0, 0, 0))
  ) {
    def addMinartColor(color: Color): QoiState =
      addColor(QoiColor.fromMinartColor(color))
    def addColor(color: QoiColor): QoiState = {
      lazy val hash = color.hash
      val nextAcc =
        if (previousColor.contains(color)) opAcc match {
          case Op.OpRun(run) :: xs if run < 62 => Op.OpRun(run + 1) :: xs
          case Op.OpRun(run) :: _              => Op.OpRgb(color.r, color.g, color.b) :: opAcc
          case _                               => Op.OpRun(1) :: opAcc
        }
        else if (colorMap(hash) == color) Op.OpIndex(hash) :: opAcc
        else Op.OpRgb(color.r, color.g, color.b) :: opAcc
      QoiState(nextAcc, Some(color), colorMap.updated(hash, color))
    }
  }
}
