package eu.joaocosta.minart.backend

import java.awt.image.{BufferedImage, DataBufferInt}

import eu.joaocosta.minart.graphics.{Color, MutableSurface, Surface}

/** Mutable image surface backed by an AWT Buffered Image.
  */
final class BufferedImageSurface(val bufferedImage: BufferedImage) extends MutableSurface {
  val width              = bufferedImage.getWidth()
  val height             = bufferedImage.getHeight()
  private val dataBuffer = bufferedImage.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

  def unsafeGetPixel(x: Int, y: Int): Color =
    Color.fromRGB(dataBuffer.getElem(y * width + x))

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer.setElem(y * width + x, color.argb)

  override def fill(color: Color): Unit = {
    var i = 0
    while (i < height * width) {
      dataBuffer.setElem(i, color.argb)
      i += 1
    }
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val _x = math.max(x, 0)
    val _y = math.max(y, 0)
    val _w = math.min(w, width - _x)
    val _h = math.min(h, height - _y)
    var yy = 0
    while (yy < _h) {
      val lineBase = (yy + _y) * width
      var xx       = 0
      while (xx < _w) {
        dataBuffer.setElem(lineBase + xx + _x, color.argb)
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
