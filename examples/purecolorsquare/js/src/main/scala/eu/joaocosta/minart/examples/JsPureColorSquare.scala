package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object JsPureColorSquare extends PureColorSquare {
  val renderLoop = JsRenderLoop
  val canvasManager: CanvasManager = new HtmlCanvas(128, 128, scale = 4)
}
