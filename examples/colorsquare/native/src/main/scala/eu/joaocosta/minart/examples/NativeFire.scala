package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._

object NativeFire {
  def main(args: Array[String]): Unit = {
    Fire.runExample(new SdlCanvas(128, 128, scale = 4), SdlRenderLoop)
  }
}
