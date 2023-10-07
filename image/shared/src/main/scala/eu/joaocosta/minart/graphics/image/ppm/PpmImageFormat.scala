package eu.joaocosta.minart.graphics.image.ppm

/** Image format and writer for PGM/PPM files.
  *
  * Supports reading P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range
  * and stores data as P6 PPM files with a 8 bit color range.
  */
final class PpmImageFormat() extends PpmImageReader with PpmImageWriter

object PpmImageFormat {
  val defaultFormat = new PpmImageFormat()

  val supportedFormats = Set("P2", "P3", "P5", "P6")
}
