package eu.joaocosta.minart.audio.sound.wav

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.internal._

/** Audio format WAV files.
  */
final class WavAudioFormat[R, W](val byteReader: ByteReader[R], val byteWriter: ByteWriter[W])
    extends WavAudioReader[R]
    with WavAudioWriter[W]

object WavAudioFormat {
  val defaultFormat =
    new WavAudioFormat[ByteReader.CustomInputStream, Iterator[Array[Byte]]](
      ByteReader.InputStreamByteReader,
      ByteWriter.IteratorByteWriter
    )
}
