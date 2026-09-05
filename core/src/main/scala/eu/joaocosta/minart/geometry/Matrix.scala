package eu.joaocosta.minart.geometry

/** Affine Transformation matrix of the form:
  *
  * ```
  * [a b c]
  * [d e f]
  * [0 0 1]
  * ```
  */
final case class Matrix(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double) {

  /** The inverse of this matrix.
    *
    *  If the determinant is 0, this returns an undefined matrix.
    */
  lazy val inverse: Matrix = {
    // https://nigeltao.github.io/blog/2021/inverting-3x2-affine-transformation-matrix.html
    val determinant = a * e - b * d
    if (determinant == 0) Matrix.undefined
    else if (determinant == 1) // Just translation and rotation
      Matrix(
        e,
        -b,
        (b * f) - (e * c),
        -d,
        a,
        (d * c) - (a * f)
      )
    else if (determinant == -1) // Improper rotation (reflection)
      Matrix(
        -e,
        b,
        (e * c) - (b * f),
        d,
        -a,
        (a * f) - (d * c)
      )
    else
      Matrix(
        e / determinant,
        -b / determinant,
        ((b * f) - (e * c)) / determinant,
        -d / determinant,
        a / determinant,
        ((d * c) - (a * f)) / determinant
      )
  }

  /** Multiplies this matrix with another matrix. */
  def multiply(that: Matrix) =
    Matrix(
      this.a * that.a + this.b * that.d,
      this.a * that.b + this.b * that.e,
      this.a * that.c + this.b * that.f + this.c,
      this.d * that.a + this.e * that.d,
      this.d * that.b + this.e * that.e,
      this.d * that.c + this.e * that.f + this.f
    )

  inline def applyX(x: Double, y: Double): Double = a * x + b * y + c * 1
  inline def applyX(x: Int, y: Int): Double       = (a * x + b * y + c * 1)
  inline def applyY(x: Double, y: Double): Double = d * x + e * y + f * 1
  inline def applyY(x: Int, y: Int): Double       = (d * x + e * y + f * 1)

  /** Applies the transformation to (x, y). */
  def apply(x: Double, y: Double): (Double, Double) =
    (applyX(x, y), applyY(x, y))

  /** Applies the transformation to (x, y). */
  def apply(x: Int, y: Int): (Double, Double) = {
    (applyX(x, y), applyY(x, y))
  }
}

object Matrix {

  /** Identity matrix.
    * Keeps everything unchanged.
    *
    * Some methods skip operations when they detect an identity matrix,
    * so you should use this object when possible to make the detection
    * faster (just a reference equality).
    *
    * ```
    * [1 0 0]
    * [0 1 0]
    * [0 0 1]
    * ```
    */
  val identity = Matrix(1, 0, 0, 0, 1, 0)

  /** Undefined matrix. All values are NaN.
    *
    * ```
    * [? ? ?]
    * [? ? ?]
    * [0 0 1]
    * ```
    *
    * Ideally, this should only be used to signal an error.
    */
  val undefined = Matrix(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN)

  /** Translation matrix.
    *
    * ```
    * [ 1  0 dx]
    * [ 0  1 dy]
    * [ 0  0  1]
    * ```
    */
  def translation(dx: Double, dy: Double): Matrix =
    if (dx == 0 && dy == 0) Matrix.identity
    else Matrix(1, 0, dx, 0, 1, dy)

  /** Matrix that flips values horizontally.
    *
    * ```
    * [-1  0  0]
    * [ 0  1  0]
    * [ 0  0  1]
    * ```
    */
  val flipH: Matrix = Matrix(-1, 0, 0, 0, 1, 0)

  /** Matrix that flips values vertically.
    *
    * ```
    * [ 1  0  0]
    * [ 0 -1  0]
    * [ 0  0  1]
    * ```
    */
  val flipV: Matrix = Matrix(1, 0, 0, 0, -1, 0)

  /** Matrix that transposes values (switch x and y coordinates).
    *
    * ```
    * [ 0  1  0]
    * [ 1  0  0]
    * [ 0  0  1]
    * ```
    */
  val transpose: Matrix = Matrix(0, 1, 0, 1, 0, 0)

  /** Scaling matrix.
    *
    * ```
    * [sx  0  0]
    * [ 0 sy  0]
    * [ 0  0  1]
    * ```
    */
  def scaling(sx: Double, sy: Double): Matrix =
    if (sx == 1.0 && sy == 1.0) Matrix.identity
    else Matrix(sx, 0, 0, 0, sy, 0)

  /** Rotation matrix.
    *
    * ```
    * [ cos  -sin    0]
    * [ sin   cos    0]
    * [   0     0    1]
    * ```
    */
  def rotation(theta: Double): Matrix =
    val ct = Math.cos(theta)
    if (ct == 1.0) Matrix.identity
    else {
      val st = Math.sin(theta)
      // cos and sin have precision issues near 0, so we round the result here to help with multiplications
      if (math.abs(ct) < 1e-10) Matrix(0, -st, 0, st, 0, 0)
      if (math.abs(st) < 1e-10) Matrix(ct, 0, 0, 0, ct, 0)
      else Matrix(ct, -st, 0, st, ct, 0)
    }

  /** Shear matrix.
    *
    * ```
    * [ 1 sx  0]
    * [sy  1  0]
    * [ 0  0  1]
    * ```
    */
  def shear(sx: Double, sy: Double): Matrix =
    if (sx == 0.0 && sy == 0.0) Matrix.identity
    else Matrix(1.0, sx, 0, sy, 1.0, 0)
}
