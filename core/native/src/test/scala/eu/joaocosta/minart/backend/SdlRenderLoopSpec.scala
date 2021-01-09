package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object SdlRenderLoopSpec extends RenderLoopTests {
  lazy val renderLoop: ImpureRenderLoop = SdlRenderLoop

  loopTest()
}
