package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.core._

object MousePointer {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  def main(args: Array[String]): Unit = {
    RenderLoop
      .default()
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
        FrameRate.Uncapped
      )(CanvasManager.default(), canvasSettings)
  }
}
