package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core.RenderLoop._

object ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  def finiteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S] = {
    new StatefulRenderLoop[S] {
      def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S) = {
        val canvas = canvasManager.init(canvasSettings)
        runner.finiteLoop(
          (state: S) => renderFrame(canvas, state),
          (newState: S) => terminateWhen(newState) || !canvas.isCreated(),
          frameRate,
          () => if (canvas.isCreated()) canvas.destroy()
        )(initialState)
      }
    }
  }

  def infiniteRenderLoop[S](
      renderFrame: Function2[Canvas, S, S],
      frameRate: FrameRate
  ): StatefulRenderLoop[S] =
    finiteRenderLoop(renderFrame, (_: S) => false, frameRate)

  def infiniteRenderLoop(
      renderFrame: Canvas => Unit,
      frameRate: FrameRate
  ): StatelessRenderLoop =
    infiniteRenderLoop[Unit]((c: Canvas, _: Unit) => renderFrame(c), frameRate).withInitialState(())

  def singleFrame(renderFrame: Canvas => Unit): StatelessRenderLoop = new StatelessRenderLoop {
    def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit = {
      val canvas = canvasManager.init(canvasSettings)
      runner.singleRun(() => renderFrame(canvas), () => if (canvas.isCreated()) canvas.destroy())()
    }
  }
}
