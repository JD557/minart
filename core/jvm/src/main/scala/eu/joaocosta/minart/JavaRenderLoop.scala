package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JavaRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameRate: FrameRate): Unit = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      if (waitTime > 0) Thread.sleep(waitTime)
      if (terminateWhen(newState)) ()
      else finiteRenderLoopAux(newState)
    }
    finiteRenderLoopAux(initialState)
  }
}
