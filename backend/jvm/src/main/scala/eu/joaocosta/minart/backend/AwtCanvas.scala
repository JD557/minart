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
import java.util.concurrent.ConcurrentLinkedQueue
import javax.swing.{JFrame, WindowConstants}

import scala.collection.JavaConverters._

import eu.joaocosta.minart.graphics.LowLevelCanvas.ExtendedSettings
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input.KeyboardInput.Key
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

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    changeSettings(newSettings)
  }

  def changeSettings(newSettings: Canvas.Settings) = {
    if (extendedSettings == null || newSettings != settings) {
      extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
      val image = new BufferedImage(newSettings.width, newSettings.height, BufferedImage.TYPE_INT_ARGB)
      surface = new BufferedImageSurface(image)
      if (javaCanvas != null) javaCanvas.frame.dispose()
      javaCanvas = new AwtCanvas.InnerCanvas(
        extendedSettings.scaledWidth,
        extendedSettings.scaledHeight,
        newSettings.fullScreen,
        newSettings.title,
        this
      )
      extendedSettings = extendedSettings.copy(
        windowWidth = javaCanvas.getWidth,
        windowHeight = javaCanvas.getHeight
      )
      keyListener = new AwtCanvas.KeyListener()
      mouseListener = new AwtCanvas.MouseListener(javaCanvas, extendedSettings)
      javaCanvas.addKeyListener(keyListener)
      javaCanvas.frame.addKeyListener(keyListener)
      javaCanvas.addMouseListener(mouseListener)
      javaCanvas.frame.addMouseListener(mouseListener)
      clear(Set(Canvas.Buffer.Backbuffer))
    }
  }

  // Cleanup

  def unsafeDestroy(): Unit = if (javaCanvas != null) {
    javaCanvas.frame.dispose()
    javaCanvas = null
  }

  // Canvas operations

  def clear(buffers: Set[Canvas.Buffer]): Unit = {
    if (buffers.contains(Canvas.Buffer.KeyboardBuffer)) {
      keyListener.clearPressRelease()
    }
    if (buffers.contains(Canvas.Buffer.PointerBuffer)) {
      mouseListener.clearPressRelease()
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
    private[this] val events = new ConcurrentLinkedQueue[KeyListener.KeyboardEvent]()
    private[this] var state  = KeyboardInput.empty

    private[this] def computeState(): KeyboardInput = synchronized {
      state = events.asScala.foldLeft(state) {
        case (st, KeyListener.KeyboardEvent.Pressed(key)) =>
          st.press(key)
        case (st, KeyListener.KeyboardEvent.Released(key)) =>
          st.release(key)
      }
      events.clear()
      state
    }

    private[this] def pushEvent(ev: KeyListener.KeyboardEvent): Unit = {
      events.add(ev)
      if (events.size > 20) computeState()
    }

    def keyPressed(ev: KeyEvent): Unit =
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => pushEvent(KeyListener.KeyboardEvent.Pressed(key)))
    def keyReleased(ev: KeyEvent): Unit =
      AwtKeyMapping.getKey(ev.getKeyCode).foreach(key => pushEvent(KeyListener.KeyboardEvent.Released(key)))
    def keyTyped(ev: KeyEvent): Unit = ()
    def clearPressRelease(): Unit = synchronized {
      state = state.clearPressRelease()
    }
    def getKeyboardInput(): KeyboardInput =
      computeState()
  }

  private object KeyListener {
    sealed trait KeyboardEvent
    object KeyboardEvent {
      case class Pressed(key: Key)  extends KeyboardEvent
      case class Released(key: Key) extends KeyboardEvent
    }
  }

  private class MouseListener(canvas: JavaCanvas, extendedSettings: ExtendedSettings) extends JavaMouseListener {
    private[this] val events          = new ConcurrentLinkedQueue[MouseListener.MouseEvent]()
    @volatile private[this] var state = PointerInput.empty

    private[this] def computeState(): PointerInput = synchronized {
      state = events.asScala.foldLeft(state) {
        case (st, MouseListener.MouseEvent.Pressed(pos)) =>
          st.move(pos).press
        case (st, MouseListener.MouseEvent.Released(pos)) =>
          st.move(pos).release
      }
      events.clear()
      state
    }

    private[this] def pushEvent(ev: MouseListener.MouseEvent): Unit = {
      events.add(ev)
      if (events.size > 20) computeState()
    }

    def getMousePos(): Option[PointerInput.Position] = {
      val point =
        if (extendedSettings.settings.fullScreen) MouseInfo.getPointerInfo().getLocation()
        else canvas.getMousePosition()
      Option(point).map { p =>
        PointerInput.Position(
          (p.x - extendedSettings.canvasX) / extendedSettings.settings.scale,
          (p.y - extendedSettings.canvasY) / extendedSettings.settings.scale
        )
      }
    }

    def mousePressed(ev: MouseEvent): Unit  = pushEvent(MouseListener.MouseEvent.Pressed(getMousePos()))
    def mouseReleased(ev: MouseEvent): Unit = pushEvent(MouseListener.MouseEvent.Released(getMousePos()))
    def mouseClicked(ev: MouseEvent): Unit  = ()
    def mouseEntered(ev: MouseEvent): Unit  = ()
    def mouseExited(ev: MouseEvent): Unit   = ()
    def clearPressRelease(): Unit = synchronized {
      state = state.clearPressRelease()
    }
    def getPointerInput(): PointerInput = computeState().move(getMousePos())
  }

  private object MouseListener {
    sealed trait MouseEvent
    object MouseEvent {
      case class Pressed(pos: Option[PointerInput.Position])  extends MouseEvent
      case class Released(pos: Option[PointerInput.Position]) extends MouseEvent
    }
  }
}
