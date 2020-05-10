package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._

object JsFire {
  def main(args: Array[String]): Unit = {
    Fire.runExample(new HtmlCanvas(128, 128, scale = 4), JsRenderLoop)
  }
}
