package eu.joaocosta.minart.backend

import org.scalajs.dom
import org.scalajs.dom.raw.ImageData

import eu.joaocosta.minart.graphics.{Color, Surface}

final class ImageDataSurface(val data: ImageData) extends Surface.MutableSurface {

  val width: Int        = data.width
  val height: Int       = data.height
  private val lines     = 0 until height
  private val columns   = 0 until width
  private val allPixels = 0 until height * width

  def getPixel(x: Int, y: Int): Option[Color] = {
    val baseAddr =
      4 * (y * width + x)
    try {
      Some(Color(data.data(baseAddr + 0), data.data(baseAddr + 1), data.data(baseAddr + 2)))
    } catch { case _: Throwable => None }
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

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    val lineBase = y * width
    val baseAddr = 4 * (lineBase + x)
    data.data(baseAddr + 0) = color.r
    data.data(baseAddr + 1) = color.g
    data.data(baseAddr + 2) = color.b
  } catch { case _: Throwable => () }

  def fill(color: Color): Unit =
    allPixels.foreach { i =>
      val base = 4 * i
      data.data(base + 0) = color.r
      data.data(base + 1) = color.g
      data.data(base + 2) = color.b
      data.data(base + 3) = 255
    }
}
