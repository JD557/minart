package $package$

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.pure._
import eu.joaocosta.minart.runtime._

object Main extends MinartApp[Unit, LowLevelCanvas] {
  val loopRunner      = LoopRunner()
  val createSubsystem = () => LowLevelCanvas.create()
  val canvasSettings  = Canvas.Settings(width = 128, height = 128, scale = 4)

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
      .configure(canvasSettings, LoopFrequency.Never)
}
