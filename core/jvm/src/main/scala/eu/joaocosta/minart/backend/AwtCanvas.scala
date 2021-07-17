package eu.joaocosta.minart.backend

import java.awt.event.{KeyEvent, KeyListener => JavaKeyListener}
import java.awt.event.{MouseEvent, MouseListener => JavaMouseListener}
import java.awt.event.{WindowAdapter, WindowEvent}
import java.awt.image.{DataBufferInt, BufferedImage}
import java.awt.{Canvas => JavaCanvas, Color => JavaColor}
import java.awt.{Dimension, Graphics, GraphicsDevice, GraphicsEnvironment, MouseInfo}
import java.util.concurrent.ConcurrentLinkedQueue
import javax.swing.JFrame
import javax.swing.WindowConstants
import scala.collection.JavaConverters._

import eu.joaocosta.minart.core.Canvas.Resource
import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

import eu.joaocosta.minart.core.LowLevelCanvas.ExtendedSettings

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
      mouseListener = new AwtCanvas.MouseListener(javaCanvas, extendedSettings)
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
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
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
    private[this] val events = new ConcurrentLinkedQueue[KeyListener.KeyboardEvent]()
    private[this] var state  = KeyboardInput(Set(), Set(), Set())

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
    @volatile private[this] var state = PointerInput(None, Nil, Nil, false)

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
