package eu.joaocosta.minart.graphics.image.bmp

final case class Header(
    magic: String,
    size: Int,
    offset: Int,
    width: Int,
    height: Int,
    bitsPerPixel: Int
)
