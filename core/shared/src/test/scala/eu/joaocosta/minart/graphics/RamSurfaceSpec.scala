package eu.joaocosta.minart.graphics

object RamSurfaceSpec extends MutableSurfaceTests {
  lazy val surface = new RamSurface(48, 64, Color(0, 0, 0))
}
