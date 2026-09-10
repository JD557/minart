package eu.joaocosta.minart.graphics

/** A mutable surface stored in RAM.
  *
  * @param data the raw data that backs this surface
  */
final class RamSurface(val dataBuffer: Array[Color], val height: Int) extends MutableSurface {
  val width = if (height <= 0) 0 else dataBuffer.size / height

  def this(colors: Seq[Seq[Color]]) =
    this(colors.iterator.flatten.toArray, colors.size)

  def this(width: Int, height: Int, color: Color) =
    this(Array.fill(width * height)(color), height)

  def unsafeGetPixel(x: Int, y: Int): Color =
    dataBuffer(y * width + x)

  override def getPixels(): Vector[Array[Color]] = {
    val b = Vector.newBuilder[Array[Color]]
    b.sizeHint(height)
    var y = 0
    while (y < height) {
      if (width <= 0) {
        b += Array.empty[Color]
      } else {
        val base = y * width
        b += dataBuffer.slice(base, base + width)
      }
      y += 1
    }
    b.result()
  }

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit =
    dataBuffer(y * width + x) = color

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val x1 = Math.max(x, 0)
    val y1 = Math.max(y, 0)
    val x2 = Math.min(x + w, width)
    val y2 = Math.min(y + h, height)
    if (x1 != x2 && y1 != y2) {
      var _y = y1
      while (_y < y2) {
        var _x = x1
        while (_x < x2) {
          dataBuffer(_y * width + _x) = color
          _x += 1
        }
        _y += 1
      }
    }
  }

  override def fill(color: Color): Unit = {
    var i = 0
    while (i < dataBuffer.length) {
      dataBuffer(i) = color
      i = i + 1
    }
  }
}

object RamSurface {

  /** Copies a surface into a RAM Surface surface.
    *
    * This is just an alias to Suface#toRamSurface.
    *
    * @param surface surface to copy from
    */
  def copyFrom(surface: Surface): RamSurface =
    surface.toRamSurface()

  /** Produces a RAM surface containing values of a given function
    * over ranges of integer values starting from 0.
    *
    * @param width the surface width
    * @param height the surface height
    * @param f the function computing the element values
    */
  def tabulate(width: Int, height: Int)(f: (Int, Int) => Color): RamSurface = {
    val b = Array.newBuilder[Color]
    b.sizeHint(height)
    var y = 0
    while (y < height) {
      var x = 0
      while (x < width) {
        b += f(x, y)
        x += 1
      }
      y += 1
    }
    new RamSurface(b.result(), height)
  }
}
