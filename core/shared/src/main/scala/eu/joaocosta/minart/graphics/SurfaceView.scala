package eu.joaocosta.minart.graphics

/** A view over a surface, stored as a plane limited by a width and height.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
final case class SurfaceView(plane: Plane, width: Int, height: Int) extends Surface {

  def getPixel(x: Int, y: Int): Option[Color] =
    if (x >= 0 && y >= 0 && x < width && y < height) Some(plane.getPixel(x, y))
    else None

  /** Maps the colors from this surface view. */
  def map(f: Color => Color): SurfaceView = copy(plane.map(f))

  /** Contramaps the positions from this surface view. */
  def contramap(f: (Int, Int) => (Int, Int)): Plane =
    plane.contramap(f)

  /** Combines this view with a surface by combining their colors with the given function. */
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
    that.view
      .zipWith(plane, f)
      .copy(
        width = math.min(that.width, width),
        height = math.min(that.height, height)
      )

  /** Combines this view with a plane by combining their colors with the given function. */
  def zipWith(that: Plane, f: (Color, Color) => Color): SurfaceView =
    copy(plane = plane.zipWith(that, f))

  /** Clips this view to a chosen rectangle
    *
    * @param cx leftmost pixel on the surface
    * @param cy topmost pixel on the surface
    * @param cw clip width
    * @param ch clip height
    */
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView = {
    val newWidth  = math.min(cw, this.width - cx)
    val newHeight = math.min(ch, this.height - cy)
    if (cx == 0 && cy == 0)
      if (newWidth == width && newHeight == height) this
      else copy(width = newWidth, height = newHeight)
    else
      SurfaceView(
        plane.contramap((x, y) => (cx + x, cy + y)),
        width = newWidth,
        height = newHeight
      )
  }

  def getPixels(): Vector[Array[Color]] =
    Vector.tabulate(height)(y => Array.tabulate(width)(x => getPixel(x, y).getOrElse(SurfaceView.defaultColor)))

  override def view: SurfaceView = this
}

object SurfaceView {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety

  def apply(surface: Surface): SurfaceView =
    SurfaceView(Plane.fromSurfaceWithFallback(surface, defaultColor), surface.width, surface.height)
}
