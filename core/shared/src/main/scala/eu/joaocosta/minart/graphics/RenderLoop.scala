package eu.joaocosta.minart.graphics

import scala.concurrent.{ExecutionContext, Future}

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
  def run(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings, initialState: S): Future[S]

  /** Runs this render loop with a custom loop runner and canvas manager.
    *
    * @param runner custom loop runner to use
    * @param canvasManager custom canvas manager to use to create a new canvas
    * @param canvasSettings settings to use to build the canvas
    */
  final def run(runner: LoopRunner, canvasManager: CanvasManager, canvasSettings: Canvas.Settings)(implicit
      ev: Unit =:= S
  ): Future[S] = run(runner, canvasManager, canvasSettings, ev(()))

  /** Runs this render loop.
    *
    * @param canvasSettings settings to use to build the canvas
    * @param initalState initial render loop state
    */
  final def run(canvasSettings: Canvas.Settings, initialState: S)(implicit
      lr: DefaultBackend[Any, LoopRunner],
      cm: DefaultBackend[Any, LowLevelCanvas]
  ): Future[S] =
    run(LoopRunner(), CanvasManager(), canvasSettings, initialState)

  /** Runs this render loop.
    *
    * @param canvasSettings settings to use to build the canvas
    */
  final def run(
      canvasSettings: Canvas.Settings
  )(implicit lr: DefaultBackend[Any, LoopRunner], cm: DefaultBackend[Any, LowLevelCanvas], ev: Unit =:= S): Future[S] =
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
    ): Future[Unit] =
      self.run(runner, canvasManager, canvasSettings, state).map(_ => ())(ExecutionContext.global)
  }
}

object RenderLoop {

  /** Contains a set of helpful methods to implement basic render
    * loops in a platform agonstic way.
    *
    * @tparam F1 effect type for stateless loops
    * @tparam F2 effect type for stateful loops
    */
  trait Builder[F1[-_, +_], F2[-_, -_, +_]] {

    /** Creates a render loop that keeps and updates a state on every iteration,
      *  terminating when a certain condition is reached.
      *
      * @param renderFrame operation to render the frame and update the state
      * @param frameRate frame rate limit
      * @param terminateWhen loop termination check
      */
    def statefulRenderLoop[S](
        renderFrame: F2[Canvas, S, S],
        frameRate: LoopFrequency,
        terminateWhen: S => Boolean = (_: S) => false
    ): RenderLoop[S]

    /** Creates a render loop that keeps no state.
      *
      * @param renderFrame operation to render the frame
      * @param frameRate frame rate limit
      */
    def statelessRenderLoop(
        renderFrame: F1[Canvas, Unit],
        frameRate: LoopFrequency
    ): RenderLoop[Unit]

    /** Renders a single frame.
      *
      * @param renderFrame operation to render the frame and update the state
      */
    def singleFrame(renderFrame: F1[Canvas, Unit]): RenderLoop[Unit]
  }
}
