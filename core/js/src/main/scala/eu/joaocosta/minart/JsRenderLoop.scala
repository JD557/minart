package eu.joaocosta.minart

import scala.scalajs.js.timers

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JsRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameDuration: FiniteDuration): Unit = {
    val frameMillis = frameDuration.toMillis
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (!terminateWhen(state)) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
    }
    finiteRenderLoopAux(initialState)
  }
}
