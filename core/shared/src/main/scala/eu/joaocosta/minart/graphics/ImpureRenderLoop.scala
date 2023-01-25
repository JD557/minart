package eu.joaocosta.minart.graphics

import scala.concurrent.Future

import eu.joaocosta.minart.runtime._

/** A render loop that takes a side-effectful renderFrame operation. */
object ImpureRenderLoop extends RenderLoop.Builder[Function1, Function2] {
  def statefulRenderLoop[S](
      renderFrame: (Canvas, S) => S,
      frameRate: LoopFrequency,
      terminateWhen: S => Boolean = (_: S) => false
  ): RenderLoop[S] = {
    new RenderLoop[S] {
      def run(
          runner: LoopRunner,
          createCanvas: () => LowLevelCanvas,
          canvasSettings: Canvas.Settings,
          initialState: S
      ): Future[S] = {
        val canvas = createCanvas().init(canvasSettings)
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

  def statelessRenderLoop(
      renderFrame: Canvas => Unit,
      frameRate: LoopFrequency
  ): RenderLoop[Unit] =
    statefulRenderLoop[Unit]((c: Canvas, _: Unit) => renderFrame(c), frameRate)

  def singleFrame(renderFrame: Canvas => Unit): RenderLoop[Unit] =
    statelessRenderLoop(renderFrame, LoopFrequency.Never)
}
