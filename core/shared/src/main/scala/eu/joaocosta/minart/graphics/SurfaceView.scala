package eu.joaocosta.minart.graphics

/** A view over a surface.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
trait SurfaceView extends Surface {

  /** Maps the colors from this surface view. */
  final def map(f: Color => Color): SurfaceView = new SurfaceView.MapView(this, f)

  /** Contramaps the positions from this surface view. */
  final def contramap(f: (Int, Int) => (Int, Int), fallback: Color = SurfaceView.defaultColor): Plane =
    Plane.fromSurfaceWithFallback(this, fallback).contramap(f)

  /** Combines this view with a surface by combining their colors with the given function. */
  final def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView = new SurfaceView.ZipView(this, that, f)

  /** Combines this view with a plane by combining their colors with the given function. */
  final def zipWith(that: Plane, f: (Color, Color) => Color): SurfaceView =
    that.zipWith(this, (c1, c2) => f(c2, c1))

  /** Clips this view to a chosen rectangle
    *
    * @param cx leftmost pixel on the surface
    * @param cy topmost pixel on the surface
    * @param cw clip width
    * @param ch clip height
    */
  final def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    new SurfaceView.ClippedView(
      (x, y) => this.getPixel(x, y),
      cx,
      cy,
      math.min(cw, this.width - cx),
      math.min(ch, this.height - cy)
    )

  def getPixels(): Vector[Array[Color]] =
    Vector.tabulate(height)(y => Array.tabulate(width)(x => getPixel(x, y).getOrElse(SurfaceView.defaultColor)))

  override def view: SurfaceView = this
}

object SurfaceView {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety

  /** A view over a surface that does nothing.
    */
  final class IdentityView(inner: Surface) extends SurfaceView {
    def width: Int                                 = inner.width
    def height: Int                                = inner.height
    def getPixel(x: Int, y: Int): Option[Color]    = inner.getPixel(x, y)
    override def getPixels(): Vector[Array[Color]] = inner.getPixels()
  }

  /** A view over a surface that maps all colors.
    */
  final class MapView(inner: Surface, f: Color => Color) extends SurfaceView {
    def width: Int                              = inner.width
    def height: Int                             = inner.height
    def getPixel(x: Int, y: Int): Option[Color] = inner.getPixel(x, y).map(f)
  }

  /** A view that combines two surfaces.
    */
  final class ZipView(innerA: Surface, innerB: Surface, f: (Color, Color) => Color) extends SurfaceView {
    def width: Int  = math.min(innerA.width, innerB.width)
    def height: Int = math.min(innerA.height, innerB.height)
    def getPixel(x: Int, y: Int): Option[Color] = for {
      pixelA <- innerA.getPixel(x, y)
      pixelB <- innerB.getPixel(x, y)
    } yield f(pixelA, pixelB)
  }

  /** A clipped view over a surface or plane.
    */
  final class ClippedView(accessor: (Int, Int) => Option[Color], cx: Int, cy: Int, cw: Int, ch: Int)
      extends SurfaceView {
    def width: Int  = cw
    def height: Int = ch
    def getPixel(x: Int, y: Int): Option[Color] =
      accessor(cx + x, cy + y)
  }
}
