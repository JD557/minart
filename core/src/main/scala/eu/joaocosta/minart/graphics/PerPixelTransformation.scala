package eu.joaocosta.minart.graphics

/** Function describing how to transform pixels in an image:
  * Given a color and a position, returns the new color.
  */
trait PerPixelTransformation {
  def apply(color: Color, x: Int, y: Int): Color
}
