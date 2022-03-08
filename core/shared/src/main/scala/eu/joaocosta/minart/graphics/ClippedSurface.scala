package eu.joaocosta.minart.graphics

/** A view over a surface, representing a clipped version of it.
  */
class ClippedSurface(inner: Surface, cx: Int, cy: Int, cw: Int, ch: Int) extends Surface {
  def width: Int  = math.min(cw, inner.width - cx)
  def height: Int = math.min(ch, inner.height - cy)
  def getPixel(x: Int, y: Int): Option[Color] =
    inner.getPixel(cx + x, cy + y)
  def getPixels(): Vector[Array[Color]] =
    inner.getPixels().slice(cy, cy + ch).map(_.slice(cx, cx + cw))
}
