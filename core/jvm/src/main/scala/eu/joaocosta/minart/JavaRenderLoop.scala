package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JavaRenderLoop extends RenderLoop {
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
    canvas.init()
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(canvas, state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (waitTime > 0) Thread.sleep(waitTime)
      if (terminateWhen(newState) || !canvas.isCreated()) ()
      else finiteRenderLoopAux(newState)
    }
    finiteRenderLoopAux(initialState)
    canvas.destroy()
  }
}
