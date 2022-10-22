package eu.joaocosta.minart.graphics.image.bmp

import eu.joaocosta.minart.internal._

/** Image reader and writer for BMP files.
  *
  * Supports reading uncompressed 24/32bit Windows BMPs and writing uncompressed 24 bit Windows BMPs.
  */
final class BmpImageFormat[R, W](val byteReader: ByteReader[R], val byteWriter: ByteWriter[W])
    extends BmpImageReader[R]
    with BmpImageWriter[W]

object BmpImageFormat {
  val defaultFormat = new BmpImageFormat[ByteReader.CustomInputStream, Iterator[Array[Byte]]](
    ByteReader.InputStreamByteReader,
    ByteWriter.IteratorByteWriter
  )

  val supportedFormats = Set("BM")

  // Every line in a BMP file is padded to 4 bytes
  private[bmp] def linePadding(width: Int, bitsPerPixel: Int): Int = {
    val bytesPerPixel = bitsPerPixel / 8
    val bytesPerLine  = bytesPerPixel * width
    val extraBytes    = bytesPerLine % 4
    if (extraBytes == 0) 0 else (4 - extraBytes)
  }
}
