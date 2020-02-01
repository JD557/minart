package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scalanative.native._

import sdl2.SDL._
import sdl2.Extras._

object SdlRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameRate: FrameRate): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    val event = stackalloc[SDL_Event]
    var quit = false
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = scala.math.max(0, frameMillis - (endTime - startTime))
      while (SDL_PollEvent(event) != 0) {
        if (event.type_ == SDL_QUIT) quit = true
      }
      if (waitTime > 0) SDL_Delay(waitTime.toUInt)
      if (quit == true || terminateWhen(newState)) ()
      else finiteRenderLoopAux(newState)
    }
    finiteRenderLoopAux(initialState)
    SDL_Quit()
  }
}
