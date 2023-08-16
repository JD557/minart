package eu.joaocosta.minart.graphics

/** Blend strategy to be used by the blitter */
sealed trait BlendMode
object BlendMode {

  /** Simply copy the pixel values */
  case object Copy extends BlendMode

  /** Copy all pixels except the ones with the same value as the mask */
  final case class ColorMask(mask: Color) extends BlendMode

  /** Copy all pixels with alpha greater than the provided value */
  final case class AlphaTest(alpha: Int) extends BlendMode

  /** Blends the surfaces assuming that the source uses premultiplied alpha */
  case object PremultAlphaAdd extends BlendMode
}
