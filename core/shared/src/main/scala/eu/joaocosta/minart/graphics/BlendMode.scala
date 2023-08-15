package eu.joaocosta.minart.graphics

/** Blend strategy to be used by the blitter */
sealed trait BlendMode
object BlendMode {

  /** Simply copy the pixel values */
  object Copy extends BlendMode

  /** Copy all pixels except the ones with the same value as the mask */
  final case class ColorMask(mask: Color) extends BlendMode
}
