//> using scala "3.2.0"
//> using lib "eu.joaocosta::minart::0.4.4-SNAPSHOT"

/**
 * Just like we can load image from external resources, we can load sounds.
 * The minart-sound library and audio.sound includes some codecs out of the box.
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
 * Just like loading an image, we can load a sound file
 */
val sound = Sound.loadRtttlClip(Resource("assets/ringtone.rtttl")).get

// Here we have exactly the same logic from the audio tutorial
ImpureRenderLoop
  .statelessRenderLoop(
    canvas => {
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
        AudioPlayer().play(sound)
      if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
        AudioPlayer().stop()
      canvas.clear()
      canvas.fill(Color(0, 128, 0))
      canvas.redraw()
    },
    LoopFrequency.hz60
  )
  .run(Canvas.Settings(width = 128, height = 128))
