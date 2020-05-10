package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._

object NativeColorSquare {
  def main(args: Array[String]): Unit = {
    ColorSquare.runExample(new SdlCanvas(128, 128, scale = 4), SdlRenderLoop)
  }
}
