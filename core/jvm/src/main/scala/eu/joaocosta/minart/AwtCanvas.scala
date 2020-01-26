package eu.joaocosta.minart

import java.awt.image.{ DataBufferInt, BufferedImage }
import java.awt.{ Canvas => JavaCanvas, Color => JavaColor, Graphics, Dimension }
import javax.swing.JFrame

class AwtCanvas(
  val width: Int,
  val height: Int,
  val scale: Int = 1,
  val clearColor: Color = Color(255, 255, 255)) extends Canvas { outerCanvas =>

  private[this] val javaCanvas = new AwtCanvas.InnerCanvas(scaledWidth, scaledHeight, this)

  private[this] val deltas = for {
    dx <- 0 until scale
    dy <- 0 until scale
  } yield (dx, dy)

  private[this] def pack(r: Int, g: Int, b: Int): Int =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  private[this] def unpack(c: Int): Color =
    Color(
      r = (c & 0x00FF0000) >> 16,
      g = (c & 0x0000FF00) >> 8,
      b = (c & 0x000000FF))

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit = deltas.foreach {
    case (dx, dy) =>
      javaCanvas.imagePixels
        .setElem((y * scale + dy) * scaledWidth + (x * scale + dx) % scaledWidth, pack(c.r, c.g, c.b))
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit =
    javaCanvas.imagePixels
      .setElem(y * scaledWidth + x % scaledWidth, pack(c.r, c.g, c.b))

  private[this] val _putPixel =
    if (scale == 1) { (x: Int, y: Int, c: Color) => putPixelUnscaled(x, y, c) }
    else { (x: Int, y: Int, c: Color) => putPixelScaled(x, y, c) }

  def putPixel(x: Int, y: Int, color: Color): Unit = _putPixel(x, y, color)

  def getBackbufferPixel(x: Int, y: Int): Color = {
    unpack(javaCanvas.imagePixels.getElem(y * scale * scaledWidth + (x * scale) % scaledWidth))
  }

  def clear(): Unit = {
    val color = pack(clearColor.r, clearColor.g, clearColor.b)
    for { i <- (0 until (scaledWidth * scaledWidth)) } javaCanvas.imagePixels.setElem(i, color)
  }

  def redraw(): Unit = {
    val g = javaCanvas.buffStrategy.getDrawGraphics()
    g.drawImage(javaCanvas.image, 0, 0, scaledWidth, scaledHeight, javaCanvas)
    g.dispose()
    javaCanvas.buffStrategy.show()
  }
}

object AwtCanvas {
  private class InnerCanvas(scaledWidth: Int, scaledHeight: Int, outerCanvas: AwtCanvas) extends JavaCanvas {
    val frame = new JFrame()
    frame.setSize(new Dimension(scaledWidth, scaledHeight))
    frame.setMaximumSize(new Dimension(scaledWidth, scaledHeight))
    frame.setMinimumSize(new Dimension(scaledWidth, scaledHeight))
    frame.add(this)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()

    this.createBufferStrategy(2)
    val buffStrategy = getBufferStrategy
    val image = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB)
    val imagePixels = image.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

    override def repaint() = outerCanvas.redraw()

    frame.setVisible(true)
  }
}
