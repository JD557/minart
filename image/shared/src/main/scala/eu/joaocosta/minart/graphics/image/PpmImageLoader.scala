package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.image.helpers._

@deprecated("Use eu.joaocosta.minart.graphics.image.ppm.PpmImageFormat instead")
final class PpmImageLoader[F[_]](val byteReader: ByteReader[F]) extends ppm.PpmImageLoader[F]

object PpmImageLoader {
  @deprecated("Use eu.joaocosta.minart.graphics.image.ppm.PpmImageFormat.defaultFormat instead")
  val defaultLoader = ppm.PpmImageFormat.defaultFormat
}
