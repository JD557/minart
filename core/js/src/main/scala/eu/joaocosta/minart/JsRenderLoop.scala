package eu.joaocosta.minart

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.timers

object JsRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](
    canvas: Canvas,
    initialState: S,
    renderFrame: (RenderLoopCanvas, S) => S,
    terminateWhen: S => Boolean, frameRate: FrameRate): Unit = {
    canvas.init()
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(canvas, state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (!terminateWhen(state) && canvas.isCreated())
        if (waitTime > 0) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
        else dom.window.requestAnimationFrame((_: Double) => finiteRenderLoopAux(newState))
    }
    finiteRenderLoopAux(initialState)
    canvas.destroy()
  }
}
