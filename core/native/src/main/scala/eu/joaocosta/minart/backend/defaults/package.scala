package eu.joaocosta.minart.backend

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, SdlCanvas] =
    DefaultBackend.fromFunction((_) => new SdlCanvas())

  implicit val defaultRenderLoop: DefaultBackend[Any, SdlRenderLoop.type] =
    DefaultBackend.fromConstant(SdlRenderLoop)
}
