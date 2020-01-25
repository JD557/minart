package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JavaRenderLoop extends RenderLoop {
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameDuration: FiniteDuration): Unit = {
    val frameMillis = frameDuration.toMillis
    @tailrec
    def finiteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime))
      Thread.sleep(math.max(0, frameMillis - (endTime - startTime)))
      if (terminateWhen(newState)) ()
      else finiteRenderLoopAux(newState)
    }
    finiteRenderLoopAux(initialState)
  }
}
