package eu.joaocosta.minart.graphics.image.kitty

import java.io.OutputStream

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image writer for Kitty images.
  *
  * Stores data as a 24 bit raw pixel data, using a=T (transmit and display image) and the direct transmission medium
  */
trait KittyImageWriter extends ImageWriter {
  import ByteWriter.*

  private val ESC = "\u001b"
  private val b64 = java.util.Base64.getEncoder()

  private def colorToBytes(color: Color): Array[Byte] =
    Array(color.r.toByte, color.g.toByte, color.b.toByte)

  private def storeHeader(surface: Surface): ByteStreamState[String] =
    for {
      _ <- writeString(s"${ESC}_Gf=24,a=T,s=${surface.width},v=${surface.height};")
    } yield ()

  final def storeImage(surface: Surface, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeHeader(surface)
      _ <- writeString(b64.encodeToString(surface.getPixels().iterator.flatten.flatMap(colorToBytes).toArray))
      _ <- writeStringLn(s"${ESC}\\")
    } yield ()
    toOutputStream(state, os)
  }
}
