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

  lazy val aabb: AxisAlignedBoundingBox =
    AxisAlignedBoundingBox.fromPoints(vertices)

  lazy val knownFace: Option[Shape.Face] =
    faceAt(vertices.head)

  lazy val contour: Vector[Stroke] =
    (vertices :+ vertices.head)
      .sliding(2)
      .collect { case Vector(a, b) => Stroke.Line(a, b) }
      .toVector

  // See https://jtsorlinis.github.io/rendering-tutorial/ and
  // https://lisyarus.github.io/blog/posts/implementing-a-tiny-cpu-rasterizer-part-2.html
  private inline def determinant(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Double =
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)

  // Helpful loop to avoid boxing and allocations
  private inline def determinantsForEach(x: Double, y: Double)(inline f: Double => Unit): Unit = {
    var idx = 0
    while (idx < size) {
      val current = vertices(idx)
      val next    = if (idx + 1 >= size) vertices(0) else vertices(idx + 1)
      val det     = determinant(current.x, current.y, next.x, next.y, x, y)
      f(det)
      idx += 1
    }
  }

  def faceAt(x: Int, y: Int): Option[Shape.Face] = {
    var inside: Boolean         = true
    var firstIteration: Boolean = true
    var last: Boolean           = true
    determinantsForEach(x, y) { det =>
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

  override def contains(x: Int, y: Int): Boolean = {
    var inside: Boolean         = true
    var firstIteration: Boolean = true
    var last: Boolean           = true
    determinantsForEach(x, y) { det =>
      if (det != 0 && inside) {
        val sign = det >= 0
        if (!firstIteration && last != sign) inside = false
        last = sign
        firstIteration = false
      }
    }
    inside
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

  override def mapMatrix(matrix: Matrix): Shape.ShapeWithContour =
    if (matrix == Matrix.identity) this
    else ConvexPolygon.MatrixPolygon(matrix, this)

  // Overrides to refine the type signature
  override def contramapMatrix(matrix: Matrix): Shape.ShapeWithContour =
    mapMatrix(matrix.inverse)
  override def translate(dx: Double, dy: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.translation(dx, dy))
  override def flipH: Shape.ShapeWithContour                         = mapMatrix(Matrix.flipH)
  override def flipV: Shape.ShapeWithContour                         = mapMatrix(Matrix.flipV)
  override def scale(sx: Double, sy: Double): Shape.ShapeWithContour =
    mapMatrix(Matrix.scaling(sx, sy))
  override def scale(s: Double): Shape.ShapeWithContour      = scale(s, s)
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

    def knownFace: Option[Shape.Face]              = toConvexPolygon.knownFace
    def aabb: AxisAlignedBoundingBox               = toConvexPolygon.aabb
    def contour                                    = toConvexPolygon.contour
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
