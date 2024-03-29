package eu.joaocosta.minart.graphics

class MatrixSpec extends munit.FunSuite {

  test("Can be applied") {
    // [1 2 3] [3] = [10] (1*3 + 2*2 + 3*1)
    // [4 5 6] [2]   [28] (4*3 + 5*2 + 6*1)
    // [0 0 1] [1]   [ 1]
    val testMatrix = Matrix(1, 2, 3, 4, 5, 6)
    assert(testMatrix.apply(3, 2) == (10, 28))
  }

  test("Can be multiplied") {
    // [1 2 3] [6 5 4] = [12  9  9]
    // [4 5 6] [3 2 1]   [39 30 27]
    // [0 0 1] [0 0 1]   [ 0  0  1]
    val testMatrix = Matrix(1, 2, 3, 4, 5, 6).multiply(
      Matrix(6, 5, 4, 3, 2, 1)
    )
    assert(testMatrix == Matrix(12, 9, 9, 39, 30, 27))
  }
}
