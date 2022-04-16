package eu.joaocosta.minart.graphics

/** A mutable surface stored in RAM.
  *
  * @param data the raw data that backs this surface
  */
final class RamSurface(val data: Vector[Array[Color]]) extends MutableSurface {
  val width  = data.headOption.map(_.size).getOrElse(0)
  val height = data.size

  def this(colors: Seq[Seq[Color]]) =
    this(colors.map(_.toArray).toVector)

  def unsafeGetPixel(x: Int, y: Int): Color =
    data(y)(x)

  def getPixels(): Vector[Array[Color]] = data.map(_.clone())

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x >= 0 && y >= 0 && x < width && y < height)
      data(y)(x) = color

  def fill(color: Color): Unit = {
    var y = 0
    while (y < height) {
      var x = 0
      while (x < width) {
        data(y)(x) = color
        x += 1
      }
      y += 1
    }
  }

}
