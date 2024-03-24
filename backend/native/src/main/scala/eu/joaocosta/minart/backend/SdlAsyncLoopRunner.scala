package eu.joaocosta.minart.backend

import scala.concurrent.*
import scala.scalanative.meta.LinktimeInfo.isMultithreadingEnabled

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the native backend, backed by SDL.
  *
  *  All Futures run on Scala Native's QueueExecutionContext, as SDL requires events to be pumped in the main thread.
  *  See: https://wiki.libsdl.org/SDL2/SDL_PumpEvents
  */
object SdlAsyncLoopRunner extends LoopRunner[Future] {
  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit,
      frequency: LoopFrequency
  ): Future[S] =
    if (isMultithreadingEnabled)
      SdlMultiThreadedAsyncLoopRunner.finiteLoop(initialState, operation, terminateWhen, cleanup, frequency)
    else SdlSingleThreadedAsyncLoopRunner.finiteLoop(initialState, operation, terminateWhen, cleanup, frequency)
}
