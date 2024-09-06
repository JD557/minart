package eu.joaocosta.minart.geometry

/** Abstract shape.
  *
  *  Can be combined with other shapes and can check if a point is inside of it.
  */
trait Shape {

  /** Bounding box that contains this shape.
    *
    * This is useful for some optimizations, like short circuiting collisions and rasterization.
    *
    * Methods from this class not check if a point is inside this bounding box. It is expected that
    * the caller will do this check if they want the performance benefits.
    */
  val aabb: AxisAlignedBoundingBox

  /** Checks if this shape contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def contains(x: Int, y: Int): Option[Shape.Face]

  /** Checks if this shape contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def contains(point: Shape.Point): Option[Shape.Face] = contains(point.x, point.y)
}

object Shape {

  /** Face if a convex polygon.
    *
    * If the points are defined in clockwise order, the front face faces the viewer.
    */
  enum Face {
    case Front
    case Back
  }

  // Preallocated values to avoid repeated allocations
  private[geometry] val someFront = Some(Face.Front)
  private[geometry] val someBack  = Some(Face.Back)

  final case class Point(x: Int, y: Int)
}
