package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.pure._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.RenderLoop._

object PureRenderLoop extends RenderLoop[RIO, StateCanvasIO] {
  def finiteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      terminateWhen: S => Boolean,
      frameRate: LoopFrequency
  ): StatefulRenderLoop[S] =
    ImpureRenderLoop.finiteRenderLoop[S](
      (canvas, state) => renderFrame(state).run(canvas),
      terminateWhen,
      frameRate
    )

  def infiniteRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      frameRate: LoopFrequency
  ): StatefulRenderLoop[S] =
    ImpureRenderLoop
      .infiniteRenderLoop[S](
        (canvas, state) => renderFrame(state).run(canvas),
        frameRate
      )

  def infiniteRenderLoop(
      renderFrame: CanvasIO[Unit],
      frameRate: LoopFrequency
  ): StatelessRenderLoop =
    ImpureRenderLoop.infiniteRenderLoop(
      canvas => renderFrame.run(canvas),
      frameRate
    )

  def singleFrame(renderFrame: CanvasIO[Unit]): StatelessRenderLoop =
    ImpureRenderLoop.singleFrame(canvas => renderFrame.run(canvas))
}
