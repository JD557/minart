package eu.joaocosta.minart.backend

import java.awt.image.{BufferedImage, DataBufferInt}

import eu.joaocosta.minart.graphics.{Color, Surface}

/** Mutable image surface backed by an AWT Buffered Image.
  */
final class BufferedImageSurface(val bufferedImage: BufferedImage) extends Surface.MutableSurface {
  val width               = bufferedImage.getWidth()
  val height              = bufferedImage.getHeight()
  private val imagePixels = bufferedImage.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

  def getPixel(x: Int, y: Int): Option[Color] = try {
    Some(
      Color.fromRGB(
        imagePixels.getElem(
          y * width + x
        )
      )
    )
  } catch { case _: Throwable => None }

  def getPixels(): Vector[Array[Color]] = try {
    imagePixels.getData().iterator.map(Color.fromRGB).grouped(width).map(_.toArray).toVector
  } catch { case _: Throwable => Vector.empty }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    imagePixels
      .setElem(y * width + x, color.argb)
  } catch { case _: Throwable => () }

  def fill(color: Color): Unit = try {
    var i = 0
    while (i < height * width) {
      imagePixels.setElem(i, color.argb)
      i += 1
    }
  } catch { case _: Throwable => () }

  override def blit(
      that: Surface
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = try {
    that match {
      case img: BufferedImageSurface =>
        val g = bufferedImage.getGraphics()
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
        super.blit(that)(x, y, cx, cy, cw, ch)
    }
  } catch { case _: Throwable => () }
}
