package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core.Loop._

trait LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate,
      cleanup: () => Unit
  ): StatefulLoop[S]

  def singleRun(operation: () => Unit, cleanup: () => Unit): StatelessLoop
}

object LoopRunner {
  def default()(implicit d: DefaultBackend[Any, LoopRunner]): LoopRunner =
    d.defaultValue(())
}
