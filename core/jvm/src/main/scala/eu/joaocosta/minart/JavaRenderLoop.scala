package eu.joaocosta.minart

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

object JavaRenderLoop extends RenderLoop {
  def infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameDuration: FiniteDuration): Unit = {
    val frameMillis = frameDuration.toMillis
    @tailrec
    def infiniteRenderLoopAux(state: S): Unit = {
      val startTime = System.currentTimeMillis()
      val newState = renderFrame(state)
      val endTime = System.currentTimeMillis()
      val waitTime = math.max(0, frameMillis - (endTime - startTime)) 
      Thread.sleep(math.max(0, frameMillis - (endTime - startTime)))
      infiniteRenderLoopAux(newState)
    }
    infiniteRenderLoopAux(initialState)
  }
}
