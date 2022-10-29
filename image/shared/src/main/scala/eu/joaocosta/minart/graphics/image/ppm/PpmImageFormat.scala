package eu.joaocosta.minart.graphics.image.ppm

import eu.joaocosta.minart.internal._

/** Image format and writer for PGM/PPM files.
  *
  * Supports reading P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range
  * and stores data as P6 PPM files with a 8 bit color range.
  */
final class PpmImageFormat[R, W](val byteReader: ByteReader[R], val byteWriter: ByteWriter[W])
    extends PpmImageReader[R]
    with PpmImageWriter[W]

object PpmImageFormat {
  val defaultFormat = new PpmImageFormat[ByteReader.CustomInputStream, Iterator[Array[Byte]]](
    ByteReader.InputStreamByteReader,
    ByteWriter.IteratorByteWriter
  )

  val supportedFormats = Set("P2", "P3", "P5", "P6")
}
