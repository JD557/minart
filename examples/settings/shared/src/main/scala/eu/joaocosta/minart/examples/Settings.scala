package eu.joaocosta.minart.examples

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input.KeyboardInput.Key
import eu.joaocosta.minart.runtime._

object Settings {
  val settingsA = Canvas.Settings(width = 128, height = 128, scale = 4, clearColor = Color(128, 255, 128))
  val settingsB =
    Canvas.Settings(width = 640, height = 480, scale = 1, fullScreen = true, clearColor = Color(128, 255, 128))

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .infiniteRenderLoop(
        c => {
          val keyboardInput = c.getKeyboardInput()
          c.clear()
          if (keyboardInput.keysPressed(Key.A)) c.changeSettings(settingsA)
          else if (keyboardInput.keysPressed(Key.B)) c.changeSettings(settingsB)
          for {
            x <- (0 until c.width)
            y <- (0 until c.height)
          } {
            val color =
              Color((255 * x.toDouble / c.width).toInt, (255 * y.toDouble / c.height).toInt, 255)
            c.putPixel(x, y, color)
          }
          c.redraw()
        },
        LoopFrequency.Uncapped
      )(settingsA)
  }
}
