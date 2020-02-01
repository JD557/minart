package eu.joaocosta.minart

import sdl2.SDL._
import sdl2.Extras._

import scalanative.native._

class SdlCanvas(
  val width: Int,
  val height: Int,
  val scale: Int = 1,
  val clearColor: Color = Color(255, 255, 255)) extends Canvas {

  SDL_Init(SDL_INIT_VIDEO)
  private[this] val window = SDL_CreateWindow(
    c"Minart",
    SDL_WINDOWPOS_CENTERED,
    SDL_WINDOWPOS_CENTERED,
    scaledWidth,
    scaledHeight,
    SDL_WINDOW_SHOWN)
  private[this] val surface = SDL_GetWindowSurface(window)
  private[this] val renderer = SDL_CreateSoftwareRenderer(surface)

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit = {
    SDL_SetRenderDrawColor(
      renderer,
      c.r.toUByte,
      c.g.toUByte,
      c.b.toUByte,
      0.toUByte)
    val rect = stackalloc[SDL_Rect].init(x * scale, y * scale, scale, scale)
    SDL_RenderFillRect(renderer, rect)
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit = {
    SDL_SetRenderDrawColor(
      renderer,
      c.r.toUByte,
      c.g.toUByte,
      c.b.toUByte,
      0.toUByte)
    SDL_RenderDrawPoint(renderer, x, y)
  }

  private[this] val _putPixel =
    if (scale == 1) { (x: Int, y: Int, c: Color) => putPixelUnscaled(x, y, c) }
    else { (x: Int, y: Int, c: Color) => putPixelScaled(x, y, c) }

  def putPixel(x: Int, y: Int, color: Color): Unit = _putPixel(x, y, color)

  def getBackbufferPixel(x: Int, y: Int): Color = {
    // Assuming a BGRA surface
    val baseAddr = 4 * (y * scale * scaledWidth + (x * scale) % scaledWidth)
    Color(
      surface.pixels(baseAddr + 2).toInt & 0xFF,
      surface.pixels(baseAddr + 1).toInt & 0xFF,
      surface.pixels(baseAddr + 0).toInt & 0xFF)
  }

  def clear(): Unit = {
    SDL_SetRenderDrawColor(
      renderer,
      clearColor.r.toUByte,
      clearColor.g.toUByte,
      clearColor.b.toUByte,
      0.toUByte)
    SDL_RenderClear(renderer)
  }

  def redraw(): Unit =
    SDL_UpdateWindowSurface(window)
}
