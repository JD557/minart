package eu.joaocosta.minart.backend

import scala.scalajs.js

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => JsCanvas}
import org.scalajs.dom.{CanvasRenderingContext2D, Event, ImageBitmap, KeyboardEvent, PointerEvent}

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** A low level Canvas implementation that shows the image in an HTML Canvas element.
  */
class HtmlCanvas() extends SurfaceBackedCanvas {

  // Rendering resources

  private[this] var canvas: JsCanvas                  = _
  private[this] var ctx: dom.CanvasRenderingContext2D = _
  private[this] var childNode: dom.Node               = _
  protected var surface: ImageDataSurface             = _

  // Input resources

  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  private[this] var pointerInput: PointerInput   = PointerInput(None, Nil, Nil, false)
  private[this] var rawPointerPos: (Int, Int)    = _
  private[this] def cleanPointerPos: Option[PointerInput.Position] = Option(rawPointerPos).map { case (x, y) =>
    val (offsetX, offsetY) =
      if (settings.fullScreen) (extendedSettings.canvasX, extendedSettings.canvasY)
      else {
        val canvasRect = canvas.getBoundingClientRect()
        (canvasRect.left.toInt, canvasRect.top.toInt)
      }
    PointerInput.Position((x - offsetX) / settings.scale, (y - offsetY) / settings.scale)
  }

  // Initialization

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
    ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
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

    def handlePress()   = { pointerInput = pointerInput.move(cleanPointerPos).press }
    def handleRelease() = { pointerInput = pointerInput.move(cleanPointerPos).release }
    def handleMove(x: Int, y: Int) = {
      val (offsetX, offsetY) =
        if (settings.fullScreen) (extendedSettings.canvasX, extendedSettings.canvasY)
        else (0, 0)
      if (
        x >= offsetX && y >= offsetY &&
        x < extendedSettings.scaledWidth + offsetX && y < extendedSettings.scaledHeight + offsetY
      ) {
        rawPointerPos = (x, y)
      } else {
        rawPointerPos = null
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

  def changeSettings(newSettings: Canvas.Settings) = if (extendedSettings == null || newSettings != settings) {
    extendedSettings =
      LowLevelCanvas.ExtendedSettings(newSettings, dom.window.screen.width.toInt, dom.window.screen.height.toInt)
    canvas.width = if (newSettings.fullScreen) extendedSettings.windowWidth else newSettings.width
    canvas.height = if (newSettings.fullScreen) extendedSettings.windowHeight else newSettings.height
    canvas.style =
      s"width:${extendedSettings.scaledWidth}px;height:${extendedSettings.scaledHeight}px;image-rendering:pixelated;"
    ctx.imageSmoothingEnabled = false
    childNode = dom.document.body.appendChild(canvas)
    surface = new ImageDataSurface(ctx.getImageData(0, 0, newSettings.width, newSettings.height))

    if (newSettings.fullScreen) {
      canvas.requestFullscreen()
    } else if (dom.document.fullscreenElement != null && !js.isUndefined(dom.document.fullscreenElement)) {
      dom.document.exitFullscreen()
    }
    ctx.fillStyle = s"rgb(${newSettings.clearColor.r},${newSettings.clearColor.g},${newSettings.clearColor.b})"
    ctx.fillRect(0, 0, extendedSettings.windowWidth, extendedSettings.windowHeight)
    clear(Set(Canvas.Buffer.Backbuffer))
  }

  // Cleanup

  def unsafeDestroy(): Unit = if (childNode != null) {
    dom.document.body.removeChild(childNode)
    childNode = null
  }

  // Canvas operations

  def clear(buffers: Set[Canvas.Buffer]): Unit = {
    if (buffers.contains(Canvas.Buffer.KeyboardBuffer)) {
      keyboardInput = keyboardInput.clearPressRelease()
    }
    if (buffers.contains(Canvas.Buffer.PointerBuffer)) {
      pointerInput = pointerInput.clearPressRelease()
    }
    if (buffers.contains(Canvas.Buffer.Backbuffer)) {
      surface.fill(settings.clearColor)
    }
  }

  def redraw(): Unit = {
    if (!settings.fullScreen) {
      ctx.putImageData(surface.data, 0, 0)
    } else if (settings.scale == 1) {
      ctx.putImageData(surface.data, extendedSettings.canvasX, extendedSettings.canvasY)
    } else {
      dom.window
        .createImageBitmap(surface.data)
        .`then`[Unit] { (bitmap: ImageBitmap) =>
          ctx
            .asInstanceOf[js.Dynamic]
            .drawImage(
              bitmap,
              extendedSettings.canvasX,
              extendedSettings.canvasY,
              extendedSettings.scaledWidth,
              extendedSettings.scaledHeight
            )
          js.|.from[Unit, Unit, js.Thenable[Unit]](())
        }
    }
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput.move(cleanPointerPos)
}
