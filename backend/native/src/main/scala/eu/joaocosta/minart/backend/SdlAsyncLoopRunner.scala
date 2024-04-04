package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.*
import scala.scalanative.concurrent.NativeExecutionContext.Implicits.queue
import scala.scalanative.libc.stdlib.*
import scala.scalanative.unsafe.{blocking as _, *}
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_EventType.*

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
  ): Future[S] = {
    val fullCleanup = () => {
      cleanup()
      SDL_Quit()
    }
    frequency match {
      case LoopFrequency.Never =>
        new NeverLoop(operation, fullCleanup).run(initialState)
      case LoopFrequency.Uncapped =>
        new UncappedLoop(operation, terminateWhen, fullCleanup).run(initialState)
      case freq @ LoopFrequency.LoopDuration(_) =>
        new CappedLoop(operation, terminateWhen, freq.millis, fullCleanup).run(initialState)
    }
  }

  final class NeverLoop[S](operation: S => S, cleanup: () => Unit) {
    def checkQuit(event: Ptr[SDL_Event]) =
      SDL_WaitEvent(event) == 1 && SDL_EventType.define((!event).`type`) == SDL_QUIT

    @tailrec
    private def finiteLoopAux(event: Ptr[SDL_Event]): Unit = {
      val quit = checkQuit(event)
      Thread.`yield`()
      if (quit) ()
      else finiteLoopAux(event)
    }

    def run(initialState: S): Future[S] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      Future(operation(initialState))
        .map { res =>
          finiteLoopAux(event)
          res
        }
        .map { res =>
          free(event.asInstanceOf[Ptr[Byte]])
          cleanup()
          res
        }
    }
  }

  final class UncappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) {
    @tailrec
    def finiteLoopAux(state: S): S = {
      val newState = operation(state)
      Thread.`yield`()
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else newState
    }
    def run(initialState: S): Future[S] = Future {
      val res = finiteLoopAux(initialState)
      cleanup()
      res
    }
  }

  final class CappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) {
    @tailrec
    def finiteLoopAux(state: S): S = {
      val startTime = SDL_GetTicks().toLong
      val newState  = operation(state)
      Thread.`yield`()
      if (!terminateWhen(newState)) {
        val endTime  = SDL_GetTicks().toLong
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
        finiteLoopAux(newState)
      } else newState
    }

    def run(initialState: S): Future[S] =
      Future {
        val res = finiteLoopAux(initialState)
        cleanup()
        res
      }
  }
}
