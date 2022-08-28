package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics._

/** A sprite sheet containing multiple sprites in a single image.
  *
  *  @param surface Surface with the sprite sheet
  *  @param spriteWidth width of each sprite
  *  @param spriteHeight height of each sprite
  */
class SpriteSheet(surface: Surface, spriteWidth: Int, spriteHeight: Int) {

  /** How many sprites are stored on each line */
  val spritesPerLine = surface.width / spriteWidth

  /** Gets a sprite at a given position in the sheet.
    *
    *  @param column column of the sprite
    *  @param line line of the sprite
    *  @return surface view with the sprite
    */
  def getSprite(column: Int, line: Int): SurfaceView =
    surface.view.clip(line * spriteWidth, column * spriteHeight, spriteWidth, spriteHeight)

  /** Gets a sprite at the nth position in the sheet.
    *
    *  @param n position of the sprite (moves down a column when the end of a line is reached)
    *  @return surface view with the sprite
    */
  def getSprite(n: Int): SurfaceView =
    getSprite(n / spritesPerLine, n % spritesPerLine)
}
