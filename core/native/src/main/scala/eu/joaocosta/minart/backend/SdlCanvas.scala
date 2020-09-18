package eu.joaocosta.minart.backend

import scalanative.native._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core.KeyboardInput.Key
import eu.joaocosta.minart.core._

/**
 * A low level Canvas implementation that shows the image in a SDL Window.
 */
class SdlCanvas(val settings: Canvas.Settings) extends LowLevelCanvas {

  private[this] var window: Ptr[SDL_Window] = _
  private[this] var surface: Ptr[SDL_Surface] = _
  private[this] var buffer: Ptr[SDL_Surface] = _
  private[this] var renderer: Ptr[SDL_Renderer] = _
  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  private[this] var mouseInput: PointerInput = PointerInput(None, Nil, Nil, false)

  def unsafeInit() = {
    SDL_Init(SDL_INIT_VIDEO)
    window = SDL_CreateWindow(
      c"Minart",
      SDL_WINDOWPOS_CENTERED,
      SDL_WINDOWPOS_CENTERED,
      settings.scaledWidth,
      settings.scaledHeight,
      SDL_WINDOW_SHOWN)
    surface = SDL_GetWindowSurface(window)
    renderer = SDL_CreateSoftwareRenderer(surface)
    keyboardInput = KeyboardInput(Set(), Set(), Set())
  }
  def unsafeDestroy() = {
    SDL_Quit()
  }

  private[this] val ubyteClearR = settings.clearColor.r.toUByte
  private[this] val ubyteClearG = settings.clearColor.g.toUByte
  private[this] val ubyteClearB = settings.clearColor.b.toUByte

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit = {
    var dy = 0
    while (dy < settings.scale) {
      var dx = 0
      val lineBase = (y * settings.scale + dy) * settings.scaledWidth
      while (dx < settings.scale) {
        val baseAddr = 4 * (lineBase + (x * settings.scale + dx))
        surface.pixels(baseAddr + 0) = c.b.toByte
        surface.pixels(baseAddr + 1) = c.g.toByte
        surface.pixels(baseAddr + 2) = c.r.toByte
        dx = dx + 1
      }
      dy = dy + 1
    }
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit = {
    // Assuming a BGRA surface
    val lineBase = y * settings.scaledWidth
    val baseAddr = 4 * (lineBase + x)
    surface.pixels(baseAddr + 0) = c.b.toByte
    surface.pixels(baseAddr + 1) = c.g.toByte
    surface.pixels(baseAddr + 2) = c.r.toByte
  }

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (settings.scale == 1) putPixelUnscaled(x, y, color)
    else putPixelScaled(x, y, color)

  def getBackbufferPixel(x: Int, y: Int): Color = {
    // Assuming a BGRA surface
    val baseAddr = 4 * (y * settings.scale * settings.scaledWidth + (x * settings.scale))
    Color(
      surface.pixels(baseAddr + 2).toInt & 0xFF,
      surface.pixels(baseAddr + 1).toInt & 0xFF,
      surface.pixels(baseAddr + 0).toInt & 0xFF)
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    (0 until settings.height).map { y =>
      val lineBase = y * settings.scale * settings.scaledWidth
      (0 until settings.width).map { x =>
        val baseAddr = 4 * (lineBase + (x * settings.scale))
        Color(
          surface.pixels(baseAddr + 2).toInt & 0xFF,
          surface.pixels(baseAddr + 1).toInt & 0xFF,
          surface.pixels(baseAddr + 0).toInt & 0xFF)
      }.toVector
    }.toVector
  }

  private[this] def handleEvents(): Boolean = {
    val event = stackalloc[SDL_Event]
    var keepGoing: Boolean = true
    while (keepGoing && SDL_PollEvent(event) != 0) {
      keepGoing = event.type_ match {
        case SDL_QUIT =>
          destroy()
          false
        case SDL_KEYDOWN =>
          SdlKeyMapping.getKey(event.key.keysym.sym).foreach(k => keyboardInput = keyboardInput.press(k))
          true
        case SDL_KEYUP =>
          SdlKeyMapping.getKey(event.key.keysym.sym).foreach(k => keyboardInput = keyboardInput.release(k))
          true
        case SDL_MOUSEMOTION =>
          mouseInput = mouseInput.move(
            Option(PointerInput.Position(event.motion.x / settings.scale, event.motion.y / settings.scale)))
          true
        case SDL_MOUSEBUTTONDOWN =>
          mouseInput = mouseInput.press
          true
        case SDL_MOUSEBUTTONUP =>
          mouseInput = mouseInput.release
          true
        case _ =>
          true
      }
    }
    keepGoing
  }

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Keyboard)) {
      keyboardInput = keyboardInput.clearPressRelease()
    }
    if (resources.contains(Canvas.Resource.Pointer)) {
      mouseInput = mouseInput.clearPressRelease()
    }
    val keepGoing = handleEvents()
    if (resources.contains(Canvas.Resource.Backbuffer) && keepGoing) {
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
  def getPointerInput(): PointerInput = mouseInput
}
