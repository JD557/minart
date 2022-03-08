package eu.joaocosta.minart.graphics

/** A view over a surface.
  *  Allows lazy operations to be applied over a surface.
  *
  *  This can have a performance impact. However, a new RAM surface with the operations already applied can be constructed using `toRamSurface`
  */
trait SurfaceView extends Surface {
  def map(f: Color => Color): SurfaceView = new SurfaceView.MapView(this, f)
  def contramap(f: (Int, Int) => (Int, Int), width: Int, height: Int, fallback: Color = Color(0, 0, 0)): SurfaceView =
    new SurfaceView.ContramapView(this, f, width, height, fallback)
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView = new SurfaceView.ZipView(this, that, f)
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
  class ContramapView(inner: Surface, f: (Int, Int) => (Int, Int), val width: Int, val height: Int, fallback: Color)
      extends SurfaceView {
    def getPixel(x: Int, y: Int): Option[Color] = if (x >= 0 && x < width && y >= 0 && y < height) {
      val (xx, yy) = f(x, y)
      Some(inner.getPixel(xx, yy).getOrElse(fallback))
    } else None
    def getPixels(): Vector[Array[Color]] =
      Vector.tabulate(height)(y => Array.tabulate(width)(x => getPixel(x, y)).flatten)
  }

  /** A view that combines two surfaces.
    */
  class ZipView(innerA: Surface, innerB: Surface, f: (Color, Color) => Color) extends SurfaceView {
    def width: Int  = math.min(innerA.width, innerB.width)
    def height: Int = math.min(innerA.height, innerB.height)
    def getPixel(x: Int, y: Int): Option[Color] = for {
      pixelA <- innerA.getPixel(x, y)
      pixelB <- innerB.getPixel(x, y)
    } yield f(pixelA, pixelB)
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
