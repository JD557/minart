//> using scala "3.1.1"
//> using lib "eu.joaocosta::minart:0.4.0-SNAPSHOT"

/*
 * Now that we learned the basics of animation, we can start to look at more dynamic applications.
 *
 * What we need now is to handle some input. Let's see how that's done.
 */

/*
 * First our imports. Note the addition of eu.joaocosta.minart.input.
 * We'll need this to handle our inputs, such as keyboard and mouse.
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime._

/**
 * Note the adition of clearColor here.
 * This is the color of the canvas when nothing is drawn.
 */
val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4, clearColor = Color(255, 255, 255))

ImpureRenderLoop
  .statelessRenderLoop(
    canvas => {
      /*
       * Notice that now we are calling canvas.clear() at the beginning of each frame.
       * This not only clears the image with the clear color, but also clears some information regarding the input,
       * such as which keys and buttons were pressed on the previous frame.
       */
      canvas.clear()

      /*
       * Then we fetch the mouse with getPointerInput().
       */
      val mouse = canvas.getPointerInput()

      /*
       * Then we decide which color we will use for the pointer.
       * Red if it's pressed, black if it's not.
       */
      val mouseColor =
        if (mouse.isPressed) Color(255, 0, 0)
        else Color(0, 0, 0)

      /*
       * Then we draw a pixel on the mouse position.
       */
      mouse.position.foreach(pos => canvas.putPixel(pos.x, pos.y, mouseColor))

      /*
       * Then we call redraw(). Note that here we call clear() first and then redraw().
       * This reduces frame latency, but increases frame jitter. In some situations, you might prefer to run the redraw
       * at the beginning of the function.
       */
      canvas.redraw()
    },
    LoopFrequency.Uncapped // As fast as possible
  ).run(canvasSettings)
