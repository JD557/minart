package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {

  /** Enqueues an audio clip to be played later.
    */
  def play(wave: AudioClip): Unit

  /** Enqueues an audio clip to be played later in a certain channel.
    */
  def play(wave: AudioClip, channel: Int): Unit

  /** Checks if this player still has data to be played.
    */
  def isPlaying(): Boolean

  /** Stops playback and removes all enqueued waves.
    */
  def stop(): Unit

  /** Stops playback and removes all enqueued waves in a certain channel.
    */
  def stop(channel: Int): Unit
}

object AudioPlayer {

  /** Returns a new [[AudioPlayer]] for the default backend for the target platform.
    *
    * @return [[AudioPlayer]] using the default backend for the target platform
    */
  def create(settings: AudioPlayer.Settings)(implicit backend: DefaultBackend[Any, LowLevelAudioPlayer]): AudioPlayer =
    LowLevelAudioPlayer.create().init(settings)

  case class Settings(sampleRate: Int = 44100, bufferSize: Int = 4096)
}
