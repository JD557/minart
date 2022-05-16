package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)

  implicit val defaultAudioPlayer: DefaultBackend[Any, JavaAudioPlayer.type] =
    DefaultBackend.fromConstant(JavaAudioPlayer)

  implicit val defaultPlatform: DefaultBackend[Any, Platform.JVM.type] =
    DefaultBackend.fromConstant(Platform.JVM)

  implicit val defaultResource: DefaultBackend[String, JavaResource] =
    DefaultBackend.fromFunction[String, JavaResource](JavaResource.apply)
}
