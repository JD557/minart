package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.graphics.RenderLoop._
import eu.joaocosta.minart.runtime._

/** A render loop that takes a side-effectful renderFrame operation. */
object ImpureRenderLoop extends RenderLoop[Function1, Function2] {
  def finiteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      terminateWhen: S => Boolean,
      frameRate: LoopFrequency
  ): StatefulRenderLoop[S] = {
    new StatefulRenderLoop[S] {
      def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S) = {
        val canvas = canvasManager.init(canvasSettings)
        runner
          .finiteLoop(
            (state: S) => renderFrame(canvas, state),
            (newState: S) => terminateWhen(newState) || !canvas.isCreated(),
            frameRate,
            () => if (canvas.isCreated()) canvas.close()
          )
          .run(initialState)
      }
    }
  }

  def infiniteRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      frameRate: LoopFrequency
  ): StatefulRenderLoop[S] =
    finiteRenderLoop(renderFrame, (_: S) => false, frameRate)

  def infiniteRenderLoop(
      renderFrame: Canvas => Unit,
      frameRate: LoopFrequency
  ): StatelessRenderLoop =
    infiniteRenderLoop[Unit]((c: Canvas, _: Unit) => renderFrame(c), frameRate).withInitialState(())

  def singleFrame(renderFrame: Canvas => Unit): StatelessRenderLoop = new StatelessRenderLoop {
    def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit = {
      val canvas = canvasManager.init(canvasSettings)
      runner.singleRun(() => renderFrame(canvas), () => if (canvas.isCreated()) canvas.close()).run()
    }
  }
}
