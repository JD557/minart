package eu.joaocosta.minart.backend

import eu.joaocosta.minart.runtime.Platform

package object defaults {
  implicit val defaultCanvas: DefaultBackend[Any, AwtCanvas] =
    DefaultBackend.fromFunction((_) => new AwtCanvas())

  implicit val defaultLoopRunner: DefaultBackend[Any, JavaLoopRunner.type] =
    DefaultBackend.fromConstant(JavaLoopRunner)

  implicit val defaultPlatform: DefaultBackend[Any, Platform.JVM.type] =
    DefaultBackend.fromConstant(Platform.JVM)
}
