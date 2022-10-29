package eu.joaocosta.minart.audio.sound

import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.audio._
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
}
