package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object NativePureColorSquare extends PureColorSquare {
  val renderLoop = SdlRenderLoop
  val canvasManager: CanvasManager = new SdlCanvas(128, 128, scale = 4)
}
