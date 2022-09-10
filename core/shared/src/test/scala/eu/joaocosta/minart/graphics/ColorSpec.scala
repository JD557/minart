package eu.joaocosta.minart.graphics

import scala.util.Random

import verify._

object ColorSpec extends BasicTestSuite {

  test("Can be created from RGB values") {
    val colorInt  = Color(110, 120, 130)
    val colorByte = Color(110.toByte, 120.toByte, 130.toByte)
    assert(colorInt == colorByte)
    assert(colorInt.r == 110 && colorInt.g == 120 && colorInt.b == 130)
  }

  test("Can be created from grayscale values") {
    val colorInt  = Color.grayscale(130)
    val colorByte = Color.grayscale(130)
    assert(colorInt == colorByte)
    assert(colorInt.r == 130 && colorInt.g == 130 && colorInt.b == 130)
  }

  test("Can be created from raw RGB values") {
    val color    = Color(110, 120, 130)
    val newColor = Color.fromRGB(color.argb)
    assert(color == newColor)
  }

  test("Can be summed/subtracted without overflowing/underflowing") {
    val colorA = Color(10, 20, 30)
    val colorB = Color(30, 40, 50)
    val colorC = Color(250, 40, 240)

    assert(colorA + colorB == Color(40, 60, 80))
    assert(colorB + colorC == Color(255, 80, 255))
    assert(colorA + colorC == Color(255, 60, 255))

    assert(colorA - colorB == Color(0, 0, 0))
    assert(colorB - colorA == Color(20, 20, 20))
    assert(colorB - colorC == Color(0, 0, 0))
    assert(colorC - colorB == Color(220, 0, 190))
    assert(colorA - colorC == Color(0, 0, 0))
    assert(colorC - colorA == Color(240, 20, 210))
  }

  test("Can be multiplied without overflowing/undeflowing") {
    val color = Color(0, 128, 255)

    assert(color * color == Color(0, 64, 255))
  }

  test("Can be inverted") {
    val color = Color(0, 128, 255).invert

    assert(color == Color(255, 127, 0))
  }

}
