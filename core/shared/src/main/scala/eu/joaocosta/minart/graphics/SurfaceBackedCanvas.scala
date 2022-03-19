package eu.joaocosta.minart.graphics

/** Canvas backed by a mutable surface.
  */
trait SurfaceBackedCanvas extends LowLevelCanvas {

  protected def surface: Surface.MutableSurface

  def putPixel(x: Int, y: Int, color: Color): Unit =
    surface.putPixel(x, y, color)

  def getPixel(x: Int, y: Int): Option[Color] =
    surface.getPixel(x, y)

  def getPixels(): Vector[Array[Color]] =
    surface.getPixels()

  def fill(color: Color): Unit =
    surface.fill(color)

  override def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    surface.blit(that)(x, y, cx, cy, cw, ch)
}
