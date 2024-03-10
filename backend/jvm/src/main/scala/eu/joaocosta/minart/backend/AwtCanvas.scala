package eu.joaocosta.minart.backend

import java.awt.event.{
  KeyEvent,
  KeyListener as JavaKeyListener,
  MouseEvent,
  MouseListener as JavaMouseListener,
  WindowAdapter,
  WindowEvent
}
import java.awt.image.BufferedImage
import java.awt.{Canvas as JavaCanvas, Color as JavaColor, Dimension, Graphics, GraphicsEnvironment, MouseInfo}
import javax.swing.{JFrame, WindowConstants}

import scala.jdk.CollectionConverters.*

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.LowLevelCanvas.ExtendedSettings
import eu.joaocosta.minart.input.*

/** A low level Canvas implementation that shows the image in an AWT/Swing window.
  */
final class AwtCanvas() extends SurfaceBackedCanvas {

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

  private[this] val keyListener: AwtCanvas.KeyListener     = new AwtCanvas.KeyListener()
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
    mouseListener = new AwtCanvas.MouseListener(javaCanvas, fullExtendedSettings)
    // Handles input when the canvas is in focus
    javaCanvas.addKeyListener(keyListener)
    javaCanvas.addMouseListener(mouseListener)
    // Handles input when the canvas is out of focus
    javaCanvas.frame.addKeyListener(keyListener)
    javaCanvas.frame.addMouseListener(mouseListener)
    surface.fill(newSettings.clearColor)
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

    override def paint(g: Graphics) = outerCanvas.javaRedraw(g)

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
    val buffStrategy = getBufferStrategy()

    frame.setVisible(true)
    frame.setResizable(false)
    frame.addWindowListener(new WindowAdapter() {
      override def windowClosing(e: WindowEvent): Unit = {
        outerCanvas.close()
      }
    });
  }

  private final class KeyListener extends JavaKeyListener {
    private[this] var state = KeyboardInput.empty

    def keyPressed(ev: KeyEvent): Unit =
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state.synchronized { state = state.press(key) })
    def keyReleased(ev: KeyEvent): Unit = synchronized {
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => state.synchronized { state = state.release(key) })
    }
    def keyTyped(ev: KeyEvent): Unit = ()
    def clearPressRelease(): Unit = state.synchronized {
      state = state.clearPressRelease()
    }
    def getKeyboardInput(): KeyboardInput = state.synchronized {
      state
    }
  }

  private final class MouseListener(canvas: JavaCanvas, extendedSettings: ExtendedSettings) extends JavaMouseListener {
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
