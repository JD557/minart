package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.*
import scala.scalanative.libc.stdlib.*
import scala.scalanative.unsafe.{blocking as _, *}
import scala.scalanative.unsigned.*
import scala.util.Try

import sdl2.all.*
import sdl2.enumerations.SDL_EventType.*

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the native backend, backed by SDL.
  */
object SdlSyncLoopRunner extends LoopRunner[Try] {
  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit,
      frequency: LoopFrequency
  ): Try[S] = {
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
    def finiteLoopAux(event: Ptr[SDL_Event]): Unit = {
      def checkQuit() = SDL_WaitEvent(event) == 1 && SDL_EventType.define((!event).`type`) == SDL_QUIT
      while (!checkQuit()) {}
      ()
    }

    def run(initialState: S): Try[S] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      val res                   = operation(initialState)
      Try {
        finiteLoopAux(event)
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
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else newState
    }

    def run(initialState: S): Try[S] =
      Try {
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
    def finiteLoopAux(state: S, startTime: Long): S = {
      val endTime  = SDL_GetTicks().toLong
      val waitTime = iterationMillis - (endTime - startTime)
      if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
      val newStartTime = SDL_GetTicks().toLong
      val newState     = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState, newStartTime)
      else newState
    }

    def run(initialState: S): Try[S] =
      Try {
        val res = finiteLoopAux(initialState, SDL_GetTicks().toLong)
        cleanup()
        res
      }
  }
}
