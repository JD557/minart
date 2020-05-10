package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend._

object JvmFire {
  def main(args: Array[String]): Unit = {
    Fire.runExample(new AwtCanvas(128, 128, scale = 4), JavaRenderLoop)
  }
}
