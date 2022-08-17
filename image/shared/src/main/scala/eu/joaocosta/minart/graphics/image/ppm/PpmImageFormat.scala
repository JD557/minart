package eu.joaocosta.minart.graphics.image.ppm

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._
import eu.joaocosta.minart.graphics.image.ppm

/** Image loader and writer for PGM/PPM files.
  *
  * Supports P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range.
  */
final class PpmImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends PpmImageLoader[F]
    with PpmImageWriter[F]

object PpmImageFormat {
  val defaultFormat = new PpmImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("P2", "P3", "P5", "P6")
}
