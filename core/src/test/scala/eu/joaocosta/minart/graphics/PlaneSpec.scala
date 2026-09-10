package eu.joaocosta.minart.graphics

import scala.util.Random

class PlaneSpec extends munit.FunSuite {

  lazy val surface = new RamSurface(
    Vector.fill(16)(Vector.fill(8)(Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))))
  )
  lazy val originalPixels = surface.getPixels()

  test("Can be created from a constant color") {
    val plane = Plane.fromConstant(Color(10, 20, 30))
    assertEquals(plane.getPixel(Random.nextInt(), Random.nextInt()), Color(10, 20, 30))
  }

  test("Can be created from a function") {
    val plane = Plane.fromFunction((x, y) => Color(x, y, x + y))
    assertEquals(plane.getPixel(10, 20), Color(10, 20, 30))
  }

  test("Can be created from a surface with a fallback color") {
    val plane = Plane.fromSurfaceWithFallback(surface, Color(1, 2, 3))
    assertEquals(plane.getPixel(1, 2), surface.getPixel(1, 2).get)
    assertEquals(plane.getPixel(-1, -2), Color(1, 2, 3))
    assertEquals(plane.getPixel(1000, 1000), Color(1, 2, 3))
  }

  test("Can be created from a surface with repetition") {
    val plane = surface.view.repeating
    assertEquals(plane.getPixel(1, 2), surface.getPixel(1, 2).get)
    assertEquals(plane.getPixel(1 + 8, 2 + 16), surface.getPixel(1, 2).get)
    assertEquals(plane.getPixel(1 - 8, 2 - 16), surface.getPixel(1, 2).get)
  }

  test("Mapping it updates the colors") {
    val newSurface =
      surface.view.repeating
        .map(_.invert)
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_.invert))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Flatmapping it updates the colors based on the position") {
    val newSurface =
      surface.view.repeating
        .flatMap(color => (x, y) => if (y >= 8) color.invert else color)
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels =
      originalPixels.take(8) ++ originalPixels.drop(8).map(_.map(_.invert))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Transforming pixels is similar to flatmap") {
    val flatMapSurface =
      surface.view.repeating
        .flatMap(color => (x, y) => if (y >= 8) color.invert else color)
        .toRamSurface(surface.width, surface.height)
    val transformSurface =
      surface.view.repeating
        .transformPixels((color, x, y) => if (y >= 8) color.invert else color)
        .toRamSurface(surface.width, surface.height)
    val expectedPixels =
      flatMapSurface.getPixels()
    val actualPixels =
      transformSurface.getPixels()

    assertEquals(actualPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Contramapping updates the positions") {
    val newSurface =
      surface.view.repeating
        .contramap((x, y) => (y, x))
        .toRamSurface(surface.height, surface.width)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assertEquals(newSurface.width, surface.height)
    assertEquals(newSurface.height, surface.width)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Zipping combines two planes") {
    val plane =
      surface.view.repeating
    val newSurface =
      plane
        .zipWith(plane, (c1: Color, c2: Color) => Color(c1.r - c2.r, c1.g - c2.g, c1.b - c2.b))
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_ => Color(0, 0, 0)))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Zipping combines a plane and a surface") {
    val plane =
      surface.view.repeating
    val newSurface =
      plane
        .zipWith(surface, (c1: Color, c2: Color) => Color(c1.r - c2.r, c1.g - c2.g, c1.b - c2.b))
        .toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_ => Color(0, 0, 0)))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Coflatmapping it updates the colors based on the kernel function") {
    val newSurface =
      surface.view.repeating
        .coflatMap(img => img(1, 2))
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels =
      surface.view.repeating
        .translate(-1, -2)
        .toRamSurface(surface.width, surface.height)
        .getPixels()

    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Coflatmapping it updates the colors based on the kernel") {
    val kernel = Kernel(
      Seq(
        Seq(0, 0, 0),
        Seq(0, 0, 0),
        Seq(0, 0, 0),
        Seq(0, 0, 0),
        Seq(0, 0, 1)
      ),
      1
    )
    val newSurface =
      surface.view.repeating
        .coflatMap(kernel)
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels =
      surface.view.repeating
        .translate(-1, -2)
        .toRamSurface(surface.width, surface.height)
        .getPixels()

    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Clipping clips the view") {
    val newSurface =
      surface.view.repeating
        .clip(5, 5, 2, 2)
        .toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.slice(5, 7).map(_.slice(5, 7))

    assertEquals(newSurface.width, 2)
    assertEquals(newSurface.height, 2)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Overlay combines a plane and a surface") {
    val plane =
      surface.view.repeating
    val newPlane =
      plane.overlay(surface)(2, 2)

    assertEquals(newPlane(0, 0), surface.unsafeGetPixel(0, 0))
    assertEquals(newPlane(1, 1), surface.unsafeGetPixel(1, 1))
    assertEquals(newPlane(2, 2), surface.unsafeGetPixel(0, 0))
    assertEquals(newPlane(3, 3), surface.unsafeGetPixel(1, 1))
  }

  test("Inverting the color updates all colors with the inverse") {
    assertEquals(Plane.fromConstant(Color(110, 120, 130)).invertColor(100, 100), Color(110, 120, 130).invert)
  }

  test("Translation moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.translate(5, 10)
    assertEquals(original(0, 0), transformed(5, 10))
  }

  test("FlipH mirrors the pixels with the Y axis") {
    val original =
      surface.view.repeating
    val transformed = original.flipH
    assertEquals(original(5, 10), transformed(-5, 10))
  }

  test("FlipV mirrors the pixels with the X axis") {
    val original =
      surface.view.repeating
    val transformed = original.flipV
    assertEquals(original(5, 10), transformed(5, -10))
  }

  test("Scale upscales the plane") {
    val original =
      surface.view.repeating
    val transformed = original.scale(2)
    assertEquals(original(0, 0), transformed(0, 0))
    assertEquals(original(0, 0), transformed(1, 0))
    assertEquals(original(0, 0), transformed(0, 1))
    assertEquals(original(0, 0), transformed(1, 1))

    assertEquals(original(1, 1), transformed(2, 2))
    assertEquals(original(1, 1), transformed(3, 2))
    assertEquals(original(1, 1), transformed(2, 3))
    assertEquals(original(1, 1), transformed(3, 3))
  }

  test("Scale downscales the plane") {
    val original =
      surface.view.repeating
    val transformed = original.scale(0.5)
    assertEquals(original(0, 0), transformed(0, 0))
    assertEquals(original(2, 2), transformed(1, 1))
  }

  test("Rotate moves all pixels clockwise") {
    val original =
      surface.view.repeating
    val transformed = original.rotate(Math.PI / 2)
    assertEquals(original(0, 0), transformed(0, 0))
    assertEquals(original(5, 3), transformed(-3, 5))
  }

  test("Shear moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.shear(1, 0)
    assertEquals(original(0, 0), transformed(0, 0))
    assertEquals(original(0, 1), transformed(1, 1))
    assertEquals(original(0, 2), transformed(2, 2))
  }

  test("Transpose moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.transpose

    assertEquals(original(0, 0), transformed(0, 0))
    assertEquals(original(0, 1), transformed(1, 0))
    assertEquals(original(0, 2), transformed(2, 0))
  }
}
