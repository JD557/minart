package eu.joaocosta.minart.graphics

/** A view over a surface, stored as a plane limited by a width and height.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
final case class SurfaceView(plane: Plane, width: Int, height: Int) extends Surface { outer =>

  def unsafeGetPixel(x: Int, y: Int): Color =
    plane.getPixel(x, y)

  /** Maps the colors from this surface view. */
  def map(f: Color => Color): SurfaceView = copy(plane.map(f))

  /** Flatmaps the inner plane of this surface view */
  def flatMap(f: Color => Plane): SurfaceView =
    copy(plane = plane.flatMap(f))

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

  /** Coflatmaps this plane with a SurfaceView => Color function.
    * Effectively, each pixel of the new view is computed from a translated view, which can be used to
    * implement convolutions.
    */
  def coflatMap(f: SurfaceView => Color): SurfaceView =
    copy(plane = new Plane {
      def getPixel(x: Int, y: Int): Color =
        f(outer.clip(x, y, width - x, height - y))
    })

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
    else plane.clip(cx, cy, newWidth, newHeight)
  }

  /** Inverts a surface color. */
  def invertColor: SurfaceView = map(_.invert)

  /** Flips a surface horizontally. */
  def flipH: SurfaceView =
    copy(plane = plane.flipH.translate(width - 1, 0))

  /** Flips a surface vertically. */
  def flipV: SurfaceView =
    copy(plane = plane.flipV.translate(0, height - 1))

  /** Scales a surface. */
  def scale(sx: Double, sy: Double): SurfaceView =
    copy(plane = plane.scale(sx, sy), width = (width * sx).toInt, height = (height * sy).toInt)

  /** Scales a surface. */
  def scale(s: Double): SurfaceView = scale(s, s)

  /** Transposes a surface. */
  def transpose: SurfaceView =
    plane.transpose.toSurfaceView(height, width)

  /** Returns a plane that repeats this surface forever */
  def repeating: Plane =
    if (width <= 0 || height <= 0) Plane.fromConstant(SurfaceView.defaultColor)
    else
      new Plane {
        def getPixel(x: Int, y: Int): Color = {
          outer.plane.getPixel(SurfaceView.floorMod(x, outer.width), SurfaceView.floorMod(y, outer.height))
        }
      }

  /** Repeats this surface xTimes on the x axis and yTimes on the yAxis */
  def repeating(xTimes: Int, yTimes: Int): SurfaceView =
    repeating.toSurfaceView(width * xTimes, height * yTimes)

  /** Forces the surface to be computed and returns a new view.
    * Equivalent to `toRamSurface().view`.
    *
    * This can be particularly useful to force the computation before a heavy
    * coflatMap (e.g. a convolution with a large kernel) to avoid recomputing
    * the same pixel multiple times.
    */
  def precompute: SurfaceView = toRamSurface().view

  override def view: SurfaceView = this
}

object SurfaceView {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety
  private def floorMod(x: Int, y: Int): Int = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }

  /** Generates a surface view from a surface */
  def apply(surface: Surface): SurfaceView =
    SurfaceView(Plane.fromSurfaceWithFallback(surface, defaultColor), surface.width, surface.height)
}
