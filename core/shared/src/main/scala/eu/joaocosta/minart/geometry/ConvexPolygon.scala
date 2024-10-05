package eu.joaocosta.minart.geometry

/** Convex polygon constructed from a series of vertices.
  *
  * It's considered to be facing the viewer if the vertices are clockwise.
  *
  * There is no check in place to guarantee that the generated polygon is actually convex.
  * If this is not the case, the methods may return wrong results.
  *
  * This API is *experimental* and might change in the near future.
  *
  * @param vertices ordered sequence of vertices.
  */
final case class ConvexPolygon(vertices: Vector[Point]) extends Shape.ShapeWithContour {
  val size = vertices.size
  require(size >= 3, "A polygon needs at least 3 vertices")

  lazy val aabb: AxisAlignedBoundingBox = {
    val builder = AxisAlignedBoundingBox.Builder()
    vertices.foreach(builder.add)
    builder.result()
  }

  lazy val knownFace: Option[Shape.Face] =
    faceAt(vertices.head)

  lazy val contour: Vector[Stroke] =
    (vertices :+ vertices.head)
      .sliding(2)
      .collect { case Vector(a, b) =>
        Stroke.Line(a, b)
      }
      .toVector

  private def edgeFunction(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Double =
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

  private def edgeFunction(p1: Point, p2: Point, p3: Point): Double =
    edgeFunction(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)

  private def rawWeights(x: Double, y: Double): Iterator[Double] = {
    (0 until size).iterator.map(idx =>
      val current = vertices(idx)
      val next    = if (idx + 1 >= size) vertices(0) else vertices(idx + 1)
      edgeFunction(current.x, current.y, next.x, next.y, x, y)
    )
  }

  private lazy val maxWeight: Double =
    (vertices.tail)
      .sliding(2)
      .collect { case Vector(b, c) =>
        edgeFunction(vertices.head, b, c)
      }
      .sum

  def faceAt(x: Int, y: Int): Option[Shape.Face] = {
    val it                      = rawWeights(x, y).filter(_ != 0).map(_ >= 0)
    var res: Option[Shape.Face] = null
    if (it.hasNext) {
      var last = it.next()
      while (it.hasNext && res != None) {
        val value = it.next()
        if (last != value) res = None
        last = value
      }
      if (res == null) {
        if (last) res = Shape.someFront
        else res = Shape.someBack
      }
    }
    if (res == null) None else res
  }

  override def contains(x: Int, y: Int): Boolean = {
    val it  = rawWeights(x, y).filter(_ != 0).map(_ >= 0)
    var res = true
    if (it.hasNext) {
      var last = it.next()
      while (it.hasNext && res) {
        val value = it.next()
        if (last != value) res = false
        last = value
      }
    }
    res
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
  def edgeWeights(point: Point): Vector[Double] = {
    rawWeights(point.x, point.y).map(_ / maxWeight.toDouble).toVector
  }

  override def mapMatrix(matrix: Matrix): Shape.ShapeWithContour =
    if (matrix == Matrix.identity) this
    else ConvexPolygon.MatrixPolygon(matrix, this)

  // Overrides to refine the type signature
  override def contramapMatrix(matrix: Matrix): Shape.ShapeWithContour =
    mapMatrix(matrix.inverse)
  override def translate(dx: Double, dy: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.translation(dx, dy))
  override def flipH: Shape.ShapeWithContour = mapMatrix(Matrix.flipH)
  override def flipV: Shape.ShapeWithContour = mapMatrix(Matrix.flipV)
  override def scale(sx: Double, sy: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.scaling(sx, sy))
  override def scale(s: Double): Shape.ShapeWithContour = scale(s, s)
  override def rotate(theta: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.rotation(theta))
  override def shear(sx: Double, sy: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.shear(sx, sy))
  override def transpose: Shape.ShapeWithContour = mapMatrix(Matrix.transpose)
}

object ConvexPolygon {
  private[ConvexPolygon] final case class MatrixPolygon(matrix: Matrix, polygon: ConvexPolygon)
      extends Shape.ShapeWithContour {
    lazy val toConvexPolygon = ConvexPolygon(
      vertices = polygon.vertices.map { point =>
        Point(
          matrix.applyX(point.x, point.y),
          matrix.applyY(point.x, point.y)
        )
      }
    )

    def knownFace: Option[Shape.Face] = toConvexPolygon.knownFace
    def aabb: AxisAlignedBoundingBox  = toConvexPolygon.aabb
    def contour                       = toConvexPolygon.contour
    def faceAt(x: Int, y: Int): Option[Shape.Face] =
      toConvexPolygon.faceAt(x, y)
    override def contains(x: Int, y: Int): Boolean =
      toConvexPolygon.contains(x, y)
    override def mapMatrix(matrix: Matrix): MatrixPolygon =
      if (matrix == Matrix.identity) this
      else MatrixPolygon(matrix.multiply(this.matrix), polygon)
    override def translate(dx: Double, dy: Double): MatrixPolygon =
      mapMatrix(Matrix.translation(dx, dy))
  }
}
