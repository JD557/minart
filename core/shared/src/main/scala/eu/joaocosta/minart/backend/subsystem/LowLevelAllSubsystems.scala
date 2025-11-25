package eu.joaocosta.minart.backend.subsystem

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.graphics.*

/** Aggregation of all subsystems.
  */
final case class LowLevelAllSubsystems(
    lowLevelCanvas: LowLevelCanvas,
    lowLevelAudioPlayer: LowLevelAudioPlayer
) extends LowLevelSubsystem[(Canvas.Settings, AudioPlayer.Settings)] {
  def settings: (Canvas.Settings, AudioPlayer.Settings) = (lowLevelCanvas.settings, lowLevelAudioPlayer.settings)
  def isCreated(): Boolean                              = lowLevelCanvas.isCreated() && lowLevelAudioPlayer.isCreated()
  def init(settings: (Canvas.Settings, AudioPlayer.Settings)): this.type = {
    lowLevelCanvas.init(settings._1)
    lowLevelAudioPlayer.init(settings._2)
    this
  }
  def changeSettings(newSettings: (Canvas.Settings, AudioPlayer.Settings)): Unit = {
    lowLevelCanvas.changeSettings(newSettings._1)
    lowLevelAudioPlayer.changeSettings(newSettings._2)
  }
  def close(): Unit = {
    lowLevelCanvas.close()
    lowLevelAudioPlayer.close()
  }

  val canvas: Canvas           = lowLevelCanvas
  val audioPlayer: AudioPlayer = lowLevelAudioPlayer
}

object LowLevelAllSubsystems {
  given defaultLowLevelAllSubsystems(using
      c: DefaultBackend[Any, LowLevelCanvas],
      a: DefaultBackend[Any, LowLevelAudioPlayer]
  ): DefaultBackend[Any, LowLevelAllSubsystems] =
    DefaultBackend.fromFunction((_) => LowLevelAllSubsystems(c.defaultValue(), a.defaultValue()))
}
