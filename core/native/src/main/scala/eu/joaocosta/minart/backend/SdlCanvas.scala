package eu.joaocosta.minart.backend

import scalanative.unsafe._
import scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input.KeyboardInput.Key
import eu.joaocosta.minart.input._

/** A low level Canvas implementation that shows the image in a SDL Window.
  */
class SdlCanvas() extends LowLevelCanvas {

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  private[this] var window: Ptr[SDL_Window]      = _
  private[this] var surface: Ptr[SDL_Surface]    = _
  private[this] var buffer: Ptr[SDL_Surface]     = _
  private[this] var renderer: Ptr[SDL_Renderer]  = _
  private[this] var keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())
  private[this] var rawMousePos: (Int, Int)      = _
  private[this] def cleanMousePos: Option[PointerInput.Position] = Option(rawMousePos).map { case (x, y) =>
    PointerInput.Position(
      (x - extendedSettings.canvasX) / settings.scale,
      (y - extendedSettings.canvasY) / settings.scale
    )
  }
  private[this] var mouseInput: PointerInput = PointerInput(None, Nil, Nil, false)

  private[this] var ubyteClearR: UByte = _
  private[this] var ubyteClearG: UByte = _
  private[this] var ubyteClearB: UByte = _

  def unsafeInit(newSettings: Canvas.Settings) = {
    SDL_Init(SDL_INIT_VIDEO)
    changeSettings(newSettings)
  }
  def unsafeDestroy() = {
    SDL_Quit()
  }
  def changeSettings(newSettings: Canvas.Settings) = if (extendedSettings == null || newSettings != settings) {
    extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
    SDL_DestroyWindow(window)
    ubyteClearR = newSettings.clearColor.r.toUByte
    ubyteClearG = newSettings.clearColor.g.toUByte
    ubyteClearB = newSettings.clearColor.b.toUByte
    window = SDL_CreateWindow(
      c"Minart",
      SDL_WINDOWPOS_CENTERED,
      SDL_WINDOWPOS_CENTERED,
      extendedSettings.scaledWidth,
      extendedSettings.scaledHeight,
      if (extendedSettings.settings.fullScreen) SDL_WINDOW_FULLSCREEN_DESKTOP
      else SDL_WINDOW_SHOWN
    )
    surface = SDL_GetWindowSurface(window)
    renderer = SDL_CreateSoftwareRenderer(surface)
    keyboardInput = KeyboardInput(Set(), Set(), Set())
    extendedSettings = extendedSettings.copy(
      windowWidth = surface.w,
      windowHeight = surface.h
    )
    clear(Set(Canvas.Resource.Backbuffer))
  }

  private[this] def putPixelScaled(x: Int, y: Int, c: Color): Unit = {
    extendedSettings.pixelSize.foreach { dy =>
      val lineBase = (y * settings.scale + dy) * extendedSettings.windowWidth
      extendedSettings.pixelSize.foreach { dx =>
        val baseAddr = 4 * (lineBase + (x * settings.scale + dx))
        surface.pixels(baseAddr + 0) = c.b.toByte
        surface.pixels(baseAddr + 1) = c.g.toByte
        surface.pixels(baseAddr + 2) = c.r.toByte
      }
    }
  }

  private[this] def putPixelUnscaled(x: Int, y: Int, c: Color): Unit = {
    // Assuming a BGRA surface
    val lineBase = y * extendedSettings.windowWidth
    val baseAddr = 4 * (lineBase + x)
    surface.pixels(baseAddr + 0) = c.b.toByte
    surface.pixels(baseAddr + 1) = c.g.toByte
    surface.pixels(baseAddr + 2) = c.r.toByte
  }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    if (settings.scale == 1)
      putPixelUnscaled(x + extendedSettings.canvasX, y + extendedSettings.canvasY, color)
    else putPixelScaled(x + extendedSettings.canvasX, y + extendedSettings.canvasY, color)
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    val xx = extendedSettings.canvasX + x
    val yy = extendedSettings.canvasY + y
    // Assuming a BGRA surface
    val baseAddr =
      4 * (yy * settings.scale * extendedSettings.windowWidth + (xx * settings.scale))
    Color(
      (surface.pixels(baseAddr + 2) & 0xff),
      (surface.pixels(baseAddr + 1) & 0xff),
      (surface.pixels(baseAddr + 0) & 0xff)
    )
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    extendedSettings.lines.map { y =>
      val yy       = extendedSettings.canvasY + y
      val lineBase = yy * settings.scale * extendedSettings.windowWidth
      extendedSettings.columns.map { x =>
        val xx       = extendedSettings.canvasX + x
        val baseAddr = 4 * (lineBase + (xx * settings.scale))
        Color(
          (surface.pixels(baseAddr + 2) & 0xff),
          (surface.pixels(baseAddr + 1) & 0xff),
          (surface.pixels(baseAddr + 0) & 0xff)
        )
      }.toVector
    }.toVector
  }

  private[this] def handleEvents(): Boolean = {
    val event              = stackalloc[SDL_Event]
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
          rawMousePos = (event.motion.x, event.motion.y)
          true
        case SDL_MOUSEBUTTONDOWN =>
          mouseInput = mouseInput.move(cleanMousePos).press
          true
        case SDL_MOUSEBUTTONUP =>
          mouseInput = mouseInput.move(cleanMousePos).release
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
      SDL_SetRenderDrawColor(renderer, ubyteClearR, ubyteClearG, ubyteClearB, 0.toUByte)
      SDL_RenderClear(renderer)
    }
  }

  def redraw(): Unit = {
    if (handleEvents()) SDL_UpdateWindowSurface(window)
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = mouseInput.move(cleanMousePos)
}
