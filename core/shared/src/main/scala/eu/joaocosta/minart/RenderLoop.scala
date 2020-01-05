package eu.joaocosta.minart

import scala.concurrent.duration.FiniteDuration

trait RenderLoop {
  def infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameDuration: FiniteDuration): Unit
  def infiniteRenderLoop(renderFrame: Unit => Unit, frameDuration: FiniteDuration): Unit =
    infiniteRenderLoop((), renderFrame, frameDuration)
}
