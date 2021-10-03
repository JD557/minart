package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.graphics.Surface._

/** A mutable surface stored in RAM.
  *
  *  @data the raw data that backs this surface
  */
class RamSurface(val data: Vector[Array[Int]]) extends MutableSurface {
  val width  = data.headOption.map(_.size).getOrElse(0)
  val height = data.size

  def this(colors: Seq[Seq[Color]]) =
    this(colors.map(_.map(_.argb).toArray).toVector)

  private[this] val lines   = (0 until height)
  private[this] val columns = (0 until width)

  def getPixel(x: Int, y: Int): Option[Color] = {
    if (x < 0 || y < 0 || x >= width || y >= height) None
    else Some(Color.fromRGB(data(y)(x)))
  }

  def getPixels(): Vector[Array[Color]] =
    data.map(_.map(Color.fromRGB))

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x < 0 || y < 0 || x >= width || y >= height)
      data(y)(x) = color.argb

  def fill(color: Color): Unit =
    for {
      y <- lines
      x <- columns
    } data(y)(x) = color.argb

}
