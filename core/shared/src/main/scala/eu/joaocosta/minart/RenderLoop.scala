package eu.joaocosta.minart

import scala.concurrent.duration.FiniteDuration

/**
 * The `RenderLoop` contains a set of helpful methods to implement basic render
 * loops in a platform agonstic way.
 */
trait RenderLoop {

  /**
   * Creates a render loop that terminates when a certain condition is reached.
   *
   * Each loop iteration receives and passes an updated state.
   *
   * @param initialState Initial state when the loop starts
   * @param renderFrame Operation to render the frame and update the state
   * @param terminateWhen Loop termination check
   * @param frameDuration Desired frame duration
   */
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameDuration: FiniteDuration): Unit

  /**
   * Creates a render loop that never terminates.
   *
   * Each loop iteration receives and passes an updated state.
   *
   * @param initialState Initial state when the loop starts
   * @param renderFrame Operation to render the frame and update the state
   * @param frameDuration Desired frame duration
   */
  def infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameDuration: FiniteDuration): Unit =
    finiteRenderLoop(initialState, renderFrame, (_: S) => false, frameDuration)

  /**
   * Creates a render loop that never terminates.
   *
   * @param renderFrame Operation to render the frame and update the state
   * @param frameDuration Desired frame duration
   */
  def infiniteRenderLoop(renderFrame: Unit => Unit, frameDuration: FiniteDuration): Unit =
    infiniteRenderLoop((), renderFrame, frameDuration)
}
