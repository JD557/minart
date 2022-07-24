package eu.joaocosta.minart.graphics

import java.awt.image.BufferedImage

import eu.joaocosta.minart.backend._

object BufferedImageSurfaceSpec extends MutableSurfaceTests {
  lazy val image   = new BufferedImage(64, 48, BufferedImage.TYPE_INT_ARGB)
  lazy val surface = new BufferedImageSurface(image)

}
