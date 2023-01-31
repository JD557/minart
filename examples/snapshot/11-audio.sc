//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.4.4-SNAPSHOT"

/**
 * Here we'll see how to generate and play audio
 *
 * Note: This is an experimental API, it might break in a future version
 */
import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.backend.defaults._


/**
 * First, let's define a simple song
 */
object Audio {
  // A function from a time in milliseconds to a frequency
  private val song = (t: Double) => {
    val note =
      if (t < 0.3) 0 // A
      else if (t < 0.5) 4 // C#
      else if (t < 0.7) 7 // E
      else 12 // A
    math.pow(2, note / 12.0) * 440 // Convert the notes to frequencies (equal temperament)
  }

  // Here we generate a sin wave with the frequencies from our song
  val arpeggio =
    AudioClip(wave = (t: Double) => math.sin(song(t) * 6.28 * t), duration = 1.0)

  // We can also use the provided oscilators
  val bass =
    Oscilator.sin.generate(frequency = 220, amplitude = 1.0).clip(2.0)

  // Mix our arpeggio (going up and down) with a low root note
  val testSample =
    arpeggio.append(arpeggio.reverse)
      .zipWith(bass, (high, low) => high * 0.7 + low * 0.3)
}

val audioPlayer = AudioPlayer.create(AudioPlayer.Settings())

AppLoop
  .statelessRenderLoop(
    (canvas: Canvas) => {
      // When someone presses "Space", we send our sound wave to the queue
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
        audioPlayer.play(Audio.testSample)
      // When someone presses "Backspace", we stop the audio player
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
        audioPlayer.stop()
      canvas.clear()
      canvas.fill(Color(0, 128, 0))
      canvas.redraw()
    }
  )
  .configure(Canvas.Settings(width = 128, height = 128), LoopFrequency.hz60)
  .run()
