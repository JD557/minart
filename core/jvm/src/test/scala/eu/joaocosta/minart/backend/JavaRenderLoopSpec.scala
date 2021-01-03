package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

class JavaRenderLoopSpec extends RenderLoopTests {
  lazy val renderLoop: ImpureRenderLoop = JavaRenderLoop
  lazy val renderLoopName: String = "JavaRenderLoop"
}
