//> using scala "3.1.2"
//> using lib "eu.joaocosta::minart::0.4.1-SNAPSHOT"

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.backend.defaults._

val song = (t: Double) => {
  val exp =
    if (t < 0.2) 0
    else if (t < 0.4) 4
    else if (t < 0.6) 7
    else 12
  math.pow(2, exp/12.0)*440
}
val testSample =
  AudioSample.fromFunction(t => math.sin(song(t) * 6.28 * t), 1.0)

AudioPlayer().play(testSample)

val canvasSettings = Canvas.Settings(width = 128, height = 128, scale = 4)
ImpureRenderLoop
  .singleFrame(canvas => {
    for {
      x <- (0 until canvas.width)
      y <- (0 until canvas.height)
    } {
      val color =
        Color((255 * x.toDouble / canvas.width).toInt, (255 * y.toDouble / canvas.height).toInt, 255)
      canvas.putPixel(x, y, color)
    }
    canvas.redraw()
  })
  .run(canvasSettings)
