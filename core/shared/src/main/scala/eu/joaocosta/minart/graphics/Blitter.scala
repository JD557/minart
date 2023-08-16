package eu.joaocosta.minart.graphics

import scala.annotation.tailrec

/** Object with the internal blitting logic.
  */
private[graphics] object Blitter {

  def unsafeBlitSurface(
      dest: MutableSurface,
      source: Surface,
      blendMode: BlendMode,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    blendMode match {
      case BlendMode.Copy =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = source.unsafeGetPixel(dx + cx, srcY)
            dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.ColorMask(maskColor) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = source.unsafeGetPixel(dx + cx, srcY)
            if (color != maskColor) dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.AlphaTest(alpha) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = source.unsafeGetPixel(dx + cx, srcY)
            if (color.a > alpha) dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.PremultAlphaAdd =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX       = dx + x
            val colorSource = source.unsafeGetPixel(dx + cx, srcY)
            val colorDest   = dest.unsafeGetPixel(destX, destY)
            val color = Color(
              (colorDest.r * (255 - colorSource.a)) / 255 + colorSource.r,
              (colorDest.g * (255 - colorSource.a)) / 255 + colorSource.g,
              (colorDest.b * (255 - colorSource.a)) / 255 + colorSource.b
            )
            dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
    }
  }

  def unsafeBlitMatrix(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      blendMode: BlendMode,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    blendMode match {
      case BlendMode.Copy =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = line(dx + cx)
            dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.ColorMask(maskColor) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = line(dx + cx)
            if (color != maskColor) dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.AlphaTest(alpha) =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = line(dx + cx)
            if (color.a > alpha) dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
      case BlendMode.PremultAlphaAdd =>
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX       = dx + x
            val colorSource = line(dx + cx)
            val colorDest   = dest.unsafeGetPixel(destX, destY)
            val color = Color(
              (colorDest.r * (255 - colorSource.a)) / 255 + colorSource.r,
              (colorDest.g * (255 - colorSource.a)) / 255 + colorSource.g,
              (colorDest.b * (255 - colorSource.a)) / 255 + colorSource.b
            )
            dest.unsafePutPixel(destX, destY, color)
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
      blendMode: BlendMode,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      cw: Int,
      ch: Int
  ): Unit = {
    // Handle negative offsets
    if (x < 0) fullBlit(dest, source, blendMode, 0, y, cx - x, cy, cw + x, ch)
    else if (y < 0) fullBlit(dest, source, blendMode, x, 0, cx, cy - y, cw, ch + y)
    else if (cx < 0) fullBlit(dest, source, blendMode, x - cx, y, 0, cy, cw + cx, ch)
    else if (cy < 0) fullBlit(dest, source, blendMode, x, y - cy, cx, 0, cw, ch + cy)
    else {
      val maxX = Math.min(cw, Math.min(source.width - cx, dest.width - x))
      val maxY = Math.min(ch, Math.min(source.height - cy, dest.height - y))

      if (maxX > 0 && maxY > 0) {
        source match {
          case ramSurf: RamSurface => unsafeBlitMatrix(dest, ramSurf.dataBuffer, blendMode, x, y, cx, cy, maxX, maxY)
          case _                   => unsafeBlitSurface(dest, source, blendMode, x, y, cx, cy, maxX, maxY)
        }

      }
    }
  }
}
