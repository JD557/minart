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
   * @param canvas Canvas to use in the render loop
   * @param initialState Initial state when the loop starts
   * @param renderFrame Operation to render the frame and update the state
   * @param terminateWhen Loop termination check
   * @param frameRate Frame rate limit
   */
  def finiteRenderLoop[S](
    canvas: Canvas,
    initialState: S,
    renderFrame: (RenderLoopCanvas, S) => S,
    terminateWhen: S => Boolean,
    frameRate: FrameRate): Unit

  /**
   * Creates a render loop that never terminates.
   *
   * Each loop iteration receives and passes an updated state.
   *
   * @param canvas Canvas to use in the render loop
   * @param initialState Initial state when the loop starts
   * @param renderFrame Operation to render the frame and update the state
   * @param frameRate Frame rate limit
   */
  def infiniteRenderLoop[S](
    canvas: Canvas,
    initialState: S,
    renderFrame: (RenderLoopCanvas, S) => S,
    frameRate: FrameRate): Unit =
    finiteRenderLoop(canvas, initialState, renderFrame, (_: S) => false, frameRate)

  /**
   * Creates a render loop that never terminates.
   *
   * @param canvas Canvas to use in the render loop
   * @param renderFrame Operation to render the frame and update the state
   * @param frameRate Frame rate limit
   */
  def infiniteRenderLoop(
    canvas: Canvas,
    renderFrame: RenderLoopCanvas => Unit,
    frameRate: FrameRate): Unit =
    infiniteRenderLoop(canvas, (), (c: RenderLoopCanvas, _: Unit) => renderFrame(c), frameRate)
}
