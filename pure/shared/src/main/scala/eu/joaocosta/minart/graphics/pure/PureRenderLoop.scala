package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core._
import eu.joaocosta.minart.core.pure._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.RenderLoop._

object PureRenderLoop extends RenderLoop[RIO, StateCanvasIO] {
  def finiteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    ImpureRenderLoop.finiteRenderLoop[S](
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate
    )

  def infiniteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    ImpureRenderLoop
      .infiniteRenderLoop[S](
        (canvas, state) => renderFrame(state).run(canvas),
        frameRate
      )

  def infiniteRenderLoop(
      renderFrame: CanvasIO[Unit],
      frameRate: FrameRate
  ): StatelessRenderLoop =
    ImpureRenderLoop.infiniteRenderLoop(
      canvas => renderFrame.run(canvas),
      frameRate
    )

  def singleFrame(renderFrame: CanvasIO[Unit]): StatelessRenderLoop =
    ImpureRenderLoop.singleFrame(canvas => renderFrame.run(canvas))
}
