//> using scala "3.1.2"
//> using lib "eu.joaocosta::minart:0.4.0-RC1"

/*
 * It is sometimes convenient to load images from external resources.
 * The minart-image library and graphics.image package allows one to do just that.
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.runtime._

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

/*
 * Notice the use of Resource here.
 * The Resource primitive is part of the runtime package, and it abstracts file access in a platform agnostic way.
 * It can access files from the file system, packed resources or from an URL, depending on the backend.
 */
val bitmap = Image.loadBmpImage(Resource("assets/scala.bmp")).get

ImpureRenderLoop
  .singleFrame(canvas => {
    canvas.blit(bitmap)(0, 0)
    canvas.redraw()
  })
  .run(canvasSettings)