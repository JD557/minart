//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.4.4-SNAPSHOT"

/**
 * Just like images, it can also be convenient to load sound files.
 * The minart-sound project includes some sound formats to get started.
 *
 * Note: This is an experimental API, it might break in a future version
 */
import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.backend.defaults._

/**
 * Just like loading an image, we can load sound resources
 */
val clip = Sound.loadAiffClip(Resource("assets/sample.aiff")).get

val audioPlayer = AudioPlayer.create(AudioPlayer.Settings())

// Same logic as the audio example
AppLoop
  .statelessRenderLoop(
    (canvas: Canvas) => {
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
        audioPlayer.play(clip)
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
        audioPlayer.stop()
      canvas.clear()
      canvas.fill(Color(0, 128, 0))
      canvas.redraw()
    }
  )
  .configure(Canvas.Settings(width = 128, height = 128), LoopFrequency.hz60)
  .run()
