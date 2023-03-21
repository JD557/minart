package eu.joaocosta.minart.audio.sound

import java.io.{ByteArrayOutputStream, OutputStream}

import scala.util.Try

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.runtime.Resource

/** Audio Clip writer with a low-level implementation on how to load an audio clip.
  */
trait AudioClipWriter {

  /** Stores an audio clip to an OutputStream.
    *
    * @param clip AudioClip to store
    * @param os OutputStream where to store the data
    * @return Either unit or an error string
    */
  def storeClip(clip: AudioClip, os: OutputStream): Either[String, Unit]

  /** Stores an audio clip to a Resource.
    *
    * @param clip AudioCLip to store
    * @param resource Resource where to store the data
    * @return Either unit or an error string, inside a Try capturing the IO exceptions
    */
  def storeClip(clip: AudioClip, resource: Resource): Try[Either[String, Unit]] =
    resource.withOutputStream(os => storeClip(clip, os))

  /** Returns the audio clip data as a byte array.
    *
    * @param clip AudioClip to convert
    * @return Either an array with the clip data or an error string
    */
  def toByteArray(clip: AudioClip): Either[String, Array[Byte]] = {
    val os = new ByteArrayOutputStream()
    storeClip(clip, os).map(_ => os.toByteArray)
  }
}
