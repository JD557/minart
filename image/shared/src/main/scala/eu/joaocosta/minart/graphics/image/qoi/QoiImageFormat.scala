package eu.joaocosta.minart.graphics.image.qoi

import eu.joaocosta.minart.graphics.image.helpers._

/** Image format for QOI files.
  */
final class QoiImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F])
    extends QoiImageReader[F]
    with QoiImageWriter[F]

object QoiImageFormat {
  val defaultFormat = new QoiImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("qoif")
}
