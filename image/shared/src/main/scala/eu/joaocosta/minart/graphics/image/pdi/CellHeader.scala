package eu.joaocosta.minart.graphics.image.pdi

private[pdi] final case class CellHeader(
    clipWidth: Int,
    clipHeight: Int,
    stride: Int,
    clipLeft: Int,
    clipRight: Int,
    clipTop: Int,
    clipBottom: Int,
    transparency: Boolean
)
