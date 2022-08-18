package eu.joaocosta.minart.graphics.image.qoi

final case class Header(
    magic: String,
    width: Long,
    height: Long,
    channels: Byte,
    colorspace: Byte
)
