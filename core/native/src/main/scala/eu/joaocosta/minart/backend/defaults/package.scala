package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, SdlCanvas] =
    DefaultBackend.fromFunction((_) => new SdlCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, SdlLoopRunner.type] =
    DefaultBackend.fromConstant(SdlLoopRunner)
}
