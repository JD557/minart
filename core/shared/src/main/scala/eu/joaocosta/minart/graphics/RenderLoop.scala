package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

/** Render loop that keeps an internal state that is passed to every iteration.
  *
  * @tparam S State
  */
trait RenderLoop[S] { self =>

  /** Runs this render loop with a custom loop runner and canvas manager.
    *
    * @param runner custom loop runner to use
    * @param canvasManager custom canvas manager to use to create a new canvas
    * @param canvasSettings settings to use to build the canvas
    * @param initalState initial render loop state
    */
  def run(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Unit

  /** Runs this render loop with a custom loop runner and canvas manager.
    *
    * @param runner custom loop runner to use
    * @param canvasManager custom canvas manager to use to create a new canvas
    * @param canvasSettings settings to use to build the canvas
    */
  final def run(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings)(implicit
      ev: Unit =:= S
  ): Unit = run(runner, canvasManager, canvasSettings, ev(()))

  /** Runs this render loop.
    *
    * @param canvasSettings settings to use to build the canvas
    * @param initalState initial render loop state
    */
  final def run(canvasSettings: Canvas.Settings, initialState: S)(implicit
      lr: DefaultBackend[Any, LoopRunner],
      cm: DefaultBackend[Any, LowLevelCanvas]
  ): Unit =
    run(LoopRunner(), CanvasManager(), canvasSettings, initialState)

  /** Runs this render loop.
    *
    * @param canvasSettings settings to use to build the canvas
    */
  final def run(
      canvasSettings: Canvas.Settings
  )(implicit lr: DefaultBackend[Any, LoopRunner], cm: DefaultBackend[Any, LowLevelCanvas], ev: Unit =:= S): Unit =
    run(canvasSettings, ev(()))

  /** Converts this render loop to a stateless render loop, with a predefined initial state.
    *
    * @param initalState initial render loop state
    */
  def withInitialState(state: S): RenderLoop[Unit] = new RenderLoop[Unit] {
    def run(
        runner: LoopRunner,
        canvasManager: CanvasManager,
        canvasSettings: Canvas.Settings,
        initialState: Unit
    ): Unit =
      self.run(runner, canvasManager, canvasSettings, state)
  }
}

object RenderLoop {

  /** The `RenderLoop` contains a set of helpful methods to implement basic render
    * loops in a platform agonstic way.
    *
    * @tparam F1 effect type for stateless loops
    * @tparam F2 effect type for stateful loops
    */
  trait Builder[F1[-_, +_], F2[-_, -_, +_]] {

    /** Creates a render loop that terminates when a certain condition is reached.
      *
      * Each loop iteration receives and passes an updated state.
      *
      * @param renderFrame Operation to render the frame and update the state
      * @param frameRate Frame rate limit
      * @param terminateWhen Loop termination check
      */
    def statefulRenderLoop[S](
        renderFrame: F2[Canvas, S, S],
        frameRate: LoopFrequency,
        terminateWhen: S => Boolean = (s: S) => false
    ): RenderLoop[S]

    /** Creates a render loop that never terminates.
      *
      * @param renderFrame Operation to render the frame
      * @param frameRate Frame rate limit
      */
    def statelessRenderLoop(
        renderFrame: F1[Canvas, Unit],
        frameRate: LoopFrequency
    ): RenderLoop[Unit]

    /** Renders a single frame
      *
      * @param renderFrame Operation to render the frame and update the state
      */
    def singleFrame(renderFrame: F1[Canvas, Unit]): RenderLoop[Unit]
  }
}
