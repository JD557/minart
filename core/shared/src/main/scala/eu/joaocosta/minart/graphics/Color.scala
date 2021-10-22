package eu.joaocosta.minart.graphics

/** RGB Color */
final class Color private (val argb: Int) extends AnyVal {
  @inline def r: Int = (argb & 0x00ff0000) >> 16
  @inline def g: Int = (argb & 0x0000ff00) >> 8
  @inline def b: Int = (argb & 0x000000ff)

  @inline def rgb: Int  = (argb & 0x00ffffff)
  override def toString = s"Color($r,$g,$b)"
}

object Color {
  def apply(r: Int, g: Int, b: Int): Color =
    new Color((255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255))

  def fromRGB(rgb: Int): Color =
    new Color(0xff000000 | rgb)

  def unapply(color: Color): Some[(Int, Int, Int)] =
    Some(color.r, color.g, color.b)
}
