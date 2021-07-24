package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.graphics._

object SdlRenderLoopSpec extends RenderLoopTests {
  lazy val loopRunner: LoopRunner = SdlLoopRunner

  loopTest()
}
