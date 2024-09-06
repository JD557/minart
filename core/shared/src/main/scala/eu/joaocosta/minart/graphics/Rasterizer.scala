package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.geometry.{AxisAlignedBoundingBox, ConvexPolygon}

/** Object with the internal rasterization logic.
  */
private[graphics] object Rasterizer {
  def rasterizePolygon(
      dest: MutableSurface,
      polygon: ConvexPolygon,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color],
      blendMode: BlendMode
  ): Unit = {
    val area = polygon.aabb & AxisAlignedBoundingBox(0, 0, dest.width, dest.height)
    val (finalFrontfaceColor, finalBackfaceColor) = blendMode match {
      case BlendMode.ColorMask(mask) =>
        (frontfaceColor.filter(_ != mask), backfaceColor.filter(_ != mask))
      case BlendMode.AlphaTest(alpha) =>
        (frontfaceColor.filter(_.a > alpha), backfaceColor.filter(_.a > alpha))
      case _ =>
        (frontfaceColor, backfaceColor)
    }
    blendMode match {
      case BlendMode.Copy | BlendMode.ColorMask(_) | BlendMode.AlphaTest(_) =>
        area.foreach { (x, y) =>
          (polygon.contains(x, y) match {
            case Some(ConvexPolygon.Face.Front) => finalFrontfaceColor
            case Some(ConvexPolygon.Face.Back)  => finalBackfaceColor
            case _                              => None
          }).foreach(color => dest.unsafePutPixel(x, y, color))
        }
      case _ =>
        area.foreach { (x, y) =>
          (polygon.contains(x, y) match {
            case Some(ConvexPolygon.Face.Front) => finalFrontfaceColor
            case Some(ConvexPolygon.Face.Back)  => finalBackfaceColor
            case _                              => None
          }).foreach { colorSource =>
            val color = blendMode.blend(colorSource, dest.unsafeGetPixel(x, y))
            dest.unsafePutPixel(x, y, color)
          }
        }
    }
  }
}
