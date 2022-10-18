package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit lazy val defaultCanvas: DefaultBackend[Any, SdlCanvas] =
    DefaultBackend.fromFunction((_) => new SdlCanvas())

  implicit lazy val defaultLoopRunner: DefaultBackend[Any, SdlLoopRunner.type] =
    DefaultBackend.fromConstant(SdlLoopRunner)

  implicit lazy val defaultAudioPlayer: DefaultBackend[Any, SdlAudioPlayer.type] =
    DefaultBackend.fromConstant(SdlAudioPlayer)

  implicit lazy val defaultPlatform: DefaultBackend[Any, Platform.Native.type] =
    DefaultBackend.fromConstant(Platform.Native)

  implicit lazy val defaultResource: DefaultBackend[String, NativeResource] =
    DefaultBackend.fromFunction[String, NativeResource](NativeResource.apply)
}
