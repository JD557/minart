package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scalanative.unsafe._
import scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.core.Loop._
import eu.joaocosta.minart.graphics.FrameRate

object SdlLoopRunner extends LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate,
      cleanup: () => Unit
  ): StatefulLoop[S] = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    new StatefulLoop[S] {
      def apply(initialState: S) = {
        @tailrec
        def finiteLoopAux(state: S): Unit = {
          val startTime = SDL_GetTicks()
          val newState  = operation(state)
          if (!terminateWhen(newState)) {
            val endTime  = SDL_GetTicks()
            val waitTime = frameMillis - (endTime - startTime).toInt
            if (waitTime > 0) SDL_Delay(waitTime.toUInt)
            finiteLoopAux(newState)
          } else ()
        }
        finiteLoopAux(initialState)
        cleanup()
      }
    }
  }

  def singleRun(operation: () => Unit, cleanup: () => Unit): StatelessLoop = new StatelessLoop {
    def apply() = {
      operation()
      var quit  = false
      val event = stackalloc[SDL_Event]
      while (!quit) {
        while (SDL_PollEvent(event) != 0) {
          if (event.type_ == SDL_QUIT) { quit = true }
        }
      }
      cleanup()
    }
  }
}
