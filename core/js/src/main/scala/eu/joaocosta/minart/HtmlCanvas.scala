package eu.joaocosta.minart

import org.scalajs.dom
import org.scalajs.dom.html.{ Canvas => JsCanvas }
import org.scalajs.dom.raw.KeyboardEvent

class HtmlCanvas(
  val width: Int,
  val height: Int,
  val scale: Int = 1,
  val clearColor: Color = Color(255, 255, 255)) extends LowLevelCanvas {
  private[this] val canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private[this] var childNode: dom.Node = _
  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  canvas.width = scaledWidth
  canvas.height = scaledHeight

  def unsafeInit(): Unit = {
    childNode = dom.document.body.appendChild(canvas)
    dom.document.addEventListener[KeyboardEvent]("keydown", (ev: KeyboardEvent) =>
      HtmlCanvas.convertKeyCode(ev.keyCode).foreach(k => keyboardInput = keyboardInput.press(k)))
    dom.document.addEventListener[KeyboardEvent]("keyup", (ev: KeyboardEvent) =>
      HtmlCanvas.convertKeyCode(ev.keyCode).foreach(k => keyboardInput = keyboardInput.release(k)))
  }
  def unsafeDestroy(): Unit = {
    dom.document.body.removeChild(childNode)
    childNode = null
  }

  private[this] val canvasBuff = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctxBuff = canvasBuff.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  canvasBuff.width = scaledWidth
  canvasBuff.height = scaledHeight

  private[this] val clearColorStr = s"rgb(${clearColor.r}, ${clearColor.g}, ${clearColor.b})"

  def putPixel(x: Int, y: Int, color: Color): Unit = {
    ctxBuff.fillStyle = s"rgb(${color.r}, ${color.g}, ${color.b})"
    ctxBuff.fillRect(x * scale, y * scale, scale, scale)
  }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val imgData = ctxBuff.getImageData(x * scale, y * scale, 1, 1).data
    Color(imgData(0), imgData(1), imgData(2))
  }

  def clear(): Unit = {
    ctxBuff.fillStyle = clearColorStr
    ctxBuff.fillRect(0, 0, scaledWidth, scaledHeight)
  }

  def redraw(): Unit = {
    ctx.drawImage(canvasBuff, 0, 0)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
}

object HtmlCanvas {
  private def convertKeyCode(code: Int): Option[KeyboardInput.Key] = code match {
    case 38 => Some(KeyboardInput.Key.Up)
    case 40 => Some(KeyboardInput.Key.Down)
    case 37 => Some(KeyboardInput.Key.Left)
    case 39 => Some(KeyboardInput.Key.Right)
    case _ => None
  }
}
