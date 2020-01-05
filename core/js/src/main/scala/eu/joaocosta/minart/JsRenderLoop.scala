package eu.joaocosta.minart

import scala.scalajs.js.timers

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JsRenderLoop extends RenderLoop {
  def infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameDuration: FiniteDuration): Unit = {
    val frameMillis = frameDuration.toMillis
    def infiniteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime)) 
      timers.setTimeout(waitTime.toDouble)(infiniteRenderLoopAux(newState))
    }
    infiniteRenderLoopAux(initialState)
  }
}
