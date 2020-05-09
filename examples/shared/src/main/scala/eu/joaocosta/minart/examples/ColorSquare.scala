package eu.joaocosta.minart.examples

import eu.joaocosta.minart.core._

object ColorSquare {
  def runExample(canvas: LowLevelCanvas, renderLoop: RenderLoop) = {
    renderLoop.singleFrame(canvas, c => {
      for {
        x <- (0 until c.width)
        y <- (0 until c.height)
      } {
        val color = Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
        c.putPixel(x, y, color)
      }
      c.redraw()
    })
  }
}
