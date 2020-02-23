package eu.joaocosta.minart

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.timers

object JsRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](
    canvasManager: CanvasManager,
    initialState: S,
    renderFrame: (Canvas, S) => S,
    terminateWhen: S => Boolean, frameRate: FrameRate): Unit = {
    val canvas = canvasManager.init()
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(canvas, state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (!terminateWhen(state) && canvasManager.isCreated())
        if (waitTime > 0) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
        else dom.window.requestAnimationFrame((_: Double) => finiteRenderLoopAux(newState))
    }
    finiteRenderLoopAux(initialState)
    canvasManager.destroy()
  }

  def singleFrame(
    canvasManager: CanvasManager,
    renderFrame: Canvas => Unit): Unit = {
    val canvas = canvasManager.init()
    renderFrame(canvas)
  }
}
