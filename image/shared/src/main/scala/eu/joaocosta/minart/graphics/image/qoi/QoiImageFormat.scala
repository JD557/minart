package eu.joaocosta.minart.graphics.image.qoi

import eu.joaocosta.minart.graphics.image.helpers._

/** Image format for QOI files.
  */
final class QoiImageFormat[R, W[_]](val byteReader: ByteReader[R], val byteWriter: ByteWriter[W])
    extends QoiImageReader[R]
    with QoiImageWriter[W]

object QoiImageFormat {
  val defaultFormat = new QoiImageFormat[ByteReader.ModifiableInputStream, Iterator](
    ByteReader.InputStreamByteReader,
    ByteWriter.IteratorByteWriter
  )

  val supportedFormats = Set("qoif")
}
