package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Canvas.Settings, HtmlCanvas] =
    DefaultBackend.fromFunction((settings: Canvas.Settings) => new HtmlCanvas(settings))

  implicit val defaultRenderLoop: DefaultBackend[Any, JsRenderLoop.type] =
    DefaultBackend.fromConstant(JsRenderLoop)
}
