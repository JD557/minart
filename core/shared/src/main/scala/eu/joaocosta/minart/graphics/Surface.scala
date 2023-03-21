package eu.joaocosta.minart.graphics

/** A Surface is an object that contains a set of pixels.
  */
trait Surface {

  /** The surface width. */
  def width: Int

  /** The surface height. */
  def height: Int

  /** Returns a view over this surface.
    *
    *  Operations performed on a view are executed in a defered fashion.
    */
  def view: SurfaceView = SurfaceView(this)

  /** Gets the color from the this surface in an unsafe way.
    *
    * This operation is unsafe: reading a out of bounds pixel has undefined behavior.
    * You should only use this if the performance of `getPixel` and `getPixels` are not acceptable.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def unsafeGetPixel(x: Int, y: Int): Color

  /** Gets the color from the this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getPixels` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def getPixel(x: Int, y: Int): Option[Color] =
    if (x >= 0 && y >= 0 && x < width && y < height) Some(unsafeGetPixel(x, y))
    else None

  /** Returns the pixels from this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return color matrix
    */
  def getPixels(): Vector[Array[Color]] = {
    Vector.tabulate(height) { y =>
      Array.tabulate(width)(x => unsafeGetPixel(x, y))
    }
  }

  /** Copies this surface into a new surface stored in RAM. */
  final def toRamSurface(): RamSurface = new RamSurface(getPixels())
}
