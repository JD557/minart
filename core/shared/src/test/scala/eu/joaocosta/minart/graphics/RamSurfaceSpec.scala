package eu.joaocosta.minart.graphics

object RamSurfaceSpec extends MutableSurfaceTests {
  lazy val surface = new RamSurface(Vector.fill(64)(Array.fill(48)(Color(0, 0, 0))))
}
