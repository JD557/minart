package eu.joaocosta.minart.geometry

/** Convex polygon constructed from a series of vertices.
  *
  * It's considered to be facing the viewer if the vertices are clockwise.
  *
  * There is no check in place to guarantee that the generated polygon is actually convex.
  * If this is not the case, the methods may return wrong results.
  *
  * @param vertices ordered sequence of vertices.
  */
final case class ConvexPolygon(vertices: Vector[ConvexPolygon.Point]) {
  require(vertices.size >= 3, "A polygon needs at least 3 vertices")

  /** Bounding box that contains this polygon.
    *
    * This is useful for some optimizations, like short circuiting collisions and rasterization.
    *
    * Methods inside the convex polygon do not check if a point is inside this bounding box. It is expected that
    * the caller will do this check if they want the performance benefits.
    */
  val aabb: AxisAlignedBoundingBox = {
    val x1 = vertices.iterator.minBy(_.x).x
    val y1 = vertices.iterator.minBy(_.y).y
    val x2 = vertices.iterator.maxBy(_.x).x
    val y2 = vertices.iterator.maxBy(_.y).y
    AxisAlignedBoundingBox(x1, y1, x2 - x1, y2 - y1)
  }

  private def edgeFunction(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int): Int =
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

  private def edgeFunction(p1: ConvexPolygon.Point, p2: ConvexPolygon.Point, p3: ConvexPolygon.Point): Int =
    edgeFunction(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)

  private def rawWeights(x: Int, y: Int): Iterator[Int] =
    (0 until vertices.size).iterator.map(idx =>
      val current = vertices(idx)
      val next    = if (idx + 1 >= vertices.size) vertices(0) else vertices(idx + 1)
      edgeFunction(current.x, current.y, next.x, next.y, x, y)
    )

  private val maxWeight: Int =
    (vertices.tail)
      .sliding(2)
      .collect { case Vector(b, c) =>
        edgeFunction(vertices.head, b, c)
      }
      .sum

  /** Checks if this polygon contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def contains(x: Int, y: Int): Option[ConvexPolygon.Face] = {
    val sides = rawWeights(x, y).map(_ >= 0).distinct.toVector
    if (sides.size == 1) {
      if (sides.head) ConvexPolygon.someFront else ConvexPolygon.someBack
    } else None
  }

  /** Checks if this polygon contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def contains(point: ConvexPolygon.Point): Option[ConvexPolygon.Face] = contains(point.x, point.y)

  /** Checks if this polygon contains another polygon.
    *
    * @param that polygon to check
    * @return true if that polygon is contained in this polygon
    */
  def contains(that: ConvexPolygon): Boolean =
    that.vertices.forall(p => this.contains(p).isDefined)

  /** Checks if this polygon collides with another polygon.
    *
    * @param that polygon to check
    * @return true if the polygons collide
    */
  def collides(that: ConvexPolygon): Boolean =
    this.vertices.exists(v => that.contains(v).isDefined) || that.vertices.exists(v => this.contains(v).isDefined)

  /** Normalized distance from an edge. Useful for some tricks like color interpolation.
    *
    * This can be a bit confusing on polygons with more than 3 edges, but on a triangle is similar
    * vertex weights.
    *
    * On a triangle:
    *   - a value of 0 means that the point is on top of the edge,
    *   - a value of 1 means that the point is on the vertex opposite to the edge.
    *   - a negative value means that the point is on the wrong side of the edge
    *
    * So, on a triangle, and edge weight can be seen as the vetex weight of the vertex opposed to the edge.
    *
    * @param x x coordinates of the point
    * @param y x coordinates of the point
    * @return weight
    */
  def edgeWeights(x: Int, y: Int): Vector[Double] = {
    rawWeights(x, y).map(_ / maxWeight.toDouble).toVector
  }

  /** Normalized distance from an edge. Useful for some tricks like color interpolation.
    *
    * This can be a bit confusing on polygons with more than 3 edges, but on a triangle is similar
    * vertex weights.
    *
    * On a triangle:
    *   - a value of 0 means that the point is on top of the edge,
    *   - a value of 1 means that the point is on the vertex opposite to the edge.
    *   - a negative value means that the point is on the wrong side of the edge
    *
    * So, on a triangle, and edge weight can be seen as the vetex weight of the vertex opposed to the edge.
    *
    * @param point point to test
    * @return weight
    */
  def edgeWeights(point: ConvexPolygon.Point): Vector[Double] = {
    rawWeights(point.x, point.y).map(_ / maxWeight.toDouble).toVector
  }
}

object ConvexPolygon {

  /** Face if a convex polygon.
    *
    * If the points are defined in clockwise order, the front face faces the viewer.
    */
  enum Face {
    case Front
    case Back
  }

  // Preallocated values to avoid repeated allocations
  private[ConvexPolygon] val someFront = Some(Face.Front)
  private[ConvexPolygon] val someBack  = Some(Face.Back)

  final case class Point(x: Int, y: Int)
}
