package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.graphics._

object JavaRenderLoopSpec extends RenderLoopTests {
  lazy val loopRunner: LoopRunner = JavaLoopRunner

  singleFrameTest()
  loopTest()
}
