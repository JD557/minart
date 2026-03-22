package eu.joaocosta.minart.graphics.image.kitty

/** Image format and writer for Kitty image protocol.
  *
  * Only supports basic direct image rendering
  */
final class KittyImageFormat() extends KittyImageWriter
object KittyImageFormat {
  val defaultFormat = new KittyImageFormat()

  val supportedFormats = Set("_G")
}
