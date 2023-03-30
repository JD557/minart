//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.5.0-SNAPSHOT"

/*
 * It can be quite cumbersome an ineficient to apply multiple transformations to a surface if we just use the getPixel
 * and putPixel operations.
 *
 * Surface Views and Planes are a way to manipulate surfaces with operations like map, contramap, etc without allocating
 * intermediate results.
 * This tutorial will show how to use those
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.runtime._

// First, let's load our example scala logo image
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4, clearColor = Color(0, 0, 0))
val bitmap         = Image.loadBmpImage(Resource("assets/scala.bmp")).get

/*
 * Now, let's manipulate our image a bit
 */

val updatedBitmap = bitmap.view
  .map(color => if (color == Color(255, 255, 255)) Color(0, 0, 0) else color) // change background color
  .clip(14, 0, 100, 128)                                                      // clip the image
  .toRamSurface()                                                             // convert it back to a RamSurface

/*
 * Note that in the above example we convert the image back to a RAM surface.
 * It is also possible to blit surface views, but that will apply the transformations everytime you draw the image.
 * That can be advantageous if the image changes on each frame or if most of the image will be off-canvas.
 * Since the image here is pretty small and static here, it's preferable to just allocate a new surface.
 */

/*
 * Let's now talk about planes.
 * A plane can be seen as an unlimited surface view (basically, a (Int, Int) => Color).
 * This allows us to contramap surface views and handling infinite images-
 * Let's see an example of the classic rotozoom effect.
 */
AppLoop
  .statefulRenderLoop((t: Double) =>
    (canvas: Canvas) => {
      val frameSin = math.sin(t)
      val frameCos = math.cos(t)
      val zoom     = 1.0 / (frameSin + 2.0)

      val image = Plane
        .fromSurfaceWithRepetition(updatedBitmap)                         // Create an inifinitePlane from our surface
        .scale(zoom, zoom)                                                // scale
        .rotate(t)                                                        // rotate
        .contramap((x, y) => (x + (5 * math.sin(t + y / 10.0)).toInt, y)) // Wobbly effect
        .flatMap(color =>
          (x, y) => // Add a crazy checkerboard effect
            if (x % 32 < 16 != y % 32 < 16) color.invert
            else color
        )
        .clip(0, 0, 128, 128) // Clip into a SurfaceView

      // Draw the image. Note that image is a SurfaceView, we don't need to convert it into a RamSurface.
      canvas.blit(image)(0, 0)
      canvas.redraw()
      t + 0.01
    }
  )
  .configure(canvasSettings, LoopFrequency.hz60, 0)
  .run()
