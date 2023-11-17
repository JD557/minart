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
    val x1 = Math.max(x, 0)
    val y1 = Math.max(y, 0)
    val x2 = Math.min(x + w, width)
    val y2 = Math.min(y + h, height)
    if (x1 != x2 && y1 != y2) {
      var _y = y1
      while (_y < y2) {
        var _x   = x1
        val line = dataBuffer(_y)
        while (_x < x2) {
          line(_x) = color
          _x += 1
        }
        _y += 1
      }
    }
  }
}
