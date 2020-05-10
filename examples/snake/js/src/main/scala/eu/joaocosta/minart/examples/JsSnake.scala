package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.core._

object JsSnake {
  def main(args: Array[String]): Unit = {
    Snake.runExample(new HtmlCanvas(32, 32, scale = 16, clearColor = (Color(0, 0, 0))), JsRenderLoop)
  }
}
