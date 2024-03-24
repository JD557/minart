package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.*
import scala.scalanative.runtime.ExecutionContext.global as queueEc
import scala.scalanative.unsigned.*

import sdl2.all.*

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the native backend, backed by SDL.
  *
  *  All Futures run on Scala Native's QueueExecutionContext, as SDL requires events to be pumped in the main thread.
  *  See: https://wiki.libsdl.org/SDL2/SDL_PumpEvents
  */
private[backend] object SdlMultiThreadedAsyncLoopRunner extends LoopRunner[Future] {
  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit,
      frequency: LoopFrequency
  ): Future[S] = {
    given ExecutionContext = queueEc
    frequency match {
      case LoopFrequency.Never | LoopFrequency.Uncapped =>
        Future { SdlSyncLoopRunner.finiteLoop(initialState, operation, terminateWhen, cleanup, frequency).get }
      case freq @ LoopFrequency.LoopDuration(_) =>
        val fullCleanup = () => {
          cleanup()
          SDL_Quit()
        }
        new CappedLoop(operation, terminateWhen, freq.millis, fullCleanup).run(initialState)
    }
  }

  final class CappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) {
    given ExecutionContext = queueEc

    @tailrec
    def finiteLoopAux(state: S, startTime: Long): S = {
      val endTime  = SDL_GetTicks().toLong
      val waitTime = iterationMillis - (endTime - startTime)
      if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
      val newStartTime = SDL_GetTicks().toLong
      val newState     = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState, newStartTime)
      else newState
    }

    def run(initialState: S): Future[S] =
      Future {
        val res = finiteLoopAux(initialState, SDL_GetTicks().toLong)
        cleanup()
        res
      }
  }
}
