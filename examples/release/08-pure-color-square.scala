//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.5.0-RC1"

/*
 * Some people might prefer to code using a "programs as values" approach.
 * The minart-pure library and pure packages come with their own RIO abstraction, which allows you to do just that.
 */

/*
 * When importing the packages, note the new pure suffix.
 * The eu.joaocosta.minart.graphics.pure adds SurfaceIO/MSurfaceIO/CanvasIO helpers, which help when working with
 * RIO[Surface, _], RIO[MutableSurface, _] and RIO[Canvas, _].
 * The eu.joaocosta.minart.runtime package adds the MinartApp helper, which can be used for both pure and impure apps.
 */
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.pure._
import eu.joaocosta.minart.runtime._

/*
 * The MinartApp helper allows you to create a pure app.
 * To do so, you need to fill the required variables
 *
 * The type arguments are the state and the subsystem that needs to be initialized.
 * In this example we won't have any state and will only use a Canvas.
 */
object PureColorSquare extends MinartApp[Unit, LowLevelCanvas] {
  /* The LoopRunner and LowLevelCanvas are low level primitives from the backend.
   * It is recommended to just use the default values here
   */
  val loopRunner      = LoopRunner()
  val createSubsystem = () => LowLevelCanvas.create()

  /** Here we define our app loop, just as we did previously.
    *  However, instead of a function, we will use a CanvasIO.
    *  If we wanted a statefull loop, we would use a state => CanvasIO function.
    *
    *  Also, note that we don't call run() in the end.
    */
  val appLoop =
    AppLoop
      .statelessRenderLoop(
        CanvasIO.canvasSettings
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
      )
      .configure(Canvas.Settings(width = 128, height = 128, scale = 4), LoopFrequency.Never)
}
