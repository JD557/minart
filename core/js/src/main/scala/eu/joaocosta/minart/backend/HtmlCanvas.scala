package eu.joaocosta.minart.backend

import org.scalajs.dom
import org.scalajs.dom.html.{ Canvas => JsCanvas }
import org.scalajs.dom.raw.KeyboardEvent

import eu.joaocosta.minart.core._

/**
 * A low level Canvas implementation that shows the image in an HTML Canvas element.
 */
class HtmlCanvas(val settings: Canvas.Settings) extends LowLevelCanvas {
  private[this] val canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private[this] var childNode: dom.Node = _
  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  canvas.width = settings.scaledWidth
  canvas.height = settings.scaledHeight

  def unsafeInit(): Unit = {
    childNode = dom.document.body.appendChild(canvas)
    dom.document.addEventListener[KeyboardEvent]("keydown", (ev: KeyboardEvent) =>
      JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.press(k)))
    dom.document.addEventListener[KeyboardEvent]("keyup", (ev: KeyboardEvent) =>
      JsKeyMapping.getKey(ev.keyCode).foreach(k => keyboardInput = keyboardInput.release(k)))
  }
  def unsafeDestroy(): Unit = {
    dom.document.body.removeChild(childNode)
    childNode = null
  }

  private[this] val canvasBuff = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctxBuff = canvasBuff.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  canvasBuff.width = settings.scaledWidth
  canvasBuff.height = settings.scaledHeight

  private[this] val clearColorStr = s"rgb(${settings.clearColor.r}, ${settings.clearColor.g}, ${settings.clearColor.b})"

  def putPixel(x: Int, y: Int, color: Color): Unit = {
    ctxBuff.fillStyle = s"rgb(${color.r}, ${color.g}, ${color.b})"
    ctxBuff.fillRect(x * settings.scale, y * settings.scale, settings.scale, settings.scale)
  }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val imgData = ctxBuff.getImageData(x * settings.scale, y * settings.scale, 1, 1).data
    Color(imgData(0), imgData(1), imgData(2))
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    val imgData = ctxBuff.getImageData(0, 0, settings.scaledWidth, settings.scaledHeight).data
    (0 until settings.height).map { y =>
      val lineBase = y * settings.scale * settings.scaledWidth
      (0 until settings.width).map { x =>
        val baseAddr = 4 * (lineBase + (x * settings.scale))
        Color(imgData(baseAddr), imgData(baseAddr + 1), imgData(baseAddr + 2))
      }.toVector
    }.toVector
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      ctxBuff.fillStyle = clearColorStr
      ctxBuff.fillRect(0, 0, settings.scaledWidth, settings.scaledHeight)
    }
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyboardInput = keyboardInput.clearPressRelease
    }
  }

  def redraw(): Unit = {
    ctx.drawImage(canvasBuff, 0, 0)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
}
