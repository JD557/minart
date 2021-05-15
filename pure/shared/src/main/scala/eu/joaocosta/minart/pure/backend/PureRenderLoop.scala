package eu.joaocosta.minart.pure.backend

import eu.joaocosta.minart.backend.ImpureRenderLoop
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.pure._

class PureRenderLoop(impureRenderLoop: ImpureRenderLoop) extends RenderLoop[RIO, PureRenderLoop.StateCanvasIO] {

  def finiteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit =
    impureRenderLoop.finiteRenderLoop[S](
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate
    )(canvasManager, canvasSettings, initialState)

  def infiniteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit =
    impureRenderLoop
      .infiniteRenderLoop[S](
        (canvas, state) => renderFrame(state).run(canvas),
        frameRate
      )(canvasManager, canvasSettings, initialState)

  def infiniteRenderLoop(
      renderFrame: CanvasIO[Unit],
      frameRate: FrameRate
  )(canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit =
    impureRenderLoop.infiniteRenderLoop(
      canvas => renderFrame.run(canvas),
      frameRate
    )(canvasManager, canvasSettings)

  def singleFrame(renderFrame: CanvasIO[Unit])(canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit =
    impureRenderLoop.singleFrame(canvas => renderFrame.run(canvas))(canvasManager, canvasSettings)
}

object PureRenderLoop {
  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]

  /** Returns [[PureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): PureRenderLoop =
    new PureRenderLoop(d.defaultValue())
}
