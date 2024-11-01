package eu.joaocosta.minart.graphics

class KernelSpec extends munit.FunSuite {

  test("Can be created from a odd-sized matrix") {
    val kernel = Kernel(
      Seq(
        Seq(1, 2, 3, 0, 0),
        Seq(3, 2, 1, 0, 0),
        Seq(1, 1, 1, 1, 1)
      ),
      1,
      0
    )
    assert(kernel.width == 5)
    assert(kernel.height == 3)
  }

  test("Cannot be created from a even-sized matrix") {
    intercept[IllegalArgumentException] {
      Kernel(
        Seq(
          Seq(1, 2, 3, 0, 0),
          Seq(3, 2, 1, 0, 0)
        ),
        1,
        0
      )
    }
  }

  test("Cannot be created from an unbalanced matrix") {
    intercept[IllegalArgumentException] {
      Kernel(
        Seq(
          Seq(1, 2, 3, 0, 0),
          Seq(3),
          Seq(1, 1, 1, 1, 1)
        ),
        1,
        0
      )
    }
  }

  test("Cannot be created with a normalization constant of 0") {
    intercept[IllegalArgumentException] {
      Kernel(
        Seq(
          Seq(1, 2, 3, 0, 0),
          Seq(3, 2, 1, 0, 0),
          Seq(1, 1, 1, 1, 1)
        ),
        0,
        0
      )
    }
  }

  test("Can be compared for equality") {
    val kernelA = Kernel(
      Seq(
        Seq(1, 0, 1),
        Seq(0, 1, 0),
        Seq(1, 0, 1)
      ),
      1,
      0
    )
    val kernelB = Kernel(
      Seq(
        Seq(1, 0, 1),
        Seq(0, 1, 0),
        Seq(1, 0, 1)
      ),
      1,
      0
    )
    val kernelC = Kernel(
      Seq(
        Seq(1, 0, 1),
        Seq(0, 2, 0),
        Seq(1, 0, 1)
      ),
      1,
      0
    )
    val kernelD = Kernel(
      Seq(
        Seq(1, 0, 1),
        Seq(0, 1, 0),
        Seq(1, 0, 1)
      ),
      2,
      0
    )
    val kernelE = Kernel(
      Seq(
        Seq(1, 0, 1),
        Seq(0, 1, 0),
        Seq(1, 0, 1)
      ),
      1,
      127
    )
    assert(kernelA == kernelB)
    assert(kernelA != kernelC)
    assert(kernelA != kernelD)
    assert(kernelA != kernelE)
  }

}
