package eu.joaocosta.minart.graphics.image.qoi

import eu.joaocosta.minart.internal._

/** Image format for QOI files.
  */
final class QoiImageFormat[W](val byteWriter: ByteWriter[W]) extends QoiImageReader with QoiImageWriter[W]

object QoiImageFormat {
  val defaultFormat = new QoiImageFormat[Iterator[Array[Byte]]](
    ByteWriter.IteratorByteWriter
  )

  val supportedFormats = Set("qoif")
}
