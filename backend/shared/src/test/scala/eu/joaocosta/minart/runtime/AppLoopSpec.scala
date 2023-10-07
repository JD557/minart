package eu.joaocosta.minart.runtime

import scala.concurrent.ExecutionContext.Implicits.global

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

class AppLoopSpec extends munit.FunSuite {

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

  // See https://github.com/scala-native/scala-native/issues/3464
  if (Platform() != Platform.Native) {
    test("Have a statefulRenderLoop operation that ends when a certain state is reached") {
      var renderCount: Int = 0
      AppLoop
        .statefulRenderLoop(
          renderFrame = (state: Int) =>
            (canvas: Canvas) => {
              renderCount += 1
              state + 1
            },
          terminateWhen = (state: Int) => state >= 5
        )
        .configure(
          initialSettings = Canvas.Settings(4, 4),
          frameRate = LoopFrequency.Uncapped,
          initialState = 0
        )
        .run(
          runner = LoopRunner(),
          createSubsystems = () => TestCanvas
        )
        .map { _ =>
          assert(renderCount == 5)
        }
    }
  }
}
