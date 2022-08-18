package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.image.helpers._

@deprecated("Use eu.joaocosta.minart.graphics.image.qoi.QoiImageFormat instead")
final class QoiImageLoader[F[_]](val byteReader: ByteReader[F]) extends qoi.QoiImageLoader[F]

object QoiImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.qoi.QoiImageFormat.defaultFormat instead")
  val defaultLoader = qoi.QoiImageFormat.defaultFormat
}
