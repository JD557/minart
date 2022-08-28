package eu.joaocosta.minart.graphics.image.ppm

import java.io.InputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image format and writer for PGM/PPM files.
  *
  * Supports P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range.
  */
final class PpmImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends PpmImageReader[F]
    with PpmImageWriter[F]

object PpmImageFormat {
  val defaultFormat = new PpmImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("P2", "P3", "P5", "P6")
}
