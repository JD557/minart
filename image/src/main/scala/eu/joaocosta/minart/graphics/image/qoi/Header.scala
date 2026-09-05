package eu.joaocosta.minart.graphics.image.qoi

private[qoi] final case class Header(
    magic: String,
    width: Long,
    height: Long,
    channels: Byte,
    colorspace: Byte
)
