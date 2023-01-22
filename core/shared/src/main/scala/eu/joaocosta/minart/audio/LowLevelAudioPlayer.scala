package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.backend.subsystem.LowLevelSubsystem

/** A low-level version of a audio player that provides its own manager.
  */
trait LowLevelAudioPlayer extends AudioPlayer with LowLevelSubsystem.Simple[AudioPlayer.Settings] {
  protected lazy val defaultSettings: AudioPlayer.Settings = AudioPlayer.Settings()
}

object LowLevelAudioPlayer {

  /** Returns a new [[LowLevelAudioPlayer]] for the default backend for the target platform.
    *
    * @return [[LowLevelAudioPlayer]] using the default backend for the target platform
    */
  def create()(implicit backend: DefaultBackend[Any, LowLevelAudioPlayer]): LowLevelAudioPlayer =
    backend.defaultValue()
}
