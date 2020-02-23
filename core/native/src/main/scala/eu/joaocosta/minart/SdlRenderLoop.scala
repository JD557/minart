package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scalanative.native._

import sdl2.SDL._
import sdl2.Extras._

object SdlRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](
    canvas: Canvas,
    initialState: S,
    renderFrame: (RenderLoopCanvas, S) => S,
    terminateWhen: S => Boolean,
    frameRate: FrameRate): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(canvas, state)
      val endTime = System.currentTimeMillis()
      val waitTime = scala.math.max(0, frameMillis - (endTime - startTime))
      if (waitTime > 0) SDL_Delay(waitTime.toUInt)
      if (terminateWhen(newState) || !canvas.isCreated()) ()
      else finiteRenderLoopAux(newState)
    }
    finiteRenderLoopAux(initialState)
    canvas.destroy()
  }
}
