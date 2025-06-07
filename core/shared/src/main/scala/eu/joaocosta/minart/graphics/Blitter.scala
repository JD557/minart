package eu.joaocosta.minart.graphics

import scala.annotation.tailrec

/** Object with the internal blitting logic.
  */
private[graphics] object Blitter {

  inline def unsafeBlitSurfaceLoop(
      dest: MutableSurface,
      source: Surface,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  )(inline putColor: (Int, Int, Color) => Unit): Unit = {
    var dy = 0
    while (dy < maxY) {
      val srcY  = dy + cy
      val destY = dy + y
      var dx    = 0
      while (dx < maxX) {
        val destX = dx + x
        putColor(destX, destY, source.unsafeGetPixel(dx + cx, srcY))
        dx += 1
      }
      dy += 1
    }
  }

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
    blendMode match {
      case BlendMode.Copy =>
        unsafeBlitSurfaceLoop(dest, source, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.ColorMask(maskColor) =>
        unsafeBlitSurfaceLoop(dest, source, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          if (color != maskColor) dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.AlphaTest(alpha) =>
        unsafeBlitSurfaceLoop(dest, source, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          if (color.a > alpha) dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.AlphaAdd =>
        unsafeBlitSurfaceLoop(dest, source, x, y, cx, cy, maxX, maxY)((destX, destY, colorSource) =>
          val colorDest = dest.unsafeGetPixel(destX, destY)
          val color     = colorDest * Color.grayscale(255 - colorSource.a) + colorSource
          dest.unsafePutPixel(destX, destY, color)
        )
      case blendMode => // Custom BlendMode
        var dy = 0
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = blendMode.blend(source.unsafeGetPixel(dx + cx, srcY), dest.unsafeGetPixel(destX, destY))
            dest.unsafePutPixel(destX, destY, color)
            dx += 1
          }
          dy += 1
        }
    }
  }

  inline def unsafeBlitArrayLoop(
      dest: MutableSurface,
      source: Array[Color],
      lineSize: Int,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  )(inline putColor: (Int, Int, Color) => Unit): Unit = {
    var dy = 0
    while (dy < maxY) {
      val srcY  = dy + cy
      val destY = dy + y
      val base  = srcY * lineSize + cx
      var dx    = 0
      while (dx < maxX) {
        val destX = dx + x
        putColor(destX, destY, source(base + dx))
        dx += 1
      }
      dy += 1
    }
  }

  def unsafeBlitArray(
      dest: MutableSurface,
      source: Array[Color],
      lineSize: Int,
      blendMode: BlendMode,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    blendMode match {
      case BlendMode.Copy =>
        unsafeBlitArrayLoop(dest, source, lineSize, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.ColorMask(maskColor) =>
        unsafeBlitArrayLoop(dest, source, lineSize, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          if (color != maskColor) dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.AlphaTest(alpha) =>
        unsafeBlitArrayLoop(dest, source, lineSize, x, y, cx, cy, maxX, maxY)((destX, destY, color) =>
          if (color.a > alpha) dest.unsafePutPixel(destX, destY, color)
        )
      case BlendMode.AlphaAdd =>
        unsafeBlitArrayLoop(dest, source, lineSize, x, y, cx, cy, maxX, maxY)((destX, destY, colorSource) =>
          val colorDest = dest.unsafeGetPixel(destX, destY)
          val color     = colorDest * Color.grayscale(255 - colorSource.a) + colorSource
          dest.unsafePutPixel(destX, destY, color)
        )
      case blendMode => // Custom BlendMode
        var dy = 0
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val base  = srcY * lineSize + cx
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = blendMode.blend(source(base + dx), dest.unsafeGetPixel(destX, destY))
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
          case ramSurf: RamSurface =>
            unsafeBlitArray(dest, ramSurf.dataBuffer, ramSurf.width, blendMode, x, y, cx, cy, maxX, maxY)
          case SurfaceView.RamSurfaceView(surface, _cx, _cy, _, _) =>
            fullBlit(dest, surface, blendMode, x, y, cx + _cx, cy + _cy, cw, ch)
          case _ => unsafeBlitSurface(dest, source, blendMode, x, y, cx, cy, maxX, maxY)
        }

      }
    }
  }
}
