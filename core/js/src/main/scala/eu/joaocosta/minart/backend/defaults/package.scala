package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime._

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, HtmlCanvas] =
    DefaultBackend.fromFunction((_) => new HtmlCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, JsLoopRunner.type] =
    DefaultBackend.fromConstant(JsLoopRunner)
}
