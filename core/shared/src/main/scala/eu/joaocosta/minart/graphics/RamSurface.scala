package eu.joaocosta.minart.graphics

/** A mutable surface stored in RAM.
  *
  * @param data the raw data that backs this surface
  */
final class RamSurface(val dataBuffer: Vector[Array[Color]]) extends MutableSurface {
  val width  = dataBuffer.headOption.map(_.size).getOrElse(0)
  val height = dataBuffer.size

  def this(colors: Seq[Seq[Color]]) =
    this(colors.map(_.toArray).toVector)

  def this(width: Int, height: Int, color: Color) =
    this(Vector.fill(height)(Array.fill(width)(color)))

  def unsafeGetPixel(x: Int, y: Int): Color =
    dataBuffer(y)(x)

  override def getPixels(): Vector[Array[Color]] = dataBuffer.map(_.clone())

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (x >= 0 && y >= 0 && x < width && y < height)
      dataBuffer(y)(x) = color

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    var yy = 0
    while (yy < h) {
      var xx   = 0
      val line = dataBuffer(yy)
      while (xx < w) {
        line(xx) = color
        xx += 1
      }
      yy += 1
    }
  }
}
