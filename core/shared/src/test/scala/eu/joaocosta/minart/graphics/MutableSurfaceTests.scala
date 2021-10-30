package eu.joaocosta.minart.graphics

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

trait MutableSurfaceTests extends BasicTestSuite {

  def surface: Surface.MutableSurface

  test("Return the correct number of pixels") {
    val pixels = surface.getPixels()
    assert(pixels.size == surface.height)
    assert(pixels.forall(_.size == surface.width))
  }

  test("Write and read pixels in certain positions") {
    surface.putPixel(0, 0, Color(1, 2, 3))
    surface.putPixel(0, 1, Color(3, 2, 1))
    surface.putPixel(1, 0, Color(2, 1, 3))
    assert(surface.getPixel(0, 0) == Some(Color(1, 2, 3)))
    assert(surface.getPixel(0, 1) == Some(Color(3, 2, 1)))
    assert(surface.getPixel(1, 0) == Some(Color(2, 1, 3)))
    assert(surface.getPixels()(0)(0) == Color(1, 2, 3))
    assert(surface.getPixels()(1)(0) == Color(3, 2, 1))
    assert(surface.getPixels()(0)(1) == Color(2, 1, 3))
  }

  test("Don't blow up when invalid positions are provided") {
    surface.putPixel(-1, -1, Color(1, 2, 3))
    surface.putPixel(surface.width, 0, Color(1, 2, 3))
    surface.putPixel(0, surface.height, Color(1, 2, 3))

    assert(surface.getPixel(-1, -1) == None)
    assert(surface.getPixel(surface.width, 0) == None)
    assert(surface.getPixel(0, surface.height) == None)
  }

  test("Fill the surface with a single color") {
    surface.fill(Color(1, 2, 3))
    assert(surface.getPixels().flatten.forall(_ == Color(1, 2, 3)))
    surface.fill(Color(3, 2, 1))
    assert(surface.getPixels().flatten.forall(_ == Color(3, 2, 1)))
  }
}
