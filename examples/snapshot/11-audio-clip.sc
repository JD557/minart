//> using scala "3.3.1"
//> using lib "eu.joaocosta::minart::0.5.4-SNAPSHOT"

/** Just like images, it can also be convenient to load sound files.
  * The minart-sound project includes some sound formats to get started.
  *
  * Note: This is an experimental API, it might break in a future version
  */
import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.backend.subsystem.*
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*

/** Just like loading an image, we can load sound resources
  */
val clip = Sound.loadAiffClip(Resource("assets/sample.aiff")).get

// Same logic as the audio example
AppLoop
  .statelessAppLoop((system: CanvasSubsystem with AudioPlayerSubsystem) => {
    import system._
    if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
      audioPlayer.play(clip)
    if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
      audioPlayer.stop()
    canvas.clear()
    if (!audioPlayer.isPlaying()) canvas.fill(Color(0, 128, 0))
    else canvas.fill(Color(128, 0, 0))
    canvas.redraw()
  })
  .configure((Canvas.Settings(width = 128, height = 128), AudioPlayer.Settings()), LoopFrequency.hz60)
  .run()
