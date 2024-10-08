package eu.joaocosta.minart.geometry

class MatrixSpec extends munit.FunSuite {

  test("Can be applied") {
    // [1 2 3] [3] = [10] (1*3 + 2*2 + 3*1)
    // [4 5 6] [2]   [28] (4*3 + 5*2 + 6*1)
    // [0 0 1] [1]   [ 1]
    val testMatrix = Matrix(1, 2, 3, 4, 5, 6)
    assertEquals(testMatrix.apply(3, 2), (10.0, 28.0))
  }

  test("Can be multiplied") {
    // [1 2 3] [6 5 4] = [12  9  9]
    // [4 5 6] [3 2 1]   [39 30 27]
    // [0 0 1] [0 0 1]   [ 0  0  1]
    val testMatrix = Matrix(1, 2, 3, 4, 5, 6).multiply(
      Matrix(6, 5, 4, 3, 2, 1)
    )
    assertEquals(testMatrix, Matrix(12, 9, 9, 39, 30, 27))
  }

  test("Can be inverted") {
    val testMatrix = Matrix(1, 2, 3, 4, 5, 6)
    val result     = testMatrix.inverse.multiply(testMatrix)

    // Approximate equality
    assert(math.abs(Matrix.identity.a - result.a) <= 0.01)
    assert(math.abs(Matrix.identity.b - result.b) <= 0.01)
    assert(math.abs(Matrix.identity.c - result.c) <= 0.01)
    assert(math.abs(Matrix.identity.d - result.d) <= 0.01)
    assert(math.abs(Matrix.identity.e - result.e) <= 0.01)
    assert(math.abs(Matrix.identity.f - result.f) <= 0.01)
  }
}
