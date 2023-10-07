package eu.joaocosta.minart.audio.sound.wav

import eu.joaocosta.minart.internal._

/** Audio format WAV files.
  */
final class WavAudioFormat[W](val byteWriter: ByteWriter[W], val bitRate: Int)
    extends WavAudioReader
    with WavAudioWriter[W]

object WavAudioFormat {
  val defaultFormat =
    new WavAudioFormat[Iterator[Array[Byte]]](
      ByteWriter.IteratorByteWriter,
      16
    )
}
