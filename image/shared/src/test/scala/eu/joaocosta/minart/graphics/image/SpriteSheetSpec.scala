package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics._

class SpriteSheetSpec extends munit.FunSuite {
  val surface = new RamSurface(
    List(
      List(Color(255, 0, 0), Color(0, 255, 0), Color(0, 0, 255)),
      List(Color(0, 255, 255), Color(255, 0, 255), Color(255, 255, 0))
    )
  ).view.scale(2, 4).toRamSurface()

  test("Compute the number of sprites") {
    val spriteSheet = new SpriteSheet(surface, 2, 4)

    assert(spriteSheet.spritesPerLine == 3)
    assert(spriteSheet.spritesPerColumn == 2)
    assert(spriteSheet.size == 6)
  }

  test("Fetch sprite by position") {
    val spriteSheet = new SpriteSheet(surface, 2, 4)

    val red     = spriteSheet.getSprite(line = 0, column = 0)
    val green   = spriteSheet.getSprite(line = 0, column = 1)
    val blue    = spriteSheet.getSprite(line = 0, column = 2)
    val cyan    = spriteSheet.getSprite(line = 1, column = 0)
    val magenta = spriteSheet.getSprite(line = 1, column = 1)
    val yellow  = spriteSheet.getSprite(line = 1, column = 2)

    assert(red.getPixels().flatten.toSet == Set(Color(255, 0, 0)))
    assert(green.getPixels().flatten.toSet == Set(Color(0, 255, 0)))
    assert(blue.getPixels().flatten.toSet == Set(Color(0, 0, 255)))
    assert(cyan.getPixels().flatten.toSet == Set(Color(0, 255, 255)))
    assert(magenta.getPixels().flatten.toSet == Set(Color(255, 0, 255)))
    assert(yellow.getPixels().flatten.toSet == Set(Color(255, 255, 0)))
  }

  test("Fetch sprite by index") {
    val spriteSheet = new SpriteSheet(surface, 2, 4)

    val red     = spriteSheet.getSprite(0)
    val green   = spriteSheet.getSprite(1)
    val blue    = spriteSheet.getSprite(2)
    val cyan    = spriteSheet.getSprite(3)
    val magenta = spriteSheet.getSprite(4)
    val yellow  = spriteSheet.getSprite(5)

    assert(red.getPixels().flatten.toSet == Set(Color(255, 0, 0)))
    assert(green.getPixels().flatten.toSet == Set(Color(0, 255, 0)))
    assert(blue.getPixels().flatten.toSet == Set(Color(0, 0, 255)))
    assert(cyan.getPixels().flatten.toSet == Set(Color(0, 255, 255)))
    assert(magenta.getPixels().flatten.toSet == Set(Color(255, 0, 255)))
    assert(yellow.getPixels().flatten.toSet == Set(Color(255, 255, 0)))
  }
}
