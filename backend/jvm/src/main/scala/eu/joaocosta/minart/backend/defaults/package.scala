package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit lazy val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit lazy val defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)

  implicit lazy val defaultAudioPlayer: DefaultBackend[Any, JavaAudioPlayer] =
    DefaultBackend.fromFunction((_) => new JavaAudioPlayer())

  implicit lazy val defaultPlatform: DefaultBackend[Any, Platform.JVM.type] =
    DefaultBackend.fromConstant(Platform.JVM)

  implicit lazy val defaultResource: DefaultBackend[String, JavaResource] =
    DefaultBackend.fromFunction[String, JavaResource](JavaResource.apply)
}
