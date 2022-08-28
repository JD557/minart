package eu.joaocosta.minart.graphics.image.ppm

import eu.joaocosta.minart.graphics.image.helpers._

/** Image format and writer for PGM/PPM files.
  *
  * Supports reading P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range
  * and stores data as P6 PPM files with a 8 bit color range.
  */
final class PpmImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends PpmImageReader[F]
    with PpmImageWriter[F]

object PpmImageFormat {
  val defaultFormat = new PpmImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("P2", "P3", "P5", "P6")
}
