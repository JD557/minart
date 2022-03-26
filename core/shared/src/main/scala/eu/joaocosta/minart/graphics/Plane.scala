package eu.joaocosta.minart.graphics

/* A procedurally generated infinite surface.
 *
 * Can be clipped to create a surface.
 */
final class Plane private (unboxedGenerator: (Int, Int) => Int) {
  def getPixel(x: Int, y: Int): Color = Color.fromRGB(unboxedGenerator(x, y))

  /** Maps the colors from this plane. */
  def map(f: Color => Color): Plane = Plane.fromFunction((x, y) => f(getPixel(x, y)))

  /** Contramaps the positions from this plane. */
  def contramap(f: (Int, Int) => (Int, Int)): Plane = new Plane((x, y) => {
    val res = f(x, y)
    unboxedGenerator(res._1, res._2)
  })

  /** Combines this plane with another by combining their colors with the given function. */
  def zipWith(that: Plane, f: (Color, Color) => Color): Plane = Plane.fromFunction((x, y) => {
    val c1 = this.getPixel(x, y)
    val c2 = that.getPixel(x, y)
    f(c1, c2)
  })

  /** Combines this plane with a surface by combining their colors with the given function. */
  def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView =
    this.toSurfaceView(that.width, that.height).zipWith(that, f)

  /** Clips this plane to a chosen rectangle, returning a surface view.
    *
    * @param cx leftmost pixel on the plane
    * @param cy topmost pixel on the plane
    * @param cw clip width
    * @param ch clip height
    */
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    if (cx == 0 && cy == 0) toSurfaceView(cw, ch)
    else contramap((x, y) => (cx + x, cy + y)).toSurfaceView(cw, ch)

  /** Converts this plane to a surface view, assuming (0, 0) as the top-left corner
    *
    * @param width surface view width
    * @param height surface view height
    */
  def toSurfaceView(width: Int, height: Int): SurfaceView =
    SurfaceView(this, width, height)

  /** Converts this plane to a RAM surface, assuming (0, 0) as the top-left corner
    *
    * @param width surface width
    * @param height surface height
    */
  def toRamSurface(width: Int, height: Int): RamSurface =
    toSurfaceView(width, height).toRamSurface()
}

object Plane {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety
  private def floorMod(x: Int, y: Int): Int = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }

  /** Creates a plane from a generator function
    *
    * @param generator generator function
    */
  def fromFunction(generator: (Int, Int) => Color): Plane = new Plane((x, y) => generator(x, y).argb)

  /** Creates a plane from a surface, filling the remaining pixels with a fallback color.
    * @param surface reference surface
    * @param fallback fallback color
    */
  def fromSurfaceWithFallback(surface: Surface, fallback: Color): Plane =
    new Plane((x, y) => surface.getPixel(x, y).getOrElse(fallback).argb)

  /** Creates a plane from a surface, by repeating that surface accross both axis.
    * @param surface reference surface
    */
  def fromSurfaceWithRepetition(surface: Surface): Plane =
    new Plane((x, y) =>
      surface
        .getPixel(
          floorMod(x, surface.width),
          floorMod(y, surface.height)
        )
        .getOrElse(defaultColor)
        .argb
    )
}
