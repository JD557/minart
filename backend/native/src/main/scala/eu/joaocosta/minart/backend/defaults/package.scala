package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  given defaultCanvas: DefaultBackend[Any, SdlCanvas] =
    DefaultBackend.fromFunction((_) => new SdlCanvas())

  given defaultLoopRunner: DefaultBackend[Any, SdlAsyncLoopRunner.type] =
    DefaultBackend.fromConstant(SdlAsyncLoopRunner)

  given defaultAudioPlayer: DefaultBackend[Any, SdlAudioPlayer] =
    DefaultBackend.fromFunction((_) => new SdlAudioPlayer())

  given defaultPlatform: DefaultBackend[Any, Platform.Native.type] =
    DefaultBackend.fromConstant(Platform.Native)

  given defaultResource: DefaultBackend[String, NativeResource] =
    DefaultBackend.fromFunction[String, NativeResource](NativeResource.apply)
}
