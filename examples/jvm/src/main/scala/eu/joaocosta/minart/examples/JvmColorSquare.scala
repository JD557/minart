package eu.joaocosta.minart.examples

import eu.joaocosta.minart._

object JvmColorSquare {
  def main(args: Array[String]): Unit = {
    ColorSquare.runExample(AwtCanvas(128, 128, scale = 4))
  }
}
