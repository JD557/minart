package eu.joaocosta.minart.backend

import scala.annotation.tailrec

import eu.joaocosta.minart.core._

object JavaRenderLoop extends ImpureRenderLoop {
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

  def singleFrame(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, renderFrame: Canvas => Unit): Unit = {
    val canvas = canvasManager.init(canvasSettings)
    renderFrame(canvas)
  }
}
