package eu.joaocosta.minart.graphics.image.ppm

import java.io.OutputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image writer for PPM files.
  *
  * Stores data as P6 PPM files with a 8 bit color range.
  */
trait PpmImageWriter[F[_]] extends ImageWriter {
  val byteWriter: ByteWriter[F]

  import byteWriter._

  private def storeBinaryRgbPixel(color: Color): ByteStreamState[String] =
    writeBytes(List(color.r, color.g, color.b))

  private def storePixels(
      storeColor: Color => ByteStreamState[String],
      surface: Surface,
      currentPixel: Int = 0,
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (currentPixel >= surface.width * surface.height) acc
    else {
      val color = surface.unsafeGetPixel(currentPixel % surface.width, currentPixel / surface.width)
      storePixels(storeColor, surface, currentPixel + 1, acc.flatMap(_ => storeColor(color)))
    }
  }

  private def storeHeader(surface: Surface): ByteStreamState[String] =
    for {
      _ <- writeStringLn("P6")
      _ <- writeStringLn(s"${surface.width} ${surface.height}")
      _ <- writeStringLn("255")
    } yield ()

  def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      _ <- storePixels(storeBinaryRgbPixel, surface)
    } yield ()
    toOutputStream(state, os)
  }
}
