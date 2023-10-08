package eu.joaocosta.minart.backend

import scala.concurrent._
import scala.scalanative.libc.stdlib._
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.all._
import sdl2.enumerations.SDL_EventType._

import eu.joaocosta.minart.runtime._

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
    frequency match {
      case LoopFrequency.Never =>
        new NeverLoop(operation, cleanup).run(initialState)
      case LoopFrequency.Uncapped =>
        new UncappedLoop(operation, terminateWhen, cleanup).run(initialState)
      case freq @ LoopFrequency.LoopDuration(_) =>
        new CappedLoop(operation, terminateWhen, freq.millis, cleanup).run(initialState)
    }
  }

  final class NeverLoop[S](operation: S => S, cleanup: () => Unit) {
    given ExecutionContext = ExecutionContext.global
    def finiteLoopAux(): Future[Unit] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      Future { SDL_WaitEvent(event) == 1 && SDL_EventType.define((!event).`type`) == SDL_QUIT }.flatMap { quit =>
        if (quit) {
          free(event.asInstanceOf[Ptr[Byte]])
          Future.successful(())
        } else finiteLoopAux()
      }
    }
    def run(initialState: S): Future[S] = {
      val res = operation(initialState)
      finiteLoopAux().map { _ =>
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
    given ExecutionContext = ExecutionContext.global
    def run(initialState: S): Future[S] =
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
    given ExecutionContext = ExecutionContext.global
    def finiteLoopAux(state: S, startTime: Long): Future[S] =
      Future {
        val endTime  = SDL_GetTicks().toLong
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
        (SDL_GetTicks().toLong, operation(state))
      }.flatMap { case (startTime, newState) =>
        if (!terminateWhen(newState)) finiteLoopAux(newState, startTime)
        else Future.successful(state)
      }
    def run(initialState: S): Future[S] =
      finiteLoopAux(initialState, SDL_GetTicks().toLong).map { res =>
        cleanup()
        res
      }
  }
}
