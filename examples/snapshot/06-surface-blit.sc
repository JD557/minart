//> using scala "3.1.2"
//> using lib "eu.joaocosta::minart:0.4.1-SNAPSHOT"

/*
 * Writing directly to a canvas pixel by pixel worked fine in the previous examples, but
 * sometimes it's helpful to store some full images to draw.
 * This is where surfaces come in.
 */

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

/*
 * We start by allocating a surface in RAM.
 */
val image = new RamSurface(
  (0 until 16).map { y =>
    (0 until 16).map { x =>
      if ((x + y) < 16) Color(0, 0, 0)
      else Color((16 * x.toDouble).toInt, (16 * y.toDouble).toInt, 255)
    }
  }
)

/*
 * Then we create our background as a RAM surface.
 * Note that mutable surfaces also support the putPixel operation, like the canvas.
 * Actually, a Canvas extends MutableSurface.
 *
 * Also, by returning a Surface instead of MutableSurface, we make sure that no one else can mutate it.
 */
val background: Surface = {
  val surface = new RamSurface(Vector.fill(canvasSettings.height)(Array.fill(canvasSettings.width)(Color(0, 0, 0))))
  for {
    x <- (0 until surface.width)
    y <- (0 until surface.height)
  } {
    val color =
      Color((255 * x.toDouble / surface.width).toInt, (255 * y.toDouble / surface.height).toInt, 255)
    surface.putPixel(x, y, color)
  }
  surface
}

ImpureRenderLoop
  .statefulRenderLoop[Int](
    (canvas, state) => {
      /*
       * Two surfaces can be combined with the blit operation.
       * Here, we draw the background on the canvas, at position (0, 0)
       */
      canvas.blit(background)(0, 0)
      /*
       * The blit operation also supports a clipping rect, which allows us to only draw a part of the image.
       * Here, we draw our image on the canvas, at position (state, state).
       * Instead of drawing the whole image, however, we draw just the rectangle (4, 4, 12, 12).
       */
      canvas.blit(image)(state, state, 4, 4, 8, 8)
      /*
       * Finally, the blit operation supports an optional color to act as a mask.
       * Pixels with this color are ignored, working as a transparent.
       */
      canvas.blit(image, Some(Color(0, 0, 0)))((128 - 16 - 1) - state, state, 4, 4, 8, 8)
      canvas.redraw()
      (state + 1) % (128 - 16)
    },
    LoopFrequency.hz60
  )
  .run(canvasSettings, 0)
