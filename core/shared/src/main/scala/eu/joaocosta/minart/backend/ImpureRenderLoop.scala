package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.backend.defaults.DefaultBackend

trait ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  override def infiniteRenderLoop[S](
      renderFrame: Function2[Canvas, S, S],
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit =
    finiteRenderLoop(renderFrame, (_: S) => false, frameRate)(canvasManager, canvasSettings, initialState)

  def infiniteRenderLoop(
      renderFrame: Canvas => Unit,
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit =
    infiniteRenderLoop[Unit]((c: Canvas, _: Unit) => renderFrame(c), frameRate)(canvasManager, canvasSettings, ())
}

object ImpureRenderLoop {

  /** Returns an [[ImpureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): ImpureRenderLoop =
    d.defaultValue()
}
