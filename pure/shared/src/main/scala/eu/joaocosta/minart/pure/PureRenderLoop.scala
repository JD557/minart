package eu.joaocosta.minart.pure

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.backend.defaults.DefaultBackend

class PureRenderLoop(impureRenderLoop: RenderLoop.ImpureRenderLoop) extends RenderLoop[RIO, PureRenderLoop.StateCanvasIO] {

  def finiteRenderLoop[S](
    canvasManager: CanvasManager,
    initialState: S,
    renderFrame: S => CanvasIO[S],
    terminateWhen: S => Boolean,
    frameRate: FrameRate): Unit =
    impureRenderLoop.finiteRenderLoop[S](
      canvasManager,
      initialState,
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate)

  def infiniteRenderLoop[S](
    canvasManager: CanvasManager,
    initialState: S,
    renderFrame: S => CanvasIO[S],
    frameRate: FrameRate): Unit =
    impureRenderLoop.infiniteRenderLoop[S](
      canvasManager,
      initialState,
      (canvas, state) => renderFrame(state).run(canvas),
      frameRate)

  def infiniteRenderLoop(
    canvasManager: CanvasManager,
    renderFrame: CanvasIO[Unit],
    frameRate: FrameRate): Unit =
    impureRenderLoop.infiniteRenderLoop(
      canvasManager,
      canvas => renderFrame.run(canvas),
      frameRate)

  def singleFrame(
    canvasManager: CanvasManager,
    renderFrame: CanvasIO[Unit]): Unit =
    impureRenderLoop.singleFrame(
      canvasManager,
      canvas => renderFrame.run(canvas))
}

object PureRenderLoop {
  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]

  /**
   * Returns [[PureRenderLoop]] for the default backend for the target platform.
   */
  def default()(implicit d: DefaultBackend[Any, RenderLoop.ImpureRenderLoop]): PureRenderLoop =
    new PureRenderLoop(d.defaultValue(()))
}
