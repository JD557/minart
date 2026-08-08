package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  given defaultCanvas: DefaultBackend[Any, HtmlCanvas] =
    DefaultBackend.fromFunction((_) => new HtmlCanvas())

  given defaultLoopRunner: DefaultBackend[Any, JsLoopRunner.type] =
    DefaultBackend.fromConstant(JsLoopRunner)

  given defaultAudioPlayer: DefaultBackend[Any, JsAudioPlayer] =
    DefaultBackend.fromFunction((_) => new JsAudioPlayer())

  given defaultPlatform: DefaultBackend[Any, Platform.JS.type] =
    DefaultBackend.fromConstant(Platform.JS)

  given defaultResource: DefaultBackend[Option[String], JsResource] =
    DefaultBackend.fromFunction[Option[String], JsResource](JsResource.apply)
}
