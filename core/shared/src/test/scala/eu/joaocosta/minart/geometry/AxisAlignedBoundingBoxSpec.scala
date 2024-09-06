package eu.joaocosta.minart.geometry

class AxisAlignedBoundingBoxSpec extends munit.FunSuite {

  test("Computes helper positions") {
    val aabb = AxisAlignedBoundingBox(10, 15, 10, 20)
    assertEquals(aabb.x1, 10)
    assertEquals(aabb.x2, 20)
    assertEquals(aabb.y1, 15)
    assertEquals(aabb.y2, 35)
    assertEquals(aabb.centerX, 15)
    assertEquals(aabb.centerY, 25)
  }

  test("Checks if a point is inside the box") {
    val aabb = AxisAlignedBoundingBox(10, 10, 10, 10)
    assertEquals(aabb.contains(0, 0), false)
    assertEquals(aabb.contains(15, 15), true)
    assertEquals(aabb.contains(30, 30), false)
  }

  test("Checks if a box is inside the box") {
    val aabb = AxisAlignedBoundingBox(10, 10, 10, 10)
    assertEquals(aabb.contains(AxisAlignedBoundingBox(0, 0, 9, 9)), false)
    assertEquals(aabb.contains(AxisAlignedBoundingBox(0, 0, 11, 11)), false)
    assertEquals(aabb.contains(AxisAlignedBoundingBox(12, 12, 1, 1)), true)
  }

  test("Checks if a box collides the box") {
    val aabb = AxisAlignedBoundingBox(10, 10, 10, 10)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 9, 9)), false)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 100, 9)), false)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 10, 10)), true)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 11, 11)), true)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(12, 12, 1, 1)), true)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 100, 100)), true)
    assertEquals(aabb.collides(AxisAlignedBoundingBox(0, 0, 100, 15)), true)
  }

  test("Merge two bounding boxes when there's a gap") {
    val aabb1 = AxisAlignedBoundingBox(10, 10, 10, 10)
    val aabb2 = AxisAlignedBoundingBox(30, 10, 10, 10)
    assertEquals(aabb1.union(aabb2), AxisAlignedBoundingBox(10, 10, 30, 10))
    assertEquals(aabb2.union(aabb1), AxisAlignedBoundingBox(10, 10, 30, 10))
  }

  test("Merge two bounding boxes when they intersect") {
    val aabb1 = AxisAlignedBoundingBox(10, 10, 10, 10)
    val aabb2 = AxisAlignedBoundingBox(15, 10, 10, 10)
    assertEquals(aabb1.union(aabb2), AxisAlignedBoundingBox(10, 10, 15, 10))
    assertEquals(aabb2.union(aabb1), AxisAlignedBoundingBox(10, 10, 15, 10))
  }

  test("Return an empty bounding box intersection when there's a gap") {
    val aabb1 = AxisAlignedBoundingBox(10, 10, 10, 10)
    val aabb2 = AxisAlignedBoundingBox(30, 10, 10, 10)
    assertEquals(aabb1.intersect(aabb2).isEmpty, true)
    assertEquals(aabb2.intersect(aabb1).isEmpty, true)
  }

  test("Shrink two bounding boxes when they intersect") {
    val aabb1 = AxisAlignedBoundingBox(10, 10, 10, 10)
    val aabb2 = AxisAlignedBoundingBox(15, 10, 10, 10)
    assertEquals(aabb1.intersect(aabb2), AxisAlignedBoundingBox(15, 10, 5, 10))
    assertEquals(aabb2.intersect(aabb1), AxisAlignedBoundingBox(15, 10, 5, 10))
  }
}
