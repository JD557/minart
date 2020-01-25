package eu.joaocosta.minart

import org.scalajs.dom
import org.scalajs.dom.html.{ Canvas => JsCanvas }

case class HtmlCanvas(
  width: Int,
  height: Int,
  scale: Int = 1,
  clearColor: Color = Color(255, 255, 255)) extends Canvas {
  private[this] val canvas = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  canvas.width = scaledWidth
  canvas.height = scaledHeight
  dom.document.body.appendChild(canvas)

  private[this] val canvasBuff = dom.document.createElement("canvas").asInstanceOf[JsCanvas]
  private[this] val ctxBuff = canvasBuff.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  canvasBuff.width = scaledWidth
  canvasBuff.height = scaledHeight

  def putPixel(x: Int, y: Int, color: Color): Unit = {
    ctxBuff.fillStyle = s"rgb(${color.r}, ${color.g}, ${color.b})"
    ctxBuff.fillRect(x * scale, y * scale, scale, scale)
  }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val imgData = ctxBuff.getImageData(x * scale, y * scale, 1, 1).data
    Color(imgData(0), imgData(1), imgData(2))
  }

  def clear(): Unit = {
    ctxBuff.fillStyle = s"rgb(${clearColor.r}, ${clearColor.g}, ${clearColor.b})"
    ctxBuff.fillRect(0, 0, scaledWidth, scaledHeight)
  }

  def redraw(): Unit = {
    ctx.drawImage(canvasBuff, 0, 0)
  }
}
