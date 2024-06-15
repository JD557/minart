package eu.joaocosta.minart.graphics.image.pdi

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image writer for Pdi files.
  *
  * Stores data as uncompressed PlayDate PDIs.
  */
trait PdiImageWriter extends ImageWriter {
  import ByteWriter.*

  private def colorsToByte(acc: Byte, colors: List[Color], f: Color => Boolean): Byte = colors match {
    case Nil => acc
    case color :: rem =>
      if (f(color)) colorsToByte(((acc << 1) | 0x01).toByte, rem, f)
      else colorsToByte((acc << 1).toByte, rem, f)
  }

  private def lineToBytes(colors: List[Color], f: Color => Boolean): List[Byte] =
    colors.sliding(8, 8).map(cs => colorsToByte(0.toByte, cs.padTo(8, Color(0, 0, 0, 0)), f)).toList

  private def storeLine(colors: Array[Color], f: Color => Boolean): ByteStreamState[String] =
    writeBytes(lineToBytes(colors.toList, f).map(java.lang.Byte.toUnsignedInt))

  @tailrec
  private def storePixels(
      lines: Vector[Array[Color]],
      f: Color => Boolean,
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (lines.isEmpty) acc
    else storePixels(lines.tail, f, acc.flatMap(_ => storeLine(lines.head, f)))
  }

  private val storeHeader: ByteStreamState[String] = {
    (for {
      _ <- writeString("Playdate IMG")
      _ <- writeLENumber(0, 4) // Disable compression
    } yield ())
  }

  private def storeCellHeader(surface: Surface): ByteStreamState[String] = {
    (for {
      _ <- writeLENumber(surface.width, 2)
      _ <- writeLENumber(surface.height, 2)
      _ <- writeLENumber(math.ceil(surface.width / 8.0).toInt, 2)
      _ <- writeLENumber(0, 2)    // No padding
      _ <- writeLENumber(0, 2)    // No padding
      _ <- writeLENumber(0, 2)    // No padding
      _ <- writeLENumber(0, 2)    // No padding
      _ <- writeLENumber(0x03, 2) // Always enable transparency
    } yield ())
  }

  final def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader
      _ <- storeCellHeader(surface)
      _ <- storePixels(surface.getPixels(), color => math.max(math.max(color.r, color.g), color.b) >= 127)
      _ <- storePixels(surface.getPixels(), color => color.a > 0)
    } yield ()
    toOutputStream(state, os)
  }
}
