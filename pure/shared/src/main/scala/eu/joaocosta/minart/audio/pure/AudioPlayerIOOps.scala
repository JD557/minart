package eu.joaocosta.minart.audio.pure

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime.pure._

/** Representation of an audio player operation, with the common Monad operations.
  */
trait AudioPlayerIOOps {

  /** Store an unsafe audio player operation in an [[AudioPlayerIO]]. */
  def accessAudioPlayer[A](f: AudioPlayer => A): AudioPlayerIO[A] =
    RIO.access[AudioPlayer, A](f)

  /** Enqueues an audio clip to be played later in channel 0.
    *
    *  @param clip audio clip to play
    */
  def play(clip: AudioClip): AudioPlayerIO[Unit] = accessAudioPlayer(_.play(clip))

  /** Enqueues an audio clip to be played later in a certain channel.
    *
    *  @param clip audio clip to play
    *  @param channel channel where to play the audio clip
    */
  def play(clip: AudioClip, channel: Int): AudioPlayerIO[Unit] = accessAudioPlayer(_.play(clip, channel))

  /** Enqueues an audio wave to be played later in channel 0.
    * The Audio Wave will play infinitely until stop() is called.
    *
    *  @param wave audio wave to play
    */
  def play(wave: AudioWave): AudioPlayerIO[Unit] = accessAudioPlayer(_.play(wave))

  /** Enqueues an audio wave to be played later in a certain channel.
    *  The Audio Wave will play infinitely until stop() is called.
    *  @param wave audio wave to play
    *  @param channel channel where to play the audio wave
    */
  def play(wave: AudioWave, channel: Int): AudioPlayerIO[Unit] = accessAudioPlayer(_.play(wave, channel))

  /** Checks if this player still has data to be played.
    */
  val isPlaying: AudioPlayerIO[Boolean] = accessAudioPlayer(_.isPlaying())

  /** Stops playback and removes all enqueued waves.
    */
  val stop: AudioPlayerIO[Unit] = accessAudioPlayer(_.stop())

  /** Stops playback and removes all enqueued waves in a certain channel.
    */
  def stop(channel: Int): AudioPlayerIO[Unit] = accessAudioPlayer(_.stop(channel))
}
