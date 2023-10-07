package eu.joaocosta.minart.backend.subsystem

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** Internal object with an intersection of all subsystems.
  */
private[minart] class AllSubsystems(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer)
    extends LowLevelSubsystem.Composite[Canvas.Settings, AudioPlayer.Settings, LowLevelCanvas, LowLevelAudioPlayer](
      canvas,
      audioPlayer
    )
    with Canvas
    with AudioPlayer {

  private val _canvas: Canvas = canvas // Export only Canvas methods
  export _canvas.*

  private val _audioPlayer: AudioPlayer = audioPlayer // Export only AudioPlayer methods
  export _audioPlayer.*
}
