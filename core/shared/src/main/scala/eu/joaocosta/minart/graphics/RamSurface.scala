package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.graphics.Surface._

/** A mutable surface stored in RAM.
  *
  * @param data the raw data that backs this surface
  */
class RamSurface(val data: Vector[Array[Int]]) extends MutableSurface {
  val width  = data.headOption.map(_.size).getOrElse(0)
  val height = data.size

  def this(colors: Seq[Seq[Color]]) =
    this(colors.map(_.map(_.argb).toArray).toVector)

  def getPixel(x: Int, y: Int): Option[Color] = {
    if (x < 0 || y < 0 || x >= width || y >= height) None
    else Some(Color.fromRGB(data(y)(x)))
  }

  def getPixels(): Vector[Array[Color]] =
    data.map(_.map(Color.fromRGB))

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x < 0 || y < 0 || x >= width || y >= height)
      data(y)(x) = color.argb

  def fill(color: Color): Unit = {
    var y = 0
    while (y < height) {
      var x = 0
      while (x < width) {
        data(y)(x) = color.argb
        x += 1
      }
      y += 1
    }
  }

}
