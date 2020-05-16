package eu.joaocosta.minart.examples

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.pure._

object PureColorSquare extends MinartApp {
  type State = Unit
  val renderLoop = RenderLoop.default()
  val canvasSettings = Canvas.Settings(
    width = 128,
    height = 128,
    scale = 4)
  val canvasManager: CanvasManager = CanvasManager.default(canvasSettings)
  val initialState: State = ()
  val frameRate = FrameRate.Uncapped
  val terminateWhen = (_: State) => false
  val renderFrame = (_: State) => {
    val colors = for {
      settings <- CanvasIO.getSettings
    } yield for {
      x <- (0 until settings.width)
      y <- (0 until settings.height)
      r = (255 * x.toDouble / settings.width).toInt
      g = (255 * y.toDouble / settings.height).toInt
      b = 255
    } yield (x, y, Color(r, g, b))
    colors.flatMap(c =>
      CanvasIO.forEach(c) { case (x, y, c) => CanvasIO.putPixel(x, y, c) }).andThen(CanvasIO.redraw)
  }
}
