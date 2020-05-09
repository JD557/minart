package eu.joaocosta.minart.backend

import scalanative.native._

import sdl2.SDL._
import sdl2.Extras._

import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

class SdlCanvas(
  val width: Int,
  val height: Int,
  val scale: Int = 1,
  val clearColor: Color = Color(255, 255, 255)) extends LowLevelCanvas {

  private[this] var window: Ptr[SDL_Window] = _
  private[this] var surface: Ptr[SDL_Surface] = _
  private[this] var renderer: Ptr[SDL_Renderer] = _
  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())

  def unsafeInit() = {
    SDL_Init(SDL_INIT_VIDEO)
    window = SDL_CreateWindow(
      c"Minart",
      SDL_WINDOWPOS_CENTERED,
      SDL_WINDOWPOS_CENTERED,
      scaledWidth,
      scaledHeight,
      SDL_WINDOW_SHOWN)
    surface = SDL_GetWindowSurface(window)
    renderer = SDL_CreateSoftwareRenderer(surface)
    keyboardInput = KeyboardInput(Set(), Set(), Set())
  }
  def unsafeDestroy() = {
    SDL_Quit()
  }

  private[this] val ubyteClearR = clearColor.r.toUByte
  private[this] val ubyteClearG = clearColor.g.toUByte
  private[this] val ubyteClearB = clearColor.b.toUByte

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

  private[this] def handleEvents(): Boolean = {
    val event = stackalloc[SDL_Event]
    while (SDL_PollEvent(event) != 0) {
      if (event.type_ == SDL_QUIT) {
        destroy()
        return false
      } else if (event.type_ == SDL_KEYDOWN) {
        SdlCanvas.convertKeyCode(event.key.keysym.sym).foreach(k => keyboardInput = keyboardInput.press(k))
      } else if (event.type_ == SDL_KEYUP) {
        SdlCanvas.convertKeyCode(event.key.keysym.sym).foreach(k => keyboardInput = keyboardInput.release(k))
      }
    }
    true
  }

  def clear(): Unit = {
    keyboardInput = keyboardInput.clearPressRelease()
    if (handleEvents()) {
      SDL_SetRenderDrawColor(
        renderer,
        ubyteClearR,
        ubyteClearG,
        ubyteClearB,
        0.toUByte)
      SDL_RenderClear(renderer)
    }
  }

  def redraw(): Unit = {
    if (handleEvents()) SDL_UpdateWindowSurface(window)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
}

object SdlCanvas {
  private def convertKeyCode(code: SDL_Keycode): Option[KeyboardInput.Key] = code match {
    case SDLK_UP => Some(Key.Up)
    case SDLK_DOWN => Some(Key.Down)
    case SDLK_LEFT => Some(Key.Left)
    case SDLK_RIGHT => Some(Key.Right)
    case _ => None
  }
}
