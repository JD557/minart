package eu.joaocosta.minart.geometry

/** Circle shape.
  *
  * It's considered to be facing the viewer if the radius is positive.
  *
  * @param center center of the circle.
  * @param radius circle radius
  */
final case class Circle(center: Shape.Point, radius: Int) extends Shape {

  /** The absolute radius */
  val absRadius = math.abs(radius)

  /** The squared radius */
  val squareRadius = radius * radius

  val aabb: AxisAlignedBoundingBox = {
    val x = center.x - absRadius
    val y = center.y - absRadius
    val d = absRadius * 2
    AxisAlignedBoundingBox(x, y, d, d)
  }

  def contains(x: Int, y: Int): Option[Shape.Face] = {
    if ((x - center.x) * (x - center.x) + (y - center.y) * (y - center.y) <= squareRadius)
      if (radius >= 0) Shape.someFront
      else Shape.someBack
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
}
