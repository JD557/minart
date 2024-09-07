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
final case class ConvexPolygon(vertices: Vector[Shape.Point]) extends Shape {
  require(vertices.size >= 3, "A polygon needs at least 3 vertices")

  val aabb: AxisAlignedBoundingBox = {
    val x1 = vertices.iterator.minBy(_.x).x
    val y1 = vertices.iterator.minBy(_.y).y
    val x2 = vertices.iterator.maxBy(_.x).x
    val y2 = vertices.iterator.maxBy(_.y).y
    AxisAlignedBoundingBox(x1, y1, x2 - x1, y2 - y1)
  }

  val knownFace: Option[Shape.Face] =
    faceAt(aabb.centerX, aabb.centerY)

  private def edgeFunction(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int): Int =
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

  private def edgeFunction(p1: Shape.Point, p2: Shape.Point, p3: Shape.Point): Int =
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

  def faceAt(x: Int, y: Int): Option[Shape.Face] = {
    val sides = rawWeights(x, y).map(_ >= 0).distinct.toVector
    if (sides.size == 1) {
      if (sides.head) Shape.someFront else Shape.someBack
    } else None
  }

  override def contains(x: Int, y: Int): Boolean = {
    rawWeights(x, y).map(_ >= 0).distinct.size == 1
  }

  /** Checks if this polygon contains another polygon.
    *
    * @param that polygon to check
    * @return true if that polygon is contained in this polygon
    */
  def contains(that: ConvexPolygon): Boolean =
    that.vertices.forall(p => this.contains(p))

  /** Checks if this polygon collides with another polygon.
    *
    * @param that polygon to check
    * @return true if the polygons collide
    */
  def collides(that: ConvexPolygon): Boolean =
    this.vertices.exists(v => that.contains(v)) || that.vertices.exists(v => this.contains(v))

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
  def edgeWeights(point: Shape.Point): Vector[Double] = {
    rawWeights(point.x, point.y).map(_ / maxWeight.toDouble).toVector
  }

  override def mapMatrix(matrix: Matrix) =
    if (matrix == Matrix.identity) this
    else ConvexPolygon.MatrixPolygon(matrix, this)
}

object ConvexPolygon {
  private[ConvexPolygon] final case class MatrixPolygon(matrix: Matrix, polygon: ConvexPolygon) extends Shape {
    lazy val toConvexPolygon = ConvexPolygon(
      vertices = polygon.vertices.map { point =>
        Shape.Point(
          matrix.applyX(point.x, point.y),
          matrix.applyY(point.x, point.y)
        )
      }
    )

    def knownFace: Option[Shape.Face] = toConvexPolygon.knownFace
    def aabb: AxisAlignedBoundingBox  = toConvexPolygon.aabb
    def faceAt(x: Int, y: Int): Option[Shape.Face] =
      toConvexPolygon.faceAt(x, y)
    override def contains(x: Int, y: Int): Boolean =
      toConvexPolygon.contains(x, y)
    override def mapMatrix(matrix: Matrix) =
      if (matrix == Matrix.identity) this
      else MatrixPolygon(matrix.multiply(this.matrix), polygon)
  }
}
