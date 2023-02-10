package eu.joaocosta.minart.backend

import java.awt.image.{BufferedImage, DataBufferInt}

import eu.joaocosta.minart.graphics.{Color, MutableSurface, Surface}

/** Mutable image surface backed by an AWT Buffered Image.
  */
final class BufferedImageSurface(val bufferedImage: BufferedImage) extends MutableSurface {
  val width               = bufferedImage.getWidth()
  val height              = bufferedImage.getHeight()
  private val imagePixels = bufferedImage.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

  def unsafeGetPixel(x: Int, y: Int): Color =
    Color.fromRGB(imagePixels.getElem(y * width + x))

  def getPixels(): Vector[Array[Color]] =
    imagePixels.getData().iterator.map(Color.fromRGB).grouped(width).map(_.toArray).toVector

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x >= 0 && y >= 0 && x < width && y < height)
      imagePixels
        .setElem(y * width + x, color.argb)

  override def fill(color: Color): Unit = {
    var i = 0
    while (i < height * width) {
      imagePixels.setElem(i, color.argb)
      i += 1
    }
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    var yy = 0
    while (yy < h) {
      val lineBase = yy * width
      var xx       = 0
      while (xx < w) {
        imagePixels.setElem(lineBase + xx + x, color.argb)
        xx += 1
      }
      yy += 1
    }
  }

  override def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    that match {
      case img: BufferedImageSurface if mask.isEmpty =>
        val g = bufferedImage.createGraphics()
        g.drawImage(
          img.bufferedImage,
          x,
          y,
          x + cw,
          y + ch,
          cx,
          cy,
          cx + cw,
          cy + ch,
          null
        )
        g.dispose()
      case _ =>
        super.blit(that, mask)(x, y, cx, cy, cw, ch)
    }
}
