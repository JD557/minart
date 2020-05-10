package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

class JavaRenderLoopSpec extends RenderLoopTests {
  lazy val renderLoop: RenderLoop = JavaRenderLoop
  lazy val renderLoopName: String = "JavaRenderLoop"
}
