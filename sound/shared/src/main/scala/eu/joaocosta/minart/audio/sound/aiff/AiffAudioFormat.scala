package eu.joaocosta.minart.audio.sound.aiff

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.internal._

/** Audio format RTTTL files.
  */
final class AiffAudioFormat[R](val oscilator: Oscilator, val byteReader: ByteReader[R]) extends AiffAudioReader[R]

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat[ByteReader.CustomInputStream](Oscilator.sin, ByteReader.InputStreamByteReader)
}
