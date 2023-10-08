//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.5.4-SNAPSHOT"

/*
 * It is sometimes convenient to load images from external resources.
 * The minart-image library and graphics.image package allows one to do just that.
 */
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.runtime.*

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = Some(4))

/*
 * Notice the use of Resource here.
 * The Resource primitive is part of the runtime package, and it abstracts file access in a platform agnostic way.
 * It can access files from the file system, packed resources or from an URL, depending on the backend.
 */
val bitmap = Image.loadBmpImage(Resource("assets/scala.bmp")).get

AppLoop
  .statelessRenderLoop((canvas: Canvas) => {
    canvas.blit(bitmap)(0, 0)
    canvas.redraw()
  })
  .configure(canvasSettings, LoopFrequency.Never)
  .run()
