package eu.joaocosta.minart.graphics

/** A Surface is an object that contains a set of pixels.
  */
trait Surface {

  /** The surface width */
  def width: Int

  /** The surface height */
  def height: Int

  /** Gets the color from the this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getPixels` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def getPixel(x: Int, y: Int): Option[Color]

  /** Returns the pixels from this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return color matrix
    */
  def getPixels(): Vector[Array[Color]]
}

object Surface {

  /** A surface that can be drawn on using mutable operations.
    */
  trait MutableSurface extends Surface {

    /** Put a pixel in the surface with a certain color.
      *
      * @param x pixel x position
      * @param y pixel y position
      * @param color `Color` to apply to the pixel
      */
    def putPixel(x: Int, y: Int, color: Color): Unit

    /** Fill the surface with a certain color
      *
      * @param color `Color` to fill the surface with
      */
    def fill(color: Color): Unit

    /** Draws a surface on top of this surface.
      *
      * @param that surface to draw
      * @param x leftmost pixel on the destination surface
      * @param y topmost pixel on the destination surface
      * @param cx leftmost pixel on the source surface
      * @param cy topmost pixel on the source surface
      * @param cw clip width of the source surface
      * @param ch clip height of the source surface
      */
    def blit(
        that: Surface
    )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = {
      val minYClip   = -y
      val minXClip   = -x
      val maxYClip   = this.height - y
      val maxXClip   = this.width - x
      val yRange     = (math.max(0, minYClip) until math.min(ch, maxYClip))
      val xRange     = (math.max(0, minXClip) until math.min(cw, maxXClip))
      val thatPixels = that.getPixels()
      for {
        dy <- yRange
        sourceY = dy + cy
        destY   = dy + y
        dx <- xRange
        sourceX = dx + cx
        destX   = dx + x
        color   = thatPixels(sourceY)(sourceX)
      } putPixel(destX, destY, color)
    }

    /** Draws a surface on top of this surface and masks the pixels with a certain color.
      *
      * @param that surface to draw
      * @param mask color to usa as a mask
      * @param x leftmost pixel on the destination surface
      * @param y topmost pixel on the destination surface
      * @param cx leftmost pixel on the source surface
      * @param cy topmost pixel on the source surface
      * @param cw clip width of the source surface
      * @param ch clip height of the source surface
      */
    def blitWithMask(
        that: Surface,
        mask: Color
    )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = {
      val minYClip   = -y
      val minXClip   = -x
      val maxYClip   = this.height - y
      val maxXClip   = this.width - x
      val yRange     = (math.max(0, minYClip) until math.min(ch, maxYClip))
      val xRange     = (math.max(0, minXClip) until math.min(cw, maxXClip))
      val thatPixels = that.getPixels()
      for {
        dy <- yRange
        sourceY = dy + cy
        destY   = dy + y
        dx <- xRange
        sourceX = dx + cx
        destX   = dx + x
        color   = thatPixels(sourceY)(sourceX)
        if color != mask
      } putPixel(destX, destY, color)
    }
  }
}
