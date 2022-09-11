package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent._
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.runtime._

/** Loop Runner for the native backend, backed by SDL.
  */
object SdlLoopRunner extends LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frequency: LoopFrequency,
      cleanup: () => Unit
  ): Loop[S] = {
    frequency match {
      case LoopFrequency.Never =>
        new NeverRenderLoop(operation, cleanup)
      case LoopFrequency.Uncapped =>
        new UncappedRenderLoop(operation, terminateWhen, cleanup)
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new CappedRenderLoop(operation, terminateWhen, iterationMillis, cleanup)
    }
  }

  final class NeverRenderLoop[S](operation: S => S, cleanup: () => Unit) extends Loop[S] {
    def run(initialState: S) = {
      operation(initialState)
      var quit  = false
      val event = stackalloc[SDL_Event]()
      while (!quit) {
        if (SDL_WaitEvent(event) == 1 && event.type_ == SDL_QUIT) { quit = true }
      }
      cleanup()
    }
    def finiteLoopAsyncAux()(implicit ec: ExecutionContext): Future[Unit] = {
      val event = stackalloc[SDL_Event]()
      Future { SDL_WaitEvent(event) == 1 && event.type_ == SDL_QUIT }.flatMap { quit =>
        if (quit) Future.successful(())
        else finiteLoopAsyncAux()
      }
    }
    def runAsync(initialState: S)(implicit ec: ExecutionContext) =
      finiteLoopAsyncAux().map { _ =>
        cleanup()
        initialState
      }

  }

  final class UncappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) extends Loop[S] {
    @tailrec
    def finiteLoopAux(state: S): Unit = {
      val newState = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else ()
    }
    def run(initialState: S) = {
      finiteLoopAux(initialState)
      cleanup()
    }
    def runAsync(initialState: S)(implicit ec: ExecutionContext): Future[S] =
      Future(operation(initialState)).flatMap { newState =>
        if (!terminateWhen(newState)) runAsync(newState)
        else
          Future {
            cleanup()
            initialState
          }
      }
  }

  final class CappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) extends Loop[S] {
    @tailrec
    def finiteLoopAux(state: S): Unit = {
      val startTime = SDL_GetTicks().toLong
      val newState  = operation(state)
      if (!terminateWhen(newState)) {
        val endTime  = SDL_GetTicks().toLong
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) SDL_Delay(waitTime.toUInt)
        finiteLoopAux(newState)
      } else ()
    }
    def run(initialState: S) = {
      finiteLoopAux(initialState)
      cleanup()
    }
    def finiteLoopAsyncAux(state: S, startTime: Long)(implicit ec: ExecutionContext): Future[S] =
      Future {
        val endTime  = SDL_GetTicks().toLong
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { SDL_Delay(waitTime.toUInt) }
        (SDL_GetTicks().toLong, operation(state))
      }.flatMap { case (startTime, newState) =>
        if (!terminateWhen(newState)) finiteLoopAsyncAux(newState, startTime)
        else Future.successful(state)
      }
    def runAsync(initialState: S)(implicit ec: ExecutionContext) =
      finiteLoopAsyncAux(initialState, SDL_GetTicks().toLong).map { res =>
        cleanup()
        res
      }
  }
}
