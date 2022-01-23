package eu.joaocosta.minart.examples

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object Image {
  val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)

  val bitmap = image.Image.loadPpmImage(Resource("scala.ppm")).get

  def main(args: Array[String]): Unit = {
    ImpureRenderLoop
      .singleFrame(canvas => {
        canvas.blit(bitmap)(0, 0)
        canvas.redraw()
      })(canvasSettings)
  }
}
