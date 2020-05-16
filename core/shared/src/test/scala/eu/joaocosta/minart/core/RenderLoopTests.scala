package eu.joaocosta.minart.core

import org.specs2.mutable._

import eu.joaocosta.minart.backend.PpmCanvas

trait RenderLoopTests extends Specification {

  def renderLoop: RenderLoop
  def renderLoopName: String
  def testLoop: Boolean = true

  s"A $renderLoopName" should {
    "have a singleFrame operation that runs only once" in {
      var renderCount: Int = 0
      renderLoop.singleFrame(
        canvasManager = new PpmCanvas(Canvas.Settings(4, 4)),
        renderFrame = (canvas: Canvas) => renderCount += 1)
      renderCount === 1
    }

    "have a finiteRenderLoop operation that ends when a certain state is reached" in {
      if (!testLoop) skipped("Test not supported")
      var renderCount: Int = 0
      renderLoop.finiteRenderLoop[Int](
        canvasManager = new PpmCanvas(Canvas.Settings(4, 4)),
        initialState = 0,
        renderFrame = (canvas: Canvas, state: Int) => {
          renderCount += 1
          state + 1
        },
        terminateWhen = _ >= 5,
        frameRate = FrameRate.Uncapped)
      renderCount === 5
    }
  }
}
