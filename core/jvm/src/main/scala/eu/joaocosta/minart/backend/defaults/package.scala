package eu.joaocosta.minart.backend

import eu.joaocosta.minart.backend._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)
}
