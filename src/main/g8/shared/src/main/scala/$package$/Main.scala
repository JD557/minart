package $package$

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object Main {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

  def main(args: Array[String]): Unit = {
    AppLoop
      .statelessRenderLoop((canvas: Canvas) => {
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
      .configure(canvasSettings, LoopFrequency.Never)
      .run()
  }
}
