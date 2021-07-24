package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core._

object ColorSquare {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .singleFrame(c => {
        for {
          x <- (0 until c.settings.width)
          y <- (0 until c.settings.height)
        } {
          val color =
            Color((255 * x.toDouble / c.settings.width).toInt, (255 * y.toDouble / c.settings.height).toInt, 255)
          c.putPixel(x, y, color)
        }
        c.redraw()
      })(canvasSettings)
  }
}
