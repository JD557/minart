package eu.joaocosta.minart.examples

import eu.joaocosta.minart._
import eu.joaocosta.minart.pure._

trait PureColorSquare extends MinartApp {
  type State = Unit
  val initialState: State = ()
  val frameRate = FrameRate.Uncapped
  val terminateWhen = (_: State) => false
  val renderFrame = (_: State) => {
    val colors = for {
      w <- CanvasIO.getWidth
      h <- CanvasIO.getHeight
    } yield for {
      x <- (0 until w)
      y <- (0 until h)
      r = (255 * x.toDouble / w).toInt
      g = (255 * y.toDouble / h).toInt
      b = 255
    } yield (x, y, Color(r, g, b))
    colors.flatMap(c =>
      CanvasIO.forEach(c) { case (x, y, c) => CanvasIO.putPixel(x, y, c) }).andThen(CanvasIO.redraw)
  }
}
