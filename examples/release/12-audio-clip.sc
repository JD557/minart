//> using scala "3.3.0"
//> using lib "eu.joaocosta::minart::0.5.2"

/** Just like images, it can also be convenient to load sound files.
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

/** Just like loading an image, we can load sound resources
  */
val clip = Sound.loadAiffClip(Resource("assets/sample.aiff")).get

// Same logic as the audio example
AppLoop
  .statelessAppLoop((system: Canvas with AudioPlayer) => {
    if (system.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
      system.play(clip)
    if (system.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
      system.stop()
    system.clear()
    if (!system.isPlaying()) system.fill(Color(0, 128, 0))
    else system.fill(Color(128, 0, 0))
    system.redraw()
  })
  .configure((Canvas.Settings(width = 128, height = 128), AudioPlayer.Settings()), LoopFrequency.hz60)
  .run()
