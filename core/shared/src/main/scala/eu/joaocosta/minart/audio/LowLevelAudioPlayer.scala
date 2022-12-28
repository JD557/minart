package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

/** A low-level version of a audio player that provides its own manager.
  */
trait LowLevelAudioPlayer extends AudioPlayer with AutoCloseable {
  private[this] var _settings: AudioPlayer.Settings = _
  def settings: AudioPlayer.Settings =
    if (_settings == null) AudioPlayer.Settings()
    else _settings

  protected def unsafeInit(settings: AudioPlayer.Settings): Unit
  protected def unsafeDestroy(): Unit

  /** Checks if the audio player is created or if it has been destroyed
    */
  def isCreated(): Boolean = !(_settings == null)

  /** Creates the audio player.
    *
    * Playback operations can only be called after calling this.
    */
  def init(settings: AudioPlayer.Settings): Unit = {
    if (isCreated()) {
      close()
    }
    if (!isCreated()) {
      unsafeInit(settings)
    }
  }

  /** Destroys the audio player.
    *
    * Calling any operation on this player after calling close() without calling
    * init() has an undefined behavior.
    */
  def close(): Unit = if (isCreated()) {
    unsafeDestroy()
    _settings = null
  }
}

object LowLevelAudioPlayer {

  /** Returns a new [[LowLevelAudioPlayer]] for the default backend for the target platform.
    *
    * @return [[LowLevelAudioPlayer]] using the default backend for the target platform
    */
  def create()(implicit backend: DefaultBackend[Any, LowLevelAudioPlayer]): LowLevelAudioPlayer =
    backend.defaultValue()
}
