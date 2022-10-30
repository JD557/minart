package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent._
import scala.scalanative.libc.stdlib._
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
    private implicit val ec: ExecutionContext = ExecutionContext.global
    def finiteLoopAux(): Future[Unit] = {
      val event: Ptr[SDL_Event] = malloc(sizeof[SDL_Event]).asInstanceOf[Ptr[SDL_Event]]
      Future { SDL_WaitEvent(event) == 1 && event.type_ == SDL_QUIT }.flatMap { quit =>
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

  final class UncappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) extends Loop[S] {
    private implicit val ec: ExecutionContext = ExecutionContext.global
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

  final class CappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) extends Loop[S] {
    private implicit val ec: ExecutionContext = ExecutionContext.global
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
