package eu.joaocosta.minart.graphics.image.qoi

import eu.joaocosta.minart.graphics.Color

private[qoi] final class QoiColor private (val argb: Int) {
  @inline def a: Int = ((argb & 0xff000000) >> 24) & 0x0ff
  @inline def r: Int = (argb & 0x00ff0000) >> 16
  @inline def g: Int = (argb & 0x0000ff00) >> 8
  @inline def b: Int = (argb & 0x000000ff)

  def hash        = (r * 3 + g * 5 + b * 7 + a * 11) % 64
  def minartColor = Color.fromARGB(argb)

  override def toString: String  = s"QoiColor($r,$g,$b)"
  override def hashCode(): Int   = hash
  override def equals(that: Any) =
    (that.isInstanceOf[QoiColor] && this.argb == that.asInstanceOf[QoiColor].argb)
}

private[qoi] object QoiColor {
  def apply(r: Int, g: Int, b: Int, a: Int): QoiColor =
    new QoiColor(((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255))

  def unapply(color: QoiColor): Some[(Int, Int, Int, Int)] =
    Some(color.r, color.g, color.b, color.a)

  def fromMinartColor(color: Color): QoiColor = new QoiColor(color.argb)
}
