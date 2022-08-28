package eu.joaocosta.minart.graphics.image.bmp

import eu.joaocosta.minart.graphics.image.helpers._

/** Image reader and writer for BMP files.
  *
  * Supports reading uncompressed 24/32bit Windows BMPs and writing uncompressed 24 bit Windows BMPs.
  */
final class BmpImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends BmpImageReader[F]
    with BmpImageWriter[F]

object BmpImageFormat {
  val defaultFormat = new BmpImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("BM")
}
