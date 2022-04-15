package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.pure._

object PureRenderLoop extends RenderLoop.Builder[RIO, StateCanvasIO] {
  def statefulRenderLoop[S](
      renderFrame: S => CanvasIO[S],
      frameRate: LoopFrequency,
      terminateWhen: S => Boolean = (_: S) => false
  ): RenderLoop[S] =
    ImpureRenderLoop.statefulRenderLoop[S](
      (canvas, state) => renderFrame(state).run(canvas),
      frameRate,
      terminateWhen
    )

  def statelessRenderLoop(
      renderFrame: CanvasIO[Unit],
      frameRate: LoopFrequency
  ): RenderLoop[Unit] =
    ImpureRenderLoop.statelessRenderLoop(
      canvas => renderFrame.run(canvas),
      frameRate
    )

  def singleFrame(renderFrame: CanvasIO[Unit]): RenderLoop[Unit] =
    ImpureRenderLoop.singleFrame(canvas => renderFrame.run(canvas))
}
