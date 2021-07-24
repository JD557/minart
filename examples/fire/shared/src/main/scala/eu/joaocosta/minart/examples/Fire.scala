package eu.joaocosta.minart.examples

import scala.concurrent.duration._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

object Fire {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {

    var temperatureMod = 1.0

    def automata(backbuffer: Vector[Vector[Color]], x: Int, y: Int, w: Int): Color = {
      val neighbors =
        (math.max(0, x - 1) to math.min(x + 1, w - 1)).toList.map { xx =>
          backbuffer(y + 1)(xx)
        }
      val randomLoss  = 0.8 + (scala.util.Random.nextDouble() / 5)
      val temperature = ((neighbors.map(c => (c.r + c.g + c.b) / 3).sum / 3) * randomLoss).toInt
      Color(
        math.min(255, temperature * 1.6 * temperatureMod).toInt,
        (temperature * 0.8 * temperatureMod).toInt,
        (temperature * 0.6 * temperatureMod).toInt
      )
    }

    ImpureRenderLoop
      .infiniteRenderLoop(
        canvas => {
          val keys            = canvas.getKeyboardInput()
          val (width, height) = (canvas.settings.width, canvas.settings.height)
          if (keys.isDown(KeyboardInput.Key.Up)) temperatureMod = math.min(temperatureMod + 0.1, 1.0)
          else if (keys.isDown(KeyboardInput.Key.Down)) temperatureMod = math.max(0.1, temperatureMod - 0.1)
          // Add bottom fire root
          for {
            x <- (0 until width)
            y <- (height - 4 until height)
          } {
            canvas.putPixel(x, y, Color(255, 255, 255))
          }

          // Add middle fire root
          for {
            x <- (0 until width)
            y <- (0 until height)
            dist = math.pow(x - width / 2, 2) + math.pow(y - height / 2, 2)
          } {
            if (dist > 25 && dist <= 100) canvas.putPixel(x, y, Color(255, 255, 255))
          }

          // Evolve fire
          val backbuffer = canvas.getBackbuffer()
          for {
            x <- (0 until width)
            y <- (0 until (height - 1)).reverse
          } {
            val color = automata(backbuffer, x, y, canvas.settings.width)
            canvas.putPixel(x, y, color)
          }
          canvas.redraw()
        },
        FrameRate.fps60
      )(canvasSettings)
  }
}
