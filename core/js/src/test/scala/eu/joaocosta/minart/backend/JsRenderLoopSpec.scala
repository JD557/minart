package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object JsRenderLoopSpec extends RenderLoopTests {
  lazy val loopRunner: LoopRunner = JsLoopRunner

  singleFrameTest()
}
