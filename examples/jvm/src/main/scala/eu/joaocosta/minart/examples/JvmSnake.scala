package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object JvmSnake {
  def main(args: Array[String]): Unit = {
    Snake.runExample(new AwtCanvas(32, 32, scale = 16, clearColor = (Color(0, 0, 0))), JavaRenderLoop)
  }
}
