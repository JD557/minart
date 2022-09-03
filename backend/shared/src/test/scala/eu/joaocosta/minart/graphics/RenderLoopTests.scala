package eu.joaocosta.minart.graphics

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime._

trait RenderLoopTests extends BasicTestSuite {

  def loopRunner: LoopRunner

  object TestCanvas extends SurfaceBackedCanvas {
    protected var surface: RamSurface = _
    def unsafeInit(newSettings: Canvas.Settings): Unit = {
      surface = new RamSurface(newSettings.width, newSettings.height, newSettings.clearColor)
      extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
    }
    def changeSettings(newSettings: Canvas.Settings) = init(newSettings)
    def unsafeDestroy(): Unit                        = ()
    def clear(buffers: Set[Canvas.Buffer]): Unit = {
      if (buffers.contains(Canvas.Buffer.Backbuffer)) { fill(settings.clearColor) }
    }
    def redraw(): Unit                    = ()
    def getKeyboardInput(): KeyboardInput = KeyboardInput.empty
    def getPointerInput(): PointerInput   = PointerInput.empty
  }

  def singleFrameTest() = test("Have a singleFrame operation that runs only once") {
    var renderCount: Int = 0
    ImpureRenderLoop
      .singleFrame(
        renderFrame = (canvas: Canvas) => renderCount += 1
      )
      .run(
        runner = loopRunner,
        canvasManager = CanvasManager(() => TestCanvas),
        canvasSettings = Canvas.Settings(4, 4)
      )
    assert(renderCount == 1)
  }

  def loopTest() = test("Have a finiteRenderLoop operation that ends when a certain state is reached") {
    var renderCount: Int = 0
    ImpureRenderLoop
      .statefulRenderLoop[Int](
        renderFrame = (canvas: Canvas, state: Int) => {
          renderCount += 1
          state + 1
        },
        terminateWhen = _ >= 5,
        frameRate = LoopFrequency.Uncapped
      )
      .run(
        runner = loopRunner,
        canvasManager = CanvasManager(() => TestCanvas),
        canvasSettings = Canvas.Settings(4, 4),
        initialState = 0
      )
    assert(renderCount == 5)
  }
}
