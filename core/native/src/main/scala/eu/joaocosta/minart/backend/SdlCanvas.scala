package eu.joaocosta.minart.backend

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.core._

/** A low level Canvas implementation that shows the image in a SDL Window.
  */
class SdlCanvas() extends LowLevelCanvas {

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  private[this] var window: Ptr[SDL_Window]         = _
  private[this] var windowSurface: Ptr[SDL_Surface] = _
  private[this] var surface: Ptr[SDL_Surface]       = _
  private[this] var renderer: Ptr[SDL_Renderer]     = _
  private[this] var keyboardInput: KeyboardInput    = KeyboardInput(Set(), Set(), Set())
  private[this] var rawMousePos: (Int, Int)         = _
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
    windowSurface = SDL_GetWindowSurface(window)
    surface =
      SDL_CreateRGBSurface(0.toUInt, newSettings.width, newSettings.height, 32, 0.toUInt, 0.toUInt, 0.toUInt, 0.toUInt)
    renderer = SDL_CreateSoftwareRenderer(surface)
    keyboardInput = KeyboardInput(Set(), Set(), Set())
    extendedSettings = extendedSettings.copy(
      windowWidth = windowSurface.w,
      windowHeight = windowSurface.h
    )
    (0 until extendedSettings.windowHeight * extendedSettings.windowWidth).foreach { i =>
      val baseAddr = i * 4
      windowSurface.pixels(baseAddr + 0) = ubyteClearB.toByte
      windowSurface.pixels(baseAddr + 1) = ubyteClearG.toByte
      windowSurface.pixels(baseAddr + 2) = ubyteClearR.toByte
      windowSurface.pixels(baseAddr + 3) = 255.toByte
    }
    SDL_SetRenderDrawColor(renderer, ubyteClearR, ubyteClearG, ubyteClearB, 0.toUByte)
    clear(Set(Canvas.Resource.Backbuffer))
  }

  def putPixel(x: Int, y: Int, color: Color): Unit = try {
    // Assuming a BGRA surface
    val lineBase = y * extendedSettings.settings.width
    val baseAddr = 4 * (lineBase + x)
    surface.pixels(baseAddr + 0) = color.b.toByte
    surface.pixels(baseAddr + 1) = color.g.toByte
    surface.pixels(baseAddr + 2) = color.r.toByte
  } catch { case _: Throwable => () }

  def getBackbufferPixel(x: Int, y: Int): Color = {
    // Assuming a BGRA surface
    val baseAddr =
      4 * (y * extendedSettings.settings.width + x)
    Color(
      (surface.pixels(baseAddr + 2) & 0xff),
      (surface.pixels(baseAddr + 1) & 0xff),
      (surface.pixels(baseAddr + 0) & 0xff)
    )
  }

  def getBackbuffer(): Vector[Vector[Color]] = {
    extendedSettings.lines.map { y =>
      val lineBase = y * extendedSettings.settings.width
      extendedSettings.columns.map { x =>
        val baseAddr = 4 * (lineBase + x)
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
      SDL_RenderClear(renderer)
    }
  }

  def redraw(): Unit = {
    if (handleEvents()) {
      val windowRect = stackalloc[SDL_Rect].init(
        extendedSettings.canvasX,
        extendedSettings.canvasY,
        extendedSettings.scaledWidth,
        extendedSettings.scaledHeight
      )
      SDL_UpperBlitScaled(surface, null, windowSurface, windowRect)
      SDL_UpdateWindowSurface(window)
    }
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = mouseInput.move(cleanMousePos)
}
