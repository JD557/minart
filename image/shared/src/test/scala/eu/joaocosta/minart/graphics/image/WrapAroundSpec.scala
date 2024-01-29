package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.*

class WrapAroundSpec extends munit.FunSuite {
  val surface = new RamSurface(
    List(
      List(Color(255, 0, 0), Color(0, 255, 0), Color(0, 0, 255)),
      List(Color(0, 255, 255), Color(255, 0, 255), Color(255, 255, 0))
    )
  )

  test("getSurface generate a surface with the correct size") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.getSurface(0, 0)
    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
  }

  test("lineScroll generate a surface with the correct size") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.lineScroll(_ => 0)
    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
  }

  test("columnScroll generate a surface with the correct size") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.columnScroll(_ => 0)
    assert(newSurface.width == surface.width)
    assert(newSurface.height == surface.height)
  }

  test("getSurface handles no scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.getSurface(0, 0)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }

  test("lineScroll handles no scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.lineScroll(_ => 0)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }

  test("columnScroll handles no scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.columnScroll(_ => 0)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }

  test("getSurface handles positive scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.getSurface(2, 1)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 255, 0), Color(0, 255, 255), Color(255, 0, 255)),
          List(Color(0, 0, 255), Color(255, 0, 0), Color(0, 255, 0))
        )
    )
  }

  test("lineScroll handles positive scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(2, 0)
    val newSurface      = wrapAround.lineScroll(_ => 2)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("columnScroll handles positive scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(0, 1)
    val newSurface      = wrapAround.columnScroll(_ => 1)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("getSurface handles negative scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.getSurface(-1, -1)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 255, 0), Color(0, 255, 255), Color(255, 0, 255)),
          List(Color(0, 0, 255), Color(255, 0, 0), Color(0, 255, 0))
        )
    )
  }

  test("lineScroll handles negative scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(-1, 0)
    val newSurface      = wrapAround.lineScroll(_ => -1)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("columnScroll handles negative scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(0, -1)
    val newSurface      = wrapAround.columnScroll(_ => -1)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("getSurface handles overflow scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.getSurface(6, -4)
    assert(newSurface.getPixels().flatten.toList == surface.getPixels().flatten.toList)
  }

  test("lineScroll handles overflow scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(6, 0)
    val newSurface      = wrapAround.lineScroll(_ => 6)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("columnScroll handles overflow scroll") {
    val wrapAround      = new WrapAround(surface)
    val expectedSurface = wrapAround.getSurface(0, -4)
    val newSurface      = wrapAround.columnScroll(_ => -4)
    assert(newSurface.getPixels().flatten.toList == expectedSurface.getPixels().flatten.toList)
  }

  test("lineScroll handles variable scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.lineScroll(y => y)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 0, 0), Color(0, 255, 0), Color(0, 0, 255)),
          List(Color(255, 0, 255), Color(255, 255, 0), Color(0, 255, 255))
        )
    )
  }

  test("columnScroll handles variable scroll") {
    val wrapAround = new WrapAround(surface)
    val newSurface = wrapAround.columnScroll(x => x)
    assert(
      newSurface.getPixels().toList.map(_.toList) ==
        List(
          List(Color(255, 0, 0), Color(255, 0, 255), Color(0, 0, 255)),
          List(Color(0, 255, 255), Color(0, 255, 0), Color(255, 255, 0))
        )
    )
  }
}
