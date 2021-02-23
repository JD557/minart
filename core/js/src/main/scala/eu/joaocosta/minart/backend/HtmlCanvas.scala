package eu.joaocosta.minart.backend

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => JsCanvas}
import org.scalajs.dom.raw.{ImageData, MouseEvent, KeyboardEvent, TouchEvent}

import eu.joaocosta.minart.core._

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
      if (x >= 0 && y >= 0 && x < currentSettings.scaledWidth && y < currentSettings.scaledHeight) {
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
    clear(Set(Canvas.Resource.Backbuffer)) // Sets the clear color and the alpha to 255
  }
  def unsafeDestroy(): Unit = if (childNode != null) {
    dom.document.body.removeChild(childNode)
    childNode = null
  }
  def changeSettings(newSettings: Canvas.Settings) = if (
    currentSettings == null || newSettings != currentSettings.settings
  ) {
    val extendedSettings = Canvas.ExtendedSettings(newSettings)
    canvas.width = extendedSettings.scaledWidth
    canvas.height = extendedSettings.scaledHeight
    childNode = dom.document.body.appendChild(canvas)
    buffer = ctx.getImageData(0, 0, extendedSettings.scaledWidth, extendedSettings.scaledHeight)

    if (newSettings.fullScreen) {
      canvas.requestFullscreen()
    } else {
      dom.document.exitFullscreen()
    }
    currentSettings = extendedSettings
  }

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit =
    currentSettings.pixelSize.foreach { dy =>
      val lineBase = (y * currentSettings.settings.scale + dy) * currentSettings.scaledWidth
      currentSettings.pixelSize.foreach { dx =>
        val baseAddr = 4 * (lineBase + (x * currentSettings.settings.scale + dx))
        buffer.data(baseAddr + 0) = c.r
        buffer.data(baseAddr + 1) = c.g
        buffer.data(baseAddr + 2) = c.b
      }
    }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit = {
    val lineBase = y * currentSettings.scaledWidth
    val baseAddr = 4 * (lineBase + x)
    buffer.data(baseAddr + 0) = c.r
    buffer.data(baseAddr + 1) = c.g
    buffer.data(baseAddr + 2) = c.b
  }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    if (currentSettings.settings.scale == 1) putPixelUnscaled(x, y, color)
    else putPixelScaled(x, y, color)
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val baseAddr =
      4 * (y * currentSettings.settings.scale * currentSettings.scaledWidth + (x * currentSettings.settings.scale))
    Color(buffer.data(baseAddr + 0), buffer.data(baseAddr + 1), buffer.data(baseAddr + 2))
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val imgData = buffer.data
    currentSettings.lines.map { y =>
      val lineBase = y * currentSettings.settings.scale * currentSettings.scaledWidth
      currentSettings.columns.map { x =>
        val baseAddr = 4 * (lineBase + (x * currentSettings.settings.scale))
        Color(imgData(baseAddr), imgData(baseAddr + 1), imgData(baseAddr + 2))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      currentSettings.allPixels.foreach { i =>
        val base = 4 * i
        buffer.data(base + 0) = currentSettings.settings.clearColor.r
        buffer.data(base + 1) = currentSettings.settings.clearColor.g
        buffer.data(base + 2) = currentSettings.settings.clearColor.b
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
    ctx.putImageData(buffer, 0, 0)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput
}
