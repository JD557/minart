package eu.joaocosta.minart.geometry

/** Abstract shape.
  *
  * Can be combined with other shapes and can check if a point is inside of it.
  *
  * This API is *experimental* and might change in the near future.
  */
trait Shape {

  /** If this shape returns always the same face, this method returns that.
    * Otherwise, returns None.
    */
  def knownFace: Option[Shape.Face]

  /** Bounding box that contains this shape.
    *
    * This is useful for some optimizations, like short circuiting collisions and rasterization.
    *
    * Methods from this class not check if a point is inside this bounding box. It is expected that
    * the caller will do this check if they want the performance benefits.
    */
  def aabb: AxisAlignedBoundingBox

  /** Checks the face of this shape at a certain point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def faceAt(x: Int, y: Int): Option[Shape.Face]

  /** Checks the face of this shape at a certain point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  final def faceAt(point: Point): Option[Shape.Face] = faceAt(point.x.toInt, point.y.toInt)

  /** Checks if this shape contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  def contains(x: Int, y: Int): Boolean = faceAt(x, y).isDefined

  /** Checks if this shape contains a point.
    *
    * Returns the face that contains the point, so that the end user can decide to do backface culling or not.
    *
    * @param x x coordinates of the point
    * @param y y coordinates of the point
    * @return None if the point is not contained, Some(face) if the point is contained.
    */
  final def contains(point: Point): Boolean = contains(point.x.toInt, point.y.toInt)

  /** Contramaps the points in this shape using a matrix.
    *
    * Note that this is *contramapping*. The operation is applied as
    *
    * ```
    * [a b c] [dx] = [sx]
    * [d e f] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    * ```
    *
    * Where (sx,sy) are the positions in the original shape and (dx, dy) are the positions in the new shape.
    *
    * This means that you need to invert the transformations to use the common transformation matrices.
    *
    * For example, the matrix:
    *
    * ```
    * [2 0 0] [dx] = [sx]
    * [0 2 0] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    * ```
    *
    * Will *scale down* the shape, not scale up.
    *
    * Internally, this method will invert the matrix, so for performance sensitive operations it is recommended to use
    * mapMatrix with the direct matrix instead.
    */
  def contramapMatrix(matrix: Matrix): Shape =
    mapMatrix(matrix.inverse)

  /** Maps this the points in this shape using a matrix.
    *
    * This method can be chained multiple times efficiently.
    */
  def mapMatrix(matrix: Matrix): Shape =
    if (matrix == Matrix.identity) this
    else Shape.MatrixShape(matrix, this)

  /** Translates this shape. */
  def translate(dx: Double, dy: Double): Shape =
    mapMatrix(Matrix.translation(dx, dy))

  /** Flips a shape horizontally. */
  def flipH: Shape = mapMatrix(Matrix.flipH)

  /** Flips a shape vertically. */
  def flipV: Shape = mapMatrix(Matrix.flipV)

  /** Scales a shape. */
  def scale(sx: Double, sy: Double): Shape =
    mapMatrix(Matrix.scaling(sx, sy))

  /** Scales a shape. */
  def scale(s: Double): Shape = scale(s, s)

  /** Rotates a shape by a certain angle (clockwise). */
  def rotate(theta: Double): Shape =
    mapMatrix(Matrix.rotation(theta))

  /** Shears a shape. */
  def shear(sx: Double, sy: Double): Shape =
    mapMatrix(Matrix.shear(sx, sy))

  /** Transposes a shape (switches the x and y coordinates). */
  def transpose: Shape = mapMatrix(Matrix.transpose)

}

object Shape {

  /** The shape of a circle.
    *
    * If the radius is positive, the circle's front face is facing the viewer.
    * If the radius is negative, the circle's back face is facing the viewer.
    */
  def circle(center: Point, radius: Int): Circle = Circle(center, radius)

  /** The shape of an axis aligned rectangle.
    *
    * If p1 is the top left point or bottom right point, the rectangle's front face is facing the viewer.
    * Otherwise, the rectangle's back face is facing the viewer.
    */
  def rectangle(p1: Point, p2: Point): ConvexPolygon = ConvexPolygon(
    Vector(
      p1,
      Point(p2.x, p1.y),
      p2,
      Point(p1.x, p2.y)
    )
  )

  /** The shape of a triangle.
    *
    * If the points are ordered clockwise, the triangle's front face is facing the viewer.
    * Otherwise, the triangle's back face is facing the viewer.
    */
  def triangle(p1: Point, p2: Point, p3: Point): ConvexPolygon = ConvexPolygon(
    Vector(
      p1,
      p2,
      p3
    )
  )

  /** The shape of an arbitrary convex polygon.
    *
    * If the points are ordered clockwise, the polygon's front face is facing the viewer.
    * Otherwise, the polygon's back face is facing the viewer.
    *
    * If the points do not form a convex polygon, the behavior is undefined.
    */
  def convexPolygon(p1: Point, p2: Point, p3: Point, ps: Point*): ConvexPolygon = ConvexPolygon(
    Vector(
      p1,
      p2,
      p3
    ) ++ ps
  )

  /** Face of a convex polygon.
    *
    * If the points are defined in clockwise order, the front face faces the viewer.
    */
  enum Face {
    case Front
    case Back
  }

  /** Shape with a contour that can be rendered.
    *
    * It's OK for some shapes to not provide a contour.
    */
  trait ShapeWithContour extends Shape {

    /** Contour of this shape as a list of strokes.
      */
    def contour: Vector[Stroke]
  }

  // Preallocated values to avoid repeated allocations
  private[geometry] val someFront = Some(Face.Front)
  private[geometry] val someBack  = Some(Face.Back)

  final case class MatrixShape private[Shape] (matrix: Matrix, shape: Shape) extends Shape {
    def knownFace: Option[Shape.Face] = if (matrix.a * matrix.e < 0)
      shape.knownFace.map {
        case Face.Front => Face.Back
        case Face.Back  => Face.Front
      }
    else shape.knownFace
    lazy val aabb: AxisAlignedBoundingBox = {
      val xs = Vector(
        matrix.applyX(shape.aabb.x1, shape.aabb.y1),
        matrix.applyX(shape.aabb.x2, shape.aabb.y1),
        matrix.applyX(shape.aabb.x1, shape.aabb.y2),
        matrix.applyX(shape.aabb.x2, shape.aabb.y2)
      )
      val ys = Vector(
        matrix.applyY(shape.aabb.x1, shape.aabb.y1),
        matrix.applyY(shape.aabb.x2, shape.aabb.y1),
        matrix.applyY(shape.aabb.x1, shape.aabb.y2),
        matrix.applyY(shape.aabb.x2, shape.aabb.y2)
      )
      val minX = math.floor(xs.min).toInt
      val minY = math.floor(ys.min).toInt
      val maxX = math.ceil(xs.max).toInt
      val maxY = math.ceil(ys.max).toInt
      AxisAlignedBoundingBox(minX, minY, maxX - minX, maxY - minY)
    }
    def faceAt(x: Int, y: Int): Option[Shape.Face] =
      shape.faceAt(math.round(matrix.inverse.applyX(x, y)).toInt, math.round(matrix.inverse.applyY(x, y)).toInt)
    override def contains(x: Int, y: Int): Boolean =
      shape.contains(math.round(matrix.inverse.applyX(x, y)).toInt, math.round(matrix.inverse.applyY(x, y)).toInt)
    override def mapMatrix(matrix: Matrix): MatrixShape =
      MatrixShape(matrix.multiply(this.matrix), shape)
  }
}
