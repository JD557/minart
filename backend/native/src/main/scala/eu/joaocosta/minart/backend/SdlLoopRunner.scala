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
    val iterationMillis = frequency match {
      case LoopFrequency.Uncapped         => 0
      case LoopFrequency.LoopDuration(ms) => ms
    }
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
    new Loop[S] {
      def run(initialState: S) = {
        finiteLoopAux(initialState)
        cleanup()
      }
    }
  }

  def singleRun(operation: () => Unit, cleanup: () => Unit): Loop[Unit] = new Loop[Unit] {
    def run(initialState: Unit) = {
      operation()
      var quit  = false
      val event = stackalloc[SDL_Event]()
      while (!quit) {
        while (SDL_PollEvent(event) != 0) {
          if (event.type_ == SDL_QUIT) { quit = true }
        }
      }
      cleanup()
    }
  }
}
