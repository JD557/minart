package eu.joaocosta.minart.audio.sound.wav

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.internal._

/** Audio format WAV files.
  */
final class WavAudioFormat[R](val byteReader: ByteReader[R]) extends WavAudioReader[R]

object WavAudioFormat {
  val defaultFormat =
    new WavAudioFormat[ByteReader.CustomInputStream](ByteReader.InputStreamByteReader)
}
