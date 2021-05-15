package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

import eu.joaocosta.minart.core._

object JavaRenderLoop extends ImpureRenderLoop {
  def finiteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    val canvas = canvasManager.init(canvasSettings)
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState  = renderFrame(canvas, state)
      if (!terminateWhen(newState) && canvas.isCreated()) {
        val endTime  = System.currentTimeMillis()
        val waitTime = frameMillis - (endTime - startTime)
        if (waitTime > 0) Thread.sleep(waitTime)
        finiteRenderLoopAux(newState)
      } else ()
    }
    finiteRenderLoopAux(initialState)
    if (canvas.isCreated()) canvas.destroy()
  }

  def singleFrame(renderFrame: Canvas => Unit)(canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit = {
    val canvas = canvasManager.init(canvasSettings)
    renderFrame(canvas)
  }
}
