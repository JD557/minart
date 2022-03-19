package eu.joaocosta.minart.graphics

/* A procedurally generated infinite surface.
 *
 * Can be clipped to create a surface
 */
class Plane(generator: (Int, Int) => Color) {
  def getPixel(x: Int, y: Int): Color = generator(x, y)
  def map(f: Color => Color): Plane   = new Plane((x, y) => f(generator(x, y)))
  def contramap(f: (Int, Int) => (Int, Int)): Plane = new Plane((x, y) => {
    val (xx, yy) = f(x, y)
    generator(xx, yy)
  })
  def zipWith(that: Plane, f: (Color, Color) => Color): Plane = new Plane((x, y) => {
    val c1 = this.getPixel(x, y)
    val c2 = that.getPixel(x, y)
    f(c1, c2)
  })
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
    this.clip(0, 0, that.width, that.height).zipWith(that, f)
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    new SurfaceView.ClippedView((x, y) => Some(this.getPixel(x, y)), cx, cy, cw, ch)
  def toSurfaceView(width: Int, height: Int): SurfaceView =
    clip(0, 0, width, height)
  def toRamSurface(width: Int, height: Int): RamSurface =
    toSurfaceView(width, height).toRamSurface()
}

object Plane {
  def fromFunction(generator: (Int, Int) => Color): Plane = new Plane(generator)
  def fromSurfaceWithFallback(surface: Surface, fallback: Color): Plane =
    new Plane((x, y) => surface.getPixel(x, y).getOrElse(fallback))
  def fromSurfaceWithRepetition(surface: Surface): Plane =
    new Plane((x, y) =>
      surface
        .getPixel(
          math.floorMod(x, surface.width),
          math.floorMod(y, surface.height)
        )
        .getOrElse(Color(0, 0, 0))
    )
}
