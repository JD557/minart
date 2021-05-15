package eu.joaocosta.minart.pure.backend

import eu.joaocosta.minart.backend.ImpureRenderLoop
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.pure._
import eu.joaocosta.minart.core.RenderLoop._

class PureRenderLoop(impureRenderLoop: ImpureRenderLoop) extends RenderLoop[RIO, PureRenderLoop.StateCanvasIO] {

  def finiteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    impureRenderLoop.finiteRenderLoop[S](
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate
    )

  def infiniteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    impureRenderLoop
      .infiniteRenderLoop[S](
        (canvas, state) => renderFrame(state).run(canvas),
        frameRate
      )

  def infiniteRenderLoop(
      renderFrame: CanvasIO[Unit],
      frameRate: FrameRate
  ): StatelessRenderLoop =
    impureRenderLoop.infiniteRenderLoop(
      canvas => renderFrame.run(canvas),
      frameRate
    )

  def singleFrame(renderFrame: CanvasIO[Unit]): StatelessRenderLoop =
    impureRenderLoop.singleFrame(canvas => renderFrame.run(canvas))
}

object PureRenderLoop {
  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]

  /** Returns [[PureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): PureRenderLoop =
    new PureRenderLoop(d.defaultValue())
}
