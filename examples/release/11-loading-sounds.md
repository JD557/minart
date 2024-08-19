# 11. Loading sounds

Just like we did with images, we can also load audio clips from files.

Minart supports multiple audio formats (RTTL AIFF, WAV and QOA).

Here's a small example to play a clip from a file

## A simple audio player

### Dependencies and imports

The audio loaders are included in the `audio.sound` package, so we need to include that.

This package also has an `Sound` object with helpers to call the loaders.


```scala
//> using scala "3.3.3"
//> using dep "eu.joaocosta::minart::0.6.1"

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.backend.subsystem.*
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*
```

### Loading a sound

Just like loading an image, we can load sound resources.

```scala
val clip = Sound.loadAiffClip(Resource("assets/sample.aiff")).get
```

And then we can play it back using the same logic as in the audio playback example.

```scala
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
```
