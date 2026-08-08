package eu.joaocosta.minart.graphics.image.pdi

private[pdi] final case class Header(
    magic: String,
    compressed: Boolean
)
