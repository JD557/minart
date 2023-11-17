package eu.joaocosta.minart.graphics.image.qoi

/** Image format for QOI files.
  */
final class QoiImageFormat() extends QoiImageReader with QoiImageWriter

object QoiImageFormat {
  val defaultFormat = new QoiImageFormat()

  val supportedFormats = Set("qoif")
}
