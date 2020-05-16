package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Canvas.Settings, SdlCanvas] =
    DefaultBackend.fromFunction((settings: Canvas.Settings) => new SdlCanvas(settings))

  implicit val defaultRenderLoop: DefaultBackend[Any, SdlRenderLoop.type] =
    DefaultBackend.fromConstant(SdlRenderLoop)
}

