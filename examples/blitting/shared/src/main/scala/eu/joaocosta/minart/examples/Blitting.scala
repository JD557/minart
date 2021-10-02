package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object Blitting {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  val image = new RamSurface(
    (0 until 16).map { y =>
      (0 until 16).map { x =>
        if ((x + y) < 16) Color(0, 0, 0).argb
        else Color((16 * x.toDouble).toInt, (16 * y.toDouble).toInt, 255).argb
      }.toArray
    }.toVector
  )

  def renderBackground(canvas: Canvas): Unit =
    for {
      x <- (0 until canvas.width)
      y <- (0 until canvas.height)
    } {
      val color =
        Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
      canvas.putPixel(x, y, color)
    }

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .infiniteRenderLoop[Int](
        (canvas, state) => {
          renderBackground(canvas)
          canvas.blit(image)(state, state, 4, 4, 8, 8)
          canvas.blitWithMask(image, Color(0, 0, 0))((128 - 16 - 1) - state, state, 4, 4, 8, 8)
          canvas.redraw()
          (state + 1) % (128 - 16)
        },
        LoopFrequency.hz60
      )(canvasSettings, 0)
  }
}
