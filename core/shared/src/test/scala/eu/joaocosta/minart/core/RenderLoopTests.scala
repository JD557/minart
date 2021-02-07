package eu.joaocosta.minart.core

import verify._

import eu.joaocosta.minart.backend._

trait RenderLoopTests extends BasicTestSuite {

  def renderLoop: ImpureRenderLoop

  def singleFrameTest() = test("Have a singleFrame operation that runs only once") {
    var renderCount: Int = 0
    renderLoop.singleFrame(
      canvasManager = new PpmCanvas(Canvas.Settings(4, 4)),
      renderFrame = (canvas: Canvas) => renderCount += 1
    )
    assert(renderCount == 1)
  }

  def loopTest() = test("Have a finiteRenderLoop operation that ends when a certain state is reached") {
    var renderCount: Int = 0
    renderLoop.finiteRenderLoop[Int](
      canvasManager = new PpmCanvas(Canvas.Settings(4, 4)),
      initialState = 0,
      renderFrame = (canvas: Canvas, state: Int) => {
        renderCount += 1
        state + 1
      },
      terminateWhen = _ >= 5,
      frameRate = FrameRate.Uncapped
    )
    assert(renderCount == 5)
  }
}
