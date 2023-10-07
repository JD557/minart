package eu.joaocosta.minart.backend.subsystem

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** Aggregation of all subsystems.
  */
case class LowLevelAllSubsystems(
    lowLevelCanvas: LowLevelCanvas,
    lowLevelAudioPlayer: LowLevelAudioPlayer
) extends AllSubsystems(lowLevelCanvas, lowLevelAudioPlayer)
    with LowLevelSubsystem[(Canvas.Settings, AudioPlayer.Settings)] {
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
}

object LowLevelAllSubsystems {
  implicit def defaultLowLevelAllSubsystems(implicit
      c: DefaultBackend[Any, LowLevelCanvas],
      a: DefaultBackend[Any, LowLevelAudioPlayer]
  ): DefaultBackend[Any, LowLevelAllSubsystems] =
    DefaultBackend.fromFunction((_) => LowLevelAllSubsystems(c.defaultValue(), a.defaultValue()))
}
