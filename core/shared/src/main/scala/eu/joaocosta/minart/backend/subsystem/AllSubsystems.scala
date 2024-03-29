package eu.joaocosta.minart.backend.subsystem

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.graphics.*

/** Internal object with an intersection of all subsystems.
  */
private[minart] class AllSubsystems(_canvas: LowLevelCanvas, _audioPlayer: LowLevelAudioPlayer)
    extends CanvasSubsystem(_canvas)
    with AudioPlayerSubsystem(_audioPlayer)
