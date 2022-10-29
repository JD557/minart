package eu.joaocosta.minart.audio.sound.rtttl

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.internal._

/** Audio format RTTTL files.
  */
final class RtttlAudioFormat[R](val oscilator: Oscilator, val byteReader: ByteReader[R]) extends RtttlAudioReader[R]

object RtttlAudioFormat {
  val defaultFormat =
    new RtttlAudioFormat[ByteReader.CustomInputStream](Oscilator.sin, ByteReader.InputStreamByteReader)
}
