package eu.joaocosta.minart.geometry

import eu.joaocosta.minart.geometry.Shape.Point

class ConvexPolygonSpec extends munit.FunSuite {
  test("Computes the bounding box of a polygon") {
    val polygon = ConvexPolygon(
      Vector(
        Point(0, -10),
        Point(5, 5),
        Point(-5, 5)
      )
    )
    assertEquals(polygon.aabb, AxisAlignedBoundingBox(-5, -10, 10, 15))
  }

  test("Check if a polygon contains a point") {
    val polygon = ConvexPolygon(
      Vector(
        Point(0, -10),
        Point(5, 5),
        Point(-5, 5)
      )
    )
    assertEquals(polygon.contains(0, 0).isDefined, true)
    assertEquals(polygon.contains(0, -10).isDefined, true)
    assertEquals(polygon.contains(4, -9).isDefined, false)
    assertEquals(polygon.contains(0, -11).isDefined, false)
  }

  test("Return the polygon face") {
    val cwPolygon = ConvexPolygon(
      Vector(
        Point(0, -10),
        Point(5, 5),
        Point(-5, 5)
      )
    )
    val ccwPolygon = ConvexPolygon(cwPolygon.vertices.reverse)
    assertEquals(cwPolygon.contains(0, 0), Some(Shape.Face.Front))
    assertEquals(ccwPolygon.contains(0, 0), Some(Shape.Face.Back))
  }

  test("check if a polygon is contained in another") {
    val bigPolygon = ConvexPolygon(
      Vector(
        Point(0, -10),
        Point(5, 5),
        Point(-5, 5)
      )
    )
    val smallPolygon = ConvexPolygon(
      Vector(
        Point(0, -9),
        Point(4, 4),
        Point(-4, 4)
      )
    )
    val intersectPolygon = ConvexPolygon(
      Vector(
        Point(1, -9),
        Point(6, 6),
        Point(-4, 6)
      )
    )
    val strayPolygon = ConvexPolygon(
      Vector(
        Point(100, -10),
        Point(105, 5),
        Point(95, 5)
      )
    )
    assertEquals(bigPolygon.contains(smallPolygon), true)
    assertEquals(bigPolygon.contains(intersectPolygon), false)
    assertEquals(bigPolygon.contains(strayPolygon), false)
    assertEquals(smallPolygon.contains(bigPolygon), false)
  }

  test("check if a polygon collides with another") {
    val bigPolygon = ConvexPolygon(
      Vector(
        Point(0, -10),
        Point(5, 5),
        Point(-5, 5)
      )
    )
    val smallPolygon = ConvexPolygon(
      Vector(
        Point(0, -9),
        Point(4, 4),
        Point(-4, 4)
      )
    )
    val intersectPolygon = ConvexPolygon(
      Vector(
        Point(1, -9),
        Point(6, 6),
        Point(-4, 6)
      )
    )
    val strayPolygon = ConvexPolygon(
      Vector(
        Point(100, -10),
        Point(105, 5),
        Point(95, 5)
      )
    )
    assertEquals(bigPolygon.collides(smallPolygon), true)
    assertEquals(bigPolygon.collides(intersectPolygon), true)
    assertEquals(bigPolygon.collides(strayPolygon), false)
    assertEquals(smallPolygon.collides(bigPolygon), true)
  }
}
