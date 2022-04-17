package eu.joaocosta.minart.graphics

import scala.util.Random

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

object PlaneSpec extends BasicTestSuite {

  lazy val surface = new RamSurface(
    Vector.fill(16)(Array.fill(8)(Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))))
  )
  lazy val originalPixels = surface.getPixels()

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
    val plane = Plane.fromSurfaceWithRepetition(surface)
    assert(plane.getPixel(1, 2) == surface.getPixel(1, 2).get)
    assert(plane.getPixel(1 + 8, 2 + 16) == surface.getPixel(1, 2).get)
    assert(plane.getPixel(1 - 8, 2 - 16) == surface.getPixel(1, 2).get)
  }

  test("Mapping it updates the colors") {
    val newSurface =
      Plane
        .fromSurfaceWithRepetition(surface)
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
      Plane
        .fromSurfaceWithRepetition(surface)
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
      Plane
        .fromSurfaceWithRepetition(surface)
        .contramap((x, y) => (y, x))
        .toRamSurface(surface.height, surface.width)
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assert(newSurface.width == surface.height)
    assert(newSurface.height == surface.width)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("Zipping combines two planes") {
    val plane = Plane.fromSurfaceWithRepetition(surface)
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
    val plane = Plane.fromSurfaceWithRepetition(surface)
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

  test("Clipping clips the view") {
    val newSurface =
      Plane.fromSurfaceWithRepetition(surface).clip(5, 5, 2, 2).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.slice(5, 7).map(_.slice(5, 7))

    assert(newSurface.width == 2)
    assert(newSurface.height == 2)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }
}
