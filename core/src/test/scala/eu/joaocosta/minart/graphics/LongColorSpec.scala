package eu.joaocosta.minart.graphics

class LongColorSpec extends munit.FunSuite {

  test("Can be created from a RGB values") {
    val color = LongColor(110, 120, 130)
    assert(color.r == 110 && color.g == 120 && color.b == 130)
  }

  test("Can be created from a RGBA values") {
    val color = LongColor(110, 120, 130, 140)
    assert(color.r == 110 && color.g == 120 && color.b == 130 && color.a == 140)
  }

  test("Can be created from a Color values") {
    val color     = Color(110, 120, 130, 140)
    val longColor = LongColor(color)
    assert(
      color.r == longColor.r && color.g == longColor.g && color.b == longColor.b && color.a == longColor.a
    )
  }

  test("Can be converted back into a Color") {
    val color = Color(110, 120, 130, 140)
    assert(LongColor(color).toColor == color)
  }

  test("Can be made opaque") {
    val color = Color(110, 120, 130, 140)
    assert(LongColor(color).opaque.toColor == color.copy(a = 255))
  }

  test("sumClamp sums to colors and clamp channels if needed") {
    val color1 = LongColor(10, 20, 30, 40)
    val color2 = LongColor(110, 120, 130, 140)

    assert(LongColor.sumClamp(color1, color2) == LongColor(120, 140, 160, 180))
    assert(LongColor.sumClamp(color2, color2) == LongColor(220, 240, 255, 255))
  }

  test("sumWrapAround sums to colors and wraps around channels if needed") {
    val color1 = LongColor(10, 20, 30, 40)
    val color2 = LongColor(110, 120, 130, 140)
    assert(LongColor.sumWrapAround(color1, color2) == LongColor(120, 140, 160, 180))
    assert(LongColor.sumWrapAround(color2, color2) == LongColor(220, 240, 4, 24))
  }

  test("weight merges colors as expected") {
    val color = LongColor(110, 120, 130, 140)

    assert(LongColor.weight(color, 0) == LongColor(0, 0, 0, 0))
    assert(LongColor.weight(color, 127.toByte) == LongColor(55, 60, 65, 70))
    assert(LongColor.weight(color, 255.toByte) == color)
  }
}
