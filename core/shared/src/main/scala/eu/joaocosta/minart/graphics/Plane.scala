package eu.joaocosta.minart.graphics

/* A procedurally generated infinite surface.
 *
 * Can be clipped to create a surface
 */
final class Plane private (unboxedGenerator: (Int, Int) => Int) {
  def getPixel(x: Int, y: Int): Color = Color.fromRGB(unboxedGenerator(x, y))
  def map(f: Color => Color): Plane   = Plane.fromFunction((x, y) => f(getPixel(x, y)))
  def contramap(f: (Int, Int) => (Int, Int)): Plane = new Plane((x, y) => {
    val res = f(x, y)
    unboxedGenerator(res._1, res._2)
  })
  def zipWith(that: Plane, f: (Color, Color) => Color): Plane = Plane.fromFunction((x, y) => {
    val c1 = this.getPixel(x, y)
    val c2 = that.getPixel(x, y)
    f(c1, c2)
  })
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
    this.toSurfaceView(that.width, that.height).zipWith(that, f)
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    new SurfaceView.ClippedView((x, y) => Some(this.getPixel(x, y)), cx, cy, cw, ch)
  def toSurfaceView(width: Int, height: Int): SurfaceView =
    clip(0, 0, width, height)
  def toRamSurface(width: Int, height: Int): RamSurface =
    toSurfaceView(width, height).toRamSurface()
}

object Plane {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety

  def fromFunction(generator: (Int, Int) => Color): Plane = new Plane((x, y) => generator(x, y).argb)
  def fromSurfaceWithFallback(surface: Surface, fallback: Color): Plane =
    new Plane((x, y) => surface.getPixel(x, y).getOrElse(fallback).argb)
  def fromSurfaceWithRepetition(surface: Surface): Plane =
    new Plane((x, y) =>
      surface
        .getPixel(
          math.floorMod(x, surface.width),
          math.floorMod(y, surface.height)
        )
        .getOrElse(defaultColor)
        .argb
    )
}
