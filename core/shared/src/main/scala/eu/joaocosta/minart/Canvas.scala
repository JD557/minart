package eu.joaocosta.minart

trait Canvas {
  def width: Int
  def height: Int
  def scale: Int
  def clearColor: Color

  lazy val scaledWidth = width * scale
  lazy val scaledHeight = height * scale

  def putPixel(x: Int, y: Int, color: Color): Unit
  def getBackbufferPixel(x: Int, y: Int): Color
  def clear(): Unit
  def redraw(): Unit
}
