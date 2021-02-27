package eu.joaocosta.minart.pure.backend

import eu.joaocosta.minart.backend.ImpureRenderLoop
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.pure._

class PureRenderLoop(impureRenderLoop: ImpureRenderLoop) extends RenderLoop[RIO, PureRenderLoop.StateCanvasIO] {

  def finiteRenderLoop[S](
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      initialState: S,
      renderFrame: S => CanvasIO[S],
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): Unit =
    impureRenderLoop.finiteRenderLoop[S](
      canvasManager,
      canvasSettings,
      initialState,
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate
    )

  def infiniteRenderLoop[S](
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      initialState: S,
      renderFrame: S => CanvasIO[S],
      frameRate: FrameRate
  ): Unit =
    impureRenderLoop
      .infiniteRenderLoop[S](
        canvasManager,
        canvasSettings,
        initialState,
        (canvas, state) => renderFrame(state).run(canvas),
        frameRate
      )

  def infiniteRenderLoop(
      canvasManager: CanvasManager,
      canvasSettings: Canvas.Settings,
      renderFrame: CanvasIO[Unit],
      frameRate: FrameRate
  ): Unit =
    impureRenderLoop.infiniteRenderLoop(
      canvasManager,
      canvasSettings: Canvas.Settings,
      canvas => renderFrame.run(canvas),
      frameRate
    )

  def singleFrame(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, renderFrame: CanvasIO[Unit]): Unit =
    impureRenderLoop.singleFrame(canvasManager, canvasSettings, canvas => renderFrame.run(canvas))
}

object PureRenderLoop {
  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]

  /** Returns [[PureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): PureRenderLoop =
    new PureRenderLoop(d.defaultValue())
}
