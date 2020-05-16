package eu.joaocosta.minart.backend

import java.awt.event.{ WindowAdapter, WindowEvent }
import java.awt.event.{ KeyEvent, KeyListener => JavaKeyListener }
import java.awt.image.{ DataBufferInt, BufferedImage }
import java.awt.{ Canvas => JavaCanvas, Color => JavaColor, Graphics, Dimension }
import javax.swing.JFrame

import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

class AwtCanvas(val settings: Canvas.Settings) extends LowLevelCanvas {

  private[this] var javaCanvas: AwtCanvas.InnerCanvas = _
  private[this] var keyListener: AwtCanvas.KeyListener = _

  def unsafeInit(): Unit = {
    javaCanvas = new AwtCanvas.InnerCanvas(settings.scaledWidth, settings.scaledHeight, this)
    keyListener = new AwtCanvas.KeyListener()
    javaCanvas.addKeyListener(keyListener)
  }
  def unsafeDestroy(): Unit = {
    javaCanvas.frame.dispose()
    javaCanvas = null
  }

  private[this] val deltas = for {
    dx <- 0 until settings.scale
    dy <- 0 until settings.scale
  } yield (dx, dy)

  private[this] def pack(r: Int, g: Int, b: Int): Int =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  private[this] def unpack(c: Int): Color =
    Color(
      r = (c & 0x00FF0000) >> 16,
      g = (c & 0x0000FF00) >> 8,
      b = (c & 0x000000FF))

  private[this] val packedClearColor = pack(settings.clearColor.r, settings.clearColor.g, settings.clearColor.b)

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit = deltas.foreach {
    case (dx, dy) =>
      javaCanvas.imagePixels
        .setElem(
          (y * settings.scale + dy) * settings.scaledWidth + (x * settings.scale + dx) % settings.scaledWidth,
          pack(c.r, c.g, c.b))
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit =
    javaCanvas.imagePixels
      .setElem(
        y * settings.scaledWidth + x % settings.scaledWidth,
        pack(c.r, c.g, c.b))

  private[this] val _putPixel =
    if (settings.scale == 1) { (x: Int, y: Int, c: Color) => putPixelUnscaled(x, y, c) }
    else { (x: Int, y: Int, c: Color) => putPixelScaled(x, y, c) }

  def putPixel(x: Int, y: Int, color: Color): Unit = _putPixel(x, y, color)

  def getBackbufferPixel(x: Int, y: Int): Color = {
    unpack(javaCanvas.imagePixels.getElem(y * settings.scale * settings.scaledWidth + (x * settings.scale) % settings.scaledWidth))
  }

  def clear(): Unit = {
    for { i <- (0 until (settings.scaledWidth * settings.scaledWidth)) } javaCanvas.imagePixels.setElem(i, packedClearColor)
    keyListener.clearPressRelease()
  }

  def redraw(): Unit = {
    val g = javaCanvas.buffStrategy.getDrawGraphics()
    g.drawImage(javaCanvas.image, 0, 0, settings.scaledWidth, settings.scaledHeight, javaCanvas)
    g.dispose()
    javaCanvas.buffStrategy.show()
  }

  def getKeyboardInput(): KeyboardInput = keyListener.getKeyboardInput()
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
    frame.addWindowListener(new WindowAdapter() {
      override def windowClosing(e: WindowEvent) {
        outerCanvas.destroy()
      }
    });
  }

  private class KeyListener extends JavaKeyListener {
    private[this] var state = KeyboardInput(Set(), Set(), Set())

    def keyPressed(ev: KeyEvent): Unit = AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state = state.press(key))
    def keyReleased(ev: KeyEvent): Unit = AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state = state.release(key))
    def keyTyped(ev: KeyEvent): Unit = ()
    def clearPressRelease(): Unit = state = state.clearPressRelease()
    def getKeyboardInput(): KeyboardInput = state
  }
}
