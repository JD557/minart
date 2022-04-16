package eu.joaocosta.minart.backend

import org.scalajs.dom.ImageData

import eu.joaocosta.minart.graphics.{Color, MutableSurface}

/** A mutable surface backed by an ImageData.
  *
  *  @param imageData imageData that backs this surface
  */
final class ImageDataSurface(val data: ImageData) extends MutableSurface {

  val width: Int      = data.width
  val height: Int     = data.height
  private val lines   = 0 until height
  private val columns = 0 until width

  def unsafeGetPixel(x: Int, y: Int): Color = {
    val baseAddr = 4 * (y * width + x)
    Color(data.data(baseAddr + 0), data.data(baseAddr + 1), data.data(baseAddr + 2))
  }

  def getPixels(): Vector[Array[Color]] = {
    val imgData = data.data
    lines.map { y =>
      val lineBase = y * width
      columns.map { x =>
        val baseAddr = 4 * (lineBase + x)
        Color(imgData(baseAddr), imgData(baseAddr + 1), imgData(baseAddr + 2))
      }.toArray
    }.toVector
  }

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x >= 0 && y >= 0 && x < width && y < height) {
      val lineBase = y * width
      val baseAddr = 4 * (lineBase + x)
      data.data(baseAddr + 0) = color.r
      data.data(baseAddr + 1) = color.g
      data.data(baseAddr + 2) = color.b
    }

  def fill(color: Color): Unit = {
    var base = 0
    while (base < 4 * height * width) {
      data.data(base + 0) = color.r
      data.data(base + 1) = color.g
      data.data(base + 2) = color.b
      data.data(base + 3) = 255
      base += 4
    }
  }
}
