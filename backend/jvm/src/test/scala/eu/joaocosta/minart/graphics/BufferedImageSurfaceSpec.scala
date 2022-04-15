package eu.joaocosta.minart.graphics

import java.awt.image.BufferedImage

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

object BufferedImageSurfaceSpec extends MutableSurfaceTests {
  lazy val image   = new BufferedImage(64, 48, BufferedImage.TYPE_INT_ARGB)
  lazy val surface = new BufferedImageSurface(image)

}
