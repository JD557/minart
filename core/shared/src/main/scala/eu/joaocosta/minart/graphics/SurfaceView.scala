package eu.joaocosta.minart.graphics

/** A view over a surface.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
trait SurfaceView extends Surface {
  def map(f: Color => Color): SurfaceView                 = new SurfaceView.MapView(this, f)
  def contramap(f: (Int, Int) => (Int, Int)): SurfaceView = new SurfaceView.ContramapView(this, f)
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    new SurfaceView.ClippedView(this, cx, cy, cw, ch)

  override def view: SurfaceView = this

  override def toRamSurface(): RamSurface =
    new RamSurface(Vector.tabulate(height)(y => Array.tabulate(width)(x => getPixel(x, y)).flatten))
}

object SurfaceView {

  /** A view over a surface that does nothing.
    */
  class IdentityView(inner: Surface) extends SurfaceView {
    def width: Int                              = inner.width
    def height: Int                             = inner.height
    def getPixel(x: Int, y: Int): Option[Color] = inner.getPixel(x, y)
    def getPixels(): Vector[Array[Color]]       = inner.getPixels()
  }

  /** A view over a surface that maps all colors.
    */
  class MapView(inner: Surface, f: Color => Color) extends SurfaceView {
    def width: Int                              = inner.width
    def height: Int                             = inner.height
    def getPixel(x: Int, y: Int): Option[Color] = inner.getPixel(x, y).map(f)
    def getPixels(): Vector[Array[Color]]       = inner.getPixels().map(_.map(f))
  }

  /** A view over a surface that contramaps the positions.
    */
  class ContramapView(inner: Surface, f: (Int, Int) => (Int, Int)) extends SurfaceView {
    def width: Int  = inner.width
    def height: Int = inner.height
    def getPixel(x: Int, y: Int): Option[Color] = {
      val (xx, yy) = f(x, y)
      inner.getPixel(xx, yy)
    }
    def getPixels(): Vector[Array[Color]] =
      Vector.tabulate(height)(y => Array.tabulate(width)(x => getPixel(x, y)).flatten)
  }

  /** A clipped view over a surface.
    */
  class ClippedView(inner: Surface, cx: Int, cy: Int, cw: Int, ch: Int) extends SurfaceView {
    def width: Int  = math.min(cw, inner.width - cx)
    def height: Int = math.min(ch, inner.height - cy)
    def getPixel(x: Int, y: Int): Option[Color] =
      inner.getPixel(cx + x, cy + y)
    def getPixels(): Vector[Array[Color]] =
      inner.getPixels().slice(cy, cy + ch).map(_.slice(cx, cx + cw))
  }
}
