package eu.joaocosta.minart.graphics

import scala.util.Random

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

object SurfaceViewSpec extends BasicTestSuite {

  lazy val surface = new RamSurface(
    Vector.fill(16)(Array.fill(8)(Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))))
  )
  lazy val originalPixels = surface.getPixels()

  test("The identity view does nothing") {
    val newSurface = surface.view.toRamSurface()
    val newPixels  = newSurface.getPixels()

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == originalPixels.map(_.toVector))
  }

  test("The map view updates the colors") {
    val newSurface     = surface.view.map(_.invert).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_.invert))

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("The contramap view updates the positions") {
    val newSurface = surface.view.contramap((x, y) => (y, x)).clip(0, 0, surface.height, surface.width).toRamSurface()
    val newPixels  = newSurface.getPixels()
    val expectedPixels = originalPixels.transpose

    assert(newSurface.width == surface.height)
    assert(newSurface.height == surface.width)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("The zip view combines two surfaces") {
    val newSurface =
      surface.view.zipWith(surface, (c1, c2) => Color(c1.r - c2.r, c1.g - c2.g, c1.b - c2.b)).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.map(_.map(_ => Color(0, 0, 0)))

    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }

  test("The clip view clips a surface") {
    val newSurface =
      surface.view.clip(5, 5, 2, 2).toRamSurface()
    val newPixels      = newSurface.getPixels()
    val expectedPixels = originalPixels.slice(5, 7).map(_.slice(5, 7))

    assert(newSurface.width == 2)
    assert(newSurface.height == 2)
    assert(newPixels.map(_.toVector) == expectedPixels.map(_.toVector))
  }
}
