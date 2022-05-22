//> using scala "3.1.2"
//> using lib "eu.joaocosta::minart::0.4.0"

/*
 * Welcome to the minart tutorial!
 *
 * In this example, we are going to show how to draw some colors on the screen.
 *
 * Note that this first example will only work on the JVM. Don't worry, soon we will cover cross-compilation.
 */

/*
 * First, the imports.
 *
 * The eu.joaocosta.minart.backend.defaults package contains the implicits with the backend-specific (JVM/JS/Native)
 * logic. If for some reason you forget to import this, don't worry, you should get a helpful warning message to
 * remind you.
 *
 * Since we want to work with graphics, we also need to import eu.joaocosta.minart.graphics._
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._

/*
 * The Canvas.Settings are the settings of our window.
 * Those will be covered in more detail in a future example. Right now, you can see that this will create a 512x512
 * window with a 128x128 canvas (each pixel in the canvas will be a 4x4 square on the screen).
 */
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

/*
 * Then we need to create our Canvas.
 * This is quite a low-level way to do it, but it can be helpful to understand how things work.
 */

val canvas = LowLevelCanvas.create()
canvas.init(canvasSettings)

/*
 * Here comes the drawing part.
 *
 * For each pixel on the screen, we will write a different color based on the x and y position.
 * We can write colors with the helpful putPixel operation.
 */
for {
  x <- (0 until canvas.width)
  y <- (0 until canvas.height)
} {
  val color =
    Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
  canvas.putPixel(x, y, color)
}

/*
 * Now, this alone won't draw to the screen, we need to call redraw to update what's shown
 */

canvas.redraw()

/*
 * Finally, we close the canvas.
 * Let's close it after waiting a few seconds.
 */

Thread.sleep(5000)
canvas.close()
