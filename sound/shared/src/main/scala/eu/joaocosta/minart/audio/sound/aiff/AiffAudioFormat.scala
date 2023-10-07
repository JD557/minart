package eu.joaocosta.minart.audio.sound.aiff

import eu.joaocosta.minart.internal._

/** Audio format AIFF files.
  */
final class AiffAudioFormat[W](val byteWriter: ByteWriter[W], val bitRate: Int)
    extends AiffAudioReader
    with AiffAudioWriter[W]

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat[Iterator[Array[Byte]]](
      ByteWriter.IteratorByteWriter,
      16
    )
}
