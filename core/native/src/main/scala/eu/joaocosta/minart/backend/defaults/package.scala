package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, SdlCanvas] =
    DefaultBackend.fromFunction((_) => new SdlCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, SdlLoopRunner.type] =
    DefaultBackend.fromConstant(SdlLoopRunner)

  implicit val defaultPlatform: DefaultBackend[Any, Platform.Native.type] =
    DefaultBackend.fromConstant(Platform.Native)

  implicit val defaultResourceLoader: DefaultBackend[Any, NativeResourceLoader.type] =
    DefaultBackend.fromConstant(NativeResourceLoader)
}
