package eu.joaocosta.minart.backend

import scala.concurrent.*
import scala.concurrent.duration.Duration
import scala.util.Try

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the Java platform.
  */
object JavaSyncLoopRunner extends LoopRunner[Try] {
  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit,
      frequency: LoopFrequency
  ): Try[S] = Try {
    Await.result(
      JavaAsyncLoopRunner.finiteLoop(initialState, operation, terminateWhen, cleanup, frequency),
      Duration.Inf
    )
  }
}
