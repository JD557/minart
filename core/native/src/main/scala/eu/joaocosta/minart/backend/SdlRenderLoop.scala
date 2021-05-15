package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scalanative.unsafe._
import scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.core.RenderLoop._

object SdlRenderLoop extends ImpureRenderLoop {
  def finiteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S] = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    new StatefulRenderLoop[S] {
      def apply(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S) = {
        val canvas = canvasManager.init(canvasSettings)
        @tailrec
        def finiteRenderLoopAux(state: S): Unit = {
          val startTime = SDL_GetTicks()
          val newState  = renderFrame(canvas, state)
          if (!terminateWhen(newState) && canvas.isCreated()) {
            val endTime  = SDL_GetTicks()
            val waitTime = frameMillis - (endTime - startTime).toInt
            if (waitTime > 0) SDL_Delay(waitTime.toUInt)
            finiteRenderLoopAux(newState)
          } else ()
        }
        finiteRenderLoopAux(initialState)
        if (canvas.isCreated()) canvas.destroy()
      }
    }
  }

  def singleFrame(renderFrame: Canvas => Unit): StatelessRenderLoop = new StatelessRenderLoop {
    def apply(canvasManager: CanvasManager, canvasSettings: Canvas.Settings) = {
      val canvas = canvasManager.init(canvasSettings)
      renderFrame(canvas)
      var quit  = false
      val event = stackalloc[SDL_Event]
      while (!quit) {
        while (SDL_PollEvent(event) != 0) {
          if (event.type_ == SDL_QUIT) { quit = true }
        }
      }
      canvas.destroy()
    }
  }
}
