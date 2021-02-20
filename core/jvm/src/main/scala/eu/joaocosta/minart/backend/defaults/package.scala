package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultRenderLoop: DefaultBackend[Any, JavaRenderLoop.type] =
    DefaultBackend.fromConstant(JavaRenderLoop)
}
