package eu.joaocosta.minart.graphics.image.bmp

import java.io.OutputStream

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image writer for BMP files.
  *
  * Stores data as uncompressed 24bit Windows BMPs.
  */
trait BmpImageWriter extends ImageWriter {
  import ByteWriter.*

  private def colorToBytes(color: Color): Array[Byte] =
    Array(color.b.toByte, color.g.toByte, color.r.toByte)

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

  final def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      padding      = Array.fill(BmpImageFormat.linePadding(surface.width, 24))(0.toByte)
      byteIterator = surface.getPixels().reverseIterator.flatMap(_.iterator.map(colorToBytes) ++ Iterator(padding))
      _ <- append(byteIterator)
    } yield ()
    toOutputStream(state, os)
  }
}
