package eu.joaocosta.minart

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
   * @param frameRate Frame rate limit
   */
  def finiteRenderLoop[S](initialState: S, renderFrame: S => S, terminateWhen: S => Boolean, frameRate: FrameRate): Unit

  /**
   * Creates a render loop that never terminates.
   *
   * Each loop iteration receives and passes an updated state.
   *
   * @param initialState Initial state when the loop starts
   * @param renderFrame Operation to render the frame and update the state
   * @param frameRate Frame rate limit
   */
  def infiniteRenderLoop[S](initialState: S, renderFrame: S => S, frameRate: FrameRate): Unit =
    finiteRenderLoop(initialState, renderFrame, (_: S) => false, frameRate)

  /**
   * Creates a render loop that never terminates.
   *
   * @param renderFrame Operation to render the frame and update the state
   * @param frameRate Frame rate limit
   */
  def infiniteRenderLoop(renderFrame: Unit => Unit, frameRate: FrameRate): Unit =
    infiniteRenderLoop((), renderFrame, frameRate)
}
