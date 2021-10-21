package eu.joaocosta.minart.examples

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object MousePointer {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .infiniteRenderLoop(
        c => {
          c.clear()
          val mouse = c.getPointerInput()
          val mouseColor =
            if (mouse.isPressed) Color(255, 0, 0)
            else Color(0, 0, 0)
          mouse.position.foreach(pos => c.putPixel(pos.x, pos.y, mouseColor))
          c.redraw()
        },
        LoopFrequency.Uncapped
      )(canvasSettings)
  }
}
