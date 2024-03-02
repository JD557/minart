package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.*
import scala.scalanative.libc.stdlib.*
import scala.scalanative.meta.LinktimeInfo.isMultithreadingEnabled
import scala.scalanative.runtime.ExecutionContext.{global as queueEc}
import scala.scalanative.unsafe.{blocking as _, *}
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_EventType.*

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the native backend, backed by SDL.
  */
object SdlLoopRunner extends LoopRunner {
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
    given ExecutionContext = queueEc

    def finiteLoopAux(event: Ptr[SDL_Event]): Unit = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      def checkQuit()           = SDL_WaitEvent(event) == 1 && SDL_EventType.define((!event).`type`) == SDL_QUIT
      while (!checkQuit()) {}
      ()
    }

    def finiteEventLoopAux(event: Ptr[SDL_Event]): Future[Unit] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      def checkQuit()           = SDL_WaitEvent(event) == 1 && SDL_EventType.define((!event).`type`) == SDL_QUIT
      Future(checkQuit()).flatMap { quit =>
        if (quit) Future.successful(())
        else finiteEventLoopAux(event)
      }
    }

    def run(initialState: S): Future[S] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      val res                   = operation(initialState)
      if (isMultithreadingEnabled) {
        Future {
          finiteLoopAux(event)
          free(event.asInstanceOf[Ptr[Byte]])
          cleanup()
          res
        }
      } else {
        finiteEventLoopAux(event).map { _ =>
          free(event.asInstanceOf[Ptr[Byte]])
          cleanup()
          res
        }
      }
    }
  }

  final class UncappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) {
    given ExecutionContext = queueEc

    @tailrec
    def finiteLoopAux(state: S): S = {
      val newState = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else newState
    }

    def run(initialState: S): Future[S] =
      if (isMultithreadingEnabled) Future {
        val res = finiteLoopAux(initialState)
        cleanup()
        res
      }
      else
        Future(operation(initialState)).flatMap { newState =>
          if (!terminateWhen(newState)) run(newState)
          else
            Future {
              cleanup()
              newState
            }
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

    def finiteEventLoopAux(state: S, startTime: Long): Future[S] =
      Future {
        val endTime  = SDL_GetTicks().toLong
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
        (SDL_GetTicks().toLong, operation(state))
      }.flatMap { case (startTime, newState) =>
        if (!terminateWhen(newState)) finiteEventLoopAux(newState, startTime)
        else Future.successful(state)
      }

    def run(initialState: S): Future[S] =
      if (isMultithreadingEnabled) Future {
        val res = finiteLoopAux(initialState, SDL_GetTicks().toLong)
        cleanup()
        res
      }
      finiteEventLoopAux(initialState, SDL_GetTicks().toLong).map { res =>
        cleanup()
        res
      }
  }
}
