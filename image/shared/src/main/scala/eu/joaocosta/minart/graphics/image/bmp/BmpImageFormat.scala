package eu.joaocosta.minart.graphics.image.bmp

import java.io.InputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader and writer for BMP files.
  *
  * Supports uncompressed 24/32bit Windows BMPs.
  */
final class BmpImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends BmpImageReader[F]
    with BmpImageWriter[F]

object BmpImageFormat {
  val defaultFormat = new BmpImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("BM")
}
