package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {

  /** Enqueues an audio clip to be played later in channel 0.
    */
  def play(clip: AudioClip): Unit = play(clip, 0)

  /** Enqueues an audio clip to be played later in a certain channel.
    */
  def play(clip: AudioClip, channel: Int): Unit

  /** Enqueues an audio wave to be played later in channel 0.
    *  The Audio Wave will play infinitely until stop() is called.
    */
  def play(wave: AudioWave): Unit =
    play(wave, 0)

  /** Enqueues an audio wave to be played later in a certain channel.
    *  The Audio Wave will play infinitely until stop() is called.
    */
  def play(wave: AudioWave, channel: Int): Unit =
    play(AudioClip(wave, Double.PositiveInfinity))

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
