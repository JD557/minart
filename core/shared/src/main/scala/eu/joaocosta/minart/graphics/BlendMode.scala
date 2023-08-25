package eu.joaocosta.minart.graphics

/** Blend strategy to be used to combine two colors
  *
  * Note that while this trait is unsealed, backends can provide opimized implementations
  * for the default blend modes.
  * As such, those should always be prefered to custom ones.
  */
trait BlendMode {

  /** Blends two colors
    *
    * @param src the color to overlay
    * @param dst the color to blend into
    */
  def blend(src: => Color, dst: => Color): Color
}
object BlendMode {

  /** Simply copy the pixel values */
  case object Copy extends BlendMode {
    def blend(src: => Color, dst: => Color): Color =
      src
  }

  /** Copy all pixels except the ones with the same value as the mask */
  final case class ColorMask(mask: Color) extends BlendMode {
    def blend(src: => Color, dst: => Color): Color = {
      val colorSource = src
      if (colorSource != mask) src else dst
    }
  }

  /** Copy all pixels with alpha greater than the provided value */
  final case class AlphaTest(alpha: Int) extends BlendMode {
    def blend(src: => Color, dst: => Color): Color = {
      val colorSource = src
      if (colorSource.a > alpha) src else dst
    }
  }

  /** Blends the surfaces using weighted adition: dstColor * (1-srcAlpha) + srcColor
    *  This behaves as normal alpha blending if the source uses premultiplied alpha.
    */
  case object AlphaAdd extends BlendMode {
    def blend(src: => Color, dst: => Color): Color = {
      val colorSource = src
      val colorDest   = dst
      Color(
        Math.min((colorDest.r * (255 - colorSource.a)) / 255 + colorSource.r, 255),
        Math.min((colorDest.g * (255 - colorSource.a)) / 255 + colorSource.g, 255),
        Math.min((colorDest.b * (255 - colorSource.a)) / 255 + colorSource.b, 255)
      )
    }
  }
}
