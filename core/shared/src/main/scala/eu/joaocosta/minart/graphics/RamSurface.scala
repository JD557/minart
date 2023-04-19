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

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer(y)(x) = color

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val _x = math.max(x, 0)
    val _y = math.max(y, 0)
    val _w = math.min(w, width - _x)
    val _h = math.min(h, height - _y)
    var yy = 0
    while (yy < _h) {
      var xx   = 0
      val line = dataBuffer(_y + yy)
      while (xx < _w) {
        line(_x + xx) = color
        xx += 1
      }
      yy += 1
    }
  }
}
