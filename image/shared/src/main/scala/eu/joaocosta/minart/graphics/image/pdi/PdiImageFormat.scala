package eu.joaocosta.minart.graphics.image.pdi

/** Image reader and writer for PDI files.
  *
  * Supports reading and writing uncompressed Playdate PDIs.
  */
final class PdiImageFormat() extends PdiImageReader with PdiImageWriter

object PdiImageFormat {
  val defaultFormat = new PdiImageFormat()

  val supportedFormats = Set("Playdate IMG")
}
