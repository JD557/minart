//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.5.0"

/*
 * In the previous examples we just drew a static image on the screen.
 *
 * This time, we'll write an animated fire!
 */

/*
 * As before, let's import the backend and graphics and set our canvas settings.
 *
 * This time we also need to import the runtime. This package contains some helpful methods and objects to handle
 * render loops, such as the LoopFrequency.
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

/*
 * This is just the function to define the fire animation, you can skip over this step.
 */
def automata(backbuffer: Vector[Array[Color]], x: Int, y: Int): Color = {
  // For each pixel, we fetch the colors 3 pixels below (SW, S, SE)
  val neighbors =
    (math.max(0, x - 1) to math.min(x + 1, canvasSettings.width - 1)).toList.map { xx =>
      backbuffer(y + 1)(xx)
    }
  // We compute some random loss
  val randomLoss = 0.8 + (scala.util.Random.nextDouble() / 5)

  // The pixel temperature will be the average of the neighbors intensity, with the random loss applied
  val temperature = ((neighbors.map(c => (c.r + c.g + c.b) / 3).sum / 3) * randomLoss).toInt

  // Then we generate a nice yelow-red tint based on the temperature
  Color(
    math.min(255, temperature * 1.6).toInt,
    (temperature * 0.8).toInt,
    (temperature * 0.6).toInt
  )
}

/*
 * Now, here's the important part.
 * Instead of using the LoopFrequency.Never, we now use the LoopFrequency.hz60.
 * This will run our rendering function on each frame, with a specified delay.
 */
AppLoop
  .statelessRenderLoop((canvas: Canvas) => {

    // We set some pixels to always be white, so that the fire keeps growing from there
    // Add bottom fire root
    for {
      x <- (0 until canvas.width)
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

    /** The getPixels method returns all pixels from the canvas.
      *  We'll pass this to our automata
      */
    val backbuffer = canvas.getPixels()
    for {
      x <- (0 until canvas.width)
      y <- (0 until (canvas.height - 1)).reverse
    } {
      val color = automata(backbuffer, x, y)
      canvas.putPixel(x, y, color)
    }
    canvas.redraw()
  })
  .configure(canvasSettings, LoopFrequency.hz60) // Try to run at 60 FPS
  .run()
