package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core.RenderLoop._

trait ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  override def infiniteRenderLoop[S](
      renderFrame: Function2[Canvas, S, S],
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    finiteRenderLoop(renderFrame, (_: S) => false, frameRate)

  def infiniteRenderLoop(
      renderFrame: Canvas => Unit,
      frameRate: FrameRate
  ): StatelessRenderLoop =
    infiniteRenderLoop[Unit]((c: Canvas, _: Unit) => renderFrame(c), frameRate).withInitialState(())
}

object ImpureRenderLoop {

  /** Returns an [[ImpureRenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, ImpureRenderLoop]): ImpureRenderLoop =
    d.defaultValue()
}
