package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics.RamSurface
import eu.joaocosta.minart.graphics.image.helpers._

@deprecated("Use eu.joaocosta.minart.graphics.image.ppm.PpmImageFormat instead")
final class PpmImageLoader[F[_]](val byteReader: ByteReader[F]) extends ImageLoader { self =>
  private val reader = new ppm.PpmImageReader[F] { val byteReader = self.byteReader }
  def loadImage(is: InputStream): Either[String, RamSurface] = reader.loadImage(is)
}

object PpmImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.ppm.PpmImageFormat.defaultFormat instead")
  val defaultLoader = ppm.PpmImageFormat.defaultFormat
}
