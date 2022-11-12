package eu.joaocosta.minart.audio.sound.aiff

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.internal._

/** Audio format AIFF files.
  */
final class AiffAudioFormat[R](val byteReader: ByteReader[R]) extends AiffAudioReader[R]

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat[ByteReader.CustomInputStream](ByteReader.InputStreamByteReader)
}
