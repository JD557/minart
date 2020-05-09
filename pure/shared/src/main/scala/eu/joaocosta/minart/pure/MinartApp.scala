package eu.joaocosta.minart.pure

import eu.joaocosta.minart.core._

trait MinartApp {
  type State
  def canvasManager: CanvasManager
  def renderLoop: RenderLoop
  def initialState: State
  def renderFrame: State => CanvasIO[State]
  def terminateWhen: State => Boolean
  def frameRate: FrameRate

  def main(args: Array[String]): Unit = {
    renderLoop.finiteRenderLoop[State](
      canvasManager,
      initialState,
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate)
  }
}
