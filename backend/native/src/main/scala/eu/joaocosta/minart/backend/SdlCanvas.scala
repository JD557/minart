package eu.joaocosta.minart.backend

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_BlendMode.*
import sdl2.enumerations.SDL_EventType.*
import sdl2.enumerations.SDL_InitFlag.*
import sdl2.enumerations.SDL_KeyCode.*
import sdl2.enumerations.SDL_WindowFlags.*

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*

/** A low level Canvas implementation that shows the image in a SDL Window.
  */
final class SdlCanvas() extends SurfaceBackedCanvas {

  // Rendering resources

  private[this] var ubyteClearR: UByte              = _
  private[this] var ubyteClearG: UByte              = _
  private[this] var ubyteClearB: UByte              = _
  private[this] var window: Ptr[SDL_Window]         = _
  private[this] var windowSurface: Ptr[SDL_Surface] = _
  protected var surface: SdlSurface                 = _

  // Input resources

  private[this] var keyboardInput: KeyboardInput = KeyboardInput.empty
  private[this] var pointerInput: PointerInput   = PointerInput.empty
  private[this] var rawPointerPos: (Int, Int)    = _
  private[this] def cleanPointerPos: Option[PointerInput.Position] = if (isCreated())
    Option(rawPointerPos).map { case (x, y) =>
      PointerInput.Position(
        (x - extendedSettings.canvasX) / extendedSettings.scale,
        (y - extendedSettings.canvasY) / extendedSettings.scale
      )
    }
  else None

  private[this] def handleEvents(): Boolean = {
    val event              = stackalloc[SDL_Event]()
    var keepGoing: Boolean = isCreated()
    while (keepGoing && SDL_PollEvent(event) != 0) {
      SDL_EventType.define((!event).`type`) match {
        case SDL_KEYDOWN =>
          SdlKeyMapping
            .getKey(SDL_KeyCode.define((!event).key.keysym.sym))
            .foreach(k => keyboardInput = keyboardInput.press(k))
        case SDL_KEYUP =>
          SdlKeyMapping
            .getKey(SDL_KeyCode.define((!event).key.keysym.sym))
            .foreach(k => keyboardInput = keyboardInput.release(k))
        case SDL_MOUSEMOTION =>
          rawPointerPos = ((!event).motion.x, (!event).motion.y)
        case SDL_MOUSEBUTTONDOWN =>
          pointerInput = pointerInput.move(cleanPointerPos).press
        case SDL_MOUSEBUTTONUP =>
          pointerInput = pointerInput.move(cleanPointerPos).release
        case SDL_QUIT =>
          close()
          keepGoing = false
        case _ =>
      }
    }
    keepGoing
  }

  // Initialization

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  protected def unsafeInit(): Unit = {
    SDL_InitSubSystem(SDL_INIT_VIDEO)
  }

  protected def unsafeApplySettings(newSettings: Canvas.Settings): LowLevelCanvas.ExtendedSettings = {
    val extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
    SDL_DestroyWindow(window)
    ubyteClearR = newSettings.clearColor.r.toUByte
    ubyteClearG = newSettings.clearColor.g.toUByte
    ubyteClearB = newSettings.clearColor.b.toUByte
    Zone {
      window = SDL_CreateWindow(
        toCString(newSettings.title),
        0x2fff0000, // SDL_WINDOWPOS_CENTERED
        0x2fff0000, // SDL_WINDOWPOS_CENTERED
        extendedSettings.scaledWidth,
        extendedSettings.scaledHeight,
        (if (extendedSettings.settings.fullScreen) SDL_WINDOW_FULLSCREEN_DESKTOP
         else SDL_WINDOW_SHOWN).value.toUInt
      )
    }
    windowSurface = SDL_GetWindowSurface(window)
    SDL_SetSurfaceBlendMode(windowSurface, SDL_BLENDMODE_NONE)
    surface = new SdlSurface(
      SDL_CreateRGBSurface(
        0.toUInt,
        newSettings.width,
        newSettings.height,
        32,
        0x00ff0000.toUInt,
        0x0000ff00.toUInt,
        0x000000ff.toUInt,
        0xff000000.toUInt
      )
    )
    SDL_SetSurfaceBlendMode(surface.data, SDL_BLENDMODE_NONE)
    val fullExtendedSettings = extendedSettings.copy(
      windowWidth = (!windowSurface).w,
      windowHeight = (!windowSurface).h
    )
    (0 until fullExtendedSettings.windowHeight * fullExtendedSettings.windowWidth).foreach { i =>
      val baseAddr = i * 4
      (!windowSurface).pixels(baseAddr + 0) = ubyteClearB.toByte
      (!windowSurface).pixels(baseAddr + 1) = ubyteClearG.toByte
      (!windowSurface).pixels(baseAddr + 2) = ubyteClearR.toByte
      (!windowSurface).pixels(baseAddr + 3) = 255.toByte
    }
    clear(Set(Canvas.Buffer.Backbuffer))
    fullExtendedSettings
  }

  // Cleanup

  protected def unsafeDestroy() = {
    SDL_Quit()
  }

  // Canvas operations

  def clear(buffers: Set[Canvas.Buffer]): Unit = {
    if (buffers.contains(Canvas.Buffer.KeyboardBuffer)) {
      keyboardInput = keyboardInput.clearPressRelease()
    }
    if (buffers.contains(Canvas.Buffer.PointerBuffer)) {
      pointerInput = pointerInput.clearEvents()
    }
    if (handleEvents() && buffers.contains(Canvas.Buffer.Backbuffer)) {
      surface.fill(settings.clearColor)
    }
  }

  def redraw(): Unit = {
    if (handleEvents()) {
      val windowRect = stackalloc[SDL_Rect]()
      (!windowRect).x = extendedSettings.canvasX
      (!windowRect).y = extendedSettings.canvasY
      (!windowRect).w = extendedSettings.scaledWidth
      (!windowRect).h = extendedSettings.scaledHeight
      SDL_UpperBlitScaled(surface.data, null, windowSurface, windowRect)
      SDL_UpdateWindowSurface(window)
    }
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput.move(cleanPointerPos)
}
