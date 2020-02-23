package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object ColorSquare {
  def runExample(canvas: LowLevelCanvas) = {
    canvas.init()
    for {
      x <- (0 until canvas.width)
      y <- (0 until canvas.height)
    } {
      val color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
      canvas.putPixel(x, y, color)
    }
    canvas.redraw()
  }
}
