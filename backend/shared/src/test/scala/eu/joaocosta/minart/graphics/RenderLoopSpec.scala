package eu.joaocosta.minart.graphics

import scala.concurrent.ExecutionContext.Implicits.global

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime._

object RenderLoopTests extends BasicTestSuite {

  object TestCanvas extends SurfaceBackedCanvas {
    protected var surface: RamSurface = _
    def unsafeInit(): Unit            = {}
    def unsafeApplySettings(newSettings: Canvas.Settings): LowLevelCanvas.ExtendedSettings = {
      surface = new RamSurface(newSettings.width, newSettings.height, newSettings.clearColor)
      LowLevelCanvas.ExtendedSettings(newSettings)
    }
    def unsafeDestroy(): Unit = ()
    def clear(buffers: Set[Canvas.Buffer]): Unit = {
      if (buffers.contains(Canvas.Buffer.Backbuffer)) { fill(settings.clearColor) }
    }
    def redraw(): Unit                    = ()
    def getKeyboardInput(): KeyboardInput = KeyboardInput.empty
    def getPointerInput(): PointerInput   = PointerInput.empty
  }

  testAsync("Have a finiteRenderLoop operation that ends when a certain state is reached") {
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
        runner = LoopRunner(),
        createCanvas = () => TestCanvas,
        canvasSettings = Canvas.Settings(4, 4),
        initialState = 0
      )
      .map { _ =>
        assert(renderCount == 5)
      }
  }
}
