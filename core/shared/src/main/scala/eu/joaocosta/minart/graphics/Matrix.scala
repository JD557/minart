package eu.joaocosta.minart.graphics

/** Affine Transformatiom matrix of the form
  *  [a b c]
  *  [d e f]
  *  [0 0 1]
  */
final case class Matrix(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double) {
  def multiply(that: Matrix) =
    Matrix(
      this.a * that.a + this.b * that.d,
      this.a * that.b + this.b * that.e,
      this.a * that.c + this.b * that.f + this.c,
      this.d * that.a + this.e * that.d,
      this.d * that.b + this.e * that.e,
      this.d * that.c + this.e * that.f + this.f
    )
  def apply(x: Double, y: Double): (Double, Double) =
    (a * x + b * y + c * 1, d * x + e * y + f * 1)
  def apply(x: Int, y: Int): (Int, Int) = {
    val res = apply(x.toDouble, y.toDouble)
    (res._1.toInt, res._2.toInt)
  }
}
