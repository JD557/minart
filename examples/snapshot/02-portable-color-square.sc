//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.5.4-SNAPSHOT"

/*
 * In this next example, we are going to do draw the same colored square, but now in a portable way.
 */

/*
 * We start with the same imports and canvas settings
 */
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.*

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

/*
 * Now, here's the different bit.
 *
 * The problem comes from writing a render loop.
 * Some platforms don't support thread sleep, or require some event loop to be polled.
 *
 * As such, Minart comes with some helpful AppLoop abstractions, which handle canvas
 * creation, destruction and the render loop itself.
 *
 * Here we will use the statelessRenderLoop operation with LoopFrequency.Never,
 * which will show a single image and then wait for the window to be closed.
 */

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
