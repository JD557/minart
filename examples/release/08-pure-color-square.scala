//> using scala "3.1.3"
//> using lib "eu.joaocosta::minart::0.4.1"

/*
 * Some people might prefer to code using a "programs as values" approach.
 * The minart-pure library and pure packages come with their own RIO abstraction, which allows you to do just that.
 */

/*
 * When importing the packages, note the new pure suffix.
 * The eu.joaocosta.minart.graphics.pure adds SurfaceIO/MSurfaceIO/CanvasIO helpers, which help when working with
 * RIO[Surface, _], RIO[MutableSurface, _] and RIO[Canvas, _].
 * The eu.joaocosta.minart.runtime.pure package adds the MinartApp helper.
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.pure._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.pure._

/*
 * The MinartApp helper allows you to create a pure app.
 * To do so, you need to fill the required variables
 */
object PureColorSquare extends MinartApp {
  /* The LoopRunner and CanvasManager are low level primitives from the backend.
   * It is recommended to just use the default values here
   */
  val loopRunner    = LoopRunner()
  val canvasManager = CanvasManager()

  /*
   * The state must always be defined.
   * In this case, we'll just set it to unit, as we don't care about the state
   */
  type State = Unit
  val initialState  = ()
  val terminateWhen = (_: State) => false

  // Then we define the canvas settings and frame rate
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)
  val frameRate      = LoopFrequency.Uncapped

  /*
   * And finally, the renderFrame is a function State => CanvasIO[State].
   * Note that the CanvasIO object provides helper methods with all the usual canvas operations, such as putPixel.
   */
  val renderFrame = (_: State) => {
    CanvasIO.getSettings
      .map { settings =>
        for {
          x <- (0 until settings.width)
          y <- (0 until settings.height)
          r = (255 * x.toDouble / settings.width).toInt
          g = (255 * y.toDouble / settings.height).toInt
        } yield CanvasIO.putPixel(x, y, Color(r, g, 255))
      }
      .flatMap(CanvasIO.sequence)
      .andThen(CanvasIO.redraw)
  }
}
