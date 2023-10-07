package eu.joaocosta.minart.backend

import java.awt.event.{
  KeyEvent,
  KeyListener => JavaKeyListener,
  MouseEvent,
  MouseListener => JavaMouseListener,
  WindowAdapter,
  WindowEvent
}
import java.awt.image.BufferedImage
import java.awt.{Canvas => JavaCanvas, Color => JavaColor, Dimension, Graphics, GraphicsEnvironment, MouseInfo}
import javax.swing.{JFrame, WindowConstants}

import scala.jdk.CollectionConverters._

import eu.joaocosta.minart.graphics.LowLevelCanvas.ExtendedSettings
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** A low level Canvas implementation that shows the image in an AWT/Swing window.
  */
class AwtCanvas() extends SurfaceBackedCanvas {

  // Rendering resources

  private[this] var javaCanvas: AwtCanvas.InnerCanvas = _
  protected var surface: BufferedImageSurface         = _

  private[AwtCanvas] def javaRedraw(g: Graphics): Unit = if (javaCanvas != null) {
    g.setColor(new JavaColor(settings.clearColor.rgb))
    g.fillRect(
      0,
      0,
      javaCanvas.getWidth,
      javaCanvas.getHeight
    )
    g.drawImage(
      surface.bufferedImage,
      extendedSettings.canvasX,
      extendedSettings.canvasY,
      extendedSettings.scaledWidth,
      extendedSettings.scaledHeight,
      javaCanvas
    )
    javaCanvas.buffStrategy.show()
  }

  // Input resources

  private[this] var keyListener: AwtCanvas.KeyListener     = _
  private[this] var mouseListener: AwtCanvas.MouseListener = _

  // Initialization

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  protected def unsafeInit(): Unit = {}

  protected def unsafeApplySettings(newSettings: Canvas.Settings): LowLevelCanvas.ExtendedSettings = {
    val extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
    val image            = new BufferedImage(newSettings.width, newSettings.height, BufferedImage.TYPE_INT_RGB)
    surface = new BufferedImageSurface(image)
    if (javaCanvas != null) javaCanvas.frame.dispose()
    javaCanvas = new AwtCanvas.InnerCanvas(
      extendedSettings.scaledWidth,
      extendedSettings.scaledHeight,
      newSettings.fullScreen,
      newSettings.title,
      this
    )
    val fullExtendedSettings = extendedSettings.copy(
      windowWidth = javaCanvas.getWidth,
      windowHeight = javaCanvas.getHeight
    )
    keyListener = new AwtCanvas.KeyListener()
    mouseListener = new AwtCanvas.MouseListener(javaCanvas, fullExtendedSettings)
    javaCanvas.addKeyListener(keyListener)
    javaCanvas.frame.addKeyListener(keyListener)
    javaCanvas.addMouseListener(mouseListener)
    javaCanvas.frame.addMouseListener(mouseListener)
    clear(Set(Canvas.Buffer.Backbuffer))
    fullExtendedSettings
  }

  // Cleanup

  protected def unsafeDestroy(): Unit = if (javaCanvas != null) {
    javaCanvas.frame.dispose()
    javaCanvas = null
  }

  // Canvas operations

  def clear(buffers: Set[Canvas.Buffer]): Unit = {
    if (buffers.contains(Canvas.Buffer.KeyboardBuffer)) {
      keyListener.clearPressRelease()
    }
    if (buffers.contains(Canvas.Buffer.PointerBuffer)) {
      mouseListener.clearEvents()
    }
    if (buffers.contains(Canvas.Buffer.Backbuffer)) {
      surface.fill(settings.clearColor)
    }
  }

  def redraw(): Unit = try {
    val g = javaCanvas.buffStrategy.getDrawGraphics()
    javaRedraw(g)
    g.dispose()
  } catch { case _: Throwable => () }

  def getKeyboardInput(): KeyboardInput = keyListener.getKeyboardInput()
  def getPointerInput(): PointerInput   = mouseListener.getPointerInput()
}

object AwtCanvas {
  private class InnerCanvas(
      scaledWidth: Int,
      scaledHeight: Int,
      fullScreen: Boolean,
      title: String,
      outerCanvas: AwtCanvas
  ) extends JavaCanvas {

    override def getPreferredSize(): Dimension =
      new Dimension(scaledWidth, scaledHeight)

    val frame = new JFrame(title)
    frame.setUndecorated(fullScreen)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    frame.add(this)
    frame.pack()
    GraphicsEnvironment
      .getLocalGraphicsEnvironment()
      .getScreenDevices()
      .headOption
      .foreach(_.setFullScreenWindow(if (fullScreen) frame else null))

    this.createBufferStrategy(2)
    val buffStrategy = getBufferStrategy

    override def paint(g: Graphics) = outerCanvas.javaRedraw(g)
    frame.setVisible(true)
    frame.setResizable(false)
    frame.addWindowListener(new WindowAdapter() {
      override def windowClosing(e: WindowEvent): Unit = {
        outerCanvas.close()
      }
    });
  }

  private class KeyListener extends JavaKeyListener {
    private[this] var state = KeyboardInput.empty

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

  private class MouseListener(canvas: JavaCanvas, extendedSettings: ExtendedSettings) extends JavaMouseListener {
    @volatile private[this] var state = PointerInput.empty

    def getMousePos(): Option[PointerInput.Position] = {
      val point =
        if (extendedSettings.settings.fullScreen) MouseInfo.getPointerInfo().getLocation()
        else canvas.getMousePosition()
      Option(point).map { p =>
        PointerInput.Position(
          (p.x - extendedSettings.canvasX) / extendedSettings.scale,
          (p.y - extendedSettings.canvasY) / extendedSettings.scale
        )
      }
    }

    def mousePressed(ev: MouseEvent): Unit =
      synchronized {
        state = state.move(getMousePos()).press
      }
    def mouseReleased(ev: MouseEvent): Unit = synchronized {
      state = state.move(getMousePos()).release
    }
    def mouseClicked(ev: MouseEvent): Unit = ()
    def mouseEntered(ev: MouseEvent): Unit = ()
    def mouseExited(ev: MouseEvent): Unit  = ()
    def clearEvents(): Unit = synchronized {
      state = state.clearEvents()
    }
    def getPointerInput(): PointerInput = synchronized {
      state.move(getMousePos())
    }
  }
}
