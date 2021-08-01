package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core._

object SdlRenderLoop extends ImpureRenderLoop {
  def finiteRenderLoop[S](
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      initialState: S,
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
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

  def singleFrame(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, renderFrame: Canvas => Unit): Unit = {
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
