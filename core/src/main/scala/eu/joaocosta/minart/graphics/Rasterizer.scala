package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.geometry.*

/** Object with the internal rasterization logic.
  */
private[graphics] object Rasterizer {
  def rasterizeShapeSingleFace(
      dest: MutableSurface,
      area: AxisAlignedBoundingBox,
      shape: Shape,
      color: Color,
      blendMode: BlendMode
  ): Unit = {
    blendMode match {
      case BlendMode.Copy | BlendMode.ColorMask(_) | BlendMode.AlphaTest(_) =>
        area.foreach { (x, y) =>
          if (shape.contains(x, y)) dest.unsafePutPixel(x, y, color)
        }
      case _ =>
        area.foreach { (x, y) =>
          if (shape.contains(x, y)) {
            val finalColor = blendMode.blend(color, dest.unsafeGetPixel(x, y))
            dest.unsafePutPixel(x, y, finalColor)
          }
        }
    }
  }

  def rasterizeShapeBothFaces(
      dest: MutableSurface,
      area: AxisAlignedBoundingBox,
      shape: Shape,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color],
      blendMode: BlendMode
  ): Unit = {
    blendMode match {
      case BlendMode.Copy | BlendMode.ColorMask(_) | BlendMode.AlphaTest(_) =>
        area.foreach { (x, y) =>
          shape
            .faceAt(x, y)
            .flatMap {
              case Shape.Face.Front => frontfaceColor
              case Shape.Face.Back  => backfaceColor
            }
            .foreach(color => dest.unsafePutPixel(x, y, color))
        }
      case _ =>
        area.foreach { (x, y) =>
          shape
            .faceAt(x, y)
            .flatMap {
              case Shape.Face.Front => frontfaceColor
              case Shape.Face.Back  => backfaceColor
            }
            .foreach { colorSource =>
              val color = blendMode.blend(colorSource, dest.unsafeGetPixel(x, y))
              dest.unsafePutPixel(x, y, color)
            }
        }
    }
  }

  def rasterizeShape(
      dest: MutableSurface,
      shape: Shape,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color],
      blendMode: BlendMode
  ): Unit = {
    val area = AxisAlignedBoundingBox(0, 0, dest.width, dest.height).intersect(shape.aabb)
    if (!area.isEmpty) {
      val (finalFrontfaceColor, finalBackfaceColor) = blendMode match {
        case BlendMode.ColorMask(mask) =>
          (frontfaceColor.filter(_ != mask), backfaceColor.filter(_ != mask))
        case BlendMode.AlphaTest(alpha) =>
          (frontfaceColor.filter(_.a > alpha), backfaceColor.filter(_.a > alpha))
        case _ =>
          (frontfaceColor, backfaceColor)
      }
      if (finalFrontfaceColor.isDefined || finalBackfaceColor.isDefined)
        shape.knownFace match {
          case Some(Shape.Face.Front) if finalFrontfaceColor.isDefined =>
            rasterizeShapeSingleFace(dest, area, shape, finalFrontfaceColor.get, blendMode)
          case Some(Shape.Face.Back) if finalBackfaceColor.isDefined =>
            rasterizeShapeSingleFace(dest, area, shape, finalBackfaceColor.get, blendMode)
          case None if finalFrontfaceColor.isDefined || finalBackfaceColor.isDefined =>
            rasterizeShapeBothFaces(dest, area, shape, finalFrontfaceColor, finalBackfaceColor, blendMode)
          case _ => // Do nothing, there's nothing to draw
        }
    }
  }

  // From https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
  def rasterizeLine(dest: MutableSurface, stroke: Stroke.Line, color: Color): Unit = {
    val x1 = math.round(stroke.p1.x).toInt
    val y1 = math.round(stroke.p1.y).toInt
    val x2 = math.round(stroke.p2.x).toInt
    val y2 = math.round(stroke.p2.y).toInt

    val dx = math.abs(x2 - x1)
    val sx = if (x1 < x2) 1 else -1

    val dy = -math.abs(y2 - y1)
    val sy = if (y1 < y2) 1 else -1

    var x     = x1
    var y     = y1
    var error = dx + dy

    while (!(x == x2 && y == y2)) {
      dest.putPixel(x, y, color)
      val doubleError = 2 * error
      if (doubleError >= dy && x != x2) {
        error = error + dy
        x = x + sx
      }
      if (doubleError <= dx && y != y2) {
        error = error + dx
        y = y + sy
      }
    }
    dest.putPixel(x, y, color)
  }

  // From https://en.wikipedia.org/wiki/Midpoint_circle_algorithm
  def rasterizeCircle(dest: MutableSurface, stroke: Stroke.Circle, color: Color): Unit = {
    val cx = math.round(stroke.center.x).toInt
    val cy = math.round(stroke.center.y).toInt

    var t  = math.round(stroke.radius / 16).toInt
    var dx = math.round(stroke.radius).toInt
    var dy = 0

    while (dx >= dy) {
      dest.putPixel(cx + dx, cy + dy, color)
      dest.putPixel(cx + dx, cy - dy, color)
      dest.putPixel(cx - dx, cy + dy, color)
      dest.putPixel(cx - dx, cy - dy, color)

      dest.putPixel(cx + dy, cy + dx, color)
      dest.putPixel(cx + dy, cy - dx, color)
      dest.putPixel(cx - dy, cy + dx, color)
      dest.putPixel(cx - dy, cy - dx, color)

      dy = dy + 1
      t = t + dy
      if (t - dx >= 0) {
        t = t - dx
        dx = dx - 1
      }
    }
  }

  def rasterizeStroke(dest: MutableSurface, stroke: Stroke, color: Color, dx: Int, dy: Int): Unit = stroke match {
    case Stroke.Line(Point(x1, y1), Point(x2, y2)) =>
      rasterizeLine(dest, Stroke.Line(Point(x1 + dx, y1 + dy), Point(x2 + dx, y2 + dy)), color)
    case Stroke.Circle(Point(cx, cy), radius) =>
      rasterizeCircle(dest, Stroke.Circle(Point(cx + dx, cy + dy), radius), color)
  }

}
