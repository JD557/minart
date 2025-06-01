package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.geometry.{AxisAlignedBoundingBox, Shape}

/** A view over a surface.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
sealed trait SurfaceView extends Surface {
  def unsafeGetPixel(x: Int, y: Int): Color

  /** Maps the colors from this surface view. */
  def map(f: Color => Color): SurfaceView

  /** Flatmaps the inner plane of this surface view */
  def flatMap(f: Color => Plane): SurfaceView

  /** Contramaps the positions from this surface view. */
  def contramap(f: (Int, Int) => (Int, Int)): Plane

  /** Combines this view with a surface by combining their colors with the given function. */
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView

  /** Combines this view with a plane by combining their colors with the given function. */
  def zipWith(that: Plane, f: (Color, Color) => Color): SurfaceView

  /** Coflatmaps this surface view with a SurfaceView => Color function.
    * Effectively, each pixel of the new view is computed from a translated view, which can be used to
    * implement convolutions.
    */
  def coflatMap(f: SurfaceView => Color): SurfaceView

  /** Coflatmaps this surface view with a convolution kernel.
    *
    *  Out of bounds pixels are treated as if the view was clamped into a plane
    */
  def coflatMap(kernel: Kernel): SurfaceView

  /** Clips this view to a chosen region
    *
    * @param cx leftmost pixel on the surface
    * @param cy topmost pixel on the surface
    * @param cw clip width
    * @param ch clip height
    */
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView

  /** Clips this view to a chosen rectangle
    *
    * @param region chosen region
    */
  def clip(region: AxisAlignedBoundingBox): SurfaceView = clip(region.x, region.y, region.width, region.height)

  /** Overlays a surface on top of this view.
    *
    * Similar to MutableSurface#blit, but for surface views.
    *
    * @param that surface to overlay
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination plane
    * @param y topmost pixel on the destination plane
    */
  def overlay(that: Surface, blendMode: BlendMode = BlendMode.Copy)(x: Int, y: Int): SurfaceView

  /** Overlays a shape on top of this view.
    *
    * Similar to MutableSurface#rasterize, but for surface views.
    *
    * This API is *experimental* and might change in the near future.
    *
    * @param that surface to overlay
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination plane
    * @param y topmost pixel on the destination plane
    */
  def overlayShape(
      that: Shape,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color] = None,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int): SurfaceView

  /** Inverts a surface color. */
  final def invertColor: SurfaceView = map(_.invert)

  /** Premultiplies the color channels with the alpha channel.
    *
    * If this surface is going to be used multiple times, it is usually recommended
    * to store this as a temporary surface instead of using a surface view.
    */
  final def premultiplyAlpha: SurfaceView = map(_.premultiplyAlpha)

  /** Flips a surface horizontally. */
  def flipH: SurfaceView

  /** Flips a surface vertically. */
  def flipV: SurfaceView

  /** Scales a surface. */
  def scale(sx: Double, sy: Double): SurfaceView

  /** Scales a surface. */
  final def scale(s: Double): SurfaceView = scale(s, s)

  /** Transposes a surface. */
  def transpose: SurfaceView

  /** Returns a plane that repeats this surface forever */
  def repeating: Plane

  /** Repeats this surface xTimes on the x axis and yTimes on the yAxis */
  final def repeating(xTimes: Int, yTimes: Int): SurfaceView =
    repeating.toSurfaceView(width * xTimes, height * yTimes)

  /** Returns a plane that clamps this surface when out of bounds */
  def clamped: Plane

  /** Forces the surface to be computed and returns a new view.
    * Equivalent to `toRamSurface().view`.
    *
    * This can be particularly useful to force the computation before a heavy
    * coflatMap (e.g. a convolution with a large kernel) to avoid recomputing
    * the same pixel multiple times.
    */
  final def precompute: SurfaceView = toRamSurface().view

  override def view: SurfaceView = this
}

object SurfaceView {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety
  private def floorMod(x: Int, y: Int): Int = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }
  private def clamp(minValue: Int, value: Int, maxValue: Int): Int =
    Math.max(minValue, Math.min(value, maxValue))

  /** Generates a surface view from a surface */
  def apply(surface: Surface): SurfaceView = surface match {
    case sv: SurfaceView        => sv
    case ramSurface: RamSurface => RamSurfaceView(ramSurface, 0, 0, surface.width, surface.height)
    case _ =>
      PlaneSurfaceView(Plane.fromSurfaceWithFallback(surface, defaultColor), surface.width, surface.height)
  }

  private[graphics] final case class PlaneSurfaceView(plane: Plane, width: Int, height: Int) extends SurfaceView {
    outer =>

    def unsafeGetPixel(x: Int, y: Int): Color =
      plane.getPixel(x, y)

    def map(f: Color => Color): SurfaceView = copy(plane.map(f))

    def flatMap(f: Color => Plane): SurfaceView =
      copy(plane = plane.flatMap(f))

    def contramap(f: (Int, Int) => (Int, Int)): Plane =
      plane.contramap(f)

    def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
      plane
        .zipWith(that, f)
        .clip(0, 0, Math.min(that.width, this.width), Math.min(that.height, this.height))

    def zipWith(that: Plane, f: (Color, Color) => Color): SurfaceView =
      copy(plane = plane.zipWith(that, f))

    def coflatMap(f: SurfaceView => Color): SurfaceView =
      copy(plane = new Plane {
        def getPixel(x: Int, y: Int): Color =
          f(outer.clip(x, y, width - x, height - y))
      })

    def coflatMap(kernel: Kernel): SurfaceView =
      copy(plane = clamped.coflatMap(kernel))

    def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView = {
      val newWidth  = Math.min(cw, this.width - cx)
      val newHeight = Math.min(ch, this.height - cy)
      if (cx == 0 && cy == 0 && newWidth == width && newHeight == height) this
      else plane.clip(cx, cy, newWidth, newHeight)
    }

    def overlay(that: Surface, blendMode: BlendMode = BlendMode.Copy)(x: Int, y: Int): SurfaceView =
      copy(plane = plane.overlay(that, blendMode)(x, y))

    def overlayShape(
        that: Shape,
        frontfaceColor: Option[Color],
        backfaceColor: Option[Color] = None,
        blendMode: BlendMode = BlendMode.Copy
    )(x: Int, y: Int): SurfaceView =
      copy(plane = plane.overlayShape(that, frontfaceColor, backfaceColor, blendMode)(x, y))

    def flipH: SurfaceView =
      copy(plane = plane.flipH.translate(width - 1, 0))

    def flipV: SurfaceView =
      copy(plane = plane.flipV.translate(0, height - 1))

    def scale(sx: Double, sy: Double): SurfaceView =
      if (sx == 1.0 && sy == 1.0) this
      else copy(plane = plane.scale(sx, sy), width = (width * sx).toInt, height = (height * sy).toInt)

    def transpose: SurfaceView =
      plane.transpose.toSurfaceView(height, width)

    def repeating: Plane =
      if (width <= 0 || height <= 0) Plane.fromConstant(SurfaceView.defaultColor)
      else
        new Plane {
          def getPixel(x: Int, y: Int): Color = {
            outer.plane.getPixel(SurfaceView.floorMod(x, outer.width), SurfaceView.floorMod(y, outer.height))
          }
        }

    def clamped: Plane =
      if (width <= 0 || height <= 0) Plane.fromConstant(SurfaceView.defaultColor)
      else
        new Plane {
          def getPixel(x: Int, y: Int): Color = {
            outer.plane.getPixel(SurfaceView.clamp(0, x, outer.width - 1), SurfaceView.clamp(0, y, outer.height - 1))
          }
        }

    override def view: SurfaceView = this
  }

  /** Optimized view of a clipped RamSurface */
  private[graphics] final case class RamSurfaceView(
      ramSurface: RamSurface,
      cx: Int,
      cy: Int,
      width: Int,
      height: Int
  ) extends SurfaceView {
    outer =>

    private inline def toPlaneSurfaceView(): SurfaceView =
      PlaneSurfaceView(Plane.fromSurfaceWithFallback(ramSurface, defaultColor), ramSurface.width, ramSurface.height)
        .clip(cx, cy, width, height)

    def unsafeGetPixel(x: Int, y: Int): Color = ramSurface.getPixelOrElse(cx + x, cy + y, SurfaceView.defaultColor)

    override def getPixels(): Vector[Array[Color]] = {
      val b = Vector.newBuilder[Array[Color]]
      b.sizeHint(height)
      var y = 0
      while (y < height) {
        if (width <= 0) {
          b += Array.empty[Color]
        } else {
          val base = (cy + y) * ramSurface.width + cx
          b += ramSurface.dataBuffer.slice(base, base + width)
        }
        y += 1
      }
      b.result()
    }

    def map(f: Color => Color): SurfaceView = toPlaneSurfaceView().map(f)

    def flatMap(f: Color => Plane): SurfaceView =
      toPlaneSurfaceView().flatMap(f)

    def contramap(f: (Int, Int) => (Int, Int)): Plane =
      toPlaneSurfaceView().contramap(f)

    def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
      toPlaneSurfaceView().zipWith(that, f)

    def zipWith(that: Plane, f: (Color, Color) => Color): SurfaceView =
      toPlaneSurfaceView().zipWith(that, f)

    def coflatMap(f: SurfaceView => Color): SurfaceView =
      toPlaneSurfaceView().coflatMap(f)

    def coflatMap(kernel: Kernel): SurfaceView =
      toPlaneSurfaceView().coflatMap(kernel)

    def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
      val newCx     = this.cx + cx
      val newCy     = this.cy + cy
      val newWidth  = Math.min(cw, this.width - cx)
      val newHeight = Math.min(ch, this.height - cy)
      copy(cx = newCx, cy = newCy, newWidth, newHeight)

    def overlay(that: Surface, blendMode: BlendMode = BlendMode.Copy)(x: Int, y: Int): SurfaceView =
      toPlaneSurfaceView().overlay(that, blendMode)(x, y)

    def overlayShape(
        that: Shape,
        frontfaceColor: Option[Color],
        backfaceColor: Option[Color] = None,
        blendMode: BlendMode = BlendMode.Copy
    )(x: Int, y: Int): SurfaceView =
      toPlaneSurfaceView().overlayShape(that, frontfaceColor, backfaceColor, blendMode)(x, y)

    def flipH: SurfaceView =
      toPlaneSurfaceView().flipH

    def flipV: SurfaceView =
      toPlaneSurfaceView().flipV

    def scale(sx: Double, sy: Double): SurfaceView =
      if (sx == 1.0 && sy == 1.0) this
      else toPlaneSurfaceView().scale(sx, sy)

    def transpose: SurfaceView =
      toPlaneSurfaceView().transpose

    def repeating: Plane =
      if (width <= 0 || height <= 0) Plane.fromConstant(SurfaceView.defaultColor)
      else
        new Plane {
          def getPixel(x: Int, y: Int): Color = {
            ramSurface.unsafeGetPixel(
              outer.cx + SurfaceView.floorMod(x, outer.width),
              outer.cy + SurfaceView.floorMod(y, outer.height)
            )
          }
        }

    def clamped: Plane =
      if (width <= 0 || height <= 0) Plane.fromConstant(SurfaceView.defaultColor)
      else
        new Plane {
          def getPixel(x: Int, y: Int): Color = {
            ramSurface.unsafeGetPixel(
              cx + SurfaceView.clamp(0, x, outer.width - 1),
              cy + SurfaceView.clamp(0, y, outer.height - 1)
            )
          }
        }

    override def view: SurfaceView = this
  }
}
