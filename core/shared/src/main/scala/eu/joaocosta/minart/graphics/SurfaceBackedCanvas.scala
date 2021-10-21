package eu.joaocosta.minart.graphics

/** Canvas backed by a mutable surface.
  */
trait SurfaceBackedCanvas extends LowLevelCanvas {

  protected def surface: Surface.MutableSurface

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    surface.putPixel(x, y, color)
  } catch { case _: Throwable => () }

  def getPixel(x: Int, y: Int): Option[Color] = try {
    surface.getPixel(x, y)
  } catch { case _: Throwable => None }

  def getPixels(): Vector[Array[Color]] = try {
    surface.getPixels()
  } catch { case _: Throwable => Vector.empty }

  def fill(color: Color): Unit = try {
    surface.fill(color)
  } catch { case _: Throwable => () }

  override def blit(
      that: Surface
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    surface.blit(that)(x, y, cx, cy, cw, ch)

  override def blitWithMask(
      that: Surface,
      mask: Color
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    surface.blitWithMask(that, mask)(x, y, cx, cy, cw, ch)
}
