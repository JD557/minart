package eu.joaocosta.minart.graphics

trait MutableSurfaceTests extends munit.FunSuite {

  def surface: MutableSurface

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

    surface.fillRegion(0, 0, 1, 2, Color(1, 1, 1))
    assert(surface.getPixel(0, 0) == Some(Color(1, 1, 1)))
    assert(surface.getPixel(0, 1) == Some(Color(1, 1, 1)))
    assert(surface.getPixel(1, 0) == Some(Color(3, 2, 1)))

    surface.fillRegion(1, 1, 1, 1, Color(2, 2, 2))
    assert(surface.getPixel(0, 0) == Some(Color(1, 1, 1)))
    assert(surface.getPixel(0, 1) == Some(Color(1, 1, 1)))
    assert(surface.getPixel(1, 0) == Some(Color(3, 2, 1)))
    assert(surface.getPixel(1, 1) == Some(Color(2, 2, 2)))

    // offscreen, do nothing
    surface.fillRegion(-100, -100, 100, 100, Color(0, 0, 0))
    assert(surface.getPixel(0, 0) == Some(Color(1, 1, 1)))

    surface.fillRegion(-100, -100, 200, 200, Color(0, 0, 0))
    assert(surface.getPixel(0, 0) == Some(Color(0, 0, 0)))
  }

  test("Combine two surfaces without blowing up") {
    val source = surface.toRamSurface()

    surface.blit(source)(0, 0)
    surface.blit(source)(-1, -1)
    surface.blit(source)(1, 1)

    surface.blit(source)(0, 0, 1, 1)
    surface.blit(source)(0, 0, -1, -1)

    surface.blit(source)(0, 0, 0, 0, -1, -1)
    surface.blit(source)(0, 0, 0, 0, 2 * source.width, 2 * source.height)
  }

  test("Combine a surface with a surface view without blowing up") {
    val source = surface.toRamSurface().view

    surface.blit(source)(0, 0)
    surface.blit(source)(-1, -1)
    surface.blit(source)(1, 1)

    surface.blit(source)(0, 0, 1, 1)
    surface.blit(source)(0, 0, -1, -1)

    surface.blit(source)(0, 0, 0, 0, -1, -1)
    surface.blit(source)(0, 0, 0, 0, 2 * source.width, 2 * source.height)
  }

  test("Combine a surface with a plane without blowing up") {
    val source = surface.view.repeating

    surface.blitPlane(source)(0, 0)
    surface.blitPlane(source)(-1, -1)
    surface.blitPlane(source)(1, 1)
  }

  test("Correctly combine two surfaces") {
    surface.fill(Color(255, 0, 0))
    val source = surface.toRamSurface()
    surface.fill(Color(0, 0, 0))

    assert(surface.getPixel(0, 0) == Some(Color(0, 0, 0)))
    assert(surface.getPixel(1, 1) == Some(Color(0, 0, 0)))

    surface.blit(source)(1, 1)

    assert(surface.getPixel(0, 0) == Some(Color(0, 0, 0)))
    assert(source.getPixel(0, 0) == Some(Color(255, 0, 0)))
    assert(surface.getPixel(1, 1) == Some(Color(255, 0, 0)))

    surface.blit(source, BlendMode.ColorMask(Color(255, 0, 0)))(0, 0)

    assert(surface.getPixel(0, 0) == Some(Color(0, 0, 0)))
    assert(source.getPixel(0, 0) == Some(Color(255, 0, 0)))
    assert(surface.getPixel(1, 1) == Some(Color(255, 0, 0)))

    surface.blit(source, BlendMode.ColorMask(Color(0, 0, 0)))(0, 0)

    assert(surface.getPixel(0, 0) == Some(Color(255, 0, 0)))
    assert(source.getPixel(0, 0) == Some(Color(255, 0, 0)))
    assert(surface.getPixel(1, 1) == Some(Color(255, 0, 0)))
  }

  test("Modify a surface in place") {
    surface.modify(_.map(_ => Color(1, 2, 3)))
    assert(surface.getPixels().flatten.forall(_ == Color(1, 2, 3)))
  }
}
