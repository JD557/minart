package eu.joaocosta.minart.audio.sound.aiff

import eu.joaocosta.minart.internal._

/** Audio format AIFF files.
  */
final class AiffAudioFormat[R, W](val byteReader: ByteReader[R], val byteWriter: ByteWriter[W], val bitRate: Int)
    extends AiffAudioReader[R]
    with AiffAudioWriter[W]

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat[ByteReader.CustomInputStream, Iterator[Array[Byte]]](
      ByteReader.InputStreamByteReader,
      ByteWriter.IteratorByteWriter,
      16
    )
}
