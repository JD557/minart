package eu.joaocosta.minart.backend

import scala.scalajs.js.typedarray._

import org.scalajs.dom.ImageData

import eu.joaocosta.minart.graphics.{Color, MutableSurface}

/** A mutable surface backed by an ImageData.
  *
  *  @param imageData imageData that backs this surface
  */
final class ImageDataSurface(val data: ImageData) extends MutableSurface {

  val width: Int         = data.width
  val height: Int        = data.height
  private val dataBuffer = new Int32Array(data.data.buffer)

  def unsafeGetPixel(x: Int, y: Int): Color = Color.fromABGR(dataBuffer(y * width + x))

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer(y * width + x) = color.abgr

  override def fill(color: Color): Unit = {
    dataBuffer.fill(color.abgr)
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val _x = Math.max(x, 0)
    val _y = Math.max(y, 0)
    val _w = Math.min(w, width - _x)
    val _h = Math.min(h, height - _y)
    var yy = 0
    while (yy < _h) {
      val start = (yy + _y) * width + _x
      dataBuffer.fill(color.abgr, start, start + _w)
      yy += 1
    }
  }
}
