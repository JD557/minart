package eu.joaocosta.minart.graphics

/** A view over a surface, stored as a plane limited by a width and height.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
final case class SurfaceView(plane: Plane, width: Int, height: Int) extends Surface {

  def unsafeGetPixel(x: Int, y: Int): Color =
    plane.getPixel(x, y)

  /** Maps the colors from this surface view. */
  def map(f: Color => Color): SurfaceView = copy(plane.map(f))

  /** Contramaps the positions from this surface view. */
  def contramap(f: (Int, Int) => (Int, Int)): Plane =
    plane.contramap(f)

  /** Combines this view with a surface by combining their colors with the given function. */
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
    plane
      .zipWith(that, f)
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
    if (cx == 0 && cy == 0 && newWidth == width && newHeight == height) this
    else plane.clip(cx, cy, cw, ch)
  }

  /** Inverts a surface color. */
  def invertColor: SurfaceView = map(_.invert)

  /** Flips a surface horizontally. */
  def flipH: SurfaceView =
    contramap((x, y) => (width - x - 1, y))
      .toSurfaceView(width, height)

  /** Flips an surface vertically. */
  def flipV: SurfaceView = contramap((x, y) => (x, height - y - 1))
    .toSurfaceView(width, height)

  /** Transposes a surface. */
  def transpose: SurfaceView =
    plane.transpose.toSurfaceView(height, width)

  def getPixels(): Vector[Array[Color]] =
    Vector.tabulate(height)(y => Array.tabulate(width)(x => unsafeGetPixel(x, y)))

  override def view: SurfaceView = this
}

object SurfaceView {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety

  def apply(surface: Surface): SurfaceView =
    SurfaceView(Plane.fromSurfaceWithFallback(surface, defaultColor), surface.width, surface.height)
}
