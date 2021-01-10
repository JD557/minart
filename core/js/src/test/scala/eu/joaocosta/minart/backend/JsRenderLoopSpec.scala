package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object JsRenderLoopSpec extends RenderLoopTests {
  lazy val renderLoop: ImpureRenderLoop = JsRenderLoop

  singleFrameTest()
}
