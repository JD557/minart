package eu.joaocosta.minart.backend

import scala.scalajs.js

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => JsCanvas}
import org.scalajs.dom.raw.{Event, ImageBitmap, ImageData, KeyboardEvent, PointerEvent}

import eu.joaocosta.minart.core._

/** A low level Canvas implementation that shows the image in an HTML Canvas element.
  */
class HtmlCanvas() extends LowLevelCanvas {

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  private[this] var canvas: JsCanvas                  = _
  private[this] var ctx: dom.CanvasRenderingContext2D = _
  private[this] var childNode: dom.Node               = _
  private[this] var keyboardInput: KeyboardInput      = KeyboardInput(Set(), Set(), Set())
  private[this] var rawMousePos: (Int, Int)           = _
  private[this] def cleanMousePos: Option[PointerInput.Position] = Option(rawMousePos).map { case (x, y) =>
    val (offsetX, offsetY) =
      if (settings.fullScreen) (extendedSettings.canvasX, extendedSettings.canvasY)
      else {
        val canvasRect = canvas.getBoundingClientRect()
        (canvasRect.left.toInt, canvasRect.top.toInt)
      }
    PointerInput.Position((x - offsetX) / settings.scale, (y - offsetY) / settings.scale)
  }
  private[this] var pointerInput: PointerInput = PointerInput(None, Nil, Nil, false)

  private[this] var buffer: ImageData = _

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.imageSmoothingEnabled = false
    changeSettings(newSettings)
    dom.document.addEventListener[Event](
      "fullscreenchange",
      (_: Event) => if (dom.document.fullscreenElement == null) changeSettings(settings.copy(fullScreen = false))
    )
    dom.document.addEventListener[KeyboardEvent](
      "keydown",
      (ev: KeyboardEvent) => {
        JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.press(k))
      }
    )
    dom.document.addEventListener[KeyboardEvent](
      "keyup",
      (ev: KeyboardEvent) => JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.release(k))
    )

    def handlePress() = { pointerInput = pointerInput.move(cleanMousePos).press }
    def handleRelease() = { pointerInput = pointerInput.move(cleanMousePos).release }
    def handleMove(x: Int, y: Int) = {
      val (offsetX, offsetY) =
        if (settings.fullScreen) (extendedSettings.canvasX, extendedSettings.canvasY)
        else (0, 0)
      if (
        x >= offsetX && y >= offsetY &&
        x < extendedSettings.scaledWidth + offsetX && y < extendedSettings.scaledHeight + offsetY
      ) {
        rawMousePos = (x, y)
      } else {
        rawMousePos = null
      }
    }
    dom.document.addEventListener[PointerEvent](
      "pointerdown",
      (ev: PointerEvent) => {
        handleMove(ev.clientX.toInt, ev.clientY.toInt)
        handlePress()
      }
    )
    dom.document.addEventListener[PointerEvent](
      "pointerup",
      (ev: PointerEvent) => {
        handleMove(ev.clientX.toInt, ev.clientY.toInt)
        handleRelease()
      }
    )
    dom.document.addEventListener[PointerEvent]("pointercancel", (_: PointerEvent) => handleRelease())
    canvas.addEventListener[PointerEvent](
      "pointermove",
      (ev: PointerEvent) => {
        handleMove(ev.clientX.toInt, ev.clientY.toInt)
      }
    )
  }
  def unsafeDestroy(): Unit = if (childNode != null) {
    dom.document.body.removeChild(childNode)
    childNode = null
  }
  def changeSettings(newSettings: Canvas.Settings) = if (extendedSettings == null || newSettings != settings) {
    extendedSettings =
      LowLevelCanvas.ExtendedSettings(newSettings, dom.window.screen.width.toInt, dom.window.screen.height.toInt)
    canvas.width = if (newSettings.fullScreen) extendedSettings.windowWidth else extendedSettings.scaledWidth
    canvas.height = if (newSettings.fullScreen) extendedSettings.windowHeight else extendedSettings.scaledHeight
    childNode = dom.document.body.appendChild(canvas)
    buffer = ctx.getImageData(0, 0, newSettings.width, newSettings.height)

    if (newSettings.fullScreen) {
      canvas.requestFullscreen()
    } else if (dom.document.fullscreenElement != null && !js.isUndefined(dom.document.fullscreenElement)) {
      dom.document.exitFullscreen()
    }
    ctx.fillStyle = s"rgb(${newSettings.clearColor.r},${newSettings.clearColor.g},${newSettings.clearColor.b})"
    ctx.fillRect(0, 0, extendedSettings.windowWidth, extendedSettings.windowHeight)
    clear(Set(Canvas.Resource.Backbuffer))
  }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    val lineBase = y * extendedSettings.settings.width
    val baseAddr = 4 * (lineBase + x)
    buffer.data(baseAddr + 0) = color.r
    buffer.data(baseAddr + 1) = color.g
    buffer.data(baseAddr + 2) = color.b
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val baseAddr =
      4 * (y * extendedSettings.settings.width + x)
    Color(buffer.data(baseAddr + 0), buffer.data(baseAddr + 1), buffer.data(baseAddr + 2))
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val imgData = buffer.data
    extendedSettings.lines.map { y =>
      val lineBase = y * extendedSettings.settings.width
      extendedSettings.columns.map { x =>
        val baseAddr = 4 * (lineBase + x)
        Color(imgData(baseAddr), imgData(baseAddr + 1), imgData(baseAddr + 2))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyboardInput = keyboardInput.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Pointer)) {
      pointerInput = pointerInput.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      (0 until extendedSettings.windowHeight * extendedSettings.windowWidth).foreach { i =>
        val base = 4 * i
        buffer.data(base + 0) = settings.clearColor.r
        buffer.data(base + 1) = settings.clearColor.g
        buffer.data(base + 2) = settings.clearColor.b
        buffer.data(base + 3) = 255
      }
    }
  }

  def redraw(): Unit = {
    if (settings.scale == 1)
      if (settings.fullScreen)
        ctx.putImageData(buffer, extendedSettings.canvasX, extendedSettings.canvasY)
      else
        ctx.putImageData(buffer, 0, 0)
    else {
      dom.window
        .createImageBitmap(buffer)
        .`then`[js.Dynamic] { (bitmap: ImageBitmap) =>
          if (settings.fullScreen)
            ctx
              .asInstanceOf[js.Dynamic]
              .drawImage(
                bitmap,
                extendedSettings.canvasX,
                extendedSettings.canvasY,
                extendedSettings.scaledWidth,
                extendedSettings.scaledHeight
              )
          else
            ctx
              .asInstanceOf[js.Dynamic]
              .drawImage(bitmap, 0, 0, extendedSettings.scaledWidth, extendedSettings.scaledHeight)
        }
    }
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput.move(cleanMousePos)
}
