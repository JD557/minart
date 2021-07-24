package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.graphics._

object JsRenderLoopSpec extends RenderLoopTests {
  lazy val loopRunner: LoopRunner = JsLoopRunner

  singleFrameTest()
}
