package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.backend.defaults.DefaultBackend

trait ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  def infiniteRenderLoop[S](
    canvasManager: CanvasManager,
    initialState: S,
    renderFrame: (Canvas, S) => S,
    frameRate: FrameRate): Unit =
    finiteRenderLoop(canvasManager, initialState, renderFrame, (_: S) => false, frameRate)

  def infiniteRenderLoop(
    canvasManager: CanvasManager,
    renderFrame: Canvas => Unit,
    frameRate: FrameRate): Unit =
    infiniteRenderLoop(canvasManager, (), (c: Canvas, _: Unit) => renderFrame(c), frameRate)
}

object ImpureRenderLoop {
  /**
   * Returns an [[ImpureRenderLoop]] for the default backend for the target platform.
   */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): ImpureRenderLoop =
    d.defaultValue(())
}
