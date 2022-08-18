package eu.joaocosta.minart.graphics.image.qoi

import java.io.InputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for QOI files.
  */
final class QoiImageFormat[F[_]](val byteReader: ByteReader[F], val byteWriter: ByteWriter[F]) extends QoiImageLoader[F]

object QoiImageFormat {
  val defaultFormat = new QoiImageFormat[Iterator](ByteReader.IteratorByteReader, ByteWriter.IteratorByteWriter)

  val supportedFormats = Set("qoif")
}
