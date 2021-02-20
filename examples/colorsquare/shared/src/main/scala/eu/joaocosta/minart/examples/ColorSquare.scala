package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core._

object ColorSquare {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    RenderLoop
      .default()
      .singleFrame(
        CanvasManager.default(),
        canvasSettings,
        c => {
          val (width, height) = c.settings.map(s => (s.width, s.height)).getOrElse(0, 0)
          for {
            x <- (0 until width)
            y <- (0 until height)
          } {
            val color =
              Color((255 * x.toDouble / width).toInt, (255 * y.toDouble / height).toInt, 255)
            c.putPixel(x, y, color)
          }
          c.redraw()
        }
      )
  }
}
