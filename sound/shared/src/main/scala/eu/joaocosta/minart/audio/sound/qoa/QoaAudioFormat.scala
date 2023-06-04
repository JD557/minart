package eu.joaocosta.minart.audio.sound.qoa

import eu.joaocosta.minart.internal._

/** Audio format QOA files.
  */
final class QoaAudioFormat[R](val byteReader: ByteReader[R]) extends QoaAudioReader[R]

object QoaAudioFormat {
  val defaultFormat =
    new QoaAudioFormat[ByteReader.CustomInputStream](
      ByteReader.InputStreamByteReader
    )
}
