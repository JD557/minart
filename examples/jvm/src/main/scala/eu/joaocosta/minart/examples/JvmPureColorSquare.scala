package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object JvmPureColorSquare extends PureColorSquare {
  val renderLoop = JavaRenderLoop
  val canvasManager: CanvasManager = new AwtCanvas(128, 128, scale = 4)
}
