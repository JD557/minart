package eu.joaocosta.minart.graphics

import scala.scalanative.unsigned._

import sdl2.all._

import eu.joaocosta.minart.backend._

class SdlImageSurfaceSpec extends MutableSurfaceTests {
  lazy val surface = new SdlSurface(
    SDL_CreateRGBSurface(
      0.toUInt,
      64,
      48,
      32,
      0x00ff0000.toUInt,
      0x0000ff00.toUInt,
      0x000000ff.toUInt,
      0xff000000.toUInt
    )
  )

}
