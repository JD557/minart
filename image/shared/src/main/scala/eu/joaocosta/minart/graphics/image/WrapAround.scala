package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.graphics.*

/** A surface that's optimzied to be used as a scrolling layer that wraps around.
  *
  *  Unlike a repeating plane, this is limited in height and width. However, it can be quite faster, as some data is precomputed.
  *
  *  This also means that changes to the original surface after the scroller creation won't be reflected.
  *
  *  @param surface reference surface to use
  */
final class WrapAround(surface: Surface) {

  private val precomputed = surface.view.repeating(2, 2).precompute

  /** Gets a surface offset to start at position (x, y).
    *
    *  @param x horizontal position on the reference surface
    *  @param y vertical position on the reference surface
    *  @return surface view with the scrolled surface
    */
  def getSurface(x: Int, y: Int): SurfaceView = {
    val startX = WrapAround.floorMod(x, surface.width)
    val startY = WrapAround.floorMod(y, surface.height)
    precomputed.clip(startX, startY, surface.width, surface.height)
  }

  /** Gets a surface with every line offset by dx.
    *
    *  @param dx horizontal offset of each line, based on the y position
    *  @return surface view with the scrolled surface
    */
  def lineScroll(dx: Int => Int): SurfaceView = {
    val precomputedOffsets = Array.tabulate(surface.height)(y => dx(y)).map(x => WrapAround.floorMod(x, surface.width))
    precomputed.contramap((x, y) => (x + precomputedOffsets(y), y)).toSurfaceView(surface.width, surface.height)
  }

  /** Gets a surface with every column offset by dy.
    *
    *  @param dy horizontal offset of each line, based on the x position
    *  @return surface view with the scrolled surface
    */
  def columnScroll(dy: Int => Int): SurfaceView = {
    val precomputedOffsets = Array.tabulate(surface.width)(x => dy(x)).map(y => WrapAround.floorMod(y, surface.height))
    precomputed.contramap((x, y) => (x, y + precomputedOffsets(x))).toSurfaceView(surface.width, surface.height)
  }
}

object WrapAround {
  private def floorMod(x: Int, y: Int): Int = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }
}
