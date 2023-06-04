package eu.joaocosta.minart.graphics

/** A surface that can be drawn on using mutable operations.
  */
trait MutableSurface extends Surface {

  /** Puts a pixel in the surface in an unsafe way.
    *
    * This operation is unsafe: writing a out of bounds pixel has undefined behavior.
    * You should only use this if the performance of `putPixel` is not acceptable.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def unsafePutPixel(x: Int, y: Int, color: Color): Unit

  /** Put a pixel in the surface with a certain color.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @param color `Color` to apply to the pixel
    */
  final def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x >= 0 && y >= 0 && x < width && y < height) {
      unsafePutPixel(x, y, color)
    }

  /** Fill part of the surface with a certain color.
    *
    * @param color `Color` to fill the surface with
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param w region width
    * @param h region height
    */
  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit

  /** Fill the whole surface with a certain color.
    *
    * @param color `Color` to fill the surface with
    */
  def fill(color: Color): Unit = fillRegion(0, 0, width, height, color)

  /** Draws a surface on top of this surface.
    *
    * @param that surface to draw
    * @param mask color to use as a mask (pixels with this color won't be merged)
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param cx leftmost pixel on the source surface
    * @param cy topmost pixel on the source surface
    * @param cw clip width of the source surface
    * @param ch clip height of the source surface
    */
  def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = {
    Blitter.fullBlit(this, that, mask, x, y, cx, cy, cw, ch)
  }
}
