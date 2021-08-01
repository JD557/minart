package eu.joaocosta.minart.graphics

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

trait RenderLoopTests extends BasicTestSuite {

  def loopRunner: LoopRunner

  def singleFrameTest() = test("Have a singleFrame operation that runs only once") {
    var renderCount: Int = 0
    ImpureRenderLoop.singleFrame(
      renderFrame = (canvas: Canvas) => renderCount += 1
    )(runner = loopRunner, canvasManager = CanvasManager(() => new PpmCanvas()), canvasSettings = Canvas.Settings(4, 4))
    assert(renderCount == 1)
  }

  def loopTest() = test("Have a finiteRenderLoop operation that ends when a certain state is reached") {
    var renderCount: Int = 0
    ImpureRenderLoop.finiteRenderLoop[Int](
      renderFrame = (canvas: Canvas, state: Int) => {
        renderCount += 1
        state + 1
      },
      terminateWhen = _ >= 5,
      frameRate = LoopFrequency.Uncapped
    )(
      runner = loopRunner,
      canvasManager = CanvasManager(() => new PpmCanvas()),
      canvasSettings = Canvas.Settings(4, 4),
      initialState = 0
    )
    assert(renderCount == 5)
  }
}
