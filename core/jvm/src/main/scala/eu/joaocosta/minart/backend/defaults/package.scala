package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)
}
