package eu.joaocosta.minart.backend

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultRenderLoop: DefaultBackend[Any, JavaRenderLoop.type] =
    DefaultBackend.fromConstant(JavaRenderLoop)
}
