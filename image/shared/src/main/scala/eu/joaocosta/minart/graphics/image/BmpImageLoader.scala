package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics.RamSurface
import eu.joaocosta.minart.internal._

@deprecated("Use eu.joaocosta.minart.graphics.image.bmp.BmpImageFormat instead")
final class BmpImageLoader[F[_]](val byteReader: ByteReader[F[Int]]) extends ImageLoader { self =>
  private val reader = new bmp.BmpImageReader[F[Int]] { val byteReader = self.byteReader }
  def loadImage(is: InputStream): Either[String, RamSurface] = reader.loadImage(is)
}

object BmpImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.bmp.BmpImageFormat.defaultFormat instead")
  val defaultLoader = bmp.BmpImageFormat.defaultFormat
}
