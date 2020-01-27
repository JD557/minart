package eu.joaocosta.minart

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.timers

object JsRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameRate: FrameRate): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (!terminateWhen(state))
        if (waitTime > 0) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
        else dom.window.requestAnimationFrame(_ => finiteRenderLoopAux(newState))
    }
    finiteRenderLoopAux(initialState)
  }
}
