package eu.joaocosta.minart.audio.sound

import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.runtime.Resource

/** Object containing user-friendly functions to audio clips. */
object Sound {

  /** Loads an sound clip using a custom AudioClipReader.
    *
    * @param loader AudioClipReader to use
    * @param resource Resource pointing to the audio clip
    */
  def loadClip(loader: AudioClipReader, resource: Resource): Try[AudioClip] = {
    loader.loadClip(resource).flatMap {
      case Left(error)   => Failure(new Exception(error))
      case Right(result) => Success(result)
    }
  }

  /** Loads an audio clip in the RTTTL format.
    */
  def loadRtttlClip(resource: Resource): Try[AudioClip] =
    loadClip(rtttl.RtttlAudioFormat.defaultFormat, resource)

  /** Loads an audio clip in the AIFF format.
    */
  def loadAiffClip(resource: Resource): Try[AudioClip] =
    loadClip(aiff.AiffAudioFormat.defaultFormat, resource)

  /** Loads an audio clip in the WAV format.
    */
  def loadWavClip(resource: Resource): Try[AudioClip] =
    loadClip(wav.WavAudioFormat.defaultFormat, resource)

  /** Loads an audio clip in the QOA format.
    */
  def loadQoaClip(resource: Resource): Try[AudioClip] =
    loadClip(qoa.QoaAudioFormat.defaultFormat, resource)

  /** Stores an audio clip using a custom AudioClipWriter.
    *
    * @param writer AudioClipWriter to use
    * @param clip AudioClip to store
    * @param resource Resource pointing to the output destination
    */
  def storeClip(writer: AudioClipWriter, clip: AudioClip, resource: Resource): Try[Unit] = {
    writer.storeClip(clip, resource).flatMap {
      case Left(error)   => Failure(new Exception(error))
      case Right(result) => Success(result)
    }
  }

  /** Stores an audio clip in the AIFF format.
    */
  def storeAiffClip(clip: AudioClip, resource: Resource): Try[Unit] =
    storeClip(aiff.AiffAudioFormat.defaultFormat, clip, resource)

  /** Stores an audio clip in the WAV format.
    */
  def storeWavClip(clip: AudioClip, resource: Resource): Try[Unit] =
    storeClip(wav.WavAudioFormat.defaultFormat, clip, resource)
}
