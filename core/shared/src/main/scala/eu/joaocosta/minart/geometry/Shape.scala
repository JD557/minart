package eu.joaocosta.minart.geometry

/** Abstract shape.
  *
  *  Can be combined with other shapes and can check if a point is inside of it.
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
  final def faceAt(point: Shape.Point): Option[Shape.Face] = faceAt(point.x, point.y)

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
  final def contains(point: Shape.Point): Boolean = contains(point.x, point.y)

  /** Contramaps the points in this shape using a matrix.
    *
    *  This method can be chained multiple times efficiently.
    *
    * Note that this is *contramaping*. The operation is applied as
    * [a b c] [dx] = [sx]
    * [d e f] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Where (sx,sy) are the positions in the original shape and (dx, dy) are the positions in the new shape.
    *
    * This means that you need to invert the transformations to use the common transformation matrices.
    *
    * For example, the matrix:
    *
    * [2 0 0] [dx] = [sx]
    * [0 2 0] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Will *scale down* the shape, not scale up.
    */
  def contramapMatrix(matrix: Matrix) =
    if (matrix == Matrix.identity) this
    else Shape.MatrixShape(matrix, this)

  /** Translates this shape. */
  final def translate(dx: Double, dy: Double): Shape =
    if (dx == 0 && dy == 0) this
    else contramapMatrix(Matrix(1, 0, -dx, 0, 1, -dy))

  /** Flips a shape horizontally. */
  final def flipH: Shape = contramapMatrix(Matrix(-1, 0, 0, 0, 1, 0))

  /** Flips a shape vertically. */
  final def flipV: Shape = contramapMatrix(Matrix(1, 0, 0, 0, -1, 0))

  /** Scales a shape. */
  final def scale(sx: Double, sy: Double): Shape =
    if (sx == 1.0 && sy == 1.0) this
    else contramapMatrix(Matrix(1.0 / sx, 0, 0, 0, 1.0 / sy, 0))

  /** Scales a shape. */
  final def scale(s: Double): Shape = scale(s, s)

  /** Rotates a shape by a certain angle (clockwise). */
  final def rotate(theta: Double): Shape = {
    val ct = Math.cos(-theta)
    if (ct == 1.0) this
    else {
      val st = Math.sin(-theta)
      contramapMatrix(Matrix(ct, -st, 0, st, ct, 0))
    }
  }

  /** Shears a shape. */
  final def shear(sx: Double, sy: Double): Shape =
    if (sx == 0.0 && sy == 0.0) this
    else contramapMatrix(Matrix(1.0, -sx, 0, -sy, 1.0, 0))

  /** Transposes a shape (switches the x and y coordinates). */
  final def transpose: Shape = contramapMatrix(Matrix(0, 1, 0, 1, 0, 0))

}

object Shape {
  private[Shape] final case class MatrixShape(matrix: Matrix, shape: Shape) extends Shape {
    def knownFace: Option[Shape.Face] = shape.knownFace
    lazy val aabb: AxisAlignedBoundingBox = {
      val inverse = matrix.inverse
      val xs = Vector(
        inverse.applyX(shape.aabb.x1, shape.aabb.y1),
        inverse.applyX(shape.aabb.x2, shape.aabb.y1),
        inverse.applyX(shape.aabb.x1, shape.aabb.y2),
        inverse.applyX(shape.aabb.x2, shape.aabb.y2)
      )
      val ys = Vector(
        inverse.applyY(shape.aabb.x1, shape.aabb.y1),
        inverse.applyY(shape.aabb.x2, shape.aabb.y1),
        inverse.applyY(shape.aabb.x1, shape.aabb.y2),
        inverse.applyY(shape.aabb.x2, shape.aabb.y2)
      )
      val minX = xs.min
      val minY = ys.min
      val maxX = xs.max
      val maxY = ys.max
      AxisAlignedBoundingBox(minX, minY, maxX - minX, maxY - minY)
    }
    def faceAt(x: Int, y: Int): Option[Shape.Face] =
      shape.faceAt(matrix.applyX(x, y), matrix.applyY(x, y))
    override def contains(x: Int, y: Int): Boolean =
      shape.contains(matrix.applyX(x, y), matrix.applyY(x, y))
  }

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
