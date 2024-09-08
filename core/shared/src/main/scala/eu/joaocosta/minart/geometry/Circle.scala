package eu.joaocosta.minart.geometry

/** Circle shape.
  *
  * It's considered to be facing the viewer if the radius is positive.
  *
  * This API is *experimental* and might change in the near future.
  *
  * @param center center of the circle.
  * @param radius circle radius
  */
final case class Circle(center: Shape.Point, radius: Double) extends Shape {

  /** The absolute radius */
  val absRadius = math.abs(radius)

  /** The squared radius */
  val squareRadius = radius * radius

  val aabb: AxisAlignedBoundingBox = {
    val x = center.x - absRadius
    val y = center.y - absRadius
    val d = absRadius * 2
    AxisAlignedBoundingBox(math.floor(x).toInt, math.floor(y).toInt, math.ceil(d).toInt, math.ceil(d).toInt)
  }

  val knownFace: Option[Shape.Face] =
    if (radius > 0) Shape.someFront
    else if (radius < 0) Shape.someBack
    else None

  def faceAt(x: Int, y: Int): Option[Shape.Face] = {
    if ((x - center.x) * (x - center.x) + (y - center.y) * (y - center.y) <= squareRadius)
      knownFace
    else None
  }

  /** Checks if this circle contains a circle.
    *
    * @param that circle to check
    * @return true if that circle is contained in this circle
    */
  def contains(that: Circle): Boolean =
    if (this.absRadius >= that.absRadius) {
      val innerRadius = this.absRadius - that.absRadius
      (this.center.x - that.center.x) * (this.center.x - that.center.x) +
        (this.center.y - that.center.y) * (this.center.y - that.center.y) <= innerRadius * innerRadius
    } else false

  /** Checks if this circle collides with another circle.
    *
    * @param that circle to check
    * @return true if both circles collide
    */
  def collides(that: Circle): Boolean =
    val sumRadius = this.absRadius + that.absRadius
    (this.center.x - that.center.x) * (this.center.x - that.center.x) +
      (this.center.y - that.center.y) * (this.center.y - that.center.y) <= sumRadius * sumRadius

  override def translate(dx: Double, dy: Double): Shape =
    if (dx == 0 && dy == 0) this
    else Circle(Shape.Point(center.x + dx, center.y + dy), radius)

  override def flipH: Circle =
    Circle(Shape.Point(-center.x, center.y), -radius)

  override def flipV: Circle =
    Circle(Shape.Point(center.x, -center.y), -radius)

  override def scale(s: Double): Shape =
    if (s == 1.0) this
    else Circle(Shape.Point(center.x * s, center.y * s), radius * s)

  override def rotate(theta: Double): Shape = {
    val matrix = Matrix.rotation(theta)
    if (matrix == Matrix.identity) this
    else {
      Circle(
        Shape.Point(
          matrix.applyX(center.x.toDouble, center.y.toDouble),
          matrix.applyY(center.x.toDouble, center.y.toDouble)
        ),
        radius
      )
    }
  }

  override def transpose: Circle =
    Circle(center = Shape.Point(center.y, center.x), -radius)
}
