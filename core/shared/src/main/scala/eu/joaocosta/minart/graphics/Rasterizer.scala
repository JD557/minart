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

  private def rasterizeShapeBothFaces(
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

}
