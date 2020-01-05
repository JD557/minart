package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object JvmFire {
  def main(args: Array[String]): Unit = {
    Fire.runExample(AwtCanvas(128, 128, scale = 4), JavaRenderLoop)
  }
}
