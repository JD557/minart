package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

/** Multi-channel mono audio player.
  *
  * Can play and stop audio clips and audio waves on distinct channels.
  */
trait AudioPlayer {

  /** Enqueues an audio clip to be played later in channel 0.
    *
    *  @param clip audio clip to play
    */
  final def play(clip: AudioClip): Unit = play(clip, 0)

  /** Enqueues an audio clip to be played later in a certain channel.
    *
    *  @param clip audio clip to play
    *  @param channel channel where to play the audio clip
    */
  def play(clip: AudioClip, channel: Int): Unit

  /** Enqueues an audio wave to be played later in channel 0.
    * The Audio Wave will play infinitely until stop() is called.
    *
    *  @param wave audio wave to play
    */
  final def play(wave: AudioWave): Unit = play(wave, 0)

  /** Enqueues an audio wave to be played later in a certain channel.
    *  The Audio Wave will play infinitely until stop() is called.
    *  @param wave audio wave to play
    *  @param channel channel where to play the audio wave
    */
  final def play(wave: AudioWave, channel: Int): Unit =
    play(AudioClip(wave, Double.PositiveInfinity), channel)

  /** Checks if this player still has data to be played.
    *
    *  @return true of the player is still playing, false otherwise
    */
  def isPlaying(): Boolean

  /** Checks if a channel still has data to be played.
    *
    *  @param channel channel to check
    *  @return true of the channel is still playing, false otherwise
    */
  def isPlaying(channel: Int): Boolean

  /** Stops playback and removes all enqueued waves.
    */
  def stop(): Unit

  /** Stops playback and removes all enqueued waves in a certain channel.
    *
    *  @param channel channel to stop
    */
  def stop(channel: Int): Unit
}

object AudioPlayer {

  /** Returns a new [[AudioPlayer]] for the default backend for the target platform.
    *
    * @param settings settings for this audio player, such as the sample rate and buffer size
    * @return [[AudioPlayer]] using the default backend for the target platform
    */
  def create(settings: AudioPlayer.Settings)(using backend: DefaultBackend[Any, LowLevelAudioPlayer]): AudioPlayer =
    LowLevelAudioPlayer.create().init(settings)

  final case class Settings(sampleRate: Int = 44100, bufferSize: Int = 4096)
}
