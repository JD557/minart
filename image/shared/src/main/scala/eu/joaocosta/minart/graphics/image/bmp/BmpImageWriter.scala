package eu.joaocosta.minart.graphics.image.bmp

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.internal._

/** Image writer for BMP files.
  *
  * Stores data as uncompressed 24bit Windows BMPs.
  */
trait BmpImageWriter[ByteSeq] extends ImageWriter {
  val byteWriter: ByteWriter[ByteSeq]
  import byteWriter._

  private def storeBgrPixel(color: Color): ByteStreamState[String] =
    writeBytes(List(color.b, color.g, color.r))

  @tailrec
  private def storePixels(
      storeColor: Color => ByteStreamState[String],
      surface: Surface,
      width: Int,
      padding: Int,
      currentPixel: Int = 0,
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (currentPixel >= surface.width * surface.height) acc
    else {
      val x     = currentPixel % surface.width
      val y     = (surface.height - 1) - (currentPixel / surface.width) // lines are stored upside down
      val color = surface.unsafeGetPixel(x, y)
      val nextAcc = acc.flatMap { _ =>
        if (x == width - 1) storeColor(color).flatMap(_ => writeBytes(List.fill(padding)(0)))
        else storeColor(color)
      }
      storePixels(storeColor, surface, width, padding, currentPixel + 1, nextAcc)
    }
  }

  private def storeHeader(surface: Surface): ByteStreamState[String] = {
    (for {
      _ <- writeString("BM")
      _ <- writeLENumber(14 + 40 + 3 * surface.width * surface.height, 4) // BMP size
      _ <- writeBytes(List.fill(4)(0))
      _ <- writeLENumber(14 + 40, 4)                                      // BMP offset
      _ <- writeLENumber(40, 4)                                           // DIB Header size
      _ <- writeLENumber(surface.width, 4)
      _ <- writeLENumber(surface.height, 4)
      _ <- writeLENumber(1, 2)                                            // Color planes
      _ <- writeLENumber(24, 2)                                           // Bits per pixel
      _ <- writeLENumber(0, 4)                                            // No compression
      _ <- writeLENumber(0, 4)                                            // Image size (can be 0)
      _ <- writeLENumber(1, 4)                                            // Horizontal Res (1 px/meter)
      _ <- writeLENumber(1, 4)                                            // Vertical Res (1 px/meter)
      _ <- writeLENumber(0, 4)                                            // Pallete colors (can be 0)
      _ <- writeLENumber(0, 4)                                            // Important colors (can be 0)
    } yield ())
  }

  def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      _ <- storePixels(storeBgrPixel, surface, surface.width, BmpImageFormat.linePadding(surface.width, 24))
    } yield ()
    toOutputStream(state, os)
  }
}
