package eu.joaocosta.minart.graphics

import scala.annotation.tailrec

/** Object with the internal blitting logic.
  */
private[graphics] object Blitter {

  def unsafeBlitSurface(
      dest: MutableSurface,
      source: Surface,
      mask: Option[Color],
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    mask match {
      case None =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = source.unsafeGetPixel(dx + cx, srcY)
            dest.putPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case Some(maskColor) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = source.unsafeGetPixel(dx + cx, srcY)
            if (color != maskColor) dest.putPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
    }
  }

  def unsafeBlitMatrix(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      mask: Option[Color],
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    mask match {
      case None =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = line(dx + cx)
            dest.putPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case Some(maskColor) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = line(dx + cx)
            if (color != maskColor) dest.putPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
    }
  }

  @tailrec
  def fullBlit(
      dest: MutableSurface,
      source: Surface,
      mask: Option[Color],
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      cw: Int,
      ch: Int
  ): Unit = {
    // Handle negative offsets
    if (x < 0) fullBlit(dest, source, mask, 0, y, cx - x, cy, cw + x, ch)
    else if (y < 0) fullBlit(dest, source, mask, x, 0, cx, cy - y, cw, ch + y)
    else if (cx < 0) fullBlit(dest, source, mask, x - cx, y, 0, cy, cw + cx, ch)
    else if (cy < 0) fullBlit(dest, source, mask, x, y - cy, cx, 0, cw, ch + cy)
    else {
      val maxX = math.min(cw, math.min(source.width - cx, dest.width - x))
      val maxY = math.min(ch, math.min(source.height - cy, dest.height - y))

      if (maxX > 0 && maxY > 0) {
        source match {
          case ramSurf: RamSurface => unsafeBlitMatrix(dest, ramSurf.dataBuffer, mask, x, y, cx, cy, maxX, maxY)
          case _                   => unsafeBlitSurface(dest, source, mask, x, y, cx, cy, maxX, maxY)
        }

      }
    }
  }
}
