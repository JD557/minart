package eu.joaocosta.minart.examples

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.pure._
import eu.joaocosta.minart.pure.backend._

object PureColorSquare extends MinartApp {
  type State = Unit
  val renderLoop                   = PureRenderLoop.default()
  val canvasSettings               = Canvas.Settings(width = 128, height = 128, scale = 4)
  val canvasManager: CanvasManager = CanvasManager.default()
  val initialState: State          = ()
  val frameRate                    = FrameRate.Uncapped
  val terminateWhen                = (_: State) => false
  val renderFrame = (_: State) => {
    CanvasIO.getSettings
      .map {
        case Some(settings) =>
          for {
            x <- (0 until settings.width)
            y <- (0 until settings.height)
            r = (255 * x.toDouble / settings.width).toInt
            g = (255 * y.toDouble / settings.height).toInt
          } yield CanvasIO.putPixel(x, y, Color(r, g, 255))
        case None => List.empty
      }
      .flatMap(CanvasIO.sequence)
      .andThen(CanvasIO.redraw)
  }
}
