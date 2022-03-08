package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics._

/** A sprite sheet containing multiple sprites in a single image.
  */
class SpriteSheet(surface: Surface, spriteWidth: Int, spriteHeight: Int) {

  val spritesPerLine = surface.width / spriteWidth

  def getSprite(column: Int, line: Int): Surface =
    surface.view.clip(line * spriteWidth, column * spriteHeight, spriteWidth, spriteHeight)

  def getSprite(n: Int): Surface =
    getSprite(n / spritesPerLine, n % spritesPerLine)
}
