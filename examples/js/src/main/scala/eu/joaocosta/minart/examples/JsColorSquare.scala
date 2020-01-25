package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object JsColorSquare {
  def main(args: Array[String]): Unit = {
    ColorSquare.runExample(new HtmlCanvas(128, 128, scale = 4))
  }
}
