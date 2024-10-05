package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.geometry.*

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
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param w region width
    * @param h region height
    * @param color `Color` to fill the surface with
    */
  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit

  /** Fill part of the surface with a certain color.
    *
    * @param region axis aligned region
    * @param color `Color` to fill the surface with
    */
  def fillRegion(region: AxisAlignedBoundingBox, color: Color): Unit =
    fillRegion(region.x, region.y, region.width, region.height, color)

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
  @deprecated("Use blendMode instead of mask")
  def blit(
      that: Surface,
      mask: Option[Color]
  )(x: Int, y: Int, cx: Int, cy: Int, cw: Int, ch: Int): Unit = {
    mask match {
      case None        => blit(that, BlendMode.Copy)(x, y, cx, cy, cw, ch)
      case Some(color) => blit(that, BlendMode.ColorMask(color))(x, y, cx, cy, cw, ch)
    }
  }

  /** Draws a surface on top of this surface.
    *
    * @param that surface to draw
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param cx leftmost pixel on the source surface
    * @param cy topmost pixel on the source surface
    * @param cw clip width of the source surface
    * @param ch clip height of the source surface
    */
  def blit(
      that: Surface,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = {
    Blitter.fullBlit(this, that, blendMode, x, y, cx, cy, cw, ch)
  }

  /** Draws a plane on top of this surface.
    *
    * The plane will always fill the full surface, and the x, y parameters only define where the plane origin should be.
    *
    * @param that plane to draw
    * @param blendMode blend strategy to use
    * @param x position of the plane origin on the destination surface
    * @param y position of the plane origin on the destination surface
    */
  def blitPlane(
      that: Plane,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int): Unit = {
    val surface = that.clip(-x, -y, this.width, this.height)
    blit(surface, blendMode)(0, 0)
  }

  /** Draws a stroke on top of this surface.
    *
    * This API is *experimental* and might change in the near future.
    *
    * @param stroke shape to draw
    * @param color color of the line
    * @param x position of the shape origin on the destination surface
    * @param y position of the shape origin on the destination surface
    */
  def rasterizeStroke(
      stroke: Stroke,
      color: Color
  )(x: Int, y: Int): Unit = {
    Rasterizer.rasterizeStroke(this, stroke, color, x, y)
  }

  /** Draws a shape on top of this surface.
    *
    * This API is *experimental* and might change in the near future.
    *
    * @param shape shape to draw
    * @param frontfaceColor color of the front face
    * @param backfaceColor color of the back face
    * @param blendMode blend strategy to use
    * @param x position of the shape origin on the destination surface
    * @param y position of the shape origin on the destination surface
    */
  def rasterizeShape(
      shape: Shape,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color] = None,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int): Unit = {
    Rasterizer.rasterizeShape(this, shape.translate(x, y), frontfaceColor, backfaceColor, blendMode)
  }

  /** Draws the contour of a shape on top of this surface.
    *
    * This API is *experimental* and might change in the near future.
    *
    * @param shape shape whose countour to draw
    * @param color color of the line
    * @param x position of the shape origin on the destination surface
    * @param y position of the shape origin on the destination surface
    */
  def rasterizeContour(
      shape: Shape.ShapeWithContour,
      color: Color
  )(x: Int, y: Int): Unit = {
    shape.contour.foreach(stroke => rasterizeStroke(stroke, color)(x, y))
  }

  /** Modifies this surface using surface view transformations
    *
    * @param f operations to apply
    */
  def modify(f: SurfaceView => Surface): Unit = {
    blit(f(this.view).toRamSurface())(0, 0)
  }
}
