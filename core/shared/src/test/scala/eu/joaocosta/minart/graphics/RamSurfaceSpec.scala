package eu.joaocosta.minart.graphics

import verify._

import eu.joaocosta.minart.backend._
import eu.joaocosta.minart.runtime._

object RamSurfaceSpec extends MutableSurfaceTests {
  lazy val surface = new RamSurface(Vector.fill(64)(Array.fill(48)(Color(0, 0, 0))))
}
