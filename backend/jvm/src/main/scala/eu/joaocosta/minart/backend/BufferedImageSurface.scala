package eu.joaocosta.minart.backend

import java.awt.image.{BufferedImage, DataBufferInt}

import eu.joaocosta.minart.graphics.{BlendMode, Color, MutableSurface, Surface}

/** Mutable image surface backed by an AWT Buffered Image.
  */
final class BufferedImageSurface(val bufferedImage: BufferedImage) extends MutableSurface {
  val width              = bufferedImage.getWidth()
  val height             = bufferedImage.getHeight()
  private val dataBuffer = bufferedImage.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

  def unsafeGetPixel(x: Int, y: Int): Color =
    Color.fromARGB(dataBuffer.getElem(y * width + x))

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer.setElem(y * width + x, color.argb)

  override def fill(color: Color): Unit = {
    var i = 0
    while (i < height * width) {
      dataBuffer.setElem(i, color.argb)
      i += 1
    }
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val x1 = Math.max(x, 0)
    val y1 = Math.max(y, 0)
    val x2 = Math.min(x + w, width)
    val y2 = Math.min(y + h, height)
    if (x1 != x2 && y1 != y2) {
      var _y = y1
      while (_y < y2) {
        val lineBase = _y * width
        var _x       = x1
        while (_x < x2) {
          dataBuffer.setElem(lineBase + _x, color.argb)
          _x += 1
        }
        _y += 1
      }
    }
  }

  override def blit(
      that: Surface,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    (that, blendMode) match {
      case (img: BufferedImageSurface, BlendMode.Copy) =>
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
        super.blit(that, blendMode)(x, y, cx, cy, cw, ch)
    }
}
