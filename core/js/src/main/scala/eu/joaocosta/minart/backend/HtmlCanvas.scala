package eu.joaocosta.minart.backend

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => JsCanvas}
import org.scalajs.dom.raw.{ImageData, MouseEvent, KeyboardEvent, TouchEvent}

import eu.joaocosta.minart.core._

import org.scalajs.dom.raw.Event

/** A low level Canvas implementation that shows the image in an HTML Canvas element.
  */
class HtmlCanvas() extends LowLevelCanvas {
  private[this] var canvas: JsCanvas                  = _
  private[this] var ctx: dom.CanvasRenderingContext2D = _
  private[this] var childNode: dom.Node               = _
  private[this] var keyboardInput: KeyboardInput      = KeyboardInput(Set(), Set(), Set())
  private[this] var pointerInput: PointerInput        = PointerInput(None, Nil, Nil, false)

  private[this] var buffer: ImageData     = _
  private[this] var listenersSet: Boolean = false

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    changeSettings(newSettings)
    dom.document.addEventListener[Event](
      "fullscreenchange",
      (_: Event) => if (dom.document.fullscreenElement == null) changeSettings(settings.copy(fullScreen = false))
    )
    dom.document.addEventListener[KeyboardEvent](
      "keydown",
      (ev: KeyboardEvent) => {
        JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.press(k))
        println("keydown")
      }
    )
    dom.document.addEventListener[KeyboardEvent](
      "keyup",
      (ev: KeyboardEvent) => JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.release(k))
    )

    val canvasRect = canvas.getBoundingClientRect();
    def handlePress() = { pointerInput = pointerInput.press }
    def handleRelease() = { pointerInput = pointerInput.release }
    def handleMove(x: Int, y: Int) = {
      if (x >= 0 && y >= 0 && x < extendedSettings.scaledWidth && y < extendedSettings.scaledHeight) {
        pointerInput = pointerInput.move(Some(PointerInput.Position(x / newSettings.scale, y / newSettings.scale)))
      } else {
        pointerInput = pointerInput.move(None)
      }
    }
    dom.document.addEventListener[MouseEvent]("mousedown", (_: MouseEvent) => handlePress())
    dom.document.addEventListener[MouseEvent]("mouseup", (_: MouseEvent) => handleRelease())
    canvas.addEventListener[MouseEvent](
      "mousemove",
      (ev: MouseEvent) => {
        val x = (ev.clientX - canvasRect.left).toInt
        val y = (ev.clientY - canvasRect.top).toInt
        handleMove(x, y)
      }
    )
    dom.document.addEventListener[TouchEvent](
      "touchstart",
      (ev: TouchEvent) => {
        val touch = ev.changedTouches(0)
        val x     = (touch.clientX - canvasRect.left).toInt
        val y     = (touch.clientY - canvasRect.top).toInt
        handleMove(x, y)
        handlePress()
      }
    )
    dom.document.addEventListener[TouchEvent]("touchend", (_: TouchEvent) => handleRelease())
    dom.document.addEventListener[TouchEvent]("touchcancel", (_: TouchEvent) => handleRelease())
    canvas.addEventListener[TouchEvent](
      "touchmove",
      (ev: TouchEvent) => {
        val touch = ev.changedTouches(0)
        val x     = (touch.clientX - canvasRect.left).toInt
        val y     = (touch.clientY - canvasRect.top).toInt
        handleMove(x, y)
      }
    )
  }
  def unsafeDestroy(): Unit = if (childNode != null) {
    dom.document.body.removeChild(childNode)
    childNode = null
  }
  def changeSettings(newSettings: Canvas.Settings) = if (extendedSettings == null || newSettings != settings) {
    extendedSettings =
      Canvas.ExtendedSettings(newSettings, dom.window.screen.width.toInt, dom.window.screen.height.toInt)
    canvas.width = if (newSettings.fullScreen) extendedSettings.windowWidth else extendedSettings.scaledWidth
    canvas.height = if (newSettings.fullScreen) extendedSettings.windowHeight else extendedSettings.scaledHeight
    childNode = dom.document.body.appendChild(canvas)
    buffer = ctx.getImageData(0, 0, extendedSettings.scaledWidth, extendedSettings.scaledHeight)

    if (newSettings.fullScreen) {
      canvas.requestFullscreen()
    } else if (dom.document.fullscreenElement != null) {
      dom.document.exitFullscreen()
    }
    ctx.fillStyle = s"rgb(${newSettings.clearColor.r},${newSettings.clearColor.g},${newSettings.clearColor.b})"
    ctx.fillRect(0, 0, extendedSettings.windowWidth, extendedSettings.windowHeight)
    clear(Set(Canvas.Resource.Backbuffer))
  }

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit =
    extendedSettings.pixelSize.foreach { dy =>
      val lineBase = (y * settings.scale + dy) * extendedSettings.scaledWidth
      extendedSettings.pixelSize.foreach { dx =>
        val baseAddr = 4 * (lineBase + (x * settings.scale + dx))
        buffer.data(baseAddr + 0) = c.r
        buffer.data(baseAddr + 1) = c.g
        buffer.data(baseAddr + 2) = c.b
      }
    }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit = {
    val lineBase = y * extendedSettings.scaledWidth
    val baseAddr = 4 * (lineBase + x)
    buffer.data(baseAddr + 0) = c.r
    buffer.data(baseAddr + 1) = c.g
    buffer.data(baseAddr + 2) = c.b
  }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    if (settings.scale == 1) putPixelUnscaled(x, y, color)
    else putPixelScaled(x, y, color)
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val baseAddr =
      4 * (y * settings.scale * extendedSettings.scaledWidth + (x * settings.scale))
    Color(buffer.data(baseAddr + 0), buffer.data(baseAddr + 1), buffer.data(baseAddr + 2))
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val imgData = buffer.data
    extendedSettings.lines.map { y =>
      val lineBase = y * settings.scale * extendedSettings.scaledWidth
      extendedSettings.columns.map { x =>
        val baseAddr = 4 * (lineBase + (x * settings.scale))
        Color(imgData(baseAddr), imgData(baseAddr + 1), imgData(baseAddr + 2))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      extendedSettings.allPixels.foreach { i =>
        val base = 4 * i
        buffer.data(base + 0) = settings.clearColor.r
        buffer.data(base + 1) = settings.clearColor.g
        buffer.data(base + 2) = settings.clearColor.b
        buffer.data(base + 3) = 255
      }
    }
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyboardInput = keyboardInput.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Pointer)) {
      pointerInput = pointerInput.clearPressRelease()
    }
  }

  def redraw(): Unit = {
    if (settings.fullScreen)
      ctx.putImageData(buffer, extendedSettings.canvasX, extendedSettings.canvasY)
    else
      ctx.putImageData(buffer, 0, 0)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput
}
