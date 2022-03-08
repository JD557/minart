package eu.joaocosta.minart.graphics

/** A Surface clone using a copy-on-write strategy
  */
class CowSurface(private var inner: Surface) extends Surface.MutableSurface {
  private var copied: Boolean = false
  def width: Int              = inner.width
  def height: Int             = inner.height
  def getPixel(x: Int, y: Int): Option[Color] =
    inner.getPixel(x, y)
  def getPixels(): Vector[Array[Color]] =
    inner.getPixels()
  def putPixel(x: Int, y: Int, color: Color): Unit =
    asRamSurface().putPixel(x, y, color)
  def fill(color: Color): Unit =
    asRamSurface().fill(color)

  /** Returns this Surface as a mutable RAM surface.
    *  Will create a copy if needed
    */
  def asRamSurface(): RamSurface = {
    if (!copied) {
      inner = new RamSurface(inner.getPixels())
      copied = true
    }
    inner.asInstanceOf[RamSurface]
  }
}
