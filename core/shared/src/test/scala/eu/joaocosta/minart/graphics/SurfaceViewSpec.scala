package eu.joaocosta.minart.graphics

import scala.util.Random

class SurfaceViewSpec extends munit.FunSuite {

  lazy val surface = new RamSurface(
    Vector.fill(16)(Vector.fill(8)(Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))))
  )
  lazy val originalPixels = surface.getPixels()

  test("The identity view does nothing") {
    val newSurface = surface.view.toRamSurface()
    val newPixels  = newSurface.getPixels()

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), originalPixels.map(_.toVector))
  }

  test("The map view updates the colors") {
    val newSurface     = surface.view.map(_.invert).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_.invert))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("The flatMap view updates the colors based on the position") {
    val newSurface = surface.view
      .flatMap(color => (x, y) => if (y >= 8) color.invert else color)
      .toRamSurface()
    val newPixels = newSurface.getPixels()
    val expectedPixels =
      originalPixels.take(8) ++ originalPixels.drop(8).map(_.map(_.invert))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("The contramap view updates the positions") {
    val newSurface = surface.view.contramap((x, y) => (y, x)).clip(0, 0, surface.height, surface.width).toRamSurface()
    val newPixels  = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assertEquals(newSurface.width, surface.height)
    assertEquals(newSurface.height, surface.width)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("The zip view combines two surfaces") {
    val newSurface =
      surface.view
        .zipWith(surface, (c1: Color, c2: Color) => Color(c1.r - c2.r, c1.g - c2.g, c1.b - c2.b))
        .toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_ => Color(0, 0, 0)))

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Coflatmapping it updates the colors based on the kernel") {
    val newSurface =
      surface.view
        .coflatMap(img => img.getPixel(1, 2).getOrElse(Color(50, 150, 200)))
        .toRamSurface()
    val newPixels = newSurface.getPixels()
    val expectedPixels =
      Plane
        .fromSurfaceWithFallback(surface, Color(50, 150, 200))
        .translate(-1, -2)
        .toRamSurface(surface.width, surface.height)
        .getPixels()

    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("The clip view clips a surface") {
    val newSurface =
      surface.view.clip(5, 5, 2, 2).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.slice(5, 7).map(_.slice(5, 7))

    assertEquals(newSurface.width, 2)
    assertEquals(newSurface.height, 2)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Overlay combines two surfaces") {
    val newSurface =
      surface.view.overlay(surface)(2, 2).toRamSurface()

    val newPixels = newSurface.getPixels()
    val expectedPixels =
      originalPixels.take(2) ++
        originalPixels.drop(2).zip(originalPixels).map { case (oldLine, newLine) =>
          oldLine.take(2) ++ newLine.dropRight(2)
        }

    assertEquals(newSurface.width, surface.width)
    assertEquals(newSurface.height, surface.height)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("FlipH mirrors the surface horizontally") {
    val newSurface =
      surface.view.flipH.toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.reverse)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("FlipV mirrors the surface vertically") {
    val newSurface =
      surface.view.flipV.toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.reverse
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }

  test("Scale upscales the surface") {
    val newSurface =
      surface.view.scale(2.0).toRamSurface()

    assertEquals(newSurface.width, 2 * surface.width)
    assertEquals(newSurface.height, 2 * surface.height)
  }

  test("Scale downscales the plane") {
    val newSurface =
      surface.view.scale(0.5).toRamSurface()

    assertEquals(newSurface.width, surface.width / 2)
    assertEquals(newSurface.height, surface.height / 2)
  }

  test("Scale upscale/downscales the plane across independent axis") {
    val newSurface =
      surface.view.scale(0.5, 2.0).toRamSurface()

    assertEquals(newSurface.width, surface.width / 2)
    assertEquals(newSurface.height, surface.height * 2)
  }

  test("Transpose transposes the image") {
    val newSurface =
      surface.view.transpose.toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assertEquals(newSurface.width, surface.height)
    assertEquals(newSurface.height, surface.width)
    assertEquals(newPixels.map(_.toVector), expectedPixels.map(_.toVector))
  }
}
