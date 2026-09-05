package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.*

/** A sprite sheet containing multiple sprites in a single image.
  *
  *  @param surface Surface with the sprite sheet
  *  @param spriteWidth width of each sprite
  *  @param spriteHeight height of each sprite
  */
final class SpriteSheet(surface: Surface, val spriteWidth: Int, val spriteHeight: Int) {

  /** How many sprites are stored on each line */
  val spritesPerLine = surface.width / spriteWidth

  /** How many sprites are stored on each column */
  val spritesPerColumn = surface.height / spriteHeight

  /** How many sprites are stored */
  val size = spritesPerColumn * spritesPerLine

  /** Gets a sprite at a given position in the sheet.
    *
    *  @param line line of the sprite
    *  @param column column of the sprite
    *  @return surface view with the sprite
    */
  def getSprite(line: Int, column: Int): SurfaceView =
    surface.view.clip(column * spriteWidth, line * spriteHeight, spriteWidth, spriteHeight)

  /** Gets a sprite at the nth position in the sheet.
    *
    *  @param n position of the sprite (moves down a column when the end of a line is reached)
    *  @return surface view with the sprite
    */
  def getSprite(n: Int): SurfaceView =
    getSprite(n / spritesPerLine, n % spritesPerLine)
}
