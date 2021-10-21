package eu.joaocosta.minart.examples

import eu.joaocosta.minart.graphics._

object ColorSquare {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .singleFrame(c => {
        for {
          x <- (0 until c.width)
          y <- (0 until c.height)
        } {
          val color =
            Color((255 * x.toDouble / c.width).toInt, (255 * y.toDouble / c.height).toInt, 255)
          c.putPixel(x, y, color)
        }
        c.redraw()
      })(canvasSettings)
  }
}
