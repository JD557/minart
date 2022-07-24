package eu.joaocosta.minart.backend

import scala.scalajs.js

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas => JsCanvas}
import org.scalajs.dom.{Event, KeyboardEvent, PointerEvent}

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** A low level Canvas implementation that shows the image in an HTML Canvas element.
  */
class HtmlCanvas() extends SurfaceBackedCanvas {

  // Rendering resources

  private[this] var containerDiv: dom.HTMLDivElement  = _
  private[this] var canvas: JsCanvas                  = _
  private[this] var ctx: dom.CanvasRenderingContext2D = _
  private[this] var childNode: dom.Node               = _
  protected var surface: ImageDataSurface             = _

  // Input resources

  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  private[this] var pointerInput: PointerInput   = PointerInput(None, Nil, Nil, false)
  private[this] var rawPointerPos: (Int, Int)    = _
  private[this] def cleanPointerPos: Option[PointerInput.Position] = Option(rawPointerPos).flatMap { case (x, y) =>
    val (offsetX, offsetY) = {
      val canvasRect = canvas.getBoundingClientRect()
      (canvasRect.left.toInt, canvasRect.top.toInt)
    }
    val xx = (x - offsetX) / settings.scale
    val yy = (y - offsetY) / settings.scale
    if (xx >= 0 && yy >= 0 && xx < settings.width && yy < settings.height)
      Some(PointerInput.Position(xx, yy))
    else None
  }

  // Initialization

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    containerDiv = dom.document.createElement("div").asInstanceOf[dom.HTMLDivElement]
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
      }
    )
    dom.document.addEventListener[KeyboardEvent](
      "keyup",
      (ev: KeyboardEvent) => JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.release(k))
    )

    def handlePress()   = { pointerInput = pointerInput.move(cleanPointerPos).press }
    def handleRelease() = { pointerInput = pointerInput.move(cleanPointerPos).release }
    def handleMove(x: Int, y: Int) = {
      rawPointerPos = (x, y)
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
    val clearColorStr = s"rgb(${newSettings.clearColor.r},${newSettings.clearColor.g},${newSettings.clearColor.b})"
    extendedSettings =
      LowLevelCanvas.ExtendedSettings(newSettings, dom.window.screen.width.toInt, dom.window.screen.height.toInt)
    canvas.width = newSettings.width
    canvas.height = newSettings.height
    canvas.style =
      s"width:${extendedSettings.scaledWidth}px;height:${extendedSettings.scaledHeight}px;image-rendering:pixelated;"
    ctx.imageSmoothingEnabled = false

    containerDiv.style =
      if (newSettings.fullScreen)
        s"display:flex;justify-content:center;align-items:center;background:$clearColorStr;"
      else ""
    containerDiv.appendChild(canvas)
    childNode = dom.document.body.appendChild(containerDiv)
    surface = new ImageDataSurface(ctx.getImageData(0, 0, newSettings.width, newSettings.height))

    if (newSettings.fullScreen) {
      containerDiv.requestFullscreen()
    } else if (dom.document.fullscreenElement != null && !js.isUndefined(dom.document.fullscreenElement)) {
      dom.document.exitFullscreen()
    }
    ctx.fillStyle = clearColorStr
    ctx.fillRect(0, 0, newSettings.width, newSettings.height)
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
    ctx.putImageData(surface.data, 0, 0)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput.move(cleanPointerPos)
}
