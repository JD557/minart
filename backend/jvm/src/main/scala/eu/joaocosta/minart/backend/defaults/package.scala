package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  given defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  given defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)

  given defaultAudioPlayer: DefaultBackend[Any, JavaAudioPlayer] =
    DefaultBackend.fromFunction((_) => new JavaAudioPlayer())

  given defaultPlatform: DefaultBackend[Any, Platform.JVM.type] =
    DefaultBackend.fromConstant(Platform.JVM)

  given defaultResource: DefaultBackend[String, JavaResource] =
    DefaultBackend.fromFunction[String, JavaResource](JavaResource.apply)
}
