package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.backend.defaults.DefaultBackend

trait ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  def infiniteRenderLoop[S](
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      initialState: S,
      renderFrame: (Canvas, S) => S,
      frameRate: FrameRate
  ): Unit =
    finiteRenderLoop(canvasManager, canvasSettings, initialState, renderFrame, (_: S) => false, frameRate)

  def infiniteRenderLoop(
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      renderFrame: Canvas => Unit,
      frameRate: FrameRate
  ): Unit =
    infiniteRenderLoop(canvasManager, canvasSettings, (), (c: Canvas, _: Unit) => renderFrame(c), frameRate)
}

object ImpureRenderLoop {

  /** Returns an [[ImpureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): ImpureRenderLoop =
    d.defaultValue(())
}
