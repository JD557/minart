package eu.joaocosta.minart.core

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.core.RenderLoop._

/** The `RenderLoop` contains a set of helpful methods to implement basic render
  * loops in a platform agonstic way.
  */
trait RenderLoop[F1[-_, +_], F2[-_, -_, +_]] {

  /** Creates a render loop that terminates when a certain condition is reached.
    *
    * Each loop iteration receives and passes an updated state.
    *
    * @param canvasManager Canvas manager to use in the render loop
    * @param canvasSettings The canvas settings to use
    * @param initialState Initial state when the loop starts
    * @param renderFrame Operation to render the frame and update the state
    * @param terminateWhen Loop termination check
    * @param frameRate Frame rate limit
    */
  def finiteRenderLoop[S](
      renderFrame: F2[Canvas, S, S],
      terminateWhen: S => Boolean,
      frameRate: FrameRate
  ): StatefulRenderLoop[S]

  /** Creates a render loop that never terminates.
    *
    * Each loop iteration receives and passes an updated state.
    *
    * @param canvasManager Canvas manager to use in the render loop
    * @param canvasSettings The canvas settings to use
    * @param initialState Initial state when the loop starts
    * @param renderFrame Operation to render the frame and update the state
    * @param frameRate Frame rate limit
    */
  def infiniteRenderLoop[S](
      renderFrame: F2[Canvas, S, S],
      frameRate: FrameRate
  ): StatefulRenderLoop[S]

  /** Creates a render loop that never terminates.
    *
    * @param canvasManager Canvas manager to use in the render loop
    * @param canvasSettings The canvas settings to use
    * @param renderFrame Operation to render the frame
    * @param frameRate Frame rate limit
    */
  def infiniteRenderLoop(
      renderFrame: F1[Canvas, Unit],
      frameRate: FrameRate
  ): StatelessRenderLoop

  /** Renders a single frame
    *
    * @param canvasManager Canvas manager to use in the render loop
    * @param canvasSettings The canvas settings to use
    * @param renderFrame Operation to render the frame and update the state
    */
  def singleFrame(renderFrame: F1[Canvas, Unit]): StatelessRenderLoop
}

object RenderLoop {

  trait StatelessRenderLoop extends StatefulRenderLoop[Unit] {
    def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit
    def apply(canvasSettings: Canvas.Settings)(implicit
        runner: DefaultBackend[Any, LoopRunner],
        canvasManager: DefaultBackend[Any, CanvasManager]
    ): Unit = apply(runner.defaultValue(()), canvasManager.defaultValue(()), canvasSettings)
    def apply(
        runner: LoopRunner,
        canvasManager: CanvasManager,
        canvasSettings: Canvas.Settings,
        initialState: Unit
    ): Unit =
      apply(runner, canvasManager, canvasSettings)
    override def withInitialState(initialState: Unit): this.type = this
  }

  trait StatefulRenderLoop[S] { self =>
    def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit
    def apply(canvasSettings: Canvas.Settings, initialState: S)(implicit
        runner: DefaultBackend[Any, LoopRunner],
        canvasManager: DefaultBackend[Any, CanvasManager]
    ): Unit = apply(runner.defaultValue(()), canvasManager.defaultValue(()), canvasSettings, initialState)
    def withInitialState(initialState: S): StatelessRenderLoop = new StatelessRenderLoop {
      def apply(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings): Unit =
        self.apply(runner, canvasManager, canvasSettings, initialState)
    }
  }

  /** Returns a [[RenderLoop]] for the default backend for the target platform.
    */
  def default()(implicit d: DefaultBackend[Any, RenderLoop[Function1, Function2]]): RenderLoop[Function1, Function2] =
    d.defaultValue(())
}
