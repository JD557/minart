package $package$

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._

object Main {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .singleFrame(canvas => {
        for {
          x <- (0 until canvas.width)
          y <- (0 until canvas.height)
        } {
          val color =
            Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
          canvas.putPixel(x, y, color)
        }
        canvas.redraw()
      })
      .run(canvasSettings)
  }
}
