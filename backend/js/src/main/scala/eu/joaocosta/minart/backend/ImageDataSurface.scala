package eu.joaocosta.minart.backend

import scala.concurrent.Future
import scala.scalajs.js.typedarray.*

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, Image, ImageBitmap, ImageData, OffscreenCanvas, TwoDContextAttributes, window}

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
    val x1 = Math.max(x, 0)
    val y1 = Math.max(y, 0)
    val x2 = Math.min(x + w, width)
    val y2 = Math.min(y + h, height)
    if (x1 != x2 && y1 != y2) {
      val _w = x2 - x1
      var _y = y1
      while (_y < y2) {
        val start = _y * width + x1
        dataBuffer.fill(color.abgr, start, start + _w)
        _y += 1
      }
    }
  }

  /** Converts this surface to an ImageBitmap
    */
  def asImageBitmap(): Future[ImageBitmap] =
    window.createImageBitmap(data).toFuture
}

object ImageDataSurface {

  /** Loads an ImageDataOpaqueSurface from an offscreen canvas
    *
    * @param canvas offscreen canvas
    * @return loaded surface
    */
  def fromOffscreenCanvas(canvas: OffscreenCanvas): ImageDataSurface = {
    val ctx = canvas
      .getContext("2d", new TwoDContextAttributes { alpha = true })
      .asInstanceOf[CanvasRenderingContext2D]
    new ImageDataSurface(ctx.getImageData(0, 0, canvas.width, canvas.height))
  }

  /** Loads an ImageDataSurface from an image element
    *
    * @param image image element
    * @return loaded surface
    */
  def fromImage(image: Image): ImageDataSurface = {
    val offscreenCanvas = new OffscreenCanvas(image.width, image.height)
    val ctx = offscreenCanvas
      .getContext("2d", new TwoDContextAttributes { alpha = true })
      .asInstanceOf[CanvasRenderingContext2D]
    ctx.drawImage(image, 0, 0)
    fromOffscreenCanvas(offscreenCanvas)
  }
}
