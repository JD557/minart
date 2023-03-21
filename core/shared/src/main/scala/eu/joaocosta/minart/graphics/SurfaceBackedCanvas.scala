package eu.joaocosta.minart.graphics

/** Canvas backed by a mutable surface.
  */
trait SurfaceBackedCanvas extends LowLevelCanvas {

  protected def surface: MutableSurface

  def putPixel(x: Int, y: Int, color: Color): Unit =
    surface.putPixel(x, y, color)

  def unsafeGetPixel(x: Int, y: Int): Color =
    surface.unsafeGetPixel(x, y)

  override def getPixels(): Vector[Array[Color]] =
    surface.getPixels()

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit =
    surface.fillRegion(x, y, w, h, color)

  override def fill(color: Color): Unit =
    surface.fill(color)

  override def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    surface.blit(that, mask)(x, y, cx, cy, cw, ch)
}
