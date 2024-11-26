package eu.joaocosta.minart.graphics

import scala.annotation.tailrec

/** Object with the internal blitting logic.
  */
private[graphics] object Blitter {

  def unsafeBlitSurfaceCopy(
      dest: MutableSurface,
      source: Surface,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitSurfaceColorMask(
      dest: MutableSurface,
      source: Surface,
      maskColor: Color,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitSurfaceAlphaTest(
      dest: MutableSurface,
      source: Surface,
      alpha: Int,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitSurfaceAlphaAdd(
      dest: MutableSurface,
      source: Surface,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    while (dy < maxY) {
      val srcY  = dy + cy
      val destY = dy + y
      var dx    = 0
      while (dx < maxX) {
        val destX       = dx + x
        val colorSource = source.unsafeGetPixel(dx + cx, srcY)
        val colorDest   = dest.unsafeGetPixel(destX, destY)
        val factor      = 255 - colorSource.a
        val color = Color(
          Math.min(Color.mulDiv255(colorDest.r, factor) + colorSource.r, 255),
          Math.min(Color.mulDiv255(colorDest.g, factor) + colorSource.g, 255),
          Math.min(Color.mulDiv255(colorDest.b, factor) + colorSource.b, 255)
        )
        dest.unsafePutPixel(destX, destY, color)
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
        unsafeBlitSurfaceCopy(dest, source, x, y, cx, cy, maxX, maxY)
      case BlendMode.ColorMask(maskColor) =>
        unsafeBlitSurfaceColorMask(dest, source, maskColor, x, y, cx, cy, maxX, maxY)
      case BlendMode.AlphaTest(alpha) =>
        unsafeBlitSurfaceAlphaTest(dest, source, alpha, x, y, cx, cy, maxX, maxY)
      case BlendMode.AlphaAdd =>
        unsafeBlitSurfaceAlphaAdd(dest, source, x, y, cx, cy, maxX, maxY)
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

  def unsafeBlitMatrixCopy(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitMatrixColorMask(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      maskColor: Color,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitMatrixAlphaTest(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      alpha: Int,
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
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
  }

  def unsafeBlitMatrixAlphaAdd(
      dest: MutableSurface,
      source: Vector[Array[Color]],
      x: Int,
      y: Int,
      cx: Int,
      cy: Int,
      maxX: Int,
      maxY: Int
  ): Unit = {
    var dy = 0
    while (dy < maxY) {
      val srcY  = dy + cy
      val destY = dy + y
      val line  = source(srcY)
      var dx    = 0
      while (dx < maxX) {
        val destX       = dx + x
        val colorSource = line(dx + cx)
        val colorDest   = dest.unsafeGetPixel(destX, destY)
        val factor      = 255 - colorSource.a
        val color = Color(
          Math.min(Color.mulDiv255(colorDest.r, factor) + colorSource.r, 255),
          Math.min(Color.mulDiv255(colorDest.g, factor) + colorSource.g, 255),
          Math.min(Color.mulDiv255(colorDest.b, factor) + colorSource.b, 255)
        )
        dest.unsafePutPixel(destX, destY, color)
        dx += 1
      }
      dy += 1
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
    blendMode match {
      case BlendMode.Copy =>
        unsafeBlitMatrixCopy(dest, source, x, y, cx, cy, maxX, maxY)
      case BlendMode.ColorMask(maskColor) =>
        unsafeBlitMatrixColorMask(dest, source, maskColor, x, y, cx, cy, maxX, maxY)
      case BlendMode.AlphaTest(alpha) =>
        unsafeBlitMatrixAlphaTest(dest, source, alpha, x, y, cx, cy, maxX, maxY)
      case BlendMode.AlphaAdd =>
        unsafeBlitMatrixAlphaAdd(dest, source, x, y, cx, cy, maxX, maxY)
      case blendMode => // Custom BlendMode
        var dy = 0
        while (dy < maxY) {
          val srcY  = dy + cy
          val destY = dy + y
          val line  = source(srcY)
          var dx    = 0
          while (dx < maxX) {
            val destX = dx + x
            val color = blendMode.blend(line(dx + cx), dest.unsafeGetPixel(destX, destY))
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
          case SurfaceView.RamSurfaceView(surface, _cx, _cy, _, _) =>
            fullBlit(dest, surface, blendMode, x, y, cx + _cx, cy + _cy, cw, ch)
          case _ => unsafeBlitSurface(dest, source, blendMode, x, y, cx, cy, maxX, maxY)
        }

      }
    }
  }
}
