package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

object Settings {
  val settingsA = Canvas.Settings(width = 128, height = 128, scale = 4, clearColor = Color(128, 255, 128))
  val settingsB =
    Canvas.Settings(width = 640, height = 480, scale = 1, fullScreen = true, clearColor = Color(128, 255, 128))

  def main(args: Array[String]): Unit = {
    RenderLoop
      .default()
      .infiniteRenderLoop(
        CanvasManager.default(),
        settingsA,
        c => {
          val keyboardInput = c.getKeyboardInput()
          c.clear()
          if (keyboardInput.keysPressed(Key.A)) c.changeSettings(settingsA)
          else if (keyboardInput.keysPressed(Key.B)) c.changeSettings(settingsB)
          val (width, height) = c.settings.map(s => (s.width, s.height)).getOrElse(0, 0)
          for {
            x <- (0 until width)
            y <- (0 until height)
          } {
            val color =
              Color((255 * x.toDouble / width).toInt, (255 * y.toDouble / height).toInt, 255)
            c.putPixel(x, y, color)
          }
          c.redraw()
        },
        FrameRate.Uncapped
      )
  }
}
