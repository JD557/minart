package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object NativePureColorSquare extends PureColorSquare {
  val renderLoop = SdlRenderLoop
  val canvasManager: CanvasManager = new SdlCanvas(128, 128, scale = 4)
}
