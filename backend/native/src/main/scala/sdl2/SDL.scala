// Copied from
// https://github.com/regb/scalanative-graphics-bindings/blob/d8b3e8ee13e10fe0aaf972fdac3f7d9f5a42ee07/sdl2/src/main/scala/sdl2/SDL.scala

package sdl2

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

/*
 * Provide @extern definitions for the entire SDL.h
 * header file, including all sub-headers included by
 * SDL.h (SDL_video.h, SDL_render.h, SDL_events.h, ...)
 * It would be nicer to decompose these extern functions
 * into one object per component, but it would then become
 * annoying to import. With this design, clients can simply
 * import sdl2.SDL._, which is pretty much equivalent to #include <SDL.h>
 * for the C API. The only additional import
 * is sdl2.Extras._, needed to provide all macros and proper methods/fields
 * to structures.
 */
@extern
@link("SDL2")
object SDL {

  def SDL_GetTicks(): UInt      = extern
  def SDL_Delay(ms: UInt): Unit = extern

  type _2  = Nat._2
  type _16 = Nat.Digit2[Nat._1, Nat._6]
  type _32 = Nat.Digit2[Nat._3, Nat._2]
  type _52 = Nat.Digit2[Nat._5, Nat._2]
  type _56 = Nat.Digit2[Nat._5, Nat._6]
  type _64 = Nat.Digit2[Nat._6, Nat._4]

  /** ************************************
    * *********** SDL_audio.h ************
    * ************************************
    */

  type SDL_AudioDeviceID = UInt
  type SDL_AudioFormat   = UShort
  type SDL_AudioCallback = CFuncPtr3[Ptr[Byte], Ptr[UByte], CInt, Unit]
  type SDL_AudioSpec = CStruct9[CInt, SDL_AudioFormat, UByte, UByte, UShort, UShort, UInt, SDL_AudioCallback, Ptr[Byte]]

  def SDL_QueueAudio(dev: SDL_AudioDeviceID, data: Ptr[Byte], len: UInt): CInt = extern
  def SDL_OpenAudioDevice(
      device: CString,
      iscapture: CInt,
      desired: Ptr[SDL_AudioSpec],
      obtained: Ptr[SDL_AudioSpec],
      allowedChanges: CInt
  ): SDL_AudioDeviceID = extern
  def SDL_PauseAudioDevice(dev: SDL_AudioDeviceID, pause_on: CInt): Unit = extern;
  def SDL_LockAudioDevice(dev: SDL_AudioDeviceID): Unit                  = extern;
  def SDL_UnlockAudioDevice(dev: SDL_AudioDeviceID): Unit                = extern;

  /** ************************************
    * *********** SDL_error.h ************
    * ************************************
    */

  //   def SDL_SetError(fmt: CString, args: CVararg*): CInt = extern
  def SDL_GetError(): CString = extern
  def SDL_ClearError(): Unit  = extern

  type SDL_errorcode = UInt
  def SDL_Error(code: SDL_errorcode): CInt = extern

  /** ************************************
    * ********** SDL_atomic.h *************
    * ************************************
    */

  type SDL_SpinLock = CInt

  def SDL_AtomicTryLock(lock: Ptr[SDL_SpinLock]): SDL_bool = extern
  def SDL_AtomicLock(lock: Ptr[SDL_SpinLock]): Unit        = extern
  def SDL_AtomicUnlock(lock: Ptr[SDL_SpinLock]): Unit      = extern

  def SDL_MemoryBarrierRelease(): Unit = extern
  def SDL_MemoryBarrierAcquire(): Unit = extern

  type SDL_atomic_t = CStruct1[CInt]

  def SDL_AtomicCAS(a: Ptr[SDL_atomic_t], oldval: CInt, newval: CInt): SDL_bool = extern
  def SDL_AtomicSet(a: Ptr[SDL_atomic_t], v: CInt): CInt                        = extern
  def SDL_AtomicGet(a: Ptr[SDL_atomic_t]): CInt                                 = extern
  def SDL_AtomicAdd(a: Ptr[SDL_atomic_t], v: CInt): CInt                        = extern

  def SDL_AtomicCASPtr(a: Ptr[Ptr[Byte]], oldval: Ptr[Byte], newval: Ptr[Byte]): SDL_bool = extern
  def SDL_AtomicSetPtr(a: Ptr[Ptr[Byte]], v: Ptr[Byte]): Ptr[Byte]                        = extern
  def SDL_AtomicGetPtr(a: Ptr[Ptr[Byte]]): Ptr[Byte]                                      = extern

  /** ************************************
    * *********** SDL_mutex.h *************
    * ************************************
    */

  type SDL_mutex = CStruct0
  def SDL_CreateMutex(): Ptr[SDL_mutex]             = extern
  def SDL_LockMutex(mutex: Ptr[SDL_mutex]): CInt    = extern
  def SDL_TryLockMutex(mutex: Ptr[SDL_mutex]): CInt = extern
  def SDL_UnlockMutex(mutex: Ptr[SDL_mutex]): CInt  = extern
  def SDL_DestroyMutex(mutex: Ptr[SDL_mutex]): Unit = extern

  type SDL_semaphore = CStruct0
  type SDL_sem       = SDL_semaphore
  def SDL_CreateSemaphore(initial_value: UInt): Ptr[SDL_sem] = extern
  def SDL_DestroySemaphore(sem: Ptr[SDL_sem]): Unit          = extern
  def SDL_SemWait(sem: Ptr[SDL_sem]): CInt                   = extern
  def SDL_SemTryWait(sem: Ptr[SDL_sem]): CInt                = extern
  def SDL_SemWaitTimeout(sem: Ptr[SDL_sem], ms: UInt): CInt  = extern
  def SDL_SemPost(sem: Ptr[SDL_sem]): CInt                   = extern
  def SDL_SemValue(sem: Ptr[SDL_sem]): UInt                  = extern

  type SDL_cond = CStruct0
  def SDL_CreateCond(): Ptr[SDL_cond]                                                 = extern
  def SDL_DestroyCond(cond: Ptr[SDL_cond]): Unit                                      = extern
  def SDL_CondSignal(cond: Ptr[SDL_cond]): CInt                                       = extern
  def SDL_CondBroadcast(cond: Ptr[SDL_cond]): CInt                                    = extern
  def SDL_CondWait(cond: Ptr[SDL_cond], mutex: Ptr[SDL_mutex]): CInt                  = extern
  def SDL_CondWaitTimeout(cond: Ptr[SDL_cond], mutex: Ptr[SDL_mutex], ms: UInt): CInt = extern

  /** ************************************
    * ********** SDL_thread.h *************
    * ************************************
    */

  type SDL_Thread         = CStruct0
  type SDL_threadID       = CUnsignedLong
  type SDL_TLSID          = CUnsignedInt
  type SDL_ThreadPriority = CInt
  type SDL_ThreadFunction = CFuncPtr1[Ptr[Byte], CInt]

  def SDL_CreateThread(fn: SDL_ThreadFunction, name: CString, data: Ptr[Byte]): Ptr[SDL_Thread] = extern
  def SDL_GetThreadName(thread: Ptr[SDL_Thread]): CString                                       = extern
  def SDL_ThreadID(): SDL_threadID                                                              = extern
  def SDL_GetThreadID(thread: Ptr[SDL_Thread]): SDL_threadID                                    = extern
  def SDL_SetThreadPriority(priority: SDL_ThreadPriority): CInt                                 = extern
  def SDL_WaitThread(thread: Ptr[SDL_Thread], status: Ptr[CInt]): Unit                          = extern
  def SDL_DetachThread(thread: Ptr[SDL_Thread]): Unit                                           = extern
  def SDL_TLSCreate(): SDL_TLSID                                                                = extern
  def SDL_TLSGet(id: SDL_TLSID): Ptr[Byte]                                                      = extern
  def SDL_TLSSet(id: SDL_TLSID, value: Ptr[Byte], destructor: CFuncPtr0[Unit]): CInt            = extern

  /** ************************************
    * *********** SDL_rwops.h *************
    * ************************************
    */

  // TODO: exact structure (with a lot of function pointers)
  type SDL_RWops = CStruct0

  def SDL_RWFromFile(file: CString, mode: CString): Ptr[SDL_RWops] = extern
  // TODO: fp should be Ptr[FILE], from stdio
  def SDL_RWFromFP(fp: Ptr[Byte], autoclose: SDL_bool): Ptr[SDL_RWops] = extern
  def SDL_RWFromMem(mem: Ptr[Byte], size: CInt): Ptr[SDL_RWops]        = extern
  def SDL_RWFromConstMem(mem: Ptr[Byte], size: CInt): Ptr[SDL_RWops]   = extern

  def SDL_AllocRW(): Ptr[SDL_RWops]          = extern
  def SDL_FreeRW(area: Ptr[SDL_RWops]): Unit = extern

  def SDL_ReadU8(src: Ptr[SDL_RWops]): UByte    = extern
  def SDL_ReadLE16(src: Ptr[SDL_RWops]): UShort = extern
  def SDL_ReadBE16(src: Ptr[SDL_RWops]): UShort = extern
  def SDL_ReadLE32(src: Ptr[SDL_RWops]): UInt   = extern
  def SDL_ReadBE32(src: Ptr[SDL_RWops]): UInt   = extern
  def SDL_ReadLE64(src: Ptr[SDL_RWops]): ULong  = extern
  def SDL_ReadBE64(src: Ptr[SDL_RWops]): ULong  = extern

  def SDL_WriteU8(dst: Ptr[SDL_RWops], value: UByte): CSize    = extern
  def SDL_WriteLE16(dst: Ptr[SDL_RWops], value: UShort): CSize = extern
  def SDL_WriteBE16(dst: Ptr[SDL_RWops], value: UShort): CSize = extern
  def SDL_WriteLE32(dst: Ptr[SDL_RWops], value: UInt): CSize   = extern
  def SDL_WriteBE32(dst: Ptr[SDL_RWops], value: UInt): CSize   = extern
  def SDL_WriteLE64(dst: Ptr[SDL_RWops], value: ULong): CSize  = extern
  def SDL_WriteBE64(dst: Ptr[SDL_RWops], value: ULong): CSize  = extern

  /** ************************************
    * ********* SDL_blendmode.h ***********
    * ************************************
    */

  type SDL_BlendMode = UInt

  /** ************************************
    * *********** SDL_events.h ************
    * ************************************
    */

  type SDL_CommonEvent = CStruct2[UInt, UInt]

  // TODO: confirm that Sint32 is Int in scala-native
  type SDL_WindowEvent = CStruct9[UInt, UInt, UInt, UByte, UByte, UByte, UByte, Int, Int]

  type SDL_KeyboardEvent = CStruct8[UInt, UInt, UInt, UByte, UByte, UByte, UByte, SDL_Keysym]

  type SDL_TextEditingEvent = CStruct6[UInt, UInt, UInt, CArray[CChar, _32], Int, Int]

  type SDL_TextInputEvent = CStruct4[UInt, UInt, UInt, CArray[CChar, _32]]

  type SDL_MouseMotionEvent = CStruct9[UInt, UInt, UInt, UInt, UInt, Int, Int, Int, Int]

  type SDL_MouseButtonEvent = CStruct10[UInt, UInt, UInt, UInt, UByte, UByte, UByte, UByte, Int, Int]

  // TODO:
  // type SDL_MouseWheelEvent
  // type SDL_JoyAxisEvent
  // type SDL_JoyBallEvent
  // type SDL_JoyHatEvent
  // type SDL_JoyButtonEvent
  // type SDL_JoyDeviceEvent
  // type SDL_ControllerAxisEvent
  // type SDL_ControllerButtonEvent
  // type SDL_ControllerDeviceEvent
  // type SDL_AudioDeviceEvent

  // TODO: for iOS
  // type SDL_TouchFingerEvent

  // type SDL_MultiGestureEvent
  // type SDL_DollarGestureEvent
  // type SDL_DropEvent

  type SDL_QuitEvent = CStruct2[UInt, UInt]
  type SDL_OSEvent   = CStruct2[UInt, UInt]

  type SDL_UserEvent = CStruct6[UInt, UInt, UInt, Int, Ptr[Byte], Ptr[Byte]]

  type SDL_SysWMmsg = CStruct0

  type SDL_SysWMEvent = CStruct3[UInt, UInt, Ptr[SDL_SysWMmsg]]

  // SDL_Event is a union of all events defined above
  // SDL defines the padding to be an array of size 56 bytes, we describe
  // a two element struct, with the first element being the type UInt shared
  // by all members of the union, and the second element completes the padding
  // with 52 bytes (to reach 56).
  type SDL_Event = CStruct2[UInt, CArray[Byte, _52]]

  def SDL_PumpEvents(): Unit = extern

  def SDL_PeepEvents(events: Ptr[SDL_Event], numevents: CInt, action: UInt, minType: UInt, maxType: UInt): Unit = extern

  // TODO: is it fine to have param name differ? should be "type"
  def SDL_HasEvent(type_ : UInt): SDL_bool                  = extern
  def SDL_HasEvents(minType: UInt, maxType: UInt): SDL_bool = extern

  def SDL_FlushEvent(type_ : UInt): Unit                  = extern
  def SDL_FlushEvents(minType: UInt, maxType: UInt): Unit = extern

  def SDL_PollEvent(event: Ptr[SDL_Event]): CInt = extern

  def SDL_WaitEvent(event: Ptr[SDL_Event]): CInt                       = extern
  def SDL_WaitEventTimeout(event: Ptr[SDL_Event], timeout: CInt): CInt = extern

  def SDL_PushEvent(event: Ptr[SDL_Event]): CInt = extern

  // TODO:
  // typedef int (SDLCALL * SDL_EventFilter) (void *userdata, SDL_Event * event);
  // def SDL_SetEventFilter(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern
  // def SDL_GetEventFilter(filter: Ptr[SDL_EventFilter], userdata: Ptr[Ptr[Byte]]): Unit = extern
  // def SDL_AddEventWatch(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern
  // def SDL_DelEventWatch(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern
  // def SDL_FilterEvents(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern

  def SDL_EventState(type_ : UInt, state: CInt): UByte = extern

  def SDL_RegisterEvents(numevents: CInt): UInt = extern

  /** *************************************
    * ********** SDL_keycode.h *************
    * *************************************
    */

  type SDL_Keycode = Int
  type SDL_Keymod  = UInt

  /** *************************************
    * ********** SDL_keyboard.h ************
    * *************************************
    */

  type SDL_Keysym = CStruct4[SDL_Scancode, SDL_Keycode, UShort, UInt]

  def SDL_GetKeyboardFocus(): Ptr[SDL_Window] = extern

  def SDL_GetKeyboardState(numkeys: Ptr[CInt]): UByte = extern

  def SDL_GetModState(): SDL_Keymod               = extern
  def SDL_SetModState(modstate: SDL_Keymod): Unit = extern

  def SDL_GetKeyFromScancode(scancode: SDL_Scancode): SDL_Keycode = extern
  def SDL_GetScancodeFromKey(key: SDL_Keycode): SDL_Scancode      = extern
  def SDL_GetScancodeName(scancode: SDL_Scancode): CString        = extern
  def SDL_GetScancodeFromName(name: CString): SDL_Scancode        = extern
  def SDL_GetKeyName(key: SDL_Keycode): CString                   = extern
  def SDL_GetKeyFromName(name: CString): SDL_Keycode              = extern

  def SDL_StartTextInput(): Unit                      = extern
  def SDL_IsTextInputActive(): SDL_bool               = extern
  def SDL_StopTextInput(): Unit                       = extern
  def SDL_SetTextInputRect(rect: Ptr[SDL_Rect]): Unit = extern

  def SDL_HasScreenKeyboardSupport(): SDL_bool                     = extern
  def SDL_IsScreenKeyboardShown(window: Ptr[SDL_Window]): SDL_bool = extern

  /** ************************************
    * *********** SDL_mouse.h *************
    * ************************************
    */

  type SDL_Cursor              = CStruct0
  type SDL_SystemCursor        = CInt
  type SDL_MouseWheelDirection = CInt

  def SDL_GetMouseFocus(): Ptr[SDL_Window]                        = extern
  def SDL_GetMouseState(x: Ptr[CInt], y: Ptr[CInt]): UInt         = extern
  def SDL_GetGlobalMouseState(x: Ptr[CInt], y: Ptr[CInt]): UInt   = extern
  def SDL_GetRelativeMouseState(x: Ptr[CInt], y: Ptr[CInt]): UInt = extern

  def SDL_WarpMouseInWindow(window: Ptr[SDL_Window], x: CInt, y: CInt): Unit = extern
  def SDL_WarpMouseGlobal(x: CInt, y: CInt): Unit                            = extern

  def SDL_SetRelativeMouseMode(enabled: SDL_bool): CInt = extern
  def SDL_CaptureMouse(enabled: SDL_bool): CInt         = extern
  def SDL_GetRelativeMouseMode(): SDL_bool              = extern

  def SDL_CreateCursor(
      data: Ptr[UByte],
      mask: Ptr[UByte],
      w: CInt,
      h: CInt,
      hot_x: CInt,
      hot_y: CInt
  ): Ptr[SDL_Cursor] = extern
  def SDL_CreateColorCursor(surface: Ptr[SDL_Surface], hot_x: CInt, hot_y: CInt): Ptr[SDL_Cursor] = extern
  def SDL_CreateSystemCursor(id: SDL_SystemCursor): Ptr[SDL_Cursor]                               = extern
  def SDL_SetCursor(cursor: Ptr[SDL_Cursor]): Unit                                                = extern
  def SDL_GetCursor(): Ptr[SDL_Cursor]                                                            = extern
  def SDL_GetDefaultCursor(): Ptr[SDL_Cursor]                                                     = extern
  def SDL_FreeCursor(cursor: Ptr[SDL_Cursor]): Unit                                               = extern
  def SDL_ShowCursor(toggle: CInt): CInt                                                          = extern

  /** *************************************
    * *********** SDL_pixels.h *************
    * *************************************
    */

  /*
   * SDL_Color is defined as CStruct4[UByte, UByte, UByte, UByte],
   * however it seems that after being compiled the underlying value
   * will be equivalent to a UInt (32 bits). We use the UInt
   * instead of the CStruct4 because scala-native does not support passing
   * struct by value. The trick however is that, probably due to endianness
   * on my computer, the rgba values were reveresed.
   *
   * TODO: One technique used by scala native implementation is to write
   *       helper/wrapper functions in C (with supported signature, that is
   *       with pointers) and bind these instead. See Complex implementation in
   *       Scala native.
   */
  type SDL_Color   = UInt
  type SDL_Palette = CStruct4[CInt, Ptr[SDL_Color], UInt, CInt]

  type SDL_PixelFormat = CStruct19[
    UInt,
    Ptr[SDL_Palette],
    UByte,
    UByte,
    CArray[UByte, _2],
    UInt,
    UInt,
    UInt,
    UInt,
    UByte,
    UByte,
    UByte,
    UByte,
    UByte,
    UByte,
    UByte,
    UByte,
    CInt,
    Ptr[Byte]
  ]

  def SDL_GetPixelFormatName(format: UInt): CString = extern
  def SDL_PixelFormatEnumToMasks(
      format: UInt,
      bpp: Ptr[CInt],
      Rmask: Ptr[UInt],
      Gmask: Ptr[UInt],
      Bmask: Ptr[UInt],
      Amask: Ptr[UInt]
  ): SDL_bool = extern
  def SDL_MasksToPixelFormatEnum(bpp: CInt, Rmask: UInt, Gmask: UInt, Bmask: UInt, Amask: UInt): UInt = extern

  def SDL_AllocFormat(pixel_format: UInt): Ptr[SDL_PixelFormat] = extern
  def SDL_FreeFormat(format: Ptr[SDL_PixelFormat]): Unit        = extern

  def SDL_AllocPalette(ncolors: CInt): Ptr[SDL_Palette]                                        = extern
  def SDL_SetPixelFormatPalette(format: Ptr[SDL_PixelFormat], palette: Ptr[SDL_Palette]): CInt = extern
  def SDL_SetPaletteColors(palette: Ptr[SDL_Palette], colors: Ptr[SDL_Color], firstcolor: CInt, ncolors: CInt): CInt =
    extern
  def SDL_FreePalette(palette: Ptr[SDL_Palette]): Unit = extern

  def SDL_MapRGB(format: Ptr[SDL_PixelFormat], r: UByte, g: UByte, b: UByte): UInt                             = extern
  def SDL_MapRGBA(format: Ptr[SDL_PixelFormat], r: UByte, g: UByte, b: UByte, a: UByte): UInt                  = extern
  def SDL_GetRGB(pixel: UInt, format: Ptr[SDL_PixelFormat], r: Ptr[UByte], g: Ptr[UByte], b: Ptr[UByte]): Unit = extern
  def SDL_GetRGBA(
      pixel: UInt,
      format: Ptr[SDL_PixelFormat],
      r: Ptr[UByte],
      g: Ptr[UByte],
      b: Ptr[UByte],
      a: Ptr[UByte]
  ): Unit = extern

  def SDL_CalculateGammaRamp(gamma: CFloat, ramp: Ptr[UShort]): Unit = extern

  /** ************************************
    * ************ SDL_rect.h *************
    * ************************************
    */

  type SDL_Point = CStruct2[CInt, CInt]
  type SDL_Rect  = CStruct4[CInt, CInt, CInt, CInt]

  def SDL_HasIntersection(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect]): SDL_bool                      = extern
  def SDL_IntersectRect(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): SDL_bool = extern
  def SDL_UnionRect(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): SDL_bool     = extern
  def SDL_EnclosePoints(points: Ptr[SDL_Point], count: CInt, clip: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): SDL_bool =
    extern
  def SDL_IntersectRectAndLine(
      rect: Ptr[SDL_Point],
      x1: Ptr[CInt],
      y1: Ptr[CInt],
      x2: Ptr[CInt],
      y2: Ptr[CInt]
  ): SDL_bool = extern

  /** ************************************
    * ********** SDL_surface.h ************
    * ************************************
    */

  type SDL_BlitMap = CStruct0
  type SDL_Surface = CStruct12[UInt, Ptr[SDL_PixelFormat], CInt, CInt, CInt, Ptr[Byte], Ptr[Byte], CInt, Ptr[
    Byte
  ], SDL_Rect, Ptr[SDL_BlitMap], CInt]

  def SDL_CreateRGBSurface(
      flags: UInt,
      width: CInt,
      height: CInt,
      depth: CInt,
      Rmask: UInt,
      Gmask: UInt,
      Bmask: UInt,
      Amask: UInt
  ): Ptr[SDL_Surface] = extern
  def SDL_LoadBMP_RW(src: Ptr[SDL_RWops], freesrc: CInt): Ptr[SDL_Surface] = extern
  def SDL_FreeSurface(surface: Ptr[SDL_Surface]): Unit                     = extern
  def SDL_SetClipRect(
      surface: Ptr[SDL_Surface],
      rect: Ptr[SDL_Rect]
  ): Int = extern
  def SDL_UpperBlit(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): Int = extern
  def SDL_UpperBlitScaled(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): Int = extern

  /** ************************************
    * *********** SDL_render.h *************
    * ************************************
    */

  type SDL_RendererFlags = UInt

  type SDL_RendererInfo = CStruct6[CString, UInt, UInt, CArray[UInt, _16], CInt, CInt]

  type SDL_TextureAccess   = UInt
  type SDL_TextureModulate = UInt
  type SDL_RendererFlip    = UInt

  type SDL_Renderer = CStruct0
  type SDL_Texture  = CStruct0

  def SDL_GetNumRenderDriver(): CInt                                          = extern
  def SDL_GetRenderDriverInfo(index: CInt, info: Ptr[SDL_RendererInfo]): CInt = extern

  def SDL_CreateWindowAndRenderer(
      width: CInt,
      height: CInt,
      flags: UInt,
      window: Ptr[Ptr[SDL_Window]],
      renderer: Ptr[Ptr[SDL_Renderer]]
  ): CInt = extern
  def SDL_CreateRenderer(window: Ptr[SDL_Window], index: CInt, flags: UInt): Ptr[SDL_Renderer] = extern
  def SDL_CreateSoftwareRenderer(surface: Ptr[SDL_Surface]): Ptr[SDL_Renderer]                 = extern
  def SDL_GetRenderer(window: Ptr[SDL_Window]): Ptr[SDL_Renderer]                              = extern
  def SDL_GetRendererInfo(renderer: Ptr[SDL_Renderer], info: Ptr[SDL_RendererInfo]): CInt      = extern
  def SDL_GetRendererOutputSize(renderer: Ptr[SDL_Renderer], w: Ptr[CInt], h: Ptr[CInt]): CInt = extern

  def SDL_CreateTexture(renderer: Ptr[SDL_Renderer], format: UInt, access: CInt, w: Int, h: Int): Ptr[SDL_Texture] =
    extern
  def SDL_CreateTextureFromSurface(renderer: Ptr[SDL_Renderer], surface: Ptr[SDL_Surface]): Ptr[SDL_Texture] = extern
  def SDL_QueryTexture(
      texture: Ptr[SDL_Texture],
      format: Ptr[UInt],
      access: Ptr[CInt],
      w: Ptr[CInt],
      h: Ptr[CInt]
  ): CInt = extern

  def SDL_SetTextureColorMod(texture: Ptr[SDL_Texture], r: UByte, g: UByte, b: UByte): CInt                = extern
  def SDL_GetTextureColorMod(texture: Ptr[SDL_Texture], r: Ptr[UByte], g: Ptr[UByte], b: Ptr[UByte]): CInt = extern
  def SDL_SetTextureAlphaMod(texture: Ptr[SDL_Texture], alpha: UByte): CInt                                = extern
  def SDL_GetTextureAlphaMod(texture: Ptr[SDL_Texture], alpha: Ptr[UByte]): CInt                           = extern
  def SDL_SetTextureBlendMod(texture: Ptr[SDL_Texture], blendMode: SDL_BlendMode): CInt                    = extern
  def SDL_GetTextureBlendMod(texture: Ptr[SDL_Texture], blendMode: Ptr[SDL_BlendMode]): CInt               = extern

  def SDL_UpdateTexture(texture: Ptr[SDL_Texture], rect: Ptr[SDL_Rect], pixels: Ptr[Byte], pictch: CInt): CInt = extern
  def SDL_UpdateYUVTexture(
      texture: Ptr[SDL_Texture],
      rect: Ptr[SDL_Rect],
      Yplane: Ptr[UByte],
      Ypitch: Ptr[UByte],
      Uplane: Ptr[UByte],
      Upitch: Ptr[UByte],
      Vplane: Ptr[UByte],
      Vpitch: Ptr[UByte]
  ): CInt = extern

  def SDL_LockTexture(texture: Ptr[SDL_Texture], rect: Ptr[SDL_Rect], pixels: Ptr[Ptr[Byte]], pitch: Ptr[CInt]): CInt =
    extern
  def SDL_UnlockTexture(texture: Ptr[SDL_Texture]): Unit = extern

  def SDL_RenderTargetSupported(renderer: Ptr[SDL_Renderer]): SDL_bool                  = extern
  def SDL_SetRenderTarget(renderer: Ptr[SDL_Renderer], texture: Ptr[SDL_Texture]): CInt = extern
  def SDL_GetRenderTarget(renderer: Ptr[SDL_Renderer]): Ptr[SDL_Texture]                = extern

  def SDL_RenderSetLogicalSize(renderer: Ptr[SDL_Renderer], w: CInt, h: CInt): CInt           = extern
  def SDL_RenderGetLogicalSize(renderer: Ptr[SDL_Renderer], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern
  def SDL_RenderSetViewport(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt           = extern
  def SDL_RenderGetViewport(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): Unit           = extern

  def SDL_RenderSetClipRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt                   = extern
  def SDL_RenderGetClipRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): Unit                   = extern
  def SDL_RenderSetScale(renderer: Ptr[SDL_Renderer], scaleX: CFloat, scaleY: CFloat): CInt           = extern
  def SDL_RenderGetScale(renderer: Ptr[SDL_Renderer], scaleX: Ptr[CFloat], scaleY: Ptr[CFloat]): CInt = extern

  def SDL_SetRenderDrawColor(renderer: Ptr[SDL_Renderer], r: UByte, g: UByte, b: UByte, a: UByte): CInt = extern
  def SDL_GetRenderDrawColor(
      renderer: Ptr[SDL_Renderer],
      r: Ptr[UByte],
      g: Ptr[UByte],
      b: Ptr[UByte],
      a: Ptr[UByte]
  ): CInt = extern

  def SDL_SetRenderDrawBlendMode(renderer: Ptr[SDL_Renderer], blendMode: SDL_BlendMode): CInt      = extern
  def SDL_GetRenderDrawBlendMode(renderer: Ptr[SDL_Renderer], blendMode: Ptr[SDL_BlendMode]): CInt = extern

  def SDL_RenderClear(renderer: Ptr[SDL_Renderer]): CInt = extern

  def SDL_RenderDrawPoint(renderer: Ptr[SDL_Renderer], x: CInt, y: CInt): CInt                      = extern
  def SDL_RenderDrawPoints(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_Point], count: CInt): CInt  = extern
  def SDL_RenderDrawLine(renderer: Ptr[SDL_Renderer], x1: CInt, y1: CInt, x2: CInt, y2: CInt): CInt = extern
  def SDL_RenderDrawLines(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_Point], count: CInt): CInt   = extern

  def SDL_RenderDrawRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt                = extern
  def SDL_RenderDrawRects(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_Rect], count: CInt): CInt = extern
  def SDL_RenderFillRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt                = extern
  def SDL_RenderFillRects(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_Rect], count: CInt): CInt = extern

  def SDL_RenderCopy(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      destrect: Ptr[SDL_Rect]
  ): CInt = extern
  def SDL_RenderCopyEx(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      destrect: Ptr[SDL_Rect],
      angle: CDouble,
      center: Ptr[SDL_Point],
      flip: SDL_RendererFlip
  ): CInt = extern

  def SDL_RenderReadPixels(
      renderer: Ptr[SDL_Renderer],
      rect: Ptr[SDL_Rect],
      format: UInt,
      pixels: Ptr[Byte],
      pitch: CInt
  ): CInt = extern

  def SDL_RenderPresent(renderer: Ptr[SDL_Renderer]): Unit = extern

  def SDL_DestroyTexture(texture: Ptr[SDL_Texture]): Unit    = extern
  def SDL_DestroyRenderer(renderer: Ptr[SDL_Renderer]): Unit = extern

  def SDL_GL_BindTexture(texture: Ptr[SDL_Texture], texw: Ptr[CFloat], texh: Ptr[CFloat]): CInt = extern
  def SDL_GL_UnbindTexture(texture: Ptr[SDL_Texture]): CInt                                     = extern

  /** ************************************
    * ********** SDL_scancode.h ***********
    * ************************************
    */

  type SDL_Scancode = Int

  /** *************************************
    * *********** SDL_stdinc.h *************
    * *************************************
    */

  type SDL_bool = UInt

  /** ************************************
    * ********** SDL_version.h ************
    * ************************************
    */

  type SDL_version = CStruct3[UByte, UByte, UByte]

  def SDL_GetVersion(ver: Ptr[SDL_version]): Unit = extern
  def SDL_GetRevision(): CString                  = extern
  def SDL_GetRevisionNumber(): CInt               = extern

  /** ************************************
    * *********** SDL_video.h *************
    * ************************************
    */

  type SDL_DisplayMode = CStruct5[UInt, CInt, CInt, CInt, Ptr[Byte]]
  type SDL_Window      = CStruct0

  type SDL_GLContext            = Ptr[Byte]
  type SDL_GLattr               = UInt
  type SDL_GLprofile            = UShort
  type SDL_GLcontextFlag        = UShort
  type SDL_GLcontextReleaseFlag = UShort

  def SDL_GetNumVideoDrivers(): CInt           = extern
  def SDL_GetVideoDriver(index: CInt): CString = extern

  def SDL_VideoInit(driver_name: CString): CInt = extern
  def SDL_VideoQuit(): Unit                     = extern

  def SDL_GetCurrentVideoDriver(): CString                                                                 = extern
  def SDL_GetNumVideoDisplays(): CInt                                                                      = extern
  def SDL_GetDisplayName(displayIndex: CInt): CString                                                      = extern
  def SDL_GetDisplayBounds(displayIndex: CInt, rect: Ptr[SDL_Rect]): CInt                                  = extern
  def SDL_GetDisplayDPI(displayIndex: CInt, ddpi: Ptr[CFloat], hdpi: Ptr[CFloat], vdpi: Ptr[CFloat]): CInt = extern

  def SDL_GetNumDisplayModes(displayIndex: CInt): CInt                                          = extern
  def SDL_GetDisplayMode(displayIndex: CInt, modeIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt = extern
  def SDL_GetDesktopDisplayMode(displayIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt           = extern
  def SDL_GetCurrentDisplayMode(displayIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt           = extern
  def SDL_GetClosestDisplayMode(displayIndex: CInt, mode: Ptr[SDL_DisplayMode], closest: Ptr[SDL_DisplayMode]): CInt =
    extern

  def SDL_GetWindowDisplayIndex(window: Ptr[SDL_Window]): CInt                            = extern
  def SDL_SetWindowDisplayMode(window: Ptr[SDL_Window], mode: Ptr[SDL_DisplayMode]): CInt = extern
  def SDL_GetWindowDisplayMode(window: Ptr[SDL_Window], mode: Ptr[SDL_DisplayMode]): CInt = extern
  def SDL_GetWindowPixelFormat(window: Ptr[SDL_Window]): UInt                             = extern

  def SDL_CreateWindow(title: CString, x: CInt, y: CInt, w: CInt, h: CInt, flags: UInt): Ptr[SDL_Window] = extern
  def SDL_CreateWindowFrom(data: Ptr[Byte]): Ptr[SDL_Window]                                             = extern

  def SDL_GetWindowID(window: Ptr[SDL_Window]): UInt                                            = extern
  def SDL_GetWindowFromID(id: UInt): Ptr[SDL_Window]                                            = extern
  def SDL_GetWindowFlags(window: Ptr[SDL_Window]): UInt                                         = extern
  def SDL_SetWindowTitle(window: Ptr[SDL_Window], title: CString): Unit                         = extern
  def SDL_GetWindowTitle(window: Ptr[SDL_Window]): CString                                      = extern
  def SDL_SetWindowIcon(window: Ptr[SDL_Window], icon: Ptr[SDL_Surface]): Unit                  = extern
  def SDL_SetWindowData(window: Ptr[SDL_Window], name: CString, userdata: Ptr[Byte]): Ptr[Byte] = extern
  def SDL_GetWindowData(window: Ptr[SDL_Window], name: CString): Ptr[Byte]                      = extern
  def SDL_SetWindowPosition(window: Ptr[SDL_Window], x: CInt, y: CInt): Unit                    = extern
  def SDL_GetWindowPosition(window: Ptr[SDL_Window], x: Ptr[CInt], y: Ptr[CInt]): Unit          = extern
  def SDL_SetWindowSize(window: Ptr[SDL_Window], w: CInt, h: CInt): Unit                        = extern
  def SDL_GetWindowSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit              = extern
  def SDL_SetWindowMinimumSize(window: Ptr[SDL_Window], min_w: CInt, min_h: CInt): Unit         = extern
  def SDL_GetWindowMinimumSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit       = extern
  def SDL_SetWindowMaximumSize(window: Ptr[SDL_Window], max_w: CInt, max_h: CInt): Unit         = extern
  def SDL_GetWindowMaximumSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit       = extern
  def SDL_SetWindowBordered(window: Ptr[SDL_Window], bordered: SDL_bool): Unit                  = extern

  def SDL_ShowWIndow(window: Ptr[SDL_Window]): Unit                       = extern
  def SDL_HideWIndow(window: Ptr[SDL_Window]): Unit                       = extern
  def SDL_RaiseWIndow(window: Ptr[SDL_Window]): Unit                      = extern
  def SDL_MaximizeWIndow(window: Ptr[SDL_Window]): Unit                   = extern
  def SDL_MinimizeWIndow(window: Ptr[SDL_Window]): Unit                   = extern
  def SDL_RestoreWIndow(window: Ptr[SDL_Window]): Unit                    = extern
  def SDL_SetWindowFullscreen(window: Ptr[SDL_Window], flags: UInt): CInt = extern

  def SDL_GetWindowSurface(window: Ptr[SDL_Window]): Ptr[SDL_Surface]                                   = extern
  def SDL_UpdateWindowSurface(window: Ptr[SDL_Window]): Ptr[SDL_Surface]                                = extern
  def SDL_UpdateWindowSurfaceRects(window: Ptr[SDL_Window], rects: Ptr[SDL_Rect], numrects: CInt): CInt = extern

  def SDL_SetWindowGrab(window: Ptr[SDL_Window], grabbed: SDL_bool): Unit        = extern
  def SDL_GetWindowGrab(window: Ptr[SDL_Window]): SDL_bool                       = extern
  def SDL_GetGrabbedWindow(): Ptr[SDL_Window]                                    = extern
  def SDL_SetWindowBrightness(window: Ptr[SDL_Window], brightness: CFloat): CInt = extern
  def SDL_GetWindowBrightness(window: Ptr[SDL_Window]): CFloat                   = extern
  def SDL_SetWindowGammaRamp(window: Ptr[SDL_Window], red: Ptr[UShort], green: Ptr[UShort], blue: Ptr[UShort]): CInt =
    extern
  def SDL_GetWindowGammaRamp(window: Ptr[SDL_Window], red: Ptr[UShort], green: Ptr[UShort], blue: Ptr[UShort]): CInt =
    extern

  type SDL_HitTestResult = UInt
  // TODO:
  // typedef SDL_HitTestResult (SDLCALL *SDL_HitTest)(SDL_Window *win,
  //                                               const SDL_Point *area,
  //                                               void *data);
  // extern DECLSPEC int SDLCALL SDL_SetWindowHitTest(SDL_Window * window,
  //                                                 SDL_HitTest callback,
  //                                                 void *callback_data);

  def SDL_DestroyWindow(window: Ptr[SDL_Window]): Unit = extern

  def SDL_IsScreenSaverEnabled(): SDL_bool = extern
  def SDL_EnableScreenSaver(): Unit        = extern
  def SDL_DisableScreenSaver(): Unit       = extern

  /* Start OpenGL support functions */

  def SDL_GL_LoadLibrary(path: CString): CInt         = extern
  def SDL_GL_GetProcAddress(proc: CString): Ptr[Byte] = extern
  def SDL_GL_UnloadLibrary(): Unit                    = extern

  def SDL_GL_ExtensionSupported(extension: CString): SDL_bool       = extern
  def SDL_GL_ResetAttributes(): Unit                                = extern
  def SDL_GL_SetAttribute(attr: SDL_GLattr, value: CInt): CInt      = extern
  def SDL_GL_GetAttribute(attr: SDL_GLattr, value: Ptr[CInt]): CInt = extern

  def SDL_GL_CreateContext(window: Ptr[SDL_Window]): SDL_GLContext              = extern
  def SDL_GL_MakeCurrent(window: Ptr[SDL_Window], context: SDL_GLContext): CInt = extern

  def SDL_GL_GetCurrentWindow(): Ptr[SDL_Window]                                        = extern
  def SDL_GL_GetCurrentContext(): SDL_GLContext                                         = extern
  def SDL_GL_GetDrawableSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern
  def SDL_GL_SetSwapInterval(interval: CInt): CInt                                      = extern
  def SDL_GL_GetSwapInterval(): CInt                                                    = extern
  def SDL_GL_SwapWindow(window: Ptr[SDL_Window]): Unit                                  = extern

  def SDL_GL_DeleteContext(context: SDL_GLContext): Unit = extern

  /* End OpenGl support functions */

  /** ************************************
    * ************** SDL.h ****************
    * ************************************
    */

  def SDL_Init(flags: UInt): CInt          = extern
  def SDL_InitSubSystem(flags: UInt): CInt = extern
  def SDL_QuitSubSystem(flags: UInt): Unit = extern
  def SDL_WasInit(flags: UInt): UInt       = extern
  def SDL_Quit(): Unit                     = extern

}
