package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics.RamSurface
import eu.joaocosta.minart.graphics.image.helpers._

@deprecated("Use eu.joaocosta.minart.graphics.image.qoi.QoiImageFormat instead")
final class QoiImageLoader[F[_]](val byteReader: ByteReader[F[Int]]) extends ImageLoader { self =>
  private val reader = new qoi.QoiImageReader[F[Int]] { val byteReader = self.byteReader }
  def loadImage(is: InputStream): Either[String, RamSurface] = reader.loadImage(is)
}

object QoiImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.qoi.QoiImageFormat.defaultFormat instead")
  val defaultLoader = qoi.QoiImageFormat.defaultFormat
}
