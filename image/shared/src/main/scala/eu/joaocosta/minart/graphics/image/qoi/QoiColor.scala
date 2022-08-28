package eu.joaocosta.minart.graphics.image.qoi

import eu.joaocosta.minart.graphics.Color

private[qoi] final case class QoiColor(r: Int, g: Int, b: Int, a: Int) {
  def toMinartColor = Color(r, g, b)
  def hash          = (r * 3 + g * 5 + b * 7 + a * 11) % 64
}

private[qoi] object QoiColor {
  def fromMinartColor(color: Color): QoiColor =
    QoiColor(color.r, color.g, color.b, 255)
}
