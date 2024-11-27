package eu.joaocosta.minart.graphics

/** Canvas backed by a mutable surface.
  */
trait SurfaceBackedCanvas extends LowLevelCanvas {

  protected def surface: MutableSurface

  final def unsafePutPixel(x: Int, y: Int, color: Color): Unit =
    surface.unsafePutPixel(x, y, color)

  final def unsafeGetPixel(x: Int, y: Int): Color =
    surface.unsafeGetPixel(x, y)

  override final def getPixels(): Vector[Array[Color]] =
    surface.getPixels()

  final def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit =
    surface.fillRegion(x, y, w, h, color)

  override final def fill(color: Color): Unit =
    surface.fill(color)

  override final def blit(
      that: Surface,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    surface.blit(that, blendMode)(x, y, cx, cy, cw, ch)
}
