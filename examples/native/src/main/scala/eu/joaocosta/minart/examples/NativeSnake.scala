package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object NativeSnake {
  def main(args: Array[String]): Unit = {
    Snake.runExample(new SdlCanvas(32, 32, scale = 16, clearColor = (Color(0, 0, 0))), SdlRenderLoop)
  }
}
