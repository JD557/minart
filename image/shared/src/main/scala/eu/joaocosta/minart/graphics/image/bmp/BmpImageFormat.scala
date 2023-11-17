package eu.joaocosta.minart.graphics.image.bmp

/** Image reader and writer for BMP files.
  *
  * Supports reading uncompressed 24/32bit Windows BMPs and writing uncompressed 24 bit Windows BMPs.
  */
final class BmpImageFormat() extends BmpImageReader with BmpImageWriter

object BmpImageFormat {
  val defaultFormat = new BmpImageFormat()

  val supportedFormats = Set("BM")

  // Every line in a BMP file is padded to 4 bytes
  private[bmp] def linePadding(width: Int, bitsPerPixel: Int): Int = {
    val bytesPerPixel = bitsPerPixel / 8
    val bytesPerLine  = bytesPerPixel * width
    val extraBytes    = bytesPerLine % 4
    if (extraBytes == 0) 0 else (4 - extraBytes)
  }
}
