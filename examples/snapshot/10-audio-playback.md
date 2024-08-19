# 10. Audio playback

Besides graphics and input, Minart also supports loading and playing back audio.

Here we will see how to generate audio waves and play a simple audio clip.

## Playing Audio

### Dependencies and imports

```scala
//> using scala "3.3.3"
//> using dep "eu.joaocosta::minart::0.6.2-SNAPSHOT"

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.backend.subsystem.*
import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*
import eu.joaocosta.minart.runtime.*
```

To play audio, we now import the `eu.joaocosta.minart.audio` package.

We'll also use the graphics and input in this example, just to start/stop the audio when the user presses the space bar
and stops when they press backspace.

### Audio from functions

An audio wave is just a function from a time to an amplitude.

In Minart, `AudioWave`s can be created from a function that takes a time in milliseconds to an amplitude in [-1, 1].

Let's write a simple arpeggio using sin waves:

```scala
val song = (t: Double) => {
  val note =
    if (t < 0.3) 0               // A
    else if (t < 0.5) 4          // C#
    else if (t < 0.7) 7          // E
    else 12                      // A
  Math.pow(2, note / 12.0) * 440 // Convert the notes to frequencies (equal temperament)
}

val arpeggio: AudioClip =
  AudioWave.fromFunction((t: Double) => Math.sin(song(t) * 6.28 * t)).take(1.0)
```

We then take the first 1.0 seconds, to turn our `AudioWave` into an `AudioClip`.

An audio clip is jut a wave with a defined duration.

### Audio from oscilators

In the code above, you can see that we generated our audio by:
 - Defining a frequency and duration
 - Converting it to an amplitude using `Math.sin`

Turns out this use case is pretty common, so Minart provides some `Oscilator`s out of the box.

To combine the output from multiple oscilators, we can use `AudioClip#append`.

Here's a bass line generated using Oscilators:

```scala
val bass =
  Oscillator.sin
    .generateClip(duration = 0.5, frequency = 220, amplitude = 1.0)
    .append(Oscillator.sin.generateClip(duration = 0.5, frequency = 330, amplitude = 1.0))
    .append(Oscillator.sin.generateClip(duration = 1.0, frequency = 220, amplitude = 1.0))
```

### Mixing

Now we'll mix our arpeggio with our bass line, giving our arpeggio a bit more volume.

The recommended way to mix audio clips is with `AudioClip.mix`, which takes a sequence of clips
and mixes them in an efficient way. The duration of the resulting clip is the result of the smallest clip.

```scala
val lead = arpeggio.append(arpeggio.reverse).map(_ * 0.7)
val harmony = bass.map(_ * 0.3)
val clip = AudioClip.mix(List(lead, harmony))
```

### Putting it all together

Here we use `statelessAppLoop` instead of `statelessRenderLoop` so that we get an object with a `canvas` and an `audioPlayer`

```scala
AppLoop
  .statelessAppLoop((system: CanvasSubsystem with AudioPlayerSubsystem) => {
    import system._
    // When someone presses "Space", we send our sound wave to the queue
    if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Space))
      audioPlayer.play(clip)
    // When someone presses "Backspace", we stop the audio player
    if (canvas.getKeyboardInput().keysPressed.contains(KeyboardInput.Key.Backspace))
      audioPlayer.stop()
    canvas.clear()
    // Paint green when nothing is playing and red otherwise
    if (!audioPlayer.isPlaying()) canvas.fill(Color(0, 128, 0))
    else canvas.fill(Color(128, 0, 0))
    canvas.redraw()
  })
  .configure((Canvas.Settings(width = 128, height = 128), AudioPlayer.Settings()), LoopFrequency.hz60)
  .run()
```
