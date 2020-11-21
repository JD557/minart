package eu.joaocosta.minart.backend

import java.awt.event.{ KeyEvent, KeyListener => JavaKeyListener }
import java.awt.event.{ MouseEvent, MouseListener => JavaMouseListener }
import java.awt.event.{ WindowAdapter, WindowEvent }
import java.awt.image.{ DataBufferInt, BufferedImage }
import java.awt.{ Canvas => JavaCanvas, Color => JavaColor, Graphics, Dimension }
import javax.swing.JFrame

import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

/**
 * A low level Canvas implementation that shows the image in an AWT/Swing window.
 */
class AwtCanvas(val settings: Canvas.Settings) extends LowLevelCanvas {

  private[this] var javaCanvas: AwtCanvas.InnerCanvas = _
  private[this] var keyListener: AwtCanvas.KeyListener = _
  private[this] var mouseListener: AwtCanvas.MouseListener = _

  def unsafeInit(): Unit = {
    javaCanvas = new AwtCanvas.InnerCanvas(settings.scaledWidth, settings.scaledHeight, this)
    keyListener = new AwtCanvas.KeyListener()
    mouseListener = new AwtCanvas.MouseListener(() => for {
      point <- Option(javaCanvas.getMousePosition())
      x <- Option(point.getX())
      y <- Option(point.getY())
    } yield PointerInput.Position(x.toInt / settings.scale, y.toInt / settings.scale))
    javaCanvas.addKeyListener(keyListener)
    javaCanvas.addMouseListener(mouseListener)
  }
  def unsafeDestroy(): Unit = {
    javaCanvas.frame.dispose()
    javaCanvas = null
  }

  private[this] def pack(r: Int, g: Int, b: Int): Int =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  private[this] def unpack(c: Int): Color =
    Color(
      r = ((c & 0x00FF0000) >> 16).toShort,
      g = ((c & 0x0000FF00) >> 8).toShort,
      b = ((c & 0x000000FF)).toShort)

  private[this] val allPixels = (0 until (settings.scaledHeight * settings.scaledWidth))
  private[this] val pixelSize = (0 until settings.scale)
  private[this] val lines = (0 until settings.height)
  private[this] val columns = (0 until settings.width)

  private[this] val packedClearColor = pack(settings.clearColor.r, settings.clearColor.g, settings.clearColor.b)

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit =
    pixelSize.foreach { dy =>
      val lineBase = (y * settings.scale + dy) * settings.scaledWidth
      pixelSize.foreach { dx =>
        val baseAddr = lineBase + (x * settings.scale + dx)
        javaCanvas.imagePixels
          .setElem(
            baseAddr,
            pack(c.r, c.g, c.b))
      }
    }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit =
    javaCanvas.imagePixels
      .setElem(
        y * settings.scaledWidth + x % settings.scaledWidth,
        pack(c.r, c.g, c.b))

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (settings.scale == 1) putPixelUnscaled(x, y, color)
    else putPixelScaled(x, y, color)

  def getBackbufferPixel(x: Int, y: Int): Color = {
    unpack(javaCanvas.imagePixels.getElem(y * settings.scale * settings.scaledWidth + (x * settings.scale)))
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val flatData = javaCanvas.imagePixels.getData()
    lines.map { y =>
      val lineBase = y * settings.scale * settings.scaledWidth
      columns.map { x =>
        val baseAddr = (lineBase + (x * settings.scale))
        unpack(flatData(baseAddr))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      allPixels.foreach(i => javaCanvas.imagePixels.setElem(i, packedClearColor))
    }
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyListener.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Pointer)) {
      mouseListener.clearPressRelease()
    }
  }

  def redraw(): Unit = {
    val g = javaCanvas.buffStrategy.getDrawGraphics()
    g.drawImage(javaCanvas.image, 0, 0, settings.scaledWidth, settings.scaledHeight, javaCanvas)
    g.dispose()
    javaCanvas.buffStrategy.show()
  }

  def getKeyboardInput(): KeyboardInput = keyListener.getKeyboardInput()
  def getPointerInput(): PointerInput = mouseListener.getPointerInput()
}

object AwtCanvas {
  private class InnerCanvas(scaledWidth: Int, scaledHeight: Int, outerCanvas: AwtCanvas) extends JavaCanvas {

    override def getPreferredSize(): Dimension =
      new Dimension(scaledWidth, scaledHeight)

    val frame = new JFrame()
    frame.add(this)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setResizable(false)

    this.createBufferStrategy(2)
    val buffStrategy = getBufferStrategy
    val image = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB)
    val imagePixels = image.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

    override def repaint() = outerCanvas.redraw()

    frame.setVisible(true)
    frame.addWindowListener(new WindowAdapter() {
      override def windowClosing(e: WindowEvent): Unit = {
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

  private class MouseListener(getMousePos: () => Option[PointerInput.Position]) extends JavaMouseListener {
    private[this] var state = PointerInput(None, Nil, Nil, false)

    def mousePressed(ev: MouseEvent): Unit = state = state.move(getMousePos()).press
    def mouseReleased(ev: MouseEvent): Unit = state = state.move(getMousePos()).release
    def mouseClicked(ev: MouseEvent): Unit = ()
    def mouseEntered(ev: MouseEvent): Unit = ()
    def mouseExited(ev: MouseEvent): Unit = ()
    def clearPressRelease(): Unit = state = state.clearPressRelease()
    def getPointerInput(): PointerInput = state.move(getMousePos())
  }
}
