package eu.joaocosta.minart.backend

import java.awt.event.{KeyEvent, KeyListener => JavaKeyListener}
import java.awt.event.{MouseEvent, MouseListener => JavaMouseListener}
import java.awt.event.{WindowAdapter, WindowEvent}
import java.awt.image.{DataBufferInt, BufferedImage}
import java.awt.{Canvas => JavaCanvas, Color => JavaColor}
import java.awt.{Dimension, Graphics, GraphicsDevice, GraphicsEnvironment, MouseInfo}
import javax.swing.JFrame

import eu.joaocosta.minart.core.Canvas.Resource
import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

/** A low level Canvas implementation that shows the image in an AWT/Swing window.
  */
class AwtCanvas() extends LowLevelCanvas {

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  private[this] var javaCanvas: AwtCanvas.InnerCanvas      = _
  private[this] var keyListener: AwtCanvas.KeyListener     = _
  private[this] var mouseListener: AwtCanvas.MouseListener = _

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    changeSettings(newSettings)
  }
  def unsafeDestroy(): Unit = if (javaCanvas != null) {
    javaCanvas.frame.dispose()
    javaCanvas = null
  }
  def changeSettings(newSettings: Canvas.Settings) = {
    if (extendedSettings == null || newSettings != settings) {
      extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
      if (javaCanvas != null) javaCanvas.frame.dispose()
      javaCanvas = new AwtCanvas.InnerCanvas(
        extendedSettings.scaledWidth,
        extendedSettings.scaledHeight,
        newSettings.fullScreen,
        this
      )
      extendedSettings = extendedSettings.copy(
        windowWidth = javaCanvas.getWidth,
        windowHeight = javaCanvas.getHeight
      )
      keyListener = new AwtCanvas.KeyListener()
      mouseListener = new AwtCanvas.MouseListener(() =>
        for {
          point <-
            if (newSettings.fullScreen) Option(MouseInfo.getPointerInfo().getLocation())
            else Option(javaCanvas.getMousePosition())
          x <- Option(point.getX())
          y <- Option(point.getY())
        } yield PointerInput.Position(
          (x - extendedSettings.canvasX).toInt / newSettings.scale,
          (y - extendedSettings.canvasY).toInt / newSettings.scale
        )
      )
      javaCanvas.addKeyListener(keyListener)
      javaCanvas.addMouseListener(mouseListener)
      clear(Set(Resource.Backbuffer))
    }
  }

  private[this] def pack(r: Int, g: Int, b: Int): Int =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit =
    extendedSettings.pixelSize.foreach { dy =>
      val lineBase = (y * settings.scale + dy) * extendedSettings.scaledWidth
      extendedSettings.pixelSize.foreach { dx =>
        val baseAddr = lineBase + (x * settings.scale + dx)
        javaCanvas.imagePixels
          .setElem(baseAddr, c.argb)
      }
    }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit =
    javaCanvas.imagePixels
      .setElem(y * extendedSettings.scaledWidth + x % extendedSettings.scaledWidth, c.argb)

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    if (settings.scale == 1) putPixelUnscaled(x, y, color)
    else putPixelScaled(x, y, color)
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    Color.fromRGB(
      javaCanvas.imagePixels.getElem(
        y * settings.scale * extendedSettings.scaledWidth + (x * settings.scale)
      )
    )
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val flatData = javaCanvas.imagePixels.getData()
    extendedSettings.lines.map { y =>
      val lineBase = y * settings.scale * extendedSettings.scaledWidth
      extendedSettings.columns.map { x =>
        val baseAddr = (lineBase + (x * settings.scale))
        Color.fromRGB(flatData(baseAddr))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = try {
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyListener.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Pointer)) {
      mouseListener.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      extendedSettings.allPixels.foreach(i => javaCanvas.imagePixels.setElem(i, settings.clearColor.argb))
    }
  } catch { case _: Throwable => () }

  def redraw(): Unit = try {
    val g = javaCanvas.buffStrategy.getDrawGraphics()
    g.setColor(new JavaColor(settings.clearColor.rgb))
    g.fillRect(
      0,
      0,
      javaCanvas.getWidth,
      javaCanvas.getHeight
    )
    g.drawImage(
      javaCanvas.image,
      extendedSettings.canvasX,
      extendedSettings.canvasY,
      extendedSettings.scaledWidth,
      extendedSettings.scaledHeight,
      javaCanvas
    )
    g.dispose()
    javaCanvas.buffStrategy.show()
  } catch { case _: Throwable => () }

  def getKeyboardInput(): KeyboardInput = keyListener.getKeyboardInput()
  def getPointerInput(): PointerInput   = mouseListener.getPointerInput()
}

object AwtCanvas {
  private class InnerCanvas(scaledWidth: Int, scaledHeight: Int, fullScreen: Boolean, outerCanvas: AwtCanvas)
      extends JavaCanvas {

    override def getPreferredSize(): Dimension =
      new Dimension(scaledWidth, scaledHeight)

    val frame = new JFrame()
    frame.add(this)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setResizable(false)
    GraphicsEnvironment
      .getLocalGraphicsEnvironment()
      .getScreenDevices()
      .headOption
      .foreach(_.setFullScreenWindow(if (fullScreen) frame else null))

    this.createBufferStrategy(2)
    val buffStrategy = getBufferStrategy
    val image        = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB)
    val imagePixels  = image.getRaster.getDataBuffer.asInstanceOf[DataBufferInt]

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

    def keyPressed(ev: KeyEvent): Unit = synchronized {
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state = state.press(key))
    }
    def keyReleased(ev: KeyEvent): Unit = synchronized {
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state = state.release(key))
    }
    def keyTyped(ev: KeyEvent): Unit = ()
    def clearPressRelease(): Unit = synchronized {
      state = state.clearPressRelease()
    }
    def getKeyboardInput(): KeyboardInput = synchronized {
      state
    }
  }

  private class MouseListener(getMousePos: () => Option[PointerInput.Position]) extends JavaMouseListener {
    private[this] var state = PointerInput(None, Nil, Nil, false)

    def mousePressed(ev: MouseEvent): Unit = synchronized {
      state = state.move(getMousePos()).press
    }
    def mouseReleased(ev: MouseEvent): Unit = synchronized {
      state = state.move(getMousePos()).release
    }
    def mouseClicked(ev: MouseEvent): Unit = ()
    def mouseEntered(ev: MouseEvent): Unit = ()
    def mouseExited(ev: MouseEvent): Unit  = ()
    def clearPressRelease(): Unit = synchronized {
      state = state.clearPressRelease()
    }
    def getPointerInput(): PointerInput = synchronized {
      state.move(getMousePos())
    }
  }
}
