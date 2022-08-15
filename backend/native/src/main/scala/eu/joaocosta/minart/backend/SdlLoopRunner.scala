package eu.joaocosta.minart.backend

import scala.annotation.tailrec
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
        new Loop[S] {
          def run(initialState: S) = {
            operation(initialState)
            var quit  = false
            val event = stackalloc[SDL_Event]()
            while (!quit) {
              if (SDL_WaitEvent(event) == 1 && event.type_ == SDL_QUIT) { quit = true }
            }
            cleanup()
          }
        }
      case LoopFrequency.Uncapped =>
        new Loop[S] {
          @tailrec
          def finiteLoopAux(state: S): Unit = {
            val newState = operation(state)
            if (!terminateWhen(newState)) {
              finiteLoopAux(newState)
            } else ()
          }
          def run(initialState: S) = {
            finiteLoopAux(initialState)
            cleanup()
          }
        }
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new Loop[S] {
          @tailrec
          def finiteLoopAux(state: S): Unit = {
            val startTime = SDL_GetTicks()
            val newState  = operation(state)
            if (!terminateWhen(newState)) {
              val endTime  = SDL_GetTicks()
              val waitTime = iterationMillis - (endTime - startTime).toInt
              if (waitTime > 0) SDL_Delay(waitTime.toUInt)
              finiteLoopAux(newState)
            } else ()
          }
          def run(initialState: S) = {
            finiteLoopAux(initialState)
            cleanup()
          }
        }
    }
  }
}
