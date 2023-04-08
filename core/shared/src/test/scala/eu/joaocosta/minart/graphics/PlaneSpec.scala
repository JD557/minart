package eu.joaocosta.minart.graphics

import scala.util.Random

import verify._

object PlaneSpec extends BasicTestSuite {

  lazy val surface = new RamSurface(
    Vector.fill(16)(Array.fill(8)(Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))))
  )
  lazy val originalPixels = surface.getPixels()

  test("Can be created from a constant color") {
    val plane = Plane.fromConstant(Color(10, 20, 30))
    assert(plane.getPixel(Random.nextInt(), Random.nextInt()) == Color(10, 20, 30))
  }

  test("Can be created from a function") {
    val plane = Plane.fromFunction((x, y) => Color(x, y, x + y))
    assert(plane.getPixel(10, 20) == Color(10, 20, 30))
  }

  test("Can be created from a surface with a fallback color") {
    val plane = Plane.fromSurfaceWithFallback(surface, Color(1, 2, 3))
    assert(plane.getPixel(1, 2) == surface.getPixel(1, 2).get)
    assert(plane.getPixel(-1, -2) == Color(1, 2, 3))
    assert(plane.getPixel(1000, 1000) == Color(1, 2, 3))
  }

  test("Can be created from a surface with repetition") {
    val plane = surface.view.repeating
    assert(plane.getPixel(1, 2) == surface.getPixel(1, 2).get)
    assert(plane.getPixel(1 + 8, 2 + 16) == surface.getPixel(1, 2).get)
    assert(plane.getPixel(1 - 8, 2 - 16) == surface.getPixel(1, 2).get)
  }

  test("Mapping it updates the colors") {
    val newSurface =
      surface.view.repeating
        .map(_.invert)
        .toRamSurface(surface.width, surface.height)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_.invert))

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Flatmapping it updates the colors based on the position") {
    val newSurface =
      surface.view.repeating
        .flatMap(color => (x, y) => if (y >= 8) color.invert else color)
        .toRamSurface(surface.width, surface.height)
    val newPixels = newSurface.getPixels()
    val expectedPixels =
      originalPixels.take(8) ++ originalPixels.drop(8).map(_.map(_.invert))

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Contramapping updates the positions") {
    val newSurface =
      surface.view.repeating
        .contramap((x, y) => (y, x))
        .toRamSurface(surface.height, surface.width)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assert(newSurface.width == surface.height)
    assert(newSurface.height == surface.width)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
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

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
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

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Coflatmapping it updates the colors based on the kernel") {
    val newSurface =
      surface.view.repeating
        .coflatMap(img => img(1, 2))
        .toRamSurface(surface.width, surface.height)
    val newPixels = newSurface.getPixels()
    val expectedPixels =
      surface.view.repeating
        .translate(-1, -2)
        .toRamSurface(surface.width, surface.height)
        .getPixels()

    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Clipping clips the view") {
    val newSurface =
      surface.view.repeating
        .clip(5, 5, 2, 2)
        .toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.slice(5, 7).map(_.slice(5, 7))

    assert(newSurface.width == 2)
    assert(newSurface.height == 2)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Inverting the color updates all colors with the inverse") {
    assert(Plane.fromConstant(Color(110, 120, 130)).invertColor(100, 100) == Color(110, 120, 130).invert)
  }

  test("Translation moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.translate(5, 10)
    assert(original(0, 0) == transformed(5, 10))
  }

  test("FlipH mirrors the pixels with the Y axis") {
    val original =
      surface.view.repeating
    val transformed = original.flipH
    assert(original(5, 10) == transformed(-5, 10))
  }

  test("FlipV mirrors the pixels with the X axis") {
    val original =
      surface.view.repeating
    val transformed = original.flipV
    assert(original(5, 10) == transformed(5, -10))
  }

  test("Scale upscales the plane") {
    val original =
      surface.view.repeating
    val transformed = original.scale(2)
    assert(original(0, 0) == transformed(0, 0))
    assert(original(0, 0) == transformed(1, 0))
    assert(original(0, 0) == transformed(0, 1))
    assert(original(0, 0) == transformed(1, 1))

    assert(original(1, 1) == transformed(2, 2))
    assert(original(1, 1) == transformed(3, 2))
    assert(original(1, 1) == transformed(2, 3))
    assert(original(1, 1) == transformed(3, 3))
  }

  test("Scale downscales the plane") {
    val original =
      surface.view.repeating
    val transformed = original.scale(0.5)
    assert(original(0, 0) == transformed(0, 0))
    assert(original(2, 2) == transformed(1, 1))
  }

  test("Rotate moves all pixels clockwise") {
    val original =
      surface.view.repeating
    val transformed = original.rotate(math.Pi / 2)
    assert(original(0, 0) == transformed(0, 0))
    assert(original(5, 3) == transformed(-3, 5))
  }

  test("Shear moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.shear(1, 0)
    assert(original(0, 0) == transformed(0, 0))
    assert(original(0, 1) == transformed(1, 1))
    assert(original(0, 2) == transformed(2, 2))
  }

  test("Transpose moves all pixels") {
    val original =
      surface.view.repeating
    val transformed = original.transpose

    assert(original(0, 0) == transformed(0, 0))
    assert(original(0, 1) == transformed(1, 0))
    assert(original(0, 2) == transformed(2, 0))
  }
}
