package eu.joaocosta.minart.backend

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.{isUndefined, timers}

import eu.joaocosta.minart.core._

object JsRenderLoop extends ImpureRenderLoop {
  lazy val hasWindow = !isUndefined(dom.window)

  def finiteRenderLoop[S](
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      initialState: S,
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): Unit = {
    val canvas = canvasManager.init(canvasSettings)
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState  = renderFrame(canvas, state)
      if (!terminateWhen(newState) && canvasManager.isCreated()) {
        val endTime  = System.currentTimeMillis()
        val waitTime = frameMillis - (endTime - startTime)
        if (waitTime > 0 || !hasWindow) timers.setTimeout(waitTime.toDouble)(finiteRenderLoopAux(newState))
        else dom.window.requestAnimationFrame((_: Double) => finiteRenderLoopAux(newState))
      } else if (canvasManager.isCreated()) canvasManager.destroy()
    }
    finiteRenderLoopAux(initialState)
  }

  def singleFrame(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, renderFrame: Canvas => Unit): Unit = {
    val canvas = canvasManager.init(canvasSettings)
    renderFrame(canvas)
  }
}
