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
  private val dataBuffer = new Int32Array(data.data.asInstanceOf[Uint8ClampedArray].buffer)

  def unsafeGetPixel(x: Int, y: Int): Color = Color.fromBGR(dataBuffer(y * width + x))

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer(y * width + x) = color.abgr

  override def fill(color: Color): Unit = {
    dataBuffer.fill(color.abgr)
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    var yy = 0
    while (yy < h) {
      val start = (yy + y) * width + x
      dataBuffer.fill(color.abgr, start, start + w)
      yy += 1
    }
  }
}
