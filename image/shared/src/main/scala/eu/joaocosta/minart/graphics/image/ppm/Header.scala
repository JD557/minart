package eu.joaocosta.minart.graphics.image.ppm

final case class Header(
    magic: String,
    width: Int,
    height: Int,
    colorRange: Int
)
