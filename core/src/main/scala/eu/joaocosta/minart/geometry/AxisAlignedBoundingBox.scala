package eu.joaocosta.minart.geometry

/** An Axis Aligned Bounding Box.
  *
  * Represents a rectangular region aligned to the (x, y) axis and provides some basic functionality.
  *
  * This API is *experimental* and might change in the near future.
  *
  * @param x x position of the top-left coordinate
  * @param y y position of the top-left coordinate
  * @param width box width in pixels
  * @param height box height in pixels
  */
final case class AxisAlignedBoundingBox(
    x: Int,
    y: Int,
    width: Int,
    height: Int
) {

  /** Leftmost position */
  inline def x1 = x

  /** Topmost position */
  inline def y1 = y

  /** Rightmost position */
  inline def x2 = x + width

  /** Bottommost position */
  inline def y2 = y + height

  /** Horizontal center */
  inline def centerX = x + width / 2

  /** Vertical center */
  inline def centerY = y + height / 2

  /** Returns true if the rectangle has no area
    */
  def isEmpty: Boolean = width <= 0 || height <= 0

  /** Checks if this bounding box contains a point.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return true if the point is inside the bounding box (edges included)
    */
  def contains(x: Int, y: Int): Boolean =
    x >= x1 && x <= x2 && y >= y1 && y <= y2

  /** Checks if this bounding box contains another bounding box.
    *
    * @param that bounding box to test
    * @return true if the bounding box is inside this bounding box (edges included)
    */
  def contains(that: AxisAlignedBoundingBox): Boolean =
    this.contains(that.x1, that.y1) && this.contains(that.x2, that.y2)

  /** Checks if this bounding box collides with another bounding box.
    *
    * @param that bounding box to test
    * @return true if the bounding boxes collide
    */
  def collides(that: AxisAlignedBoundingBox): Boolean =
    Math.abs(2 * (this.x - that.x) + (this.width - that.width)) <= (this.width + that.width) &&
      Math.abs(2 * (this.y - that.y) + (this.height - that.height)) <= (this.height + that.height)

  /** Merges this bounding box with another one.
    *
    * Gaps between the boxes will also be considered as part of the final area.
    */
  def union(that: AxisAlignedBoundingBox): AxisAlignedBoundingBox = {
    val minX = math.min(this.x1, that.x1)
    val maxX = math.max(this.x2, that.x2)
    val minY = math.min(this.y1, that.y1)
    val maxY = math.max(this.y2, that.y2)
    AxisAlignedBoundingBox(x = minX, y = minY, width = maxX - minX, height = maxY - minY)
  }

  /** Intersects this bounding box with another one.
    */
  def intersect(that: AxisAlignedBoundingBox): AxisAlignedBoundingBox = {
    val maxX1 = math.max(this.x1, that.x1)
    val maxY1 = math.max(this.y1, that.y1)
    val minX2 = math.min(this.x2, that.x2)
    val minY2 = math.min(this.y2, that.y2)
    AxisAlignedBoundingBox(x = maxX1, y = maxY1, width = minX2 - maxX1, height = minY2 - maxY1)
  }

  /** Performs a side effect for every pixel in the bounding box.
    *
    * @param f side effect, receiving (x, y) points
    */
  def foreach(f: (Int, Int) => Unit): Unit = {
    var y = y1
    while (y < y2) {
      var x = x1
      while (x < x2) {
        f(x, y)
        x = x + 1
      }
      y = y + 1
    }
  }
}

object AxisAlignedBoundingBox {

  /** Construct an AABB that contains a sequence of points */
  def fromPoints(points: Seq[Point]): AxisAlignedBoundingBox = {
    var x1: Double = Double.MaxValue
    var y1: Double = Double.MaxValue
    var x2: Double = Double.MinValue
    var y2: Double = Double.MinValue

    points.foreach { point =>
      if (point.x < x1) x1 = point.x
      if (point.y < y1) y1 = point.y
      if (point.x > x2) x2 = point.x
      if (point.y > y2) y2 = point.y
    }

    val _x1 = math.floor(x1).toInt
    val _y1 = math.floor(y1).toInt
    val _x2 = math.ceil(x2).toInt
    val _y2 = math.ceil(y2).toInt
    if (_x1 > _x2 || _y1 > _y2) AxisAlignedBoundingBox(0, 0, 0, 0)
    else AxisAlignedBoundingBox(_x1, _y1, _x2 - _x1, _y2 - _y1)
  }

  /** Mutable Builder for AABBs.
    */
  final class Builder() {
    private var x1: Double = Double.MaxValue
    private var y1: Double = Double.MaxValue
    private var x2: Double = Double.MinValue
    private var y2: Double = Double.MinValue

    def add(x: Int, y: Int): this.type = {
      if (x < x1) x1 = x
      if (y < y1) y1 = y
      if (x > x2) x2 = x
      if (y > y2) y2 = y
      this
    }

    def add(x: Double, y: Double): this.type = {
      if (x < x1) x1 = x
      if (y < y1) y1 = y
      if (x > x2) x2 = x
      if (y > y2) y2 = y
      this
    }

    def add(point: Point): this.type = add(point.x, point.y)

    def result(): AxisAlignedBoundingBox = {
      val _x1 = math.floor(x1).toInt
      val _y1 = math.floor(y1).toInt
      val _x2 = math.ceil(x2).toInt
      val _y2 = math.ceil(y2).toInt
      if (_x1 > _x2 || _y1 > _y2) AxisAlignedBoundingBox(0, 0, 0, 0)
      else AxisAlignedBoundingBox(_x1, _y1, _x2 - _x1, _y2 - _y1)
    }
  }
}
