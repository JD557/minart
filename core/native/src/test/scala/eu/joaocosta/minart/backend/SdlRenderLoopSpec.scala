package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

class SdlRenderLoopSpec extends RenderLoopTests {
  lazy val renderLoop: RenderLoop = SdlRenderLoop
  lazy val renderLoopName: String = "SdlRenderLoop"
}
