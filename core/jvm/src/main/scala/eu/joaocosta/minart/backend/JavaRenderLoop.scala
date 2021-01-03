package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

import eu.joaocosta.minart.core._

object JavaRenderLoop extends ImpureRenderLoop {
  def finiteRenderLoop[S](
    canvasManager: CanvasManager,
    initialState: S,
    renderFrame: (Canvas, S) => S,
    terminateWhen: S => Boolean,
    frameRate: FrameRate): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    val canvas = canvasManager.init()
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(canvas, state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (waitTime > 0) Thread.sleep(waitTime)
      if (terminateWhen(newState) || !canvasManager.isCreated()) ()
      else finiteRenderLoopAux(newState)
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
