package eu.joaocosta.minart.backend

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_BlendMode.*
import sdl2.enumerations.SDL_EventType.*
import sdl2.enumerations.SDL_InitFlag.*
import sdl2.enumerations.SDL_WindowFlags.*

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.input.*

/** A low level Canvas implementation that shows the image in a SDL Window.
  *
  *  @param handleQuit if true, the canvas should handle quit events. Otherwise, it is expected that those are handled in the application code.
  */
final class SdlCanvas(handleQuit: Boolean = false) extends SurfaceBackedCanvas {

  // Rendering resources

  private[this] var window: Ptr[SDL_Window]         = _
  private[this] var windowSurface: Ptr[SDL_Surface] = _
  protected var surface: SdlSurface                 = _

  // Input resources

  private[this] var keyboardInput: KeyboardInput = {
    val localesPtr = SDL_GetPreferredLocales()
    val locale     = ((!localesPtr).language, (!localesPtr).country) match {
      case (null, _)       => None
      case (lang, null)    => Some(fromCString(lang))
      case (lang, country) => Some(fromCString(lang) + "-" + fromCString(country))
    }
    SDL_free(localesPtr.asInstanceOf[Ptr[Byte]])
    KeyboardInput.empty.copy(locale = locale)
  }
  private[this] var pointerInput: PointerInput                     = PointerInput.empty
  private[this] var rawPointerPos: (Int, Int)                      = _
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
    def nextEvent(): Int   = {
      if (isCreated()) {
        if (handleQuit) SDL_PollEvent(event)
        else {
          SDL_PumpEvents()
          SDL_PeepEvents(event, 1, SDL_eventaction.SDL_GETEVENT, SDL_KEYDOWN.uint, SDL_LASTEVENT.uint)
        }
      } else 0
    }
    if (isCreated()) SDL_PumpEvents()
    while (keepGoing && nextEvent() > 0) {
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
    // Update window
    if (window != null) {
      SDL_DestroyWindow(window)
    }
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
    SdlSurface.withRawData(windowSurface)(_.fill(newSettings.clearColor))
    val fullExtendedSettings = extendedSettings.copy(
      windowWidth = (!windowSurface).w,
      windowHeight = (!windowSurface).h
    )

    // Update internal surface
    val newSurface = new SdlSurface(
      SDL_CreateRGBSurfaceWithFormat(
        0.toUInt,
        newSettings.width,
        newSettings.height,
        32,
        SDL_PixelFormatEnum.SDL_PIXELFORMAT_ARGB8888.uint
      )
    )
    SDL_SetSurfaceBlendMode(newSurface.data, SDL_BLENDMODE_NONE)
    newSurface.fill(newSettings.clearColor)
    if (surface != null) {
      val oldSurface = surface
      surface = newSurface
      oldSurface.cleanup()
      SDL_FreeSurface(oldSurface.data)
    } else {
      surface = newSurface
    }

    // Update settings
    fullExtendedSettings
  }

  // Cleanup

  protected def unsafeDestroy() = {
    SDL_DestroyWindow(window)
    surface.cleanup()
    SDL_FreeSurface(surface.data)
    SDL_QuitSubSystem(SDL_INIT_VIDEO)
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
