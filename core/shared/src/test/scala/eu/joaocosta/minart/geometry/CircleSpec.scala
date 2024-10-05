package eu.joaocosta.minart.geometry

class CircleSpec extends munit.FunSuite {
  test("Computes the bounding box of a circle") {
    val circle = Circle(
      Point(0, -10),
      10
    )
    assertEquals(circle.aabb, AxisAlignedBoundingBox(-10, -20, 20, 20))
  }

  test("Check if a circle contains a point") {
    val circle = Circle(
      Point(0, -10),
      10
    )

    assertEquals(circle.contains(0, -10), true)
    assertEquals(circle.contains(0, 0), true)
    assertEquals(circle.contains(10, 0), false)
    assertEquals(circle.contains(10, 10), false)
  }

  test("Return the circle face") {
    val positiveCircle = Circle(
      Point(0, -10),
      10
    )
    val negativeCircle = Circle(
      Point(0, -10),
      -10
    )

    assertEquals(positiveCircle.knownFace, Some(Shape.Face.Front))
    assertEquals(negativeCircle.knownFace, Some(Shape.Face.Back))

    assertEquals(positiveCircle.faceAt(0, -10), Some(Shape.Face.Front))
    assertEquals(negativeCircle.faceAt(0, -10), Some(Shape.Face.Back))
  }

  test("Check if a circle is contained in a circle") {
    val bigCircle = Circle(
      Point(0, 0),
      10
    )
    val smallCircle = Circle(
      Point(0, 0),
      5
    )
    val intersectCircle = Circle(
      Point(5, 0),
      9
    )
    val strayCircle = Circle(
      Point(100, 100),
      10
    )
    assertEquals(bigCircle.contains(smallCircle), true)
    assertEquals(bigCircle.contains(intersectCircle), false)
    assertEquals(bigCircle.contains(strayCircle), false)
    assertEquals(smallCircle.contains(bigCircle), false)
  }

  test("Check if a circle collides with another") {
    val bigCircle = Circle(
      Point(0, 0),
      10
    )
    val smallCircle = Circle(
      Point(0, 0),
      5
    )
    val intersectCircle = Circle(
      Point(5, 0),
      9
    )
    val strayCircle = Circle(
      Point(100, 100),
      10
    )
    assertEquals(bigCircle.collides(smallCircle), true)
    assertEquals(bigCircle.collides(intersectCircle), true)
    assertEquals(bigCircle.collides(strayCircle), false)
    assertEquals(smallCircle.collides(bigCircle), true)
  }

  test("Can be transformed with a chain of transformations") {
    val originalCircle = Circle(
      Point(-5, -10),
      10
    )
    val transformedCircle =
      originalCircle.flipH.flipV
        .translate(0, -10)
        .rotate(Math.PI)
        .translate(5, 0)
        .scale(0.5)
        .scale(2, 2)
        .scale(0.5)
    val expectedCircle = Circle(
      Point(0, 0),
      5
    )

    assertEquals(transformedCircle.aabb, expectedCircle.aabb)
    expectedCircle.aabb.foreach((x, y) => assertEquals(transformedCircle.faceAt(x, y), expectedCircle.faceAt(x, y)))
  }
}
