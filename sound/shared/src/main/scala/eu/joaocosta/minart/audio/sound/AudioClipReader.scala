package eu.joaocosta.minart.audio.sound

import java.io.{ByteArrayInputStream, InputStream}

import scala.util.Try

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.runtime.Resource

/** Audio Clip reader with a low-level implementation on how to load an audio clip.
  */
trait AudioClipReader {

  /** Loads an audio clip from an InputStream.
    *
    * @param is InputStream with the audio clip data
    * @return Either a AudioClip with the audio clip data or an error string
    */
  def loadClip(is: InputStream): Either[String, AudioClip]

  /** Loads an audio clip from a Resource.
    *
    * @param resource Resource with the audio clip data
    * @return Either a AudioClip with the audio clip data or an error string, inside a Try capturing the IO exceptions
    */
  def loadClip(resource: Resource): Try[Either[String, AudioClip]] =
    resource.withInputStream(is => loadClip(is))

  /** Loads an audio clip from a byte array.
    *
    * @param data Byte array
    * @return Either a AudioClip with the audio data or an error string
    */
  def fromByteArray(data: Array[Byte]): Either[String, AudioClip] = {
    val is = new ByteArrayInputStream(data)
    loadClip(is)
  }
}
