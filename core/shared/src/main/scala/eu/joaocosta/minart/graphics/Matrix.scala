package eu.joaocosta.minart.graphics

/** Affine Transformation matrix of the form:
  *  ```
  *  [a b c]
  *  [d e f]
  *  [0 0 1]
  *  ```
  */
final case class Matrix(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double) {

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
  inline def applyX(x: Int, y: Int): Int          = (a * x + b * y + c * 1).toInt
  inline def applyY(x: Double, y: Double): Double = d * x + e * y + f * 1
  inline def applyY(x: Int, y: Int): Int          = (d * x + e * y + f * 1).toInt

  /** Applies the transformation to (x, y). */
  def apply(x: Double, y: Double): (Double, Double) =
    (applyX(x, y), applyY(x, y))

  /** Applies the transformation to (x, y). */
  def apply(x: Int, y: Int): (Int, Int) = {
    (applyX(x, y), applyY(x, y))
  }
}
