package eu.joaocosta.minart.geometry

import eu.joaocosta.minart.geometry.Shape.Point

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

    assertEquals(circle.contains(0, -10).isDefined, true)
    assertEquals(circle.contains(0, 0).isDefined, true)
    assertEquals(circle.contains(10, 0).isDefined, false)
    assertEquals(circle.contains(10, 10).isDefined, false)
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
    assertEquals(positiveCircle.contains(0, -10), Some(Shape.Face.Front))
    assertEquals(negativeCircle.contains(0, -10), Some(Shape.Face.Back))
  }

  test("check if a circle is contained in a circle") {
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

  test("check if a circle collides with another") {
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
}
