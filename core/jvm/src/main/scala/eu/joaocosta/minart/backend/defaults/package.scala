package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Canvas.Settings, AwtCanvas] =
    DefaultBackend.fromFunction((settings: Canvas.Settings) => new AwtCanvas(settings))

  implicit val defaultRenderLoop: DefaultBackend[Any, JavaRenderLoop.type] =
    DefaultBackend.fromConstant(JavaRenderLoop)
}

