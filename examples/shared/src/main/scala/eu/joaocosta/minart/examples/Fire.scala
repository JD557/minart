package eu.joaocosta.minart.examples

import scala.concurrent.duration._

import eu.joaocosta.minart._

object Fire {

  def runExample(canvas: LowLevelCanvas, renderLoop: RenderLoop) = {

    canvas.init()
    // Fill everything with black
    for {
      x <- (0 until canvas.width)
      y <- (0 until canvas.height)
    } {
      val color = Color(0, 0, 255)
      canvas.putPixel(x, y, color)
    }
    canvas.redraw()

    def automata(x: Int, y: Int): Color = {
      val neighbors =
        (math.max(0, x - 1) to math.min(x + 1, canvas.width - 1)).toList.map { xx =>
          canvas.getBackbufferPixel(xx, y + 1)
        }
      val randomLoss = 0.8 + (scala.util.Random.nextDouble() / 5)
      val temperature = ((neighbors.map(c => (c.r + c.g + c.b) / 3).sum / 3) * randomLoss).toInt
      Color(math.min(255, temperature * 1.6).toInt, (temperature * 0.8).toInt, (temperature * 0.6).toInt)
    }

    renderLoop.infiniteRenderLoop(canvas, safeCanvas => {
      // Add bottom fire root
      for {
        x <- (0 until safeCanvas.width)
        y <- (canvas.height - 4 until canvas.height)
      } {
        canvas.putPixel(x, y, Color(255, 255, 255))
      }

      // Add middle fire root
      for {
        x <- (0 until canvas.width)
        y <- (0 until canvas.height)
        dist = math.pow(x - canvas.width / 2, 2) + math.pow(y - canvas.height / 2, 2)
      } {
        if (dist > 25 && dist <= 100) canvas.putPixel(x, y, Color(255, 255, 255))
      }

      // Evolve fire
      for {
        x <- (0 until canvas.width)
        y <- (0 until (canvas.height - 1)).reverse
      } {
        val color = automata(x, y)
        canvas.putPixel(x, y, color)
      }
      canvas.redraw()
    }, FrameRate.fps60)
  }
}
