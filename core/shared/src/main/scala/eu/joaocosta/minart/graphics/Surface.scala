package eu.joaocosta.minart.graphics

/** A Surface is an object that contains a set of pixels.
  */
trait Surface {

  /** The surface width */
  def width: Int

  /** The surface height */
  def height: Int

  /** Returns a view over this surface.
    *
    *  Operations performed on a view are executed in a defered fashion.
    */
  def view: SurfaceView = new SurfaceView.IdentityView(this)

  /** Gets the color from the this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getPixels` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def getPixel(x: Int, y: Int): Option[Color]

  /** Returns the pixels from this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return color matrix
    */
  def getPixels(): Vector[Array[Color]]

  /** Copies this surface into a new surface stored in RAM */
  final def toRamSurface(): RamSurface = new RamSurface(getPixels())
}
