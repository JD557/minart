package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.image.helpers._

@deprecated("Use eu.joaocosta.minart.graphics.image.bmp.BmpImageFormat instead")
final class BmpImageLoader[F[_]](val byteReader: ByteReader[F]) extends bmp.BmpImageLoader[F]

object BmpImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.bmp.BmpImageFormat.defaultFormat instead")
  val defaultLoader = bmp.BmpImageFormat.defaultFormat
}
