package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit lazy val defaultCanvas: DefaultBackend[Any, HtmlCanvas] =
    DefaultBackend.fromFunction((_) => new HtmlCanvas())

  implicit lazy val defaultLoopRunner: DefaultBackend[Any, JsLoopRunner.type] =
    DefaultBackend.fromConstant(JsLoopRunner)

  implicit lazy val defaultAudioPlayer: DefaultBackend[Any, JsAudioPlayer] =
    DefaultBackend.fromFunction((_) => new JsAudioPlayer())

  implicit lazy val defaultPlatform: DefaultBackend[Any, Platform.JS.type] =
    DefaultBackend.fromConstant(Platform.JS)

  implicit lazy val defaultResource: DefaultBackend[String, JsResource] =
    DefaultBackend.fromFunction[String, JsResource](JsResource.apply)
}
