package eu.joaocosta.minart.pure

import eu.joaocosta.minart.core._

/** Entrypoint for pure Minart applications. */
trait MinartApp {
  type State
  def canvasManager: CanvasManager
  def renderLoop: PureRenderLoop
  def initialState: State
  def renderFrame: State => CanvasIO[State]
  def terminateWhen: State => Boolean
  def frameRate: FrameRate

  def main(args: Array[String]): Unit = {
    renderLoop.finiteRenderLoop[State](
      canvasManager,
      initialState,
      renderFrame,
      terminateWhen,
      frameRate)
  }
}
