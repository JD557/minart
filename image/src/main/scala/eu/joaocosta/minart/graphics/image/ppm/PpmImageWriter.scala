package eu.joaocosta.minart.graphics.image.ppm

import java.io.OutputStream

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image writer for PPM files.
  *
  * Stores data as P6 PPM files with a 8 bit color range.
  */
trait PpmImageWriter extends ImageWriter {
  import ByteWriter.*

  private def colorToBytes(color: Color): Array[Byte] =
    Array(color.r.toByte, color.g.toByte, color.b.toByte)

  private def storeHeader(surface: Surface): ByteStreamState[String] =
    for {
      _ <- writeStringLn("P6")
      _ <- writeStringLn(s"${surface.width} ${surface.height}")
      _ <- writeStringLn("255")
    } yield ()

  final def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      _ <- append(surface.getPixels().iterator.flatten.map(colorToBytes))
    } yield ()
    toOutputStream(state, os)
  }
}
