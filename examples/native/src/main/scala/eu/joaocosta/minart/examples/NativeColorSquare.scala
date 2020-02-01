package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object NativeColorSquare {
  def main(args: Array[String]): Unit = {
    val canvas = new SdlCanvas(128, 128, scale = 4)
    SdlRenderLoop.infiniteRenderLoop(
      _ => ColorSquare.runExample(canvas),
      FrameRate.Uncapped)
  }
}
