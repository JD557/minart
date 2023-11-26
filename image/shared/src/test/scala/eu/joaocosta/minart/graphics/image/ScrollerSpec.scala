package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.*

class ScrollerSpec extends munit.FunSuite {
  val surface = new RamSurface(
    List(
      List(Color(255, 0, 0), Color(0, 255, 0), Color(0, 0, 255)),
      List(Color(0, 255, 255), Color(255, 0, 255), Color(255, 255, 0))
    )
  )

  test("Generate a surface with the correct size") {
    val scroller   = new Scroller(surface)
    val newSurface = scroller.getSurface(0, 0)
    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
  }

  test("handle no scroll") {
    val scroller   = new Scroller(surface)
    val newSurface = scroller.getSurface(0, 0)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }

  test("handle positive scroll") {
    val scroller   = new Scroller(surface)
    val newSurface = scroller.getSurface(2, 1)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 255, 0), Color(0, 255, 255), Color(255, 0, 255)),
          List(Color(0, 0, 255), Color(255, 0, 0), Color(0, 255, 0))
        )
    )
  }

  test("handle negative scroll") {
    val scroller   = new Scroller(surface)
    val newSurface = scroller.getSurface(-1, -1)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 255, 0), Color(0, 255, 255), Color(255, 0, 255)),
          List(Color(0, 0, 255), Color(255, 0, 0), Color(0, 255, 0))
        )
    )
  }

  test("handle overflow scroll") {
    val scroller   = new Scroller(surface)
    val newSurface = scroller.getSurface(6, -4)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }
}
