package eu.joaocosta.minart.geometry

/** Convex polygon constructed from a series of vertices.
  *
  * It's considered to be facing the viewer if the vertices are clockwise.
  *
  * There is no check in place to guarantee that the generated polygon is actually convex.
  * If this is not the case, the methods may return wrong results.
  *
  * This API is *experimental* and might change in the near future.
  */
sealed trait ConvexPolygon extends Shape.ShapeWithContour {

  /** Ordered sequence of vertices.
    */
  def vertices: Vector[Point]

  final lazy val size = vertices.size

  final lazy val aabb: AxisAlignedBoundingBox =
    AxisAlignedBoundingBox.fromPoints(vertices)

  final lazy val contour: Vector[Stroke] =
    (vertices :+ vertices.head)
      .sliding(2)
      .collect { case Vector(a, b) => Stroke.Line(a, b) }
      .toVector

  final lazy val knownFace: Option[Shape.Face] =
    faceAt(vertices.head)

  /** Iterates over all edges and applies a function f: (i, det(V_i - V_i+1, P - Vi)) => Unit.
    *
    * Useful for low level rasterization operations.
    *
    * @param x Px
    * @param y Py
    * @param f (i, det(V_i - V_i+1, P - Vi)) => Unit
    */
  inline final def determinantsForEach(x: Double, y: Double)(inline f: (Int, Double) => Unit): Unit = {
    var idx = 0
    while (idx < size) {
      val current = vertices(idx)
      val next    = if (idx + 1 >= size) vertices(0) else vertices(idx + 1)
      val det     = ConvexPolygon.determinant(current.x, current.y, next.x, next.y, x, y)
      f(idx, det)
      idx += 1
    }
  }

  /** Checks if this polygon contains another polygon.
    *
    * @param that polygon to check
    * @return true if that polygon is contained in this polygon
    */
  final def contains(that: ConvexPolygon): Boolean =
    that.vertices.forall(p => this.contains(p))

  /** Checks if this polygon collides with another polygon.
    *
    * @param that polygon to check
    * @return true if the polygons collide
    */
  final def collides(that: ConvexPolygon): Boolean =
    this.vertices.exists(v => that.contains(v)) || that.vertices.exists(v => this.contains(v))

  final def faceAt(x: Int, y: Int): Option[Shape.Face] = {
    var inside: Boolean         = true
    var firstIteration: Boolean = true
    var last: Boolean           = true
    determinantsForEach(x, y) { (_, det) =>
      if (det != 0 && inside) {
        val sign = det >= 0
        if (!firstIteration && last != sign) inside = false
        last = sign
        firstIteration = false
      }
    }
    if (!inside || firstIteration) None
    else if (last) Shape.someFront
    else Shape.someBack
  }

  override final def contains(x: Int, y: Int): Boolean = {
    var inside: Boolean         = true
    var firstIteration: Boolean = true
    var last: Boolean           = true
    determinantsForEach(x, y) { (_, det) =>
      if (det != 0 && inside) {
        val sign = det >= 0
        if (!firstIteration && last != sign) inside = false
        last = sign
        firstIteration = false
      }
    }
    inside
  }

  override def mapMatrix(matrix: Matrix): ConvexPolygon =
    if (matrix == Matrix.identity) this
    else ConvexPolygon.MatrixConvexPolygon(matrix, this)

  // Overrides to refine the type signature
  override final def contramapMatrix(matrix: Matrix): ConvexPolygon   = mapMatrix(matrix.inverse)
  override final def translate(dx: Double, dy: Double): ConvexPolygon = mapMatrix(Matrix.translation(dx, dy))
  override final def flipH: ConvexPolygon                             = mapMatrix(Matrix.flipH)
  override final def flipV: ConvexPolygon                             = mapMatrix(Matrix.flipV)
  override final def scale(sx: Double, sy: Double): ConvexPolygon     = mapMatrix(Matrix.scaling(sx, sy))
  override final def scale(s: Double): ConvexPolygon                  = scale(s, s)
  override final def rotate(theta: Double): ConvexPolygon             = mapMatrix(Matrix.rotation(theta))
  override final def shear(sx: Double, sy: Double): ConvexPolygon     = mapMatrix(Matrix.shear(sx, sy))
  override final def transpose: ConvexPolygon                         = mapMatrix(Matrix.transpose)
}

object ConvexPolygon {

  /** Convex polygon constructed from a series of vertices.
    *
    * It's considered to be facing the viewer if the vertices are clockwise.
    *
    * There is no check in place to guarantee that the generated polygon is actually convex.
    * If this is not the case, the methods may return wrong results.
    *
    * @param vertices ordered sequence of vertices.
    */
  def apply(vertices: Vector[Point]): StrictConvexPolygon = {
    require(vertices.size >= 3, "A convex polygon needs at least 3 vertices")
    StrictConvexPolygon(vertices)
  }

  // See https://jtsorlinis.github.io/rendering-tutorial/ and
  // https://lisyarus.github.io/blog/posts/implementing-a-tiny-cpu-rasterizer-part-2.html
  inline def determinant(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Double =
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

  inline def determinant(p1: Point, p2: Point, p3: Point): Double =
    determinant(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)

  /** Convex Polygon with vertices computed ahead of time
    *
    * @param vertices ordered sequence of vertices
    */
  final case class StrictConvexPolygon private[ConvexPolygon] (vertices: Vector[Point]) extends ConvexPolygon

  /** Convex Polygon with a matrix transformation applied
    *
    * @param matrix Matrix transformation
    * @param polygon inner polygon
    */
  final case class MatrixConvexPolygon private[ConvexPolygon] (matrix: Matrix, polygon: ConvexPolygon)
      extends ConvexPolygon {
    lazy val vertices = polygon.vertices.map { point =>
      Point(
        matrix.applyX(point.x, point.y),
        matrix.applyY(point.x, point.y)
      )
    }
    override def mapMatrix(matrix: Matrix): MatrixConvexPolygon =
      if (matrix == Matrix.identity) this
      else MatrixConvexPolygon(matrix.multiply(this.matrix), polygon)
  }
}
