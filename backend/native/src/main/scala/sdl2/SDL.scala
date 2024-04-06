package sdl2

// File generated with bindgen. Manually added code marked with [MANUAL]

import _root_.scala.scalanative.*
import _root_.scala.scalanative.libc.*
import _root_.scala.scalanative.unsafe.*
import _root_.scala.scalanative.unsigned.*

object predef:
  private[sdl2] trait CEnum[T](using eq: T =:= Int):
    given Tag[T] = Tag.Int.asInstanceOf[Tag[T]]
    extension (inline t: T)
      inline def int: CInt   = eq.apply(t)
      inline def uint: UInt  = eq.apply(t).toUInt
      inline def value: CInt = eq.apply(t)

object enumerations:
  import predef.*

  /** Array component order, low byte -> high byte.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_ArrayOrder = CInt
  object SDL_ArrayOrder extends CEnum[SDL_ArrayOrder]:
    given _tag: Tag[SDL_ArrayOrder]                   = Tag.Int
    inline def define(inline a: CInt): SDL_ArrayOrder = a
    val SDL_ARRAYORDER_NONE                           = define(0)
    val SDL_ARRAYORDER_RGB                            = define(1)
    val SDL_ARRAYORDER_RGBA                           = define(2)
    val SDL_ARRAYORDER_ARGB                           = define(3)
    val SDL_ARRAYORDER_BGR                            = define(4)
    val SDL_ARRAYORDER_BGRA                           = define(5)
    val SDL_ARRAYORDER_ABGR                           = define(6)
    inline def getName(inline value: SDL_ArrayOrder): Option[String] =
      inline value match
        case SDL_ARRAYORDER_NONE => Some("SDL_ARRAYORDER_NONE")
        case SDL_ARRAYORDER_RGB  => Some("SDL_ARRAYORDER_RGB")
        case SDL_ARRAYORDER_RGBA => Some("SDL_ARRAYORDER_RGBA")
        case SDL_ARRAYORDER_ARGB => Some("SDL_ARRAYORDER_ARGB")
        case SDL_ARRAYORDER_BGR  => Some("SDL_ARRAYORDER_BGR")
        case SDL_ARRAYORDER_BGRA => Some("SDL_ARRAYORDER_BGRA")
        case SDL_ARRAYORDER_ABGR => Some("SDL_ARRAYORDER_ABGR")
        case _                   => None
    extension (a: SDL_ArrayOrder)
      inline def &(b: SDL_ArrayOrder): SDL_ArrayOrder = a & b
      inline def |(b: SDL_ArrayOrder): SDL_ArrayOrder = a | b
      inline def is(b: SDL_ArrayOrder): Boolean       = (a & b) == b

  /** [bindgen] header: ./SDL_assert.h
    */
  opaque type SDL_AssertState = CInt
  object SDL_AssertState extends CEnum[SDL_AssertState]:
    given _tag: Tag[SDL_AssertState]                   = Tag.Int
    inline def define(inline a: CInt): SDL_AssertState = a
    val SDL_ASSERTION_RETRY                            = define(0)
    val SDL_ASSERTION_BREAK                            = define(1)
    val SDL_ASSERTION_ABORT                            = define(2)
    val SDL_ASSERTION_IGNORE                           = define(3)
    val SDL_ASSERTION_ALWAYS_IGNORE                    = define(4)
    inline def getName(inline value: SDL_AssertState): Option[String] =
      inline value match
        case SDL_ASSERTION_RETRY         => Some("SDL_ASSERTION_RETRY")
        case SDL_ASSERTION_BREAK         => Some("SDL_ASSERTION_BREAK")
        case SDL_ASSERTION_ABORT         => Some("SDL_ASSERTION_ABORT")
        case SDL_ASSERTION_IGNORE        => Some("SDL_ASSERTION_IGNORE")
        case SDL_ASSERTION_ALWAYS_IGNORE => Some("SDL_ASSERTION_ALWAYS_IGNORE")
        case _                           => None
    extension (a: SDL_AssertState)
      inline def &(b: SDL_AssertState): SDL_AssertState = a & b
      inline def |(b: SDL_AssertState): SDL_AssertState = a | b
      inline def is(b: SDL_AssertState): Boolean        = (a & b) == b

  /* Original bindgen code
  type SDL_AudioFormat = Uint16
  object SDL_AudioFormat:
    given _tag: Tag[SDL_AudioFormat]                        = Uint16._tag
    inline def apply(inline o: Uint16): SDL_AudioFormat     = o
    extension (v: SDL_AudioFormat) inline def value: Uint16 = v
   */

  /** Audio format flags.
    *
    * [bindgen] header: ./SDL_audio.h [MANUAL]
    */
  opaque type SDL_AudioFormat = UShort
  object SDL_AudioFormat: // extends CEnum[SDL_AudioFormat]:
    given _tag: Tag[SDL_AudioFormat]                  = Tag.UShort
    inline def define(inline a: Int): SDL_AudioFormat = a.toUShort
    val AUDIO_U8                                      = define(0x0008)
    val AUDIO_S8                                      = define(0x8008)
    val AUDIO_U16LSB                                  = define(0x0010)
    val AUDIO_S16LSB                                  = define(0x8010)
    val AUDIO_U16MSB                                  = define(0x1010)
    val AUDIO_S16MSB                                  = define(0x9010)
    val AUDIO_S32LSB                                  = define(0x8020)
    val AUDIO_S32MSB                                  = define(0x9020)
    val AUDIO_F32LSB                                  = define(0x8120)
    val AUDIO_F32MSB                                  = define(0x9120)
    inline def getName(inline value: SDL_AudioFormat): Option[String] =
      inline value match
        case AUDIO_U8     => Some("AUDIO_U8")
        case AUDIO_S8     => Some("AUDIO_S8")
        case AUDIO_U16LSB => Some("AUDIO_U16LSB")
        case AUDIO_S16LSB => Some("AUDIO_S16LSB")
        case AUDIO_U16MSB => Some("AUDIO_U16MSB")
        case AUDIO_S16MSB => Some("AUDIO_S16MSB")
        case AUDIO_S32LSB => Some("AUDIO_S32LSB")
        case AUDIO_S32MSB => Some("AUDIO_S32MSB")
        case AUDIO_F32LSB => Some("AUDIO_F32LSB")
        case AUDIO_F32MSB => Some("AUDIO_F32MSB")
        case _            => None
    extension (a: SDL_AudioFormat)
      inline def &(b: SDL_AudioFormat): SDL_AudioFormat = (a & b).toUShort
      inline def |(b: SDL_AudioFormat): SDL_AudioFormat = (a | b).toUShort
      inline def is(b: SDL_AudioFormat): Boolean        = (a & b) == b

  /** [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioStatus = CInt
  object SDL_AudioStatus extends CEnum[SDL_AudioStatus]:
    given _tag: Tag[SDL_AudioStatus]                   = Tag.Int
    inline def define(inline a: CInt): SDL_AudioStatus = a
    val SDL_AUDIO_STOPPED                              = define(0)
    val SDL_AUDIO_PLAYING                              = define(1)
    val SDL_AUDIO_PAUSED                               = define(2)
    inline def getName(inline value: SDL_AudioStatus): Option[String] =
      inline value match
        case SDL_AUDIO_STOPPED => Some("SDL_AUDIO_STOPPED")
        case SDL_AUDIO_PLAYING => Some("SDL_AUDIO_PLAYING")
        case SDL_AUDIO_PAUSED  => Some("SDL_AUDIO_PAUSED")
        case _                 => None
    extension (a: SDL_AudioStatus)
      inline def &(b: SDL_AudioStatus): SDL_AudioStatus = a & b
      inline def |(b: SDL_AudioStatus): SDL_AudioStatus = a | b
      inline def is(b: SDL_AudioStatus): Boolean        = (a & b) == b

  /** Bitmap pixel order, high bit -> low bit.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_BitmapOrder = CInt
  object SDL_BitmapOrder extends CEnum[SDL_BitmapOrder]:
    given _tag: Tag[SDL_BitmapOrder]                   = Tag.Int
    inline def define(inline a: CInt): SDL_BitmapOrder = a
    val SDL_BITMAPORDER_NONE                           = define(0)
    val SDL_BITMAPORDER_4321                           = define(1)
    val SDL_BITMAPORDER_1234                           = define(2)
    inline def getName(inline value: SDL_BitmapOrder): Option[String] =
      inline value match
        case SDL_BITMAPORDER_NONE => Some("SDL_BITMAPORDER_NONE")
        case SDL_BITMAPORDER_4321 => Some("SDL_BITMAPORDER_4321")
        case SDL_BITMAPORDER_1234 => Some("SDL_BITMAPORDER_1234")
        case _                    => None
    extension (a: SDL_BitmapOrder)
      inline def &(b: SDL_BitmapOrder): SDL_BitmapOrder = a & b
      inline def |(b: SDL_BitmapOrder): SDL_BitmapOrder = a | b
      inline def is(b: SDL_BitmapOrder): Boolean        = (a & b) == b

  /** The normalized factor used to multiply pixel components
    *
    * [bindgen] header: ./SDL_blendmode.h
    */
  opaque type SDL_BlendFactor = CInt
  object SDL_BlendFactor extends CEnum[SDL_BlendFactor]:
    given _tag: Tag[SDL_BlendFactor]                   = Tag.Int
    inline def define(inline a: CInt): SDL_BlendFactor = a
    val SDL_BLENDFACTOR_ZERO                           = define(1)
    val SDL_BLENDFACTOR_ONE                            = define(2)
    val SDL_BLENDFACTOR_SRC_COLOR                      = define(3)
    val SDL_BLENDFACTOR_ONE_MINUS_SRC_COLOR            = define(4)
    val SDL_BLENDFACTOR_SRC_ALPHA                      = define(5)
    val SDL_BLENDFACTOR_ONE_MINUS_SRC_ALPHA            = define(6)
    val SDL_BLENDFACTOR_DST_COLOR                      = define(7)
    val SDL_BLENDFACTOR_ONE_MINUS_DST_COLOR            = define(8)
    val SDL_BLENDFACTOR_DST_ALPHA                      = define(9)
    val SDL_BLENDFACTOR_ONE_MINUS_DST_ALPHA            = define(10)
    inline def getName(inline value: SDL_BlendFactor): Option[String] =
      inline value match
        case SDL_BLENDFACTOR_ZERO                => Some("SDL_BLENDFACTOR_ZERO")
        case SDL_BLENDFACTOR_ONE                 => Some("SDL_BLENDFACTOR_ONE")
        case SDL_BLENDFACTOR_SRC_COLOR           => Some("SDL_BLENDFACTOR_SRC_COLOR")
        case SDL_BLENDFACTOR_ONE_MINUS_SRC_COLOR => Some("SDL_BLENDFACTOR_ONE_MINUS_SRC_COLOR")
        case SDL_BLENDFACTOR_SRC_ALPHA           => Some("SDL_BLENDFACTOR_SRC_ALPHA")
        case SDL_BLENDFACTOR_ONE_MINUS_SRC_ALPHA => Some("SDL_BLENDFACTOR_ONE_MINUS_SRC_ALPHA")
        case SDL_BLENDFACTOR_DST_COLOR           => Some("SDL_BLENDFACTOR_DST_COLOR")
        case SDL_BLENDFACTOR_ONE_MINUS_DST_COLOR => Some("SDL_BLENDFACTOR_ONE_MINUS_DST_COLOR")
        case SDL_BLENDFACTOR_DST_ALPHA           => Some("SDL_BLENDFACTOR_DST_ALPHA")
        case SDL_BLENDFACTOR_ONE_MINUS_DST_ALPHA => Some("SDL_BLENDFACTOR_ONE_MINUS_DST_ALPHA")
        case _                                   => None
    extension (a: SDL_BlendFactor)
      inline def &(b: SDL_BlendFactor): SDL_BlendFactor = a & b
      inline def |(b: SDL_BlendFactor): SDL_BlendFactor = a | b
      inline def is(b: SDL_BlendFactor): Boolean        = (a & b) == b

  /** The blend mode used in SDL_RenderCopy() and drawing operations.
    *
    * [bindgen] header: ./SDL_blendmode.h
    */
  opaque type SDL_BlendMode = CInt
  object SDL_BlendMode extends CEnum[SDL_BlendMode]:
    given _tag: Tag[SDL_BlendMode]                   = Tag.Int
    inline def define(inline a: CInt): SDL_BlendMode = a
    val SDL_BLENDMODE_NONE                           = define(0)
    val SDL_BLENDMODE_BLEND                          = define(1)
    val SDL_BLENDMODE_ADD                            = define(2)
    val SDL_BLENDMODE_MOD                            = define(4)
    val SDL_BLENDMODE_MUL                            = define(8)
    val SDL_BLENDMODE_INVALID                        = define(2147483647)
    inline def getName(inline value: SDL_BlendMode): Option[String] =
      inline value match
        case SDL_BLENDMODE_NONE    => Some("SDL_BLENDMODE_NONE")
        case SDL_BLENDMODE_BLEND   => Some("SDL_BLENDMODE_BLEND")
        case SDL_BLENDMODE_ADD     => Some("SDL_BLENDMODE_ADD")
        case SDL_BLENDMODE_MOD     => Some("SDL_BLENDMODE_MOD")
        case SDL_BLENDMODE_MUL     => Some("SDL_BLENDMODE_MUL")
        case SDL_BLENDMODE_INVALID => Some("SDL_BLENDMODE_INVALID")
        case _                     => None
    extension (a: SDL_BlendMode)
      inline def &(b: SDL_BlendMode): SDL_BlendMode = a & b
      inline def |(b: SDL_BlendMode): SDL_BlendMode = a | b
      inline def is(b: SDL_BlendMode): Boolean      = (a & b) == b

  /** The blend operation used when combining source and destination pixel components
    *
    * [bindgen] header: ./SDL_blendmode.h
    */
  opaque type SDL_BlendOperation = CInt
  object SDL_BlendOperation extends CEnum[SDL_BlendOperation]:
    given _tag: Tag[SDL_BlendOperation]                   = Tag.Int
    inline def define(inline a: CInt): SDL_BlendOperation = a
    val SDL_BLENDOPERATION_ADD                            = define(1)
    val SDL_BLENDOPERATION_SUBTRACT                       = define(2)
    val SDL_BLENDOPERATION_REV_SUBTRACT                   = define(3)
    val SDL_BLENDOPERATION_MINIMUM                        = define(4)
    val SDL_BLENDOPERATION_MAXIMUM                        = define(5)
    inline def getName(inline value: SDL_BlendOperation): Option[String] =
      inline value match
        case SDL_BLENDOPERATION_ADD          => Some("SDL_BLENDOPERATION_ADD")
        case SDL_BLENDOPERATION_SUBTRACT     => Some("SDL_BLENDOPERATION_SUBTRACT")
        case SDL_BLENDOPERATION_REV_SUBTRACT => Some("SDL_BLENDOPERATION_REV_SUBTRACT")
        case SDL_BLENDOPERATION_MINIMUM      => Some("SDL_BLENDOPERATION_MINIMUM")
        case SDL_BLENDOPERATION_MAXIMUM      => Some("SDL_BLENDOPERATION_MAXIMUM")
        case _                               => None
    extension (a: SDL_BlendOperation)
      inline def &(b: SDL_BlendOperation): SDL_BlendOperation = a & b
      inline def |(b: SDL_BlendOperation): SDL_BlendOperation = a | b
      inline def is(b: SDL_BlendOperation): Boolean           = (a & b) == b

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_DUMMY_ENUM = CInt
  object SDL_DUMMY_ENUM extends CEnum[SDL_DUMMY_ENUM]:
    given _tag: Tag[SDL_DUMMY_ENUM]                   = Tag.Int
    inline def define(inline a: CInt): SDL_DUMMY_ENUM = a
    val DUMMY_ENUM_VALUE                              = define(0)
    inline def getName(inline value: SDL_DUMMY_ENUM): Option[String] =
      inline value match
        case DUMMY_ENUM_VALUE => Some("DUMMY_ENUM_VALUE")
        case _                => None
    extension (a: SDL_DUMMY_ENUM)
      inline def &(b: SDL_DUMMY_ENUM): SDL_DUMMY_ENUM = a & b
      inline def |(b: SDL_DUMMY_ENUM): SDL_DUMMY_ENUM = a | b
      inline def is(b: SDL_DUMMY_ENUM): Boolean       = (a & b) == b

  /** Event subtype for display events
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_DisplayEventID = CInt
  object SDL_DisplayEventID extends CEnum[SDL_DisplayEventID]:
    given _tag: Tag[SDL_DisplayEventID]                   = Tag.Int
    inline def define(inline a: CInt): SDL_DisplayEventID = a
    val SDL_DISPLAYEVENT_NONE                             = define(0)
    val SDL_DISPLAYEVENT_ORIENTATION                      = define(1)
    val SDL_DISPLAYEVENT_CONNECTED                        = define(2)
    val SDL_DISPLAYEVENT_DISCONNECTED                     = define(3)
    val SDL_DISPLAYEVENT_MOVED                            = define(4)
    inline def getName(inline value: SDL_DisplayEventID): Option[String] =
      inline value match
        case SDL_DISPLAYEVENT_NONE         => Some("SDL_DISPLAYEVENT_NONE")
        case SDL_DISPLAYEVENT_ORIENTATION  => Some("SDL_DISPLAYEVENT_ORIENTATION")
        case SDL_DISPLAYEVENT_CONNECTED    => Some("SDL_DISPLAYEVENT_CONNECTED")
        case SDL_DISPLAYEVENT_DISCONNECTED => Some("SDL_DISPLAYEVENT_DISCONNECTED")
        case SDL_DISPLAYEVENT_MOVED        => Some("SDL_DISPLAYEVENT_MOVED")
        case _                             => None
    extension (a: SDL_DisplayEventID)
      inline def &(b: SDL_DisplayEventID): SDL_DisplayEventID = a & b
      inline def |(b: SDL_DisplayEventID): SDL_DisplayEventID = a | b
      inline def is(b: SDL_DisplayEventID): Boolean           = (a & b) == b

  /** Display orientation
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_DisplayOrientation = CInt
  object SDL_DisplayOrientation extends CEnum[SDL_DisplayOrientation]:
    given _tag: Tag[SDL_DisplayOrientation]                   = Tag.Int
    inline def define(inline a: CInt): SDL_DisplayOrientation = a
    val SDL_ORIENTATION_UNKNOWN                               = define(0)
    val SDL_ORIENTATION_LANDSCAPE                             = define(1)
    val SDL_ORIENTATION_LANDSCAPE_FLIPPED                     = define(2)
    val SDL_ORIENTATION_PORTRAIT                              = define(3)
    val SDL_ORIENTATION_PORTRAIT_FLIPPED                      = define(4)
    inline def getName(inline value: SDL_DisplayOrientation): Option[String] =
      inline value match
        case SDL_ORIENTATION_UNKNOWN           => Some("SDL_ORIENTATION_UNKNOWN")
        case SDL_ORIENTATION_LANDSCAPE         => Some("SDL_ORIENTATION_LANDSCAPE")
        case SDL_ORIENTATION_LANDSCAPE_FLIPPED => Some("SDL_ORIENTATION_LANDSCAPE_FLIPPED")
        case SDL_ORIENTATION_PORTRAIT          => Some("SDL_ORIENTATION_PORTRAIT")
        case SDL_ORIENTATION_PORTRAIT_FLIPPED  => Some("SDL_ORIENTATION_PORTRAIT_FLIPPED")
        case _                                 => None
    extension (a: SDL_DisplayOrientation)
      inline def &(b: SDL_DisplayOrientation): SDL_DisplayOrientation = a & b
      inline def |(b: SDL_DisplayOrientation): SDL_DisplayOrientation = a | b
      inline def is(b: SDL_DisplayOrientation): Boolean               = (a & b) == b

  /** The types of events that can be delivered.
    *
    * [bindgen] header: ./SDL_events.h [MANUAL]
    */
  opaque type SDL_EventType = CInt
  object SDL_EventType extends CEnum[SDL_EventType]:
    given _tag: Tag[SDL_EventType]                     = Tag.Int
    inline def define(inline a: CInt): SDL_EventType   = a
    inline def define(inline a: UInt): SDL_EventType   = a.toInt
    val SDL_FIRSTEVENT                                 = define(0)
    val SDL_QUIT                                       = define(256)
    val SDL_APP_TERMINATING                            = define(257)
    val SDL_APP_LOWMEMORY                              = define(258)
    val SDL_APP_WILLENTERBACKGROUND                    = define(259)
    val SDL_APP_DIDENTERBACKGROUND                     = define(260)
    val SDL_APP_WILLENTERFOREGROUND                    = define(261)
    val SDL_APP_DIDENTERFOREGROUND                     = define(262)
    val SDL_LOCALECHANGED                              = define(263)
    val SDL_DISPLAYEVENT                               = define(336)
    val SDL_WINDOWEVENT                                = define(512)
    val SDL_SYSWMEVENT                                 = define(513)
    val SDL_KEYDOWN                                    = define(768)
    val SDL_KEYUP                                      = define(769)
    val SDL_TEXTEDITING                                = define(770)
    val SDL_TEXTINPUT                                  = define(771)
    val SDL_KEYMAPCHANGED                              = define(772)
    val SDL_TEXTEDITING_EXT                            = define(773)
    val SDL_MOUSEMOTION                                = define(1024)
    val SDL_MOUSEBUTTONDOWN                            = define(1025)
    val SDL_MOUSEBUTTONUP                              = define(1026)
    val SDL_MOUSEWHEEL                                 = define(1027)
    val SDL_JOYAXISMOTION                              = define(1536)
    val SDL_JOYBALLMOTION                              = define(1537)
    val SDL_JOYHATMOTION                               = define(1538)
    val SDL_JOYBUTTONDOWN                              = define(1539)
    val SDL_JOYBUTTONUP                                = define(1540)
    val SDL_JOYDEVICEADDED                             = define(1541)
    val SDL_JOYDEVICEREMOVED                           = define(1542)
    val SDL_JOYBATTERYUPDATED                          = define(1543)
    val SDL_CONTROLLERAXISMOTION                       = define(1616)
    val SDL_CONTROLLERBUTTONDOWN                       = define(1617)
    val SDL_CONTROLLERBUTTONUP                         = define(1618)
    val SDL_CONTROLLERDEVICEADDED                      = define(1619)
    val SDL_CONTROLLERDEVICEREMOVED                    = define(1620)
    val SDL_CONTROLLERDEVICEREMAPPED                   = define(1621)
    val SDL_CONTROLLERTOUCHPADDOWN                     = define(1622)
    val SDL_CONTROLLERTOUCHPADMOTION                   = define(1623)
    val SDL_CONTROLLERTOUCHPADUP                       = define(1624)
    val SDL_CONTROLLERSENSORUPDATE                     = define(1625)
    val SDL_CONTROLLERUPDATECOMPLETE_RESERVED_FOR_SDL3 = define(1626)
    val SDL_CONTROLLERSTEAMHANDLEUPDATED               = define(1627)
    val SDL_FINGERDOWN                                 = define(1792)
    val SDL_FINGERUP                                   = define(1793)
    val SDL_FINGERMOTION                               = define(1794)
    val SDL_DOLLARGESTURE                              = define(2048)
    val SDL_DOLLARRECORD                               = define(2049)
    val SDL_MULTIGESTURE                               = define(2050)
    val SDL_CLIPBOARDUPDATE                            = define(2304)
    val SDL_DROPFILE                                   = define(4096)
    val SDL_DROPTEXT                                   = define(4097)
    val SDL_DROPBEGIN                                  = define(4098)
    val SDL_DROPCOMPLETE                               = define(4099)
    val SDL_AUDIODEVICEADDED                           = define(4352)
    val SDL_AUDIODEVICEREMOVED                         = define(4353)
    val SDL_SENSORUPDATE                               = define(4608)
    val SDL_RENDER_TARGETS_RESET                       = define(8192)
    val SDL_RENDER_DEVICE_RESET                        = define(8193)
    val SDL_POLLSENTINEL                               = define(32512)
    val SDL_USEREVENT                                  = define(32768)
    val SDL_LASTEVENT                                  = define(65535)
    inline def getName(inline value: SDL_EventType): Option[String] =
      inline value match
        case SDL_FIRSTEVENT                                 => Some("SDL_FIRSTEVENT")
        case SDL_QUIT                                       => Some("SDL_QUIT")
        case SDL_APP_TERMINATING                            => Some("SDL_APP_TERMINATING")
        case SDL_APP_LOWMEMORY                              => Some("SDL_APP_LOWMEMORY")
        case SDL_APP_WILLENTERBACKGROUND                    => Some("SDL_APP_WILLENTERBACKGROUND")
        case SDL_APP_DIDENTERBACKGROUND                     => Some("SDL_APP_DIDENTERBACKGROUND")
        case SDL_APP_WILLENTERFOREGROUND                    => Some("SDL_APP_WILLENTERFOREGROUND")
        case SDL_APP_DIDENTERFOREGROUND                     => Some("SDL_APP_DIDENTERFOREGROUND")
        case SDL_LOCALECHANGED                              => Some("SDL_LOCALECHANGED")
        case SDL_DISPLAYEVENT                               => Some("SDL_DISPLAYEVENT")
        case SDL_WINDOWEVENT                                => Some("SDL_WINDOWEVENT")
        case SDL_SYSWMEVENT                                 => Some("SDL_SYSWMEVENT")
        case SDL_KEYDOWN                                    => Some("SDL_KEYDOWN")
        case SDL_KEYUP                                      => Some("SDL_KEYUP")
        case SDL_TEXTEDITING                                => Some("SDL_TEXTEDITING")
        case SDL_TEXTINPUT                                  => Some("SDL_TEXTINPUT")
        case SDL_KEYMAPCHANGED                              => Some("SDL_KEYMAPCHANGED")
        case SDL_TEXTEDITING_EXT                            => Some("SDL_TEXTEDITING_EXT")
        case SDL_MOUSEMOTION                                => Some("SDL_MOUSEMOTION")
        case SDL_MOUSEBUTTONDOWN                            => Some("SDL_MOUSEBUTTONDOWN")
        case SDL_MOUSEBUTTONUP                              => Some("SDL_MOUSEBUTTONUP")
        case SDL_MOUSEWHEEL                                 => Some("SDL_MOUSEWHEEL")
        case SDL_JOYAXISMOTION                              => Some("SDL_JOYAXISMOTION")
        case SDL_JOYBALLMOTION                              => Some("SDL_JOYBALLMOTION")
        case SDL_JOYHATMOTION                               => Some("SDL_JOYHATMOTION")
        case SDL_JOYBUTTONDOWN                              => Some("SDL_JOYBUTTONDOWN")
        case SDL_JOYBUTTONUP                                => Some("SDL_JOYBUTTONUP")
        case SDL_JOYDEVICEADDED                             => Some("SDL_JOYDEVICEADDED")
        case SDL_JOYDEVICEREMOVED                           => Some("SDL_JOYDEVICEREMOVED")
        case SDL_JOYBATTERYUPDATED                          => Some("SDL_JOYBATTERYUPDATED")
        case SDL_CONTROLLERAXISMOTION                       => Some("SDL_CONTROLLERAXISMOTION")
        case SDL_CONTROLLERBUTTONDOWN                       => Some("SDL_CONTROLLERBUTTONDOWN")
        case SDL_CONTROLLERBUTTONUP                         => Some("SDL_CONTROLLERBUTTONUP")
        case SDL_CONTROLLERDEVICEADDED                      => Some("SDL_CONTROLLERDEVICEADDED")
        case SDL_CONTROLLERDEVICEREMOVED                    => Some("SDL_CONTROLLERDEVICEREMOVED")
        case SDL_CONTROLLERDEVICEREMAPPED                   => Some("SDL_CONTROLLERDEVICEREMAPPED")
        case SDL_CONTROLLERTOUCHPADDOWN                     => Some("SDL_CONTROLLERTOUCHPADDOWN")
        case SDL_CONTROLLERTOUCHPADMOTION                   => Some("SDL_CONTROLLERTOUCHPADMOTION")
        case SDL_CONTROLLERTOUCHPADUP                       => Some("SDL_CONTROLLERTOUCHPADUP")
        case SDL_CONTROLLERSENSORUPDATE                     => Some("SDL_CONTROLLERSENSORUPDATE")
        case SDL_CONTROLLERUPDATECOMPLETE_RESERVED_FOR_SDL3 => Some("SDL_CONTROLLERUPDATECOMPLETE_RESERVED_FOR_SDL3")
        case SDL_CONTROLLERSTEAMHANDLEUPDATED               => Some("SDL_CONTROLLERSTEAMHANDLEUPDATED")
        case SDL_FINGERDOWN                                 => Some("SDL_FINGERDOWN")
        case SDL_FINGERUP                                   => Some("SDL_FINGERUP")
        case SDL_FINGERMOTION                               => Some("SDL_FINGERMOTION")
        case SDL_DOLLARGESTURE                              => Some("SDL_DOLLARGESTURE")
        case SDL_DOLLARRECORD                               => Some("SDL_DOLLARRECORD")
        case SDL_MULTIGESTURE                               => Some("SDL_MULTIGESTURE")
        case SDL_CLIPBOARDUPDATE                            => Some("SDL_CLIPBOARDUPDATE")
        case SDL_DROPFILE                                   => Some("SDL_DROPFILE")
        case SDL_DROPTEXT                                   => Some("SDL_DROPTEXT")
        case SDL_DROPBEGIN                                  => Some("SDL_DROPBEGIN")
        case SDL_DROPCOMPLETE                               => Some("SDL_DROPCOMPLETE")
        case SDL_AUDIODEVICEADDED                           => Some("SDL_AUDIODEVICEADDED")
        case SDL_AUDIODEVICEREMOVED                         => Some("SDL_AUDIODEVICEREMOVED")
        case SDL_SENSORUPDATE                               => Some("SDL_SENSORUPDATE")
        case SDL_RENDER_TARGETS_RESET                       => Some("SDL_RENDER_TARGETS_RESET")
        case SDL_RENDER_DEVICE_RESET                        => Some("SDL_RENDER_DEVICE_RESET")
        case SDL_POLLSENTINEL                               => Some("SDL_POLLSENTINEL")
        case SDL_USEREVENT                                  => Some("SDL_USEREVENT")
        case SDL_LASTEVENT                                  => Some("SDL_LASTEVENT")
        case _                                              => None
    extension (a: SDL_EventType)
      inline def &(b: SDL_EventType): SDL_EventType = a & b
      inline def |(b: SDL_EventType): SDL_EventType = a | b
      inline def is(b: SDL_EventType): Boolean      = (a & b) == b

  /** Window flash operation
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_FlashOperation = CInt
  object SDL_FlashOperation extends CEnum[SDL_FlashOperation]:
    given _tag: Tag[SDL_FlashOperation]                   = Tag.Int
    inline def define(inline a: CInt): SDL_FlashOperation = a
    val SDL_FLASH_CANCEL                                  = define(0)
    val SDL_FLASH_BRIEFLY                                 = define(1)
    val SDL_FLASH_UNTIL_FOCUSED                           = define(2)
    inline def getName(inline value: SDL_FlashOperation): Option[String] =
      inline value match
        case SDL_FLASH_CANCEL        => Some("SDL_FLASH_CANCEL")
        case SDL_FLASH_BRIEFLY       => Some("SDL_FLASH_BRIEFLY")
        case SDL_FLASH_UNTIL_FOCUSED => Some("SDL_FLASH_UNTIL_FOCUSED")
        case _                       => None
    extension (a: SDL_FlashOperation)
      inline def &(b: SDL_FlashOperation): SDL_FlashOperation = a & b
      inline def |(b: SDL_FlashOperation): SDL_FlashOperation = a | b
      inline def is(b: SDL_FlashOperation): Boolean           = (a & b) == b

  /** [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLContextResetNotification = CInt
  object SDL_GLContextResetNotification extends CEnum[SDL_GLContextResetNotification]:
    given _tag: Tag[SDL_GLContextResetNotification]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GLContextResetNotification = a
    val SDL_GL_CONTEXT_RESET_NO_NOTIFICATION                          = define(0)
    val SDL_GL_CONTEXT_RESET_LOSE_CONTEXT                             = define(1)
    inline def getName(inline value: SDL_GLContextResetNotification): Option[String] =
      inline value match
        case SDL_GL_CONTEXT_RESET_NO_NOTIFICATION => Some("SDL_GL_CONTEXT_RESET_NO_NOTIFICATION")
        case SDL_GL_CONTEXT_RESET_LOSE_CONTEXT    => Some("SDL_GL_CONTEXT_RESET_LOSE_CONTEXT")
        case _                                    => None
    extension (a: SDL_GLContextResetNotification)
      inline def &(b: SDL_GLContextResetNotification): SDL_GLContextResetNotification = a & b
      inline def |(b: SDL_GLContextResetNotification): SDL_GLContextResetNotification = a | b
      inline def is(b: SDL_GLContextResetNotification): Boolean                       = (a & b) == b

  /** OpenGL configuration attributes
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLattr = CInt
  object SDL_GLattr extends CEnum[SDL_GLattr]:
    given _tag: Tag[SDL_GLattr]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GLattr = a
    val SDL_GL_RED_SIZE                           = define(0)
    val SDL_GL_GREEN_SIZE                         = define(1)
    val SDL_GL_BLUE_SIZE                          = define(2)
    val SDL_GL_ALPHA_SIZE                         = define(3)
    val SDL_GL_BUFFER_SIZE                        = define(4)
    val SDL_GL_DOUBLEBUFFER                       = define(5)
    val SDL_GL_DEPTH_SIZE                         = define(6)
    val SDL_GL_STENCIL_SIZE                       = define(7)
    val SDL_GL_ACCUM_RED_SIZE                     = define(8)
    val SDL_GL_ACCUM_GREEN_SIZE                   = define(9)
    val SDL_GL_ACCUM_BLUE_SIZE                    = define(10)
    val SDL_GL_ACCUM_ALPHA_SIZE                   = define(11)
    val SDL_GL_STEREO                             = define(12)
    val SDL_GL_MULTISAMPLEBUFFERS                 = define(13)
    val SDL_GL_MULTISAMPLESAMPLES                 = define(14)
    val SDL_GL_ACCELERATED_VISUAL                 = define(15)
    val SDL_GL_RETAINED_BACKING                   = define(16)
    val SDL_GL_CONTEXT_MAJOR_VERSION              = define(17)
    val SDL_GL_CONTEXT_MINOR_VERSION              = define(18)
    val SDL_GL_CONTEXT_EGL                        = define(19)
    val SDL_GL_CONTEXT_FLAGS                      = define(20)
    val SDL_GL_CONTEXT_PROFILE_MASK               = define(21)
    val SDL_GL_SHARE_WITH_CURRENT_CONTEXT         = define(22)
    val SDL_GL_FRAMEBUFFER_SRGB_CAPABLE           = define(23)
    val SDL_GL_CONTEXT_RELEASE_BEHAVIOR           = define(24)
    val SDL_GL_CONTEXT_RESET_NOTIFICATION         = define(25)
    val SDL_GL_CONTEXT_NO_ERROR                   = define(26)
    val SDL_GL_FLOATBUFFERS                       = define(27)
    inline def getName(inline value: SDL_GLattr): Option[String] =
      inline value match
        case SDL_GL_RED_SIZE                   => Some("SDL_GL_RED_SIZE")
        case SDL_GL_GREEN_SIZE                 => Some("SDL_GL_GREEN_SIZE")
        case SDL_GL_BLUE_SIZE                  => Some("SDL_GL_BLUE_SIZE")
        case SDL_GL_ALPHA_SIZE                 => Some("SDL_GL_ALPHA_SIZE")
        case SDL_GL_BUFFER_SIZE                => Some("SDL_GL_BUFFER_SIZE")
        case SDL_GL_DOUBLEBUFFER               => Some("SDL_GL_DOUBLEBUFFER")
        case SDL_GL_DEPTH_SIZE                 => Some("SDL_GL_DEPTH_SIZE")
        case SDL_GL_STENCIL_SIZE               => Some("SDL_GL_STENCIL_SIZE")
        case SDL_GL_ACCUM_RED_SIZE             => Some("SDL_GL_ACCUM_RED_SIZE")
        case SDL_GL_ACCUM_GREEN_SIZE           => Some("SDL_GL_ACCUM_GREEN_SIZE")
        case SDL_GL_ACCUM_BLUE_SIZE            => Some("SDL_GL_ACCUM_BLUE_SIZE")
        case SDL_GL_ACCUM_ALPHA_SIZE           => Some("SDL_GL_ACCUM_ALPHA_SIZE")
        case SDL_GL_STEREO                     => Some("SDL_GL_STEREO")
        case SDL_GL_MULTISAMPLEBUFFERS         => Some("SDL_GL_MULTISAMPLEBUFFERS")
        case SDL_GL_MULTISAMPLESAMPLES         => Some("SDL_GL_MULTISAMPLESAMPLES")
        case SDL_GL_ACCELERATED_VISUAL         => Some("SDL_GL_ACCELERATED_VISUAL")
        case SDL_GL_RETAINED_BACKING           => Some("SDL_GL_RETAINED_BACKING")
        case SDL_GL_CONTEXT_MAJOR_VERSION      => Some("SDL_GL_CONTEXT_MAJOR_VERSION")
        case SDL_GL_CONTEXT_MINOR_VERSION      => Some("SDL_GL_CONTEXT_MINOR_VERSION")
        case SDL_GL_CONTEXT_EGL                => Some("SDL_GL_CONTEXT_EGL")
        case SDL_GL_CONTEXT_FLAGS              => Some("SDL_GL_CONTEXT_FLAGS")
        case SDL_GL_CONTEXT_PROFILE_MASK       => Some("SDL_GL_CONTEXT_PROFILE_MASK")
        case SDL_GL_SHARE_WITH_CURRENT_CONTEXT => Some("SDL_GL_SHARE_WITH_CURRENT_CONTEXT")
        case SDL_GL_FRAMEBUFFER_SRGB_CAPABLE   => Some("SDL_GL_FRAMEBUFFER_SRGB_CAPABLE")
        case SDL_GL_CONTEXT_RELEASE_BEHAVIOR   => Some("SDL_GL_CONTEXT_RELEASE_BEHAVIOR")
        case SDL_GL_CONTEXT_RESET_NOTIFICATION => Some("SDL_GL_CONTEXT_RESET_NOTIFICATION")
        case SDL_GL_CONTEXT_NO_ERROR           => Some("SDL_GL_CONTEXT_NO_ERROR")
        case SDL_GL_FLOATBUFFERS               => Some("SDL_GL_FLOATBUFFERS")
        case _                                 => None
    extension (a: SDL_GLattr)
      inline def &(b: SDL_GLattr): SDL_GLattr = a & b
      inline def |(b: SDL_GLattr): SDL_GLattr = a | b
      inline def is(b: SDL_GLattr): Boolean   = (a & b) == b

  /** [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLcontextFlag = CInt
  object SDL_GLcontextFlag extends CEnum[SDL_GLcontextFlag]:
    given _tag: Tag[SDL_GLcontextFlag]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GLcontextFlag = a
    val SDL_GL_CONTEXT_DEBUG_FLAG                        = define(1)
    val SDL_GL_CONTEXT_FORWARD_COMPATIBLE_FLAG           = define(2)
    val SDL_GL_CONTEXT_ROBUST_ACCESS_FLAG                = define(4)
    val SDL_GL_CONTEXT_RESET_ISOLATION_FLAG              = define(8)
    inline def getName(inline value: SDL_GLcontextFlag): Option[String] =
      inline value match
        case SDL_GL_CONTEXT_DEBUG_FLAG              => Some("SDL_GL_CONTEXT_DEBUG_FLAG")
        case SDL_GL_CONTEXT_FORWARD_COMPATIBLE_FLAG => Some("SDL_GL_CONTEXT_FORWARD_COMPATIBLE_FLAG")
        case SDL_GL_CONTEXT_ROBUST_ACCESS_FLAG      => Some("SDL_GL_CONTEXT_ROBUST_ACCESS_FLAG")
        case SDL_GL_CONTEXT_RESET_ISOLATION_FLAG    => Some("SDL_GL_CONTEXT_RESET_ISOLATION_FLAG")
        case _                                      => None
    extension (a: SDL_GLcontextFlag)
      inline def &(b: SDL_GLcontextFlag): SDL_GLcontextFlag = a & b
      inline def |(b: SDL_GLcontextFlag): SDL_GLcontextFlag = a | b
      inline def is(b: SDL_GLcontextFlag): Boolean          = (a & b) == b

  /** [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLcontextReleaseFlag = CInt
  object SDL_GLcontextReleaseFlag extends CEnum[SDL_GLcontextReleaseFlag]:
    given _tag: Tag[SDL_GLcontextReleaseFlag]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GLcontextReleaseFlag = a
    val SDL_GL_CONTEXT_RELEASE_BEHAVIOR_NONE                    = define(0)
    val SDL_GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH                   = define(1)
    inline def getName(inline value: SDL_GLcontextReleaseFlag): Option[String] =
      inline value match
        case SDL_GL_CONTEXT_RELEASE_BEHAVIOR_NONE  => Some("SDL_GL_CONTEXT_RELEASE_BEHAVIOR_NONE")
        case SDL_GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH => Some("SDL_GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH")
        case _                                     => None
    extension (a: SDL_GLcontextReleaseFlag)
      inline def &(b: SDL_GLcontextReleaseFlag): SDL_GLcontextReleaseFlag = a & b
      inline def |(b: SDL_GLcontextReleaseFlag): SDL_GLcontextReleaseFlag = a | b
      inline def is(b: SDL_GLcontextReleaseFlag): Boolean                 = (a & b) == b

  /** [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLprofile = CInt
  object SDL_GLprofile extends CEnum[SDL_GLprofile]:
    given _tag: Tag[SDL_GLprofile]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GLprofile = a
    val SDL_GL_CONTEXT_PROFILE_CORE                  = define(1)
    val SDL_GL_CONTEXT_PROFILE_COMPATIBILITY         = define(2)
    val SDL_GL_CONTEXT_PROFILE_ES                    = define(4)
    inline def getName(inline value: SDL_GLprofile): Option[String] =
      inline value match
        case SDL_GL_CONTEXT_PROFILE_CORE          => Some("SDL_GL_CONTEXT_PROFILE_CORE")
        case SDL_GL_CONTEXT_PROFILE_COMPATIBILITY => Some("SDL_GL_CONTEXT_PROFILE_COMPATIBILITY")
        case SDL_GL_CONTEXT_PROFILE_ES            => Some("SDL_GL_CONTEXT_PROFILE_ES")
        case _                                    => None
    extension (a: SDL_GLprofile)
      inline def &(b: SDL_GLprofile): SDL_GLprofile = a & b
      inline def |(b: SDL_GLprofile): SDL_GLprofile = a | b
      inline def is(b: SDL_GLprofile): Boolean      = (a & b) == b

  /** The list of axes available from a controller
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameControllerAxis = CInt
  object SDL_GameControllerAxis extends CEnum[SDL_GameControllerAxis]:
    given _tag: Tag[SDL_GameControllerAxis]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GameControllerAxis = a
    val SDL_CONTROLLER_AXIS_INVALID                           = define(-1)
    val SDL_CONTROLLER_AXIS_LEFTX                             = define(0)
    val SDL_CONTROLLER_AXIS_LEFTY                             = define(1)
    val SDL_CONTROLLER_AXIS_RIGHTX                            = define(2)
    val SDL_CONTROLLER_AXIS_RIGHTY                            = define(3)
    val SDL_CONTROLLER_AXIS_TRIGGERLEFT                       = define(4)
    val SDL_CONTROLLER_AXIS_TRIGGERRIGHT                      = define(5)
    val SDL_CONTROLLER_AXIS_MAX                               = define(6)
    inline def getName(inline value: SDL_GameControllerAxis): Option[String] =
      inline value match
        case SDL_CONTROLLER_AXIS_INVALID      => Some("SDL_CONTROLLER_AXIS_INVALID")
        case SDL_CONTROLLER_AXIS_LEFTX        => Some("SDL_CONTROLLER_AXIS_LEFTX")
        case SDL_CONTROLLER_AXIS_LEFTY        => Some("SDL_CONTROLLER_AXIS_LEFTY")
        case SDL_CONTROLLER_AXIS_RIGHTX       => Some("SDL_CONTROLLER_AXIS_RIGHTX")
        case SDL_CONTROLLER_AXIS_RIGHTY       => Some("SDL_CONTROLLER_AXIS_RIGHTY")
        case SDL_CONTROLLER_AXIS_TRIGGERLEFT  => Some("SDL_CONTROLLER_AXIS_TRIGGERLEFT")
        case SDL_CONTROLLER_AXIS_TRIGGERRIGHT => Some("SDL_CONTROLLER_AXIS_TRIGGERRIGHT")
        case SDL_CONTROLLER_AXIS_MAX          => Some("SDL_CONTROLLER_AXIS_MAX")
        case _                                => None
    extension (a: SDL_GameControllerAxis)
      inline def &(b: SDL_GameControllerAxis): SDL_GameControllerAxis = a & b
      inline def |(b: SDL_GameControllerAxis): SDL_GameControllerAxis = a | b
      inline def is(b: SDL_GameControllerAxis): Boolean               = (a & b) == b

  /** [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameControllerBindType = CInt
  object SDL_GameControllerBindType extends CEnum[SDL_GameControllerBindType]:
    given _tag: Tag[SDL_GameControllerBindType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GameControllerBindType = a
    val SDL_CONTROLLER_BINDTYPE_NONE                              = define(0)
    val SDL_CONTROLLER_BINDTYPE_BUTTON                            = define(1)
    val SDL_CONTROLLER_BINDTYPE_AXIS                              = define(2)
    val SDL_CONTROLLER_BINDTYPE_HAT                               = define(3)
    inline def getName(inline value: SDL_GameControllerBindType): Option[String] =
      inline value match
        case SDL_CONTROLLER_BINDTYPE_NONE   => Some("SDL_CONTROLLER_BINDTYPE_NONE")
        case SDL_CONTROLLER_BINDTYPE_BUTTON => Some("SDL_CONTROLLER_BINDTYPE_BUTTON")
        case SDL_CONTROLLER_BINDTYPE_AXIS   => Some("SDL_CONTROLLER_BINDTYPE_AXIS")
        case SDL_CONTROLLER_BINDTYPE_HAT    => Some("SDL_CONTROLLER_BINDTYPE_HAT")
        case _                              => None
    extension (a: SDL_GameControllerBindType)
      inline def &(b: SDL_GameControllerBindType): SDL_GameControllerBindType = a & b
      inline def |(b: SDL_GameControllerBindType): SDL_GameControllerBindType = a | b
      inline def is(b: SDL_GameControllerBindType): Boolean                   = (a & b) == b

  /** The list of buttons available from a controller
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameControllerButton = CInt
  object SDL_GameControllerButton extends CEnum[SDL_GameControllerButton]:
    given _tag: Tag[SDL_GameControllerButton]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GameControllerButton = a
    val SDL_CONTROLLER_BUTTON_INVALID                           = define(-1)
    val SDL_CONTROLLER_BUTTON_A                                 = define(0)
    val SDL_CONTROLLER_BUTTON_B                                 = define(1)
    val SDL_CONTROLLER_BUTTON_X                                 = define(2)
    val SDL_CONTROLLER_BUTTON_Y                                 = define(3)
    val SDL_CONTROLLER_BUTTON_BACK                              = define(4)
    val SDL_CONTROLLER_BUTTON_GUIDE                             = define(5)
    val SDL_CONTROLLER_BUTTON_START                             = define(6)
    val SDL_CONTROLLER_BUTTON_LEFTSTICK                         = define(7)
    val SDL_CONTROLLER_BUTTON_RIGHTSTICK                        = define(8)
    val SDL_CONTROLLER_BUTTON_LEFTSHOULDER                      = define(9)
    val SDL_CONTROLLER_BUTTON_RIGHTSHOULDER                     = define(10)
    val SDL_CONTROLLER_BUTTON_DPAD_UP                           = define(11)
    val SDL_CONTROLLER_BUTTON_DPAD_DOWN                         = define(12)
    val SDL_CONTROLLER_BUTTON_DPAD_LEFT                         = define(13)
    val SDL_CONTROLLER_BUTTON_DPAD_RIGHT                        = define(14)
    val SDL_CONTROLLER_BUTTON_MISC1                             = define(15)
    val SDL_CONTROLLER_BUTTON_PADDLE1                           = define(16)
    val SDL_CONTROLLER_BUTTON_PADDLE2                           = define(17)
    val SDL_CONTROLLER_BUTTON_PADDLE3                           = define(18)
    val SDL_CONTROLLER_BUTTON_PADDLE4                           = define(19)
    val SDL_CONTROLLER_BUTTON_TOUCHPAD                          = define(20)
    val SDL_CONTROLLER_BUTTON_MAX                               = define(21)
    inline def getName(inline value: SDL_GameControllerButton): Option[String] =
      inline value match
        case SDL_CONTROLLER_BUTTON_INVALID       => Some("SDL_CONTROLLER_BUTTON_INVALID")
        case SDL_CONTROLLER_BUTTON_A             => Some("SDL_CONTROLLER_BUTTON_A")
        case SDL_CONTROLLER_BUTTON_B             => Some("SDL_CONTROLLER_BUTTON_B")
        case SDL_CONTROLLER_BUTTON_X             => Some("SDL_CONTROLLER_BUTTON_X")
        case SDL_CONTROLLER_BUTTON_Y             => Some("SDL_CONTROLLER_BUTTON_Y")
        case SDL_CONTROLLER_BUTTON_BACK          => Some("SDL_CONTROLLER_BUTTON_BACK")
        case SDL_CONTROLLER_BUTTON_GUIDE         => Some("SDL_CONTROLLER_BUTTON_GUIDE")
        case SDL_CONTROLLER_BUTTON_START         => Some("SDL_CONTROLLER_BUTTON_START")
        case SDL_CONTROLLER_BUTTON_LEFTSTICK     => Some("SDL_CONTROLLER_BUTTON_LEFTSTICK")
        case SDL_CONTROLLER_BUTTON_RIGHTSTICK    => Some("SDL_CONTROLLER_BUTTON_RIGHTSTICK")
        case SDL_CONTROLLER_BUTTON_LEFTSHOULDER  => Some("SDL_CONTROLLER_BUTTON_LEFTSHOULDER")
        case SDL_CONTROLLER_BUTTON_RIGHTSHOULDER => Some("SDL_CONTROLLER_BUTTON_RIGHTSHOULDER")
        case SDL_CONTROLLER_BUTTON_DPAD_UP       => Some("SDL_CONTROLLER_BUTTON_DPAD_UP")
        case SDL_CONTROLLER_BUTTON_DPAD_DOWN     => Some("SDL_CONTROLLER_BUTTON_DPAD_DOWN")
        case SDL_CONTROLLER_BUTTON_DPAD_LEFT     => Some("SDL_CONTROLLER_BUTTON_DPAD_LEFT")
        case SDL_CONTROLLER_BUTTON_DPAD_RIGHT    => Some("SDL_CONTROLLER_BUTTON_DPAD_RIGHT")
        case SDL_CONTROLLER_BUTTON_MISC1         => Some("SDL_CONTROLLER_BUTTON_MISC1")
        case SDL_CONTROLLER_BUTTON_PADDLE1       => Some("SDL_CONTROLLER_BUTTON_PADDLE1")
        case SDL_CONTROLLER_BUTTON_PADDLE2       => Some("SDL_CONTROLLER_BUTTON_PADDLE2")
        case SDL_CONTROLLER_BUTTON_PADDLE3       => Some("SDL_CONTROLLER_BUTTON_PADDLE3")
        case SDL_CONTROLLER_BUTTON_PADDLE4       => Some("SDL_CONTROLLER_BUTTON_PADDLE4")
        case SDL_CONTROLLER_BUTTON_TOUCHPAD      => Some("SDL_CONTROLLER_BUTTON_TOUCHPAD")
        case SDL_CONTROLLER_BUTTON_MAX           => Some("SDL_CONTROLLER_BUTTON_MAX")
        case _                                   => None
    extension (a: SDL_GameControllerButton)
      inline def &(b: SDL_GameControllerButton): SDL_GameControllerButton = a & b
      inline def |(b: SDL_GameControllerButton): SDL_GameControllerButton = a | b
      inline def is(b: SDL_GameControllerButton): Boolean                 = (a & b) == b

  /** [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameControllerType = CInt
  object SDL_GameControllerType extends CEnum[SDL_GameControllerType]:
    given _tag: Tag[SDL_GameControllerType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_GameControllerType = a
    val SDL_CONTROLLER_TYPE_UNKNOWN                           = define(0)
    val SDL_CONTROLLER_TYPE_XBOX360                           = define(1)
    val SDL_CONTROLLER_TYPE_XBOXONE                           = define(2)
    val SDL_CONTROLLER_TYPE_PS3                               = define(3)
    val SDL_CONTROLLER_TYPE_PS4                               = define(4)
    val SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_PRO               = define(5)
    val SDL_CONTROLLER_TYPE_VIRTUAL                           = define(6)
    val SDL_CONTROLLER_TYPE_PS5                               = define(7)
    val SDL_CONTROLLER_TYPE_AMAZON_LUNA                       = define(8)
    val SDL_CONTROLLER_TYPE_GOOGLE_STADIA                     = define(9)
    val SDL_CONTROLLER_TYPE_NVIDIA_SHIELD                     = define(10)
    val SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_LEFT       = define(11)
    val SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_RIGHT      = define(12)
    val SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_PAIR       = define(13)
    val SDL_CONTROLLER_TYPE_MAX                               = define(14)
    inline def getName(inline value: SDL_GameControllerType): Option[String] =
      inline value match
        case SDL_CONTROLLER_TYPE_UNKNOWN                     => Some("SDL_CONTROLLER_TYPE_UNKNOWN")
        case SDL_CONTROLLER_TYPE_XBOX360                     => Some("SDL_CONTROLLER_TYPE_XBOX360")
        case SDL_CONTROLLER_TYPE_XBOXONE                     => Some("SDL_CONTROLLER_TYPE_XBOXONE")
        case SDL_CONTROLLER_TYPE_PS3                         => Some("SDL_CONTROLLER_TYPE_PS3")
        case SDL_CONTROLLER_TYPE_PS4                         => Some("SDL_CONTROLLER_TYPE_PS4")
        case SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_PRO         => Some("SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_PRO")
        case SDL_CONTROLLER_TYPE_VIRTUAL                     => Some("SDL_CONTROLLER_TYPE_VIRTUAL")
        case SDL_CONTROLLER_TYPE_PS5                         => Some("SDL_CONTROLLER_TYPE_PS5")
        case SDL_CONTROLLER_TYPE_AMAZON_LUNA                 => Some("SDL_CONTROLLER_TYPE_AMAZON_LUNA")
        case SDL_CONTROLLER_TYPE_GOOGLE_STADIA               => Some("SDL_CONTROLLER_TYPE_GOOGLE_STADIA")
        case SDL_CONTROLLER_TYPE_NVIDIA_SHIELD               => Some("SDL_CONTROLLER_TYPE_NVIDIA_SHIELD")
        case SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_LEFT => Some("SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_LEFT")
        case SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_RIGHT =>
          Some("SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_RIGHT")
        case SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_PAIR => Some("SDL_CONTROLLER_TYPE_NINTENDO_SWITCH_JOYCON_PAIR")
        case SDL_CONTROLLER_TYPE_MAX                         => Some("SDL_CONTROLLER_TYPE_MAX")
        case _                                               => None
    extension (a: SDL_GameControllerType)
      inline def &(b: SDL_GameControllerType): SDL_GameControllerType = a & b
      inline def |(b: SDL_GameControllerType): SDL_GameControllerType = a | b
      inline def is(b: SDL_GameControllerType): Boolean               = (a & b) == b

  /** An enumeration of hint priorities
    *
    * [bindgen] header: ./SDL_hints.h
    */
  opaque type SDL_HintPriority = CInt
  object SDL_HintPriority extends CEnum[SDL_HintPriority]:
    given _tag: Tag[SDL_HintPriority]                   = Tag.Int
    inline def define(inline a: CInt): SDL_HintPriority = a
    val SDL_HINT_DEFAULT                                = define(0)
    val SDL_HINT_NORMAL                                 = define(1)
    val SDL_HINT_OVERRIDE                               = define(2)
    inline def getName(inline value: SDL_HintPriority): Option[String] =
      inline value match
        case SDL_HINT_DEFAULT  => Some("SDL_HINT_DEFAULT")
        case SDL_HINT_NORMAL   => Some("SDL_HINT_NORMAL")
        case SDL_HINT_OVERRIDE => Some("SDL_HINT_OVERRIDE")
        case _                 => None
    extension (a: SDL_HintPriority)
      inline def &(b: SDL_HintPriority): SDL_HintPriority = a & b
      inline def |(b: SDL_HintPriority): SDL_HintPriority = a | b
      inline def is(b: SDL_HintPriority): Boolean         = (a & b) == b

  /** Possible return values from the SDL_HitTest callback.
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_HitTestResult = CInt
  object SDL_HitTestResult extends CEnum[SDL_HitTestResult]:
    given _tag: Tag[SDL_HitTestResult]                   = Tag.Int
    inline def define(inline a: CInt): SDL_HitTestResult = a
    val SDL_HITTEST_NORMAL                               = define(0)
    val SDL_HITTEST_DRAGGABLE                            = define(1)
    val SDL_HITTEST_RESIZE_TOPLEFT                       = define(2)
    val SDL_HITTEST_RESIZE_TOP                           = define(3)
    val SDL_HITTEST_RESIZE_TOPRIGHT                      = define(4)
    val SDL_HITTEST_RESIZE_RIGHT                         = define(5)
    val SDL_HITTEST_RESIZE_BOTTOMRIGHT                   = define(6)
    val SDL_HITTEST_RESIZE_BOTTOM                        = define(7)
    val SDL_HITTEST_RESIZE_BOTTOMLEFT                    = define(8)
    val SDL_HITTEST_RESIZE_LEFT                          = define(9)
    inline def getName(inline value: SDL_HitTestResult): Option[String] =
      inline value match
        case SDL_HITTEST_NORMAL             => Some("SDL_HITTEST_NORMAL")
        case SDL_HITTEST_DRAGGABLE          => Some("SDL_HITTEST_DRAGGABLE")
        case SDL_HITTEST_RESIZE_TOPLEFT     => Some("SDL_HITTEST_RESIZE_TOPLEFT")
        case SDL_HITTEST_RESIZE_TOP         => Some("SDL_HITTEST_RESIZE_TOP")
        case SDL_HITTEST_RESIZE_TOPRIGHT    => Some("SDL_HITTEST_RESIZE_TOPRIGHT")
        case SDL_HITTEST_RESIZE_RIGHT       => Some("SDL_HITTEST_RESIZE_RIGHT")
        case SDL_HITTEST_RESIZE_BOTTOMRIGHT => Some("SDL_HITTEST_RESIZE_BOTTOMRIGHT")
        case SDL_HITTEST_RESIZE_BOTTOM      => Some("SDL_HITTEST_RESIZE_BOTTOM")
        case SDL_HITTEST_RESIZE_BOTTOMLEFT  => Some("SDL_HITTEST_RESIZE_BOTTOMLEFT")
        case SDL_HITTEST_RESIZE_LEFT        => Some("SDL_HITTEST_RESIZE_LEFT")
        case _                              => None
    extension (a: SDL_HitTestResult)
      inline def &(b: SDL_HitTestResult): SDL_HitTestResult = a & b
      inline def |(b: SDL_HitTestResult): SDL_HitTestResult = a | b
      inline def is(b: SDL_HitTestResult): Boolean          = (a & b) == b

  /**  These are the flags which may be passed to SDL_Init().  You should
    *  specify the subsystems which you will be using in your application.
    *
    * [bindgen] header: ./SDL.h [MANUAL]
    */
  opaque type SDL_InitFlag = UInt
  object SDL_InitFlag: // extends CEnum[SDL_InitFlag]:
    given _tag: Tag[SDL_InitFlag]                  = Tag.UInt
    inline def define(inline a: Int): SDL_InitFlag = a.toUInt
    val SDL_INIT_TIMER                             = define(0x00000001)
    val SDL_INIT_AUDIO                             = define(0x00000010)
    val SDL_INIT_VIDEO                             = define(0x00000020)
    val SDL_INIT_JOYSTICK                          = define(0x00000200)
    val SDL_INIT_HAPTIC                            = define(0x00001000)
    val SDL_INIT_GAMECONTROLLER                    = define(0x00002000)
    val SDL_INIT_EVENTS                            = define(0x00004000)
    val SDL_INIT_SENSOR                            = define(0x00008000)
    val SDL_INIT_NOPARACHUTE                       = define(0x00100000)
    inline def getName(inline value: SDL_InitFlag): Option[String] =
      inline value match
        case SDL_INIT_TIMER          => Some("SDL_INIT_TIMER")
        case SDL_INIT_AUDIO          => Some("SDL_INIT_AUDIO")
        case SDL_INIT_VIDEO          => Some("SDL_INIT_VIDEO")
        case SDL_INIT_JOYSTICK       => Some("SDL_INIT_JOYSTICK")
        case SDL_INIT_HAPTIC         => Some("SDL_INIT_HAPTIC")
        case SDL_INIT_GAMECONTROLLER => Some("SDL_INIT_GAMECONTROLLER")
        case SDL_INIT_EVENTS         => Some("SDL_INIT_EVENTS")
        case SDL_INIT_SENSOR         => Some("SDL_INIT_SENSOR")
        case SDL_INIT_NOPARACHUTE    => Some("SDL_INIT_NOPARACHUTE")
        case _                       => None
    extension (a: SDL_InitFlag)
      inline def &(b: SDL_InitFlag): SDL_InitFlag = a & b
      inline def |(b: SDL_InitFlag): SDL_InitFlag = a | b
      inline def is(b: SDL_InitFlag): Boolean     = (a & b) == b

  /** [bindgen] header: ./SDL_joystick.h
    */
  opaque type SDL_JoystickPowerLevel = CInt
  object SDL_JoystickPowerLevel extends CEnum[SDL_JoystickPowerLevel]:
    given _tag: Tag[SDL_JoystickPowerLevel]                   = Tag.Int
    inline def define(inline a: CInt): SDL_JoystickPowerLevel = a
    val SDL_JOYSTICK_POWER_UNKNOWN                            = define(-1)
    val SDL_JOYSTICK_POWER_EMPTY                              = define(0)
    val SDL_JOYSTICK_POWER_LOW                                = define(1)
    val SDL_JOYSTICK_POWER_MEDIUM                             = define(2)
    val SDL_JOYSTICK_POWER_FULL                               = define(3)
    val SDL_JOYSTICK_POWER_WIRED                              = define(4)
    val SDL_JOYSTICK_POWER_MAX                                = define(5)
    inline def getName(inline value: SDL_JoystickPowerLevel): Option[String] =
      inline value match
        case SDL_JOYSTICK_POWER_UNKNOWN => Some("SDL_JOYSTICK_POWER_UNKNOWN")
        case SDL_JOYSTICK_POWER_EMPTY   => Some("SDL_JOYSTICK_POWER_EMPTY")
        case SDL_JOYSTICK_POWER_LOW     => Some("SDL_JOYSTICK_POWER_LOW")
        case SDL_JOYSTICK_POWER_MEDIUM  => Some("SDL_JOYSTICK_POWER_MEDIUM")
        case SDL_JOYSTICK_POWER_FULL    => Some("SDL_JOYSTICK_POWER_FULL")
        case SDL_JOYSTICK_POWER_WIRED   => Some("SDL_JOYSTICK_POWER_WIRED")
        case SDL_JOYSTICK_POWER_MAX     => Some("SDL_JOYSTICK_POWER_MAX")
        case _                          => None
    extension (a: SDL_JoystickPowerLevel)
      inline def &(b: SDL_JoystickPowerLevel): SDL_JoystickPowerLevel = a & b
      inline def |(b: SDL_JoystickPowerLevel): SDL_JoystickPowerLevel = a | b
      inline def is(b: SDL_JoystickPowerLevel): Boolean               = (a & b) == b

  /** [bindgen] header: ./SDL_joystick.h
    */
  opaque type SDL_JoystickType = CInt
  object SDL_JoystickType extends CEnum[SDL_JoystickType]:
    given _tag: Tag[SDL_JoystickType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_JoystickType = a
    val SDL_JOYSTICK_TYPE_UNKNOWN                       = define(0)
    val SDL_JOYSTICK_TYPE_GAMECONTROLLER                = define(1)
    val SDL_JOYSTICK_TYPE_WHEEL                         = define(2)
    val SDL_JOYSTICK_TYPE_ARCADE_STICK                  = define(3)
    val SDL_JOYSTICK_TYPE_FLIGHT_STICK                  = define(4)
    val SDL_JOYSTICK_TYPE_DANCE_PAD                     = define(5)
    val SDL_JOYSTICK_TYPE_GUITAR                        = define(6)
    val SDL_JOYSTICK_TYPE_DRUM_KIT                      = define(7)
    val SDL_JOYSTICK_TYPE_ARCADE_PAD                    = define(8)
    val SDL_JOYSTICK_TYPE_THROTTLE                      = define(9)
    inline def getName(inline value: SDL_JoystickType): Option[String] =
      inline value match
        case SDL_JOYSTICK_TYPE_UNKNOWN        => Some("SDL_JOYSTICK_TYPE_UNKNOWN")
        case SDL_JOYSTICK_TYPE_GAMECONTROLLER => Some("SDL_JOYSTICK_TYPE_GAMECONTROLLER")
        case SDL_JOYSTICK_TYPE_WHEEL          => Some("SDL_JOYSTICK_TYPE_WHEEL")
        case SDL_JOYSTICK_TYPE_ARCADE_STICK   => Some("SDL_JOYSTICK_TYPE_ARCADE_STICK")
        case SDL_JOYSTICK_TYPE_FLIGHT_STICK   => Some("SDL_JOYSTICK_TYPE_FLIGHT_STICK")
        case SDL_JOYSTICK_TYPE_DANCE_PAD      => Some("SDL_JOYSTICK_TYPE_DANCE_PAD")
        case SDL_JOYSTICK_TYPE_GUITAR         => Some("SDL_JOYSTICK_TYPE_GUITAR")
        case SDL_JOYSTICK_TYPE_DRUM_KIT       => Some("SDL_JOYSTICK_TYPE_DRUM_KIT")
        case SDL_JOYSTICK_TYPE_ARCADE_PAD     => Some("SDL_JOYSTICK_TYPE_ARCADE_PAD")
        case SDL_JOYSTICK_TYPE_THROTTLE       => Some("SDL_JOYSTICK_TYPE_THROTTLE")
        case _                                => None
    extension (a: SDL_JoystickType)
      inline def &(b: SDL_JoystickType): SDL_JoystickType = a & b
      inline def |(b: SDL_JoystickType): SDL_JoystickType = a | b
      inline def is(b: SDL_JoystickType): Boolean         = (a & b) == b

  /** [bindgen] header: ./SDL_keycode.h
    */
  opaque type SDL_KeyCode = CInt
  object SDL_KeyCode extends CEnum[SDL_KeyCode]:
    given _tag: Tag[SDL_KeyCode]                   = Tag.Int
    inline def define(inline a: CInt): SDL_KeyCode = a
    val SDLK_UNKNOWN                               = define(0)
    val SDLK_RETURN                                = define(13)
    val SDLK_ESCAPE                                = define(27)
    val SDLK_BACKSPACE                             = define(8)
    val SDLK_TAB                                   = define(9)
    val SDLK_SPACE                                 = define(32)
    val SDLK_EXCLAIM                               = define(33)
    val SDLK_QUOTEDBL                              = define(34)
    val SDLK_HASH                                  = define(35)
    val SDLK_PERCENT                               = define(37)
    val SDLK_DOLLAR                                = define(36)
    val SDLK_AMPERSAND                             = define(38)
    val SDLK_QUOTE                                 = define(39)
    val SDLK_LEFTPAREN                             = define(40)
    val SDLK_RIGHTPAREN                            = define(41)
    val SDLK_ASTERISK                              = define(42)
    val SDLK_PLUS                                  = define(43)
    val SDLK_COMMA                                 = define(44)
    val SDLK_MINUS                                 = define(45)
    val SDLK_PERIOD                                = define(46)
    val SDLK_SLASH                                 = define(47)
    val SDLK_0                                     = define(48)
    val SDLK_1                                     = define(49)
    val SDLK_2                                     = define(50)
    val SDLK_3                                     = define(51)
    val SDLK_4                                     = define(52)
    val SDLK_5                                     = define(53)
    val SDLK_6                                     = define(54)
    val SDLK_7                                     = define(55)
    val SDLK_8                                     = define(56)
    val SDLK_9                                     = define(57)
    val SDLK_COLON                                 = define(58)
    val SDLK_SEMICOLON                             = define(59)
    val SDLK_LESS                                  = define(60)
    val SDLK_EQUALS                                = define(61)
    val SDLK_GREATER                               = define(62)
    val SDLK_QUESTION                              = define(63)
    val SDLK_AT                                    = define(64)
    val SDLK_LEFTBRACKET                           = define(91)
    val SDLK_BACKSLASH                             = define(92)
    val SDLK_RIGHTBRACKET                          = define(93)
    val SDLK_CARET                                 = define(94)
    val SDLK_UNDERSCORE                            = define(95)
    val SDLK_BACKQUOTE                             = define(96)
    val SDLK_a                                     = define(97)
    val SDLK_b                                     = define(98)
    val SDLK_c                                     = define(99)
    val SDLK_d                                     = define(100)
    val SDLK_e                                     = define(101)
    val SDLK_f                                     = define(102)
    val SDLK_g                                     = define(103)
    val SDLK_h                                     = define(104)
    val SDLK_i                                     = define(105)
    val SDLK_j                                     = define(106)
    val SDLK_k                                     = define(107)
    val SDLK_l                                     = define(108)
    val SDLK_m                                     = define(109)
    val SDLK_n                                     = define(110)
    val SDLK_o                                     = define(111)
    val SDLK_p                                     = define(112)
    val SDLK_q                                     = define(113)
    val SDLK_r                                     = define(114)
    val SDLK_s                                     = define(115)
    val SDLK_t                                     = define(116)
    val SDLK_u                                     = define(117)
    val SDLK_v                                     = define(118)
    val SDLK_w                                     = define(119)
    val SDLK_x                                     = define(120)
    val SDLK_y                                     = define(121)
    val SDLK_z                                     = define(122)
    val SDLK_CAPSLOCK                              = define(1073741881)
    val SDLK_F1                                    = define(1073741882)
    val SDLK_F2                                    = define(1073741883)
    val SDLK_F3                                    = define(1073741884)
    val SDLK_F4                                    = define(1073741885)
    val SDLK_F5                                    = define(1073741886)
    val SDLK_F6                                    = define(1073741887)
    val SDLK_F7                                    = define(1073741888)
    val SDLK_F8                                    = define(1073741889)
    val SDLK_F9                                    = define(1073741890)
    val SDLK_F10                                   = define(1073741891)
    val SDLK_F11                                   = define(1073741892)
    val SDLK_F12                                   = define(1073741893)
    val SDLK_PRINTSCREEN                           = define(1073741894)
    val SDLK_SCROLLLOCK                            = define(1073741895)
    val SDLK_PAUSE                                 = define(1073741896)
    val SDLK_INSERT                                = define(1073741897)
    val SDLK_HOME                                  = define(1073741898)
    val SDLK_PAGEUP                                = define(1073741899)
    val SDLK_DELETE                                = define(127)
    val SDLK_END                                   = define(1073741901)
    val SDLK_PAGEDOWN                              = define(1073741902)
    val SDLK_RIGHT                                 = define(1073741903)
    val SDLK_LEFT                                  = define(1073741904)
    val SDLK_DOWN                                  = define(1073741905)
    val SDLK_UP                                    = define(1073741906)
    val SDLK_NUMLOCKCLEAR                          = define(1073741907)
    val SDLK_KP_DIVIDE                             = define(1073741908)
    val SDLK_KP_MULTIPLY                           = define(1073741909)
    val SDLK_KP_MINUS                              = define(1073741910)
    val SDLK_KP_PLUS                               = define(1073741911)
    val SDLK_KP_ENTER                              = define(1073741912)
    val SDLK_KP_1                                  = define(1073741913)
    val SDLK_KP_2                                  = define(1073741914)
    val SDLK_KP_3                                  = define(1073741915)
    val SDLK_KP_4                                  = define(1073741916)
    val SDLK_KP_5                                  = define(1073741917)
    val SDLK_KP_6                                  = define(1073741918)
    val SDLK_KP_7                                  = define(1073741919)
    val SDLK_KP_8                                  = define(1073741920)
    val SDLK_KP_9                                  = define(1073741921)
    val SDLK_KP_0                                  = define(1073741922)
    val SDLK_KP_PERIOD                             = define(1073741923)
    val SDLK_APPLICATION                           = define(1073741925)
    val SDLK_POWER                                 = define(1073741926)
    val SDLK_KP_EQUALS                             = define(1073741927)
    val SDLK_F13                                   = define(1073741928)
    val SDLK_F14                                   = define(1073741929)
    val SDLK_F15                                   = define(1073741930)
    val SDLK_F16                                   = define(1073741931)
    val SDLK_F17                                   = define(1073741932)
    val SDLK_F18                                   = define(1073741933)
    val SDLK_F19                                   = define(1073741934)
    val SDLK_F20                                   = define(1073741935)
    val SDLK_F21                                   = define(1073741936)
    val SDLK_F22                                   = define(1073741937)
    val SDLK_F23                                   = define(1073741938)
    val SDLK_F24                                   = define(1073741939)
    val SDLK_EXECUTE                               = define(1073741940)
    val SDLK_HELP                                  = define(1073741941)
    val SDLK_MENU                                  = define(1073741942)
    val SDLK_SELECT                                = define(1073741943)
    val SDLK_STOP                                  = define(1073741944)
    val SDLK_AGAIN                                 = define(1073741945)
    val SDLK_UNDO                                  = define(1073741946)
    val SDLK_CUT                                   = define(1073741947)
    val SDLK_COPY                                  = define(1073741948)
    val SDLK_PASTE                                 = define(1073741949)
    val SDLK_FIND                                  = define(1073741950)
    val SDLK_MUTE                                  = define(1073741951)
    val SDLK_VOLUMEUP                              = define(1073741952)
    val SDLK_VOLUMEDOWN                            = define(1073741953)
    val SDLK_KP_COMMA                              = define(1073741957)
    val SDLK_KP_EQUALSAS400                        = define(1073741958)
    val SDLK_ALTERASE                              = define(1073741977)
    val SDLK_SYSREQ                                = define(1073741978)
    val SDLK_CANCEL                                = define(1073741979)
    val SDLK_CLEAR                                 = define(1073741980)
    val SDLK_PRIOR                                 = define(1073741981)
    val SDLK_RETURN2                               = define(1073741982)
    val SDLK_SEPARATOR                             = define(1073741983)
    val SDLK_OUT                                   = define(1073741984)
    val SDLK_OPER                                  = define(1073741985)
    val SDLK_CLEARAGAIN                            = define(1073741986)
    val SDLK_CRSEL                                 = define(1073741987)
    val SDLK_EXSEL                                 = define(1073741988)
    val SDLK_KP_00                                 = define(1073742000)
    val SDLK_KP_000                                = define(1073742001)
    val SDLK_THOUSANDSSEPARATOR                    = define(1073742002)
    val SDLK_DECIMALSEPARATOR                      = define(1073742003)
    val SDLK_CURRENCYUNIT                          = define(1073742004)
    val SDLK_CURRENCYSUBUNIT                       = define(1073742005)
    val SDLK_KP_LEFTPAREN                          = define(1073742006)
    val SDLK_KP_RIGHTPAREN                         = define(1073742007)
    val SDLK_KP_LEFTBRACE                          = define(1073742008)
    val SDLK_KP_RIGHTBRACE                         = define(1073742009)
    val SDLK_KP_TAB                                = define(1073742010)
    val SDLK_KP_BACKSPACE                          = define(1073742011)
    val SDLK_KP_A                                  = define(1073742012)
    val SDLK_KP_B                                  = define(1073742013)
    val SDLK_KP_C                                  = define(1073742014)
    val SDLK_KP_D                                  = define(1073742015)
    val SDLK_KP_E                                  = define(1073742016)
    val SDLK_KP_F                                  = define(1073742017)
    val SDLK_KP_XOR                                = define(1073742018)
    val SDLK_KP_POWER                              = define(1073742019)
    val SDLK_KP_PERCENT                            = define(1073742020)
    val SDLK_KP_LESS                               = define(1073742021)
    val SDLK_KP_GREATER                            = define(1073742022)
    val SDLK_KP_AMPERSAND                          = define(1073742023)
    val SDLK_KP_DBLAMPERSAND                       = define(1073742024)
    val SDLK_KP_VERTICALBAR                        = define(1073742025)
    val SDLK_KP_DBLVERTICALBAR                     = define(1073742026)
    val SDLK_KP_COLON                              = define(1073742027)
    val SDLK_KP_HASH                               = define(1073742028)
    val SDLK_KP_SPACE                              = define(1073742029)
    val SDLK_KP_AT                                 = define(1073742030)
    val SDLK_KP_EXCLAM                             = define(1073742031)
    val SDLK_KP_MEMSTORE                           = define(1073742032)
    val SDLK_KP_MEMRECALL                          = define(1073742033)
    val SDLK_KP_MEMCLEAR                           = define(1073742034)
    val SDLK_KP_MEMADD                             = define(1073742035)
    val SDLK_KP_MEMSUBTRACT                        = define(1073742036)
    val SDLK_KP_MEMMULTIPLY                        = define(1073742037)
    val SDLK_KP_MEMDIVIDE                          = define(1073742038)
    val SDLK_KP_PLUSMINUS                          = define(1073742039)
    val SDLK_KP_CLEAR                              = define(1073742040)
    val SDLK_KP_CLEARENTRY                         = define(1073742041)
    val SDLK_KP_BINARY                             = define(1073742042)
    val SDLK_KP_OCTAL                              = define(1073742043)
    val SDLK_KP_DECIMAL                            = define(1073742044)
    val SDLK_KP_HEXADECIMAL                        = define(1073742045)
    val SDLK_LCTRL                                 = define(1073742048)
    val SDLK_LSHIFT                                = define(1073742049)
    val SDLK_LALT                                  = define(1073742050)
    val SDLK_LGUI                                  = define(1073742051)
    val SDLK_RCTRL                                 = define(1073742052)
    val SDLK_RSHIFT                                = define(1073742053)
    val SDLK_RALT                                  = define(1073742054)
    val SDLK_RGUI                                  = define(1073742055)
    val SDLK_MODE                                  = define(1073742081)
    val SDLK_AUDIONEXT                             = define(1073742082)
    val SDLK_AUDIOPREV                             = define(1073742083)
    val SDLK_AUDIOSTOP                             = define(1073742084)
    val SDLK_AUDIOPLAY                             = define(1073742085)
    val SDLK_AUDIOMUTE                             = define(1073742086)
    val SDLK_MEDIASELECT                           = define(1073742087)
    val SDLK_WWW                                   = define(1073742088)
    val SDLK_MAIL                                  = define(1073742089)
    val SDLK_CALCULATOR                            = define(1073742090)
    val SDLK_COMPUTER                              = define(1073742091)
    val SDLK_AC_SEARCH                             = define(1073742092)
    val SDLK_AC_HOME                               = define(1073742093)
    val SDLK_AC_BACK                               = define(1073742094)
    val SDLK_AC_FORWARD                            = define(1073742095)
    val SDLK_AC_STOP                               = define(1073742096)
    val SDLK_AC_REFRESH                            = define(1073742097)
    val SDLK_AC_BOOKMARKS                          = define(1073742098)
    val SDLK_BRIGHTNESSDOWN                        = define(1073742099)
    val SDLK_BRIGHTNESSUP                          = define(1073742100)
    val SDLK_DISPLAYSWITCH                         = define(1073742101)
    val SDLK_KBDILLUMTOGGLE                        = define(1073742102)
    val SDLK_KBDILLUMDOWN                          = define(1073742103)
    val SDLK_KBDILLUMUP                            = define(1073742104)
    val SDLK_EJECT                                 = define(1073742105)
    val SDLK_SLEEP                                 = define(1073742106)
    val SDLK_APP1                                  = define(1073742107)
    val SDLK_APP2                                  = define(1073742108)
    val SDLK_AUDIOREWIND                           = define(1073742109)
    val SDLK_AUDIOFASTFORWARD                      = define(1073742110)
    val SDLK_SOFTLEFT                              = define(1073742111)
    val SDLK_SOFTRIGHT                             = define(1073742112)
    val SDLK_CALL                                  = define(1073742113)
    val SDLK_ENDCALL                               = define(1073742114)
    inline def getName(inline value: SDL_KeyCode): Option[String] =
      inline value match
        case SDLK_UNKNOWN            => Some("SDLK_UNKNOWN")
        case SDLK_RETURN             => Some("SDLK_RETURN")
        case SDLK_ESCAPE             => Some("SDLK_ESCAPE")
        case SDLK_BACKSPACE          => Some("SDLK_BACKSPACE")
        case SDLK_TAB                => Some("SDLK_TAB")
        case SDLK_SPACE              => Some("SDLK_SPACE")
        case SDLK_EXCLAIM            => Some("SDLK_EXCLAIM")
        case SDLK_QUOTEDBL           => Some("SDLK_QUOTEDBL")
        case SDLK_HASH               => Some("SDLK_HASH")
        case SDLK_PERCENT            => Some("SDLK_PERCENT")
        case SDLK_DOLLAR             => Some("SDLK_DOLLAR")
        case SDLK_AMPERSAND          => Some("SDLK_AMPERSAND")
        case SDLK_QUOTE              => Some("SDLK_QUOTE")
        case SDLK_LEFTPAREN          => Some("SDLK_LEFTPAREN")
        case SDLK_RIGHTPAREN         => Some("SDLK_RIGHTPAREN")
        case SDLK_ASTERISK           => Some("SDLK_ASTERISK")
        case SDLK_PLUS               => Some("SDLK_PLUS")
        case SDLK_COMMA              => Some("SDLK_COMMA")
        case SDLK_MINUS              => Some("SDLK_MINUS")
        case SDLK_PERIOD             => Some("SDLK_PERIOD")
        case SDLK_SLASH              => Some("SDLK_SLASH")
        case SDLK_0                  => Some("SDLK_0")
        case SDLK_1                  => Some("SDLK_1")
        case SDLK_2                  => Some("SDLK_2")
        case SDLK_3                  => Some("SDLK_3")
        case SDLK_4                  => Some("SDLK_4")
        case SDLK_5                  => Some("SDLK_5")
        case SDLK_6                  => Some("SDLK_6")
        case SDLK_7                  => Some("SDLK_7")
        case SDLK_8                  => Some("SDLK_8")
        case SDLK_9                  => Some("SDLK_9")
        case SDLK_COLON              => Some("SDLK_COLON")
        case SDLK_SEMICOLON          => Some("SDLK_SEMICOLON")
        case SDLK_LESS               => Some("SDLK_LESS")
        case SDLK_EQUALS             => Some("SDLK_EQUALS")
        case SDLK_GREATER            => Some("SDLK_GREATER")
        case SDLK_QUESTION           => Some("SDLK_QUESTION")
        case SDLK_AT                 => Some("SDLK_AT")
        case SDLK_LEFTBRACKET        => Some("SDLK_LEFTBRACKET")
        case SDLK_BACKSLASH          => Some("SDLK_BACKSLASH")
        case SDLK_RIGHTBRACKET       => Some("SDLK_RIGHTBRACKET")
        case SDLK_CARET              => Some("SDLK_CARET")
        case SDLK_UNDERSCORE         => Some("SDLK_UNDERSCORE")
        case SDLK_BACKQUOTE          => Some("SDLK_BACKQUOTE")
        case SDLK_a                  => Some("SDLK_a")
        case SDLK_b                  => Some("SDLK_b")
        case SDLK_c                  => Some("SDLK_c")
        case SDLK_d                  => Some("SDLK_d")
        case SDLK_e                  => Some("SDLK_e")
        case SDLK_f                  => Some("SDLK_f")
        case SDLK_g                  => Some("SDLK_g")
        case SDLK_h                  => Some("SDLK_h")
        case SDLK_i                  => Some("SDLK_i")
        case SDLK_j                  => Some("SDLK_j")
        case SDLK_k                  => Some("SDLK_k")
        case SDLK_l                  => Some("SDLK_l")
        case SDLK_m                  => Some("SDLK_m")
        case SDLK_n                  => Some("SDLK_n")
        case SDLK_o                  => Some("SDLK_o")
        case SDLK_p                  => Some("SDLK_p")
        case SDLK_q                  => Some("SDLK_q")
        case SDLK_r                  => Some("SDLK_r")
        case SDLK_s                  => Some("SDLK_s")
        case SDLK_t                  => Some("SDLK_t")
        case SDLK_u                  => Some("SDLK_u")
        case SDLK_v                  => Some("SDLK_v")
        case SDLK_w                  => Some("SDLK_w")
        case SDLK_x                  => Some("SDLK_x")
        case SDLK_y                  => Some("SDLK_y")
        case SDLK_z                  => Some("SDLK_z")
        case SDLK_CAPSLOCK           => Some("SDLK_CAPSLOCK")
        case SDLK_F1                 => Some("SDLK_F1")
        case SDLK_F2                 => Some("SDLK_F2")
        case SDLK_F3                 => Some("SDLK_F3")
        case SDLK_F4                 => Some("SDLK_F4")
        case SDLK_F5                 => Some("SDLK_F5")
        case SDLK_F6                 => Some("SDLK_F6")
        case SDLK_F7                 => Some("SDLK_F7")
        case SDLK_F8                 => Some("SDLK_F8")
        case SDLK_F9                 => Some("SDLK_F9")
        case SDLK_F10                => Some("SDLK_F10")
        case SDLK_F11                => Some("SDLK_F11")
        case SDLK_F12                => Some("SDLK_F12")
        case SDLK_PRINTSCREEN        => Some("SDLK_PRINTSCREEN")
        case SDLK_SCROLLLOCK         => Some("SDLK_SCROLLLOCK")
        case SDLK_PAUSE              => Some("SDLK_PAUSE")
        case SDLK_INSERT             => Some("SDLK_INSERT")
        case SDLK_HOME               => Some("SDLK_HOME")
        case SDLK_PAGEUP             => Some("SDLK_PAGEUP")
        case SDLK_DELETE             => Some("SDLK_DELETE")
        case SDLK_END                => Some("SDLK_END")
        case SDLK_PAGEDOWN           => Some("SDLK_PAGEDOWN")
        case SDLK_RIGHT              => Some("SDLK_RIGHT")
        case SDLK_LEFT               => Some("SDLK_LEFT")
        case SDLK_DOWN               => Some("SDLK_DOWN")
        case SDLK_UP                 => Some("SDLK_UP")
        case SDLK_NUMLOCKCLEAR       => Some("SDLK_NUMLOCKCLEAR")
        case SDLK_KP_DIVIDE          => Some("SDLK_KP_DIVIDE")
        case SDLK_KP_MULTIPLY        => Some("SDLK_KP_MULTIPLY")
        case SDLK_KP_MINUS           => Some("SDLK_KP_MINUS")
        case SDLK_KP_PLUS            => Some("SDLK_KP_PLUS")
        case SDLK_KP_ENTER           => Some("SDLK_KP_ENTER")
        case SDLK_KP_1               => Some("SDLK_KP_1")
        case SDLK_KP_2               => Some("SDLK_KP_2")
        case SDLK_KP_3               => Some("SDLK_KP_3")
        case SDLK_KP_4               => Some("SDLK_KP_4")
        case SDLK_KP_5               => Some("SDLK_KP_5")
        case SDLK_KP_6               => Some("SDLK_KP_6")
        case SDLK_KP_7               => Some("SDLK_KP_7")
        case SDLK_KP_8               => Some("SDLK_KP_8")
        case SDLK_KP_9               => Some("SDLK_KP_9")
        case SDLK_KP_0               => Some("SDLK_KP_0")
        case SDLK_KP_PERIOD          => Some("SDLK_KP_PERIOD")
        case SDLK_APPLICATION        => Some("SDLK_APPLICATION")
        case SDLK_POWER              => Some("SDLK_POWER")
        case SDLK_KP_EQUALS          => Some("SDLK_KP_EQUALS")
        case SDLK_F13                => Some("SDLK_F13")
        case SDLK_F14                => Some("SDLK_F14")
        case SDLK_F15                => Some("SDLK_F15")
        case SDLK_F16                => Some("SDLK_F16")
        case SDLK_F17                => Some("SDLK_F17")
        case SDLK_F18                => Some("SDLK_F18")
        case SDLK_F19                => Some("SDLK_F19")
        case SDLK_F20                => Some("SDLK_F20")
        case SDLK_F21                => Some("SDLK_F21")
        case SDLK_F22                => Some("SDLK_F22")
        case SDLK_F23                => Some("SDLK_F23")
        case SDLK_F24                => Some("SDLK_F24")
        case SDLK_EXECUTE            => Some("SDLK_EXECUTE")
        case SDLK_HELP               => Some("SDLK_HELP")
        case SDLK_MENU               => Some("SDLK_MENU")
        case SDLK_SELECT             => Some("SDLK_SELECT")
        case SDLK_STOP               => Some("SDLK_STOP")
        case SDLK_AGAIN              => Some("SDLK_AGAIN")
        case SDLK_UNDO               => Some("SDLK_UNDO")
        case SDLK_CUT                => Some("SDLK_CUT")
        case SDLK_COPY               => Some("SDLK_COPY")
        case SDLK_PASTE              => Some("SDLK_PASTE")
        case SDLK_FIND               => Some("SDLK_FIND")
        case SDLK_MUTE               => Some("SDLK_MUTE")
        case SDLK_VOLUMEUP           => Some("SDLK_VOLUMEUP")
        case SDLK_VOLUMEDOWN         => Some("SDLK_VOLUMEDOWN")
        case SDLK_KP_COMMA           => Some("SDLK_KP_COMMA")
        case SDLK_KP_EQUALSAS400     => Some("SDLK_KP_EQUALSAS400")
        case SDLK_ALTERASE           => Some("SDLK_ALTERASE")
        case SDLK_SYSREQ             => Some("SDLK_SYSREQ")
        case SDLK_CANCEL             => Some("SDLK_CANCEL")
        case SDLK_CLEAR              => Some("SDLK_CLEAR")
        case SDLK_PRIOR              => Some("SDLK_PRIOR")
        case SDLK_RETURN2            => Some("SDLK_RETURN2")
        case SDLK_SEPARATOR          => Some("SDLK_SEPARATOR")
        case SDLK_OUT                => Some("SDLK_OUT")
        case SDLK_OPER               => Some("SDLK_OPER")
        case SDLK_CLEARAGAIN         => Some("SDLK_CLEARAGAIN")
        case SDLK_CRSEL              => Some("SDLK_CRSEL")
        case SDLK_EXSEL              => Some("SDLK_EXSEL")
        case SDLK_KP_00              => Some("SDLK_KP_00")
        case SDLK_KP_000             => Some("SDLK_KP_000")
        case SDLK_THOUSANDSSEPARATOR => Some("SDLK_THOUSANDSSEPARATOR")
        case SDLK_DECIMALSEPARATOR   => Some("SDLK_DECIMALSEPARATOR")
        case SDLK_CURRENCYUNIT       => Some("SDLK_CURRENCYUNIT")
        case SDLK_CURRENCYSUBUNIT    => Some("SDLK_CURRENCYSUBUNIT")
        case SDLK_KP_LEFTPAREN       => Some("SDLK_KP_LEFTPAREN")
        case SDLK_KP_RIGHTPAREN      => Some("SDLK_KP_RIGHTPAREN")
        case SDLK_KP_LEFTBRACE       => Some("SDLK_KP_LEFTBRACE")
        case SDLK_KP_RIGHTBRACE      => Some("SDLK_KP_RIGHTBRACE")
        case SDLK_KP_TAB             => Some("SDLK_KP_TAB")
        case SDLK_KP_BACKSPACE       => Some("SDLK_KP_BACKSPACE")
        case SDLK_KP_A               => Some("SDLK_KP_A")
        case SDLK_KP_B               => Some("SDLK_KP_B")
        case SDLK_KP_C               => Some("SDLK_KP_C")
        case SDLK_KP_D               => Some("SDLK_KP_D")
        case SDLK_KP_E               => Some("SDLK_KP_E")
        case SDLK_KP_F               => Some("SDLK_KP_F")
        case SDLK_KP_XOR             => Some("SDLK_KP_XOR")
        case SDLK_KP_POWER           => Some("SDLK_KP_POWER")
        case SDLK_KP_PERCENT         => Some("SDLK_KP_PERCENT")
        case SDLK_KP_LESS            => Some("SDLK_KP_LESS")
        case SDLK_KP_GREATER         => Some("SDLK_KP_GREATER")
        case SDLK_KP_AMPERSAND       => Some("SDLK_KP_AMPERSAND")
        case SDLK_KP_DBLAMPERSAND    => Some("SDLK_KP_DBLAMPERSAND")
        case SDLK_KP_VERTICALBAR     => Some("SDLK_KP_VERTICALBAR")
        case SDLK_KP_DBLVERTICALBAR  => Some("SDLK_KP_DBLVERTICALBAR")
        case SDLK_KP_COLON           => Some("SDLK_KP_COLON")
        case SDLK_KP_HASH            => Some("SDLK_KP_HASH")
        case SDLK_KP_SPACE           => Some("SDLK_KP_SPACE")
        case SDLK_KP_AT              => Some("SDLK_KP_AT")
        case SDLK_KP_EXCLAM          => Some("SDLK_KP_EXCLAM")
        case SDLK_KP_MEMSTORE        => Some("SDLK_KP_MEMSTORE")
        case SDLK_KP_MEMRECALL       => Some("SDLK_KP_MEMRECALL")
        case SDLK_KP_MEMCLEAR        => Some("SDLK_KP_MEMCLEAR")
        case SDLK_KP_MEMADD          => Some("SDLK_KP_MEMADD")
        case SDLK_KP_MEMSUBTRACT     => Some("SDLK_KP_MEMSUBTRACT")
        case SDLK_KP_MEMMULTIPLY     => Some("SDLK_KP_MEMMULTIPLY")
        case SDLK_KP_MEMDIVIDE       => Some("SDLK_KP_MEMDIVIDE")
        case SDLK_KP_PLUSMINUS       => Some("SDLK_KP_PLUSMINUS")
        case SDLK_KP_CLEAR           => Some("SDLK_KP_CLEAR")
        case SDLK_KP_CLEARENTRY      => Some("SDLK_KP_CLEARENTRY")
        case SDLK_KP_BINARY          => Some("SDLK_KP_BINARY")
        case SDLK_KP_OCTAL           => Some("SDLK_KP_OCTAL")
        case SDLK_KP_DECIMAL         => Some("SDLK_KP_DECIMAL")
        case SDLK_KP_HEXADECIMAL     => Some("SDLK_KP_HEXADECIMAL")
        case SDLK_LCTRL              => Some("SDLK_LCTRL")
        case SDLK_LSHIFT             => Some("SDLK_LSHIFT")
        case SDLK_LALT               => Some("SDLK_LALT")
        case SDLK_LGUI               => Some("SDLK_LGUI")
        case SDLK_RCTRL              => Some("SDLK_RCTRL")
        case SDLK_RSHIFT             => Some("SDLK_RSHIFT")
        case SDLK_RALT               => Some("SDLK_RALT")
        case SDLK_RGUI               => Some("SDLK_RGUI")
        case SDLK_MODE               => Some("SDLK_MODE")
        case SDLK_AUDIONEXT          => Some("SDLK_AUDIONEXT")
        case SDLK_AUDIOPREV          => Some("SDLK_AUDIOPREV")
        case SDLK_AUDIOSTOP          => Some("SDLK_AUDIOSTOP")
        case SDLK_AUDIOPLAY          => Some("SDLK_AUDIOPLAY")
        case SDLK_AUDIOMUTE          => Some("SDLK_AUDIOMUTE")
        case SDLK_MEDIASELECT        => Some("SDLK_MEDIASELECT")
        case SDLK_WWW                => Some("SDLK_WWW")
        case SDLK_MAIL               => Some("SDLK_MAIL")
        case SDLK_CALCULATOR         => Some("SDLK_CALCULATOR")
        case SDLK_COMPUTER           => Some("SDLK_COMPUTER")
        case SDLK_AC_SEARCH          => Some("SDLK_AC_SEARCH")
        case SDLK_AC_HOME            => Some("SDLK_AC_HOME")
        case SDLK_AC_BACK            => Some("SDLK_AC_BACK")
        case SDLK_AC_FORWARD         => Some("SDLK_AC_FORWARD")
        case SDLK_AC_STOP            => Some("SDLK_AC_STOP")
        case SDLK_AC_REFRESH         => Some("SDLK_AC_REFRESH")
        case SDLK_AC_BOOKMARKS       => Some("SDLK_AC_BOOKMARKS")
        case SDLK_BRIGHTNESSDOWN     => Some("SDLK_BRIGHTNESSDOWN")
        case SDLK_BRIGHTNESSUP       => Some("SDLK_BRIGHTNESSUP")
        case SDLK_DISPLAYSWITCH      => Some("SDLK_DISPLAYSWITCH")
        case SDLK_KBDILLUMTOGGLE     => Some("SDLK_KBDILLUMTOGGLE")
        case SDLK_KBDILLUMDOWN       => Some("SDLK_KBDILLUMDOWN")
        case SDLK_KBDILLUMUP         => Some("SDLK_KBDILLUMUP")
        case SDLK_EJECT              => Some("SDLK_EJECT")
        case SDLK_SLEEP              => Some("SDLK_SLEEP")
        case SDLK_APP1               => Some("SDLK_APP1")
        case SDLK_APP2               => Some("SDLK_APP2")
        case SDLK_AUDIOREWIND        => Some("SDLK_AUDIOREWIND")
        case SDLK_AUDIOFASTFORWARD   => Some("SDLK_AUDIOFASTFORWARD")
        case SDLK_SOFTLEFT           => Some("SDLK_SOFTLEFT")
        case SDLK_SOFTRIGHT          => Some("SDLK_SOFTRIGHT")
        case SDLK_CALL               => Some("SDLK_CALL")
        case SDLK_ENDCALL            => Some("SDLK_ENDCALL")
        case _                       => None
    extension (a: SDL_KeyCode)
      inline def &(b: SDL_KeyCode): SDL_KeyCode = a & b
      inline def |(b: SDL_KeyCode): SDL_KeyCode = a | b
      inline def is(b: SDL_KeyCode): Boolean    = (a & b) == b

  /** Enumeration of valid key mods (possibly OR'd together).
    *
    * [bindgen] header: ./SDL_keycode.h
    */
  opaque type SDL_Keymod = CInt
  object SDL_Keymod extends CEnum[SDL_Keymod]:
    given _tag: Tag[SDL_Keymod]                   = Tag.Int
    inline def define(inline a: CInt): SDL_Keymod = a
    val KMOD_NONE                                 = define(0)
    val KMOD_LSHIFT                               = define(1)
    val KMOD_RSHIFT                               = define(2)
    val KMOD_LCTRL                                = define(64)
    val KMOD_RCTRL                                = define(128)
    val KMOD_LALT                                 = define(256)
    val KMOD_RALT                                 = define(512)
    val KMOD_LGUI                                 = define(1024)
    val KMOD_RGUI                                 = define(2048)
    val KMOD_NUM                                  = define(4096)
    val KMOD_CAPS                                 = define(8192)
    val KMOD_MODE                                 = define(16384)
    val KMOD_SCROLL                               = define(32768)
    val KMOD_CTRL                                 = define(192)
    val KMOD_SHIFT                                = define(3)
    val KMOD_ALT                                  = define(768)
    val KMOD_GUI                                  = define(3072)
    val KMOD_RESERVED                             = define(32768)
    inline def getName(inline value: SDL_Keymod): Option[String] =
      inline value match
        case KMOD_NONE     => Some("KMOD_NONE")
        case KMOD_LSHIFT   => Some("KMOD_LSHIFT")
        case KMOD_RSHIFT   => Some("KMOD_RSHIFT")
        case KMOD_LCTRL    => Some("KMOD_LCTRL")
        case KMOD_RCTRL    => Some("KMOD_RCTRL")
        case KMOD_LALT     => Some("KMOD_LALT")
        case KMOD_RALT     => Some("KMOD_RALT")
        case KMOD_LGUI     => Some("KMOD_LGUI")
        case KMOD_RGUI     => Some("KMOD_RGUI")
        case KMOD_NUM      => Some("KMOD_NUM")
        case KMOD_CAPS     => Some("KMOD_CAPS")
        case KMOD_MODE     => Some("KMOD_MODE")
        case KMOD_SCROLL   => Some("KMOD_SCROLL")
        case KMOD_CTRL     => Some("KMOD_CTRL")
        case KMOD_SHIFT    => Some("KMOD_SHIFT")
        case KMOD_ALT      => Some("KMOD_ALT")
        case KMOD_GUI      => Some("KMOD_GUI")
        case KMOD_RESERVED => Some("KMOD_RESERVED")
        case _             => None
    extension (a: SDL_Keymod)
      inline def &(b: SDL_Keymod): SDL_Keymod = a & b
      inline def |(b: SDL_Keymod): SDL_Keymod = a | b
      inline def is(b: SDL_Keymod): Boolean   = (a & b) == b

  /** The predefined log categories
    *
    * [bindgen] header: ./SDL_log.h
    */
  opaque type SDL_LogCategory = CInt
  object SDL_LogCategory extends CEnum[SDL_LogCategory]:
    given _tag: Tag[SDL_LogCategory]                   = Tag.Int
    inline def define(inline a: CInt): SDL_LogCategory = a
    val SDL_LOG_CATEGORY_APPLICATION                   = define(0)
    val SDL_LOG_CATEGORY_ERROR                         = define(1)
    val SDL_LOG_CATEGORY_ASSERT                        = define(2)
    val SDL_LOG_CATEGORY_SYSTEM                        = define(3)
    val SDL_LOG_CATEGORY_AUDIO                         = define(4)
    val SDL_LOG_CATEGORY_VIDEO                         = define(5)
    val SDL_LOG_CATEGORY_RENDER                        = define(6)
    val SDL_LOG_CATEGORY_INPUT                         = define(7)
    val SDL_LOG_CATEGORY_TEST                          = define(8)
    val SDL_LOG_CATEGORY_RESERVED1                     = define(9)
    val SDL_LOG_CATEGORY_RESERVED2                     = define(10)
    val SDL_LOG_CATEGORY_RESERVED3                     = define(11)
    val SDL_LOG_CATEGORY_RESERVED4                     = define(12)
    val SDL_LOG_CATEGORY_RESERVED5                     = define(13)
    val SDL_LOG_CATEGORY_RESERVED6                     = define(14)
    val SDL_LOG_CATEGORY_RESERVED7                     = define(15)
    val SDL_LOG_CATEGORY_RESERVED8                     = define(16)
    val SDL_LOG_CATEGORY_RESERVED9                     = define(17)
    val SDL_LOG_CATEGORY_RESERVED10                    = define(18)
    val SDL_LOG_CATEGORY_CUSTOM                        = define(19)
    inline def getName(inline value: SDL_LogCategory): Option[String] =
      inline value match
        case SDL_LOG_CATEGORY_APPLICATION => Some("SDL_LOG_CATEGORY_APPLICATION")
        case SDL_LOG_CATEGORY_ERROR       => Some("SDL_LOG_CATEGORY_ERROR")
        case SDL_LOG_CATEGORY_ASSERT      => Some("SDL_LOG_CATEGORY_ASSERT")
        case SDL_LOG_CATEGORY_SYSTEM      => Some("SDL_LOG_CATEGORY_SYSTEM")
        case SDL_LOG_CATEGORY_AUDIO       => Some("SDL_LOG_CATEGORY_AUDIO")
        case SDL_LOG_CATEGORY_VIDEO       => Some("SDL_LOG_CATEGORY_VIDEO")
        case SDL_LOG_CATEGORY_RENDER      => Some("SDL_LOG_CATEGORY_RENDER")
        case SDL_LOG_CATEGORY_INPUT       => Some("SDL_LOG_CATEGORY_INPUT")
        case SDL_LOG_CATEGORY_TEST        => Some("SDL_LOG_CATEGORY_TEST")
        case SDL_LOG_CATEGORY_RESERVED1   => Some("SDL_LOG_CATEGORY_RESERVED1")
        case SDL_LOG_CATEGORY_RESERVED2   => Some("SDL_LOG_CATEGORY_RESERVED2")
        case SDL_LOG_CATEGORY_RESERVED3   => Some("SDL_LOG_CATEGORY_RESERVED3")
        case SDL_LOG_CATEGORY_RESERVED4   => Some("SDL_LOG_CATEGORY_RESERVED4")
        case SDL_LOG_CATEGORY_RESERVED5   => Some("SDL_LOG_CATEGORY_RESERVED5")
        case SDL_LOG_CATEGORY_RESERVED6   => Some("SDL_LOG_CATEGORY_RESERVED6")
        case SDL_LOG_CATEGORY_RESERVED7   => Some("SDL_LOG_CATEGORY_RESERVED7")
        case SDL_LOG_CATEGORY_RESERVED8   => Some("SDL_LOG_CATEGORY_RESERVED8")
        case SDL_LOG_CATEGORY_RESERVED9   => Some("SDL_LOG_CATEGORY_RESERVED9")
        case SDL_LOG_CATEGORY_RESERVED10  => Some("SDL_LOG_CATEGORY_RESERVED10")
        case SDL_LOG_CATEGORY_CUSTOM      => Some("SDL_LOG_CATEGORY_CUSTOM")
        case _                            => None
    extension (a: SDL_LogCategory)
      inline def &(b: SDL_LogCategory): SDL_LogCategory = a & b
      inline def |(b: SDL_LogCategory): SDL_LogCategory = a | b
      inline def is(b: SDL_LogCategory): Boolean        = (a & b) == b

  /** The predefined log priorities
    *
    * [bindgen] header: ./SDL_log.h
    */
  opaque type SDL_LogPriority = CInt
  object SDL_LogPriority extends CEnum[SDL_LogPriority]:
    given _tag: Tag[SDL_LogPriority]                   = Tag.Int
    inline def define(inline a: CInt): SDL_LogPriority = a
    val SDL_LOG_PRIORITY_VERBOSE                       = define(1)
    val SDL_LOG_PRIORITY_DEBUG                         = define(2)
    val SDL_LOG_PRIORITY_INFO                          = define(3)
    val SDL_LOG_PRIORITY_WARN                          = define(4)
    val SDL_LOG_PRIORITY_ERROR                         = define(5)
    val SDL_LOG_PRIORITY_CRITICAL                      = define(6)
    val SDL_NUM_LOG_PRIORITIES                         = define(7)
    inline def getName(inline value: SDL_LogPriority): Option[String] =
      inline value match
        case SDL_LOG_PRIORITY_VERBOSE  => Some("SDL_LOG_PRIORITY_VERBOSE")
        case SDL_LOG_PRIORITY_DEBUG    => Some("SDL_LOG_PRIORITY_DEBUG")
        case SDL_LOG_PRIORITY_INFO     => Some("SDL_LOG_PRIORITY_INFO")
        case SDL_LOG_PRIORITY_WARN     => Some("SDL_LOG_PRIORITY_WARN")
        case SDL_LOG_PRIORITY_ERROR    => Some("SDL_LOG_PRIORITY_ERROR")
        case SDL_LOG_PRIORITY_CRITICAL => Some("SDL_LOG_PRIORITY_CRITICAL")
        case SDL_NUM_LOG_PRIORITIES    => Some("SDL_NUM_LOG_PRIORITIES")
        case _                         => None
    extension (a: SDL_LogPriority)
      inline def &(b: SDL_LogPriority): SDL_LogPriority = a & b
      inline def |(b: SDL_LogPriority): SDL_LogPriority = a | b
      inline def is(b: SDL_LogPriority): Boolean        = (a & b) == b

  /** Flags for SDL_MessageBoxButtonData.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxButtonFlags = CInt
  object SDL_MessageBoxButtonFlags extends CEnum[SDL_MessageBoxButtonFlags]:
    given _tag: Tag[SDL_MessageBoxButtonFlags]                   = Tag.Int
    inline def define(inline a: CInt): SDL_MessageBoxButtonFlags = a
    val SDL_MESSAGEBOX_BUTTON_RETURNKEY_DEFAULT                  = define(1)
    val SDL_MESSAGEBOX_BUTTON_ESCAPEKEY_DEFAULT                  = define(2)
    inline def getName(inline value: SDL_MessageBoxButtonFlags): Option[String] =
      inline value match
        case SDL_MESSAGEBOX_BUTTON_RETURNKEY_DEFAULT => Some("SDL_MESSAGEBOX_BUTTON_RETURNKEY_DEFAULT")
        case SDL_MESSAGEBOX_BUTTON_ESCAPEKEY_DEFAULT => Some("SDL_MESSAGEBOX_BUTTON_ESCAPEKEY_DEFAULT")
        case _                                       => None
    extension (a: SDL_MessageBoxButtonFlags)
      inline def &(b: SDL_MessageBoxButtonFlags): SDL_MessageBoxButtonFlags = a & b
      inline def |(b: SDL_MessageBoxButtonFlags): SDL_MessageBoxButtonFlags = a | b
      inline def is(b: SDL_MessageBoxButtonFlags): Boolean                  = (a & b) == b

  /** [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxColorType = CInt
  object SDL_MessageBoxColorType extends CEnum[SDL_MessageBoxColorType]:
    given _tag: Tag[SDL_MessageBoxColorType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_MessageBoxColorType = a
    val SDL_MESSAGEBOX_COLOR_BACKGROUND                        = define(0)
    val SDL_MESSAGEBOX_COLOR_TEXT                              = define(1)
    val SDL_MESSAGEBOX_COLOR_BUTTON_BORDER                     = define(2)
    val SDL_MESSAGEBOX_COLOR_BUTTON_BACKGROUND                 = define(3)
    val SDL_MESSAGEBOX_COLOR_BUTTON_SELECTED                   = define(4)
    val SDL_MESSAGEBOX_COLOR_MAX                               = define(5)
    inline def getName(inline value: SDL_MessageBoxColorType): Option[String] =
      inline value match
        case SDL_MESSAGEBOX_COLOR_BACKGROUND        => Some("SDL_MESSAGEBOX_COLOR_BACKGROUND")
        case SDL_MESSAGEBOX_COLOR_TEXT              => Some("SDL_MESSAGEBOX_COLOR_TEXT")
        case SDL_MESSAGEBOX_COLOR_BUTTON_BORDER     => Some("SDL_MESSAGEBOX_COLOR_BUTTON_BORDER")
        case SDL_MESSAGEBOX_COLOR_BUTTON_BACKGROUND => Some("SDL_MESSAGEBOX_COLOR_BUTTON_BACKGROUND")
        case SDL_MESSAGEBOX_COLOR_BUTTON_SELECTED   => Some("SDL_MESSAGEBOX_COLOR_BUTTON_SELECTED")
        case SDL_MESSAGEBOX_COLOR_MAX               => Some("SDL_MESSAGEBOX_COLOR_MAX")
        case _                                      => None
    extension (a: SDL_MessageBoxColorType)
      inline def &(b: SDL_MessageBoxColorType): SDL_MessageBoxColorType = a & b
      inline def |(b: SDL_MessageBoxColorType): SDL_MessageBoxColorType = a | b
      inline def is(b: SDL_MessageBoxColorType): Boolean                = (a & b) == b

  /** SDL_MessageBox flags. If supported will display warning icon, etc.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxFlags = CInt
  object SDL_MessageBoxFlags extends CEnum[SDL_MessageBoxFlags]:
    given _tag: Tag[SDL_MessageBoxFlags]                   = Tag.Int
    inline def define(inline a: CInt): SDL_MessageBoxFlags = a
    val SDL_MESSAGEBOX_ERROR                               = define(16)
    val SDL_MESSAGEBOX_WARNING                             = define(32)
    val SDL_MESSAGEBOX_INFORMATION                         = define(64)
    val SDL_MESSAGEBOX_BUTTONS_LEFT_TO_RIGHT               = define(128)
    val SDL_MESSAGEBOX_BUTTONS_RIGHT_TO_LEFT               = define(256)
    inline def getName(inline value: SDL_MessageBoxFlags): Option[String] =
      inline value match
        case SDL_MESSAGEBOX_ERROR                 => Some("SDL_MESSAGEBOX_ERROR")
        case SDL_MESSAGEBOX_WARNING               => Some("SDL_MESSAGEBOX_WARNING")
        case SDL_MESSAGEBOX_INFORMATION           => Some("SDL_MESSAGEBOX_INFORMATION")
        case SDL_MESSAGEBOX_BUTTONS_LEFT_TO_RIGHT => Some("SDL_MESSAGEBOX_BUTTONS_LEFT_TO_RIGHT")
        case SDL_MESSAGEBOX_BUTTONS_RIGHT_TO_LEFT => Some("SDL_MESSAGEBOX_BUTTONS_RIGHT_TO_LEFT")
        case _                                    => None
    extension (a: SDL_MessageBoxFlags)
      inline def &(b: SDL_MessageBoxFlags): SDL_MessageBoxFlags = a & b
      inline def |(b: SDL_MessageBoxFlags): SDL_MessageBoxFlags = a | b
      inline def is(b: SDL_MessageBoxFlags): Boolean            = (a & b) == b

  /** Scroll direction types for the Scroll event
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  opaque type SDL_MouseWheelDirection = CInt
  object SDL_MouseWheelDirection extends CEnum[SDL_MouseWheelDirection]:
    given _tag: Tag[SDL_MouseWheelDirection]                   = Tag.Int
    inline def define(inline a: CInt): SDL_MouseWheelDirection = a
    val SDL_MOUSEWHEEL_NORMAL                                  = define(0)
    val SDL_MOUSEWHEEL_FLIPPED                                 = define(1)
    inline def getName(inline value: SDL_MouseWheelDirection): Option[String] =
      inline value match
        case SDL_MOUSEWHEEL_NORMAL  => Some("SDL_MOUSEWHEEL_NORMAL")
        case SDL_MOUSEWHEEL_FLIPPED => Some("SDL_MOUSEWHEEL_FLIPPED")
        case _                      => None
    extension (a: SDL_MouseWheelDirection)
      inline def &(b: SDL_MouseWheelDirection): SDL_MouseWheelDirection = a & b
      inline def |(b: SDL_MouseWheelDirection): SDL_MouseWheelDirection = a | b
      inline def is(b: SDL_MouseWheelDirection): Boolean                = (a & b) == b

  /** Packed component layout.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_PackedLayout = CInt
  object SDL_PackedLayout extends CEnum[SDL_PackedLayout]:
    given _tag: Tag[SDL_PackedLayout]                   = Tag.Int
    inline def define(inline a: CInt): SDL_PackedLayout = a
    val SDL_PACKEDLAYOUT_NONE                           = define(0)
    val SDL_PACKEDLAYOUT_332                            = define(1)
    val SDL_PACKEDLAYOUT_4444                           = define(2)
    val SDL_PACKEDLAYOUT_1555                           = define(3)
    val SDL_PACKEDLAYOUT_5551                           = define(4)
    val SDL_PACKEDLAYOUT_565                            = define(5)
    val SDL_PACKEDLAYOUT_8888                           = define(6)
    val SDL_PACKEDLAYOUT_2101010                        = define(7)
    val SDL_PACKEDLAYOUT_1010102                        = define(8)
    inline def getName(inline value: SDL_PackedLayout): Option[String] =
      inline value match
        case SDL_PACKEDLAYOUT_NONE    => Some("SDL_PACKEDLAYOUT_NONE")
        case SDL_PACKEDLAYOUT_332     => Some("SDL_PACKEDLAYOUT_332")
        case SDL_PACKEDLAYOUT_4444    => Some("SDL_PACKEDLAYOUT_4444")
        case SDL_PACKEDLAYOUT_1555    => Some("SDL_PACKEDLAYOUT_1555")
        case SDL_PACKEDLAYOUT_5551    => Some("SDL_PACKEDLAYOUT_5551")
        case SDL_PACKEDLAYOUT_565     => Some("SDL_PACKEDLAYOUT_565")
        case SDL_PACKEDLAYOUT_8888    => Some("SDL_PACKEDLAYOUT_8888")
        case SDL_PACKEDLAYOUT_2101010 => Some("SDL_PACKEDLAYOUT_2101010")
        case SDL_PACKEDLAYOUT_1010102 => Some("SDL_PACKEDLAYOUT_1010102")
        case _                        => None
    extension (a: SDL_PackedLayout)
      inline def &(b: SDL_PackedLayout): SDL_PackedLayout = a & b
      inline def |(b: SDL_PackedLayout): SDL_PackedLayout = a | b
      inline def is(b: SDL_PackedLayout): Boolean         = (a & b) == b

  /** Packed component order, high bit -> low bit.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_PackedOrder = CInt
  object SDL_PackedOrder extends CEnum[SDL_PackedOrder]:
    given _tag: Tag[SDL_PackedOrder]                   = Tag.Int
    inline def define(inline a: CInt): SDL_PackedOrder = a
    val SDL_PACKEDORDER_NONE                           = define(0)
    val SDL_PACKEDORDER_XRGB                           = define(1)
    val SDL_PACKEDORDER_RGBX                           = define(2)
    val SDL_PACKEDORDER_ARGB                           = define(3)
    val SDL_PACKEDORDER_RGBA                           = define(4)
    val SDL_PACKEDORDER_XBGR                           = define(5)
    val SDL_PACKEDORDER_BGRX                           = define(6)
    val SDL_PACKEDORDER_ABGR                           = define(7)
    val SDL_PACKEDORDER_BGRA                           = define(8)
    inline def getName(inline value: SDL_PackedOrder): Option[String] =
      inline value match
        case SDL_PACKEDORDER_NONE => Some("SDL_PACKEDORDER_NONE")
        case SDL_PACKEDORDER_XRGB => Some("SDL_PACKEDORDER_XRGB")
        case SDL_PACKEDORDER_RGBX => Some("SDL_PACKEDORDER_RGBX")
        case SDL_PACKEDORDER_ARGB => Some("SDL_PACKEDORDER_ARGB")
        case SDL_PACKEDORDER_RGBA => Some("SDL_PACKEDORDER_RGBA")
        case SDL_PACKEDORDER_XBGR => Some("SDL_PACKEDORDER_XBGR")
        case SDL_PACKEDORDER_BGRX => Some("SDL_PACKEDORDER_BGRX")
        case SDL_PACKEDORDER_ABGR => Some("SDL_PACKEDORDER_ABGR")
        case SDL_PACKEDORDER_BGRA => Some("SDL_PACKEDORDER_BGRA")
        case _                    => None
    extension (a: SDL_PackedOrder)
      inline def &(b: SDL_PackedOrder): SDL_PackedOrder = a & b
      inline def |(b: SDL_PackedOrder): SDL_PackedOrder = a | b
      inline def is(b: SDL_PackedOrder): Boolean        = (a & b) == b

  /** [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_PixelFormatEnum = CInt
  object SDL_PixelFormatEnum extends CEnum[SDL_PixelFormatEnum]:
    given _tag: Tag[SDL_PixelFormatEnum]                   = Tag.Int
    inline def define(inline a: CInt): SDL_PixelFormatEnum = a
    val SDL_PIXELFORMAT_UNKNOWN                            = define(0)
    val SDL_PIXELFORMAT_INDEX1LSB                          = define(286261504)
    val SDL_PIXELFORMAT_INDEX1MSB                          = define(287310080)
    val SDL_PIXELFORMAT_INDEX2LSB                          = define(470811136)
    val SDL_PIXELFORMAT_INDEX2MSB                          = define(471859712)
    val SDL_PIXELFORMAT_INDEX4LSB                          = define(303039488)
    val SDL_PIXELFORMAT_INDEX4MSB                          = define(304088064)
    val SDL_PIXELFORMAT_INDEX8                             = define(318769153)
    val SDL_PIXELFORMAT_RGB332                             = define(336660481)
    val SDL_PIXELFORMAT_XRGB4444                           = define(353504258)
    val SDL_PIXELFORMAT_RGB444                             = define(353504258)
    val SDL_PIXELFORMAT_XBGR4444                           = define(357698562)
    val SDL_PIXELFORMAT_BGR444                             = define(357698562)
    val SDL_PIXELFORMAT_XRGB1555                           = define(353570562)
    val SDL_PIXELFORMAT_RGB555                             = define(353570562)
    val SDL_PIXELFORMAT_XBGR1555                           = define(357764866)
    val SDL_PIXELFORMAT_BGR555                             = define(357764866)
    val SDL_PIXELFORMAT_ARGB4444                           = define(355602434)
    val SDL_PIXELFORMAT_RGBA4444                           = define(356651010)
    val SDL_PIXELFORMAT_ABGR4444                           = define(359796738)
    val SDL_PIXELFORMAT_BGRA4444                           = define(360845314)
    val SDL_PIXELFORMAT_ARGB1555                           = define(355667970)
    val SDL_PIXELFORMAT_RGBA5551                           = define(356782082)
    val SDL_PIXELFORMAT_ABGR1555                           = define(359862274)
    val SDL_PIXELFORMAT_BGRA5551                           = define(360976386)
    val SDL_PIXELFORMAT_RGB565                             = define(353701890)
    val SDL_PIXELFORMAT_BGR565                             = define(357896194)
    val SDL_PIXELFORMAT_RGB24                              = define(386930691)
    val SDL_PIXELFORMAT_BGR24                              = define(390076419)
    val SDL_PIXELFORMAT_XRGB8888                           = define(370546692)
    val SDL_PIXELFORMAT_RGB888                             = define(370546692)
    val SDL_PIXELFORMAT_RGBX8888                           = define(371595268)
    val SDL_PIXELFORMAT_XBGR8888                           = define(374740996)
    val SDL_PIXELFORMAT_BGR888                             = define(374740996)
    val SDL_PIXELFORMAT_BGRX8888                           = define(375789572)
    val SDL_PIXELFORMAT_ARGB8888                           = define(372645892)
    val SDL_PIXELFORMAT_RGBA8888                           = define(373694468)
    val SDL_PIXELFORMAT_ABGR8888                           = define(376840196)
    val SDL_PIXELFORMAT_BGRA8888                           = define(377888772)
    val SDL_PIXELFORMAT_ARGB2101010                        = define(372711428)
    val SDL_PIXELFORMAT_RGBA32                             = define(376840196)
    val SDL_PIXELFORMAT_ARGB32                             = define(377888772)
    val SDL_PIXELFORMAT_BGRA32                             = define(372645892)
    val SDL_PIXELFORMAT_ABGR32                             = define(373694468)
    val SDL_PIXELFORMAT_RGBX32                             = define(374740996)
    val SDL_PIXELFORMAT_XRGB32                             = define(375789572)
    val SDL_PIXELFORMAT_BGRX32                             = define(370546692)
    val SDL_PIXELFORMAT_XBGR32                             = define(371595268)
    val SDL_PIXELFORMAT_YV12                               = define(842094169)
    val SDL_PIXELFORMAT_IYUV                               = define(1448433993)
    val SDL_PIXELFORMAT_YUY2                               = define(844715353)
    val SDL_PIXELFORMAT_UYVY                               = define(1498831189)
    val SDL_PIXELFORMAT_YVYU                               = define(1431918169)
    val SDL_PIXELFORMAT_NV12                               = define(842094158)
    val SDL_PIXELFORMAT_NV21                               = define(825382478)
    val SDL_PIXELFORMAT_EXTERNAL_OES                       = define(542328143)
    inline def getName(inline value: SDL_PixelFormatEnum): Option[String] =
      inline value match
        case SDL_PIXELFORMAT_UNKNOWN      => Some("SDL_PIXELFORMAT_UNKNOWN")
        case SDL_PIXELFORMAT_INDEX1LSB    => Some("SDL_PIXELFORMAT_INDEX1LSB")
        case SDL_PIXELFORMAT_INDEX1MSB    => Some("SDL_PIXELFORMAT_INDEX1MSB")
        case SDL_PIXELFORMAT_INDEX2LSB    => Some("SDL_PIXELFORMAT_INDEX2LSB")
        case SDL_PIXELFORMAT_INDEX2MSB    => Some("SDL_PIXELFORMAT_INDEX2MSB")
        case SDL_PIXELFORMAT_INDEX4LSB    => Some("SDL_PIXELFORMAT_INDEX4LSB")
        case SDL_PIXELFORMAT_INDEX4MSB    => Some("SDL_PIXELFORMAT_INDEX4MSB")
        case SDL_PIXELFORMAT_INDEX8       => Some("SDL_PIXELFORMAT_INDEX8")
        case SDL_PIXELFORMAT_RGB332       => Some("SDL_PIXELFORMAT_RGB332")
        case SDL_PIXELFORMAT_XRGB4444     => Some("SDL_PIXELFORMAT_XRGB4444")
        case SDL_PIXELFORMAT_RGB444       => Some("SDL_PIXELFORMAT_RGB444")
        case SDL_PIXELFORMAT_XBGR4444     => Some("SDL_PIXELFORMAT_XBGR4444")
        case SDL_PIXELFORMAT_BGR444       => Some("SDL_PIXELFORMAT_BGR444")
        case SDL_PIXELFORMAT_XRGB1555     => Some("SDL_PIXELFORMAT_XRGB1555")
        case SDL_PIXELFORMAT_RGB555       => Some("SDL_PIXELFORMAT_RGB555")
        case SDL_PIXELFORMAT_XBGR1555     => Some("SDL_PIXELFORMAT_XBGR1555")
        case SDL_PIXELFORMAT_BGR555       => Some("SDL_PIXELFORMAT_BGR555")
        case SDL_PIXELFORMAT_ARGB4444     => Some("SDL_PIXELFORMAT_ARGB4444")
        case SDL_PIXELFORMAT_RGBA4444     => Some("SDL_PIXELFORMAT_RGBA4444")
        case SDL_PIXELFORMAT_ABGR4444     => Some("SDL_PIXELFORMAT_ABGR4444")
        case SDL_PIXELFORMAT_BGRA4444     => Some("SDL_PIXELFORMAT_BGRA4444")
        case SDL_PIXELFORMAT_ARGB1555     => Some("SDL_PIXELFORMAT_ARGB1555")
        case SDL_PIXELFORMAT_RGBA5551     => Some("SDL_PIXELFORMAT_RGBA5551")
        case SDL_PIXELFORMAT_ABGR1555     => Some("SDL_PIXELFORMAT_ABGR1555")
        case SDL_PIXELFORMAT_BGRA5551     => Some("SDL_PIXELFORMAT_BGRA5551")
        case SDL_PIXELFORMAT_RGB565       => Some("SDL_PIXELFORMAT_RGB565")
        case SDL_PIXELFORMAT_BGR565       => Some("SDL_PIXELFORMAT_BGR565")
        case SDL_PIXELFORMAT_RGB24        => Some("SDL_PIXELFORMAT_RGB24")
        case SDL_PIXELFORMAT_BGR24        => Some("SDL_PIXELFORMAT_BGR24")
        case SDL_PIXELFORMAT_XRGB8888     => Some("SDL_PIXELFORMAT_XRGB8888")
        case SDL_PIXELFORMAT_RGB888       => Some("SDL_PIXELFORMAT_RGB888")
        case SDL_PIXELFORMAT_RGBX8888     => Some("SDL_PIXELFORMAT_RGBX8888")
        case SDL_PIXELFORMAT_XBGR8888     => Some("SDL_PIXELFORMAT_XBGR8888")
        case SDL_PIXELFORMAT_BGR888       => Some("SDL_PIXELFORMAT_BGR888")
        case SDL_PIXELFORMAT_BGRX8888     => Some("SDL_PIXELFORMAT_BGRX8888")
        case SDL_PIXELFORMAT_ARGB8888     => Some("SDL_PIXELFORMAT_ARGB8888")
        case SDL_PIXELFORMAT_RGBA8888     => Some("SDL_PIXELFORMAT_RGBA8888")
        case SDL_PIXELFORMAT_ABGR8888     => Some("SDL_PIXELFORMAT_ABGR8888")
        case SDL_PIXELFORMAT_BGRA8888     => Some("SDL_PIXELFORMAT_BGRA8888")
        case SDL_PIXELFORMAT_ARGB2101010  => Some("SDL_PIXELFORMAT_ARGB2101010")
        case SDL_PIXELFORMAT_RGBA32       => Some("SDL_PIXELFORMAT_RGBA32")
        case SDL_PIXELFORMAT_ARGB32       => Some("SDL_PIXELFORMAT_ARGB32")
        case SDL_PIXELFORMAT_BGRA32       => Some("SDL_PIXELFORMAT_BGRA32")
        case SDL_PIXELFORMAT_ABGR32       => Some("SDL_PIXELFORMAT_ABGR32")
        case SDL_PIXELFORMAT_RGBX32       => Some("SDL_PIXELFORMAT_RGBX32")
        case SDL_PIXELFORMAT_XRGB32       => Some("SDL_PIXELFORMAT_XRGB32")
        case SDL_PIXELFORMAT_BGRX32       => Some("SDL_PIXELFORMAT_BGRX32")
        case SDL_PIXELFORMAT_XBGR32       => Some("SDL_PIXELFORMAT_XBGR32")
        case SDL_PIXELFORMAT_YV12         => Some("SDL_PIXELFORMAT_YV12")
        case SDL_PIXELFORMAT_IYUV         => Some("SDL_PIXELFORMAT_IYUV")
        case SDL_PIXELFORMAT_YUY2         => Some("SDL_PIXELFORMAT_YUY2")
        case SDL_PIXELFORMAT_UYVY         => Some("SDL_PIXELFORMAT_UYVY")
        case SDL_PIXELFORMAT_YVYU         => Some("SDL_PIXELFORMAT_YVYU")
        case SDL_PIXELFORMAT_NV12         => Some("SDL_PIXELFORMAT_NV12")
        case SDL_PIXELFORMAT_NV21         => Some("SDL_PIXELFORMAT_NV21")
        case SDL_PIXELFORMAT_EXTERNAL_OES => Some("SDL_PIXELFORMAT_EXTERNAL_OES")
        case _                            => None
    extension (a: SDL_PixelFormatEnum)
      inline def &(b: SDL_PixelFormatEnum): SDL_PixelFormatEnum = a & b
      inline def |(b: SDL_PixelFormatEnum): SDL_PixelFormatEnum = a | b
      inline def is(b: SDL_PixelFormatEnum): Boolean            = (a & b) == b

  /** Pixel type.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_PixelType = CInt
  object SDL_PixelType extends CEnum[SDL_PixelType]:
    given _tag: Tag[SDL_PixelType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_PixelType = a
    val SDL_PIXELTYPE_UNKNOWN                        = define(0)
    val SDL_PIXELTYPE_INDEX1                         = define(1)
    val SDL_PIXELTYPE_INDEX4                         = define(2)
    val SDL_PIXELTYPE_INDEX8                         = define(3)
    val SDL_PIXELTYPE_PACKED8                        = define(4)
    val SDL_PIXELTYPE_PACKED16                       = define(5)
    val SDL_PIXELTYPE_PACKED32                       = define(6)
    val SDL_PIXELTYPE_ARRAYU8                        = define(7)
    val SDL_PIXELTYPE_ARRAYU16                       = define(8)
    val SDL_PIXELTYPE_ARRAYU32                       = define(9)
    val SDL_PIXELTYPE_ARRAYF16                       = define(10)
    val SDL_PIXELTYPE_ARRAYF32                       = define(11)
    val SDL_PIXELTYPE_INDEX2                         = define(12)
    inline def getName(inline value: SDL_PixelType): Option[String] =
      inline value match
        case SDL_PIXELTYPE_UNKNOWN  => Some("SDL_PIXELTYPE_UNKNOWN")
        case SDL_PIXELTYPE_INDEX1   => Some("SDL_PIXELTYPE_INDEX1")
        case SDL_PIXELTYPE_INDEX4   => Some("SDL_PIXELTYPE_INDEX4")
        case SDL_PIXELTYPE_INDEX8   => Some("SDL_PIXELTYPE_INDEX8")
        case SDL_PIXELTYPE_PACKED8  => Some("SDL_PIXELTYPE_PACKED8")
        case SDL_PIXELTYPE_PACKED16 => Some("SDL_PIXELTYPE_PACKED16")
        case SDL_PIXELTYPE_PACKED32 => Some("SDL_PIXELTYPE_PACKED32")
        case SDL_PIXELTYPE_ARRAYU8  => Some("SDL_PIXELTYPE_ARRAYU8")
        case SDL_PIXELTYPE_ARRAYU16 => Some("SDL_PIXELTYPE_ARRAYU16")
        case SDL_PIXELTYPE_ARRAYU32 => Some("SDL_PIXELTYPE_ARRAYU32")
        case SDL_PIXELTYPE_ARRAYF16 => Some("SDL_PIXELTYPE_ARRAYF16")
        case SDL_PIXELTYPE_ARRAYF32 => Some("SDL_PIXELTYPE_ARRAYF32")
        case SDL_PIXELTYPE_INDEX2   => Some("SDL_PIXELTYPE_INDEX2")
        case _                      => None
    extension (a: SDL_PixelType)
      inline def &(b: SDL_PixelType): SDL_PixelType = a & b
      inline def |(b: SDL_PixelType): SDL_PixelType = a | b
      inline def is(b: SDL_PixelType): Boolean      = (a & b) == b

  /** The basic state for the system's power supply.
    *
    * [bindgen] header: ./SDL_power.h
    */
  opaque type SDL_PowerState = CInt
  object SDL_PowerState extends CEnum[SDL_PowerState]:
    given _tag: Tag[SDL_PowerState]                   = Tag.Int
    inline def define(inline a: CInt): SDL_PowerState = a
    val SDL_POWERSTATE_UNKNOWN                        = define(0)
    val SDL_POWERSTATE_ON_BATTERY                     = define(1)
    val SDL_POWERSTATE_NO_BATTERY                     = define(2)
    val SDL_POWERSTATE_CHARGING                       = define(3)
    val SDL_POWERSTATE_CHARGED                        = define(4)
    inline def getName(inline value: SDL_PowerState): Option[String] =
      inline value match
        case SDL_POWERSTATE_UNKNOWN    => Some("SDL_POWERSTATE_UNKNOWN")
        case SDL_POWERSTATE_ON_BATTERY => Some("SDL_POWERSTATE_ON_BATTERY")
        case SDL_POWERSTATE_NO_BATTERY => Some("SDL_POWERSTATE_NO_BATTERY")
        case SDL_POWERSTATE_CHARGING   => Some("SDL_POWERSTATE_CHARGING")
        case SDL_POWERSTATE_CHARGED    => Some("SDL_POWERSTATE_CHARGED")
        case _                         => None
    extension (a: SDL_PowerState)
      inline def &(b: SDL_PowerState): SDL_PowerState = a & b
      inline def |(b: SDL_PowerState): SDL_PowerState = a | b
      inline def is(b: SDL_PowerState): Boolean       = (a & b) == b

  /** Flags used when creating a rendering context
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_RendererFlags = CInt
  object SDL_RendererFlags extends CEnum[SDL_RendererFlags]:
    given _tag: Tag[SDL_RendererFlags]                   = Tag.Int
    inline def define(inline a: CInt): SDL_RendererFlags = a
    val SDL_RENDERER_SOFTWARE                            = define(1)
    val SDL_RENDERER_ACCELERATED                         = define(2)
    val SDL_RENDERER_PRESENTVSYNC                        = define(4)
    val SDL_RENDERER_TARGETTEXTURE                       = define(8)
    inline def getName(inline value: SDL_RendererFlags): Option[String] =
      inline value match
        case SDL_RENDERER_SOFTWARE      => Some("SDL_RENDERER_SOFTWARE")
        case SDL_RENDERER_ACCELERATED   => Some("SDL_RENDERER_ACCELERATED")
        case SDL_RENDERER_PRESENTVSYNC  => Some("SDL_RENDERER_PRESENTVSYNC")
        case SDL_RENDERER_TARGETTEXTURE => Some("SDL_RENDERER_TARGETTEXTURE")
        case _                          => None
    extension (a: SDL_RendererFlags)
      inline def &(b: SDL_RendererFlags): SDL_RendererFlags = a & b
      inline def |(b: SDL_RendererFlags): SDL_RendererFlags = a | b
      inline def is(b: SDL_RendererFlags): Boolean          = (a & b) == b

  /** Flip constants for SDL_RenderCopyEx
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_RendererFlip = CInt
  object SDL_RendererFlip extends CEnum[SDL_RendererFlip]:
    given _tag: Tag[SDL_RendererFlip]                   = Tag.Int
    inline def define(inline a: CInt): SDL_RendererFlip = a
    val SDL_FLIP_NONE                                   = define(0)
    val SDL_FLIP_HORIZONTAL                             = define(1)
    val SDL_FLIP_VERTICAL                               = define(2)
    inline def getName(inline value: SDL_RendererFlip): Option[String] =
      inline value match
        case SDL_FLIP_NONE       => Some("SDL_FLIP_NONE")
        case SDL_FLIP_HORIZONTAL => Some("SDL_FLIP_HORIZONTAL")
        case SDL_FLIP_VERTICAL   => Some("SDL_FLIP_VERTICAL")
        case _                   => None
    extension (a: SDL_RendererFlip)
      inline def &(b: SDL_RendererFlip): SDL_RendererFlip = a & b
      inline def |(b: SDL_RendererFlip): SDL_RendererFlip = a | b
      inline def is(b: SDL_RendererFlip): Boolean         = (a & b) == b

  /** The scaling mode for a texture.
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_ScaleMode = CInt
  object SDL_ScaleMode extends CEnum[SDL_ScaleMode]:
    given _tag: Tag[SDL_ScaleMode]                   = Tag.Int
    inline def define(inline a: CInt): SDL_ScaleMode = a
    val SDL_ScaleModeNearest                         = define(0)
    val SDL_ScaleModeLinear                          = define(1)
    val SDL_ScaleModeBest                            = define(2)
    inline def getName(inline value: SDL_ScaleMode): Option[String] =
      inline value match
        case SDL_ScaleModeNearest => Some("SDL_ScaleModeNearest")
        case SDL_ScaleModeLinear  => Some("SDL_ScaleModeLinear")
        case SDL_ScaleModeBest    => Some("SDL_ScaleModeBest")
        case _                    => None
    extension (a: SDL_ScaleMode)
      inline def &(b: SDL_ScaleMode): SDL_ScaleMode = a & b
      inline def |(b: SDL_ScaleMode): SDL_ScaleMode = a | b
      inline def is(b: SDL_ScaleMode): Boolean      = (a & b) == b

  /** The SDL keyboard scancode representation.
    *
    * [bindgen] header: ./SDL_scancode.h
    */
  opaque type SDL_Scancode = CInt
  object SDL_Scancode extends CEnum[SDL_Scancode]:
    given _tag: Tag[SDL_Scancode]                   = Tag.Int
    inline def define(inline a: CInt): SDL_Scancode = a
    val SDL_SCANCODE_UNKNOWN                        = define(0)
    val SDL_SCANCODE_A                              = define(4)
    val SDL_SCANCODE_B                              = define(5)
    val SDL_SCANCODE_C                              = define(6)
    val SDL_SCANCODE_D                              = define(7)
    val SDL_SCANCODE_E                              = define(8)
    val SDL_SCANCODE_F                              = define(9)
    val SDL_SCANCODE_G                              = define(10)
    val SDL_SCANCODE_H                              = define(11)
    val SDL_SCANCODE_I                              = define(12)
    val SDL_SCANCODE_J                              = define(13)
    val SDL_SCANCODE_K                              = define(14)
    val SDL_SCANCODE_L                              = define(15)
    val SDL_SCANCODE_M                              = define(16)
    val SDL_SCANCODE_N                              = define(17)
    val SDL_SCANCODE_O                              = define(18)
    val SDL_SCANCODE_P                              = define(19)
    val SDL_SCANCODE_Q                              = define(20)
    val SDL_SCANCODE_R                              = define(21)
    val SDL_SCANCODE_S                              = define(22)
    val SDL_SCANCODE_T                              = define(23)
    val SDL_SCANCODE_U                              = define(24)
    val SDL_SCANCODE_V                              = define(25)
    val SDL_SCANCODE_W                              = define(26)
    val SDL_SCANCODE_X                              = define(27)
    val SDL_SCANCODE_Y                              = define(28)
    val SDL_SCANCODE_Z                              = define(29)
    val SDL_SCANCODE_1                              = define(30)
    val SDL_SCANCODE_2                              = define(31)
    val SDL_SCANCODE_3                              = define(32)
    val SDL_SCANCODE_4                              = define(33)
    val SDL_SCANCODE_5                              = define(34)
    val SDL_SCANCODE_6                              = define(35)
    val SDL_SCANCODE_7                              = define(36)
    val SDL_SCANCODE_8                              = define(37)
    val SDL_SCANCODE_9                              = define(38)
    val SDL_SCANCODE_0                              = define(39)
    val SDL_SCANCODE_RETURN                         = define(40)
    val SDL_SCANCODE_ESCAPE                         = define(41)
    val SDL_SCANCODE_BACKSPACE                      = define(42)
    val SDL_SCANCODE_TAB                            = define(43)
    val SDL_SCANCODE_SPACE                          = define(44)
    val SDL_SCANCODE_MINUS                          = define(45)
    val SDL_SCANCODE_EQUALS                         = define(46)
    val SDL_SCANCODE_LEFTBRACKET                    = define(47)
    val SDL_SCANCODE_RIGHTBRACKET                   = define(48)
    val SDL_SCANCODE_BACKSLASH                      = define(49)
    val SDL_SCANCODE_NONUSHASH                      = define(50)
    val SDL_SCANCODE_SEMICOLON                      = define(51)
    val SDL_SCANCODE_APOSTROPHE                     = define(52)
    val SDL_SCANCODE_GRAVE                          = define(53)
    val SDL_SCANCODE_COMMA                          = define(54)
    val SDL_SCANCODE_PERIOD                         = define(55)
    val SDL_SCANCODE_SLASH                          = define(56)
    val SDL_SCANCODE_CAPSLOCK                       = define(57)
    val SDL_SCANCODE_F1                             = define(58)
    val SDL_SCANCODE_F2                             = define(59)
    val SDL_SCANCODE_F3                             = define(60)
    val SDL_SCANCODE_F4                             = define(61)
    val SDL_SCANCODE_F5                             = define(62)
    val SDL_SCANCODE_F6                             = define(63)
    val SDL_SCANCODE_F7                             = define(64)
    val SDL_SCANCODE_F8                             = define(65)
    val SDL_SCANCODE_F9                             = define(66)
    val SDL_SCANCODE_F10                            = define(67)
    val SDL_SCANCODE_F11                            = define(68)
    val SDL_SCANCODE_F12                            = define(69)
    val SDL_SCANCODE_PRINTSCREEN                    = define(70)
    val SDL_SCANCODE_SCROLLLOCK                     = define(71)
    val SDL_SCANCODE_PAUSE                          = define(72)
    val SDL_SCANCODE_INSERT                         = define(73)
    val SDL_SCANCODE_HOME                           = define(74)
    val SDL_SCANCODE_PAGEUP                         = define(75)
    val SDL_SCANCODE_DELETE                         = define(76)
    val SDL_SCANCODE_END                            = define(77)
    val SDL_SCANCODE_PAGEDOWN                       = define(78)
    val SDL_SCANCODE_RIGHT                          = define(79)
    val SDL_SCANCODE_LEFT                           = define(80)
    val SDL_SCANCODE_DOWN                           = define(81)
    val SDL_SCANCODE_UP                             = define(82)
    val SDL_SCANCODE_NUMLOCKCLEAR                   = define(83)
    val SDL_SCANCODE_KP_DIVIDE                      = define(84)
    val SDL_SCANCODE_KP_MULTIPLY                    = define(85)
    val SDL_SCANCODE_KP_MINUS                       = define(86)
    val SDL_SCANCODE_KP_PLUS                        = define(87)
    val SDL_SCANCODE_KP_ENTER                       = define(88)
    val SDL_SCANCODE_KP_1                           = define(89)
    val SDL_SCANCODE_KP_2                           = define(90)
    val SDL_SCANCODE_KP_3                           = define(91)
    val SDL_SCANCODE_KP_4                           = define(92)
    val SDL_SCANCODE_KP_5                           = define(93)
    val SDL_SCANCODE_KP_6                           = define(94)
    val SDL_SCANCODE_KP_7                           = define(95)
    val SDL_SCANCODE_KP_8                           = define(96)
    val SDL_SCANCODE_KP_9                           = define(97)
    val SDL_SCANCODE_KP_0                           = define(98)
    val SDL_SCANCODE_KP_PERIOD                      = define(99)
    val SDL_SCANCODE_NONUSBACKSLASH                 = define(100)
    val SDL_SCANCODE_APPLICATION                    = define(101)
    val SDL_SCANCODE_POWER                          = define(102)
    val SDL_SCANCODE_KP_EQUALS                      = define(103)
    val SDL_SCANCODE_F13                            = define(104)
    val SDL_SCANCODE_F14                            = define(105)
    val SDL_SCANCODE_F15                            = define(106)
    val SDL_SCANCODE_F16                            = define(107)
    val SDL_SCANCODE_F17                            = define(108)
    val SDL_SCANCODE_F18                            = define(109)
    val SDL_SCANCODE_F19                            = define(110)
    val SDL_SCANCODE_F20                            = define(111)
    val SDL_SCANCODE_F21                            = define(112)
    val SDL_SCANCODE_F22                            = define(113)
    val SDL_SCANCODE_F23                            = define(114)
    val SDL_SCANCODE_F24                            = define(115)
    val SDL_SCANCODE_EXECUTE                        = define(116)
    val SDL_SCANCODE_HELP                           = define(117)
    val SDL_SCANCODE_MENU                           = define(118)
    val SDL_SCANCODE_SELECT                         = define(119)
    val SDL_SCANCODE_STOP                           = define(120)
    val SDL_SCANCODE_AGAIN                          = define(121)
    val SDL_SCANCODE_UNDO                           = define(122)
    val SDL_SCANCODE_CUT                            = define(123)
    val SDL_SCANCODE_COPY                           = define(124)
    val SDL_SCANCODE_PASTE                          = define(125)
    val SDL_SCANCODE_FIND                           = define(126)
    val SDL_SCANCODE_MUTE                           = define(127)
    val SDL_SCANCODE_VOLUMEUP                       = define(128)
    val SDL_SCANCODE_VOLUMEDOWN                     = define(129)
    val SDL_SCANCODE_KP_COMMA                       = define(133)
    val SDL_SCANCODE_KP_EQUALSAS400                 = define(134)
    val SDL_SCANCODE_INTERNATIONAL1                 = define(135)
    val SDL_SCANCODE_INTERNATIONAL2                 = define(136)
    val SDL_SCANCODE_INTERNATIONAL3                 = define(137)
    val SDL_SCANCODE_INTERNATIONAL4                 = define(138)
    val SDL_SCANCODE_INTERNATIONAL5                 = define(139)
    val SDL_SCANCODE_INTERNATIONAL6                 = define(140)
    val SDL_SCANCODE_INTERNATIONAL7                 = define(141)
    val SDL_SCANCODE_INTERNATIONAL8                 = define(142)
    val SDL_SCANCODE_INTERNATIONAL9                 = define(143)
    val SDL_SCANCODE_LANG1                          = define(144)
    val SDL_SCANCODE_LANG2                          = define(145)
    val SDL_SCANCODE_LANG3                          = define(146)
    val SDL_SCANCODE_LANG4                          = define(147)
    val SDL_SCANCODE_LANG5                          = define(148)
    val SDL_SCANCODE_LANG6                          = define(149)
    val SDL_SCANCODE_LANG7                          = define(150)
    val SDL_SCANCODE_LANG8                          = define(151)
    val SDL_SCANCODE_LANG9                          = define(152)
    val SDL_SCANCODE_ALTERASE                       = define(153)
    val SDL_SCANCODE_SYSREQ                         = define(154)
    val SDL_SCANCODE_CANCEL                         = define(155)
    val SDL_SCANCODE_CLEAR                          = define(156)
    val SDL_SCANCODE_PRIOR                          = define(157)
    val SDL_SCANCODE_RETURN2                        = define(158)
    val SDL_SCANCODE_SEPARATOR                      = define(159)
    val SDL_SCANCODE_OUT                            = define(160)
    val SDL_SCANCODE_OPER                           = define(161)
    val SDL_SCANCODE_CLEARAGAIN                     = define(162)
    val SDL_SCANCODE_CRSEL                          = define(163)
    val SDL_SCANCODE_EXSEL                          = define(164)
    val SDL_SCANCODE_KP_00                          = define(176)
    val SDL_SCANCODE_KP_000                         = define(177)
    val SDL_SCANCODE_THOUSANDSSEPARATOR             = define(178)
    val SDL_SCANCODE_DECIMALSEPARATOR               = define(179)
    val SDL_SCANCODE_CURRENCYUNIT                   = define(180)
    val SDL_SCANCODE_CURRENCYSUBUNIT                = define(181)
    val SDL_SCANCODE_KP_LEFTPAREN                   = define(182)
    val SDL_SCANCODE_KP_RIGHTPAREN                  = define(183)
    val SDL_SCANCODE_KP_LEFTBRACE                   = define(184)
    val SDL_SCANCODE_KP_RIGHTBRACE                  = define(185)
    val SDL_SCANCODE_KP_TAB                         = define(186)
    val SDL_SCANCODE_KP_BACKSPACE                   = define(187)
    val SDL_SCANCODE_KP_A                           = define(188)
    val SDL_SCANCODE_KP_B                           = define(189)
    val SDL_SCANCODE_KP_C                           = define(190)
    val SDL_SCANCODE_KP_D                           = define(191)
    val SDL_SCANCODE_KP_E                           = define(192)
    val SDL_SCANCODE_KP_F                           = define(193)
    val SDL_SCANCODE_KP_XOR                         = define(194)
    val SDL_SCANCODE_KP_POWER                       = define(195)
    val SDL_SCANCODE_KP_PERCENT                     = define(196)
    val SDL_SCANCODE_KP_LESS                        = define(197)
    val SDL_SCANCODE_KP_GREATER                     = define(198)
    val SDL_SCANCODE_KP_AMPERSAND                   = define(199)
    val SDL_SCANCODE_KP_DBLAMPERSAND                = define(200)
    val SDL_SCANCODE_KP_VERTICALBAR                 = define(201)
    val SDL_SCANCODE_KP_DBLVERTICALBAR              = define(202)
    val SDL_SCANCODE_KP_COLON                       = define(203)
    val SDL_SCANCODE_KP_HASH                        = define(204)
    val SDL_SCANCODE_KP_SPACE                       = define(205)
    val SDL_SCANCODE_KP_AT                          = define(206)
    val SDL_SCANCODE_KP_EXCLAM                      = define(207)
    val SDL_SCANCODE_KP_MEMSTORE                    = define(208)
    val SDL_SCANCODE_KP_MEMRECALL                   = define(209)
    val SDL_SCANCODE_KP_MEMCLEAR                    = define(210)
    val SDL_SCANCODE_KP_MEMADD                      = define(211)
    val SDL_SCANCODE_KP_MEMSUBTRACT                 = define(212)
    val SDL_SCANCODE_KP_MEMMULTIPLY                 = define(213)
    val SDL_SCANCODE_KP_MEMDIVIDE                   = define(214)
    val SDL_SCANCODE_KP_PLUSMINUS                   = define(215)
    val SDL_SCANCODE_KP_CLEAR                       = define(216)
    val SDL_SCANCODE_KP_CLEARENTRY                  = define(217)
    val SDL_SCANCODE_KP_BINARY                      = define(218)
    val SDL_SCANCODE_KP_OCTAL                       = define(219)
    val SDL_SCANCODE_KP_DECIMAL                     = define(220)
    val SDL_SCANCODE_KP_HEXADECIMAL                 = define(221)
    val SDL_SCANCODE_LCTRL                          = define(224)
    val SDL_SCANCODE_LSHIFT                         = define(225)
    val SDL_SCANCODE_LALT                           = define(226)
    val SDL_SCANCODE_LGUI                           = define(227)
    val SDL_SCANCODE_RCTRL                          = define(228)
    val SDL_SCANCODE_RSHIFT                         = define(229)
    val SDL_SCANCODE_RALT                           = define(230)
    val SDL_SCANCODE_RGUI                           = define(231)
    val SDL_SCANCODE_MODE                           = define(257)
    val SDL_SCANCODE_AUDIONEXT                      = define(258)
    val SDL_SCANCODE_AUDIOPREV                      = define(259)
    val SDL_SCANCODE_AUDIOSTOP                      = define(260)
    val SDL_SCANCODE_AUDIOPLAY                      = define(261)
    val SDL_SCANCODE_AUDIOMUTE                      = define(262)
    val SDL_SCANCODE_MEDIASELECT                    = define(263)
    val SDL_SCANCODE_WWW                            = define(264)
    val SDL_SCANCODE_MAIL                           = define(265)
    val SDL_SCANCODE_CALCULATOR                     = define(266)
    val SDL_SCANCODE_COMPUTER                       = define(267)
    val SDL_SCANCODE_AC_SEARCH                      = define(268)
    val SDL_SCANCODE_AC_HOME                        = define(269)
    val SDL_SCANCODE_AC_BACK                        = define(270)
    val SDL_SCANCODE_AC_FORWARD                     = define(271)
    val SDL_SCANCODE_AC_STOP                        = define(272)
    val SDL_SCANCODE_AC_REFRESH                     = define(273)
    val SDL_SCANCODE_AC_BOOKMARKS                   = define(274)
    val SDL_SCANCODE_BRIGHTNESSDOWN                 = define(275)
    val SDL_SCANCODE_BRIGHTNESSUP                   = define(276)
    val SDL_SCANCODE_DISPLAYSWITCH                  = define(277)
    val SDL_SCANCODE_KBDILLUMTOGGLE                 = define(278)
    val SDL_SCANCODE_KBDILLUMDOWN                   = define(279)
    val SDL_SCANCODE_KBDILLUMUP                     = define(280)
    val SDL_SCANCODE_EJECT                          = define(281)
    val SDL_SCANCODE_SLEEP                          = define(282)
    val SDL_SCANCODE_APP1                           = define(283)
    val SDL_SCANCODE_APP2                           = define(284)
    val SDL_SCANCODE_AUDIOREWIND                    = define(285)
    val SDL_SCANCODE_AUDIOFASTFORWARD               = define(286)
    val SDL_SCANCODE_SOFTLEFT                       = define(287)
    val SDL_SCANCODE_SOFTRIGHT                      = define(288)
    val SDL_SCANCODE_CALL                           = define(289)
    val SDL_SCANCODE_ENDCALL                        = define(290)
    val SDL_NUM_SCANCODES                           = define(512)
    inline def getName(inline value: SDL_Scancode): Option[String] =
      inline value match
        case SDL_SCANCODE_UNKNOWN            => Some("SDL_SCANCODE_UNKNOWN")
        case SDL_SCANCODE_A                  => Some("SDL_SCANCODE_A")
        case SDL_SCANCODE_B                  => Some("SDL_SCANCODE_B")
        case SDL_SCANCODE_C                  => Some("SDL_SCANCODE_C")
        case SDL_SCANCODE_D                  => Some("SDL_SCANCODE_D")
        case SDL_SCANCODE_E                  => Some("SDL_SCANCODE_E")
        case SDL_SCANCODE_F                  => Some("SDL_SCANCODE_F")
        case SDL_SCANCODE_G                  => Some("SDL_SCANCODE_G")
        case SDL_SCANCODE_H                  => Some("SDL_SCANCODE_H")
        case SDL_SCANCODE_I                  => Some("SDL_SCANCODE_I")
        case SDL_SCANCODE_J                  => Some("SDL_SCANCODE_J")
        case SDL_SCANCODE_K                  => Some("SDL_SCANCODE_K")
        case SDL_SCANCODE_L                  => Some("SDL_SCANCODE_L")
        case SDL_SCANCODE_M                  => Some("SDL_SCANCODE_M")
        case SDL_SCANCODE_N                  => Some("SDL_SCANCODE_N")
        case SDL_SCANCODE_O                  => Some("SDL_SCANCODE_O")
        case SDL_SCANCODE_P                  => Some("SDL_SCANCODE_P")
        case SDL_SCANCODE_Q                  => Some("SDL_SCANCODE_Q")
        case SDL_SCANCODE_R                  => Some("SDL_SCANCODE_R")
        case SDL_SCANCODE_S                  => Some("SDL_SCANCODE_S")
        case SDL_SCANCODE_T                  => Some("SDL_SCANCODE_T")
        case SDL_SCANCODE_U                  => Some("SDL_SCANCODE_U")
        case SDL_SCANCODE_V                  => Some("SDL_SCANCODE_V")
        case SDL_SCANCODE_W                  => Some("SDL_SCANCODE_W")
        case SDL_SCANCODE_X                  => Some("SDL_SCANCODE_X")
        case SDL_SCANCODE_Y                  => Some("SDL_SCANCODE_Y")
        case SDL_SCANCODE_Z                  => Some("SDL_SCANCODE_Z")
        case SDL_SCANCODE_1                  => Some("SDL_SCANCODE_1")
        case SDL_SCANCODE_2                  => Some("SDL_SCANCODE_2")
        case SDL_SCANCODE_3                  => Some("SDL_SCANCODE_3")
        case SDL_SCANCODE_4                  => Some("SDL_SCANCODE_4")
        case SDL_SCANCODE_5                  => Some("SDL_SCANCODE_5")
        case SDL_SCANCODE_6                  => Some("SDL_SCANCODE_6")
        case SDL_SCANCODE_7                  => Some("SDL_SCANCODE_7")
        case SDL_SCANCODE_8                  => Some("SDL_SCANCODE_8")
        case SDL_SCANCODE_9                  => Some("SDL_SCANCODE_9")
        case SDL_SCANCODE_0                  => Some("SDL_SCANCODE_0")
        case SDL_SCANCODE_RETURN             => Some("SDL_SCANCODE_RETURN")
        case SDL_SCANCODE_ESCAPE             => Some("SDL_SCANCODE_ESCAPE")
        case SDL_SCANCODE_BACKSPACE          => Some("SDL_SCANCODE_BACKSPACE")
        case SDL_SCANCODE_TAB                => Some("SDL_SCANCODE_TAB")
        case SDL_SCANCODE_SPACE              => Some("SDL_SCANCODE_SPACE")
        case SDL_SCANCODE_MINUS              => Some("SDL_SCANCODE_MINUS")
        case SDL_SCANCODE_EQUALS             => Some("SDL_SCANCODE_EQUALS")
        case SDL_SCANCODE_LEFTBRACKET        => Some("SDL_SCANCODE_LEFTBRACKET")
        case SDL_SCANCODE_RIGHTBRACKET       => Some("SDL_SCANCODE_RIGHTBRACKET")
        case SDL_SCANCODE_BACKSLASH          => Some("SDL_SCANCODE_BACKSLASH")
        case SDL_SCANCODE_NONUSHASH          => Some("SDL_SCANCODE_NONUSHASH")
        case SDL_SCANCODE_SEMICOLON          => Some("SDL_SCANCODE_SEMICOLON")
        case SDL_SCANCODE_APOSTROPHE         => Some("SDL_SCANCODE_APOSTROPHE")
        case SDL_SCANCODE_GRAVE              => Some("SDL_SCANCODE_GRAVE")
        case SDL_SCANCODE_COMMA              => Some("SDL_SCANCODE_COMMA")
        case SDL_SCANCODE_PERIOD             => Some("SDL_SCANCODE_PERIOD")
        case SDL_SCANCODE_SLASH              => Some("SDL_SCANCODE_SLASH")
        case SDL_SCANCODE_CAPSLOCK           => Some("SDL_SCANCODE_CAPSLOCK")
        case SDL_SCANCODE_F1                 => Some("SDL_SCANCODE_F1")
        case SDL_SCANCODE_F2                 => Some("SDL_SCANCODE_F2")
        case SDL_SCANCODE_F3                 => Some("SDL_SCANCODE_F3")
        case SDL_SCANCODE_F4                 => Some("SDL_SCANCODE_F4")
        case SDL_SCANCODE_F5                 => Some("SDL_SCANCODE_F5")
        case SDL_SCANCODE_F6                 => Some("SDL_SCANCODE_F6")
        case SDL_SCANCODE_F7                 => Some("SDL_SCANCODE_F7")
        case SDL_SCANCODE_F8                 => Some("SDL_SCANCODE_F8")
        case SDL_SCANCODE_F9                 => Some("SDL_SCANCODE_F9")
        case SDL_SCANCODE_F10                => Some("SDL_SCANCODE_F10")
        case SDL_SCANCODE_F11                => Some("SDL_SCANCODE_F11")
        case SDL_SCANCODE_F12                => Some("SDL_SCANCODE_F12")
        case SDL_SCANCODE_PRINTSCREEN        => Some("SDL_SCANCODE_PRINTSCREEN")
        case SDL_SCANCODE_SCROLLLOCK         => Some("SDL_SCANCODE_SCROLLLOCK")
        case SDL_SCANCODE_PAUSE              => Some("SDL_SCANCODE_PAUSE")
        case SDL_SCANCODE_INSERT             => Some("SDL_SCANCODE_INSERT")
        case SDL_SCANCODE_HOME               => Some("SDL_SCANCODE_HOME")
        case SDL_SCANCODE_PAGEUP             => Some("SDL_SCANCODE_PAGEUP")
        case SDL_SCANCODE_DELETE             => Some("SDL_SCANCODE_DELETE")
        case SDL_SCANCODE_END                => Some("SDL_SCANCODE_END")
        case SDL_SCANCODE_PAGEDOWN           => Some("SDL_SCANCODE_PAGEDOWN")
        case SDL_SCANCODE_RIGHT              => Some("SDL_SCANCODE_RIGHT")
        case SDL_SCANCODE_LEFT               => Some("SDL_SCANCODE_LEFT")
        case SDL_SCANCODE_DOWN               => Some("SDL_SCANCODE_DOWN")
        case SDL_SCANCODE_UP                 => Some("SDL_SCANCODE_UP")
        case SDL_SCANCODE_NUMLOCKCLEAR       => Some("SDL_SCANCODE_NUMLOCKCLEAR")
        case SDL_SCANCODE_KP_DIVIDE          => Some("SDL_SCANCODE_KP_DIVIDE")
        case SDL_SCANCODE_KP_MULTIPLY        => Some("SDL_SCANCODE_KP_MULTIPLY")
        case SDL_SCANCODE_KP_MINUS           => Some("SDL_SCANCODE_KP_MINUS")
        case SDL_SCANCODE_KP_PLUS            => Some("SDL_SCANCODE_KP_PLUS")
        case SDL_SCANCODE_KP_ENTER           => Some("SDL_SCANCODE_KP_ENTER")
        case SDL_SCANCODE_KP_1               => Some("SDL_SCANCODE_KP_1")
        case SDL_SCANCODE_KP_2               => Some("SDL_SCANCODE_KP_2")
        case SDL_SCANCODE_KP_3               => Some("SDL_SCANCODE_KP_3")
        case SDL_SCANCODE_KP_4               => Some("SDL_SCANCODE_KP_4")
        case SDL_SCANCODE_KP_5               => Some("SDL_SCANCODE_KP_5")
        case SDL_SCANCODE_KP_6               => Some("SDL_SCANCODE_KP_6")
        case SDL_SCANCODE_KP_7               => Some("SDL_SCANCODE_KP_7")
        case SDL_SCANCODE_KP_8               => Some("SDL_SCANCODE_KP_8")
        case SDL_SCANCODE_KP_9               => Some("SDL_SCANCODE_KP_9")
        case SDL_SCANCODE_KP_0               => Some("SDL_SCANCODE_KP_0")
        case SDL_SCANCODE_KP_PERIOD          => Some("SDL_SCANCODE_KP_PERIOD")
        case SDL_SCANCODE_NONUSBACKSLASH     => Some("SDL_SCANCODE_NONUSBACKSLASH")
        case SDL_SCANCODE_APPLICATION        => Some("SDL_SCANCODE_APPLICATION")
        case SDL_SCANCODE_POWER              => Some("SDL_SCANCODE_POWER")
        case SDL_SCANCODE_KP_EQUALS          => Some("SDL_SCANCODE_KP_EQUALS")
        case SDL_SCANCODE_F13                => Some("SDL_SCANCODE_F13")
        case SDL_SCANCODE_F14                => Some("SDL_SCANCODE_F14")
        case SDL_SCANCODE_F15                => Some("SDL_SCANCODE_F15")
        case SDL_SCANCODE_F16                => Some("SDL_SCANCODE_F16")
        case SDL_SCANCODE_F17                => Some("SDL_SCANCODE_F17")
        case SDL_SCANCODE_F18                => Some("SDL_SCANCODE_F18")
        case SDL_SCANCODE_F19                => Some("SDL_SCANCODE_F19")
        case SDL_SCANCODE_F20                => Some("SDL_SCANCODE_F20")
        case SDL_SCANCODE_F21                => Some("SDL_SCANCODE_F21")
        case SDL_SCANCODE_F22                => Some("SDL_SCANCODE_F22")
        case SDL_SCANCODE_F23                => Some("SDL_SCANCODE_F23")
        case SDL_SCANCODE_F24                => Some("SDL_SCANCODE_F24")
        case SDL_SCANCODE_EXECUTE            => Some("SDL_SCANCODE_EXECUTE")
        case SDL_SCANCODE_HELP               => Some("SDL_SCANCODE_HELP")
        case SDL_SCANCODE_MENU               => Some("SDL_SCANCODE_MENU")
        case SDL_SCANCODE_SELECT             => Some("SDL_SCANCODE_SELECT")
        case SDL_SCANCODE_STOP               => Some("SDL_SCANCODE_STOP")
        case SDL_SCANCODE_AGAIN              => Some("SDL_SCANCODE_AGAIN")
        case SDL_SCANCODE_UNDO               => Some("SDL_SCANCODE_UNDO")
        case SDL_SCANCODE_CUT                => Some("SDL_SCANCODE_CUT")
        case SDL_SCANCODE_COPY               => Some("SDL_SCANCODE_COPY")
        case SDL_SCANCODE_PASTE              => Some("SDL_SCANCODE_PASTE")
        case SDL_SCANCODE_FIND               => Some("SDL_SCANCODE_FIND")
        case SDL_SCANCODE_MUTE               => Some("SDL_SCANCODE_MUTE")
        case SDL_SCANCODE_VOLUMEUP           => Some("SDL_SCANCODE_VOLUMEUP")
        case SDL_SCANCODE_VOLUMEDOWN         => Some("SDL_SCANCODE_VOLUMEDOWN")
        case SDL_SCANCODE_KP_COMMA           => Some("SDL_SCANCODE_KP_COMMA")
        case SDL_SCANCODE_KP_EQUALSAS400     => Some("SDL_SCANCODE_KP_EQUALSAS400")
        case SDL_SCANCODE_INTERNATIONAL1     => Some("SDL_SCANCODE_INTERNATIONAL1")
        case SDL_SCANCODE_INTERNATIONAL2     => Some("SDL_SCANCODE_INTERNATIONAL2")
        case SDL_SCANCODE_INTERNATIONAL3     => Some("SDL_SCANCODE_INTERNATIONAL3")
        case SDL_SCANCODE_INTERNATIONAL4     => Some("SDL_SCANCODE_INTERNATIONAL4")
        case SDL_SCANCODE_INTERNATIONAL5     => Some("SDL_SCANCODE_INTERNATIONAL5")
        case SDL_SCANCODE_INTERNATIONAL6     => Some("SDL_SCANCODE_INTERNATIONAL6")
        case SDL_SCANCODE_INTERNATIONAL7     => Some("SDL_SCANCODE_INTERNATIONAL7")
        case SDL_SCANCODE_INTERNATIONAL8     => Some("SDL_SCANCODE_INTERNATIONAL8")
        case SDL_SCANCODE_INTERNATIONAL9     => Some("SDL_SCANCODE_INTERNATIONAL9")
        case SDL_SCANCODE_LANG1              => Some("SDL_SCANCODE_LANG1")
        case SDL_SCANCODE_LANG2              => Some("SDL_SCANCODE_LANG2")
        case SDL_SCANCODE_LANG3              => Some("SDL_SCANCODE_LANG3")
        case SDL_SCANCODE_LANG4              => Some("SDL_SCANCODE_LANG4")
        case SDL_SCANCODE_LANG5              => Some("SDL_SCANCODE_LANG5")
        case SDL_SCANCODE_LANG6              => Some("SDL_SCANCODE_LANG6")
        case SDL_SCANCODE_LANG7              => Some("SDL_SCANCODE_LANG7")
        case SDL_SCANCODE_LANG8              => Some("SDL_SCANCODE_LANG8")
        case SDL_SCANCODE_LANG9              => Some("SDL_SCANCODE_LANG9")
        case SDL_SCANCODE_ALTERASE           => Some("SDL_SCANCODE_ALTERASE")
        case SDL_SCANCODE_SYSREQ             => Some("SDL_SCANCODE_SYSREQ")
        case SDL_SCANCODE_CANCEL             => Some("SDL_SCANCODE_CANCEL")
        case SDL_SCANCODE_CLEAR              => Some("SDL_SCANCODE_CLEAR")
        case SDL_SCANCODE_PRIOR              => Some("SDL_SCANCODE_PRIOR")
        case SDL_SCANCODE_RETURN2            => Some("SDL_SCANCODE_RETURN2")
        case SDL_SCANCODE_SEPARATOR          => Some("SDL_SCANCODE_SEPARATOR")
        case SDL_SCANCODE_OUT                => Some("SDL_SCANCODE_OUT")
        case SDL_SCANCODE_OPER               => Some("SDL_SCANCODE_OPER")
        case SDL_SCANCODE_CLEARAGAIN         => Some("SDL_SCANCODE_CLEARAGAIN")
        case SDL_SCANCODE_CRSEL              => Some("SDL_SCANCODE_CRSEL")
        case SDL_SCANCODE_EXSEL              => Some("SDL_SCANCODE_EXSEL")
        case SDL_SCANCODE_KP_00              => Some("SDL_SCANCODE_KP_00")
        case SDL_SCANCODE_KP_000             => Some("SDL_SCANCODE_KP_000")
        case SDL_SCANCODE_THOUSANDSSEPARATOR => Some("SDL_SCANCODE_THOUSANDSSEPARATOR")
        case SDL_SCANCODE_DECIMALSEPARATOR   => Some("SDL_SCANCODE_DECIMALSEPARATOR")
        case SDL_SCANCODE_CURRENCYUNIT       => Some("SDL_SCANCODE_CURRENCYUNIT")
        case SDL_SCANCODE_CURRENCYSUBUNIT    => Some("SDL_SCANCODE_CURRENCYSUBUNIT")
        case SDL_SCANCODE_KP_LEFTPAREN       => Some("SDL_SCANCODE_KP_LEFTPAREN")
        case SDL_SCANCODE_KP_RIGHTPAREN      => Some("SDL_SCANCODE_KP_RIGHTPAREN")
        case SDL_SCANCODE_KP_LEFTBRACE       => Some("SDL_SCANCODE_KP_LEFTBRACE")
        case SDL_SCANCODE_KP_RIGHTBRACE      => Some("SDL_SCANCODE_KP_RIGHTBRACE")
        case SDL_SCANCODE_KP_TAB             => Some("SDL_SCANCODE_KP_TAB")
        case SDL_SCANCODE_KP_BACKSPACE       => Some("SDL_SCANCODE_KP_BACKSPACE")
        case SDL_SCANCODE_KP_A               => Some("SDL_SCANCODE_KP_A")
        case SDL_SCANCODE_KP_B               => Some("SDL_SCANCODE_KP_B")
        case SDL_SCANCODE_KP_C               => Some("SDL_SCANCODE_KP_C")
        case SDL_SCANCODE_KP_D               => Some("SDL_SCANCODE_KP_D")
        case SDL_SCANCODE_KP_E               => Some("SDL_SCANCODE_KP_E")
        case SDL_SCANCODE_KP_F               => Some("SDL_SCANCODE_KP_F")
        case SDL_SCANCODE_KP_XOR             => Some("SDL_SCANCODE_KP_XOR")
        case SDL_SCANCODE_KP_POWER           => Some("SDL_SCANCODE_KP_POWER")
        case SDL_SCANCODE_KP_PERCENT         => Some("SDL_SCANCODE_KP_PERCENT")
        case SDL_SCANCODE_KP_LESS            => Some("SDL_SCANCODE_KP_LESS")
        case SDL_SCANCODE_KP_GREATER         => Some("SDL_SCANCODE_KP_GREATER")
        case SDL_SCANCODE_KP_AMPERSAND       => Some("SDL_SCANCODE_KP_AMPERSAND")
        case SDL_SCANCODE_KP_DBLAMPERSAND    => Some("SDL_SCANCODE_KP_DBLAMPERSAND")
        case SDL_SCANCODE_KP_VERTICALBAR     => Some("SDL_SCANCODE_KP_VERTICALBAR")
        case SDL_SCANCODE_KP_DBLVERTICALBAR  => Some("SDL_SCANCODE_KP_DBLVERTICALBAR")
        case SDL_SCANCODE_KP_COLON           => Some("SDL_SCANCODE_KP_COLON")
        case SDL_SCANCODE_KP_HASH            => Some("SDL_SCANCODE_KP_HASH")
        case SDL_SCANCODE_KP_SPACE           => Some("SDL_SCANCODE_KP_SPACE")
        case SDL_SCANCODE_KP_AT              => Some("SDL_SCANCODE_KP_AT")
        case SDL_SCANCODE_KP_EXCLAM          => Some("SDL_SCANCODE_KP_EXCLAM")
        case SDL_SCANCODE_KP_MEMSTORE        => Some("SDL_SCANCODE_KP_MEMSTORE")
        case SDL_SCANCODE_KP_MEMRECALL       => Some("SDL_SCANCODE_KP_MEMRECALL")
        case SDL_SCANCODE_KP_MEMCLEAR        => Some("SDL_SCANCODE_KP_MEMCLEAR")
        case SDL_SCANCODE_KP_MEMADD          => Some("SDL_SCANCODE_KP_MEMADD")
        case SDL_SCANCODE_KP_MEMSUBTRACT     => Some("SDL_SCANCODE_KP_MEMSUBTRACT")
        case SDL_SCANCODE_KP_MEMMULTIPLY     => Some("SDL_SCANCODE_KP_MEMMULTIPLY")
        case SDL_SCANCODE_KP_MEMDIVIDE       => Some("SDL_SCANCODE_KP_MEMDIVIDE")
        case SDL_SCANCODE_KP_PLUSMINUS       => Some("SDL_SCANCODE_KP_PLUSMINUS")
        case SDL_SCANCODE_KP_CLEAR           => Some("SDL_SCANCODE_KP_CLEAR")
        case SDL_SCANCODE_KP_CLEARENTRY      => Some("SDL_SCANCODE_KP_CLEARENTRY")
        case SDL_SCANCODE_KP_BINARY          => Some("SDL_SCANCODE_KP_BINARY")
        case SDL_SCANCODE_KP_OCTAL           => Some("SDL_SCANCODE_KP_OCTAL")
        case SDL_SCANCODE_KP_DECIMAL         => Some("SDL_SCANCODE_KP_DECIMAL")
        case SDL_SCANCODE_KP_HEXADECIMAL     => Some("SDL_SCANCODE_KP_HEXADECIMAL")
        case SDL_SCANCODE_LCTRL              => Some("SDL_SCANCODE_LCTRL")
        case SDL_SCANCODE_LSHIFT             => Some("SDL_SCANCODE_LSHIFT")
        case SDL_SCANCODE_LALT               => Some("SDL_SCANCODE_LALT")
        case SDL_SCANCODE_LGUI               => Some("SDL_SCANCODE_LGUI")
        case SDL_SCANCODE_RCTRL              => Some("SDL_SCANCODE_RCTRL")
        case SDL_SCANCODE_RSHIFT             => Some("SDL_SCANCODE_RSHIFT")
        case SDL_SCANCODE_RALT               => Some("SDL_SCANCODE_RALT")
        case SDL_SCANCODE_RGUI               => Some("SDL_SCANCODE_RGUI")
        case SDL_SCANCODE_MODE               => Some("SDL_SCANCODE_MODE")
        case SDL_SCANCODE_AUDIONEXT          => Some("SDL_SCANCODE_AUDIONEXT")
        case SDL_SCANCODE_AUDIOPREV          => Some("SDL_SCANCODE_AUDIOPREV")
        case SDL_SCANCODE_AUDIOSTOP          => Some("SDL_SCANCODE_AUDIOSTOP")
        case SDL_SCANCODE_AUDIOPLAY          => Some("SDL_SCANCODE_AUDIOPLAY")
        case SDL_SCANCODE_AUDIOMUTE          => Some("SDL_SCANCODE_AUDIOMUTE")
        case SDL_SCANCODE_MEDIASELECT        => Some("SDL_SCANCODE_MEDIASELECT")
        case SDL_SCANCODE_WWW                => Some("SDL_SCANCODE_WWW")
        case SDL_SCANCODE_MAIL               => Some("SDL_SCANCODE_MAIL")
        case SDL_SCANCODE_CALCULATOR         => Some("SDL_SCANCODE_CALCULATOR")
        case SDL_SCANCODE_COMPUTER           => Some("SDL_SCANCODE_COMPUTER")
        case SDL_SCANCODE_AC_SEARCH          => Some("SDL_SCANCODE_AC_SEARCH")
        case SDL_SCANCODE_AC_HOME            => Some("SDL_SCANCODE_AC_HOME")
        case SDL_SCANCODE_AC_BACK            => Some("SDL_SCANCODE_AC_BACK")
        case SDL_SCANCODE_AC_FORWARD         => Some("SDL_SCANCODE_AC_FORWARD")
        case SDL_SCANCODE_AC_STOP            => Some("SDL_SCANCODE_AC_STOP")
        case SDL_SCANCODE_AC_REFRESH         => Some("SDL_SCANCODE_AC_REFRESH")
        case SDL_SCANCODE_AC_BOOKMARKS       => Some("SDL_SCANCODE_AC_BOOKMARKS")
        case SDL_SCANCODE_BRIGHTNESSDOWN     => Some("SDL_SCANCODE_BRIGHTNESSDOWN")
        case SDL_SCANCODE_BRIGHTNESSUP       => Some("SDL_SCANCODE_BRIGHTNESSUP")
        case SDL_SCANCODE_DISPLAYSWITCH      => Some("SDL_SCANCODE_DISPLAYSWITCH")
        case SDL_SCANCODE_KBDILLUMTOGGLE     => Some("SDL_SCANCODE_KBDILLUMTOGGLE")
        case SDL_SCANCODE_KBDILLUMDOWN       => Some("SDL_SCANCODE_KBDILLUMDOWN")
        case SDL_SCANCODE_KBDILLUMUP         => Some("SDL_SCANCODE_KBDILLUMUP")
        case SDL_SCANCODE_EJECT              => Some("SDL_SCANCODE_EJECT")
        case SDL_SCANCODE_SLEEP              => Some("SDL_SCANCODE_SLEEP")
        case SDL_SCANCODE_APP1               => Some("SDL_SCANCODE_APP1")
        case SDL_SCANCODE_APP2               => Some("SDL_SCANCODE_APP2")
        case SDL_SCANCODE_AUDIOREWIND        => Some("SDL_SCANCODE_AUDIOREWIND")
        case SDL_SCANCODE_AUDIOFASTFORWARD   => Some("SDL_SCANCODE_AUDIOFASTFORWARD")
        case SDL_SCANCODE_SOFTLEFT           => Some("SDL_SCANCODE_SOFTLEFT")
        case SDL_SCANCODE_SOFTRIGHT          => Some("SDL_SCANCODE_SOFTRIGHT")
        case SDL_SCANCODE_CALL               => Some("SDL_SCANCODE_CALL")
        case SDL_SCANCODE_ENDCALL            => Some("SDL_SCANCODE_ENDCALL")
        case SDL_NUM_SCANCODES               => Some("SDL_NUM_SCANCODES")
        case _                               => None
    extension (a: SDL_Scancode)
      inline def &(b: SDL_Scancode): SDL_Scancode = a & b
      inline def |(b: SDL_Scancode): SDL_Scancode = a | b
      inline def is(b: SDL_Scancode): Boolean     = (a & b) == b

  /** [bindgen] header: ./SDL_sensor.h
    */
  opaque type SDL_SensorType = CInt
  object SDL_SensorType extends CEnum[SDL_SensorType]:
    given _tag: Tag[SDL_SensorType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_SensorType = a
    val SDL_SENSOR_INVALID                            = define(-1)
    val SDL_SENSOR_UNKNOWN                            = define(0)
    val SDL_SENSOR_ACCEL                              = define(1)
    val SDL_SENSOR_GYRO                               = define(2)
    val SDL_SENSOR_ACCEL_L                            = define(3)
    val SDL_SENSOR_GYRO_L                             = define(4)
    val SDL_SENSOR_ACCEL_R                            = define(5)
    val SDL_SENSOR_GYRO_R                             = define(6)
    inline def getName(inline value: SDL_SensorType): Option[String] =
      inline value match
        case SDL_SENSOR_INVALID => Some("SDL_SENSOR_INVALID")
        case SDL_SENSOR_UNKNOWN => Some("SDL_SENSOR_UNKNOWN")
        case SDL_SENSOR_ACCEL   => Some("SDL_SENSOR_ACCEL")
        case SDL_SENSOR_GYRO    => Some("SDL_SENSOR_GYRO")
        case SDL_SENSOR_ACCEL_L => Some("SDL_SENSOR_ACCEL_L")
        case SDL_SENSOR_GYRO_L  => Some("SDL_SENSOR_GYRO_L")
        case SDL_SENSOR_ACCEL_R => Some("SDL_SENSOR_ACCEL_R")
        case SDL_SENSOR_GYRO_R  => Some("SDL_SENSOR_GYRO_R")
        case _                  => None
    extension (a: SDL_SensorType)
      inline def &(b: SDL_SensorType): SDL_SensorType = a & b
      inline def |(b: SDL_SensorType): SDL_SensorType = a | b
      inline def is(b: SDL_SensorType): Boolean       = (a & b) == b

  /** Cursor types for SDL_CreateSystemCursor().
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  opaque type SDL_SystemCursor = CInt
  object SDL_SystemCursor extends CEnum[SDL_SystemCursor]:
    given _tag: Tag[SDL_SystemCursor]                   = Tag.Int
    inline def define(inline a: CInt): SDL_SystemCursor = a
    val SDL_SYSTEM_CURSOR_ARROW                         = define(0)
    val SDL_SYSTEM_CURSOR_IBEAM                         = define(1)
    val SDL_SYSTEM_CURSOR_WAIT                          = define(2)
    val SDL_SYSTEM_CURSOR_CROSSHAIR                     = define(3)
    val SDL_SYSTEM_CURSOR_WAITARROW                     = define(4)
    val SDL_SYSTEM_CURSOR_SIZENWSE                      = define(5)
    val SDL_SYSTEM_CURSOR_SIZENESW                      = define(6)
    val SDL_SYSTEM_CURSOR_SIZEWE                        = define(7)
    val SDL_SYSTEM_CURSOR_SIZENS                        = define(8)
    val SDL_SYSTEM_CURSOR_SIZEALL                       = define(9)
    val SDL_SYSTEM_CURSOR_NO                            = define(10)
    val SDL_SYSTEM_CURSOR_HAND                          = define(11)
    val SDL_NUM_SYSTEM_CURSORS                          = define(12)
    inline def getName(inline value: SDL_SystemCursor): Option[String] =
      inline value match
        case SDL_SYSTEM_CURSOR_ARROW     => Some("SDL_SYSTEM_CURSOR_ARROW")
        case SDL_SYSTEM_CURSOR_IBEAM     => Some("SDL_SYSTEM_CURSOR_IBEAM")
        case SDL_SYSTEM_CURSOR_WAIT      => Some("SDL_SYSTEM_CURSOR_WAIT")
        case SDL_SYSTEM_CURSOR_CROSSHAIR => Some("SDL_SYSTEM_CURSOR_CROSSHAIR")
        case SDL_SYSTEM_CURSOR_WAITARROW => Some("SDL_SYSTEM_CURSOR_WAITARROW")
        case SDL_SYSTEM_CURSOR_SIZENWSE  => Some("SDL_SYSTEM_CURSOR_SIZENWSE")
        case SDL_SYSTEM_CURSOR_SIZENESW  => Some("SDL_SYSTEM_CURSOR_SIZENESW")
        case SDL_SYSTEM_CURSOR_SIZEWE    => Some("SDL_SYSTEM_CURSOR_SIZEWE")
        case SDL_SYSTEM_CURSOR_SIZENS    => Some("SDL_SYSTEM_CURSOR_SIZENS")
        case SDL_SYSTEM_CURSOR_SIZEALL   => Some("SDL_SYSTEM_CURSOR_SIZEALL")
        case SDL_SYSTEM_CURSOR_NO        => Some("SDL_SYSTEM_CURSOR_NO")
        case SDL_SYSTEM_CURSOR_HAND      => Some("SDL_SYSTEM_CURSOR_HAND")
        case SDL_NUM_SYSTEM_CURSORS      => Some("SDL_NUM_SYSTEM_CURSORS")
        case _                           => None
    extension (a: SDL_SystemCursor)
      inline def &(b: SDL_SystemCursor): SDL_SystemCursor = a & b
      inline def |(b: SDL_SystemCursor): SDL_SystemCursor = a | b
      inline def is(b: SDL_SystemCursor): Boolean         = (a & b) == b

  /** The access pattern allowed for a texture.
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_TextureAccess = CInt
  object SDL_TextureAccess extends CEnum[SDL_TextureAccess]:
    given _tag: Tag[SDL_TextureAccess]                   = Tag.Int
    inline def define(inline a: CInt): SDL_TextureAccess = a
    val SDL_TEXTUREACCESS_STATIC                         = define(0)
    val SDL_TEXTUREACCESS_STREAMING                      = define(1)
    val SDL_TEXTUREACCESS_TARGET                         = define(2)
    inline def getName(inline value: SDL_TextureAccess): Option[String] =
      inline value match
        case SDL_TEXTUREACCESS_STATIC    => Some("SDL_TEXTUREACCESS_STATIC")
        case SDL_TEXTUREACCESS_STREAMING => Some("SDL_TEXTUREACCESS_STREAMING")
        case SDL_TEXTUREACCESS_TARGET    => Some("SDL_TEXTUREACCESS_TARGET")
        case _                           => None
    extension (a: SDL_TextureAccess)
      inline def &(b: SDL_TextureAccess): SDL_TextureAccess = a & b
      inline def |(b: SDL_TextureAccess): SDL_TextureAccess = a | b
      inline def is(b: SDL_TextureAccess): Boolean          = (a & b) == b

  /** The texture channel modulation used in SDL_RenderCopy().
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_TextureModulate = CInt
  object SDL_TextureModulate extends CEnum[SDL_TextureModulate]:
    given _tag: Tag[SDL_TextureModulate]                   = Tag.Int
    inline def define(inline a: CInt): SDL_TextureModulate = a
    val SDL_TEXTUREMODULATE_NONE                           = define(0)
    val SDL_TEXTUREMODULATE_COLOR                          = define(1)
    val SDL_TEXTUREMODULATE_ALPHA                          = define(2)
    inline def getName(inline value: SDL_TextureModulate): Option[String] =
      inline value match
        case SDL_TEXTUREMODULATE_NONE  => Some("SDL_TEXTUREMODULATE_NONE")
        case SDL_TEXTUREMODULATE_COLOR => Some("SDL_TEXTUREMODULATE_COLOR")
        case SDL_TEXTUREMODULATE_ALPHA => Some("SDL_TEXTUREMODULATE_ALPHA")
        case _                         => None
    extension (a: SDL_TextureModulate)
      inline def &(b: SDL_TextureModulate): SDL_TextureModulate = a & b
      inline def |(b: SDL_TextureModulate): SDL_TextureModulate = a | b
      inline def is(b: SDL_TextureModulate): Boolean            = (a & b) == b

  /** The SDL thread priority.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  opaque type SDL_ThreadPriority = CInt
  object SDL_ThreadPriority extends CEnum[SDL_ThreadPriority]:
    given _tag: Tag[SDL_ThreadPriority]                   = Tag.Int
    inline def define(inline a: CInt): SDL_ThreadPriority = a
    val SDL_THREAD_PRIORITY_LOW                           = define(0)
    val SDL_THREAD_PRIORITY_NORMAL                        = define(1)
    val SDL_THREAD_PRIORITY_HIGH                          = define(2)
    val SDL_THREAD_PRIORITY_TIME_CRITICAL                 = define(3)
    inline def getName(inline value: SDL_ThreadPriority): Option[String] =
      inline value match
        case SDL_THREAD_PRIORITY_LOW           => Some("SDL_THREAD_PRIORITY_LOW")
        case SDL_THREAD_PRIORITY_NORMAL        => Some("SDL_THREAD_PRIORITY_NORMAL")
        case SDL_THREAD_PRIORITY_HIGH          => Some("SDL_THREAD_PRIORITY_HIGH")
        case SDL_THREAD_PRIORITY_TIME_CRITICAL => Some("SDL_THREAD_PRIORITY_TIME_CRITICAL")
        case _                                 => None
    extension (a: SDL_ThreadPriority)
      inline def &(b: SDL_ThreadPriority): SDL_ThreadPriority = a & b
      inline def |(b: SDL_ThreadPriority): SDL_ThreadPriority = a | b
      inline def is(b: SDL_ThreadPriority): Boolean           = (a & b) == b

  /** [bindgen] header: ./SDL_touch.h
    */
  opaque type SDL_TouchDeviceType = CInt
  object SDL_TouchDeviceType extends CEnum[SDL_TouchDeviceType]:
    given _tag: Tag[SDL_TouchDeviceType]                   = Tag.Int
    inline def define(inline a: CInt): SDL_TouchDeviceType = a
    val SDL_TOUCH_DEVICE_INVALID                           = define(-1)
    val SDL_TOUCH_DEVICE_DIRECT                            = define(0)
    val SDL_TOUCH_DEVICE_INDIRECT_ABSOLUTE                 = define(1)
    val SDL_TOUCH_DEVICE_INDIRECT_RELATIVE                 = define(2)
    inline def getName(inline value: SDL_TouchDeviceType): Option[String] =
      inline value match
        case SDL_TOUCH_DEVICE_INVALID           => Some("SDL_TOUCH_DEVICE_INVALID")
        case SDL_TOUCH_DEVICE_DIRECT            => Some("SDL_TOUCH_DEVICE_DIRECT")
        case SDL_TOUCH_DEVICE_INDIRECT_ABSOLUTE => Some("SDL_TOUCH_DEVICE_INDIRECT_ABSOLUTE")
        case SDL_TOUCH_DEVICE_INDIRECT_RELATIVE => Some("SDL_TOUCH_DEVICE_INDIRECT_RELATIVE")
        case _                                  => None
    extension (a: SDL_TouchDeviceType)
      inline def &(b: SDL_TouchDeviceType): SDL_TouchDeviceType = a & b
      inline def |(b: SDL_TouchDeviceType): SDL_TouchDeviceType = a | b
      inline def is(b: SDL_TouchDeviceType): Boolean            = (a & b) == b

  /** Event subtype for window events
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_WindowEventID = CInt
  object SDL_WindowEventID extends CEnum[SDL_WindowEventID]:
    given _tag: Tag[SDL_WindowEventID]                   = Tag.Int
    inline def define(inline a: CInt): SDL_WindowEventID = a
    val SDL_WINDOWEVENT_NONE                             = define(0)
    val SDL_WINDOWEVENT_SHOWN                            = define(1)
    val SDL_WINDOWEVENT_HIDDEN                           = define(2)
    val SDL_WINDOWEVENT_EXPOSED                          = define(3)
    val SDL_WINDOWEVENT_MOVED                            = define(4)
    val SDL_WINDOWEVENT_RESIZED                          = define(5)
    val SDL_WINDOWEVENT_SIZE_CHANGED                     = define(6)
    val SDL_WINDOWEVENT_MINIMIZED                        = define(7)
    val SDL_WINDOWEVENT_MAXIMIZED                        = define(8)
    val SDL_WINDOWEVENT_RESTORED                         = define(9)
    val SDL_WINDOWEVENT_ENTER                            = define(10)
    val SDL_WINDOWEVENT_LEAVE                            = define(11)
    val SDL_WINDOWEVENT_FOCUS_GAINED                     = define(12)
    val SDL_WINDOWEVENT_FOCUS_LOST                       = define(13)
    val SDL_WINDOWEVENT_CLOSE                            = define(14)
    val SDL_WINDOWEVENT_TAKE_FOCUS                       = define(15)
    val SDL_WINDOWEVENT_HIT_TEST                         = define(16)
    val SDL_WINDOWEVENT_ICCPROF_CHANGED                  = define(17)
    val SDL_WINDOWEVENT_DISPLAY_CHANGED                  = define(18)
    inline def getName(inline value: SDL_WindowEventID): Option[String] =
      inline value match
        case SDL_WINDOWEVENT_NONE            => Some("SDL_WINDOWEVENT_NONE")
        case SDL_WINDOWEVENT_SHOWN           => Some("SDL_WINDOWEVENT_SHOWN")
        case SDL_WINDOWEVENT_HIDDEN          => Some("SDL_WINDOWEVENT_HIDDEN")
        case SDL_WINDOWEVENT_EXPOSED         => Some("SDL_WINDOWEVENT_EXPOSED")
        case SDL_WINDOWEVENT_MOVED           => Some("SDL_WINDOWEVENT_MOVED")
        case SDL_WINDOWEVENT_RESIZED         => Some("SDL_WINDOWEVENT_RESIZED")
        case SDL_WINDOWEVENT_SIZE_CHANGED    => Some("SDL_WINDOWEVENT_SIZE_CHANGED")
        case SDL_WINDOWEVENT_MINIMIZED       => Some("SDL_WINDOWEVENT_MINIMIZED")
        case SDL_WINDOWEVENT_MAXIMIZED       => Some("SDL_WINDOWEVENT_MAXIMIZED")
        case SDL_WINDOWEVENT_RESTORED        => Some("SDL_WINDOWEVENT_RESTORED")
        case SDL_WINDOWEVENT_ENTER           => Some("SDL_WINDOWEVENT_ENTER")
        case SDL_WINDOWEVENT_LEAVE           => Some("SDL_WINDOWEVENT_LEAVE")
        case SDL_WINDOWEVENT_FOCUS_GAINED    => Some("SDL_WINDOWEVENT_FOCUS_GAINED")
        case SDL_WINDOWEVENT_FOCUS_LOST      => Some("SDL_WINDOWEVENT_FOCUS_LOST")
        case SDL_WINDOWEVENT_CLOSE           => Some("SDL_WINDOWEVENT_CLOSE")
        case SDL_WINDOWEVENT_TAKE_FOCUS      => Some("SDL_WINDOWEVENT_TAKE_FOCUS")
        case SDL_WINDOWEVENT_HIT_TEST        => Some("SDL_WINDOWEVENT_HIT_TEST")
        case SDL_WINDOWEVENT_ICCPROF_CHANGED => Some("SDL_WINDOWEVENT_ICCPROF_CHANGED")
        case SDL_WINDOWEVENT_DISPLAY_CHANGED => Some("SDL_WINDOWEVENT_DISPLAY_CHANGED")
        case _                               => None
    extension (a: SDL_WindowEventID)
      inline def &(b: SDL_WindowEventID): SDL_WindowEventID = a & b
      inline def |(b: SDL_WindowEventID): SDL_WindowEventID = a | b
      inline def is(b: SDL_WindowEventID): Boolean          = (a & b) == b

  /** The flags on a window
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_WindowFlags = CInt
  object SDL_WindowFlags extends CEnum[SDL_WindowFlags]:
    given _tag: Tag[SDL_WindowFlags]                   = Tag.Int
    inline def define(inline a: CInt): SDL_WindowFlags = a
    val SDL_WINDOW_FULLSCREEN                          = define(1)
    val SDL_WINDOW_OPENGL                              = define(2)
    val SDL_WINDOW_SHOWN                               = define(4)
    val SDL_WINDOW_HIDDEN                              = define(8)
    val SDL_WINDOW_BORDERLESS                          = define(16)
    val SDL_WINDOW_RESIZABLE                           = define(32)
    val SDL_WINDOW_MINIMIZED                           = define(64)
    val SDL_WINDOW_MAXIMIZED                           = define(128)
    val SDL_WINDOW_MOUSE_GRABBED                       = define(256)
    val SDL_WINDOW_INPUT_FOCUS                         = define(512)
    val SDL_WINDOW_MOUSE_FOCUS                         = define(1024)
    val SDL_WINDOW_FULLSCREEN_DESKTOP                  = define(4097)
    val SDL_WINDOW_FOREIGN                             = define(2048)
    val SDL_WINDOW_ALLOW_HIGHDPI                       = define(8192)
    val SDL_WINDOW_MOUSE_CAPTURE                       = define(16384)
    val SDL_WINDOW_ALWAYS_ON_TOP                       = define(32768)
    val SDL_WINDOW_SKIP_TASKBAR                        = define(65536)
    val SDL_WINDOW_UTILITY                             = define(131072)
    val SDL_WINDOW_TOOLTIP                             = define(262144)
    val SDL_WINDOW_POPUP_MENU                          = define(524288)
    val SDL_WINDOW_KEYBOARD_GRABBED                    = define(1048576)
    val SDL_WINDOW_VULKAN                              = define(268435456)
    val SDL_WINDOW_METAL                               = define(536870912)
    val SDL_WINDOW_INPUT_GRABBED                       = define(256)
    inline def getName(inline value: SDL_WindowFlags): Option[String] =
      inline value match
        case SDL_WINDOW_FULLSCREEN         => Some("SDL_WINDOW_FULLSCREEN")
        case SDL_WINDOW_OPENGL             => Some("SDL_WINDOW_OPENGL")
        case SDL_WINDOW_SHOWN              => Some("SDL_WINDOW_SHOWN")
        case SDL_WINDOW_HIDDEN             => Some("SDL_WINDOW_HIDDEN")
        case SDL_WINDOW_BORDERLESS         => Some("SDL_WINDOW_BORDERLESS")
        case SDL_WINDOW_RESIZABLE          => Some("SDL_WINDOW_RESIZABLE")
        case SDL_WINDOW_MINIMIZED          => Some("SDL_WINDOW_MINIMIZED")
        case SDL_WINDOW_MAXIMIZED          => Some("SDL_WINDOW_MAXIMIZED")
        case SDL_WINDOW_MOUSE_GRABBED      => Some("SDL_WINDOW_MOUSE_GRABBED")
        case SDL_WINDOW_INPUT_FOCUS        => Some("SDL_WINDOW_INPUT_FOCUS")
        case SDL_WINDOW_MOUSE_FOCUS        => Some("SDL_WINDOW_MOUSE_FOCUS")
        case SDL_WINDOW_FULLSCREEN_DESKTOP => Some("SDL_WINDOW_FULLSCREEN_DESKTOP")
        case SDL_WINDOW_FOREIGN            => Some("SDL_WINDOW_FOREIGN")
        case SDL_WINDOW_ALLOW_HIGHDPI      => Some("SDL_WINDOW_ALLOW_HIGHDPI")
        case SDL_WINDOW_MOUSE_CAPTURE      => Some("SDL_WINDOW_MOUSE_CAPTURE")
        case SDL_WINDOW_ALWAYS_ON_TOP      => Some("SDL_WINDOW_ALWAYS_ON_TOP")
        case SDL_WINDOW_SKIP_TASKBAR       => Some("SDL_WINDOW_SKIP_TASKBAR")
        case SDL_WINDOW_UTILITY            => Some("SDL_WINDOW_UTILITY")
        case SDL_WINDOW_TOOLTIP            => Some("SDL_WINDOW_TOOLTIP")
        case SDL_WINDOW_POPUP_MENU         => Some("SDL_WINDOW_POPUP_MENU")
        case SDL_WINDOW_KEYBOARD_GRABBED   => Some("SDL_WINDOW_KEYBOARD_GRABBED")
        case SDL_WINDOW_VULKAN             => Some("SDL_WINDOW_VULKAN")
        case SDL_WINDOW_METAL              => Some("SDL_WINDOW_METAL")
        case SDL_WINDOW_INPUT_GRABBED      => Some("SDL_WINDOW_INPUT_GRABBED")
        case _                             => None
    extension (a: SDL_WindowFlags)
      inline def &(b: SDL_WindowFlags): SDL_WindowFlags = a & b
      inline def |(b: SDL_WindowFlags): SDL_WindowFlags = a | b
      inline def is(b: SDL_WindowFlags): Boolean        = (a & b) == b

  /** The formula used for converting between YUV and RGB
    *
    * [bindgen] header: ./SDL_surface.h
    */
  opaque type SDL_YUV_CONVERSION_MODE = CInt
  object SDL_YUV_CONVERSION_MODE extends CEnum[SDL_YUV_CONVERSION_MODE]:
    given _tag: Tag[SDL_YUV_CONVERSION_MODE]                   = Tag.Int
    inline def define(inline a: CInt): SDL_YUV_CONVERSION_MODE = a
    val SDL_YUV_CONVERSION_JPEG                                = define(0)
    val SDL_YUV_CONVERSION_BT601                               = define(1)
    val SDL_YUV_CONVERSION_BT709                               = define(2)
    val SDL_YUV_CONVERSION_AUTOMATIC                           = define(3)
    inline def getName(inline value: SDL_YUV_CONVERSION_MODE): Option[String] =
      inline value match
        case SDL_YUV_CONVERSION_JPEG      => Some("SDL_YUV_CONVERSION_JPEG")
        case SDL_YUV_CONVERSION_BT601     => Some("SDL_YUV_CONVERSION_BT601")
        case SDL_YUV_CONVERSION_BT709     => Some("SDL_YUV_CONVERSION_BT709")
        case SDL_YUV_CONVERSION_AUTOMATIC => Some("SDL_YUV_CONVERSION_AUTOMATIC")
        case _                            => None
    extension (a: SDL_YUV_CONVERSION_MODE)
      inline def &(b: SDL_YUV_CONVERSION_MODE): SDL_YUV_CONVERSION_MODE = a & b
      inline def |(b: SDL_YUV_CONVERSION_MODE): SDL_YUV_CONVERSION_MODE = a | b
      inline def is(b: SDL_YUV_CONVERSION_MODE): Boolean                = (a & b) == b

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_bool = CInt
  object SDL_bool extends CEnum[SDL_bool]:
    given _tag: Tag[SDL_bool]                   = Tag.Int
    inline def define(inline a: CInt): SDL_bool = a
    val SDL_FALSE                               = define(0)
    val SDL_TRUE                                = define(1)
    inline def getName(inline value: SDL_bool): Option[String] =
      inline value match
        case SDL_FALSE => Some("SDL_FALSE")
        case SDL_TRUE  => Some("SDL_TRUE")
        case _         => None
    extension (a: SDL_bool)
      inline def &(b: SDL_bool): SDL_bool = a & b
      inline def |(b: SDL_bool): SDL_bool = a | b
      inline def is(b: SDL_bool): Boolean = (a & b) == b

  /** [bindgen] header: ./SDL_error.h
    */
  opaque type SDL_errorcode = CInt
  object SDL_errorcode extends CEnum[SDL_errorcode]:
    given _tag: Tag[SDL_errorcode]                   = Tag.Int
    inline def define(inline a: CInt): SDL_errorcode = a
    val SDL_ENOMEM                                   = define(0)
    val SDL_EFREAD                                   = define(1)
    val SDL_EFWRITE                                  = define(2)
    val SDL_EFSEEK                                   = define(3)
    val SDL_UNSUPPORTED                              = define(4)
    val SDL_LASTERROR                                = define(5)
    inline def getName(inline value: SDL_errorcode): Option[String] =
      inline value match
        case SDL_ENOMEM      => Some("SDL_ENOMEM")
        case SDL_EFREAD      => Some("SDL_EFREAD")
        case SDL_EFWRITE     => Some("SDL_EFWRITE")
        case SDL_EFSEEK      => Some("SDL_EFSEEK")
        case SDL_UNSUPPORTED => Some("SDL_UNSUPPORTED")
        case SDL_LASTERROR   => Some("SDL_LASTERROR")
        case _               => None
    extension (a: SDL_errorcode)
      inline def &(b: SDL_errorcode): SDL_errorcode = a & b
      inline def |(b: SDL_errorcode): SDL_errorcode = a | b
      inline def is(b: SDL_errorcode): Boolean      = (a & b) == b

  /** [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_eventaction = CInt
  object SDL_eventaction extends CEnum[SDL_eventaction]:
    given _tag: Tag[SDL_eventaction]                   = Tag.Int
    inline def define(inline a: CInt): SDL_eventaction = a
    val SDL_ADDEVENT                                   = define(0)
    val SDL_PEEKEVENT                                  = define(1)
    val SDL_GETEVENT                                   = define(2)
    inline def getName(inline value: SDL_eventaction): Option[String] =
      inline value match
        case SDL_ADDEVENT  => Some("SDL_ADDEVENT")
        case SDL_PEEKEVENT => Some("SDL_PEEKEVENT")
        case SDL_GETEVENT  => Some("SDL_GETEVENT")
        case _             => None
    extension (a: SDL_eventaction)
      inline def &(b: SDL_eventaction): SDL_eventaction = a & b
      inline def |(b: SDL_eventaction): SDL_eventaction = a | b
      inline def is(b: SDL_eventaction): Boolean        = (a & b) == b

  /** An enum denoting the specific type of contents present in an SDL_WindowShapeParams union.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  opaque type WindowShapeMode = CInt
  object WindowShapeMode extends CEnum[WindowShapeMode]:
    given _tag: Tag[WindowShapeMode]                   = Tag.Int
    inline def define(inline a: CInt): WindowShapeMode = a
    val ShapeModeDefault                               = define(0)
    val ShapeModeBinarizeAlpha                         = define(1)
    val ShapeModeReverseBinarizeAlpha                  = define(2)
    val ShapeModeColorKey                              = define(3)
    inline def getName(inline value: WindowShapeMode): Option[String] =
      inline value match
        case ShapeModeDefault              => Some("ShapeModeDefault")
        case ShapeModeBinarizeAlpha        => Some("ShapeModeBinarizeAlpha")
        case ShapeModeReverseBinarizeAlpha => Some("ShapeModeReverseBinarizeAlpha")
        case ShapeModeColorKey             => Some("ShapeModeColorKey")
        case _                             => None
    extension (a: WindowShapeMode)
      inline def &(b: WindowShapeMode): WindowShapeMode = a & b
      inline def |(b: WindowShapeMode): WindowShapeMode = a | b
      inline def is(b: WindowShapeMode): Boolean        = (a & b) == b

object aliases:
  import _root_.sdl2.enumerations.*
  import _root_.sdl2.predef.*
  import _root_.sdl2.aliases.*
  import _root_.sdl2.structs.*
  import _root_.sdl2.unions.*

  /** A callback that fires when an SDL assertion fails.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  opaque type SDL_AssertionHandler = CFuncPtr2[Ptr[SDL_AssertData], Ptr[Byte], SDL_AssertState]
  object SDL_AssertionHandler:
    given _tag: Tag[SDL_AssertionHandler] = Tag.materializeCFuncPtr2[Ptr[SDL_AssertData], Ptr[Byte], SDL_AssertState]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_AssertionHandler = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[Ptr[SDL_AssertData], Ptr[Byte], SDL_AssertState]): SDL_AssertionHandler = o
    extension (v: SDL_AssertionHandler)
      inline def value: CFuncPtr2[Ptr[SDL_AssertData], Ptr[Byte], SDL_AssertState] = v
      inline def toPtr: Ptr[?]                                                     = CFuncPtr.toPtr(v)

  /** This function is called when the audio device needs more data.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioCallback = CFuncPtr3[Ptr[Byte], Ptr[Uint8], CInt, Unit]
  object SDL_AudioCallback:
    given _tag: Tag[SDL_AudioCallback] = Tag.materializeCFuncPtr3[Ptr[Byte], Ptr[Uint8], CInt, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_AudioCallback = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr3[Ptr[Byte], Ptr[Uint8], CInt, Unit]): SDL_AudioCallback = o
    extension (v: SDL_AudioCallback)
      inline def value: CFuncPtr3[Ptr[Byte], Ptr[Uint8], CInt, Unit] = v
      inline def toPtr: Ptr[?]                                       = CFuncPtr.toPtr(v)

  /** SDL Audio Device IDs.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  type SDL_AudioDeviceID = Uint32
  object SDL_AudioDeviceID:
    given _tag: Tag[SDL_AudioDeviceID]                        = Uint32._tag
    inline def apply(inline o: Uint32): SDL_AudioDeviceID     = o
    extension (v: SDL_AudioDeviceID) inline def value: Uint32 = v

  /** [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioFilter = CFuncPtr2[Ptr[SDL_AudioCVT], SDL_AudioFormat, Unit]
  object SDL_AudioFilter:
    given _tag: Tag[SDL_AudioFilter] = Tag.materializeCFuncPtr2[Ptr[SDL_AudioCVT], SDL_AudioFormat, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_AudioFilter = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[Ptr[SDL_AudioCVT], SDL_AudioFormat, Unit]): SDL_AudioFilter = o
    extension (v: SDL_AudioFilter)
      inline def value: CFuncPtr2[Ptr[SDL_AudioCVT], SDL_AudioFormat, Unit] = v
      inline def toPtr: Ptr[?]                                              = CFuncPtr.toPtr(v)

  /** A function pointer used for callbacks that watch the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_EventFilter = CFuncPtr2[Ptr[Byte], Ptr[SDL_Event], CInt]
  object SDL_EventFilter:
    given _tag: Tag[SDL_EventFilter] = Tag.materializeCFuncPtr2[Ptr[Byte], Ptr[SDL_Event], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_EventFilter = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[Ptr[Byte], Ptr[SDL_Event], CInt]): SDL_EventFilter = o
    extension (v: SDL_EventFilter)
      inline def value: CFuncPtr2[Ptr[Byte], Ptr[SDL_Event], CInt] = v
      inline def toPtr: Ptr[?]                                     = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_touch.h
    */
  type SDL_FingerID = Sint64
  object SDL_FingerID:
    given _tag: Tag[SDL_FingerID]                        = Sint64._tag
    inline def apply(inline o: Sint64): SDL_FingerID     = o
    extension (v: SDL_FingerID) inline def value: Sint64 = v

  /** An opaque handle to an OpenGL context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_GLContext = Ptr[Byte]
  object SDL_GLContext:
    given _tag: Tag[SDL_GLContext]                           = Tag.Ptr(Tag.Byte)
    inline def apply(inline o: Ptr[Byte]): SDL_GLContext     = o
    extension (v: SDL_GLContext) inline def value: Ptr[Byte] = v

  /** [bindgen] header: ./SDL_gesture.h
    */
  type SDL_GestureID = Sint64
  object SDL_GestureID:
    given _tag: Tag[SDL_GestureID]                        = Sint64._tag
    inline def apply(inline o: Sint64): SDL_GestureID     = o
    extension (v: SDL_GestureID) inline def value: Sint64 = v

  /** Type definition of the hint callback function.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  opaque type SDL_HintCallback = CFuncPtr4[Ptr[Byte], CString, CString, CString, Unit]
  object SDL_HintCallback:
    given _tag: Tag[SDL_HintCallback] = Tag.materializeCFuncPtr4[Ptr[Byte], CString, CString, CString, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_HintCallback = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr4[Ptr[Byte], CString, CString, CString, Unit]): SDL_HintCallback = o
    extension (v: SDL_HintCallback)
      inline def value: CFuncPtr4[Ptr[Byte], CString, CString, CString, Unit] = v
      inline def toPtr: Ptr[?]                                                = CFuncPtr.toPtr(v)

  /** Callback used for hit-testing.
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_HitTest = CFuncPtr3[Ptr[SDL_Window], Ptr[SDL_Point], Ptr[Byte], SDL_HitTestResult]
  object SDL_HitTest:
    given _tag: Tag[SDL_HitTest] =
      Tag.materializeCFuncPtr3[Ptr[SDL_Window], Ptr[SDL_Point], Ptr[Byte], SDL_HitTestResult]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_HitTest = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr3[Ptr[SDL_Window], Ptr[SDL_Point], Ptr[Byte], SDL_HitTestResult]): SDL_HitTest =
      o
    extension (v: SDL_HitTest)
      inline def value: CFuncPtr3[Ptr[SDL_Window], Ptr[SDL_Point], Ptr[Byte], SDL_HitTestResult] = v
      inline def toPtr: Ptr[?]                                                                   = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_joystick.h
    */
  type SDL_JoystickGUID = SDL_GUID
  object SDL_JoystickGUID:
    given _tag: Tag[SDL_JoystickGUID]                          = SDL_GUID._tag
    inline def apply(inline o: SDL_GUID): SDL_JoystickGUID     = o
    extension (v: SDL_JoystickGUID) inline def value: SDL_GUID = v

  /** This is a unique ID for a joystick for the time it is connected to the system, and is never reused for the lifetime of the application. If the joystick is disconnected and reconnected, it will get a new ID.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  type SDL_JoystickID = Sint32
  object SDL_JoystickID:
    given _tag: Tag[SDL_JoystickID]                        = Sint32._tag
    inline def apply(inline o: Sint32): SDL_JoystickID     = o
    extension (v: SDL_JoystickID) inline def value: Sint32 = v

  /** The SDL virtual key representation.
    *
    * [bindgen] header: ./SDL_keycode.h
    */
  type SDL_Keycode = Sint32
  object SDL_Keycode:
    given _tag: Tag[SDL_Keycode]                        = Sint32._tag
    inline def apply(inline o: Sint32): SDL_Keycode     = o
    extension (v: SDL_Keycode) inline def value: Sint32 = v

  /** The prototype for the log output callback function.
    *
    * [bindgen] header: ./SDL_log.h
    */
  opaque type SDL_LogOutputFunction = CFuncPtr4[Ptr[Byte], CInt, SDL_LogPriority, CString, Unit]
  object SDL_LogOutputFunction:
    given _tag: Tag[SDL_LogOutputFunction] = Tag.materializeCFuncPtr4[Ptr[Byte], CInt, SDL_LogPriority, CString, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_LogOutputFunction = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr4[Ptr[Byte], CInt, SDL_LogPriority, CString, Unit]): SDL_LogOutputFunction = o
    extension (v: SDL_LogOutputFunction)
      inline def value: CFuncPtr4[Ptr[Byte], CInt, SDL_LogPriority, CString, Unit] = v
      inline def toPtr: Ptr[?]                                                     = CFuncPtr.toPtr(v)

  /** A handle to a CAMetalLayer-backed NSView (macOS) or UIView (iOS/tvOS).
    *
    * [bindgen] header: ./SDL_metal.h
    */
  opaque type SDL_MetalView = Ptr[Byte]
  object SDL_MetalView:
    given _tag: Tag[SDL_MetalView]                           = Tag.Ptr(Tag.Byte)
    inline def apply(inline o: Ptr[Byte]): SDL_MetalView     = o
    extension (v: SDL_MetalView) inline def value: Ptr[Byte] = v

  /** This is a unique ID for a sensor for the time it is connected to the system, and is never reused for the lifetime of the application.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  type SDL_SensorID = Sint32
  object SDL_SensorID:
    given _tag: Tag[SDL_SensorID]                        = Sint32._tag
    inline def apply(inline o: Sint32): SDL_SensorID     = o
    extension (v: SDL_SensorID) inline def value: Sint32 = v

  /** [bindgen] header: ./SDL_atomic.h
    */
  opaque type SDL_SpinLock = CInt
  object SDL_SpinLock:
    given _tag: Tag[SDL_SpinLock]                      = Tag.Int
    inline def apply(inline o: CInt): SDL_SpinLock     = o
    extension (v: SDL_SpinLock) inline def value: CInt = v

  /** [bindgen] header: ./SDL_thread.h
    */
  opaque type SDL_TLSID = CUnsignedInt
  object SDL_TLSID:
    given _tag: Tag[SDL_TLSID]                              = Tag.UInt
    inline def apply(inline o: CUnsignedInt): SDL_TLSID     = o
    extension (v: SDL_TLSID) inline def value: CUnsignedInt = v

  /** The function passed to SDL_CreateThread().
    *
    * [bindgen] header: ./SDL_thread.h
    */
  opaque type SDL_ThreadFunction = CFuncPtr1[Ptr[Byte], CInt]
  object SDL_ThreadFunction:
    given _tag: Tag[SDL_ThreadFunction]                             = Tag.materializeCFuncPtr1[Ptr[Byte], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_ThreadFunction = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], CInt]): SDL_ThreadFunction = o
    extension (v: SDL_ThreadFunction)
      inline def value: CFuncPtr1[Ptr[Byte], CInt] = v
      inline def toPtr: Ptr[?]                     = CFuncPtr.toPtr(v)

  /** Function prototype for the timer callback function.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  opaque type SDL_TimerCallback = CFuncPtr2[Uint32, Ptr[Byte], Uint32]
  object SDL_TimerCallback:
    given _tag: Tag[SDL_TimerCallback]                             = Tag.materializeCFuncPtr2[Uint32, Ptr[Byte], Uint32]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_TimerCallback = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[Uint32, Ptr[Byte], Uint32]): SDL_TimerCallback = o
    extension (v: SDL_TimerCallback)
      inline def value: CFuncPtr2[Uint32, Ptr[Byte], Uint32] = v
      inline def toPtr: Ptr[?]                               = CFuncPtr.toPtr(v)

  /** Definition of the timer ID type.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  opaque type SDL_TimerID = CInt
  object SDL_TimerID:
    given _tag: Tag[SDL_TimerID]                      = Tag.Int
    inline def apply(inline o: CInt): SDL_TimerID     = o
    extension (v: SDL_TimerID) inline def value: CInt = v

  /** [bindgen] header: ./SDL_touch.h
    */
  type SDL_TouchID = Sint64
  object SDL_TouchID:
    given _tag: Tag[SDL_TouchID]                        = Sint64._tag
    inline def apply(inline o: Sint64): SDL_TouchID     = o
    extension (v: SDL_TouchID) inline def value: Sint64 = v

  /** [bindgen] header: ./SDL_system.h
    */
  opaque type SDL_WindowsMessageHook = CFuncPtr5[Ptr[Byte], Ptr[Byte], CUnsignedInt, Uint64, Sint64, Unit]
  object SDL_WindowsMessageHook:
    given _tag: Tag[SDL_WindowsMessageHook] =
      Tag.materializeCFuncPtr5[Ptr[Byte], Ptr[Byte], CUnsignedInt, Uint64, Sint64, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_WindowsMessageHook = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(
        inline o: CFuncPtr5[Ptr[Byte], Ptr[Byte], CUnsignedInt, Uint64, Sint64, Unit]
    ): SDL_WindowsMessageHook = o
    extension (v: SDL_WindowsMessageHook)
      inline def value: CFuncPtr5[Ptr[Byte], Ptr[Byte], CUnsignedInt, Uint64, Sint64, Unit] = v
      inline def toPtr: Ptr[?]                                                              = CFuncPtr.toPtr(v)

  /** The type of function used for surface blitting functions.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  opaque type SDL_blit = CFuncPtr4[Ptr[SDL_Surface], Ptr[SDL_Rect], Ptr[SDL_Surface], Ptr[SDL_Rect], CInt]
  object SDL_blit:
    given _tag: Tag[SDL_blit] =
      Tag.materializeCFuncPtr4[Ptr[SDL_Surface], Ptr[SDL_Rect], Ptr[SDL_Surface], Ptr[SDL_Rect], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_blit = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(
        inline o: CFuncPtr4[Ptr[SDL_Surface], Ptr[SDL_Rect], Ptr[SDL_Surface], Ptr[SDL_Rect], CInt]
    ): SDL_blit = o
    extension (v: SDL_blit)
      inline def value: CFuncPtr4[Ptr[SDL_Surface], Ptr[SDL_Rect], Ptr[SDL_Surface], Ptr[SDL_Rect], CInt] = v
      inline def toPtr: Ptr[?] = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_calloc_func = CFuncPtr2[size_t, size_t, Ptr[Byte]]
  object SDL_calloc_func:
    given _tag: Tag[SDL_calloc_func]                             = Tag.materializeCFuncPtr2[size_t, size_t, Ptr[Byte]]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_calloc_func = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[size_t, size_t, Ptr[Byte]]): SDL_calloc_func = o
    extension (v: SDL_calloc_func)
      inline def value: CFuncPtr2[size_t, size_t, Ptr[Byte]] = v
      inline def toPtr: Ptr[?]                               = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_free_func = CFuncPtr1[Ptr[Byte], Unit]
  object SDL_free_func:
    given _tag: Tag[SDL_free_func]                             = Tag.materializeCFuncPtr1[Ptr[Byte], Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_free_func = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr1[Ptr[Byte], Unit]): SDL_free_func = o
    extension (v: SDL_free_func)
      inline def value: CFuncPtr1[Ptr[Byte], Unit] = v
      inline def toPtr: Ptr[?]                     = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_iconv_t = Ptr[_SDL_iconv_t]
  object SDL_iconv_t:
    given _tag: Tag[SDL_iconv_t]                                   = Tag.Ptr[_SDL_iconv_t](_SDL_iconv_t._tag)
    inline def apply(inline o: Ptr[_SDL_iconv_t]): SDL_iconv_t     = o
    extension (v: SDL_iconv_t) inline def value: Ptr[_SDL_iconv_t] = v

  /** The prototype for the application's main() function
    *
    * [bindgen] header: ./SDL_main.h
    */
  opaque type SDL_main_func = CFuncPtr2[CInt, Ptr[CString], CInt]
  object SDL_main_func:
    given _tag: Tag[SDL_main_func]                             = Tag.materializeCFuncPtr2[CInt, Ptr[CString], CInt]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_main_func = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[CInt, Ptr[CString], CInt]): SDL_main_func = o
    extension (v: SDL_main_func)
      inline def value: CFuncPtr2[CInt, Ptr[CString], CInt] = v
      inline def toPtr: Ptr[?]                              = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_malloc_func = CFuncPtr1[size_t, Ptr[Byte]]
  object SDL_malloc_func:
    given _tag: Tag[SDL_malloc_func]                             = Tag.materializeCFuncPtr1[size_t, Ptr[Byte]]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_malloc_func = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr1[size_t, Ptr[Byte]]): SDL_malloc_func = o
    extension (v: SDL_malloc_func)
      inline def value: CFuncPtr1[size_t, Ptr[Byte]] = v
      inline def toPtr: Ptr[?]                       = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type SDL_realloc_func = CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]
  object SDL_realloc_func:
    given _tag: Tag[SDL_realloc_func] = Tag.materializeCFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): SDL_realloc_func = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]]): SDL_realloc_func = o
    extension (v: SDL_realloc_func)
      inline def value: CFuncPtr2[Ptr[Byte], size_t, Ptr[Byte]] = v
      inline def toPtr: Ptr[?]                                  = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_thread.h [MANUAL]
    */
  opaque type SDL_threadID = CUnsignedLongInt
  object SDL_threadID:
    given _tag: Tag[SDL_threadID]                                  = Tag.USize
    inline def apply(inline o: CUnsignedLongInt): SDL_threadID     = o
    extension (v: SDL_threadID) inline def value: CUnsignedLongInt = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Sint16 = int16_t
  object Sint16:
    given _tag: Tag[Sint16]                         = int16_t._tag
    inline def apply(inline o: int16_t): Sint16     = o
    extension (v: Sint16) inline def value: int16_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Sint32 = int32_t
  object Sint32:
    given _tag: Tag[Sint32]                         = int32_t._tag
    inline def apply(inline o: int32_t): Sint32     = o
    extension (v: Sint32) inline def value: int32_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Sint64 = int64_t
  object Sint64:
    given _tag: Tag[Sint64]                         = int64_t._tag
    inline def apply(inline o: int64_t): Sint64     = o
    extension (v: Sint64) inline def value: int64_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Sint8 = int8_t
  object Sint8:
    given _tag: Tag[Sint8]                        = int8_t._tag
    inline def apply(inline o: int8_t): Sint8     = o
    extension (v: Sint8) inline def value: int8_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Uint16 = uint16_t
  object Uint16:
    given _tag: Tag[Uint16]                          = uint16_t._tag
    inline def apply(inline o: uint16_t): Uint16     = o
    extension (v: Uint16) inline def value: uint16_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Uint32 = uint32_t
  object Uint32:
    given _tag: Tag[Uint32]                          = uint32_t._tag
    inline def apply(inline o: uint32_t): Uint32     = o
    extension (v: Uint32) inline def value: uint32_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Uint64 = uint64_t
  object Uint64:
    given _tag: Tag[Uint64]                          = uint64_t._tag
    inline def apply(inline o: uint64_t): Uint64     = o
    extension (v: Uint64) inline def value: uint64_t = v

  /** [bindgen] header: ./SDL_stdinc.h
    */
  type Uint8 = uint8_t
  object Uint8:
    given _tag: Tag[Uint8]                         = uint8_t._tag
    inline def apply(inline o: uint8_t): Uint8     = o
    extension (v: Uint8) inline def value: uint8_t = v

  type int16_t = scala.Short
  object int16_t:
    val _tag: Tag[int16_t]                               = summon[Tag[scala.Short]]
    inline def apply(inline o: scala.Short): int16_t     = o
    extension (v: int16_t) inline def value: scala.Short = v

  type int32_t = scala.scalanative.unsafe.CInt
  object int32_t:
    val _tag: Tag[int32_t]                                                 = summon[Tag[scala.scalanative.unsafe.CInt]]
    inline def apply(inline o: scala.scalanative.unsafe.CInt): int32_t     = o
    extension (v: int32_t) inline def value: scala.scalanative.unsafe.CInt = v

  type int64_t = scala.Long
  object int64_t:
    val _tag: Tag[int64_t]                              = summon[Tag[scala.Long]]
    inline def apply(inline o: scala.Long): int64_t     = o
    extension (v: int64_t) inline def value: scala.Long = v

  type int8_t = scala.scalanative.unsafe.CChar
  object int8_t:
    val _tag: Tag[int8_t]                                                  = summon[Tag[scala.scalanative.unsafe.CChar]]
    inline def apply(inline o: scala.scalanative.unsafe.CChar): int8_t     = o
    extension (v: int8_t) inline def value: scala.scalanative.unsafe.CChar = v

  /** [bindgen] header: ./SDL_thread.h
    */
  opaque type pfnSDL_CurrentBeginThread = CFuncPtr6[Ptr[Byte], CUnsignedInt, CFuncPtr1[Ptr[Byte], CUnsignedInt], Ptr[
    Byte
  ], CUnsignedInt, Ptr[CUnsignedInt], uintptr_t]
  object pfnSDL_CurrentBeginThread:
    given _tag: Tag[pfnSDL_CurrentBeginThread] =
      Tag.materializeCFuncPtr6[Ptr[Byte], CUnsignedInt, CFuncPtr1[Ptr[Byte], CUnsignedInt], Ptr[
        Byte
      ], CUnsignedInt, Ptr[CUnsignedInt], uintptr_t]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): pfnSDL_CurrentBeginThread =
      CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(
        inline o: CFuncPtr6[Ptr[Byte], CUnsignedInt, CFuncPtr1[Ptr[Byte], CUnsignedInt], Ptr[Byte], CUnsignedInt, Ptr[
          CUnsignedInt
        ], uintptr_t]
    ): pfnSDL_CurrentBeginThread = o
    extension (v: pfnSDL_CurrentBeginThread)
      inline def value: CFuncPtr6[Ptr[Byte], CUnsignedInt, CFuncPtr1[Ptr[Byte], CUnsignedInt], Ptr[
        Byte
      ], CUnsignedInt, Ptr[CUnsignedInt], uintptr_t] = v
      inline def toPtr: Ptr[?] = CFuncPtr.toPtr(v)

  /** [bindgen] header: ./SDL_thread.h
    */
  opaque type pfnSDL_CurrentEndThread = CFuncPtr1[CUnsignedInt, Unit]
  object pfnSDL_CurrentEndThread:
    given _tag: Tag[pfnSDL_CurrentEndThread]                             = Tag.materializeCFuncPtr1[CUnsignedInt, Unit]
    inline def fromPtr(ptr: Ptr[Byte] | Ptr[?]): pfnSDL_CurrentEndThread = CFuncPtr.fromPtr(ptr.asInstanceOf[Ptr[Byte]])
    inline def apply(inline o: CFuncPtr1[CUnsignedInt, Unit]): pfnSDL_CurrentEndThread = o
    extension (v: pfnSDL_CurrentEndThread)
      inline def value: CFuncPtr1[CUnsignedInt, Unit] = v
      inline def toPtr: Ptr[?]                        = CFuncPtr.toPtr(v)

  type size_t = libc.stddef.size_t
  object size_t:
    val _tag: Tag[size_t]                                      = summon[Tag[libc.stddef.size_t]]
    inline def apply(inline o: libc.stddef.size_t): size_t     = o
    extension (v: size_t) inline def value: libc.stddef.size_t = v

  type uint16_t = scala.scalanative.unsigned.UShort
  object uint16_t:
    val _tag: Tag[uint16_t] = summon[Tag[scala.scalanative.unsigned.UShort]]
    inline def apply(inline o: scala.scalanative.unsigned.UShort): uint16_t     = o
    extension (v: uint16_t) inline def value: scala.scalanative.unsigned.UShort = v

  type uint32_t = scala.scalanative.unsigned.UInt
  object uint32_t:
    val _tag: Tag[uint32_t]                                               = summon[Tag[scala.scalanative.unsigned.UInt]]
    inline def apply(inline o: scala.scalanative.unsigned.UInt): uint32_t = o
    extension (v: uint32_t) inline def value: scala.scalanative.unsigned.UInt = v

  type uint64_t = scala.scalanative.unsigned.ULong
  object uint64_t:
    val _tag: Tag[uint64_t] = summon[Tag[scala.scalanative.unsigned.ULong]]
    inline def apply(inline o: scala.scalanative.unsigned.ULong): uint64_t     = o
    extension (v: uint64_t) inline def value: scala.scalanative.unsigned.ULong = v

  type uint8_t = scala.scalanative.unsigned.UByte
  object uint8_t:
    val _tag: Tag[uint8_t] = summon[Tag[scala.scalanative.unsigned.UByte]]
    inline def apply(inline o: scala.scalanative.unsigned.UByte): uint8_t     = o
    extension (v: uint8_t) inline def value: scala.scalanative.unsigned.UByte = v

  /** [bindgen] header: C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Tools\MSVC\14.29.30133\include\vadefs.h
    */
  opaque type uintptr_t = CUnsignedLongLong
  object uintptr_t:
    given _tag: Tag[uintptr_t]                                   = Tag.ULong
    inline def apply(inline o: CUnsignedLongLong): uintptr_t     = o
    extension (v: uintptr_t) inline def value: CUnsignedLongLong = v

  type va_list = unsafe.CVarArgList
  object va_list:
    val _tag: Tag[va_list]                                      = summon[Tag[unsafe.CVarArgList]]
    inline def apply(inline o: unsafe.CVarArgList): va_list     = o
    extension (v: va_list) inline def value: unsafe.CVarArgList = v

  type wchar_t = libc.stddef.wchar_t
  object wchar_t:
    val _tag: Tag[wchar_t]                                       = summon[Tag[libc.stddef.wchar_t]]
    inline def apply(inline o: libc.stddef.wchar_t): wchar_t     = o
    extension (v: wchar_t) inline def value: libc.stddef.wchar_t = v

object structs:
  import _root_.sdl2.enumerations.*
  import _root_.sdl2.predef.*
  import _root_.sdl2.aliases.*
  import _root_.sdl2.structs.*
  import _root_.sdl2.unions.*

  /** [bindgen] header: ./SDL_system.h
    */
  opaque type ID3D11Device = CStruct0
  object ID3D11Device:
    given _tag: Tag[ID3D11Device] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_system.h
    */
  opaque type ID3D12Device = CStruct0
  object ID3D12Device:
    given _tag: Tag[ID3D12Device] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_system.h
    */
  opaque type IDirect3DDevice9 = CStruct0
  object IDirect3DDevice9:
    given _tag: Tag[IDirect3DDevice9] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_assert.h
    */
  opaque type SDL_AssertData = CStruct7[CInt, CUnsignedInt, CString, CString, CInt, CString, Ptr[Byte]]
  object SDL_AssertData:
    given _tag: Tag[SDL_AssertData] =
      Tag.materializeCStruct7Tag[CInt, CUnsignedInt, CString, CString, CInt, CString, Ptr[Byte]]
    def apply()(using Zone): Ptr[SDL_AssertData] = scala.scalanative.unsafe.alloc[SDL_AssertData](1)
    def apply(
        always_ignore: CInt,
        trigger_count: CUnsignedInt,
        condition: CString,
        filename: CString,
        linenum: CInt,
        function: CString,
        next: Ptr[SDL_AssertData]
    )(using Zone): Ptr[SDL_AssertData] =
      val ____ptr = apply()
      (!____ptr).always_ignore = always_ignore
      (!____ptr).trigger_count = trigger_count
      (!____ptr).condition = condition
      (!____ptr).filename = filename
      (!____ptr).linenum = linenum
      (!____ptr).function = function
      (!____ptr).next = next
      ____ptr
    extension (struct: SDL_AssertData)
      def always_ignore: CInt                        = struct._1
      def always_ignore_=(value: CInt): Unit         = !struct.at1 = value
      def trigger_count: CUnsignedInt                = struct._2
      def trigger_count_=(value: CUnsignedInt): Unit = !struct.at2 = value
      def condition: CString                         = struct._3
      def condition_=(value: CString): Unit          = !struct.at3 = value
      def filename: CString                          = struct._4
      def filename_=(value: CString): Unit           = !struct.at4 = value
      def linenum: CInt                              = struct._5
      def linenum_=(value: CInt): Unit               = !struct.at5 = value
      def function: CString                          = struct._6
      def function_=(value: CString): Unit           = !struct.at6 = value
      def next: Ptr[SDL_AssertData]                  = struct._7.asInstanceOf[Ptr[SDL_AssertData]]
      def next_=(value: Ptr[SDL_AssertData]): Unit   = !struct.at7 = value.asInstanceOf[Ptr[Byte]]

  /** [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioCVT = CStruct11[CInt, SDL_AudioFormat, SDL_AudioFormat, Double, Ptr[
    Uint8
  ], CInt, CInt, CInt, Double, CArray[Ptr[Byte], Nat.Digit2[Nat._1, Nat._0]], CInt]
  object SDL_AudioCVT:
    given _tag: Tag[SDL_AudioCVT] = Tag.materializeCStruct11Tag[CInt, SDL_AudioFormat, SDL_AudioFormat, Double, Ptr[
      Uint8
    ], CInt, CInt, CInt, Double, CArray[Ptr[Byte], Nat.Digit2[Nat._1, Nat._0]], CInt]
    def apply()(using Zone): Ptr[SDL_AudioCVT] = scala.scalanative.unsafe.alloc[SDL_AudioCVT](1)
    def apply(
        needed: CInt,
        src_format: SDL_AudioFormat,
        dst_format: SDL_AudioFormat,
        rate_incr: Double,
        buf: Ptr[Uint8],
        len: CInt,
        len_cvt: CInt,
        len_mult: CInt,
        len_ratio: Double,
        filters: CArray[SDL_AudioFilter, Nat.Digit2[Nat._1, Nat._0]],
        filter_index: CInt
    )(using Zone): Ptr[SDL_AudioCVT] =
      val ____ptr = apply()
      (!____ptr).needed = needed
      (!____ptr).src_format = src_format
      (!____ptr).dst_format = dst_format
      (!____ptr).rate_incr = rate_incr
      (!____ptr).buf = buf
      (!____ptr).len = len
      (!____ptr).len_cvt = len_cvt
      (!____ptr).len_mult = len_mult
      (!____ptr).len_ratio = len_ratio
      (!____ptr).filters = filters
      (!____ptr).filter_index = filter_index
      ____ptr
    extension (struct: SDL_AudioCVT)
      def needed: CInt                               = struct._1
      def needed_=(value: CInt): Unit                = !struct.at1 = value
      def src_format: SDL_AudioFormat                = struct._2
      def src_format_=(value: SDL_AudioFormat): Unit = !struct.at2 = value
      def dst_format: SDL_AudioFormat                = struct._3
      def dst_format_=(value: SDL_AudioFormat): Unit = !struct.at3 = value
      def rate_incr: Double                          = struct._4
      def rate_incr_=(value: Double): Unit           = !struct.at4 = value
      def buf: Ptr[Uint8]                            = struct._5
      def buf_=(value: Ptr[Uint8]): Unit             = !struct.at5 = value
      def len: CInt                                  = struct._6
      def len_=(value: CInt): Unit                   = !struct.at6 = value
      def len_cvt: CInt                              = struct._7
      def len_cvt_=(value: CInt): Unit               = !struct.at7 = value
      def len_mult: CInt                             = struct._8
      def len_mult_=(value: CInt): Unit              = !struct.at8 = value
      def len_ratio: Double                          = struct._9
      def len_ratio_=(value: Double): Unit           = !struct.at9 = value
      def filters: CArray[SDL_AudioFilter, Nat.Digit2[Nat._1, Nat._0]] =
        struct._10.asInstanceOf[CArray[SDL_AudioFilter, Nat.Digit2[Nat._1, Nat._0]]]
      def filters_=(value: CArray[SDL_AudioFilter, Nat.Digit2[Nat._1, Nat._0]]): Unit = !struct.at10 =
        value.asInstanceOf[CArray[Ptr[Byte], Nat.Digit2[Nat._1, Nat._0]]]
      def filter_index: CInt                = struct._11
      def filter_index_=(value: CInt): Unit = !struct.at11 = value

  /** Audio device event structure (event.adevice.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_AudioDeviceEvent = CStruct7[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8]
  object SDL_AudioDeviceEvent:
    given _tag: Tag[SDL_AudioDeviceEvent] =
      Tag.materializeCStruct7Tag[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_AudioDeviceEvent] = scala.scalanative.unsafe.alloc[SDL_AudioDeviceEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: Uint32,
        iscapture: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8
    )(using Zone): Ptr[SDL_AudioDeviceEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).iscapture = iscapture
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      ____ptr
    extension (struct: SDL_AudioDeviceEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def which: Uint32                    = struct._3
      def which_=(value: Uint32): Unit     = !struct.at3 = value
      def iscapture: Uint8                 = struct._4
      def iscapture_=(value: Uint8): Unit  = !struct.at4 = value
      def padding1: Uint8                  = struct._5
      def padding1_=(value: Uint8): Unit   = !struct.at5 = value
      def padding2: Uint8                  = struct._6
      def padding2_=(value: Uint8): Unit   = !struct.at6 = value
      def padding3: Uint8                  = struct._7
      def padding3_=(value: Uint8): Unit   = !struct.at7 = value

  /** The calculated values in this structure are calculated by SDL_OpenAudio().
    *
    * [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioSpec =
    CStruct9[CInt, SDL_AudioFormat, Uint8, Uint8, Uint16, Uint16, Uint32, SDL_AudioCallback, Ptr[Byte]]
  object SDL_AudioSpec:
    given _tag: Tag[SDL_AudioSpec] = Tag
      .materializeCStruct9Tag[CInt, SDL_AudioFormat, Uint8, Uint8, Uint16, Uint16, Uint32, SDL_AudioCallback, Ptr[Byte]]
    def apply()(using Zone): Ptr[SDL_AudioSpec] = scala.scalanative.unsafe.alloc[SDL_AudioSpec](1)
    def apply(
        freq: CInt,
        format: SDL_AudioFormat,
        channels: Uint8,
        silence: Uint8,
        samples: Uint16,
        padding: Uint16,
        size: Uint32,
        callback: SDL_AudioCallback,
        userdata: Ptr[Byte]
    )(using Zone): Ptr[SDL_AudioSpec] =
      val ____ptr = apply()
      (!____ptr).freq = freq
      (!____ptr).format = format
      (!____ptr).channels = channels
      (!____ptr).silence = silence
      (!____ptr).samples = samples
      (!____ptr).padding = padding
      (!____ptr).size = size
      (!____ptr).callback = callback
      (!____ptr).userdata = userdata
      ____ptr
    extension (struct: SDL_AudioSpec)
      def freq: CInt                                 = struct._1
      def freq_=(value: CInt): Unit                  = !struct.at1 = value
      def format: SDL_AudioFormat                    = struct._2
      def format_=(value: SDL_AudioFormat): Unit     = !struct.at2 = value
      def channels: Uint8                            = struct._3
      def channels_=(value: Uint8): Unit             = !struct.at3 = value
      def silence: Uint8                             = struct._4
      def silence_=(value: Uint8): Unit              = !struct.at4 = value
      def samples: Uint16                            = struct._5
      def samples_=(value: Uint16): Unit             = !struct.at5 = value
      def padding: Uint16                            = struct._6
      def padding_=(value: Uint16): Unit             = !struct.at6 = value
      def size: Uint32                               = struct._7
      def size_=(value: Uint32): Unit                = !struct.at7 = value
      def callback: SDL_AudioCallback                = struct._8
      def callback_=(value: SDL_AudioCallback): Unit = !struct.at8 = value
      def userdata: Ptr[Byte]                        = struct._9
      def userdata_=(value: Ptr[Byte]): Unit         = !struct.at9 = value

  /** [bindgen] header: ./SDL_audio.h
    */
  opaque type SDL_AudioStream = CStruct0
  object SDL_AudioStream:
    given _tag: Tag[SDL_AudioStream] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_surface.h
    */
  opaque type SDL_BlitMap = CStruct0
  object SDL_BlitMap:
    given _tag: Tag[SDL_BlitMap] = Tag.materializeCStruct0Tag

  /** The bits of this structure can be directly reinterpreted as an integer-packed color which uses the SDL_PIXELFORMAT_RGBA32 format (SDL_PIXELFORMAT_ABGR8888 on little-endian systems and SDL_PIXELFORMAT_RGBA8888 on big-endian systems).
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_Color = CStruct4[Uint8, Uint8, Uint8, Uint8]
  object SDL_Color:
    given _tag: Tag[SDL_Color]              = Tag.materializeCStruct4Tag[Uint8, Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_Color] = scala.scalanative.unsafe.alloc[SDL_Color](1)
    def apply(r: Uint8, g: Uint8, b: Uint8, a: Uint8)(using Zone): Ptr[SDL_Color] =
      val ____ptr = apply()
      (!____ptr).r = r
      (!____ptr).g = g
      (!____ptr).b = b
      (!____ptr).a = a
      ____ptr
    extension (struct: SDL_Color)
      def r: Uint8                = struct._1
      def r_=(value: Uint8): Unit = !struct.at1 = value
      def g: Uint8                = struct._2
      def g_=(value: Uint8): Unit = !struct.at2 = value
      def b: Uint8                = struct._3
      def b_=(value: Uint8): Unit = !struct.at3 = value
      def a: Uint8                = struct._4
      def a_=(value: Uint8): Unit = !struct.at4 = value

  /** Fields shared by every event
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_CommonEvent = CStruct2[Uint32, Uint32]
  object SDL_CommonEvent:
    given _tag: Tag[SDL_CommonEvent]              = Tag.materializeCStruct2Tag[Uint32, Uint32]
    def apply()(using Zone): Ptr[SDL_CommonEvent] = scala.scalanative.unsafe.alloc[SDL_CommonEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32)(using Zone): Ptr[SDL_CommonEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      ____ptr
    extension (struct: SDL_CommonEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value

  /** Game controller axis motion event structure (event.caxis.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_ControllerAxisEvent =
    CStruct9[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Uint16]
  object SDL_ControllerAxisEvent:
    given _tag: Tag[SDL_ControllerAxisEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Uint16]
    def apply()(using Zone): Ptr[SDL_ControllerAxisEvent] = scala.scalanative.unsafe.alloc[SDL_ControllerAxisEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        axis: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        value: Sint16,
        padding4: Uint16
    )(using Zone): Ptr[SDL_ControllerAxisEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).axis = axis
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).value = value
      (!____ptr).padding4 = padding4
      ____ptr
    extension (struct: SDL_ControllerAxisEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def axis: Uint8                          = struct._4
      def axis_=(value: Uint8): Unit           = !struct.at4 = value
      def padding1: Uint8                      = struct._5
      def padding1_=(value: Uint8): Unit       = !struct.at5 = value
      def padding2: Uint8                      = struct._6
      def padding2_=(value: Uint8): Unit       = !struct.at6 = value
      def padding3: Uint8                      = struct._7
      def padding3_=(value: Uint8): Unit       = !struct.at7 = value
      def value: Sint16                        = struct._8
      def value_=(value: Sint16): Unit         = !struct.at8 = value
      def padding4: Uint16                     = struct._9
      def padding4_=(value: Uint16): Unit      = !struct.at9 = value

  /** Game controller button event structure (event.cbutton.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_ControllerButtonEvent = CStruct7[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
  object SDL_ControllerButtonEvent:
    given _tag: Tag[SDL_ControllerButtonEvent] =
      Tag.materializeCStruct7Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_ControllerButtonEvent] =
      scala.scalanative.unsafe.alloc[SDL_ControllerButtonEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        button: Uint8,
        state: Uint8,
        padding1: Uint8,
        padding2: Uint8
    )(using Zone): Ptr[SDL_ControllerButtonEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).button = button
      (!____ptr).state = state
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      ____ptr
    extension (struct: SDL_ControllerButtonEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def button: Uint8                        = struct._4
      def button_=(value: Uint8): Unit         = !struct.at4 = value
      def state: Uint8                         = struct._5
      def state_=(value: Uint8): Unit          = !struct.at5 = value
      def padding1: Uint8                      = struct._6
      def padding1_=(value: Uint8): Unit       = !struct.at6 = value
      def padding2: Uint8                      = struct._7
      def padding2_=(value: Uint8): Unit       = !struct.at7 = value

  /** Controller device event structure (event.cdevice.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_ControllerDeviceEvent = CStruct3[Uint32, Uint32, Sint32]
  object SDL_ControllerDeviceEvent:
    given _tag: Tag[SDL_ControllerDeviceEvent] = Tag.materializeCStruct3Tag[Uint32, Uint32, Sint32]
    def apply()(using Zone): Ptr[SDL_ControllerDeviceEvent] =
      scala.scalanative.unsafe.alloc[SDL_ControllerDeviceEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, which: Sint32)(using Zone): Ptr[SDL_ControllerDeviceEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      ____ptr
    extension (struct: SDL_ControllerDeviceEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def which: Sint32                    = struct._3
      def which_=(value: Sint32): Unit     = !struct.at3 = value

  /** Game controller sensor event structure (event.csensor.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_ControllerSensorEvent =
    CStruct6[Uint32, Uint32, SDL_JoystickID, Sint32, CArray[Float, Nat._3], Uint64]
  object SDL_ControllerSensorEvent:
    given _tag: Tag[SDL_ControllerSensorEvent] =
      Tag.materializeCStruct6Tag[Uint32, Uint32, SDL_JoystickID, Sint32, CArray[Float, Nat._3], Uint64]
    def apply()(using Zone): Ptr[SDL_ControllerSensorEvent] =
      scala.scalanative.unsafe.alloc[SDL_ControllerSensorEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        sensor: Sint32,
        data: CArray[Float, Nat._3],
        timestamp_us: Uint64
    )(using Zone): Ptr[SDL_ControllerSensorEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).sensor = sensor
      (!____ptr).data = data
      (!____ptr).timestamp_us = timestamp_us
      ____ptr
    extension (struct: SDL_ControllerSensorEvent)
      def `type`: Uint32                             = struct._1
      def type_=(value: Uint32): Unit                = !struct.at1 = value
      def timestamp: Uint32                          = struct._2
      def timestamp_=(value: Uint32): Unit           = !struct.at2 = value
      def which: SDL_JoystickID                      = struct._3
      def which_=(value: SDL_JoystickID): Unit       = !struct.at3 = value
      def sensor: Sint32                             = struct._4
      def sensor_=(value: Sint32): Unit              = !struct.at4 = value
      def data: CArray[Float, Nat._3]                = struct._5
      def data_=(value: CArray[Float, Nat._3]): Unit = !struct.at5 = value
      def timestamp_us: Uint64                       = struct._6
      def timestamp_us_=(value: Uint64): Unit        = !struct.at6 = value

  /** Game controller touchpad event structure (event.ctouchpad.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_ControllerTouchpadEvent =
    CStruct8[Uint32, Uint32, SDL_JoystickID, Sint32, Sint32, Float, Float, Float]
  object SDL_ControllerTouchpadEvent:
    given _tag: Tag[SDL_ControllerTouchpadEvent] =
      Tag.materializeCStruct8Tag[Uint32, Uint32, SDL_JoystickID, Sint32, Sint32, Float, Float, Float]
    def apply()(using Zone): Ptr[SDL_ControllerTouchpadEvent] =
      scala.scalanative.unsafe.alloc[SDL_ControllerTouchpadEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        touchpad: Sint32,
        finger: Sint32,
        x: Float,
        y: Float,
        pressure: Float
    )(using Zone): Ptr[SDL_ControllerTouchpadEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).touchpad = touchpad
      (!____ptr).finger = finger
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).pressure = pressure
      ____ptr
    extension (struct: SDL_ControllerTouchpadEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def touchpad: Sint32                     = struct._4
      def touchpad_=(value: Sint32): Unit      = !struct.at4 = value
      def finger: Sint32                       = struct._5
      def finger_=(value: Sint32): Unit        = !struct.at5 = value
      def x: Float                             = struct._6
      def x_=(value: Float): Unit              = !struct.at6 = value
      def y: Float                             = struct._7
      def y_=(value: Float): Unit              = !struct.at7 = value
      def pressure: Float                      = struct._8
      def pressure_=(value: Float): Unit       = !struct.at8 = value

  /** [bindgen] header: ./SDL_mouse.h
    */
  opaque type SDL_Cursor = CStruct0
  object SDL_Cursor:
    given _tag: Tag[SDL_Cursor] = Tag.materializeCStruct0Tag

  /** Display state change event data (event.display.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_DisplayEvent = CStruct8[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32]
  object SDL_DisplayEvent:
    given _tag: Tag[SDL_DisplayEvent] =
      Tag.materializeCStruct8Tag[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32]
    def apply()(using Zone): Ptr[SDL_DisplayEvent] = scala.scalanative.unsafe.alloc[SDL_DisplayEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        display: Uint32,
        event: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        data1: Sint32
    )(using Zone): Ptr[SDL_DisplayEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).display = display
      (!____ptr).event = event
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).data1 = data1
      ____ptr
    extension (struct: SDL_DisplayEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def display: Uint32                  = struct._3
      def display_=(value: Uint32): Unit   = !struct.at3 = value
      def event: Uint8                     = struct._4
      def event_=(value: Uint8): Unit      = !struct.at4 = value
      def padding1: Uint8                  = struct._5
      def padding1_=(value: Uint8): Unit   = !struct.at5 = value
      def padding2: Uint8                  = struct._6
      def padding2_=(value: Uint8): Unit   = !struct.at6 = value
      def padding3: Uint8                  = struct._7
      def padding3_=(value: Uint8): Unit   = !struct.at7 = value
      def data1: Sint32                    = struct._8
      def data1_=(value: Sint32): Unit     = !struct.at8 = value

  /** The structure that defines a display mode
    *
    * [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_DisplayMode = CStruct5[Uint32, CInt, CInt, CInt, Ptr[Byte]]
  object SDL_DisplayMode:
    given _tag: Tag[SDL_DisplayMode]              = Tag.materializeCStruct5Tag[Uint32, CInt, CInt, CInt, Ptr[Byte]]
    def apply()(using Zone): Ptr[SDL_DisplayMode] = scala.scalanative.unsafe.alloc[SDL_DisplayMode](1)
    def apply(format: Uint32, w: CInt, h: CInt, refresh_rate: CInt, driverdata: Ptr[Byte])(using
        Zone
    ): Ptr[SDL_DisplayMode] =
      val ____ptr = apply()
      (!____ptr).format = format
      (!____ptr).w = w
      (!____ptr).h = h
      (!____ptr).refresh_rate = refresh_rate
      (!____ptr).driverdata = driverdata
      ____ptr
    extension (struct: SDL_DisplayMode)
      def format: Uint32                       = struct._1
      def format_=(value: Uint32): Unit        = !struct.at1 = value
      def w: CInt                              = struct._2
      def w_=(value: CInt): Unit               = !struct.at2 = value
      def h: CInt                              = struct._3
      def h_=(value: CInt): Unit               = !struct.at3 = value
      def refresh_rate: CInt                   = struct._4
      def refresh_rate_=(value: CInt): Unit    = !struct.at4 = value
      def driverdata: Ptr[Byte]                = struct._5
      def driverdata_=(value: Ptr[Byte]): Unit = !struct.at5 = value

  /** Dollar Gesture Event (event.dgesture.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_DollarGestureEvent = CStruct8[Uint32, Uint32, SDL_TouchID, SDL_GestureID, Uint32, Float, Float, Float]
  object SDL_DollarGestureEvent:
    given _tag: Tag[SDL_DollarGestureEvent] =
      Tag.materializeCStruct8Tag[Uint32, Uint32, SDL_TouchID, SDL_GestureID, Uint32, Float, Float, Float]
    def apply()(using Zone): Ptr[SDL_DollarGestureEvent] = scala.scalanative.unsafe.alloc[SDL_DollarGestureEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        touchId: SDL_TouchID,
        gestureId: SDL_GestureID,
        numFingers: Uint32,
        error: Float,
        x: Float,
        y: Float
    )(using Zone): Ptr[SDL_DollarGestureEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).touchId = touchId
      (!____ptr).gestureId = gestureId
      (!____ptr).numFingers = numFingers
      (!____ptr).error = error
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: SDL_DollarGestureEvent)
      def `type`: Uint32                          = struct._1
      def type_=(value: Uint32): Unit             = !struct.at1 = value
      def timestamp: Uint32                       = struct._2
      def timestamp_=(value: Uint32): Unit        = !struct.at2 = value
      def touchId: SDL_TouchID                    = struct._3
      def touchId_=(value: SDL_TouchID): Unit     = !struct.at3 = value
      def gestureId: SDL_GestureID                = struct._4
      def gestureId_=(value: SDL_GestureID): Unit = !struct.at4 = value
      def numFingers: Uint32                      = struct._5
      def numFingers_=(value: Uint32): Unit       = !struct.at5 = value
      def error: Float                            = struct._6
      def error_=(value: Float): Unit             = !struct.at6 = value
      def x: Float                                = struct._7
      def x_=(value: Float): Unit                 = !struct.at7 = value
      def y: Float                                = struct._8
      def y_=(value: Float): Unit                 = !struct.at8 = value

  /** An event used to request a file open by the system (event.drop.*) This event is enabled by default, you can disable it with SDL_EventState().
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_DropEvent = CStruct4[Uint32, Uint32, CString, Uint32]
  object SDL_DropEvent:
    given _tag: Tag[SDL_DropEvent]              = Tag.materializeCStruct4Tag[Uint32, Uint32, CString, Uint32]
    def apply()(using Zone): Ptr[SDL_DropEvent] = scala.scalanative.unsafe.alloc[SDL_DropEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, file: CString, windowID: Uint32)(using Zone): Ptr[SDL_DropEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).file = file
      (!____ptr).windowID = windowID
      ____ptr
    extension (struct: SDL_DropEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def file: CString                    = struct._3
      def file_=(value: CString): Unit     = !struct.at3 = value
      def windowID: Uint32                 = struct._4
      def windowID_=(value: Uint32): Unit  = !struct.at4 = value

  /** The structure that defines a point (floating point)
    *
    * [bindgen] header: ./SDL_rect.h
    */
  opaque type SDL_FPoint = CStruct2[Float, Float]
  object SDL_FPoint:
    given _tag: Tag[SDL_FPoint]              = Tag.materializeCStruct2Tag[Float, Float]
    def apply()(using Zone): Ptr[SDL_FPoint] = scala.scalanative.unsafe.alloc[SDL_FPoint](1)
    def apply(x: Float, y: Float)(using Zone): Ptr[SDL_FPoint] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: SDL_FPoint)
      def x: Float                = struct._1
      def x_=(value: Float): Unit = !struct.at1 = value
      def y: Float                = struct._2
      def y_=(value: Float): Unit = !struct.at2 = value

  /** A rectangle, with the origin at the upper left (floating point).
    *
    * [bindgen] header: ./SDL_rect.h
    */
  opaque type SDL_FRect = CStruct4[Float, Float, Float, Float]
  object SDL_FRect:
    given _tag: Tag[SDL_FRect]              = Tag.materializeCStruct4Tag[Float, Float, Float, Float]
    def apply()(using Zone): Ptr[SDL_FRect] = scala.scalanative.unsafe.alloc[SDL_FRect](1)
    def apply(x: Float, y: Float, w: Float, h: Float)(using Zone): Ptr[SDL_FRect] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).w = w
      (!____ptr).h = h
      ____ptr
    extension (struct: SDL_FRect)
      def x: Float                = struct._1
      def x_=(value: Float): Unit = !struct.at1 = value
      def y: Float                = struct._2
      def y_=(value: Float): Unit = !struct.at2 = value
      def w: Float                = struct._3
      def w_=(value: Float): Unit = !struct.at3 = value
      def h: Float                = struct._4
      def h_=(value: Float): Unit = !struct.at4 = value

  /** [bindgen] header: ./SDL_touch.h
    */
  opaque type SDL_Finger = CStruct4[SDL_FingerID, Float, Float, Float]
  object SDL_Finger:
    given _tag: Tag[SDL_Finger]              = Tag.materializeCStruct4Tag[SDL_FingerID, Float, Float, Float]
    def apply()(using Zone): Ptr[SDL_Finger] = scala.scalanative.unsafe.alloc[SDL_Finger](1)
    def apply(id: SDL_FingerID, x: Float, y: Float, pressure: Float)(using Zone): Ptr[SDL_Finger] =
      val ____ptr = apply()
      (!____ptr).id = id
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).pressure = pressure
      ____ptr
    extension (struct: SDL_Finger)
      def id: SDL_FingerID                = struct._1
      def id_=(value: SDL_FingerID): Unit = !struct.at1 = value
      def x: Float                        = struct._2
      def x_=(value: Float): Unit         = !struct.at2 = value
      def y: Float                        = struct._3
      def y_=(value: Float): Unit         = !struct.at3 = value
      def pressure: Float                 = struct._4
      def pressure_=(value: Float): Unit  = !struct.at4 = value

  /** An SDL_GUID is a 128-bit identifier for an input device that identifies that device across runs of SDL programs on the same platform. If the device is detached and then re-attached to a different port, or if the base system is rebooted, the device should still report the same GUID.
    *
    * [bindgen] header: ./SDL_guid.h
    */
  opaque type SDL_GUID = CStruct1[CArray[Uint8, Nat.Digit2[Nat._1, Nat._6]]]
  object SDL_GUID:
    given _tag: Tag[SDL_GUID]              = Tag.materializeCStruct1Tag[CArray[Uint8, Nat.Digit2[Nat._1, Nat._6]]]
    def apply()(using Zone): Ptr[SDL_GUID] = scala.scalanative.unsafe.alloc[SDL_GUID](1)
    def apply(data: CArray[Uint8, Nat.Digit2[Nat._1, Nat._6]])(using Zone): Ptr[SDL_GUID] =
      val ____ptr = apply()
      (!____ptr).data = data
      ____ptr
    extension (struct: SDL_GUID)
      def data: CArray[Uint8, Nat.Digit2[Nat._1, Nat._6]]                = struct._1
      def data_=(value: CArray[Uint8, Nat.Digit2[Nat._1, Nat._6]]): Unit = !struct.at1 = value

  /** The gamecontroller structure used to identify an SDL game controller
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameController = CStruct0
  object SDL_GameController:
    given _tag: Tag[SDL_GameController] = Tag.materializeCStruct0Tag

  /** Get the SDL joystick layer binding for this controller button/axis mapping
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type SDL_GameControllerButtonBind = CStruct2[SDL_GameControllerBindType, SDL_GameControllerButtonBind.Union0]
  object SDL_GameControllerButtonBind:
    /** [bindgen] header: ./SDL_gamecontroller.h
      */
    opaque type Union0 = CArray[Byte, Nat._8]
    object Union0:
      /** [bindgen] header: ./SDL_gamecontroller.h
        */
      opaque type Struct0 = CStruct2[CInt, CInt]
      object Struct0:
        given _tag: Tag[Struct0]              = Tag.materializeCStruct2Tag[CInt, CInt]
        def apply()(using Zone): Ptr[Struct0] = scala.scalanative.unsafe.alloc[Struct0](1)
        def apply(hat: CInt, hat_mask: CInt)(using Zone): Ptr[Struct0] =
          val ____ptr = apply()
          (!____ptr).hat = hat
          (!____ptr).hat_mask = hat_mask
          ____ptr
        extension (struct: Struct0)
          def hat: CInt                     = struct._1
          def hat_=(value: CInt): Unit      = !struct.at1 = value
          def hat_mask: CInt                = struct._2
          def hat_mask_=(value: CInt): Unit = !struct.at2 = value
      given _tag: Tag[Union0] = Tag.CArray[CChar, Nat._8](Tag.Byte, Tag.Nat8)
      def apply()(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        ___ptr
      @scala.annotation.targetName("apply_button")
      def apply(button: CInt)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[CInt]].update(0, button)
        ___ptr
      @scala.annotation.targetName("apply_axis")
      def apply(axis: CInt)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[CInt]].update(0, axis)
        ___ptr
      @scala.annotation.targetName("apply_hat")
      def apply(hat: SDL_GameControllerButtonBind.Union0.Struct0)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[SDL_GameControllerButtonBind.Union0.Struct0]].update(0, hat)
        ___ptr
      extension (struct: Union0)
        def button: CInt                = !struct.at(0).asInstanceOf[Ptr[CInt]]
        def button_=(value: CInt): Unit = !struct.at(0).asInstanceOf[Ptr[CInt]] = value
        def axis: CInt                  = !struct.at(0).asInstanceOf[Ptr[CInt]]
        def axis_=(value: CInt): Unit   = !struct.at(0).asInstanceOf[Ptr[CInt]] = value
        def hat: SDL_GameControllerButtonBind.Union0.Struct0 =
          !struct.at(0).asInstanceOf[Ptr[SDL_GameControllerButtonBind.Union0.Struct0]]
        def hat_=(value: SDL_GameControllerButtonBind.Union0.Struct0): Unit =
          !struct.at(0).asInstanceOf[Ptr[SDL_GameControllerButtonBind.Union0.Struct0]] = value
    given _tag: Tag[SDL_GameControllerButtonBind] =
      Tag.materializeCStruct2Tag[SDL_GameControllerBindType, SDL_GameControllerButtonBind.Union0]
    def apply()(using Zone): Ptr[SDL_GameControllerButtonBind] =
      scala.scalanative.unsafe.alloc[SDL_GameControllerButtonBind](1)
    def apply(bindType: SDL_GameControllerBindType, value: SDL_GameControllerButtonBind.Union0)(using
        Zone
    ): Ptr[SDL_GameControllerButtonBind] =
      val ____ptr = apply()
      (!____ptr).bindType = bindType
      (!____ptr).value = value
      ____ptr
    extension (struct: SDL_GameControllerButtonBind)
      def bindType: SDL_GameControllerBindType                      = struct._1
      def bindType_=(value: SDL_GameControllerBindType): Unit       = !struct.at1 = value
      def value: SDL_GameControllerButtonBind.Union0                = struct._2
      def value_=(value: SDL_GameControllerButtonBind.Union0): Unit = !struct.at2 = value

  /** The haptic structure used to identify an SDL haptic.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_Haptic = CStruct0
  object SDL_Haptic:
    given _tag: Tag[SDL_Haptic] = Tag.materializeCStruct0Tag

  /** A structure containing a template for a Condition effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticCondition = CStruct12[
    Uint16,
    SDL_HapticDirection,
    Uint32,
    Uint16,
    Uint16,
    Uint16,
    CArray[Uint16, Nat._3],
    CArray[Uint16, Nat._3],
    CArray[Sint16, Nat._3],
    CArray[Sint16, Nat._3],
    CArray[Uint16, Nat._3],
    CArray[Sint16, Nat._3]
  ]
  object SDL_HapticCondition:
    given _tag: Tag[SDL_HapticCondition] = Tag.materializeCStruct12Tag[
      Uint16,
      SDL_HapticDirection,
      Uint32,
      Uint16,
      Uint16,
      Uint16,
      CArray[Uint16, Nat._3],
      CArray[Uint16, Nat._3],
      CArray[Sint16, Nat._3],
      CArray[Sint16, Nat._3],
      CArray[Uint16, Nat._3],
      CArray[Sint16, Nat._3]
    ]
    def apply()(using Zone): Ptr[SDL_HapticCondition] = scala.scalanative.unsafe.alloc[SDL_HapticCondition](1)
    def apply(
        `type`: Uint16,
        direction: SDL_HapticDirection,
        length: Uint32,
        delay: Uint16,
        button: Uint16,
        interval: Uint16,
        right_sat: CArray[Uint16, Nat._3],
        left_sat: CArray[Uint16, Nat._3],
        right_coeff: CArray[Sint16, Nat._3],
        left_coeff: CArray[Sint16, Nat._3],
        deadband: CArray[Uint16, Nat._3],
        center: CArray[Sint16, Nat._3]
    )(using Zone): Ptr[SDL_HapticCondition] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).direction = direction
      (!____ptr).length = length
      (!____ptr).delay = delay
      (!____ptr).button = button
      (!____ptr).interval = interval
      (!____ptr).right_sat = right_sat
      (!____ptr).left_sat = left_sat
      (!____ptr).right_coeff = right_coeff
      (!____ptr).left_coeff = left_coeff
      (!____ptr).deadband = deadband
      (!____ptr).center = center
      ____ptr
    extension (struct: SDL_HapticCondition)
      def `type`: Uint16                                     = struct._1
      def type_=(value: Uint16): Unit                        = !struct.at1 = value
      def direction: SDL_HapticDirection                     = struct._2
      def direction_=(value: SDL_HapticDirection): Unit      = !struct.at2 = value
      def length: Uint32                                     = struct._3
      def length_=(value: Uint32): Unit                      = !struct.at3 = value
      def delay: Uint16                                      = struct._4
      def delay_=(value: Uint16): Unit                       = !struct.at4 = value
      def button: Uint16                                     = struct._5
      def button_=(value: Uint16): Unit                      = !struct.at5 = value
      def interval: Uint16                                   = struct._6
      def interval_=(value: Uint16): Unit                    = !struct.at6 = value
      def right_sat: CArray[Uint16, Nat._3]                  = struct._7
      def right_sat_=(value: CArray[Uint16, Nat._3]): Unit   = !struct.at7 = value
      def left_sat: CArray[Uint16, Nat._3]                   = struct._8
      def left_sat_=(value: CArray[Uint16, Nat._3]): Unit    = !struct.at8 = value
      def right_coeff: CArray[Sint16, Nat._3]                = struct._9
      def right_coeff_=(value: CArray[Sint16, Nat._3]): Unit = !struct.at9 = value
      def left_coeff: CArray[Sint16, Nat._3]                 = struct._10
      def left_coeff_=(value: CArray[Sint16, Nat._3]): Unit  = !struct.at10 = value
      def deadband: CArray[Uint16, Nat._3]                   = struct._11
      def deadband_=(value: CArray[Uint16, Nat._3]): Unit    = !struct.at11 = value
      def center: CArray[Sint16, Nat._3]                     = struct._12
      def center_=(value: CArray[Sint16, Nat._3]): Unit      = !struct.at12 = value

  /** A structure containing a template for a Constant effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticConstant =
    CStruct11[Uint16, SDL_HapticDirection, Uint32, Uint16, Uint16, Uint16, Sint16, Uint16, Uint16, Uint16, Uint16]
  object SDL_HapticConstant:
    given _tag: Tag[SDL_HapticConstant] = Tag.materializeCStruct11Tag[
      Uint16,
      SDL_HapticDirection,
      Uint32,
      Uint16,
      Uint16,
      Uint16,
      Sint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16
    ]
    def apply()(using Zone): Ptr[SDL_HapticConstant] = scala.scalanative.unsafe.alloc[SDL_HapticConstant](1)
    def apply(
        `type`: Uint16,
        direction: SDL_HapticDirection,
        length: Uint32,
        delay: Uint16,
        button: Uint16,
        interval: Uint16,
        level: Sint16,
        attack_length: Uint16,
        attack_level: Uint16,
        fade_length: Uint16,
        fade_level: Uint16
    )(using Zone): Ptr[SDL_HapticConstant] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).direction = direction
      (!____ptr).length = length
      (!____ptr).delay = delay
      (!____ptr).button = button
      (!____ptr).interval = interval
      (!____ptr).level = level
      (!____ptr).attack_length = attack_length
      (!____ptr).attack_level = attack_level
      (!____ptr).fade_length = fade_length
      (!____ptr).fade_level = fade_level
      ____ptr
    extension (struct: SDL_HapticConstant)
      def `type`: Uint16                                = struct._1
      def type_=(value: Uint16): Unit                   = !struct.at1 = value
      def direction: SDL_HapticDirection                = struct._2
      def direction_=(value: SDL_HapticDirection): Unit = !struct.at2 = value
      def length: Uint32                                = struct._3
      def length_=(value: Uint32): Unit                 = !struct.at3 = value
      def delay: Uint16                                 = struct._4
      def delay_=(value: Uint16): Unit                  = !struct.at4 = value
      def button: Uint16                                = struct._5
      def button_=(value: Uint16): Unit                 = !struct.at5 = value
      def interval: Uint16                              = struct._6
      def interval_=(value: Uint16): Unit               = !struct.at6 = value
      def level: Sint16                                 = struct._7
      def level_=(value: Sint16): Unit                  = !struct.at7 = value
      def attack_length: Uint16                         = struct._8
      def attack_length_=(value: Uint16): Unit          = !struct.at8 = value
      def attack_level: Uint16                          = struct._9
      def attack_level_=(value: Uint16): Unit           = !struct.at9 = value
      def fade_length: Uint16                           = struct._10
      def fade_length_=(value: Uint16): Unit            = !struct.at10 = value
      def fade_level: Uint16                            = struct._11
      def fade_level_=(value: Uint16): Unit             = !struct.at11 = value

  /** A structure containing a template for the ::SDL_HAPTIC_CUSTOM effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticCustom =
    CStruct14[Uint16, SDL_HapticDirection, Uint32, Uint16, Uint16, Uint16, Uint8, Uint16, Uint16, Ptr[
      Uint16
    ], Uint16, Uint16, Uint16, Uint16]
  object SDL_HapticCustom:
    given _tag: Tag[SDL_HapticCustom] = Tag
      .materializeCStruct14Tag[Uint16, SDL_HapticDirection, Uint32, Uint16, Uint16, Uint16, Uint8, Uint16, Uint16, Ptr[
        Uint16
      ], Uint16, Uint16, Uint16, Uint16]
    def apply()(using Zone): Ptr[SDL_HapticCustom] = scala.scalanative.unsafe.alloc[SDL_HapticCustom](1)
    def apply(
        `type`: Uint16,
        direction: SDL_HapticDirection,
        length: Uint32,
        delay: Uint16,
        button: Uint16,
        interval: Uint16,
        channels: Uint8,
        period: Uint16,
        samples: Uint16,
        data: Ptr[Uint16],
        attack_length: Uint16,
        attack_level: Uint16,
        fade_length: Uint16,
        fade_level: Uint16
    )(using Zone): Ptr[SDL_HapticCustom] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).direction = direction
      (!____ptr).length = length
      (!____ptr).delay = delay
      (!____ptr).button = button
      (!____ptr).interval = interval
      (!____ptr).channels = channels
      (!____ptr).period = period
      (!____ptr).samples = samples
      (!____ptr).data = data
      (!____ptr).attack_length = attack_length
      (!____ptr).attack_level = attack_level
      (!____ptr).fade_length = fade_length
      (!____ptr).fade_level = fade_level
      ____ptr
    extension (struct: SDL_HapticCustom)
      def `type`: Uint16                                = struct._1
      def type_=(value: Uint16): Unit                   = !struct.at1 = value
      def direction: SDL_HapticDirection                = struct._2
      def direction_=(value: SDL_HapticDirection): Unit = !struct.at2 = value
      def length: Uint32                                = struct._3
      def length_=(value: Uint32): Unit                 = !struct.at3 = value
      def delay: Uint16                                 = struct._4
      def delay_=(value: Uint16): Unit                  = !struct.at4 = value
      def button: Uint16                                = struct._5
      def button_=(value: Uint16): Unit                 = !struct.at5 = value
      def interval: Uint16                              = struct._6
      def interval_=(value: Uint16): Unit               = !struct.at6 = value
      def channels: Uint8                               = struct._7
      def channels_=(value: Uint8): Unit                = !struct.at7 = value
      def period: Uint16                                = struct._8
      def period_=(value: Uint16): Unit                 = !struct.at8 = value
      def samples: Uint16                               = struct._9
      def samples_=(value: Uint16): Unit                = !struct.at9 = value
      def data: Ptr[Uint16]                             = struct._10
      def data_=(value: Ptr[Uint16]): Unit              = !struct.at10 = value
      def attack_length: Uint16                         = struct._11
      def attack_length_=(value: Uint16): Unit          = !struct.at11 = value
      def attack_level: Uint16                          = struct._12
      def attack_level_=(value: Uint16): Unit           = !struct.at12 = value
      def fade_length: Uint16                           = struct._13
      def fade_length_=(value: Uint16): Unit            = !struct.at13 = value
      def fade_level: Uint16                            = struct._14
      def fade_level_=(value: Uint16): Unit             = !struct.at14 = value

  /** Structure that represents a haptic direction.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticDirection = CStruct2[Uint8, CArray[Sint32, Nat._3]]
  object SDL_HapticDirection:
    given _tag: Tag[SDL_HapticDirection]              = Tag.materializeCStruct2Tag[Uint8, CArray[Sint32, Nat._3]]
    def apply()(using Zone): Ptr[SDL_HapticDirection] = scala.scalanative.unsafe.alloc[SDL_HapticDirection](1)
    def apply(`type`: Uint8, dir: CArray[Sint32, Nat._3])(using Zone): Ptr[SDL_HapticDirection] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).dir = dir
      ____ptr
    extension (struct: SDL_HapticDirection)
      def `type`: Uint8                              = struct._1
      def type_=(value: Uint8): Unit                 = !struct.at1 = value
      def dir: CArray[Sint32, Nat._3]                = struct._2
      def dir_=(value: CArray[Sint32, Nat._3]): Unit = !struct.at2 = value

  /** A structure containing a template for a Left/Right effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticLeftRight = CStruct4[Uint16, Uint32, Uint16, Uint16]
  object SDL_HapticLeftRight:
    given _tag: Tag[SDL_HapticLeftRight]              = Tag.materializeCStruct4Tag[Uint16, Uint32, Uint16, Uint16]
    def apply()(using Zone): Ptr[SDL_HapticLeftRight] = scala.scalanative.unsafe.alloc[SDL_HapticLeftRight](1)
    def apply(`type`: Uint16, length: Uint32, large_magnitude: Uint16, small_magnitude: Uint16)(using
        Zone
    ): Ptr[SDL_HapticLeftRight] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).length = length
      (!____ptr).large_magnitude = large_magnitude
      (!____ptr).small_magnitude = small_magnitude
      ____ptr
    extension (struct: SDL_HapticLeftRight)
      def `type`: Uint16                         = struct._1
      def type_=(value: Uint16): Unit            = !struct.at1 = value
      def length: Uint32                         = struct._2
      def length_=(value: Uint32): Unit          = !struct.at2 = value
      def large_magnitude: Uint16                = struct._3
      def large_magnitude_=(value: Uint16): Unit = !struct.at3 = value
      def small_magnitude: Uint16                = struct._4
      def small_magnitude_=(value: Uint16): Unit = !struct.at4 = value

  /** A structure containing a template for a Periodic effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticPeriodic = CStruct14[
    Uint16,
    SDL_HapticDirection,
    Uint32,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Sint16,
    Sint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16
  ]
  object SDL_HapticPeriodic:
    given _tag: Tag[SDL_HapticPeriodic] = Tag.materializeCStruct14Tag[
      Uint16,
      SDL_HapticDirection,
      Uint32,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Sint16,
      Sint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16
    ]
    def apply()(using Zone): Ptr[SDL_HapticPeriodic] = scala.scalanative.unsafe.alloc[SDL_HapticPeriodic](1)
    def apply(
        `type`: Uint16,
        direction: SDL_HapticDirection,
        length: Uint32,
        delay: Uint16,
        button: Uint16,
        interval: Uint16,
        period: Uint16,
        magnitude: Sint16,
        offset: Sint16,
        phase: Uint16,
        attack_length: Uint16,
        attack_level: Uint16,
        fade_length: Uint16,
        fade_level: Uint16
    )(using Zone): Ptr[SDL_HapticPeriodic] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).direction = direction
      (!____ptr).length = length
      (!____ptr).delay = delay
      (!____ptr).button = button
      (!____ptr).interval = interval
      (!____ptr).period = period
      (!____ptr).magnitude = magnitude
      (!____ptr).offset = offset
      (!____ptr).phase = phase
      (!____ptr).attack_length = attack_length
      (!____ptr).attack_level = attack_level
      (!____ptr).fade_length = fade_length
      (!____ptr).fade_level = fade_level
      ____ptr
    extension (struct: SDL_HapticPeriodic)
      def `type`: Uint16                                = struct._1
      def type_=(value: Uint16): Unit                   = !struct.at1 = value
      def direction: SDL_HapticDirection                = struct._2
      def direction_=(value: SDL_HapticDirection): Unit = !struct.at2 = value
      def length: Uint32                                = struct._3
      def length_=(value: Uint32): Unit                 = !struct.at3 = value
      def delay: Uint16                                 = struct._4
      def delay_=(value: Uint16): Unit                  = !struct.at4 = value
      def button: Uint16                                = struct._5
      def button_=(value: Uint16): Unit                 = !struct.at5 = value
      def interval: Uint16                              = struct._6
      def interval_=(value: Uint16): Unit               = !struct.at6 = value
      def period: Uint16                                = struct._7
      def period_=(value: Uint16): Unit                 = !struct.at7 = value
      def magnitude: Sint16                             = struct._8
      def magnitude_=(value: Sint16): Unit              = !struct.at8 = value
      def offset: Sint16                                = struct._9
      def offset_=(value: Sint16): Unit                 = !struct.at9 = value
      def phase: Uint16                                 = struct._10
      def phase_=(value: Uint16): Unit                  = !struct.at10 = value
      def attack_length: Uint16                         = struct._11
      def attack_length_=(value: Uint16): Unit          = !struct.at11 = value
      def attack_level: Uint16                          = struct._12
      def attack_level_=(value: Uint16): Unit           = !struct.at12 = value
      def fade_length: Uint16                           = struct._13
      def fade_length_=(value: Uint16): Unit            = !struct.at13 = value
      def fade_level: Uint16                            = struct._14
      def fade_level_=(value: Uint16): Unit             = !struct.at14 = value

  /** A structure containing a template for a Ramp effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticRamp = CStruct12[
    Uint16,
    SDL_HapticDirection,
    Uint32,
    Uint16,
    Uint16,
    Uint16,
    Sint16,
    Sint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16
  ]
  object SDL_HapticRamp:
    given _tag: Tag[SDL_HapticRamp] = Tag.materializeCStruct12Tag[
      Uint16,
      SDL_HapticDirection,
      Uint32,
      Uint16,
      Uint16,
      Uint16,
      Sint16,
      Sint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16
    ]
    def apply()(using Zone): Ptr[SDL_HapticRamp] = scala.scalanative.unsafe.alloc[SDL_HapticRamp](1)
    def apply(
        `type`: Uint16,
        direction: SDL_HapticDirection,
        length: Uint32,
        delay: Uint16,
        button: Uint16,
        interval: Uint16,
        start: Sint16,
        end: Sint16,
        attack_length: Uint16,
        attack_level: Uint16,
        fade_length: Uint16,
        fade_level: Uint16
    )(using Zone): Ptr[SDL_HapticRamp] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).direction = direction
      (!____ptr).length = length
      (!____ptr).delay = delay
      (!____ptr).button = button
      (!____ptr).interval = interval
      (!____ptr).start = start
      (!____ptr).end = end
      (!____ptr).attack_length = attack_length
      (!____ptr).attack_level = attack_level
      (!____ptr).fade_length = fade_length
      (!____ptr).fade_level = fade_level
      ____ptr
    extension (struct: SDL_HapticRamp)
      def `type`: Uint16                                = struct._1
      def type_=(value: Uint16): Unit                   = !struct.at1 = value
      def direction: SDL_HapticDirection                = struct._2
      def direction_=(value: SDL_HapticDirection): Unit = !struct.at2 = value
      def length: Uint32                                = struct._3
      def length_=(value: Uint32): Unit                 = !struct.at3 = value
      def delay: Uint16                                 = struct._4
      def delay_=(value: Uint16): Unit                  = !struct.at4 = value
      def button: Uint16                                = struct._5
      def button_=(value: Uint16): Unit                 = !struct.at5 = value
      def interval: Uint16                              = struct._6
      def interval_=(value: Uint16): Unit               = !struct.at6 = value
      def start: Sint16                                 = struct._7
      def start_=(value: Sint16): Unit                  = !struct.at7 = value
      def end: Sint16                                   = struct._8
      def end_=(value: Sint16): Unit                    = !struct.at8 = value
      def attack_length: Uint16                         = struct._9
      def attack_length_=(value: Uint16): Unit          = !struct.at9 = value
      def attack_level: Uint16                          = struct._10
      def attack_level_=(value: Uint16): Unit           = !struct.at10 = value
      def fade_length: Uint16                           = struct._11
      def fade_length_=(value: Uint16): Unit            = !struct.at11 = value
      def fade_level: Uint16                            = struct._12
      def fade_level_=(value: Uint16): Unit             = !struct.at12 = value

  /** Joystick axis motion event structure (event.jaxis.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyAxisEvent = CStruct9[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Uint16]
  object SDL_JoyAxisEvent:
    given _tag: Tag[SDL_JoyAxisEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Uint16]
    def apply()(using Zone): Ptr[SDL_JoyAxisEvent] = scala.scalanative.unsafe.alloc[SDL_JoyAxisEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        axis: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        value: Sint16,
        padding4: Uint16
    )(using Zone): Ptr[SDL_JoyAxisEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).axis = axis
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).value = value
      (!____ptr).padding4 = padding4
      ____ptr
    extension (struct: SDL_JoyAxisEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def axis: Uint8                          = struct._4
      def axis_=(value: Uint8): Unit           = !struct.at4 = value
      def padding1: Uint8                      = struct._5
      def padding1_=(value: Uint8): Unit       = !struct.at5 = value
      def padding2: Uint8                      = struct._6
      def padding2_=(value: Uint8): Unit       = !struct.at6 = value
      def padding3: Uint8                      = struct._7
      def padding3_=(value: Uint8): Unit       = !struct.at7 = value
      def value: Sint16                        = struct._8
      def value_=(value: Sint16): Unit         = !struct.at8 = value
      def padding4: Uint16                     = struct._9
      def padding4_=(value: Uint16): Unit      = !struct.at9 = value

  /** Joystick trackball motion event structure (event.jball.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyBallEvent = CStruct9[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Sint16]
  object SDL_JoyBallEvent:
    given _tag: Tag[SDL_JoyBallEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8, Sint16, Sint16]
    def apply()(using Zone): Ptr[SDL_JoyBallEvent] = scala.scalanative.unsafe.alloc[SDL_JoyBallEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        ball: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        xrel: Sint16,
        yrel: Sint16
    )(using Zone): Ptr[SDL_JoyBallEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).ball = ball
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).xrel = xrel
      (!____ptr).yrel = yrel
      ____ptr
    extension (struct: SDL_JoyBallEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def ball: Uint8                          = struct._4
      def ball_=(value: Uint8): Unit           = !struct.at4 = value
      def padding1: Uint8                      = struct._5
      def padding1_=(value: Uint8): Unit       = !struct.at5 = value
      def padding2: Uint8                      = struct._6
      def padding2_=(value: Uint8): Unit       = !struct.at6 = value
      def padding3: Uint8                      = struct._7
      def padding3_=(value: Uint8): Unit       = !struct.at7 = value
      def xrel: Sint16                         = struct._8
      def xrel_=(value: Sint16): Unit          = !struct.at8 = value
      def yrel: Sint16                         = struct._9
      def yrel_=(value: Sint16): Unit          = !struct.at9 = value

  /** Joysick battery level change event structure (event.jbattery.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyBatteryEvent = CStruct4[Uint32, Uint32, SDL_JoystickID, SDL_JoystickPowerLevel]
  object SDL_JoyBatteryEvent:
    given _tag: Tag[SDL_JoyBatteryEvent] =
      Tag.materializeCStruct4Tag[Uint32, Uint32, SDL_JoystickID, SDL_JoystickPowerLevel]
    def apply()(using Zone): Ptr[SDL_JoyBatteryEvent] = scala.scalanative.unsafe.alloc[SDL_JoyBatteryEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, which: SDL_JoystickID, level: SDL_JoystickPowerLevel)(using
        Zone
    ): Ptr[SDL_JoyBatteryEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).level = level
      ____ptr
    extension (struct: SDL_JoyBatteryEvent)
      def `type`: Uint32                               = struct._1
      def type_=(value: Uint32): Unit                  = !struct.at1 = value
      def timestamp: Uint32                            = struct._2
      def timestamp_=(value: Uint32): Unit             = !struct.at2 = value
      def which: SDL_JoystickID                        = struct._3
      def which_=(value: SDL_JoystickID): Unit         = !struct.at3 = value
      def level: SDL_JoystickPowerLevel                = struct._4
      def level_=(value: SDL_JoystickPowerLevel): Unit = !struct.at4 = value

  /** Joystick button event structure (event.jbutton.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyButtonEvent = CStruct7[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
  object SDL_JoyButtonEvent:
    given _tag: Tag[SDL_JoyButtonEvent] =
      Tag.materializeCStruct7Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_JoyButtonEvent] = scala.scalanative.unsafe.alloc[SDL_JoyButtonEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        button: Uint8,
        state: Uint8,
        padding1: Uint8,
        padding2: Uint8
    )(using Zone): Ptr[SDL_JoyButtonEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).button = button
      (!____ptr).state = state
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      ____ptr
    extension (struct: SDL_JoyButtonEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def button: Uint8                        = struct._4
      def button_=(value: Uint8): Unit         = !struct.at4 = value
      def state: Uint8                         = struct._5
      def state_=(value: Uint8): Unit          = !struct.at5 = value
      def padding1: Uint8                      = struct._6
      def padding1_=(value: Uint8): Unit       = !struct.at6 = value
      def padding2: Uint8                      = struct._7
      def padding2_=(value: Uint8): Unit       = !struct.at7 = value

  /** Joystick device event structure (event.jdevice.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyDeviceEvent = CStruct3[Uint32, Uint32, Sint32]
  object SDL_JoyDeviceEvent:
    given _tag: Tag[SDL_JoyDeviceEvent]              = Tag.materializeCStruct3Tag[Uint32, Uint32, Sint32]
    def apply()(using Zone): Ptr[SDL_JoyDeviceEvent] = scala.scalanative.unsafe.alloc[SDL_JoyDeviceEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, which: Sint32)(using Zone): Ptr[SDL_JoyDeviceEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      ____ptr
    extension (struct: SDL_JoyDeviceEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def which: Sint32                    = struct._3
      def which_=(value: Sint32): Unit     = !struct.at3 = value

  /** Joystick hat position change event structure (event.jhat.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_JoyHatEvent = CStruct7[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
  object SDL_JoyHatEvent:
    given _tag: Tag[SDL_JoyHatEvent] =
      Tag.materializeCStruct7Tag[Uint32, Uint32, SDL_JoystickID, Uint8, Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_JoyHatEvent] = scala.scalanative.unsafe.alloc[SDL_JoyHatEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        which: SDL_JoystickID,
        hat: Uint8,
        value: Uint8,
        padding1: Uint8,
        padding2: Uint8
    )(using Zone): Ptr[SDL_JoyHatEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).hat = hat
      (!____ptr).value = value
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      ____ptr
    extension (struct: SDL_JoyHatEvent)
      def `type`: Uint32                       = struct._1
      def type_=(value: Uint32): Unit          = !struct.at1 = value
      def timestamp: Uint32                    = struct._2
      def timestamp_=(value: Uint32): Unit     = !struct.at2 = value
      def which: SDL_JoystickID                = struct._3
      def which_=(value: SDL_JoystickID): Unit = !struct.at3 = value
      def hat: Uint8                           = struct._4
      def hat_=(value: Uint8): Unit            = !struct.at4 = value
      def value: Uint8                         = struct._5
      def value_=(value: Uint8): Unit          = !struct.at5 = value
      def padding1: Uint8                      = struct._6
      def padding1_=(value: Uint8): Unit       = !struct.at6 = value
      def padding2: Uint8                      = struct._7
      def padding2_=(value: Uint8): Unit       = !struct.at7 = value

  /** [bindgen] header: ./SDL_joystick.h
    */
  opaque type SDL_Joystick = CStruct0
  object SDL_Joystick:
    given _tag: Tag[SDL_Joystick] = Tag.materializeCStruct0Tag

  /** Keyboard button event structure (event.key.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_KeyboardEvent = CStruct8[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, SDL_Keysym]
  object SDL_KeyboardEvent:
    given _tag: Tag[SDL_KeyboardEvent] =
      Tag.materializeCStruct8Tag[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, SDL_Keysym]
    def apply()(using Zone): Ptr[SDL_KeyboardEvent] = scala.scalanative.unsafe.alloc[SDL_KeyboardEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        state: Uint8,
        repeat: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        keysym: SDL_Keysym
    )(using Zone): Ptr[SDL_KeyboardEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).state = state
      (!____ptr).repeat = repeat
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).keysym = keysym
      ____ptr
    extension (struct: SDL_KeyboardEvent)
      def `type`: Uint32                    = struct._1
      def type_=(value: Uint32): Unit       = !struct.at1 = value
      def timestamp: Uint32                 = struct._2
      def timestamp_=(value: Uint32): Unit  = !struct.at2 = value
      def windowID: Uint32                  = struct._3
      def windowID_=(value: Uint32): Unit   = !struct.at3 = value
      def state: Uint8                      = struct._4
      def state_=(value: Uint8): Unit       = !struct.at4 = value
      def repeat: Uint8                     = struct._5
      def repeat_=(value: Uint8): Unit      = !struct.at5 = value
      def padding2: Uint8                   = struct._6
      def padding2_=(value: Uint8): Unit    = !struct.at6 = value
      def padding3: Uint8                   = struct._7
      def padding3_=(value: Uint8): Unit    = !struct.at7 = value
      def keysym: SDL_Keysym                = struct._8
      def keysym_=(value: SDL_Keysym): Unit = !struct.at8 = value

  /** The SDL keysym structure, used in key events.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  opaque type SDL_Keysym = CStruct4[SDL_Scancode, SDL_Keycode, Uint16, Uint32]
  object SDL_Keysym:
    given _tag: Tag[SDL_Keysym]              = Tag.materializeCStruct4Tag[SDL_Scancode, SDL_Keycode, Uint16, Uint32]
    def apply()(using Zone): Ptr[SDL_Keysym] = scala.scalanative.unsafe.alloc[SDL_Keysym](1)
    def apply(scancode: SDL_Scancode, sym: SDL_Keycode, mod: Uint16, unused: Uint32)(using Zone): Ptr[SDL_Keysym] =
      val ____ptr = apply()
      (!____ptr).scancode = scancode
      (!____ptr).sym = sym
      (!____ptr).mod = mod
      (!____ptr).unused = unused
      ____ptr
    extension (struct: SDL_Keysym)
      def scancode: SDL_Scancode                = struct._1
      def scancode_=(value: SDL_Scancode): Unit = !struct.at1 = value
      def sym: SDL_Keycode                      = struct._2
      def sym_=(value: SDL_Keycode): Unit       = !struct.at2 = value
      def mod: Uint16                           = struct._3
      def mod_=(value: Uint16): Unit            = !struct.at3 = value
      def unused: Uint32                        = struct._4
      def unused_=(value: Uint32): Unit         = !struct.at4 = value

  /** [bindgen] header: ./SDL_locale.h
    */
  opaque type SDL_Locale = CStruct2[CString, CString]
  object SDL_Locale:
    given _tag: Tag[SDL_Locale]              = Tag.materializeCStruct2Tag[CString, CString]
    def apply()(using Zone): Ptr[SDL_Locale] = scala.scalanative.unsafe.alloc[SDL_Locale](1)
    def apply(language: CString, country: CString)(using Zone): Ptr[SDL_Locale] =
      val ____ptr = apply()
      (!____ptr).language = language
      (!____ptr).country = country
      ____ptr
    extension (struct: SDL_Locale)
      def language: CString                = struct._1
      def language_=(value: CString): Unit = !struct.at1 = value
      def country: CString                 = struct._2
      def country_=(value: CString): Unit  = !struct.at2 = value

  /** Individual button data.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxButtonData = CStruct3[Uint32, CInt, CString]
  object SDL_MessageBoxButtonData:
    given _tag: Tag[SDL_MessageBoxButtonData]              = Tag.materializeCStruct3Tag[Uint32, CInt, CString]
    def apply()(using Zone): Ptr[SDL_MessageBoxButtonData] = scala.scalanative.unsafe.alloc[SDL_MessageBoxButtonData](1)
    def apply(flags: Uint32, buttonid: CInt, text: CString)(using Zone): Ptr[SDL_MessageBoxButtonData] =
      val ____ptr = apply()
      (!____ptr).flags = flags
      (!____ptr).buttonid = buttonid
      (!____ptr).text = text
      ____ptr
    extension (struct: SDL_MessageBoxButtonData)
      def flags: Uint32                 = struct._1
      def flags_=(value: Uint32): Unit  = !struct.at1 = value
      def buttonid: CInt                = struct._2
      def buttonid_=(value: CInt): Unit = !struct.at2 = value
      def text: CString                 = struct._3
      def text_=(value: CString): Unit  = !struct.at3 = value

  /** RGB value used in a message box color scheme
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxColor = CStruct3[Uint8, Uint8, Uint8]
  object SDL_MessageBoxColor:
    given _tag: Tag[SDL_MessageBoxColor]              = Tag.materializeCStruct3Tag[Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_MessageBoxColor] = scala.scalanative.unsafe.alloc[SDL_MessageBoxColor](1)
    def apply(r: Uint8, g: Uint8, b: Uint8)(using Zone): Ptr[SDL_MessageBoxColor] =
      val ____ptr = apply()
      (!____ptr).r = r
      (!____ptr).g = g
      (!____ptr).b = b
      ____ptr
    extension (struct: SDL_MessageBoxColor)
      def r: Uint8                = struct._1
      def r_=(value: Uint8): Unit = !struct.at1 = value
      def g: Uint8                = struct._2
      def g_=(value: Uint8): Unit = !struct.at2 = value
      def b: Uint8                = struct._3
      def b_=(value: Uint8): Unit = !struct.at3 = value

  /** A set of colors to use for message box dialogs
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxColorScheme = CStruct1[CArray[SDL_MessageBoxColor, Nat._5]]
  object SDL_MessageBoxColorScheme:
    given _tag: Tag[SDL_MessageBoxColorScheme] = Tag.materializeCStruct1Tag[CArray[SDL_MessageBoxColor, Nat._5]]
    def apply()(using Zone): Ptr[SDL_MessageBoxColorScheme] =
      scala.scalanative.unsafe.alloc[SDL_MessageBoxColorScheme](1)
    def apply(colors: CArray[SDL_MessageBoxColor, Nat._5])(using Zone): Ptr[SDL_MessageBoxColorScheme] =
      val ____ptr = apply()
      (!____ptr).colors = colors
      ____ptr
    extension (struct: SDL_MessageBoxColorScheme)
      def colors: CArray[SDL_MessageBoxColor, Nat._5]                = struct._1
      def colors_=(value: CArray[SDL_MessageBoxColor, Nat._5]): Unit = !struct.at1 = value

  /** MessageBox structure containing title, text, window, etc.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  opaque type SDL_MessageBoxData = CStruct7[Uint32, Ptr[SDL_Window], CString, CString, CInt, Ptr[
    SDL_MessageBoxButtonData
  ], Ptr[SDL_MessageBoxColorScheme]]
  object SDL_MessageBoxData:
    given _tag: Tag[SDL_MessageBoxData] = Tag.materializeCStruct7Tag[Uint32, Ptr[
      SDL_Window
    ], CString, CString, CInt, Ptr[SDL_MessageBoxButtonData], Ptr[SDL_MessageBoxColorScheme]]
    def apply()(using Zone): Ptr[SDL_MessageBoxData] = scala.scalanative.unsafe.alloc[SDL_MessageBoxData](1)
    def apply(
        flags: Uint32,
        window: Ptr[SDL_Window],
        title: CString,
        message: CString,
        numbuttons: CInt,
        buttons: Ptr[SDL_MessageBoxButtonData],
        colorScheme: Ptr[SDL_MessageBoxColorScheme]
    )(using Zone): Ptr[SDL_MessageBoxData] =
      val ____ptr = apply()
      (!____ptr).flags = flags
      (!____ptr).window = window
      (!____ptr).title = title
      (!____ptr).message = message
      (!____ptr).numbuttons = numbuttons
      (!____ptr).buttons = buttons
      (!____ptr).colorScheme = colorScheme
      ____ptr
    extension (struct: SDL_MessageBoxData)
      def flags: Uint32                                              = struct._1
      def flags_=(value: Uint32): Unit                               = !struct.at1 = value
      def window: Ptr[SDL_Window]                                    = struct._2
      def window_=(value: Ptr[SDL_Window]): Unit                     = !struct.at2 = value
      def title: CString                                             = struct._3
      def title_=(value: CString): Unit                              = !struct.at3 = value
      def message: CString                                           = struct._4
      def message_=(value: CString): Unit                            = !struct.at4 = value
      def numbuttons: CInt                                           = struct._5
      def numbuttons_=(value: CInt): Unit                            = !struct.at5 = value
      def buttons: Ptr[SDL_MessageBoxButtonData]                     = struct._6
      def buttons_=(value: Ptr[SDL_MessageBoxButtonData]): Unit      = !struct.at6 = value
      def colorScheme: Ptr[SDL_MessageBoxColorScheme]                = struct._7
      def colorScheme_=(value: Ptr[SDL_MessageBoxColorScheme]): Unit = !struct.at7 = value

  /** Mouse button event structure (event.button.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_MouseButtonEvent =
    CStruct10[Uint32, Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32, Sint32]
  object SDL_MouseButtonEvent:
    given _tag: Tag[SDL_MouseButtonEvent] =
      Tag.materializeCStruct10Tag[Uint32, Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_MouseButtonEvent] = scala.scalanative.unsafe.alloc[SDL_MouseButtonEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        which: Uint32,
        button: Uint8,
        state: Uint8,
        clicks: Uint8,
        padding1: Uint8,
        x: Sint32,
        y: Sint32
    )(using Zone): Ptr[SDL_MouseButtonEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).which = which
      (!____ptr).button = button
      (!____ptr).state = state
      (!____ptr).clicks = clicks
      (!____ptr).padding1 = padding1
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: SDL_MouseButtonEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def which: Uint32                    = struct._4
      def which_=(value: Uint32): Unit     = !struct.at4 = value
      def button: Uint8                    = struct._5
      def button_=(value: Uint8): Unit     = !struct.at5 = value
      def state: Uint8                     = struct._6
      def state_=(value: Uint8): Unit      = !struct.at6 = value
      def clicks: Uint8                    = struct._7
      def clicks_=(value: Uint8): Unit     = !struct.at7 = value
      def padding1: Uint8                  = struct._8
      def padding1_=(value: Uint8): Unit   = !struct.at8 = value
      def x: Sint32                        = struct._9
      def x_=(value: Sint32): Unit         = !struct.at9 = value
      def y: Sint32                        = struct._10
      def y_=(value: Sint32): Unit         = !struct.at10 = value

  /** Mouse motion event structure (event.motion.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_MouseMotionEvent = CStruct9[Uint32, Uint32, Uint32, Uint32, Uint32, Sint32, Sint32, Sint32, Sint32]
  object SDL_MouseMotionEvent:
    given _tag: Tag[SDL_MouseMotionEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, Uint32, Uint32, Uint32, Sint32, Sint32, Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_MouseMotionEvent] = scala.scalanative.unsafe.alloc[SDL_MouseMotionEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        which: Uint32,
        state: Uint32,
        x: Sint32,
        y: Sint32,
        xrel: Sint32,
        yrel: Sint32
    )(using Zone): Ptr[SDL_MouseMotionEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).which = which
      (!____ptr).state = state
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).xrel = xrel
      (!____ptr).yrel = yrel
      ____ptr
    extension (struct: SDL_MouseMotionEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def which: Uint32                    = struct._4
      def which_=(value: Uint32): Unit     = !struct.at4 = value
      def state: Uint32                    = struct._5
      def state_=(value: Uint32): Unit     = !struct.at5 = value
      def x: Sint32                        = struct._6
      def x_=(value: Sint32): Unit         = !struct.at6 = value
      def y: Sint32                        = struct._7
      def y_=(value: Sint32): Unit         = !struct.at7 = value
      def xrel: Sint32                     = struct._8
      def xrel_=(value: Sint32): Unit      = !struct.at8 = value
      def yrel: Sint32                     = struct._9
      def yrel_=(value: Sint32): Unit      = !struct.at9 = value

  /** Mouse wheel event structure (event.wheel.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_MouseWheelEvent =
    CStruct11[Uint32, Uint32, Uint32, Uint32, Sint32, Sint32, Uint32, Float, Float, Sint32, Sint32]
  object SDL_MouseWheelEvent:
    given _tag: Tag[SDL_MouseWheelEvent] =
      Tag.materializeCStruct11Tag[Uint32, Uint32, Uint32, Uint32, Sint32, Sint32, Uint32, Float, Float, Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_MouseWheelEvent] = scala.scalanative.unsafe.alloc[SDL_MouseWheelEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        which: Uint32,
        x: Sint32,
        y: Sint32,
        direction: Uint32,
        preciseX: Float,
        preciseY: Float,
        mouseX: Sint32,
        mouseY: Sint32
    )(using Zone): Ptr[SDL_MouseWheelEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).which = which
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).direction = direction
      (!____ptr).preciseX = preciseX
      (!____ptr).preciseY = preciseY
      (!____ptr).mouseX = mouseX
      (!____ptr).mouseY = mouseY
      ____ptr
    extension (struct: SDL_MouseWheelEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def which: Uint32                    = struct._4
      def which_=(value: Uint32): Unit     = !struct.at4 = value
      def x: Sint32                        = struct._5
      def x_=(value: Sint32): Unit         = !struct.at5 = value
      def y: Sint32                        = struct._6
      def y_=(value: Sint32): Unit         = !struct.at6 = value
      def direction: Uint32                = struct._7
      def direction_=(value: Uint32): Unit = !struct.at7 = value
      def preciseX: Float                  = struct._8
      def preciseX_=(value: Float): Unit   = !struct.at8 = value
      def preciseY: Float                  = struct._9
      def preciseY_=(value: Float): Unit   = !struct.at9 = value
      def mouseX: Sint32                   = struct._10
      def mouseX_=(value: Sint32): Unit    = !struct.at10 = value
      def mouseY: Sint32                   = struct._11
      def mouseY_=(value: Sint32): Unit    = !struct.at11 = value

  /** Multiple Finger Gesture Event (event.mgesture.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_MultiGestureEvent = CStruct9[Uint32, Uint32, SDL_TouchID, Float, Float, Float, Float, Uint16, Uint16]
  object SDL_MultiGestureEvent:
    given _tag: Tag[SDL_MultiGestureEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, SDL_TouchID, Float, Float, Float, Float, Uint16, Uint16]
    def apply()(using Zone): Ptr[SDL_MultiGestureEvent] = scala.scalanative.unsafe.alloc[SDL_MultiGestureEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        touchId: SDL_TouchID,
        dTheta: Float,
        dDist: Float,
        x: Float,
        y: Float,
        numFingers: Uint16,
        padding: Uint16
    )(using Zone): Ptr[SDL_MultiGestureEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).touchId = touchId
      (!____ptr).dTheta = dTheta
      (!____ptr).dDist = dDist
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).numFingers = numFingers
      (!____ptr).padding = padding
      ____ptr
    extension (struct: SDL_MultiGestureEvent)
      def `type`: Uint32                      = struct._1
      def type_=(value: Uint32): Unit         = !struct.at1 = value
      def timestamp: Uint32                   = struct._2
      def timestamp_=(value: Uint32): Unit    = !struct.at2 = value
      def touchId: SDL_TouchID                = struct._3
      def touchId_=(value: SDL_TouchID): Unit = !struct.at3 = value
      def dTheta: Float                       = struct._4
      def dTheta_=(value: Float): Unit        = !struct.at4 = value
      def dDist: Float                        = struct._5
      def dDist_=(value: Float): Unit         = !struct.at5 = value
      def x: Float                            = struct._6
      def x_=(value: Float): Unit             = !struct.at6 = value
      def y: Float                            = struct._7
      def y_=(value: Float): Unit             = !struct.at7 = value
      def numFingers: Uint16                  = struct._8
      def numFingers_=(value: Uint16): Unit   = !struct.at8 = value
      def padding: Uint16                     = struct._9
      def padding_=(value: Uint16): Unit      = !struct.at9 = value

  /** [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_Palette = CStruct4[CInt, Ptr[SDL_Color], Uint32, CInt]
  object SDL_Palette:
    given _tag: Tag[SDL_Palette]              = Tag.materializeCStruct4Tag[CInt, Ptr[SDL_Color], Uint32, CInt]
    def apply()(using Zone): Ptr[SDL_Palette] = scala.scalanative.unsafe.alloc[SDL_Palette](1)
    def apply(ncolors: CInt, colors: Ptr[SDL_Color], version: Uint32, refcount: CInt)(using Zone): Ptr[SDL_Palette] =
      val ____ptr = apply()
      (!____ptr).ncolors = ncolors
      (!____ptr).colors = colors
      (!____ptr).version = version
      (!____ptr).refcount = refcount
      ____ptr
    extension (struct: SDL_Palette)
      def ncolors: CInt                         = struct._1
      def ncolors_=(value: CInt): Unit          = !struct.at1 = value
      def colors: Ptr[SDL_Color]                = struct._2
      def colors_=(value: Ptr[SDL_Color]): Unit = !struct.at2 = value
      def version: Uint32                       = struct._3
      def version_=(value: Uint32): Unit        = !struct.at3 = value
      def refcount: CInt                        = struct._4
      def refcount_=(value: CInt): Unit         = !struct.at4 = value

  /** [bindgen] header: ./SDL_pixels.h
    */
  opaque type SDL_PixelFormat = CStruct19[
    Uint32,
    Ptr[SDL_Palette],
    Uint8,
    Uint8,
    CArray[Uint8, Nat._2],
    Uint32,
    Uint32,
    Uint32,
    Uint32,
    Uint8,
    Uint8,
    Uint8,
    Uint8,
    Uint8,
    Uint8,
    Uint8,
    Uint8,
    CInt,
    Ptr[Byte]
  ]
  object SDL_PixelFormat:
    given _tag: Tag[SDL_PixelFormat] = Tag.materializeCStruct19Tag[Uint32, Ptr[SDL_Palette], Uint8, Uint8, CArray[
      Uint8,
      Nat._2
    ], Uint32, Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Uint8, Uint8, Uint8, Uint8, CInt, Ptr[Byte]]
    def apply()(using Zone): Ptr[SDL_PixelFormat] = scala.scalanative.unsafe.alloc[SDL_PixelFormat](1)
    def apply(
        format: Uint32,
        palette: Ptr[SDL_Palette],
        BitsPerPixel: Uint8,
        BytesPerPixel: Uint8,
        padding: CArray[Uint8, Nat._2],
        Rmask: Uint32,
        Gmask: Uint32,
        Bmask: Uint32,
        Amask: Uint32,
        Rloss: Uint8,
        Gloss: Uint8,
        Bloss: Uint8,
        Aloss: Uint8,
        Rshift: Uint8,
        Gshift: Uint8,
        Bshift: Uint8,
        Ashift: Uint8,
        refcount: CInt,
        next: Ptr[SDL_PixelFormat]
    )(using Zone): Ptr[SDL_PixelFormat] =
      val ____ptr = apply()
      (!____ptr).format = format
      (!____ptr).palette = palette
      (!____ptr).BitsPerPixel = BitsPerPixel
      (!____ptr).BytesPerPixel = BytesPerPixel
      (!____ptr).padding = padding
      (!____ptr).Rmask = Rmask
      (!____ptr).Gmask = Gmask
      (!____ptr).Bmask = Bmask
      (!____ptr).Amask = Amask
      (!____ptr).Rloss = Rloss
      (!____ptr).Gloss = Gloss
      (!____ptr).Bloss = Bloss
      (!____ptr).Aloss = Aloss
      (!____ptr).Rshift = Rshift
      (!____ptr).Gshift = Gshift
      (!____ptr).Bshift = Bshift
      (!____ptr).Ashift = Ashift
      (!____ptr).refcount = refcount
      (!____ptr).next = next
      ____ptr
    extension (struct: SDL_PixelFormat)
      def format: Uint32                                = struct._1
      def format_=(value: Uint32): Unit                 = !struct.at1 = value
      def palette: Ptr[SDL_Palette]                     = struct._2
      def palette_=(value: Ptr[SDL_Palette]): Unit      = !struct.at2 = value
      def BitsPerPixel: Uint8                           = struct._3
      def BitsPerPixel_=(value: Uint8): Unit            = !struct.at3 = value
      def BytesPerPixel: Uint8                          = struct._4
      def BytesPerPixel_=(value: Uint8): Unit           = !struct.at4 = value
      def padding: CArray[Uint8, Nat._2]                = struct._5
      def padding_=(value: CArray[Uint8, Nat._2]): Unit = !struct.at5 = value
      def Rmask: Uint32                                 = struct._6
      def Rmask_=(value: Uint32): Unit                  = !struct.at6 = value
      def Gmask: Uint32                                 = struct._7
      def Gmask_=(value: Uint32): Unit                  = !struct.at7 = value
      def Bmask: Uint32                                 = struct._8
      def Bmask_=(value: Uint32): Unit                  = !struct.at8 = value
      def Amask: Uint32                                 = struct._9
      def Amask_=(value: Uint32): Unit                  = !struct.at9 = value
      def Rloss: Uint8                                  = struct._10
      def Rloss_=(value: Uint8): Unit                   = !struct.at10 = value
      def Gloss: Uint8                                  = struct._11
      def Gloss_=(value: Uint8): Unit                   = !struct.at11 = value
      def Bloss: Uint8                                  = struct._12
      def Bloss_=(value: Uint8): Unit                   = !struct.at12 = value
      def Aloss: Uint8                                  = struct._13
      def Aloss_=(value: Uint8): Unit                   = !struct.at13 = value
      def Rshift: Uint8                                 = struct._14
      def Rshift_=(value: Uint8): Unit                  = !struct.at14 = value
      def Gshift: Uint8                                 = struct._15
      def Gshift_=(value: Uint8): Unit                  = !struct.at15 = value
      def Bshift: Uint8                                 = struct._16
      def Bshift_=(value: Uint8): Unit                  = !struct.at16 = value
      def Ashift: Uint8                                 = struct._17
      def Ashift_=(value: Uint8): Unit                  = !struct.at17 = value
      def refcount: CInt                                = struct._18
      def refcount_=(value: CInt): Unit                 = !struct.at18 = value
      def next: Ptr[SDL_PixelFormat]                    = struct._19.asInstanceOf[Ptr[SDL_PixelFormat]]
      def next_=(value: Ptr[SDL_PixelFormat]): Unit     = !struct.at19 = value.asInstanceOf[Ptr[Byte]]

  /** The structure that defines a point (integer)
    *
    * [bindgen] header: ./SDL_rect.h
    */
  opaque type SDL_Point = CStruct2[CInt, CInt]
  object SDL_Point:
    given _tag: Tag[SDL_Point]              = Tag.materializeCStruct2Tag[CInt, CInt]
    def apply()(using Zone): Ptr[SDL_Point] = scala.scalanative.unsafe.alloc[SDL_Point](1)
    def apply(x: CInt, y: CInt)(using Zone): Ptr[SDL_Point] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      ____ptr
    extension (struct: SDL_Point)
      def x: CInt                = struct._1
      def x_=(value: CInt): Unit = !struct.at1 = value
      def y: CInt                = struct._2
      def y_=(value: CInt): Unit = !struct.at2 = value

  /** The "quit requested" event
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_QuitEvent = CStruct2[Uint32, Uint32]
  object SDL_QuitEvent:
    given _tag: Tag[SDL_QuitEvent]              = Tag.materializeCStruct2Tag[Uint32, Uint32]
    def apply()(using Zone): Ptr[SDL_QuitEvent] = scala.scalanative.unsafe.alloc[SDL_QuitEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32)(using Zone): Ptr[SDL_QuitEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      ____ptr
    extension (struct: SDL_QuitEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value

  /** This is the read/write operation structure -- very basic.
    *
    * [bindgen] header: ./SDL_rwops.h [MANUAL]
    */
  opaque type SDL_RWops = CStruct7[
    CFuncPtr1[Ptr[Byte], Sint64],
    CFuncPtr3[Ptr[Byte], Sint64, CInt, Sint64],
    CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t],
    CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t],
    CFuncPtr1[Ptr[Byte], CInt],
    Uint32,
    SDL_RWops.Union0
  ]
  object SDL_RWops:
    /** [bindgen] header: ./SDL_rwops.h
      */
    opaque type Union0 = CArray[Byte, Nat.Digit2[Nat._4, Nat._0]]
    object Union0:
      /** [bindgen] header: ./SDL_rwops.h
        */
      opaque type Struct0 = CStruct3[SDL_bool, Ptr[Byte], SDL_RWops.Union0.Struct0.Struct00]
      object Struct0:
        /** [bindgen] header: ./SDL_rwops.h
          */
        opaque type Struct00 = CStruct3[Ptr[Byte], size_t, size_t]
        object Struct00:
          given _tag: Tag[Struct00]              = Tag.materializeCStruct3Tag[Ptr[Byte], size_t, size_t]
          def apply()(using Zone): Ptr[Struct00] = scala.scalanative.unsafe.alloc[Struct00](1)
          def apply(data: Ptr[Byte], size: size_t, left: size_t)(using Zone): Ptr[Struct00] =
            val ____ptr = apply()
            (!____ptr).data = data
            (!____ptr).size = size
            (!____ptr).left = left
            ____ptr
          extension (struct: Struct00)
            def data: Ptr[Byte]                = struct._1
            def data_=(value: Ptr[Byte]): Unit = !struct.at1 = value
            def size: size_t                   = struct._2
            def size_=(value: size_t): Unit    = !struct.at2 = value
            def left: size_t                   = struct._3
            def left_=(value: size_t): Unit    = !struct.at3 = value
        given _tag: Tag[Struct0] = Tag.materializeCStruct3Tag[SDL_bool, Ptr[Byte], SDL_RWops.Union0.Struct0.Struct00]
        def apply()(using Zone): Ptr[Struct0] = scala.scalanative.unsafe.alloc[Struct0](1)
        def apply(append: SDL_bool, h: Ptr[Byte], buffer: SDL_RWops.Union0.Struct0.Struct00)(using Zone): Ptr[Struct0] =
          val ____ptr = apply()
          (!____ptr).append = append
          (!____ptr).h = h
          (!____ptr).buffer = buffer
          ____ptr
        extension (struct: Struct0)
          def append: SDL_bool                                         = struct._1
          def append_=(value: SDL_bool): Unit                          = !struct.at1 = value
          def h: Ptr[Byte]                                             = struct._2
          def h_=(value: Ptr[Byte]): Unit                              = !struct.at2 = value
          def buffer: SDL_RWops.Union0.Struct0.Struct00                = struct._3
          def buffer_=(value: SDL_RWops.Union0.Struct0.Struct00): Unit = !struct.at3 = value

      /** [bindgen] header: ./SDL_rwops.h
        */
      opaque type Struct1 = CStruct3[Ptr[Uint8], Ptr[Uint8], Ptr[Uint8]]
      object Struct1:
        given _tag: Tag[Struct1]              = Tag.materializeCStruct3Tag[Ptr[Uint8], Ptr[Uint8], Ptr[Uint8]]
        def apply()(using Zone): Ptr[Struct1] = scala.scalanative.unsafe.alloc[Struct1](1)
        def apply(base: Ptr[Uint8], here: Ptr[Uint8], stop: Ptr[Uint8])(using Zone): Ptr[Struct1] =
          val ____ptr = apply()
          (!____ptr).base = base
          (!____ptr).here = here
          (!____ptr).stop = stop
          ____ptr
        extension (struct: Struct1)
          def base: Ptr[Uint8]                = struct._1
          def base_=(value: Ptr[Uint8]): Unit = !struct.at1 = value
          def here: Ptr[Uint8]                = struct._2
          def here_=(value: Ptr[Uint8]): Unit = !struct.at2 = value
          def stop: Ptr[Uint8]                = struct._3
          def stop_=(value: Ptr[Uint8]): Unit = !struct.at3 = value

      /** [bindgen] header: ./SDL_rwops.h
        */
      opaque type Struct2 = CStruct2[Ptr[Byte], Ptr[Byte]]
      object Struct2:
        given _tag: Tag[Struct2]              = Tag.materializeCStruct2Tag[Ptr[Byte], Ptr[Byte]]
        def apply()(using Zone): Ptr[Struct2] = scala.scalanative.unsafe.alloc[Struct2](1)
        def apply(data1: Ptr[Byte], data2: Ptr[Byte])(using Zone): Ptr[Struct2] =
          val ____ptr = apply()
          (!____ptr).data1 = data1
          (!____ptr).data2 = data2
          ____ptr
        extension (struct: Struct2)
          def data1: Ptr[Byte]                = struct._1
          def data1_=(value: Ptr[Byte]): Unit = !struct.at1 = value
          def data2: Ptr[Byte]                = struct._2
          def data2_=(value: Ptr[Byte]): Unit = !struct.at2 = value
      given _tag: Tag[Union0] =
        Tag.CArray[CChar, Nat.Digit2[Nat._4, Nat._0]](Tag.Byte, Tag.Digit2[Nat._4, Nat._0](Tag.Nat4, Tag.Nat0))
      def apply()(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        ___ptr
      @scala.annotation.targetName("apply_windowsio")
      def apply(windowsio: SDL_RWops.Union0.Struct0)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct0]].update(0, windowsio)
        ___ptr
      @scala.annotation.targetName("apply_mem")
      def apply(mem: SDL_RWops.Union0.Struct1)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct1]].update(0, mem)
        ___ptr
      @scala.annotation.targetName("apply_unknown")
      def apply(unknown: SDL_RWops.Union0.Struct2)(using Zone): Ptr[Union0] =
        val ___ptr = alloc[Union0](1)
        val un     = !___ptr
        un.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct2]].update(0, unknown)
        ___ptr
      extension (struct: Union0)
        def windowsio: SDL_RWops.Union0.Struct0 = !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct0]]
        def windowsio_=(value: SDL_RWops.Union0.Struct0): Unit =
          !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct0]] = value
        def mem: SDL_RWops.Union0.Struct1 = !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct1]]
        def mem_=(value: SDL_RWops.Union0.Struct1): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct1]] =
          value
        def unknown: SDL_RWops.Union0.Struct2 = !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct2]]
        def unknown_=(value: SDL_RWops.Union0.Struct2): Unit =
          !struct.at(0).asInstanceOf[Ptr[SDL_RWops.Union0.Struct2]] = value
    given _tag: Tag[SDL_RWops] = Tag.materializeCStruct7Tag[
      CFuncPtr1[Ptr[Byte], Sint64],
      CFuncPtr3[Ptr[Byte], Sint64, CInt, Sint64],
      CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t],
      CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t],
      CFuncPtr1[Ptr[Byte], CInt],
      Uint32,
      SDL_RWops.Union0
    ]
    def apply()(using Zone): Ptr[SDL_RWops] = scala.scalanative.unsafe.alloc[SDL_RWops](1)
    def apply(
        size: CFuncPtr1[Ptr[SDL_RWops], Sint64],
        seek: CFuncPtr3[Ptr[SDL_RWops], Sint64, CInt, Sint64],
        read: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t],
        write: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t],
        close: CFuncPtr1[Ptr[SDL_RWops], CInt],
        `type`: Uint32,
        hidden: SDL_RWops.Union0
    )(using Zone): Ptr[SDL_RWops] =
      val ____ptr = apply()
      (!____ptr).size = size
      (!____ptr).seek = seek
      (!____ptr).read = read
      (!____ptr).write = write
      (!____ptr).close = close
      (!____ptr).`type` = `type`
      (!____ptr).hidden = hidden
      ____ptr
    extension (struct: SDL_RWops)
      def size: CFuncPtr1[Ptr[SDL_RWops], Sint64] = struct._1.asInstanceOf[CFuncPtr1[Ptr[SDL_RWops], Sint64]]
      def size_=(value: CFuncPtr1[Ptr[SDL_RWops], Sint64]): Unit = !struct.at1 =
        value.asInstanceOf[CFuncPtr1[Ptr[Byte], Sint64]]
      def seek: CFuncPtr3[Ptr[SDL_RWops], Sint64, CInt, Sint64] =
        struct._2.asInstanceOf[CFuncPtr3[Ptr[SDL_RWops], Sint64, CInt, Sint64]]
      def seek_=(value: CFuncPtr3[Ptr[SDL_RWops], Sint64, CInt, Sint64]): Unit = !struct.at2 =
        value.asInstanceOf[CFuncPtr3[Ptr[Byte], Sint64, CInt, Sint64]]
      def read: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t] =
        struct._3.asInstanceOf[CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t]]
      def read_=(value: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t]): Unit = !struct.at3 =
        value.asInstanceOf[CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t]]
      def write: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t] =
        struct._4.asInstanceOf[CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t]]
      def write_=(value: CFuncPtr4[Ptr[SDL_RWops], Ptr[Byte], size_t, size_t, size_t]): Unit = !struct.at4 =
        value.asInstanceOf[CFuncPtr4[Ptr[Byte], Ptr[Byte], size_t, size_t, size_t]]
      def close: CFuncPtr1[Ptr[SDL_RWops], CInt] = struct._5.asInstanceOf[CFuncPtr1[Ptr[SDL_RWops], CInt]]
      def close_=(value: CFuncPtr1[Ptr[SDL_RWops], CInt]): Unit = !struct.at5 =
        value.asInstanceOf[CFuncPtr1[Ptr[Byte], CInt]]
      def `type`: Uint32                          = struct._6
      def type_=(value: Uint32): Unit             = !struct.at6 = value
      def hidden: SDL_RWops.Union0                = struct._7
      def hidden_=(value: SDL_RWops.Union0): Unit = !struct.at7 = value

  /** A rectangle, with the origin at the upper left (integer).
    *
    * [bindgen] header: ./SDL_rect.h
    */
  opaque type SDL_Rect = CStruct4[CInt, CInt, CInt, CInt]
  object SDL_Rect:
    given _tag: Tag[SDL_Rect]              = Tag.materializeCStruct4Tag[CInt, CInt, CInt, CInt]
    def apply()(using Zone): Ptr[SDL_Rect] = scala.scalanative.unsafe.alloc[SDL_Rect](1)
    def apply(x: CInt, y: CInt, w: CInt, h: CInt)(using Zone): Ptr[SDL_Rect] =
      val ____ptr = apply()
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).w = w
      (!____ptr).h = h
      ____ptr
    extension (struct: SDL_Rect)
      def x: CInt                = struct._1
      def x_=(value: CInt): Unit = !struct.at1 = value
      def y: CInt                = struct._2
      def y_=(value: CInt): Unit = !struct.at2 = value
      def w: CInt                = struct._3
      def w_=(value: CInt): Unit = !struct.at3 = value
      def h: CInt                = struct._4
      def h_=(value: CInt): Unit = !struct.at4 = value

  /** A structure representing rendering state
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_Renderer = CStruct0
  object SDL_Renderer:
    given _tag: Tag[SDL_Renderer] = Tag.materializeCStruct0Tag

  /** Information on the capabilities of a render driver or context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_RendererInfo =
    CStruct6[CString, Uint32, Uint32, CArray[Uint32, Nat.Digit2[Nat._1, Nat._6]], CInt, CInt]
  object SDL_RendererInfo:
    given _tag: Tag[SDL_RendererInfo] =
      Tag.materializeCStruct6Tag[CString, Uint32, Uint32, CArray[Uint32, Nat.Digit2[Nat._1, Nat._6]], CInt, CInt]
    def apply()(using Zone): Ptr[SDL_RendererInfo] = scala.scalanative.unsafe.alloc[SDL_RendererInfo](1)
    def apply(
        name: CString,
        flags: Uint32,
        num_texture_formats: Uint32,
        texture_formats: CArray[Uint32, Nat.Digit2[Nat._1, Nat._6]],
        max_texture_width: CInt,
        max_texture_height: CInt
    )(using Zone): Ptr[SDL_RendererInfo] =
      val ____ptr = apply()
      (!____ptr).name = name
      (!____ptr).flags = flags
      (!____ptr).num_texture_formats = num_texture_formats
      (!____ptr).texture_formats = texture_formats
      (!____ptr).max_texture_width = max_texture_width
      (!____ptr).max_texture_height = max_texture_height
      ____ptr
    extension (struct: SDL_RendererInfo)
      def name: CString                                                              = struct._1
      def name_=(value: CString): Unit                                               = !struct.at1 = value
      def flags: Uint32                                                              = struct._2
      def flags_=(value: Uint32): Unit                                               = !struct.at2 = value
      def num_texture_formats: Uint32                                                = struct._3
      def num_texture_formats_=(value: Uint32): Unit                                 = !struct.at3 = value
      def texture_formats: CArray[Uint32, Nat.Digit2[Nat._1, Nat._6]]                = struct._4
      def texture_formats_=(value: CArray[Uint32, Nat.Digit2[Nat._1, Nat._6]]): Unit = !struct.at4 = value
      def max_texture_width: CInt                                                    = struct._5
      def max_texture_width_=(value: CInt): Unit                                     = !struct.at5 = value
      def max_texture_height: CInt                                                   = struct._6
      def max_texture_height_=(value: CInt): Unit                                    = !struct.at6 = value

  /** SDL_sensor.h
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  opaque type SDL_Sensor = CStruct0
  object SDL_Sensor:
    given _tag: Tag[SDL_Sensor] = Tag.materializeCStruct0Tag

  /** Sensor event structure (event.sensor.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_SensorEvent = CStruct5[Uint32, Uint32, Sint32, CArray[Float, Nat._6], Uint64]
  object SDL_SensorEvent:
    given _tag: Tag[SDL_SensorEvent] = Tag.materializeCStruct5Tag[Uint32, Uint32, Sint32, CArray[Float, Nat._6], Uint64]
    def apply()(using Zone): Ptr[SDL_SensorEvent] = scala.scalanative.unsafe.alloc[SDL_SensorEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, which: Sint32, data: CArray[Float, Nat._6], timestamp_us: Uint64)(using
        Zone
    ): Ptr[SDL_SensorEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).which = which
      (!____ptr).data = data
      (!____ptr).timestamp_us = timestamp_us
      ____ptr
    extension (struct: SDL_SensorEvent)
      def `type`: Uint32                             = struct._1
      def type_=(value: Uint32): Unit                = !struct.at1 = value
      def timestamp: Uint32                          = struct._2
      def timestamp_=(value: Uint32): Unit           = !struct.at2 = value
      def which: Sint32                              = struct._3
      def which_=(value: Sint32): Unit               = !struct.at3 = value
      def data: CArray[Float, Nat._6]                = struct._4
      def data_=(value: CArray[Float, Nat._6]): Unit = !struct.at4 = value
      def timestamp_us: Uint64                       = struct._5
      def timestamp_us_=(value: Uint64): Unit        = !struct.at5 = value

  /** A collection of pixels used in software blitting.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  opaque type SDL_Surface = CStruct12[Uint32, Ptr[Byte], CInt, CInt, CInt, Ptr[Byte], Ptr[Byte], CInt, Ptr[
    Byte
  ], SDL_Rect, Ptr[SDL_BlitMap], CInt]
  object SDL_Surface:
    given _tag: Tag[SDL_Surface] = Tag.materializeCStruct12Tag[Uint32, Ptr[Byte], CInt, CInt, CInt, Ptr[Byte], Ptr[
      Byte
    ], CInt, Ptr[Byte], SDL_Rect, Ptr[SDL_BlitMap], CInt]
    def apply()(using Zone): Ptr[SDL_Surface] = scala.scalanative.unsafe.alloc[SDL_Surface](1)
    def apply(
        flags: Uint32,
        format: Ptr[SDL_PixelFormat],
        w: CInt,
        h: CInt,
        pitch: CInt,
        pixels: Ptr[Byte],
        userdata: Ptr[Byte],
        locked: CInt,
        list_blitmap: Ptr[Byte],
        clip_rect: SDL_Rect,
        map: Ptr[SDL_BlitMap],
        refcount: CInt
    )(using Zone): Ptr[SDL_Surface] =
      val ____ptr = apply()
      (!____ptr).flags = flags
      (!____ptr).format = format
      (!____ptr).w = w
      (!____ptr).h = h
      (!____ptr).pitch = pitch
      (!____ptr).pixels = pixels
      (!____ptr).userdata = userdata
      (!____ptr).locked = locked
      (!____ptr).list_blitmap = list_blitmap
      (!____ptr).clip_rect = clip_rect
      (!____ptr).map = map
      (!____ptr).refcount = refcount
      ____ptr
    extension (struct: SDL_Surface)
      def flags: Uint32                               = struct._1
      def flags_=(value: Uint32): Unit                = !struct.at1 = value
      def format: Ptr[SDL_PixelFormat]                = struct._2.asInstanceOf[Ptr[SDL_PixelFormat]]
      def format_=(value: Ptr[SDL_PixelFormat]): Unit = !struct.at2 = value.asInstanceOf[Ptr[Byte]]
      def w: CInt                                     = struct._3
      def w_=(value: CInt): Unit                      = !struct.at3 = value
      def h: CInt                                     = struct._4
      def h_=(value: CInt): Unit                      = !struct.at4 = value
      def pitch: CInt                                 = struct._5
      def pitch_=(value: CInt): Unit                  = !struct.at5 = value
      def pixels: Ptr[Byte]                           = struct._6
      def pixels_=(value: Ptr[Byte]): Unit            = !struct.at6 = value
      def userdata: Ptr[Byte]                         = struct._7
      def userdata_=(value: Ptr[Byte]): Unit          = !struct.at7 = value
      def locked: CInt                                = struct._8
      def locked_=(value: CInt): Unit                 = !struct.at8 = value
      def list_blitmap: Ptr[Byte]                     = struct._9
      def list_blitmap_=(value: Ptr[Byte]): Unit      = !struct.at9 = value
      def clip_rect: SDL_Rect                         = struct._10
      def clip_rect_=(value: SDL_Rect): Unit          = !struct.at10 = value
      def map: Ptr[SDL_BlitMap]                       = struct._11
      def map_=(value: Ptr[SDL_BlitMap]): Unit        = !struct.at11 = value
      def refcount: CInt                              = struct._12
      def refcount_=(value: CInt): Unit               = !struct.at12 = value

  /** A video driver dependent system event (event.syswm.*) This event is disabled by default, you can enable it with SDL_EventState()
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_SysWMEvent = CStruct3[Uint32, Uint32, Ptr[SDL_SysWMmsg]]
  object SDL_SysWMEvent:
    given _tag: Tag[SDL_SysWMEvent]              = Tag.materializeCStruct3Tag[Uint32, Uint32, Ptr[SDL_SysWMmsg]]
    def apply()(using Zone): Ptr[SDL_SysWMEvent] = scala.scalanative.unsafe.alloc[SDL_SysWMEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, msg: Ptr[SDL_SysWMmsg])(using Zone): Ptr[SDL_SysWMEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).msg = msg
      ____ptr
    extension (struct: SDL_SysWMEvent)
      def `type`: Uint32                        = struct._1
      def type_=(value: Uint32): Unit           = !struct.at1 = value
      def timestamp: Uint32                     = struct._2
      def timestamp_=(value: Uint32): Unit      = !struct.at2 = value
      def msg: Ptr[SDL_SysWMmsg]                = struct._3
      def msg_=(value: Ptr[SDL_SysWMmsg]): Unit = !struct.at3 = value

  /** [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_SysWMmsg = CStruct0
  object SDL_SysWMmsg:
    given _tag: Tag[SDL_SysWMmsg] = Tag.materializeCStruct0Tag

  /** Keyboard text editing event structure (event.edit.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_TextEditingEvent =
    CStruct6[Uint32, Uint32, Uint32, CArray[CChar, Nat.Digit2[Nat._3, Nat._2]], Sint32, Sint32]
  object SDL_TextEditingEvent:
    given _tag: Tag[SDL_TextEditingEvent] =
      Tag.materializeCStruct6Tag[Uint32, Uint32, Uint32, CArray[CChar, Nat.Digit2[Nat._3, Nat._2]], Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_TextEditingEvent] = scala.scalanative.unsafe.alloc[SDL_TextEditingEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        text: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]],
        start: Sint32,
        length: Sint32
    )(using Zone): Ptr[SDL_TextEditingEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).text = text
      (!____ptr).start = start
      (!____ptr).length = length
      ____ptr
    extension (struct: SDL_TextEditingEvent)
      def `type`: Uint32                                                 = struct._1
      def type_=(value: Uint32): Unit                                    = !struct.at1 = value
      def timestamp: Uint32                                              = struct._2
      def timestamp_=(value: Uint32): Unit                               = !struct.at2 = value
      def windowID: Uint32                                               = struct._3
      def windowID_=(value: Uint32): Unit                                = !struct.at3 = value
      def text: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]                = struct._4
      def text_=(value: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]): Unit = !struct.at4 = value
      def start: Sint32                                                  = struct._5
      def start_=(value: Sint32): Unit                                   = !struct.at5 = value
      def length: Sint32                                                 = struct._6
      def length_=(value: Sint32): Unit                                  = !struct.at6 = value

  /** Extended keyboard text editing event structure (event.editExt.*) when text would be truncated if stored in the text buffer SDL_TextEditingEvent
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_TextEditingExtEvent = CStruct6[Uint32, Uint32, Uint32, CString, Sint32, Sint32]
  object SDL_TextEditingExtEvent:
    given _tag: Tag[SDL_TextEditingExtEvent] =
      Tag.materializeCStruct6Tag[Uint32, Uint32, Uint32, CString, Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_TextEditingExtEvent] = scala.scalanative.unsafe.alloc[SDL_TextEditingExtEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, windowID: Uint32, text: CString, start: Sint32, length: Sint32)(using
        Zone
    ): Ptr[SDL_TextEditingExtEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).text = text
      (!____ptr).start = start
      (!____ptr).length = length
      ____ptr
    extension (struct: SDL_TextEditingExtEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def text: CString                    = struct._4
      def text_=(value: CString): Unit     = !struct.at4 = value
      def start: Sint32                    = struct._5
      def start_=(value: Sint32): Unit     = !struct.at5 = value
      def length: Sint32                   = struct._6
      def length_=(value: Sint32): Unit    = !struct.at6 = value

  /** Keyboard text input event structure (event.text.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_TextInputEvent = CStruct4[Uint32, Uint32, Uint32, CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]]
  object SDL_TextInputEvent:
    given _tag: Tag[SDL_TextInputEvent] =
      Tag.materializeCStruct4Tag[Uint32, Uint32, Uint32, CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]]
    def apply()(using Zone): Ptr[SDL_TextInputEvent] = scala.scalanative.unsafe.alloc[SDL_TextInputEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, windowID: Uint32, text: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]])(
        using Zone
    ): Ptr[SDL_TextInputEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).text = text
      ____ptr
    extension (struct: SDL_TextInputEvent)
      def `type`: Uint32                                                 = struct._1
      def type_=(value: Uint32): Unit                                    = !struct.at1 = value
      def timestamp: Uint32                                              = struct._2
      def timestamp_=(value: Uint32): Unit                               = !struct.at2 = value
      def windowID: Uint32                                               = struct._3
      def windowID_=(value: Uint32): Unit                                = !struct.at3 = value
      def text: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]                = struct._4
      def text_=(value: CArray[CChar, Nat.Digit2[Nat._3, Nat._2]]): Unit = !struct.at4 = value

  /** An efficient driver-specific representation of pixel data
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_Texture = CStruct0
  object SDL_Texture:
    given _tag: Tag[SDL_Texture] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_thread.h
    */
  opaque type SDL_Thread = CStruct0
  object SDL_Thread:
    given _tag: Tag[SDL_Thread] = Tag.materializeCStruct0Tag

  /** Touch finger event structure (event.tfinger.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_TouchFingerEvent =
    CStruct10[Uint32, Uint32, SDL_TouchID, SDL_FingerID, Float, Float, Float, Float, Float, Uint32]
  object SDL_TouchFingerEvent:
    given _tag: Tag[SDL_TouchFingerEvent] =
      Tag.materializeCStruct10Tag[Uint32, Uint32, SDL_TouchID, SDL_FingerID, Float, Float, Float, Float, Float, Uint32]
    def apply()(using Zone): Ptr[SDL_TouchFingerEvent] = scala.scalanative.unsafe.alloc[SDL_TouchFingerEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        touchId: SDL_TouchID,
        fingerId: SDL_FingerID,
        x: Float,
        y: Float,
        dx: Float,
        dy: Float,
        pressure: Float,
        windowID: Uint32
    )(using Zone): Ptr[SDL_TouchFingerEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).touchId = touchId
      (!____ptr).fingerId = fingerId
      (!____ptr).x = x
      (!____ptr).y = y
      (!____ptr).dx = dx
      (!____ptr).dy = dy
      (!____ptr).pressure = pressure
      (!____ptr).windowID = windowID
      ____ptr
    extension (struct: SDL_TouchFingerEvent)
      def `type`: Uint32                        = struct._1
      def type_=(value: Uint32): Unit           = !struct.at1 = value
      def timestamp: Uint32                     = struct._2
      def timestamp_=(value: Uint32): Unit      = !struct.at2 = value
      def touchId: SDL_TouchID                  = struct._3
      def touchId_=(value: SDL_TouchID): Unit   = !struct.at3 = value
      def fingerId: SDL_FingerID                = struct._4
      def fingerId_=(value: SDL_FingerID): Unit = !struct.at4 = value
      def x: Float                              = struct._5
      def x_=(value: Float): Unit               = !struct.at5 = value
      def y: Float                              = struct._6
      def y_=(value: Float): Unit               = !struct.at6 = value
      def dx: Float                             = struct._7
      def dx_=(value: Float): Unit              = !struct.at7 = value
      def dy: Float                             = struct._8
      def dy_=(value: Float): Unit              = !struct.at8 = value
      def pressure: Float                       = struct._9
      def pressure_=(value: Float): Unit        = !struct.at9 = value
      def windowID: Uint32                      = struct._10
      def windowID_=(value: Uint32): Unit       = !struct.at10 = value

  /** A user-defined event type (event.user.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_UserEvent = CStruct6[Uint32, Uint32, Uint32, Sint32, Ptr[Byte], Ptr[Byte]]
  object SDL_UserEvent:
    given _tag: Tag[SDL_UserEvent] = Tag.materializeCStruct6Tag[Uint32, Uint32, Uint32, Sint32, Ptr[Byte], Ptr[Byte]]
    def apply()(using Zone): Ptr[SDL_UserEvent] = scala.scalanative.unsafe.alloc[SDL_UserEvent](1)
    def apply(`type`: Uint32, timestamp: Uint32, windowID: Uint32, code: Sint32, data1: Ptr[Byte], data2: Ptr[Byte])(
        using Zone
    ): Ptr[SDL_UserEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).code = code
      (!____ptr).data1 = data1
      (!____ptr).data2 = data2
      ____ptr
    extension (struct: SDL_UserEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def code: Sint32                     = struct._4
      def code_=(value: Sint32): Unit      = !struct.at4 = value
      def data1: Ptr[Byte]                 = struct._5
      def data1_=(value: Ptr[Byte]): Unit  = !struct.at5 = value
      def data2: Ptr[Byte]                 = struct._6
      def data2_=(value: Ptr[Byte]): Unit  = !struct.at6 = value

  /** Vertex structure
    *
    * [bindgen] header: ./SDL_render.h
    */
  opaque type SDL_Vertex = CStruct3[SDL_FPoint, SDL_Color, SDL_FPoint]
  object SDL_Vertex:
    given _tag: Tag[SDL_Vertex]              = Tag.materializeCStruct3Tag[SDL_FPoint, SDL_Color, SDL_FPoint]
    def apply()(using Zone): Ptr[SDL_Vertex] = scala.scalanative.unsafe.alloc[SDL_Vertex](1)
    def apply(position: SDL_FPoint, color: SDL_Color, tex_coord: SDL_FPoint)(using Zone): Ptr[SDL_Vertex] =
      val ____ptr = apply()
      (!____ptr).position = position
      (!____ptr).color = color
      (!____ptr).tex_coord = tex_coord
      ____ptr
    extension (struct: SDL_Vertex)
      def position: SDL_FPoint                 = struct._1
      def position_=(value: SDL_FPoint): Unit  = !struct.at1 = value
      def color: SDL_Color                     = struct._2
      def color_=(value: SDL_Color): Unit      = !struct.at2 = value
      def tex_coord: SDL_FPoint                = struct._3
      def tex_coord_=(value: SDL_FPoint): Unit = !struct.at3 = value

  /** The structure that defines an extended virtual joystick description
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  opaque type SDL_VirtualJoystickDesc = CStruct18[
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint16,
    Uint32,
    Uint32,
    CString,
    Ptr[Byte],
    CFuncPtr1[Ptr[Byte], Unit],
    CFuncPtr2[Ptr[Byte], CInt, Unit],
    CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
    CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
    CFuncPtr4[Ptr[Byte], Uint8, Uint8, Uint8, CInt],
    CFuncPtr3[Ptr[Byte], Ptr[Byte], CInt, CInt]
  ]
  object SDL_VirtualJoystickDesc:
    given _tag: Tag[SDL_VirtualJoystickDesc] = Tag.materializeCStruct18Tag[
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint16,
      Uint32,
      Uint32,
      CString,
      Ptr[Byte],
      CFuncPtr1[Ptr[Byte], Unit],
      CFuncPtr2[Ptr[Byte], CInt, Unit],
      CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
      CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
      CFuncPtr4[Ptr[Byte], Uint8, Uint8, Uint8, CInt],
      CFuncPtr3[Ptr[Byte], Ptr[Byte], CInt, CInt]
    ]
    def apply()(using Zone): Ptr[SDL_VirtualJoystickDesc] = scala.scalanative.unsafe.alloc[SDL_VirtualJoystickDesc](1)
    def apply(
        version: Uint16,
        `type`: Uint16,
        naxes: Uint16,
        nbuttons: Uint16,
        nhats: Uint16,
        vendor_id: Uint16,
        product_id: Uint16,
        padding: Uint16,
        button_mask: Uint32,
        axis_mask: Uint32,
        name: CString,
        userdata: Ptr[Byte],
        Update: CFuncPtr1[Ptr[Byte], Unit],
        SetPlayerIndex: CFuncPtr2[Ptr[Byte], CInt, Unit],
        Rumble: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
        RumbleTriggers: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt],
        SetLED: CFuncPtr4[Ptr[Byte], Uint8, Uint8, Uint8, CInt],
        SendEffect: CFuncPtr3[Ptr[Byte], Ptr[Byte], CInt, CInt]
    )(using Zone): Ptr[SDL_VirtualJoystickDesc] =
      val ____ptr = apply()
      (!____ptr).version = version
      (!____ptr).`type` = `type`
      (!____ptr).naxes = naxes
      (!____ptr).nbuttons = nbuttons
      (!____ptr).nhats = nhats
      (!____ptr).vendor_id = vendor_id
      (!____ptr).product_id = product_id
      (!____ptr).padding = padding
      (!____ptr).button_mask = button_mask
      (!____ptr).axis_mask = axis_mask
      (!____ptr).name = name
      (!____ptr).userdata = userdata
      (!____ptr).Update = Update
      (!____ptr).SetPlayerIndex = SetPlayerIndex
      (!____ptr).Rumble = Rumble
      (!____ptr).RumbleTriggers = RumbleTriggers
      (!____ptr).SetLED = SetLED
      (!____ptr).SendEffect = SendEffect
      ____ptr
    extension (struct: SDL_VirtualJoystickDesc)
      def version: Uint16                                                           = struct._1
      def version_=(value: Uint16): Unit                                            = !struct.at1 = value
      def `type`: Uint16                                                            = struct._2
      def type_=(value: Uint16): Unit                                               = !struct.at2 = value
      def naxes: Uint16                                                             = struct._3
      def naxes_=(value: Uint16): Unit                                              = !struct.at3 = value
      def nbuttons: Uint16                                                          = struct._4
      def nbuttons_=(value: Uint16): Unit                                           = !struct.at4 = value
      def nhats: Uint16                                                             = struct._5
      def nhats_=(value: Uint16): Unit                                              = !struct.at5 = value
      def vendor_id: Uint16                                                         = struct._6
      def vendor_id_=(value: Uint16): Unit                                          = !struct.at6 = value
      def product_id: Uint16                                                        = struct._7
      def product_id_=(value: Uint16): Unit                                         = !struct.at7 = value
      def padding: Uint16                                                           = struct._8
      def padding_=(value: Uint16): Unit                                            = !struct.at8 = value
      def button_mask: Uint32                                                       = struct._9
      def button_mask_=(value: Uint32): Unit                                        = !struct.at9 = value
      def axis_mask: Uint32                                                         = struct._10
      def axis_mask_=(value: Uint32): Unit                                          = !struct.at10 = value
      def name: CString                                                             = struct._11
      def name_=(value: CString): Unit                                              = !struct.at11 = value
      def userdata: Ptr[Byte]                                                       = struct._12
      def userdata_=(value: Ptr[Byte]): Unit                                        = !struct.at12 = value
      def Update: CFuncPtr1[Ptr[Byte], Unit]                                        = struct._13
      def Update_=(value: CFuncPtr1[Ptr[Byte], Unit]): Unit                         = !struct.at13 = value
      def SetPlayerIndex: CFuncPtr2[Ptr[Byte], CInt, Unit]                          = struct._14
      def SetPlayerIndex_=(value: CFuncPtr2[Ptr[Byte], CInt, Unit]): Unit           = !struct.at14 = value
      def Rumble: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt]                        = struct._15
      def Rumble_=(value: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt]): Unit         = !struct.at15 = value
      def RumbleTriggers: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt]                = struct._16
      def RumbleTriggers_=(value: CFuncPtr3[Ptr[Byte], Uint16, Uint16, CInt]): Unit = !struct.at16 = value
      def SetLED: CFuncPtr4[Ptr[Byte], Uint8, Uint8, Uint8, CInt]                   = struct._17
      def SetLED_=(value: CFuncPtr4[Ptr[Byte], Uint8, Uint8, Uint8, CInt]): Unit    = !struct.at17 = value
      def SendEffect: CFuncPtr3[Ptr[Byte], Ptr[Byte], CInt, CInt]                   = struct._18
      def SendEffect_=(value: CFuncPtr3[Ptr[Byte], Ptr[Byte], CInt, CInt]): Unit    = !struct.at18 = value

  /** [bindgen] header: ./SDL_video.h
    */
  opaque type SDL_Window = CStruct0
  object SDL_Window:
    given _tag: Tag[SDL_Window] = Tag.materializeCStruct0Tag

  /** Window state change event data (event.window.*)
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_WindowEvent = CStruct9[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32, Sint32]
  object SDL_WindowEvent:
    given _tag: Tag[SDL_WindowEvent] =
      Tag.materializeCStruct9Tag[Uint32, Uint32, Uint32, Uint8, Uint8, Uint8, Uint8, Sint32, Sint32]
    def apply()(using Zone): Ptr[SDL_WindowEvent] = scala.scalanative.unsafe.alloc[SDL_WindowEvent](1)
    def apply(
        `type`: Uint32,
        timestamp: Uint32,
        windowID: Uint32,
        event: Uint8,
        padding1: Uint8,
        padding2: Uint8,
        padding3: Uint8,
        data1: Sint32,
        data2: Sint32
    )(using Zone): Ptr[SDL_WindowEvent] =
      val ____ptr = apply()
      (!____ptr).`type` = `type`
      (!____ptr).timestamp = timestamp
      (!____ptr).windowID = windowID
      (!____ptr).event = event
      (!____ptr).padding1 = padding1
      (!____ptr).padding2 = padding2
      (!____ptr).padding3 = padding3
      (!____ptr).data1 = data1
      (!____ptr).data2 = data2
      ____ptr
    extension (struct: SDL_WindowEvent)
      def `type`: Uint32                   = struct._1
      def type_=(value: Uint32): Unit      = !struct.at1 = value
      def timestamp: Uint32                = struct._2
      def timestamp_=(value: Uint32): Unit = !struct.at2 = value
      def windowID: Uint32                 = struct._3
      def windowID_=(value: Uint32): Unit  = !struct.at3 = value
      def event: Uint8                     = struct._4
      def event_=(value: Uint8): Unit      = !struct.at4 = value
      def padding1: Uint8                  = struct._5
      def padding1_=(value: Uint8): Unit   = !struct.at5 = value
      def padding2: Uint8                  = struct._6
      def padding2_=(value: Uint8): Unit   = !struct.at6 = value
      def padding3: Uint8                  = struct._7
      def padding3_=(value: Uint8): Unit   = !struct.at7 = value
      def data1: Sint32                    = struct._8
      def data1_=(value: Sint32): Unit     = !struct.at8 = value
      def data2: Sint32                    = struct._9
      def data2_=(value: Sint32): Unit     = !struct.at9 = value

  /** A struct that tags the SDL_WindowShapeParams union with an enum describing the type of its contents.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  opaque type SDL_WindowShapeMode = CStruct2[WindowShapeMode, SDL_WindowShapeParams]
  object SDL_WindowShapeMode:
    given _tag: Tag[SDL_WindowShapeMode] = Tag.materializeCStruct2Tag[WindowShapeMode, SDL_WindowShapeParams]
    def apply()(using Zone): Ptr[SDL_WindowShapeMode] = scala.scalanative.unsafe.alloc[SDL_WindowShapeMode](1)
    def apply(mode: WindowShapeMode, parameters: SDL_WindowShapeParams)(using Zone): Ptr[SDL_WindowShapeMode] =
      val ____ptr = apply()
      (!____ptr).mode = mode
      (!____ptr).parameters = parameters
      ____ptr
    extension (struct: SDL_WindowShapeMode)
      def mode: WindowShapeMode                            = struct._1
      def mode_=(value: WindowShapeMode): Unit             = !struct.at1 = value
      def parameters: SDL_WindowShapeParams                = struct._2
      def parameters_=(value: SDL_WindowShapeParams): Unit = !struct.at2 = value

  /** A type representing an atomic integer value. It is a struct so people don't accidentally use numeric operations on it.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  opaque type SDL_atomic_t = CStruct1[CInt]
  object SDL_atomic_t:
    given _tag: Tag[SDL_atomic_t]              = Tag.materializeCStruct1Tag[CInt]
    def apply()(using Zone): Ptr[SDL_atomic_t] = scala.scalanative.unsafe.alloc[SDL_atomic_t](1)
    def apply(value: CInt)(using Zone): Ptr[SDL_atomic_t] =
      val ____ptr = apply()
      (!____ptr).value = value
      ____ptr
    extension (struct: SDL_atomic_t)
      def value: CInt                = struct._1
      def value_=(value: CInt): Unit = !struct.at1 = value

  /** [bindgen] header: ./SDL_mutex.h
    */
  opaque type SDL_cond = CStruct0
  object SDL_cond:
    given _tag: Tag[SDL_cond] = Tag.materializeCStruct0Tag

  /** A handle representing an open HID device
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  opaque type SDL_hid_device = CStruct0
  object SDL_hid_device:
    given _tag: Tag[SDL_hid_device] = Tag.materializeCStruct0Tag

  /** A handle representing an open HID device
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  opaque type SDL_hid_device_ = CStruct0
  object SDL_hid_device_ :
    given _tag: Tag[SDL_hid_device_] = Tag.materializeCStruct0Tag

  /** Information about a connected HID device
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  opaque type SDL_hid_device_info = CStruct14[
    CString,
    CUnsignedShort,
    CUnsignedShort,
    Ptr[wchar_t],
    CUnsignedShort,
    Ptr[wchar_t],
    Ptr[wchar_t],
    CUnsignedShort,
    CUnsignedShort,
    CInt,
    CInt,
    CInt,
    CInt,
    Ptr[Byte]
  ]
  object SDL_hid_device_info:
    given _tag: Tag[SDL_hid_device_info] = Tag.materializeCStruct14Tag[
      CString,
      CUnsignedShort,
      CUnsignedShort,
      Ptr[wchar_t],
      CUnsignedShort,
      Ptr[wchar_t],
      Ptr[wchar_t],
      CUnsignedShort,
      CUnsignedShort,
      CInt,
      CInt,
      CInt,
      CInt,
      Ptr[Byte]
    ]
    def apply()(using Zone): Ptr[SDL_hid_device_info] = scala.scalanative.unsafe.alloc[SDL_hid_device_info](1)
    def apply(
        path: CString,
        vendor_id: CUnsignedShort,
        product_id: CUnsignedShort,
        serial_number: Ptr[wchar_t],
        release_number: CUnsignedShort,
        manufacturer_string: Ptr[wchar_t],
        product_string: Ptr[wchar_t],
        usage_page: CUnsignedShort,
        usage: CUnsignedShort,
        interface_number: CInt,
        interface_class: CInt,
        interface_subclass: CInt,
        interface_protocol: CInt,
        next: Ptr[SDL_hid_device_info]
    )(using Zone): Ptr[SDL_hid_device_info] =
      val ____ptr = apply()
      (!____ptr).path = path
      (!____ptr).vendor_id = vendor_id
      (!____ptr).product_id = product_id
      (!____ptr).serial_number = serial_number
      (!____ptr).release_number = release_number
      (!____ptr).manufacturer_string = manufacturer_string
      (!____ptr).product_string = product_string
      (!____ptr).usage_page = usage_page
      (!____ptr).usage = usage
      (!____ptr).interface_number = interface_number
      (!____ptr).interface_class = interface_class
      (!____ptr).interface_subclass = interface_subclass
      (!____ptr).interface_protocol = interface_protocol
      (!____ptr).next = next
      ____ptr
    extension (struct: SDL_hid_device_info)
      def path: CString                                    = struct._1
      def path_=(value: CString): Unit                     = !struct.at1 = value
      def vendor_id: CUnsignedShort                        = struct._2
      def vendor_id_=(value: CUnsignedShort): Unit         = !struct.at2 = value
      def product_id: CUnsignedShort                       = struct._3
      def product_id_=(value: CUnsignedShort): Unit        = !struct.at3 = value
      def serial_number: Ptr[wchar_t]                      = struct._4
      def serial_number_=(value: Ptr[wchar_t]): Unit       = !struct.at4 = value
      def release_number: CUnsignedShort                   = struct._5
      def release_number_=(value: CUnsignedShort): Unit    = !struct.at5 = value
      def manufacturer_string: Ptr[wchar_t]                = struct._6
      def manufacturer_string_=(value: Ptr[wchar_t]): Unit = !struct.at6 = value
      def product_string: Ptr[wchar_t]                     = struct._7
      def product_string_=(value: Ptr[wchar_t]): Unit      = !struct.at7 = value
      def usage_page: CUnsignedShort                       = struct._8
      def usage_page_=(value: CUnsignedShort): Unit        = !struct.at8 = value
      def usage: CUnsignedShort                            = struct._9
      def usage_=(value: CUnsignedShort): Unit             = !struct.at9 = value
      def interface_number: CInt                           = struct._10
      def interface_number_=(value: CInt): Unit            = !struct.at10 = value
      def interface_class: CInt                            = struct._11
      def interface_class_=(value: CInt): Unit             = !struct.at11 = value
      def interface_subclass: CInt                         = struct._12
      def interface_subclass_=(value: CInt): Unit          = !struct.at12 = value
      def interface_protocol: CInt                         = struct._13
      def interface_protocol_=(value: CInt): Unit          = !struct.at13 = value
      def next: Ptr[SDL_hid_device_info]                   = struct._14.asInstanceOf[Ptr[SDL_hid_device_info]]
      def next_=(value: Ptr[SDL_hid_device_info]): Unit    = !struct.at14 = value.asInstanceOf[Ptr[Byte]]

  /** [bindgen] header: ./SDL_mutex.h
    */
  opaque type SDL_mutex = CStruct0
  object SDL_mutex:
    given _tag: Tag[SDL_mutex] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_mutex.h
    */
  opaque type SDL_sem = CStruct0
  object SDL_sem:
    given _tag: Tag[SDL_sem] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_mutex.h
    */
  opaque type SDL_semaphore = CStruct0
  object SDL_semaphore:
    given _tag: Tag[SDL_semaphore] = Tag.materializeCStruct0Tag

  /** Information about the version of SDL in use.
    *
    * [bindgen] header: ./SDL_version.h
    */
  opaque type SDL_version = CStruct3[Uint8, Uint8, Uint8]
  object SDL_version:
    given _tag: Tag[SDL_version]              = Tag.materializeCStruct3Tag[Uint8, Uint8, Uint8]
    def apply()(using Zone): Ptr[SDL_version] = scala.scalanative.unsafe.alloc[SDL_version](1)
    def apply(major: Uint8, minor: Uint8, patch: Uint8)(using Zone): Ptr[SDL_version] =
      val ____ptr = apply()
      (!____ptr).major = major
      (!____ptr).minor = minor
      (!____ptr).patch = patch
      ____ptr
    extension (struct: SDL_version)
      def major: Uint8                = struct._1
      def major_=(value: Uint8): Unit = !struct.at1 = value
      def minor: Uint8                = struct._2
      def minor_=(value: Uint8): Unit = !struct.at2 = value
      def patch: Uint8                = struct._3
      def patch_=(value: Uint8): Unit = !struct.at3 = value

  /** [bindgen] header: ./SDL_audio.h
    */
  opaque type _SDL_AudioStream = CStruct0
  object _SDL_AudioStream:
    given _tag: Tag[_SDL_AudioStream] = Tag.materializeCStruct0Tag

  /** The gamecontroller structure used to identify an SDL game controller
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  opaque type _SDL_GameController = CStruct0
  object _SDL_GameController:
    given _tag: Tag[_SDL_GameController] = Tag.materializeCStruct0Tag

  /** The haptic structure used to identify an SDL haptic.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type _SDL_Haptic = CStruct0
  object _SDL_Haptic:
    given _tag: Tag[_SDL_Haptic] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_joystick.h
    */
  opaque type _SDL_Joystick = CStruct0
  object _SDL_Joystick:
    given _tag: Tag[_SDL_Joystick] = Tag.materializeCStruct0Tag

  /** SDL_sensor.h
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  opaque type _SDL_Sensor = CStruct0
  object _SDL_Sensor:
    given _tag: Tag[_SDL_Sensor] = Tag.materializeCStruct0Tag

  /** [bindgen] header: ./SDL_stdinc.h
    */
  opaque type _SDL_iconv_t = CStruct0
  object _SDL_iconv_t:
    given _tag: Tag[_SDL_iconv_t] = Tag.materializeCStruct0Tag

object unions:
  import _root_.sdl2.enumerations.*
  import _root_.sdl2.predef.*
  import _root_.sdl2.aliases.*
  import _root_.sdl2.structs.*
  import _root_.sdl2.unions.*

  /** General event structure
    *
    * [bindgen] header: ./SDL_events.h
    */
  opaque type SDL_Event = CArray[Byte, Nat.Digit2[Nat._5, Nat._6]]
  object SDL_Event:
    given _tag: Tag[SDL_Event] =
      Tag.CArray[CChar, Nat.Digit2[Nat._5, Nat._6]](Tag.Byte, Tag.Digit2[Nat._5, Nat._6](Tag.Nat5, Tag.Nat6))
    def apply()(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      ___ptr
    @scala.annotation.targetName("apply_type")
    def apply(`type`: Uint32)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[Uint32]].update(0, `type`)
      ___ptr
    @scala.annotation.targetName("apply_common")
    def apply(common: SDL_CommonEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_CommonEvent]].update(0, common)
      ___ptr
    @scala.annotation.targetName("apply_display")
    def apply(display: SDL_DisplayEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_DisplayEvent]].update(0, display)
      ___ptr
    @scala.annotation.targetName("apply_window")
    def apply(window: SDL_WindowEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_WindowEvent]].update(0, window)
      ___ptr
    @scala.annotation.targetName("apply_key")
    def apply(key: SDL_KeyboardEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_KeyboardEvent]].update(0, key)
      ___ptr
    @scala.annotation.targetName("apply_edit")
    def apply(edit: SDL_TextEditingEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_TextEditingEvent]].update(0, edit)
      ___ptr
    @scala.annotation.targetName("apply_editExt")
    def apply(editExt: SDL_TextEditingExtEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_TextEditingExtEvent]].update(0, editExt)
      ___ptr
    @scala.annotation.targetName("apply_text")
    def apply(text: SDL_TextInputEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_TextInputEvent]].update(0, text)
      ___ptr
    @scala.annotation.targetName("apply_motion")
    def apply(motion: SDL_MouseMotionEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_MouseMotionEvent]].update(0, motion)
      ___ptr
    @scala.annotation.targetName("apply_button")
    def apply(button: SDL_MouseButtonEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_MouseButtonEvent]].update(0, button)
      ___ptr
    @scala.annotation.targetName("apply_wheel")
    def apply(wheel: SDL_MouseWheelEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_MouseWheelEvent]].update(0, wheel)
      ___ptr
    @scala.annotation.targetName("apply_jaxis")
    def apply(jaxis: SDL_JoyAxisEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyAxisEvent]].update(0, jaxis)
      ___ptr
    @scala.annotation.targetName("apply_jball")
    def apply(jball: SDL_JoyBallEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyBallEvent]].update(0, jball)
      ___ptr
    @scala.annotation.targetName("apply_jhat")
    def apply(jhat: SDL_JoyHatEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyHatEvent]].update(0, jhat)
      ___ptr
    @scala.annotation.targetName("apply_jbutton")
    def apply(jbutton: SDL_JoyButtonEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyButtonEvent]].update(0, jbutton)
      ___ptr
    @scala.annotation.targetName("apply_jdevice")
    def apply(jdevice: SDL_JoyDeviceEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyDeviceEvent]].update(0, jdevice)
      ___ptr
    @scala.annotation.targetName("apply_jbattery")
    def apply(jbattery: SDL_JoyBatteryEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_JoyBatteryEvent]].update(0, jbattery)
      ___ptr
    @scala.annotation.targetName("apply_caxis")
    def apply(caxis: SDL_ControllerAxisEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_ControllerAxisEvent]].update(0, caxis)
      ___ptr
    @scala.annotation.targetName("apply_cbutton")
    def apply(cbutton: SDL_ControllerButtonEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_ControllerButtonEvent]].update(0, cbutton)
      ___ptr
    @scala.annotation.targetName("apply_cdevice")
    def apply(cdevice: SDL_ControllerDeviceEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_ControllerDeviceEvent]].update(0, cdevice)
      ___ptr
    @scala.annotation.targetName("apply_ctouchpad")
    def apply(ctouchpad: SDL_ControllerTouchpadEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_ControllerTouchpadEvent]].update(0, ctouchpad)
      ___ptr
    @scala.annotation.targetName("apply_csensor")
    def apply(csensor: SDL_ControllerSensorEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_ControllerSensorEvent]].update(0, csensor)
      ___ptr
    @scala.annotation.targetName("apply_adevice")
    def apply(adevice: SDL_AudioDeviceEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_AudioDeviceEvent]].update(0, adevice)
      ___ptr
    @scala.annotation.targetName("apply_sensor")
    def apply(sensor: SDL_SensorEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_SensorEvent]].update(0, sensor)
      ___ptr
    @scala.annotation.targetName("apply_quit")
    def apply(quit: SDL_QuitEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_QuitEvent]].update(0, quit)
      ___ptr
    @scala.annotation.targetName("apply_user")
    def apply(user: SDL_UserEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_UserEvent]].update(0, user)
      ___ptr
    @scala.annotation.targetName("apply_syswm")
    def apply(syswm: SDL_SysWMEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_SysWMEvent]].update(0, syswm)
      ___ptr
    @scala.annotation.targetName("apply_tfinger")
    def apply(tfinger: SDL_TouchFingerEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_TouchFingerEvent]].update(0, tfinger)
      ___ptr
    @scala.annotation.targetName("apply_mgesture")
    def apply(mgesture: SDL_MultiGestureEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_MultiGestureEvent]].update(0, mgesture)
      ___ptr
    @scala.annotation.targetName("apply_dgesture")
    def apply(dgesture: SDL_DollarGestureEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_DollarGestureEvent]].update(0, dgesture)
      ___ptr
    @scala.annotation.targetName("apply_drop")
    def apply(drop: SDL_DropEvent)(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_DropEvent]].update(0, drop)
      ___ptr
    @scala.annotation.targetName("apply_padding")
    def apply(padding: CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]])(using Zone): Ptr[SDL_Event] =
      val ___ptr = alloc[SDL_Event](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]]]].update(0, padding)
      ___ptr
    extension (struct: SDL_Event)
      def `type`: Uint32                            = !struct.at(0).asInstanceOf[Ptr[Uint32]]
      def type_=(value: Uint32): Unit               = !struct.at(0).asInstanceOf[Ptr[Uint32]] = value
      def common: SDL_CommonEvent                   = !struct.at(0).asInstanceOf[Ptr[SDL_CommonEvent]]
      def common_=(value: SDL_CommonEvent): Unit    = !struct.at(0).asInstanceOf[Ptr[SDL_CommonEvent]] = value
      def display: SDL_DisplayEvent                 = !struct.at(0).asInstanceOf[Ptr[SDL_DisplayEvent]]
      def display_=(value: SDL_DisplayEvent): Unit  = !struct.at(0).asInstanceOf[Ptr[SDL_DisplayEvent]] = value
      def window: SDL_WindowEvent                   = !struct.at(0).asInstanceOf[Ptr[SDL_WindowEvent]]
      def window_=(value: SDL_WindowEvent): Unit    = !struct.at(0).asInstanceOf[Ptr[SDL_WindowEvent]] = value
      def key: SDL_KeyboardEvent                    = !struct.at(0).asInstanceOf[Ptr[SDL_KeyboardEvent]]
      def key_=(value: SDL_KeyboardEvent): Unit     = !struct.at(0).asInstanceOf[Ptr[SDL_KeyboardEvent]] = value
      def edit: SDL_TextEditingEvent                = !struct.at(0).asInstanceOf[Ptr[SDL_TextEditingEvent]]
      def edit_=(value: SDL_TextEditingEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_TextEditingEvent]] = value
      def editExt: SDL_TextEditingExtEvent          = !struct.at(0).asInstanceOf[Ptr[SDL_TextEditingExtEvent]]
      def editExt_=(value: SDL_TextEditingExtEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_TextEditingExtEvent]] =
        value
      def text: SDL_TextInputEvent                     = !struct.at(0).asInstanceOf[Ptr[SDL_TextInputEvent]]
      def text_=(value: SDL_TextInputEvent): Unit      = !struct.at(0).asInstanceOf[Ptr[SDL_TextInputEvent]] = value
      def motion: SDL_MouseMotionEvent                 = !struct.at(0).asInstanceOf[Ptr[SDL_MouseMotionEvent]]
      def motion_=(value: SDL_MouseMotionEvent): Unit  = !struct.at(0).asInstanceOf[Ptr[SDL_MouseMotionEvent]] = value
      def button: SDL_MouseButtonEvent                 = !struct.at(0).asInstanceOf[Ptr[SDL_MouseButtonEvent]]
      def button_=(value: SDL_MouseButtonEvent): Unit  = !struct.at(0).asInstanceOf[Ptr[SDL_MouseButtonEvent]] = value
      def wheel: SDL_MouseWheelEvent                   = !struct.at(0).asInstanceOf[Ptr[SDL_MouseWheelEvent]]
      def wheel_=(value: SDL_MouseWheelEvent): Unit    = !struct.at(0).asInstanceOf[Ptr[SDL_MouseWheelEvent]] = value
      def jaxis: SDL_JoyAxisEvent                      = !struct.at(0).asInstanceOf[Ptr[SDL_JoyAxisEvent]]
      def jaxis_=(value: SDL_JoyAxisEvent): Unit       = !struct.at(0).asInstanceOf[Ptr[SDL_JoyAxisEvent]] = value
      def jball: SDL_JoyBallEvent                      = !struct.at(0).asInstanceOf[Ptr[SDL_JoyBallEvent]]
      def jball_=(value: SDL_JoyBallEvent): Unit       = !struct.at(0).asInstanceOf[Ptr[SDL_JoyBallEvent]] = value
      def jhat: SDL_JoyHatEvent                        = !struct.at(0).asInstanceOf[Ptr[SDL_JoyHatEvent]]
      def jhat_=(value: SDL_JoyHatEvent): Unit         = !struct.at(0).asInstanceOf[Ptr[SDL_JoyHatEvent]] = value
      def jbutton: SDL_JoyButtonEvent                  = !struct.at(0).asInstanceOf[Ptr[SDL_JoyButtonEvent]]
      def jbutton_=(value: SDL_JoyButtonEvent): Unit   = !struct.at(0).asInstanceOf[Ptr[SDL_JoyButtonEvent]] = value
      def jdevice: SDL_JoyDeviceEvent                  = !struct.at(0).asInstanceOf[Ptr[SDL_JoyDeviceEvent]]
      def jdevice_=(value: SDL_JoyDeviceEvent): Unit   = !struct.at(0).asInstanceOf[Ptr[SDL_JoyDeviceEvent]] = value
      def jbattery: SDL_JoyBatteryEvent                = !struct.at(0).asInstanceOf[Ptr[SDL_JoyBatteryEvent]]
      def jbattery_=(value: SDL_JoyBatteryEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_JoyBatteryEvent]] = value
      def caxis: SDL_ControllerAxisEvent               = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerAxisEvent]]
      def caxis_=(value: SDL_ControllerAxisEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerAxisEvent]] =
        value
      def cbutton: SDL_ControllerButtonEvent = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerButtonEvent]]
      def cbutton_=(value: SDL_ControllerButtonEvent): Unit =
        !struct.at(0).asInstanceOf[Ptr[SDL_ControllerButtonEvent]] = value
      def cdevice: SDL_ControllerDeviceEvent = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerDeviceEvent]]
      def cdevice_=(value: SDL_ControllerDeviceEvent): Unit =
        !struct.at(0).asInstanceOf[Ptr[SDL_ControllerDeviceEvent]] = value
      def ctouchpad: SDL_ControllerTouchpadEvent = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerTouchpadEvent]]
      def ctouchpad_=(value: SDL_ControllerTouchpadEvent): Unit =
        !struct.at(0).asInstanceOf[Ptr[SDL_ControllerTouchpadEvent]] = value
      def csensor: SDL_ControllerSensorEvent = !struct.at(0).asInstanceOf[Ptr[SDL_ControllerSensorEvent]]
      def csensor_=(value: SDL_ControllerSensorEvent): Unit =
        !struct.at(0).asInstanceOf[Ptr[SDL_ControllerSensorEvent]] = value
      def adevice: SDL_AudioDeviceEvent                = !struct.at(0).asInstanceOf[Ptr[SDL_AudioDeviceEvent]]
      def adevice_=(value: SDL_AudioDeviceEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_AudioDeviceEvent]] = value
      def sensor: SDL_SensorEvent                      = !struct.at(0).asInstanceOf[Ptr[SDL_SensorEvent]]
      def sensor_=(value: SDL_SensorEvent): Unit       = !struct.at(0).asInstanceOf[Ptr[SDL_SensorEvent]] = value
      def quit: SDL_QuitEvent                          = !struct.at(0).asInstanceOf[Ptr[SDL_QuitEvent]]
      def quit_=(value: SDL_QuitEvent): Unit           = !struct.at(0).asInstanceOf[Ptr[SDL_QuitEvent]] = value
      def user: SDL_UserEvent                          = !struct.at(0).asInstanceOf[Ptr[SDL_UserEvent]]
      def user_=(value: SDL_UserEvent): Unit           = !struct.at(0).asInstanceOf[Ptr[SDL_UserEvent]] = value
      def syswm: SDL_SysWMEvent                        = !struct.at(0).asInstanceOf[Ptr[SDL_SysWMEvent]]
      def syswm_=(value: SDL_SysWMEvent): Unit         = !struct.at(0).asInstanceOf[Ptr[SDL_SysWMEvent]] = value
      def tfinger: SDL_TouchFingerEvent                = !struct.at(0).asInstanceOf[Ptr[SDL_TouchFingerEvent]]
      def tfinger_=(value: SDL_TouchFingerEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_TouchFingerEvent]] = value
      def mgesture: SDL_MultiGestureEvent              = !struct.at(0).asInstanceOf[Ptr[SDL_MultiGestureEvent]]
      def mgesture_=(value: SDL_MultiGestureEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_MultiGestureEvent]] =
        value
      def dgesture: SDL_DollarGestureEvent = !struct.at(0).asInstanceOf[Ptr[SDL_DollarGestureEvent]]
      def dgesture_=(value: SDL_DollarGestureEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_DollarGestureEvent]] =
        value
      def drop: SDL_DropEvent                = !struct.at(0).asInstanceOf[Ptr[SDL_DropEvent]]
      def drop_=(value: SDL_DropEvent): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_DropEvent]] = value
      def padding: CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]] =
        !struct.at(0).asInstanceOf[Ptr[CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]]]]
      def padding_=(value: CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]]): Unit =
        !struct.at(0).asInstanceOf[Ptr[CArray[Uint8, Nat.Digit2[Nat._5, Nat._6]]]] = value

  /** The generic template for any haptic effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  opaque type SDL_HapticEffect = CArray[Byte, Nat.Digit2[Nat._6, Nat._8]]
  object SDL_HapticEffect:
    given _tag: Tag[SDL_HapticEffect] =
      Tag.CArray[CChar, Nat.Digit2[Nat._6, Nat._8]](Tag.Byte, Tag.Digit2[Nat._6, Nat._8](Tag.Nat6, Tag.Nat8))
    def apply()(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      ___ptr
    @scala.annotation.targetName("apply_type")
    def apply(`type`: Uint16)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[Uint16]].update(0, `type`)
      ___ptr
    @scala.annotation.targetName("apply_constant")
    def apply(constant: SDL_HapticConstant)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticConstant]].update(0, constant)
      ___ptr
    @scala.annotation.targetName("apply_periodic")
    def apply(periodic: SDL_HapticPeriodic)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticPeriodic]].update(0, periodic)
      ___ptr
    @scala.annotation.targetName("apply_condition")
    def apply(condition: SDL_HapticCondition)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticCondition]].update(0, condition)
      ___ptr
    @scala.annotation.targetName("apply_ramp")
    def apply(ramp: SDL_HapticRamp)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticRamp]].update(0, ramp)
      ___ptr
    @scala.annotation.targetName("apply_leftright")
    def apply(leftright: SDL_HapticLeftRight)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticLeftRight]].update(0, leftright)
      ___ptr
    @scala.annotation.targetName("apply_custom")
    def apply(custom: SDL_HapticCustom)(using Zone): Ptr[SDL_HapticEffect] =
      val ___ptr = alloc[SDL_HapticEffect](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_HapticCustom]].update(0, custom)
      ___ptr
    extension (struct: SDL_HapticEffect)
      def `type`: Uint16                                = !struct.at(0).asInstanceOf[Ptr[Uint16]]
      def type_=(value: Uint16): Unit                   = !struct.at(0).asInstanceOf[Ptr[Uint16]] = value
      def constant: SDL_HapticConstant                  = !struct.at(0).asInstanceOf[Ptr[SDL_HapticConstant]]
      def constant_=(value: SDL_HapticConstant): Unit   = !struct.at(0).asInstanceOf[Ptr[SDL_HapticConstant]] = value
      def periodic: SDL_HapticPeriodic                  = !struct.at(0).asInstanceOf[Ptr[SDL_HapticPeriodic]]
      def periodic_=(value: SDL_HapticPeriodic): Unit   = !struct.at(0).asInstanceOf[Ptr[SDL_HapticPeriodic]] = value
      def condition: SDL_HapticCondition                = !struct.at(0).asInstanceOf[Ptr[SDL_HapticCondition]]
      def condition_=(value: SDL_HapticCondition): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_HapticCondition]] = value
      def ramp: SDL_HapticRamp                          = !struct.at(0).asInstanceOf[Ptr[SDL_HapticRamp]]
      def ramp_=(value: SDL_HapticRamp): Unit           = !struct.at(0).asInstanceOf[Ptr[SDL_HapticRamp]] = value
      def leftright: SDL_HapticLeftRight                = !struct.at(0).asInstanceOf[Ptr[SDL_HapticLeftRight]]
      def leftright_=(value: SDL_HapticLeftRight): Unit = !struct.at(0).asInstanceOf[Ptr[SDL_HapticLeftRight]] = value
      def custom: SDL_HapticCustom                      = !struct.at(0).asInstanceOf[Ptr[SDL_HapticCustom]]
      def custom_=(value: SDL_HapticCustom): Unit       = !struct.at(0).asInstanceOf[Ptr[SDL_HapticCustom]] = value

  /** A union containing parameters for shaped windows.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  opaque type SDL_WindowShapeParams = CArray[Byte, Nat._4]
  object SDL_WindowShapeParams:
    given _tag: Tag[SDL_WindowShapeParams] = Tag.CArray[CChar, Nat._4](Tag.Byte, Tag.Nat4)
    def apply()(using Zone): Ptr[SDL_WindowShapeParams] =
      val ___ptr = alloc[SDL_WindowShapeParams](1)
      ___ptr
    @scala.annotation.targetName("apply_binarizationCutoff")
    def apply(binarizationCutoff: Uint8)(using Zone): Ptr[SDL_WindowShapeParams] =
      val ___ptr = alloc[SDL_WindowShapeParams](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[Uint8]].update(0, binarizationCutoff)
      ___ptr
    @scala.annotation.targetName("apply_colorKey")
    def apply(colorKey: SDL_Color)(using Zone): Ptr[SDL_WindowShapeParams] =
      val ___ptr = alloc[SDL_WindowShapeParams](1)
      val un     = !___ptr
      un.at(0).asInstanceOf[Ptr[SDL_Color]].update(0, colorKey)
      ___ptr
    extension (struct: SDL_WindowShapeParams)
      def binarizationCutoff: Uint8                = !struct.at(0).asInstanceOf[Ptr[Uint8]]
      def binarizationCutoff_=(value: Uint8): Unit = !struct.at(0).asInstanceOf[Ptr[Uint8]] = value
      def colorKey: SDL_Color                      = !struct.at(0).asInstanceOf[Ptr[SDL_Color]]
      def colorKey_=(value: SDL_Color): Unit       = !struct.at(0).asInstanceOf[Ptr[SDL_Color]] = value

@extern
@link("SDL2") // [MANUAL]
private[sdl2] object extern_functions:
  import _root_.sdl2.enumerations.*
  import _root_.sdl2.predef.*
  import _root_.sdl2.aliases.*
  import _root_.sdl2.structs.*
  import _root_.sdl2.unions.*

  /** Add a callback to be triggered when an event is added to the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_AddEventWatch(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern

  /** Add a function to watch a particular hint.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_AddHintCallback(name: CString, callback: SDL_HintCallback, userdata: Ptr[Byte]): Unit = extern

  /** Call a callback function at a future time.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_AddTimer(interval: Uint32, callback: SDL_TimerCallback, param: Ptr[Byte]): SDL_TimerID = extern

  /** Create an SDL_PixelFormat structure corresponding to a pixel format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_AllocFormat(pixel_format: Uint32): Ptr[SDL_PixelFormat] = extern

  /** Create a palette structure with the specified number of color entries.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_AllocPalette(ncolors: CInt): Ptr[SDL_Palette] = extern

  /** Use this function to allocate an empty, unpopulated SDL_RWops structure.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_AllocRW(): Ptr[SDL_RWops] = extern

  /** Add to an atomic variable.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicAdd(a: Ptr[SDL_atomic_t], v: CInt): CInt = extern

  /** Set an atomic variable to a new value if it is currently an old value.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicCAS(a: Ptr[SDL_atomic_t], oldval: CInt, newval: CInt): SDL_bool = extern

  /** Set a pointer to a new value if it is currently an old value.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicCASPtr(a: Ptr[Ptr[Byte]], oldval: Ptr[Byte], newval: Ptr[Byte]): SDL_bool = extern

  /** Get the value of an atomic variable.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicGet(a: Ptr[SDL_atomic_t]): CInt = extern

  /** Get the value of a pointer atomically.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicGetPtr(a: Ptr[Ptr[Byte]]): Ptr[Byte] = extern

  /** Lock a spin lock by setting it to a non-zero value.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicLock(lock: Ptr[SDL_SpinLock]): Unit = extern

  /** Set an atomic variable to a value.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicSet(a: Ptr[SDL_atomic_t], v: CInt): CInt = extern

  /** Set a pointer to a value atomically.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicSetPtr(a: Ptr[Ptr[Byte]], v: Ptr[Byte]): Ptr[Byte] = extern

  /** Try to lock a spin lock by setting it to a non-zero value.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicTryLock(lock: Ptr[SDL_SpinLock]): SDL_bool = extern

  /** Unlock a spin lock by setting it to 0.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_AtomicUnlock(lock: Ptr[SDL_SpinLock]): Unit = extern

  /** Use this function to initialize a particular audio driver.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioInit(driver_name: CString): CInt = extern

  /** Use this function to shut down audio if you initialized it with SDL_AudioInit().
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioQuit(): Unit = extern

  /** Get the number of converted/resampled bytes available.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioStreamAvailable(stream: Ptr[SDL_AudioStream]): CInt = extern

  /** Clear any pending data in the stream without converting it
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioStreamClear(stream: Ptr[SDL_AudioStream]): Unit = extern

  /** Tell the stream that you're done sending data, and anything being buffered should be converted/resampled and made available immediately.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioStreamFlush(stream: Ptr[SDL_AudioStream]): CInt = extern

  /** Get converted/resampled data from the stream
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioStreamGet(stream: Ptr[SDL_AudioStream], buf: Ptr[Byte], len: CInt): CInt = extern

  /** Add data to be converted/resampled to the stream.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_AudioStreamPut(stream: Ptr[SDL_AudioStream], buf: Ptr[Byte], len: CInt): CInt = extern

  /** Initialize an SDL_AudioCVT structure for conversion.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_BuildAudioCVT(
      cvt: Ptr[SDL_AudioCVT],
      src_format: SDL_AudioFormat,
      src_channels: Uint8,
      src_rate: CInt,
      dst_format: SDL_AudioFormat,
      dst_channels: Uint8,
      dst_rate: CInt
  ): CInt = extern

  /** Calculate a 256 entry gamma ramp for a gamma value.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_CalculateGammaRamp(gamma: Float, ramp: Ptr[Uint16]): Unit = extern

  /** Capture the mouse and to track input outside an SDL window.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_CaptureMouse(enabled: SDL_bool): CInt = extern

  /** Dismiss the composition window/IME without disabling the subsystem.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_ClearComposition(): Unit = extern

  /** Clear any previous error message for this thread.
    *
    * [bindgen] header: ./SDL_error.h
    */
  def SDL_ClearError(): Unit = extern

  /** Clear all hints.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_ClearHints(): Unit = extern

  /** Drop any queued audio data waiting to be sent to the hardware.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_ClearQueuedAudio(dev: SDL_AudioDeviceID): Unit = extern

  /** This function is a legacy means of closing the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_CloseAudio(): Unit = extern

  /** Use this function to shut down audio processing and close the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_CloseAudioDevice(dev: SDL_AudioDeviceID): Unit = extern

  /** Compose a custom blend mode for renderers.
    *
    * [bindgen] header: ./SDL_blendmode.h
    */
  def SDL_ComposeCustomBlendMode(
      srcColorFactor: SDL_BlendFactor,
      dstColorFactor: SDL_BlendFactor,
      colorOperation: SDL_BlendOperation,
      srcAlphaFactor: SDL_BlendFactor,
      dstAlphaFactor: SDL_BlendFactor,
      alphaOperation: SDL_BlendOperation
  ): SDL_BlendMode = extern

  /** Restart all threads that are waiting on the condition variable.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CondBroadcast(cond: Ptr[SDL_cond]): CInt = extern

  /** Restart one of the threads that are waiting on the condition variable.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CondSignal(cond: Ptr[SDL_cond]): CInt = extern

  /** Wait until a condition variable is signaled.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CondWait(cond: Ptr[SDL_cond], mutex: Ptr[SDL_mutex]): CInt = extern

  /** Wait until a condition variable is signaled or a certain time has passed.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CondWaitTimeout(cond: Ptr[SDL_cond], mutex: Ptr[SDL_mutex], ms: Uint32): CInt = extern

  /** Convert audio data to a desired audio format.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_ConvertAudio(cvt: Ptr[SDL_AudioCVT]): CInt = extern

  /** Copy a block of pixels of one format to another format.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_ConvertPixels(
      width: CInt,
      height: CInt,
      src_format: Uint32,
      src: Ptr[Byte],
      src_pitch: CInt,
      dst_format: Uint32,
      dst: Ptr[Byte],
      dst_pitch: CInt
  ): CInt = extern

  /** Copy an existing surface to a new surface of the specified format.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_ConvertSurface(src: Ptr[SDL_Surface], fmt: Ptr[SDL_PixelFormat], flags: Uint32): Ptr[SDL_Surface] = extern

  /** Copy an existing surface to a new surface of the specified format enum.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_ConvertSurfaceFormat(src: Ptr[SDL_Surface], pixel_format: Uint32, flags: Uint32): Ptr[SDL_Surface] = extern

  /** Create a color cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_CreateColorCursor(surface: Ptr[SDL_Surface], hot_x: CInt, hot_y: CInt): Ptr[SDL_Cursor] = extern

  /** Create a condition variable.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CreateCond(): Ptr[SDL_cond] = extern

  /** Create a cursor using the specified bitmap data and mask (in MSB format).
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_CreateCursor(
      data: Ptr[Uint8],
      mask: Ptr[Uint8],
      w: CInt,
      h: CInt,
      hot_x: CInt,
      hot_y: CInt
  ): Ptr[SDL_Cursor] = extern

  /** Create a new mutex.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CreateMutex(): Ptr[SDL_mutex] = extern

  /** Allocate a new RGB surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_CreateRGBSurface(
      flags: Uint32,
      width: CInt,
      height: CInt,
      depth: CInt,
      Rmask: Uint32,
      Gmask: Uint32,
      Bmask: Uint32,
      Amask: Uint32
  ): Ptr[SDL_Surface] = extern

  /** Allocate a new RGB surface with existing pixel data.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_CreateRGBSurfaceFrom(
      pixels: Ptr[Byte],
      width: CInt,
      height: CInt,
      depth: CInt,
      pitch: CInt,
      Rmask: Uint32,
      Gmask: Uint32,
      Bmask: Uint32,
      Amask: Uint32
  ): Ptr[SDL_Surface] = extern

  /** Allocate a new RGB surface with a specific pixel format.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_CreateRGBSurfaceWithFormat(
      flags: Uint32,
      width: CInt,
      height: CInt,
      depth: CInt,
      format: Uint32
  ): Ptr[SDL_Surface] = extern

  /** Allocate a new RGB surface with with a specific pixel format and existing pixel data.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_CreateRGBSurfaceWithFormatFrom(
      pixels: Ptr[Byte],
      width: CInt,
      height: CInt,
      depth: CInt,
      pitch: CInt,
      format: Uint32
  ): Ptr[SDL_Surface] = extern

  /** Create a 2D rendering context for a window.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_CreateRenderer(window: Ptr[SDL_Window], index: CInt, flags: Uint32): Ptr[SDL_Renderer] = extern

  /** Create a semaphore.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_CreateSemaphore(initial_value: Uint32): Ptr[SDL_sem] = extern

  /** Create a window that can be shaped with the specified position, dimensions, and flags.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  def SDL_CreateShapedWindow(
      title: CString,
      x: CUnsignedInt,
      y: CUnsignedInt,
      w: CUnsignedInt,
      h: CUnsignedInt,
      flags: Uint32
  ): Ptr[SDL_Window] = extern

  /** Create a 2D software rendering context for a surface.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_CreateSoftwareRenderer(surface: Ptr[SDL_Surface]): Ptr[SDL_Renderer] = extern

  /** Create a system cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_CreateSystemCursor(id: SDL_SystemCursor): Ptr[SDL_Cursor] = extern

  /** Create a texture for a rendering context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_CreateTexture(renderer: Ptr[SDL_Renderer], format: Uint32, access: CInt, w: CInt, h: CInt): Ptr[SDL_Texture] =
    extern

  /** Create a texture from an existing surface.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_CreateTextureFromSurface(renderer: Ptr[SDL_Renderer], surface: Ptr[SDL_Surface]): Ptr[SDL_Texture] = extern

  /** [bindgen] header: ./SDL_thread.h
    */
  def SDL_CreateThread(
      fn: SDL_ThreadFunction,
      name: CString,
      data: Ptr[Byte],
      pfnBeginThread: pfnSDL_CurrentBeginThread,
      pfnEndThread: pfnSDL_CurrentEndThread
  ): Ptr[SDL_Thread] = extern

  /** [bindgen] header: ./SDL_thread.h
    */
  def SDL_CreateThreadWithStackSize(
      fn: SDL_ThreadFunction,
      name: CString,
      stacksize: size_t,
      data: Ptr[Byte],
      pfnBeginThread: pfnSDL_CurrentBeginThread,
      pfnEndThread: pfnSDL_CurrentEndThread
  ): Ptr[SDL_Thread] = extern

  /** Create a window with the specified position, dimensions, and flags.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_CreateWindow(title: CString, x: CInt, y: CInt, w: CInt, h: CInt, flags: Uint32): Ptr[SDL_Window] = extern

  /** Create a window and default renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_CreateWindowAndRenderer(
      width: CInt,
      height: CInt,
      window_flags: Uint32,
      window: Ptr[Ptr[SDL_Window]],
      renderer: Ptr[Ptr[SDL_Renderer]]
  ): CInt = extern

  /** Create an SDL window from an existing native window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_CreateWindowFrom(data: Ptr[Byte]): Ptr[SDL_Window] = extern

  /** Get the DXGI Adapter and Output indices for the specified display index.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_DXGIGetOutputInfo(displayIndex: CInt, adapterIndex: Ptr[CInt], outputIndex: Ptr[CInt]): SDL_bool = extern

  /** Remove an event watch callback added with SDL_AddEventWatch().
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_DelEventWatch(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern

  /** Remove a function watching a particular hint.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_DelHintCallback(name: CString, callback: SDL_HintCallback, userdata: Ptr[Byte]): Unit = extern

  /** Wait a specified number of milliseconds before returning.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_Delay(ms: Uint32): Unit = extern

  /** Dequeue more audio on non-callback devices.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_DequeueAudio(dev: SDL_AudioDeviceID, data: Ptr[Byte], len: Uint32): Uint32 = extern

  /** Destroy a condition variable.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_DestroyCond(cond: Ptr[SDL_cond]): Unit = extern

  /** Destroy a mutex created with SDL_CreateMutex().
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_DestroyMutex(mutex: Ptr[SDL_mutex]): Unit = extern

  /** Destroy the rendering context for a window and free associated textures.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_DestroyRenderer(renderer: Ptr[SDL_Renderer]): Unit = extern

  /** Destroy a semaphore.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_DestroySemaphore(sem: Ptr[SDL_sem]): Unit = extern

  /** Destroy the specified texture.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_DestroyTexture(texture: Ptr[SDL_Texture]): Unit = extern

  /** Destroy a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_DestroyWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Destroy the surface associated with the window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_DestroyWindowSurface(window: Ptr[SDL_Window]): CInt = extern

  /** Let a thread clean up on exit without intervention.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_DetachThread(thread: Ptr[SDL_Thread]): Unit = extern

  /** Get the D3D9 adapter index that matches the specified display index.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_Direct3D9GetAdapterIndex(displayIndex: CInt): CInt = extern

  /** Prevent the screen from being blanked by a screen saver.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_DisableScreenSaver(): Unit = extern

  /** [bindgen] header: ./SDL_surface.h
    */
  def SDL_DuplicateSurface(surface: Ptr[SDL_Surface]): Ptr[SDL_Surface] = extern

  /** Allow the screen to be blanked by a screen saver.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_EnableScreenSaver(): Unit = extern

  /** Calculate a minimal rectangle enclosing a set of points with float precision.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_EncloseFPoints(points: Ptr[SDL_FPoint], count: CInt, clip: Ptr[SDL_FRect], result: Ptr[SDL_FRect]): SDL_bool =
    extern

  /** Calculate a minimal rectangle enclosing a set of points.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_EnclosePoints(points: Ptr[SDL_Point], count: CInt, clip: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): SDL_bool =
    extern

  /** [bindgen] header: ./SDL_error.h
    */
  def SDL_Error(code: SDL_errorcode): CInt = extern

  /** Set the state of processing events by type.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_EventState(`type`: Uint32, state: CInt): Uint8 = extern

  /** Returns true if the rectangle has no area.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_FRectEmpty(r: Ptr[SDL_FRect]): SDL_bool = extern

  /** Returns true if the two rectangles are equal, using a default epsilon.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_FRectEquals(a: Ptr[SDL_FRect], b: Ptr[SDL_FRect]): SDL_bool = extern

  /** Returns true if the two rectangles are equal, within some given epsilon.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_FRectEqualsEpsilon(a: Ptr[SDL_FRect], b: Ptr[SDL_FRect], epsilon: Float): SDL_bool = extern

  /** Perform a fast fill of a rectangle with a specific color.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_FillRect(dst: Ptr[SDL_Surface], rect: Ptr[SDL_Rect], color: Uint32): CInt = extern

  /** Perform a fast fill of a set of rectangles with a specific color.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_FillRects(dst: Ptr[SDL_Surface], rects: Ptr[SDL_Rect], count: CInt, color: Uint32): CInt = extern

  /** Run a specific filter function on the current event queue, removing any events for which the filter returns 0.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_FilterEvents(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern

  /** Request a window to demand attention from the user.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_FlashWindow(window: Ptr[SDL_Window], operation: SDL_FlashOperation): CInt = extern

  /** Clear events of a specific type from the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_FlushEvent(`type`: Uint32): Unit = extern

  /** Clear events of a range of types from the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_FlushEvents(minType: Uint32, maxType: Uint32): Unit = extern

  /** Free an audio stream
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_FreeAudioStream(stream: Ptr[SDL_AudioStream]): Unit = extern

  /** Free a previously-created cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_FreeCursor(cursor: Ptr[SDL_Cursor]): Unit = extern

  /** Free an SDL_PixelFormat structure allocated by SDL_AllocFormat().
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_FreeFormat(format: Ptr[SDL_PixelFormat]): Unit = extern

  /** Free a palette created with SDL_AllocPalette().
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_FreePalette(palette: Ptr[SDL_Palette]): Unit = extern

  /** Use this function to free an SDL_RWops structure allocated by SDL_AllocRW().
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_FreeRW(area: Ptr[SDL_RWops]): Unit = extern

  /** Free an RGB surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_FreeSurface(surface: Ptr[SDL_Surface]): Unit = extern

  /** Free data previously allocated with SDL_LoadWAV() or SDL_LoadWAV_RW().
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_FreeWAV(audio_buf: Ptr[Uint8]): Unit = extern

  /** Bind an OpenGL/ES/ES2 texture to the current context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GL_BindTexture(texture: Ptr[SDL_Texture], texw: Ptr[Float], texh: Ptr[Float]): CInt = extern

  /** Create an OpenGL context for an OpenGL window, and make it current.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_CreateContext(window: Ptr[SDL_Window]): SDL_GLContext = extern

  /** Delete an OpenGL context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_DeleteContext(context: SDL_GLContext): Unit = extern

  /** Check if an OpenGL extension is supported for the current context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_ExtensionSupported(extension: CString): SDL_bool = extern

  /** Get the actual value for an attribute from the current context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetAttribute(attr: SDL_GLattr, value: Ptr[CInt]): CInt = extern

  /** Get the currently active OpenGL context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetCurrentContext(): SDL_GLContext = extern

  /** Get the currently active OpenGL window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetCurrentWindow(): Ptr[SDL_Window] = extern

  /** Get the size of a window's underlying drawable in pixels.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetDrawableSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get an OpenGL function by name.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetProcAddress(proc: CString): Ptr[Byte] = extern

  /** Get the swap interval for the current OpenGL context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_GetSwapInterval(): CInt = extern

  /** Dynamically load an OpenGL library.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_LoadLibrary(path: CString): CInt = extern

  /** Set up an OpenGL context for rendering into an OpenGL window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_MakeCurrent(window: Ptr[SDL_Window], context: SDL_GLContext): CInt = extern

  /** Reset all previously set OpenGL context attributes to their default values.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_ResetAttributes(): Unit = extern

  /** Set an OpenGL window attribute before window creation.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_SetAttribute(attr: SDL_GLattr, value: CInt): CInt = extern

  /** Set the swap interval for the current OpenGL context.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_SetSwapInterval(interval: CInt): CInt = extern

  /** Update a window with OpenGL rendering.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_SwapWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Unbind an OpenGL/ES/ES2 texture from the current context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GL_UnbindTexture(texture: Ptr[SDL_Texture]): CInt = extern

  /** Unload the OpenGL library previously loaded by SDL_GL_LoadLibrary().
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GL_UnloadLibrary(): Unit = extern

  /** Add support for controllers that SDL is unaware of or to cause an existing controller to have a different binding.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerAddMapping(mappingString: CString): CInt = extern

  /** Load a set of Game Controller mappings from a seekable SDL data stream.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerAddMappingsFromRW(rw: Ptr[SDL_RWops], freerw: CInt): CInt = extern

  /** Close a game controller previously opened with SDL_GameControllerOpen().
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerClose(gamecontroller: Ptr[SDL_GameController]): Unit = extern

  /** Query or change current state of Game Controller events.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerEventState(state: CInt): CInt = extern

  /** Get the SDL_GameController associated with an instance id.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerFromInstanceID(joyid: SDL_JoystickID): Ptr[SDL_GameController] = extern

  /** Get the SDL_GameController associated with a player index.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerFromPlayerIndex(player_index: CInt): Ptr[SDL_GameController] = extern

  /** Return the sfSymbolsName for a given axis on a game controller on Apple platforms.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetAppleSFSymbolsNameForAxis(
      gamecontroller: Ptr[SDL_GameController],
      axis: SDL_GameControllerAxis
  ): CString = extern

  /** Return the sfSymbolsName for a given button on a game controller on Apple platforms.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetAppleSFSymbolsNameForButton(
      gamecontroller: Ptr[SDL_GameController],
      button: SDL_GameControllerButton
  ): CString = extern

  /** Check if a controller has been opened and is currently connected.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetAttached(gamecontroller: Ptr[SDL_GameController]): SDL_bool = extern

  /** Get the current state of an axis control on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetAxis(gamecontroller: Ptr[SDL_GameController], axis: SDL_GameControllerAxis): Sint16 = extern

  /** Convert a string into SDL_GameControllerAxis enum.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetAxisFromString(str: CString): SDL_GameControllerAxis = extern

  /** Get the current state of a button on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetButton(gamecontroller: Ptr[SDL_GameController], button: SDL_GameControllerButton): Uint8 =
    extern

  /** Convert a string into an SDL_GameControllerButton enum.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetButtonFromString(str: CString): SDL_GameControllerButton = extern

  /** Get the firmware version of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetFirmwareVersion(gamecontroller: Ptr[SDL_GameController]): Uint16 = extern

  /** Get the Joystick ID from a Game Controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetJoystick(gamecontroller: Ptr[SDL_GameController]): Ptr[SDL_Joystick] = extern

  /** Get the number of supported simultaneous fingers on a touchpad on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetNumTouchpadFingers(gamecontroller: Ptr[SDL_GameController], touchpad: CInt): CInt = extern

  /** Get the number of touchpads on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetNumTouchpads(gamecontroller: Ptr[SDL_GameController]): CInt = extern

  /** Get the player index of an opened game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetPlayerIndex(gamecontroller: Ptr[SDL_GameController]): CInt = extern

  /** Get the USB product ID of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetProduct(gamecontroller: Ptr[SDL_GameController]): Uint16 = extern

  /** Get the product version of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetProductVersion(gamecontroller: Ptr[SDL_GameController]): Uint16 = extern

  /** Get the current state of a game controller sensor.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetSensorData(
      gamecontroller: Ptr[SDL_GameController],
      `type`: SDL_SensorType,
      data: Ptr[Float],
      num_values: CInt
  ): CInt = extern

  /** Get the data rate (number of events per second) of a game controller sensor.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetSensorDataRate(gamecontroller: Ptr[SDL_GameController], `type`: SDL_SensorType): Float =
    extern

  /** Get the current state of a game controller sensor with the timestamp of the last update.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetSensorDataWithTimestamp(
      gamecontroller: Ptr[SDL_GameController],
      `type`: SDL_SensorType,
      timestamp: Ptr[Uint64],
      data: Ptr[Float],
      num_values: CInt
  ): CInt = extern

  /** Get the serial number of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetSerial(gamecontroller: Ptr[SDL_GameController]): CString = extern

  /** Get the Steam Input handle of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetSteamHandle(gamecontroller: Ptr[SDL_GameController]): Uint64 = extern

  /** Convert from an SDL_GameControllerAxis enum to a string.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetStringForAxis(axis: SDL_GameControllerAxis): CString = extern

  /** Convert from an SDL_GameControllerButton enum to a string.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetStringForButton(button: SDL_GameControllerButton): CString = extern

  /** Get the current state of a finger on a touchpad on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetTouchpadFinger(
      gamecontroller: Ptr[SDL_GameController],
      touchpad: CInt,
      finger: CInt,
      state: Ptr[Uint8],
      x: Ptr[Float],
      y: Ptr[Float],
      pressure: Ptr[Float]
  ): CInt = extern

  /** Get the type of this currently opened controller
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetType(gamecontroller: Ptr[SDL_GameController]): SDL_GameControllerType = extern

  /** Get the USB vendor ID of an opened controller, if available.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetVendor(gamecontroller: Ptr[SDL_GameController]): Uint16 = extern

  /** Query whether a game controller has a given axis.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasAxis(gamecontroller: Ptr[SDL_GameController], axis: SDL_GameControllerAxis): SDL_bool =
    extern

  /** Query whether a game controller has a given button.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasButton(gamecontroller: Ptr[SDL_GameController], button: SDL_GameControllerButton): SDL_bool =
    extern

  /** Query whether a game controller has an LED.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasLED(gamecontroller: Ptr[SDL_GameController]): SDL_bool = extern

  /** Query whether a game controller has rumble support.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasRumble(gamecontroller: Ptr[SDL_GameController]): SDL_bool = extern

  /** Query whether a game controller has rumble support on triggers.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasRumbleTriggers(gamecontroller: Ptr[SDL_GameController]): SDL_bool = extern

  /** Return whether a game controller has a particular sensor.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerHasSensor(gamecontroller: Ptr[SDL_GameController], `type`: SDL_SensorType): SDL_bool = extern

  /** Query whether sensor data reporting is enabled for a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerIsSensorEnabled(gamecontroller: Ptr[SDL_GameController], `type`: SDL_SensorType): SDL_bool =
    extern

  /** Get the current mapping of a Game Controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerMapping(gamecontroller: Ptr[SDL_GameController]): CString = extern

  /** Get the mapping of a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerMappingForDeviceIndex(joystick_index: CInt): CString = extern

  /** Get the mapping at a particular index.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerMappingForIndex(mapping_index: CInt): CString = extern

  /** Get the implementation-dependent name for an opened game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerName(gamecontroller: Ptr[SDL_GameController]): CString = extern

  /** Get the implementation dependent name for the game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerNameForIndex(joystick_index: CInt): CString = extern

  /** Get the number of mappings installed.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerNumMappings(): CInt = extern

  /** Open a game controller for use.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerOpen(joystick_index: CInt): Ptr[SDL_GameController] = extern

  /** Get the implementation-dependent path for an opened game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerPath(gamecontroller: Ptr[SDL_GameController]): CString = extern

  /** Get the implementation dependent path for the game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerPathForIndex(joystick_index: CInt): CString = extern

  /** Start a rumble effect on a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerRumble(
      gamecontroller: Ptr[SDL_GameController],
      low_frequency_rumble: Uint16,
      high_frequency_rumble: Uint16,
      duration_ms: Uint32
  ): CInt = extern

  /** Start a rumble effect in the game controller's triggers.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerRumbleTriggers(
      gamecontroller: Ptr[SDL_GameController],
      left_rumble: Uint16,
      right_rumble: Uint16,
      duration_ms: Uint32
  ): CInt = extern

  /** Send a controller specific effect packet
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerSendEffect(gamecontroller: Ptr[SDL_GameController], data: Ptr[Byte], size: CInt): CInt = extern

  /** Update a game controller's LED color.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerSetLED(gamecontroller: Ptr[SDL_GameController], red: Uint8, green: Uint8, blue: Uint8): CInt =
    extern

  /** Set the player index of an opened game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerSetPlayerIndex(gamecontroller: Ptr[SDL_GameController], player_index: CInt): Unit = extern

  /** Set whether data reporting for a game controller sensor is enabled.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerSetSensorEnabled(
      gamecontroller: Ptr[SDL_GameController],
      `type`: SDL_SensorType,
      enabled: SDL_bool
  ): CInt = extern

  /** Get the type of a game controller.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerTypeForIndex(joystick_index: CInt): SDL_GameControllerType = extern

  /** Manually pump game controller updates if not using the loop.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerUpdate(): Unit = extern

  /** Get the current assertion handler.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  def SDL_GetAssertionHandler(puserdata: Ptr[Ptr[Byte]]): SDL_AssertionHandler = extern

  /** Get a list of all assertion failures.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  def SDL_GetAssertionReport(): Ptr[SDL_AssertData] = extern

  /** Get the human-readable name of a specific audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetAudioDeviceName(index: CInt, iscapture: CInt): CString = extern

  /** Get the preferred audio format of a specific audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetAudioDeviceSpec(index: CInt, iscapture: CInt, spec: Ptr[SDL_AudioSpec]): CInt = extern

  /** Use this function to get the current audio state of an audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetAudioDeviceStatus(dev: SDL_AudioDeviceID): SDL_AudioStatus = extern

  /** Use this function to get the name of a built in audio driver.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetAudioDriver(index: CInt): CString = extern

  /** This function is a legacy means of querying the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetAudioStatus(): SDL_AudioStatus = extern

  /** Get the directory where the application was run from.
    *
    * [bindgen] header: ./SDL_filesystem.h
    */
  def SDL_GetBasePath(): CString = extern

  /** Determine the L1 cache line size of the CPU.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_GetCPUCacheLineSize(): CInt = extern

  /** Get the number of CPU cores available.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_GetCPUCount(): CInt = extern

  /** Get the clipping rectangle for a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetClipRect(surface: Ptr[SDL_Surface], rect: Ptr[SDL_Rect]): Unit = extern

  /** Get UTF-8 text from the clipboard, which must be freed with SDL_free().
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_GetClipboardText(): CString = extern

  /** Get the closest match to the requested display mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetClosestDisplayMode(
      displayIndex: CInt,
      mode: Ptr[SDL_DisplayMode],
      closest: Ptr[SDL_DisplayMode]
  ): Ptr[SDL_DisplayMode] = extern

  /** Get the color key (transparent pixel) for a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetColorKey(surface: Ptr[SDL_Surface], key: Ptr[Uint32]): CInt = extern

  /** Get the name of the current audio driver.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetCurrentAudioDriver(): CString = extern

  /** Get information about the current display mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetCurrentDisplayMode(displayIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt = extern

  /** Get the name of the currently initialized video driver.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetCurrentVideoDriver(): CString = extern

  /** Get the active cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetCursor(): Ptr[SDL_Cursor] = extern

  /** Get the default assertion handler.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  def SDL_GetDefaultAssertionHandler(): SDL_AssertionHandler = extern

  /** Get the name and preferred format of the default audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetDefaultAudioInfo(name: Ptr[CString], spec: Ptr[SDL_AudioSpec], iscapture: CInt): CInt = extern

  /** Get the default cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetDefaultCursor(): Ptr[SDL_Cursor] = extern

  /** Get information about the desktop's display mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDesktopDisplayMode(displayIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt = extern

  /** Get the desktop area represented by a display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayBounds(displayIndex: CInt, rect: Ptr[SDL_Rect]): CInt = extern

  /** Get the dots/pixels-per-inch for a display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayDPI(displayIndex: CInt, ddpi: Ptr[Float], hdpi: Ptr[Float], vdpi: Ptr[Float]): CInt = extern

  /** Get information about a specific display mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayMode(displayIndex: CInt, modeIndex: CInt, mode: Ptr[SDL_DisplayMode]): CInt = extern

  /** Get the name of a display in UTF-8 encoding.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayName(displayIndex: CInt): CString = extern

  /** Get the orientation of a display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayOrientation(displayIndex: CInt): SDL_DisplayOrientation = extern

  /** Get the usable desktop area represented by a display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetDisplayUsableBounds(displayIndex: CInt, rect: Ptr[SDL_Rect]): CInt = extern

  /** Retrieve a message about the last error that occurred on the current thread.
    *
    * [bindgen] header: ./SDL_error.h
    */
  def SDL_GetError(): CString = extern

  /** Get the last error message that was set for the current thread.
    *
    * [bindgen] header: ./SDL_error.h
    */
  def SDL_GetErrorMsg(errstr: CString, maxlen: CInt): CString = extern

  /** Query the current event filter.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_GetEventFilter(filter: Ptr[SDL_EventFilter], userdata: Ptr[Ptr[Byte]]): SDL_bool = extern

  /** Get the current state of the mouse in relation to the desktop.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetGlobalMouseState(x: Ptr[CInt], y: Ptr[CInt]): Uint32 = extern

  /** Get the window that currently has an input grab enabled.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetGrabbedWindow(): Ptr[SDL_Window] = extern

  /** Get the value of a hint.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_GetHint(name: CString): CString = extern

  /** Get the boolean value of a hint variable.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_GetHintBoolean(name: CString, default_value: SDL_bool): SDL_bool = extern

  /** Get a key code from a human-readable name.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetKeyFromName(name: CString): SDL_Keycode = extern

  /** Get the key code corresponding to the given scancode according to the current keyboard layout.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetKeyFromScancode(scancode: SDL_Scancode): SDL_Keycode = extern

  /** Get a human-readable name for a key.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetKeyName(key: SDL_Keycode): CString = extern

  /** Query the window which currently has keyboard focus.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetKeyboardFocus(): Ptr[SDL_Window] = extern

  /** Get a snapshot of the current state of the keyboard.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetKeyboardState(numkeys: Ptr[CInt]): Ptr[Uint8] = extern

  /** Get the current set of SDL memory functions
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_GetMemoryFunctions(
      malloc_func: Ptr[SDL_malloc_func],
      calloc_func: Ptr[SDL_calloc_func],
      realloc_func: Ptr[SDL_realloc_func],
      free_func: Ptr[SDL_free_func]
  ): Unit = extern

  /** Get the current key modifier state for the keyboard.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetModState(): SDL_Keymod = extern

  /** Get the window which currently has mouse focus.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetMouseFocus(): Ptr[SDL_Window] = extern

  /** Retrieve the current state of the mouse.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetMouseState(x: Ptr[CInt], y: Ptr[CInt]): Uint32 = extern

  /** Get the number of outstanding (unfreed) allocations
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_GetNumAllocations(): CInt = extern

  /** Get the number of built-in audio devices.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetNumAudioDevices(iscapture: CInt): CInt = extern

  /** Use this function to get the number of built-in audio drivers.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetNumAudioDrivers(): CInt = extern

  /** Get the number of available display modes.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetNumDisplayModes(displayIndex: CInt): CInt = extern

  /** Get the number of 2D rendering drivers available for the current display.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetNumRenderDrivers(): CInt = extern

  /** Get the number of registered touch devices.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetNumTouchDevices(): CInt = extern

  /** Get the number of active fingers for a given touch device.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetNumTouchFingers(touchID: SDL_TouchID): CInt = extern

  /** Get the number of available video displays.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetNumVideoDisplays(): CInt = extern

  /** Get the number of video drivers compiled into SDL.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetNumVideoDrivers(): CInt = extern

  /** Get the original set of SDL memory functions
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_GetOriginalMemoryFunctions(
      malloc_func: Ptr[SDL_malloc_func],
      calloc_func: Ptr[SDL_calloc_func],
      realloc_func: Ptr[SDL_realloc_func],
      free_func: Ptr[SDL_free_func]
  ): Unit = extern

  /** Get the current value of the high resolution counter.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_GetPerformanceCounter(): Uint64 = extern

  /** Get the count per second of the high resolution counter.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_GetPerformanceFrequency(): Uint64 = extern

  /** Get the human readable name of a pixel format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_GetPixelFormatName(format: Uint32): CString = extern

  /** Get the name of the platform.
    *
    * [bindgen] header: ./SDL_platform.h
    */
  def SDL_GetPlatform(): CString = extern

  /** Get the index of the display containing a point
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetPointDisplayIndex(point: Ptr[SDL_Point]): CInt = extern

  /** Get the current power supply details.
    *
    * [bindgen] header: ./SDL_power.h
    */
  def SDL_GetPowerInfo(seconds: Ptr[CInt], percent: Ptr[CInt]): SDL_PowerState = extern

  /** Get the user-and-app-specific path where files can be written.
    *
    * [bindgen] header: ./SDL_filesystem.h
    */
  def SDL_GetPrefPath(org: CString, app: CString): CString = extern

  /** Report the user's preferred locale.
    *
    * [bindgen] header: ./SDL_locale.h
    */
  def SDL_GetPreferredLocales(): Ptr[SDL_Locale] = extern

  /** Get UTF-8 text from the primary selection, which must be freed with SDL_free().
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_GetPrimarySelectionText(): CString = extern

  /** Get the number of bytes of still-queued audio.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_GetQueuedAudioSize(dev: SDL_AudioDeviceID): Uint32 = extern

  /** Get RGB values from a pixel in the specified format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_GetRGB(pixel: Uint32, format: Ptr[SDL_PixelFormat], r: Ptr[Uint8], g: Ptr[Uint8], b: Ptr[Uint8]): Unit =
    extern

  /** Get RGBA values from a pixel in the specified format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_GetRGBA(
      pixel: Uint32,
      format: Ptr[SDL_PixelFormat],
      r: Ptr[Uint8],
      g: Ptr[Uint8],
      b: Ptr[Uint8],
      a: Ptr[Uint8]
  ): Unit = extern

  /** Get the index of the display primarily containing a rect
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetRectDisplayIndex(rect: Ptr[SDL_Rect]): CInt = extern

  /** Query whether relative mouse mode is enabled.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetRelativeMouseMode(): SDL_bool = extern

  /** Retrieve the relative state of the mouse.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_GetRelativeMouseState(x: Ptr[CInt], y: Ptr[CInt]): Uint32 = extern

  /** Get the blend mode used for drawing operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRenderDrawBlendMode(renderer: Ptr[SDL_Renderer], blendMode: Ptr[SDL_BlendMode]): CInt = extern

  /** Get the color used for drawing operations (Rect, Line and Clear).
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRenderDrawColor(
      renderer: Ptr[SDL_Renderer],
      r: Ptr[Uint8],
      g: Ptr[Uint8],
      b: Ptr[Uint8],
      a: Ptr[Uint8]
  ): CInt = extern

  /** Get info about a specific 2D rendering driver for the current display.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRenderDriverInfo(index: CInt, info: Ptr[SDL_RendererInfo]): CInt = extern

  /** Get the current render target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRenderTarget(renderer: Ptr[SDL_Renderer]): Ptr[SDL_Texture] = extern

  /** Get the renderer associated with a window.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRenderer(window: Ptr[SDL_Window]): Ptr[SDL_Renderer] = extern

  /** Get information about a rendering context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRendererInfo(renderer: Ptr[SDL_Renderer], info: Ptr[SDL_RendererInfo]): CInt = extern

  /** Get the output size in pixels of a rendering context.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetRendererOutputSize(renderer: Ptr[SDL_Renderer], w: Ptr[CInt], h: Ptr[CInt]): CInt = extern

  /** Get the code revision of SDL that is linked against your program.
    *
    * [bindgen] header: ./SDL_version.h
    */
  def SDL_GetRevision(): CString = extern

  /** Obsolete function, do not use.
    *
    * [bindgen] header: ./SDL_version.h
    */
  def SDL_GetRevisionNumber(): CInt = extern

  /** Get the scancode corresponding to the given key code according to the current keyboard layout.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetScancodeFromKey(key: SDL_Keycode): SDL_Scancode = extern

  /** Get a scancode from a human-readable name.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetScancodeFromName(name: CString): SDL_Scancode = extern

  /** Get a human-readable name for a scancode.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_GetScancodeName(scancode: SDL_Scancode): CString = extern

  /** Get the shape parameters of a shaped window.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  def SDL_GetShapedWindowMode(window: Ptr[SDL_Window], shape_mode: Ptr[SDL_WindowShapeMode]): CInt = extern

  /** Get the additional alpha value used in blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetSurfaceAlphaMod(surface: Ptr[SDL_Surface], alpha: Ptr[Uint8]): CInt = extern

  /** Get the blend mode used for blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetSurfaceBlendMode(surface: Ptr[SDL_Surface], blendMode: Ptr[SDL_BlendMode]): CInt = extern

  /** Get the additional color value multiplied into blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetSurfaceColorMod(surface: Ptr[SDL_Surface], r: Ptr[Uint8], g: Ptr[Uint8], b: Ptr[Uint8]): CInt = extern

  /** Get the amount of RAM configured in the system.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_GetSystemRAM(): CInt = extern

  /** Get the additional alpha value multiplied into render copy operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetTextureAlphaMod(texture: Ptr[SDL_Texture], alpha: Ptr[Uint8]): CInt = extern

  /** Get the blend mode used for texture copy operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetTextureBlendMode(texture: Ptr[SDL_Texture], blendMode: Ptr[SDL_BlendMode]): CInt = extern

  /** Get the additional color value multiplied into render copy operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetTextureColorMod(texture: Ptr[SDL_Texture], r: Ptr[Uint8], g: Ptr[Uint8], b: Ptr[Uint8]): CInt = extern

  /** Get the scale mode used for texture scale operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetTextureScaleMode(texture: Ptr[SDL_Texture], scaleMode: Ptr[SDL_ScaleMode]): CInt = extern

  /** Get the user-specified pointer associated with a texture
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_GetTextureUserData(texture: Ptr[SDL_Texture]): Ptr[Byte] = extern

  /** Get the thread identifier for the specified thread.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_GetThreadID(thread: Ptr[SDL_Thread]): SDL_threadID = extern

  /** Get the thread name as it was specified in SDL_CreateThread().
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_GetThreadName(thread: Ptr[SDL_Thread]): CString = extern

  /** Get the number of milliseconds since SDL library initialization.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_GetTicks(): Uint32 = extern

  /** Get the number of milliseconds since SDL library initialization.
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_GetTicks64(): Uint64 = extern

  /** Get the touch ID with the given index.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetTouchDevice(index: CInt): SDL_TouchID = extern

  /** Get the type of the given touch device.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetTouchDeviceType(touchID: SDL_TouchID): SDL_TouchDeviceType = extern

  /** Get the finger object for specified touch device ID and finger index.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetTouchFinger(touchID: SDL_TouchID, index: CInt): Ptr[SDL_Finger] = extern

  /** Get the touch device name as reported from the driver or NULL if the index is invalid.
    *
    * [bindgen] header: ./SDL_touch.h
    */
  def SDL_GetTouchName(index: CInt): CString = extern

  /** Get the version of SDL that is linked against your program.
    *
    * [bindgen] header: ./SDL_version.h
    */
  def SDL_GetVersion(ver: Ptr[SDL_version]): Unit = extern

  /** Get the name of a built in video driver.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetVideoDriver(index: CInt): CString = extern

  /** Get the size of a window's borders (decorations) around the client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowBordersSize(
      window: Ptr[SDL_Window],
      top: Ptr[CInt],
      left: Ptr[CInt],
      bottom: Ptr[CInt],
      right: Ptr[CInt]
  ): CInt = extern

  /** Get the brightness (gamma multiplier) for a given window's display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowBrightness(window: Ptr[SDL_Window]): Float = extern

  /** Retrieve the data pointer associated with a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowData(window: Ptr[SDL_Window], name: CString): Ptr[Byte] = extern

  /** Get the index of the display associated with a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowDisplayIndex(window: Ptr[SDL_Window]): CInt = extern

  /** Query the display mode to use when a window is visible at fullscreen.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowDisplayMode(window: Ptr[SDL_Window], mode: Ptr[SDL_DisplayMode]): CInt = extern

  /** Get the window flags.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowFlags(window: Ptr[SDL_Window]): Uint32 = extern

  /** Get a window from a stored ID.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowFromID(id: Uint32): Ptr[SDL_Window] = extern

  /** Get the gamma ramp for a given window's display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowGammaRamp(window: Ptr[SDL_Window], red: Ptr[Uint16], green: Ptr[Uint16], blue: Ptr[Uint16]): CInt =
    extern

  /** Get a window's input grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowGrab(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Get the raw ICC profile data for the screen the window is currently on.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowICCProfile(window: Ptr[SDL_Window], size: Ptr[size_t]): Ptr[Byte] = extern

  /** Get the numeric ID of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowID(window: Ptr[SDL_Window]): Uint32 = extern

  /** Get a window's keyboard grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowKeyboardGrab(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Get the maximum size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowMaximumSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get the minimum size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowMinimumSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get a window's mouse grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowMouseGrab(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Get the mouse confinement rectangle of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowMouseRect(window: Ptr[SDL_Window]): Ptr[SDL_Rect] = extern

  /** Get the opacity of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowOpacity(window: Ptr[SDL_Window], out_opacity: Ptr[Float]): CInt = extern

  /** Get the pixel format associated with the window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowPixelFormat(window: Ptr[SDL_Window]): Uint32 = extern

  /** Get the position of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowPosition(window: Ptr[SDL_Window], x: Ptr[CInt], y: Ptr[CInt]): Unit = extern

  /** Get the size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get the size of a window in pixels.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowSizeInPixels(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get the SDL surface associated with the window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowSurface(window: Ptr[SDL_Window]): Ptr[SDL_Surface] = extern

  /** Get the title of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_GetWindowTitle(window: Ptr[SDL_Window]): CString = extern

  /** Get the YUV conversion mode
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetYUVConversionMode(): SDL_YUV_CONVERSION_MODE = extern

  /** Get the YUV conversion mode, returning the correct mode for the resolution when the current conversion mode is SDL_YUV_CONVERSION_AUTOMATIC
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_GetYUVConversionModeForResolution(width: CInt, height: CInt): SDL_YUV_CONVERSION_MODE = extern

  /** Close a haptic device previously opened with SDL_HapticOpen().
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticClose(haptic: Ptr[SDL_Haptic]): Unit = extern

  /** Destroy a haptic effect on the device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticDestroyEffect(haptic: Ptr[SDL_Haptic], effect: CInt): Unit = extern

  /** Check to see if an effect is supported by a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticEffectSupported(haptic: Ptr[SDL_Haptic], effect: Ptr[SDL_HapticEffect]): CInt = extern

  /** Get the status of the current effect on the specified haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticGetEffectStatus(haptic: Ptr[SDL_Haptic], effect: CInt): CInt = extern

  /** Get the index of a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticIndex(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Get the implementation dependent name of a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticName(device_index: CInt): CString = extern

  /** Create a new haptic effect on a specified device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticNewEffect(haptic: Ptr[SDL_Haptic], effect: Ptr[SDL_HapticEffect]): CInt = extern

  /** Get the number of haptic axes the device has.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticNumAxes(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Get the number of effects a haptic device can store.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticNumEffects(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Get the number of effects a haptic device can play at the same time.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticNumEffectsPlaying(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Open a haptic device for use.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticOpen(device_index: CInt): Ptr[SDL_Haptic] = extern

  /** Open a haptic device for use from a joystick device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticOpenFromJoystick(joystick: Ptr[SDL_Joystick]): Ptr[SDL_Haptic] = extern

  /** Try to open a haptic device from the current mouse.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticOpenFromMouse(): Ptr[SDL_Haptic] = extern

  /** Check if the haptic device at the designated index has been opened.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticOpened(device_index: CInt): CInt = extern

  /** Pause a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticPause(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Get the haptic device's supported features in bitwise manner.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticQuery(haptic: Ptr[SDL_Haptic]): CUnsignedInt = extern

  /** Initialize a haptic device for simple rumble playback.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticRumbleInit(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Run a simple rumble effect on a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticRumblePlay(haptic: Ptr[SDL_Haptic], strength: Float, length: Uint32): CInt = extern

  /** Stop the simple rumble on a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticRumbleStop(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Check whether rumble is supported on a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticRumbleSupported(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Run the haptic effect on its associated haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticRunEffect(haptic: Ptr[SDL_Haptic], effect: CInt, iterations: Uint32): CInt = extern

  /** Set the global autocenter of the device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticSetAutocenter(haptic: Ptr[SDL_Haptic], autocenter: CInt): CInt = extern

  /** Set the global gain of the specified haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticSetGain(haptic: Ptr[SDL_Haptic], gain: CInt): CInt = extern

  /** Stop all the currently playing effects on a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticStopAll(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Stop the haptic effect on its associated haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticStopEffect(haptic: Ptr[SDL_Haptic], effect: CInt): CInt = extern

  /** Unpause a haptic device.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticUnpause(haptic: Ptr[SDL_Haptic]): CInt = extern

  /** Update the properties of an effect.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_HapticUpdateEffect(haptic: Ptr[SDL_Haptic], effect: CInt, data: Ptr[SDL_HapticEffect]): CInt = extern

  /** Determine whether the CPU has 3DNow! features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_Has3DNow(): SDL_bool = extern

  /** Determine whether the CPU has ARM SIMD (ARMv6) features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasARMSIMD(): SDL_bool = extern

  /** Determine whether the CPU has AVX features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasAVX(): SDL_bool = extern

  /** Determine whether the CPU has AVX2 features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasAVX2(): SDL_bool = extern

  /** Determine whether the CPU has AVX-512F (foundation) features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasAVX512F(): SDL_bool = extern

  /** Determine whether the CPU has AltiVec features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasAltiVec(): SDL_bool = extern

  /** Query whether the clipboard exists and contains a non-empty text string.
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_HasClipboardText(): SDL_bool = extern

  /** Returns whether the surface has a color key
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_HasColorKey(surface: Ptr[SDL_Surface]): SDL_bool = extern

  /** Check for the existence of a certain event type in the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_HasEvent(`type`: Uint32): SDL_bool = extern

  /** Check for the existence of certain event types in the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_HasEvents(minType: Uint32, maxType: Uint32): SDL_bool = extern

  /** Determine whether two rectangles intersect.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_HasIntersection(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect]): SDL_bool = extern

  /** Determine whether two rectangles intersect with float precision.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_HasIntersectionF(A: Ptr[SDL_FRect], B: Ptr[SDL_FRect]): SDL_bool = extern

  /** Determine whether the CPU has LASX (LOONGARCH SIMD) features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasLASX(): SDL_bool = extern

  /** Determine whether the CPU has LSX (LOONGARCH SIMD) features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasLSX(): SDL_bool = extern

  /** Determine whether the CPU has MMX features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasMMX(): SDL_bool = extern

  /** Determine whether the CPU has NEON (ARM SIMD) features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasNEON(): SDL_bool = extern

  /** Query whether the primary selection exists and contains a non-empty text string.
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_HasPrimarySelectionText(): SDL_bool = extern

  /** Determine whether the CPU has the RDTSC instruction.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasRDTSC(): SDL_bool = extern

  /** Determine whether the CPU has SSE features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasSSE(): SDL_bool = extern

  /** Determine whether the CPU has SSE2 features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasSSE2(): SDL_bool = extern

  /** Determine whether the CPU has SSE3 features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasSSE3(): SDL_bool = extern

  /** Determine whether the CPU has SSE4.1 features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasSSE41(): SDL_bool = extern

  /** Determine whether the CPU has SSE4.2 features.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_HasSSE42(): SDL_bool = extern

  /** Check whether the platform has screen keyboard support.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_HasScreenKeyboardSupport(): SDL_bool = extern

  /** Returns whether the surface is RLE enabled
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_HasSurfaceRLE(surface: Ptr[SDL_Surface]): SDL_bool = extern

  /** Return whether the window has a surface associated with it.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_HasWindowSurface(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Hide a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_HideWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Initialize the SDL library.
    *
    * [bindgen] header: .\SDL.h [MANUAL]
    */
  def SDL_Init(flags: SDL_InitFlag): CInt = extern

  /** Compatibility function to initialize the SDL library.
    *
    * [bindgen] header: .\SDL.h [MANUAL]
    */
  def SDL_InitSubSystem(flags: SDL_InitFlag): CInt = extern

  /** Calculate the intersection of two rectangles with float precision.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_IntersectFRect(A: Ptr[SDL_FRect], B: Ptr[SDL_FRect], result: Ptr[SDL_FRect]): SDL_bool = extern

  /** Calculate the intersection of a rectangle and line segment with float precision.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_IntersectFRectAndLine(
      rect: Ptr[SDL_FRect],
      X1: Ptr[Float],
      Y1: Ptr[Float],
      X2: Ptr[Float],
      Y2: Ptr[Float]
  ): SDL_bool = extern

  /** Calculate the intersection of two rectangles.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_IntersectRect(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): SDL_bool = extern

  /** Calculate the intersection of a rectangle and line segment.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_IntersectRectAndLine(
      rect: Ptr[SDL_Rect],
      X1: Ptr[CInt],
      Y1: Ptr[CInt],
      X2: Ptr[CInt],
      Y2: Ptr[CInt]
  ): SDL_bool = extern

  /** Check if the given joystick is supported by the game controller interface.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_IsGameController(joystick_index: CInt): SDL_bool = extern

  /** Check whether the screen keyboard is shown for given window.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_IsScreenKeyboardShown(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Check whether the screensaver is currently enabled.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_IsScreenSaverEnabled(): SDL_bool = extern

  /** Return whether the given window is a shaped window.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  def SDL_IsShapedWindow(window: Ptr[SDL_Window]): SDL_bool = extern

  /** Query if the current device is a tablet.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_IsTablet(): SDL_bool = extern

  /** Check whether or not Unicode text input events are enabled.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_IsTextInputActive(): SDL_bool = extern

  /** Returns if an IME Composite or Candidate window is currently shown.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_IsTextInputShown(): SDL_bool = extern

  /** Attach a new virtual joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickAttachVirtual(`type`: SDL_JoystickType, naxes: CInt, nbuttons: CInt, nhats: CInt): CInt = extern

  /** Attach a new virtual joystick with extended properties.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickAttachVirtualEx(desc: Ptr[SDL_VirtualJoystickDesc]): CInt = extern

  /** Close a joystick previously opened with SDL_JoystickOpen().
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickClose(joystick: Ptr[SDL_Joystick]): Unit = extern

  /** Get the battery level of a joystick as SDL_JoystickPowerLevel.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickCurrentPowerLevel(joystick: Ptr[SDL_Joystick]): SDL_JoystickPowerLevel = extern

  /** Detach a virtual joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickDetachVirtual(device_index: CInt): CInt = extern

  /** Enable/disable joystick event polling.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickEventState(state: CInt): CInt = extern

  /** Get the SDL_Joystick associated with an instance id.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickFromInstanceID(instance_id: SDL_JoystickID): Ptr[SDL_Joystick] = extern

  /** Get the SDL_Joystick associated with a player index.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickFromPlayerIndex(player_index: CInt): Ptr[SDL_Joystick] = extern

  /** Get the status of a specified joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetAttached(joystick: Ptr[SDL_Joystick]): SDL_bool = extern

  /** Get the current state of an axis control on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetAxis(joystick: Ptr[SDL_Joystick], axis: CInt): Sint16 = extern

  /** Get the initial state of an axis control on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetAxisInitialState(joystick: Ptr[SDL_Joystick], axis: CInt, state: Ptr[Sint16]): SDL_bool = extern

  /** Get the ball axis change since the last poll.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetBall(joystick: Ptr[SDL_Joystick], ball: CInt, dx: Ptr[CInt], dy: Ptr[CInt]): CInt = extern

  /** Get the current state of a button on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetButton(joystick: Ptr[SDL_Joystick], button: CInt): Uint8 = extern

  /** Get the instance ID of a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceInstanceID(device_index: CInt): SDL_JoystickID = extern

  /** Get the player index of a joystick, or -1 if it's not available This can be called before any joysticks are opened.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDevicePlayerIndex(device_index: CInt): CInt = extern

  /** Get the USB product ID of a joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceProduct(device_index: CInt): Uint16 = extern

  /** Get the product version of a joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceProductVersion(device_index: CInt): Uint16 = extern

  /** Get the type of a joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceType(device_index: CInt): SDL_JoystickType = extern

  /** Get the USB vendor ID of a joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceVendor(device_index: CInt): Uint16 = extern

  /** Get the firmware version of an opened joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetFirmwareVersion(joystick: Ptr[SDL_Joystick]): Uint16 = extern

  /** Get the current state of a POV hat on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetHat(joystick: Ptr[SDL_Joystick], hat: CInt): Uint8 = extern

  /** Get the player index of an opened joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetPlayerIndex(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Get the USB product ID of an opened joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetProduct(joystick: Ptr[SDL_Joystick]): Uint16 = extern

  /** Get the product version of an opened joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetProductVersion(joystick: Ptr[SDL_Joystick]): Uint16 = extern

  /** Get the serial number of an opened joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetSerial(joystick: Ptr[SDL_Joystick]): CString = extern

  /** Get the type of an opened joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetType(joystick: Ptr[SDL_Joystick]): SDL_JoystickType = extern

  /** Get the USB vendor ID of an opened joystick, if available.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetVendor(joystick: Ptr[SDL_Joystick]): Uint16 = extern

  /** Query whether a joystick has an LED.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickHasLED(joystick: Ptr[SDL_Joystick]): SDL_bool = extern

  /** Query whether a joystick has rumble support.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickHasRumble(joystick: Ptr[SDL_Joystick]): SDL_bool = extern

  /** Query whether a joystick has rumble support on triggers.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickHasRumbleTriggers(joystick: Ptr[SDL_Joystick]): SDL_bool = extern

  /** Get the instance ID of an opened joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickInstanceID(joystick: Ptr[SDL_Joystick]): SDL_JoystickID = extern

  /** Query if a joystick has haptic features.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_JoystickIsHaptic(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Query whether or not the joystick at a given device index is virtual.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickIsVirtual(device_index: CInt): SDL_bool = extern

  /** Get the implementation dependent name of a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickName(joystick: Ptr[SDL_Joystick]): CString = extern

  /** Get the implementation dependent name of a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickNameForIndex(device_index: CInt): CString = extern

  /** Get the number of general axis controls on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickNumAxes(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Get the number of trackballs on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickNumBalls(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Get the number of buttons on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickNumButtons(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Get the number of POV hats on a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickNumHats(joystick: Ptr[SDL_Joystick]): CInt = extern

  /** Open a joystick for use.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickOpen(device_index: CInt): Ptr[SDL_Joystick] = extern

  /** Get the implementation dependent path of a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickPath(joystick: Ptr[SDL_Joystick]): CString = extern

  /** Get the implementation dependent path of a joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickPathForIndex(device_index: CInt): CString = extern

  /** Start a rumble effect.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickRumble(
      joystick: Ptr[SDL_Joystick],
      low_frequency_rumble: Uint16,
      high_frequency_rumble: Uint16,
      duration_ms: Uint32
  ): CInt = extern

  /** Start a rumble effect in the joystick's triggers
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickRumbleTriggers(
      joystick: Ptr[SDL_Joystick],
      left_rumble: Uint16,
      right_rumble: Uint16,
      duration_ms: Uint32
  ): CInt = extern

  /** Send a joystick specific effect packet
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSendEffect(joystick: Ptr[SDL_Joystick], data: Ptr[Byte], size: CInt): CInt = extern

  /** Update a joystick's LED color.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSetLED(joystick: Ptr[SDL_Joystick], red: Uint8, green: Uint8, blue: Uint8): CInt = extern

  /** Set the player index of an opened joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSetPlayerIndex(joystick: Ptr[SDL_Joystick], player_index: CInt): Unit = extern

  /** Set values on an opened, virtual-joystick's axis.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSetVirtualAxis(joystick: Ptr[SDL_Joystick], axis: CInt, value: Sint16): CInt = extern

  /** Set values on an opened, virtual-joystick's button.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSetVirtualButton(joystick: Ptr[SDL_Joystick], button: CInt, value: Uint8): CInt = extern

  /** Set values on an opened, virtual-joystick's hat.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickSetVirtualHat(joystick: Ptr[SDL_Joystick], hat: CInt, value: Uint8): CInt = extern

  /** Update the current state of the open joysticks.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickUpdate(): Unit = extern

  /** Load a BMP image from a seekable SDL data stream.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_LoadBMP_RW(src: Ptr[SDL_RWops], freesrc: CInt): Ptr[SDL_Surface] = extern

  /** Load Dollar Gesture templates from a file.
    *
    * [bindgen] header: ./SDL_gesture.h
    */
  def SDL_LoadDollarTemplates(touchId: SDL_TouchID, src: Ptr[SDL_RWops]): CInt = extern

  /** Load all the data from a file path.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_LoadFile(file: CString, datasize: Ptr[size_t]): Ptr[Byte] = extern

  /** Load all the data from an SDL data stream.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_LoadFile_RW(src: Ptr[SDL_RWops], datasize: Ptr[size_t], freesrc: CInt): Ptr[Byte] = extern

  /** Look up the address of the named function in a shared object.
    *
    * [bindgen] header: ./SDL_loadso.h
    */
  def SDL_LoadFunction(handle: Ptr[Byte], name: CString): Ptr[Byte] = extern

  /** Dynamically load a shared object.
    *
    * [bindgen] header: ./SDL_loadso.h
    */
  def SDL_LoadObject(sofile: CString): Ptr[Byte] = extern

  /** Load the audio data of a WAVE file into memory.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_LoadWAV_RW(
      src: Ptr[SDL_RWops],
      freesrc: CInt,
      spec: Ptr[SDL_AudioSpec],
      audio_buf: Ptr[Ptr[Uint8]],
      audio_len: Ptr[Uint32]
  ): Ptr[SDL_AudioSpec] = extern

  /** This function is a legacy means of locking the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_LockAudio(): Unit = extern

  /** Use this function to lock out the audio callback function for a specified device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_LockAudioDevice(dev: SDL_AudioDeviceID): Unit = extern

  /** Locking for multi-threaded access to the joystick API
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_LockJoysticks(): Unit = extern

  /** Lock the mutex.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_LockMutex(mutex: Ptr[SDL_mutex]): CInt = extern

  /** Locking for multi-threaded access to the sensor API
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_LockSensors(): Unit = extern

  /** Set up a surface for directly accessing the pixels.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_LockSurface(surface: Ptr[SDL_Surface]): CInt = extern

  /** Lock a portion of the texture for **write-only** pixel access.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_LockTexture(texture: Ptr[SDL_Texture], rect: Ptr[SDL_Rect], pixels: Ptr[Ptr[Byte]], pitch: Ptr[CInt]): CInt =
    extern

  /** Lock a portion of the texture for **write-only** pixel access, and expose it as a SDL surface.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_LockTextureToSurface(texture: Ptr[SDL_Texture], rect: Ptr[SDL_Rect], surface: Ptr[Ptr[SDL_Surface]]): CInt =
    extern

  /** Log a message with SDL_LOG_CATEGORY_APPLICATION and SDL_LOG_PRIORITY_INFO.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_Log(fmt: CString, rest: Any*): Unit = extern

  /** Log a message with SDL_LOG_PRIORITY_CRITICAL.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogCritical(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Log a message with SDL_LOG_PRIORITY_DEBUG.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogDebug(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Log a message with SDL_LOG_PRIORITY_ERROR.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogError(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Get the current log output function.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogGetOutputFunction(callback: Ptr[SDL_LogOutputFunction], userdata: Ptr[Ptr[Byte]]): Unit = extern

  /** Get the priority of a particular log category.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogGetPriority(category: CInt): SDL_LogPriority = extern

  /** Log a message with SDL_LOG_PRIORITY_INFO.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogInfo(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Log a message with the specified category and priority.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogMessage(category: CInt, priority: SDL_LogPriority, fmt: CString, rest: Any*): Unit = extern

  /** Log a message with the specified category and priority.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogMessageV(category: CInt, priority: SDL_LogPriority, fmt: CString, ap: va_list): Unit = extern

  /** Reset all priorities to default.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogResetPriorities(): Unit = extern

  /** Set the priority of all log categories.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogSetAllPriority(priority: SDL_LogPriority): Unit = extern

  /** Replace the default log output function with one of your own.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogSetOutputFunction(callback: SDL_LogOutputFunction, userdata: Ptr[Byte]): Unit = extern

  /** Set the priority of a particular log category.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogSetPriority(category: CInt, priority: SDL_LogPriority): Unit = extern

  /** Log a message with SDL_LOG_PRIORITY_VERBOSE.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogVerbose(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Log a message with SDL_LOG_PRIORITY_WARN.
    *
    * [bindgen] header: ./SDL_log.h
    */
  def SDL_LogWarn(category: CInt, fmt: CString, rest: Any*): Unit = extern

  /** Perform low-level surface blitting only.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_LowerBlit(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Perform low-level surface scaled blitting only.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_LowerBlitScaled(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Map an RGB triple to an opaque pixel value for a given pixel format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_MapRGB(format: Ptr[SDL_PixelFormat], r: Uint8, g: Uint8, b: Uint8): Uint32 = extern

  /** Map an RGBA quadruple to a pixel value for a given pixel format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_MapRGBA(format: Ptr[SDL_PixelFormat], r: Uint8, g: Uint8, b: Uint8, a: Uint8): Uint32 = extern

  /** Convert a bpp value and RGBA masks to an enumerated pixel format.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_MasksToPixelFormatEnum(bpp: CInt, Rmask: Uint32, Gmask: Uint32, Bmask: Uint32, Amask: Uint32): Uint32 = extern

  /** Make a window as large as possible.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_MaximizeWindow(window: Ptr[SDL_Window]): Unit = extern

  /** [bindgen] header: ./SDL_atomic.h
    */
  def SDL_MemoryBarrierAcquireFunction(): Unit = extern

  /** Memory barriers are designed to prevent reads and writes from being reordered by the compiler and being seen out of order on multi-core CPUs.
    *
    * [bindgen] header: ./SDL_atomic.h
    */
  def SDL_MemoryBarrierReleaseFunction(): Unit = extern

  /** Create a CAMetalLayer-backed NSView/UIView and attach it to the specified window.
    *
    * [bindgen] header: ./SDL_metal.h
    */
  def SDL_Metal_CreateView(window: Ptr[SDL_Window]): SDL_MetalView = extern

  /** Destroy an existing SDL_MetalView object.
    *
    * [bindgen] header: ./SDL_metal.h
    */
  def SDL_Metal_DestroyView(view: SDL_MetalView): Unit = extern

  /** Get the size of a window's underlying drawable in pixels (for use with setting viewport, scissor & etc).
    *
    * [bindgen] header: ./SDL_metal.h
    */
  def SDL_Metal_GetDrawableSize(window: Ptr[SDL_Window], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get a pointer to the backing CAMetalLayer for the given view.
    *
    * [bindgen] header: ./SDL_metal.h
    */
  def SDL_Metal_GetLayer(view: SDL_MetalView): Ptr[Byte] = extern

  /** Minimize a window to an iconic representation.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_MinimizeWindow(window: Ptr[SDL_Window]): Unit = extern

  /** This function is a legacy means of mixing audio.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_MixAudio(dst: Ptr[Uint8], src: Ptr[Uint8], len: Uint32, volume: CInt): Unit = extern

  /** Mix audio data in a specified format.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_MixAudioFormat(dst: Ptr[Uint8], src: Ptr[Uint8], format: SDL_AudioFormat, len: Uint32, volume: CInt): Unit =
    extern

  /** Query whether or not the current mouse has haptic capabilities.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_MouseIsHaptic(): CInt = extern

  /** Create a new audio stream.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_NewAudioStream(
      src_format: SDL_AudioFormat,
      src_channels: Uint8,
      src_rate: CInt,
      dst_format: SDL_AudioFormat,
      dst_channels: Uint8,
      dst_rate: CInt
  ): Ptr[SDL_AudioStream] = extern

  /** Count the number of haptic devices attached to the system.
    *
    * [bindgen] header: ./SDL_haptic.h
    */
  def SDL_NumHaptics(): CInt = extern

  /** Count the number of joysticks attached to the system.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_NumJoysticks(): CInt = extern

  /** Count the number of sensors attached to the system right now.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_NumSensors(): CInt = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationDidBecomeActive(): Unit = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationDidEnterBackground(): Unit = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationDidReceiveMemoryWarning(): Unit = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationWillEnterForeground(): Unit = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationWillResignActive(): Unit = extern

  /** [bindgen] header: ./SDL_system.h
    */
  def SDL_OnApplicationWillTerminate(): Unit = extern

  /** This function is a legacy means of opening the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_OpenAudio(desired: Ptr[SDL_AudioSpec], obtained: Ptr[SDL_AudioSpec]): CInt = extern

  /** Open a specific audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_OpenAudioDevice(
      device: CString,
      iscapture: CInt,
      desired: Ptr[SDL_AudioSpec],
      obtained: Ptr[SDL_AudioSpec],
      allowed_changes: CInt
  ): SDL_AudioDeviceID = extern

  /** Open a URL/URI in the browser or other appropriate external application.
    *
    * [bindgen] header: ./SDL_misc.h
    */
  def SDL_OpenURL(url: CString): CInt = extern

  /** This function is a legacy means of pausing the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_PauseAudio(pause_on: CInt): Unit = extern

  /** Use this function to pause and unpause audio playback on a specified device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_PauseAudioDevice(dev: SDL_AudioDeviceID, pause_on: CInt): Unit = extern

  /** Check the event queue for messages and optionally return them.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_PeepEvents(
      events: Ptr[SDL_Event],
      numevents: CInt,
      action: SDL_eventaction,
      minType: Uint32,
      maxType: Uint32
  ): CInt = extern

  /** Convert one of the enumerated pixel formats to a bpp value and RGBA masks.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_PixelFormatEnumToMasks(
      format: Uint32,
      bpp: Ptr[CInt],
      Rmask: Ptr[Uint32],
      Gmask: Ptr[Uint32],
      Bmask: Ptr[Uint32],
      Amask: Ptr[Uint32]
  ): SDL_bool = extern

  /** Returns true if point resides inside a rectangle.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_PointInFRect(p: Ptr[SDL_FPoint], r: Ptr[SDL_FRect]): SDL_bool = extern

  /** Returns true if point resides inside a rectangle.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_PointInRect(p: Ptr[SDL_Point], r: Ptr[SDL_Rect]): SDL_bool = extern

  /** Poll for currently pending events.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_PollEvent(event: Ptr[SDL_Event]): CInt = extern

  /** Premultiply the alpha on a block of pixels.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_PremultiplyAlpha(
      width: CInt,
      height: CInt,
      src_format: Uint32,
      src: Ptr[Byte],
      src_pitch: CInt,
      dst_format: Uint32,
      dst: Ptr[Byte],
      dst_pitch: CInt
  ): CInt = extern

  /** Pump the event loop, gathering events from the input devices.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_PumpEvents(): Unit = extern

  /** Add an event to the event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_PushEvent(event: Ptr[SDL_Event]): CInt = extern

  /** Query the attributes of a texture.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_QueryTexture(
      texture: Ptr[SDL_Texture],
      format: Ptr[Uint32],
      access: Ptr[CInt],
      w: Ptr[CInt],
      h: Ptr[CInt]
  ): CInt = extern

  /** Queue more audio on non-callback devices.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_QueueAudio(dev: SDL_AudioDeviceID, data: Ptr[Byte], len: Uint32): CInt = extern

  /** Clean up all initialized subsystems.
    *
    * [bindgen] header: .\SDL.h
    */
  def SDL_Quit(): Unit = extern

  /** Shut down specific SDL subsystems.
    *
    * [bindgen] header: .\SDL.h [MANUAL]
    */
  def SDL_QuitSubSystem(flags: SDL_InitFlag): Unit = extern

  /** Use this function to prepare a read-only memory buffer for use with RWops.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWFromConstMem(mem: Ptr[Byte], size: CInt): Ptr[SDL_RWops] = extern

  /** Use this function to create an SDL_RWops structure from a standard I/O file pointer (stdio.h's `FILE*`).
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWFromFP(fp: Ptr[Byte], autoclose: SDL_bool): Ptr[SDL_RWops] = extern

  /** Use this function to create a new SDL_RWops structure for reading from and/or writing to a named file.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWFromFile(file: CString, mode: CString): Ptr[SDL_RWops] = extern

  /** Use this function to prepare a read-write memory buffer for use with SDL_RWops.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWFromMem(mem: Ptr[Byte], size: CInt): Ptr[SDL_RWops] = extern

  /** Close and free an allocated SDL_RWops structure.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWclose(context: Ptr[SDL_RWops]): CInt = extern

  /** Read from a data source.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWread(context: Ptr[SDL_RWops], ptr: Ptr[Byte], size: size_t, maxnum: size_t): size_t = extern

  /** Seek within an SDL_RWops data stream.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWseek(context: Ptr[SDL_RWops], offset: Sint64, whence: CInt): Sint64 = extern

  /** Use this function to get the size of the data stream in an SDL_RWops.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWsize(context: Ptr[SDL_RWops]): Sint64 = extern

  /** Determine the current read/write offset in an SDL_RWops data stream.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWtell(context: Ptr[SDL_RWops]): Sint64 = extern

  /** Write to an SDL_RWops data stream.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_RWwrite(context: Ptr[SDL_RWops], ptr: Ptr[Byte], size: size_t, num: size_t): size_t = extern

  /** Raise a window above other windows and set the input focus.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_RaiseWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Use this function to read 16 bits of big-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadBE16(src: Ptr[SDL_RWops]): Uint16 = extern

  /** Use this function to read 32 bits of big-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadBE32(src: Ptr[SDL_RWops]): Uint32 = extern

  /** Use this function to read 64 bits of big-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadBE64(src: Ptr[SDL_RWops]): Uint64 = extern

  /** Use this function to read 16 bits of little-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadLE16(src: Ptr[SDL_RWops]): Uint16 = extern

  /** Use this function to read 32 bits of little-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadLE32(src: Ptr[SDL_RWops]): Uint32 = extern

  /** Use this function to read 64 bits of little-endian data from an SDL_RWops and return in native format.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadLE64(src: Ptr[SDL_RWops]): Uint64 = extern

  /** Use this function to read a byte from an SDL_RWops.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_ReadU8(src: Ptr[SDL_RWops]): Uint8 = extern

  /** Begin recording a gesture on a specified touch device or all touch devices.
    *
    * [bindgen] header: ./SDL_gesture.h
    */
  def SDL_RecordGesture(touchId: SDL_TouchID): CInt = extern

  /** Returns true if the rectangle has no area.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_RectEmpty(r: Ptr[SDL_Rect]): SDL_bool = extern

  /** Returns true if the two rectangles are equal.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_RectEquals(a: Ptr[SDL_Rect], b: Ptr[SDL_Rect]): SDL_bool = extern

  /** Register a win32 window class for SDL's use.
    *
    * [bindgen] header: ./SDL_main.h
    */
  def SDL_RegisterApp(name: CString, style: Uint32, hInst: Ptr[Byte]): CInt = extern

  /** Allocate a set of user-defined events, and return the beginning event number for that set of events.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_RegisterEvents(numevents: CInt): Uint32 = extern

  /** Remove a timer created with SDL_AddTimer().
    *
    * [bindgen] header: ./SDL_timer.h
    */
  def SDL_RemoveTimer(id: SDL_TimerID): SDL_bool = extern

  /** Clear the current rendering target with the drawing color.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderClear(renderer: Ptr[SDL_Renderer]): CInt = extern

  /** Copy a portion of the texture to the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderCopy(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Copy a portion of the texture to the current rendering, with optional rotation and flipping.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderCopyEx(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      dstrect: Ptr[SDL_Rect],
      angle: Double,
      center: Ptr[SDL_Point],
      flip: SDL_RendererFlip
  ): CInt = extern

  /** Copy a portion of the source texture to the current rendering target, with rotation and flipping, at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderCopyExF(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      dstrect: Ptr[SDL_FRect],
      angle: Double,
      center: Ptr[SDL_FPoint],
      flip: SDL_RendererFlip
  ): CInt = extern

  /** Copy a portion of the texture to the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderCopyF(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      srcrect: Ptr[SDL_Rect],
      dstrect: Ptr[SDL_FRect]
  ): CInt = extern

  /** Draw a line on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawLine(renderer: Ptr[SDL_Renderer], x1: CInt, y1: CInt, x2: CInt, y2: CInt): CInt = extern

  /** Draw a line on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawLineF(renderer: Ptr[SDL_Renderer], x1: Float, y1: Float, x2: Float, y2: Float): CInt = extern

  /** Draw a series of connected lines on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawLines(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_Point], count: CInt): CInt = extern

  /** Draw a series of connected lines on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawLinesF(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_FPoint], count: CInt): CInt = extern

  /** Draw a point on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawPoint(renderer: Ptr[SDL_Renderer], x: CInt, y: CInt): CInt = extern

  /** Draw a point on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawPointF(renderer: Ptr[SDL_Renderer], x: Float, y: Float): CInt = extern

  /** Draw multiple points on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawPoints(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_Point], count: CInt): CInt = extern

  /** Draw multiple points on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawPointsF(renderer: Ptr[SDL_Renderer], points: Ptr[SDL_FPoint], count: CInt): CInt = extern

  /** Draw a rectangle on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt = extern

  /** Draw a rectangle on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawRectF(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_FRect]): CInt = extern

  /** Draw some number of rectangles on the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawRects(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_Rect], count: CInt): CInt = extern

  /** Draw some number of rectangles on the current rendering target at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderDrawRectsF(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_FRect], count: CInt): CInt = extern

  /** Fill a rectangle on the current rendering target with the drawing color.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderFillRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt = extern

  /** Fill a rectangle on the current rendering target with the drawing color at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderFillRectF(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_FRect]): CInt = extern

  /** Fill some number of rectangles on the current rendering target with the drawing color.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderFillRects(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_Rect], count: CInt): CInt = extern

  /** Fill some number of rectangles on the current rendering target with the drawing color at subpixel precision.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderFillRectsF(renderer: Ptr[SDL_Renderer], rects: Ptr[SDL_FRect], count: CInt): CInt = extern

  /** Force the rendering context to flush any pending commands to the underlying rendering API.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderFlush(renderer: Ptr[SDL_Renderer]): CInt = extern

  /** Render a list of triangles, optionally using a texture and indices into the vertex array Color and alpha modulation is done per vertex (SDL_SetTextureColorMod and SDL_SetTextureAlphaMod are ignored).
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGeometry(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      vertices: Ptr[SDL_Vertex],
      num_vertices: CInt,
      indices: Ptr[CInt],
      num_indices: CInt
  ): CInt = extern

  /** Render a list of triangles, optionally using a texture and indices into the vertex arrays Color and alpha modulation is done per vertex (SDL_SetTextureColorMod and SDL_SetTextureAlphaMod are ignored).
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGeometryRaw(
      renderer: Ptr[SDL_Renderer],
      texture: Ptr[SDL_Texture],
      xy: Ptr[Float],
      xy_stride: CInt,
      color: Ptr[SDL_Color],
      color_stride: CInt,
      uv: Ptr[Float],
      uv_stride: CInt,
      num_vertices: CInt,
      indices: Ptr[Byte],
      num_indices: CInt,
      size_indices: CInt
  ): CInt = extern

  /** Get the clip rectangle for the current target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetClipRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): Unit = extern

  /** Get the D3D11 device associated with a renderer.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_RenderGetD3D11Device(renderer: Ptr[SDL_Renderer]): Ptr[ID3D11Device] = extern

  /** Get the D3D12 device associated with a renderer.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_RenderGetD3D12Device(renderer: Ptr[SDL_Renderer]): Ptr[ID3D12Device] = extern

  /** Get the D3D9 device associated with a renderer.
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_RenderGetD3D9Device(renderer: Ptr[SDL_Renderer]): Ptr[IDirect3DDevice9] = extern

  /** Get whether integer scales are forced for resolution-independent rendering.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetIntegerScale(renderer: Ptr[SDL_Renderer]): SDL_bool = extern

  /** Get device independent resolution for rendering.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetLogicalSize(renderer: Ptr[SDL_Renderer], w: Ptr[CInt], h: Ptr[CInt]): Unit = extern

  /** Get the Metal command encoder for the current frame
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetMetalCommandEncoder(renderer: Ptr[SDL_Renderer]): Ptr[Byte] = extern

  /** Get the CAMetalLayer associated with the given Metal renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetMetalLayer(renderer: Ptr[SDL_Renderer]): Ptr[Byte] = extern

  /** Get the drawing scale for the current target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetScale(renderer: Ptr[SDL_Renderer], scaleX: Ptr[Float], scaleY: Ptr[Float]): Unit = extern

  /** Get the drawing area for the current target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetViewport(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): Unit = extern

  /** Get the window associated with a renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderGetWindow(renderer: Ptr[SDL_Renderer]): Ptr[SDL_Window] = extern

  /** Get whether clipping is enabled on the given renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderIsClipEnabled(renderer: Ptr[SDL_Renderer]): SDL_bool = extern

  /** Get real coordinates of point in window when given logical coordinates of point in renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderLogicalToWindow(
      renderer: Ptr[SDL_Renderer],
      logicalX: Float,
      logicalY: Float,
      windowX: Ptr[CInt],
      windowY: Ptr[CInt]
  ): Unit = extern

  /** Update the screen with any rendering performed since the previous call.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderPresent(renderer: Ptr[SDL_Renderer]): Unit = extern

  /** Read pixels from the current rendering target to an array of pixels.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderReadPixels(
      renderer: Ptr[SDL_Renderer],
      rect: Ptr[SDL_Rect],
      format: Uint32,
      pixels: Ptr[Byte],
      pitch: CInt
  ): CInt = extern

  /** Set the clip rectangle for rendering on the specified target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetClipRect(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt = extern

  /** Set whether to force integer scales for resolution-independent rendering.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetIntegerScale(renderer: Ptr[SDL_Renderer], enable: SDL_bool): CInt = extern

  /** Set a device independent resolution for rendering.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetLogicalSize(renderer: Ptr[SDL_Renderer], w: CInt, h: CInt): CInt = extern

  /** Set the drawing scale for rendering on the current target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetScale(renderer: Ptr[SDL_Renderer], scaleX: Float, scaleY: Float): CInt = extern

  /** Toggle VSync of the given renderer.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetVSync(renderer: Ptr[SDL_Renderer], vsync: CInt): CInt = extern

  /** Set the drawing area for rendering on the current target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderSetViewport(renderer: Ptr[SDL_Renderer], rect: Ptr[SDL_Rect]): CInt = extern

  /** Determine whether a renderer supports the use of render targets.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderTargetSupported(renderer: Ptr[SDL_Renderer]): SDL_bool = extern

  /** Get logical coordinates of point in renderer when given real coordinates of point in window.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_RenderWindowToLogical(
      renderer: Ptr[SDL_Renderer],
      windowX: CInt,
      windowY: CInt,
      logicalX: Ptr[Float],
      logicalY: Ptr[Float]
  ): Unit = extern

  /** [bindgen] header: ./SDL_assert.h
    */
  def SDL_ReportAssertion(_0: Ptr[SDL_AssertData], _1: CString, _2: CString, _3: CInt): SDL_AssertState = extern

  /** Clear the list of all assertion failures.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  def SDL_ResetAssertionReport(): Unit = extern

  /** Reset a hint to the default value.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_ResetHint(name: CString): SDL_bool = extern

  /** Reset all hints to the default values.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_ResetHints(): Unit = extern

  /** Clear the state of the keyboard
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_ResetKeyboard(): Unit = extern

  /** Restore the size and position of a minimized or maximized window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_RestoreWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Allocate memory in a SIMD-friendly way.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_SIMDAlloc(len: size_t): Ptr[Byte] = extern

  /** Deallocate memory obtained from SDL_SIMDAlloc
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_SIMDFree(ptr: Ptr[Byte]): Unit = extern

  /** Report the alignment this system needs for SIMD allocations.
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_SIMDGetAlignment(): size_t = extern

  /** Reallocate memory obtained from SDL_SIMDAlloc
    *
    * [bindgen] header: ./SDL_cpuinfo.h
    */
  def SDL_SIMDRealloc(mem: Ptr[Byte], len: size_t): Ptr[Byte] = extern

  /** Save all currently loaded Dollar Gesture templates.
    *
    * [bindgen] header: ./SDL_gesture.h
    */
  def SDL_SaveAllDollarTemplates(dst: Ptr[SDL_RWops]): CInt = extern

  /** Save a surface to a seekable SDL data stream in BMP format.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SaveBMP_RW(surface: Ptr[SDL_Surface], dst: Ptr[SDL_RWops], freedst: CInt): CInt = extern

  /** Save a currently loaded Dollar Gesture template.
    *
    * [bindgen] header: ./SDL_gesture.h
    */
  def SDL_SaveDollarTemplate(gestureId: SDL_GestureID, dst: Ptr[SDL_RWops]): CInt = extern

  /** Atomically increment a semaphore's value and wake waiting threads.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_SemPost(sem: Ptr[SDL_sem]): CInt = extern

  /** See if a semaphore has a positive value and decrement it if it does.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_SemTryWait(sem: Ptr[SDL_sem]): CInt = extern

  /** Get the current value of a semaphore.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_SemValue(sem: Ptr[SDL_sem]): Uint32 = extern

  /** Wait until a semaphore has a positive value and then decrements it.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_SemWait(sem: Ptr[SDL_sem]): CInt = extern

  /** Wait until a semaphore has a positive value and then decrements it.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_SemWaitTimeout(sem: Ptr[SDL_sem], timeout: Uint32): CInt = extern

  /** Close a sensor previously opened with SDL_SensorOpen().
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorClose(sensor: Ptr[SDL_Sensor]): Unit = extern

  /** Return the SDL_Sensor associated with an instance id.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorFromInstanceID(instance_id: SDL_SensorID): Ptr[SDL_Sensor] = extern

  /** Get the current state of an opened sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetData(sensor: Ptr[SDL_Sensor], data: Ptr[Float], num_values: CInt): CInt = extern

  /** Get the current state of an opened sensor with the timestamp of the last update.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetDataWithTimestamp(
      sensor: Ptr[SDL_Sensor],
      timestamp: Ptr[Uint64],
      data: Ptr[Float],
      num_values: CInt
  ): CInt = extern

  /** Get the instance ID of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetDeviceInstanceID(device_index: CInt): SDL_SensorID = extern

  /** Get the implementation dependent name of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetDeviceName(device_index: CInt): CString = extern

  /** Get the platform dependent type of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetDeviceNonPortableType(device_index: CInt): CInt = extern

  /** Get the type of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetDeviceType(device_index: CInt): SDL_SensorType = extern

  /** Get the instance ID of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetInstanceID(sensor: Ptr[SDL_Sensor]): SDL_SensorID = extern

  /** Get the implementation dependent name of a sensor
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetName(sensor: Ptr[SDL_Sensor]): CString = extern

  /** Get the platform dependent type of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetNonPortableType(sensor: Ptr[SDL_Sensor]): CInt = extern

  /** Get the type of a sensor.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorGetType(sensor: Ptr[SDL_Sensor]): SDL_SensorType = extern

  /** Open a sensor for use.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorOpen(device_index: CInt): Ptr[SDL_Sensor] = extern

  /** Update the current state of the open sensors.
    *
    * [bindgen] header: ./SDL_sensor.h
    */
  def SDL_SensorUpdate(): Unit = extern

  /** Set an application-defined assertion handler.
    *
    * [bindgen] header: ./SDL_assert.h
    */
  def SDL_SetAssertionHandler(handler: SDL_AssertionHandler, userdata: Ptr[Byte]): Unit = extern

  /** Set the clipping rectangle for a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetClipRect(surface: Ptr[SDL_Surface], rect: Ptr[SDL_Rect]): SDL_bool = extern

  /** Put UTF-8 text into the clipboard.
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_SetClipboardText(text: CString): CInt = extern

  /** Set the color key (transparent pixel) in a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetColorKey(surface: Ptr[SDL_Surface], flag: CInt, key: Uint32): CInt = extern

  /** Set the active cursor.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_SetCursor(cursor: Ptr[SDL_Cursor]): Unit = extern

  /** Set the SDL error message for the current thread.
    *
    * [bindgen] header: ./SDL_error.h
    */
  def SDL_SetError(fmt: CString, rest: Any*): CInt = extern

  /** Set up a filter to process all events before they change internal state and are posted to the internal event queue.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_SetEventFilter(filter: SDL_EventFilter, userdata: Ptr[Byte]): Unit = extern

  /** Set a hint with normal priority.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_SetHint(name: CString, value: CString): SDL_bool = extern

  /** Set a hint with a specific priority.
    *
    * [bindgen] header: ./SDL_hints.h
    */
  def SDL_SetHintWithPriority(name: CString, value: CString, priority: SDL_HintPriority): SDL_bool = extern

  /** Circumvent failure of SDL_Init() when not using SDL_main() as an entry point.
    *
    * [bindgen] header: ./SDL_main.h
    */
  def SDL_SetMainReady(): Unit = extern

  /** Replace SDL's memory allocation functions with a custom set
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_SetMemoryFunctions(
      malloc_func: SDL_malloc_func,
      calloc_func: SDL_calloc_func,
      realloc_func: SDL_realloc_func,
      free_func: SDL_free_func
  ): CInt = extern

  /** Set the current key modifier state for the keyboard.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_SetModState(modstate: SDL_Keymod): Unit = extern

  /** Set a range of colors in a palette.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_SetPaletteColors(palette: Ptr[SDL_Palette], colors: Ptr[SDL_Color], firstcolor: CInt, ncolors: CInt): CInt =
    extern

  /** Set the palette for a pixel format structure.
    *
    * [bindgen] header: ./SDL_pixels.h
    */
  def SDL_SetPixelFormatPalette(format: Ptr[SDL_PixelFormat], palette: Ptr[SDL_Palette]): CInt = extern

  /** Put UTF-8 text into the primary selection.
    *
    * [bindgen] header: ./SDL_clipboard.h
    */
  def SDL_SetPrimarySelectionText(text: CString): CInt = extern

  /** Set relative mouse mode.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_SetRelativeMouseMode(enabled: SDL_bool): CInt = extern

  /** Set the blend mode used for drawing operations (Fill and Line).
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetRenderDrawBlendMode(renderer: Ptr[SDL_Renderer], blendMode: SDL_BlendMode): CInt = extern

  /** Set the color used for drawing operations (Rect, Line and Clear).
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetRenderDrawColor(renderer: Ptr[SDL_Renderer], r: Uint8, g: Uint8, b: Uint8, a: Uint8): CInt = extern

  /** Set a texture as the current rendering target.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetRenderTarget(renderer: Ptr[SDL_Renderer], texture: Ptr[SDL_Texture]): CInt = extern

  /** Set an additional alpha value used in blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetSurfaceAlphaMod(surface: Ptr[SDL_Surface], alpha: Uint8): CInt = extern

  /** Set the blend mode used for blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetSurfaceBlendMode(surface: Ptr[SDL_Surface], blendMode: SDL_BlendMode): CInt = extern

  /** Set an additional color value multiplied into blit operations.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetSurfaceColorMod(surface: Ptr[SDL_Surface], r: Uint8, g: Uint8, b: Uint8): CInt = extern

  /** Set the palette used by a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetSurfacePalette(surface: Ptr[SDL_Surface], palette: Ptr[SDL_Palette]): CInt = extern

  /** Set the RLE acceleration hint for a surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetSurfaceRLE(surface: Ptr[SDL_Surface], flag: CInt): CInt = extern

  /** Set the rectangle used to type Unicode text inputs. Native input methods will place a window with word suggestions near it, without covering the text being inputted.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_SetTextInputRect(rect: Ptr[SDL_Rect]): Unit = extern

  /** Set an additional alpha value multiplied into render copy operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetTextureAlphaMod(texture: Ptr[SDL_Texture], alpha: Uint8): CInt = extern

  /** Set the blend mode for a texture, used by SDL_RenderCopy().
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetTextureBlendMode(texture: Ptr[SDL_Texture], blendMode: SDL_BlendMode): CInt = extern

  /** Set an additional color value multiplied into render copy operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetTextureColorMod(texture: Ptr[SDL_Texture], r: Uint8, g: Uint8, b: Uint8): CInt = extern

  /** Set the scale mode used for texture scale operations.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetTextureScaleMode(texture: Ptr[SDL_Texture], scaleMode: SDL_ScaleMode): CInt = extern

  /** Associate a user-specified pointer with a texture.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_SetTextureUserData(texture: Ptr[SDL_Texture], userdata: Ptr[Byte]): CInt = extern

  /** Set the priority for the current thread.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_SetThreadPriority(priority: SDL_ThreadPriority): CInt = extern

  /** Set the window to always be above the others.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowAlwaysOnTop(window: Ptr[SDL_Window], on_top: SDL_bool): Unit = extern

  /** Set the border state of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowBordered(window: Ptr[SDL_Window], bordered: SDL_bool): Unit = extern

  /** Set the brightness (gamma multiplier) for a given window's display.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowBrightness(window: Ptr[SDL_Window], brightness: Float): CInt = extern

  /** Associate an arbitrary named pointer with a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowData(window: Ptr[SDL_Window], name: CString, userdata: Ptr[Byte]): Ptr[Byte] = extern

  /** Set the display mode to use when a window is visible at fullscreen.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowDisplayMode(window: Ptr[SDL_Window], mode: Ptr[SDL_DisplayMode]): CInt = extern

  /** Set a window's fullscreen state.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowFullscreen(window: Ptr[SDL_Window], flags: Uint32): CInt = extern

  /** Set the gamma ramp for the display that owns a given window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowGammaRamp(window: Ptr[SDL_Window], red: Ptr[Uint16], green: Ptr[Uint16], blue: Ptr[Uint16]): CInt =
    extern

  /** Set a window's input grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowGrab(window: Ptr[SDL_Window], grabbed: SDL_bool): Unit = extern

  /** Provide a callback that decides if a window region has special properties.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowHitTest(window: Ptr[SDL_Window], callback: SDL_HitTest, callback_data: Ptr[Byte]): CInt = extern

  /** Set the icon for a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowIcon(window: Ptr[SDL_Window], icon: Ptr[SDL_Surface]): Unit = extern

  /** Explicitly set input focus to the window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowInputFocus(window: Ptr[SDL_Window]): CInt = extern

  /** Set a window's keyboard grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowKeyboardGrab(window: Ptr[SDL_Window], grabbed: SDL_bool): Unit = extern

  /** Set the maximum size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowMaximumSize(window: Ptr[SDL_Window], max_w: CInt, max_h: CInt): Unit = extern

  /** Set the minimum size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowMinimumSize(window: Ptr[SDL_Window], min_w: CInt, min_h: CInt): Unit = extern

  /** Set the window as a modal for another window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowModalFor(modal_window: Ptr[SDL_Window], parent_window: Ptr[SDL_Window]): CInt = extern

  /** Set a window's mouse grab mode.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowMouseGrab(window: Ptr[SDL_Window], grabbed: SDL_bool): Unit = extern

  /** Confines the cursor to the specified area of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowMouseRect(window: Ptr[SDL_Window], rect: Ptr[SDL_Rect]): CInt = extern

  /** Set the opacity for a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowOpacity(window: Ptr[SDL_Window], opacity: Float): CInt = extern

  /** Set the position of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowPosition(window: Ptr[SDL_Window], x: CInt, y: CInt): Unit = extern

  /** Set the user-resizable state of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowResizable(window: Ptr[SDL_Window], resizable: SDL_bool): Unit = extern

  /** Set the shape and parameters of a shaped window.
    *
    * [bindgen] header: ./SDL_shape.h
    */
  def SDL_SetWindowShape(window: Ptr[SDL_Window], shape: Ptr[SDL_Surface], shape_mode: Ptr[SDL_WindowShapeMode]): CInt =
    extern

  /** Set the size of a window's client area.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowSize(window: Ptr[SDL_Window], w: CInt, h: CInt): Unit = extern

  /** Set the title of a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_SetWindowTitle(window: Ptr[SDL_Window], title: CString): Unit = extern

  /** Set a callback for every Windows message, run before TranslateMessage().
    *
    * [bindgen] header: ./SDL_system.h
    */
  def SDL_SetWindowsMessageHook(callback: SDL_WindowsMessageHook, userdata: Ptr[Byte]): Unit = extern

  /** Set the YUV conversion mode
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SetYUVConversionMode(mode: SDL_YUV_CONVERSION_MODE): Unit = extern

  /** Toggle whether or not the cursor is shown.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_ShowCursor(toggle: CInt): CInt = extern

  /** Create a modal message box.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  def SDL_ShowMessageBox(messageboxdata: Ptr[SDL_MessageBoxData], buttonid: Ptr[CInt]): CInt = extern

  /** Display a simple modal message box.
    *
    * [bindgen] header: ./SDL_messagebox.h
    */
  def SDL_ShowSimpleMessageBox(flags: Uint32, title: CString, message: CString, window: Ptr[SDL_Window]): CInt = extern

  /** Show a window.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_ShowWindow(window: Ptr[SDL_Window]): Unit = extern

  /** Perform a fast, low quality, stretch blit between two surfaces of the same format.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SoftStretch(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Perform bilinear scaling between two surfaces of the same format, 32BPP.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_SoftStretchLinear(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Start accepting Unicode text input events.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_StartTextInput(): Unit = extern

  /** Stop receiving any text input events.
    *
    * [bindgen] header: ./SDL_keyboard.h
    */
  def SDL_StopTextInput(): Unit = extern

  /** [bindgen] header: ./SDL_endian.h
    */
  def SDL_SwapFloat(x: Float): Float = extern

  /** Cleanup all TLS data for this thread.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_TLSCleanup(): Unit = extern

  /** Create a piece of thread-local storage.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_TLSCreate(): SDL_TLSID = extern

  /** Get the current thread's value associated with a thread local storage ID.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_TLSGet(id: SDL_TLSID): Ptr[Byte] = extern

  /** Set the current thread's value associated with a thread local storage ID.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_TLSSet(id: SDL_TLSID, value: Ptr[Byte], destructor: CFuncPtr1[Ptr[Byte], Unit]): CInt = extern

  /** Get the thread identifier for the current thread.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_ThreadID(): SDL_threadID = extern

  /** Try to lock a mutex without blocking.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_TryLockMutex(mutex: Ptr[SDL_mutex]): CInt = extern

  /** Calculate the union of two rectangles with float precision.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_UnionFRect(A: Ptr[SDL_FRect], B: Ptr[SDL_FRect], result: Ptr[SDL_FRect]): Unit = extern

  /** Calculate the union of two rectangles.
    *
    * [bindgen] header: ./SDL_rect.h
    */
  def SDL_UnionRect(A: Ptr[SDL_Rect], B: Ptr[SDL_Rect], result: Ptr[SDL_Rect]): Unit = extern

  /** Unload a shared object from memory.
    *
    * [bindgen] header: ./SDL_loadso.h
    */
  def SDL_UnloadObject(handle: Ptr[Byte]): Unit = extern

  /** This function is a legacy means of unlocking the audio device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_UnlockAudio(): Unit = extern

  /** Use this function to unlock the audio callback function for a specified device.
    *
    * [bindgen] header: ./SDL_audio.h
    */
  def SDL_UnlockAudioDevice(dev: SDL_AudioDeviceID): Unit = extern

  /** Unlocking for multi-threaded access to the joystick API
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_UnlockJoysticks(): Unit = extern

  /** Unlock the mutex.
    *
    * [bindgen] header: ./SDL_mutex.h
    */
  def SDL_UnlockMutex(mutex: Ptr[SDL_mutex]): CInt = extern

  /** [bindgen] header: ./SDL_sensor.h
    */
  def SDL_UnlockSensors(): Unit = extern

  /** Release a surface after directly accessing the pixels.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_UnlockSurface(surface: Ptr[SDL_Surface]): Unit = extern

  /** Unlock a texture, uploading the changes to video memory, if needed.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_UnlockTexture(texture: Ptr[SDL_Texture]): Unit = extern

  /** Deregister the win32 window class from an SDL_RegisterApp call.
    *
    * [bindgen] header: ./SDL_main.h
    */
  def SDL_UnregisterApp(): Unit = extern

  /** Update a rectangle within a planar NV12 or NV21 texture with new pixels.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_UpdateNVTexture(
      texture: Ptr[SDL_Texture],
      rect: Ptr[SDL_Rect],
      Yplane: Ptr[Uint8],
      Ypitch: CInt,
      UVplane: Ptr[Uint8],
      UVpitch: CInt
  ): CInt = extern

  /** Update the given texture rectangle with new pixel data.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_UpdateTexture(texture: Ptr[SDL_Texture], rect: Ptr[SDL_Rect], pixels: Ptr[Byte], pitch: CInt): CInt = extern

  /** Copy the window surface to the screen.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_UpdateWindowSurface(window: Ptr[SDL_Window]): CInt = extern

  /** Copy areas of the window surface to the screen.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_UpdateWindowSurfaceRects(window: Ptr[SDL_Window], rects: Ptr[SDL_Rect], numrects: CInt): CInt = extern

  /** Update a rectangle within a planar YV12 or IYUV texture with new pixel data.
    *
    * [bindgen] header: ./SDL_render.h
    */
  def SDL_UpdateYUVTexture(
      texture: Ptr[SDL_Texture],
      rect: Ptr[SDL_Rect],
      Yplane: Ptr[Uint8],
      Ypitch: CInt,
      Uplane: Ptr[Uint8],
      Upitch: CInt,
      Vplane: Ptr[Uint8],
      Vpitch: CInt
  ): CInt = extern

  /** Perform a fast blit from the source surface to the destination surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_UpperBlit(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Perform a scaled surface copy to a destination surface.
    *
    * [bindgen] header: ./SDL_surface.h
    */
  def SDL_UpperBlitScaled(
      src: Ptr[SDL_Surface],
      srcrect: Ptr[SDL_Rect],
      dst: Ptr[SDL_Surface],
      dstrect: Ptr[SDL_Rect]
  ): CInt = extern

  /** Initialize the video subsystem, optionally specifying a video driver.
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_VideoInit(driver_name: CString): CInt = extern

  /** Shut down the video subsystem, if initialized with SDL_VideoInit().
    *
    * [bindgen] header: ./SDL_video.h
    */
  def SDL_VideoQuit(): Unit = extern

  /** Wait indefinitely for the next available event.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_WaitEvent(event: Ptr[SDL_Event]): CInt = extern

  /** Wait until the specified timeout (in milliseconds) for the next available event.
    *
    * [bindgen] header: ./SDL_events.h
    */
  def SDL_WaitEventTimeout(event: Ptr[SDL_Event], timeout: CInt): CInt = extern

  /** Wait for a thread to finish.
    *
    * [bindgen] header: ./SDL_thread.h
    */
  def SDL_WaitThread(thread: Ptr[SDL_Thread], status: Ptr[CInt]): Unit = extern

  /** Move the mouse to the given position in global screen space.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_WarpMouseGlobal(x: CInt, y: CInt): CInt = extern

  /** Move the mouse cursor to the given position within the window.
    *
    * [bindgen] header: ./SDL_mouse.h
    */
  def SDL_WarpMouseInWindow(window: Ptr[SDL_Window], x: CInt, y: CInt): Unit = extern

  /** Get a mask of the specified subsystems which are currently initialized.
    *
    * [bindgen] header: .\SDL.h [MANUAL]
    */
  def SDL_WasInit(flags: SDL_InitFlag): Uint32 = extern

  /** Use this function to write 16 bits in native format to a SDL_RWops as big-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteBE16(dst: Ptr[SDL_RWops], value: Uint16): size_t = extern

  /** Use this function to write 32 bits in native format to a SDL_RWops as big-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteBE32(dst: Ptr[SDL_RWops], value: Uint32): size_t = extern

  /** Use this function to write 64 bits in native format to a SDL_RWops as big-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteBE64(dst: Ptr[SDL_RWops], value: Uint64): size_t = extern

  /** Use this function to write 16 bits in native format to a SDL_RWops as little-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteLE16(dst: Ptr[SDL_RWops], value: Uint16): size_t = extern

  /** Use this function to write 32 bits in native format to a SDL_RWops as little-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteLE32(dst: Ptr[SDL_RWops], value: Uint32): size_t = extern

  /** Use this function to write 64 bits in native format to a SDL_RWops as little-endian data.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteLE64(dst: Ptr[SDL_RWops], value: Uint64): size_t = extern

  /** Use this function to write a byte to an SDL_RWops.
    *
    * [bindgen] header: ./SDL_rwops.h
    */
  def SDL_WriteU8(dst: Ptr[SDL_RWops], value: Uint8): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_abs(x: CInt): CInt = extern

  /** Use this function to compute arc cosine of `x`.
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_acos(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_acosf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_asin(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_asinf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_asprintf(strp: Ptr[CString], fmt: CString, rest: Any*): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atan(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atan2(y: Double, x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atan2f(y: Float, x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atanf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atof(str: CString): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_atoi(str: CString): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_bsearch(
      key: Ptr[Byte],
      base: Ptr[Byte],
      nmemb: size_t,
      size: size_t,
      compare: CFuncPtr2[Ptr[Byte], Ptr[Byte], CInt]
  ): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_calloc(nmemb: size_t, size: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ceil(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ceilf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_copysign(x: Double, y: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_copysignf(x: Float, y: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_cos(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_cosf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_crc16(crc: Uint16, data: Ptr[Byte], len: size_t): Uint16 = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_crc32(crc: Uint32, data: Ptr[Byte], len: size_t): Uint32 = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_exp(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_expf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_fabs(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_fabsf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_floor(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_floorf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_fmod(x: Double, y: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_fmodf(x: Float, y: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_free(mem: Ptr[Byte]): Unit = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_getenv(name: CString): CString = extern

  /** Start or stop a BLE scan on iOS and tvOS to pair Steam Controllers
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_ble_scan(active: SDL_bool): Unit = extern

  /** Close a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_close(dev: Ptr[SDL_hid_device]): Unit = extern

  /** Check to see if devices may have been added or removed.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_device_change_count(): Uint32 = extern

  /** Enumerate the HID Devices.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_enumerate(vendor_id: CUnsignedShort, product_id: CUnsignedShort): Ptr[SDL_hid_device_info] = extern

  /** Finalize the HIDAPI library.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_exit(): CInt = extern

  /** Free an enumeration Linked List
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_free_enumeration(devs: Ptr[SDL_hid_device_info]): Unit = extern

  /** Get a feature report from a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_get_feature_report(dev: Ptr[SDL_hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

  /** Get a string from a HID device, based on its string index.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_get_indexed_string(
      dev: Ptr[SDL_hid_device],
      string_index: CInt,
      string: Ptr[wchar_t],
      maxlen: size_t
  ): CInt = extern

  /** Get The Manufacturer String from a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_get_manufacturer_string(dev: Ptr[SDL_hid_device], string: Ptr[wchar_t], maxlen: size_t): CInt = extern

  /** Get The Product String from a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_get_product_string(dev: Ptr[SDL_hid_device], string: Ptr[wchar_t], maxlen: size_t): CInt = extern

  /** Get The Serial Number String from a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_get_serial_number_string(dev: Ptr[SDL_hid_device], string: Ptr[wchar_t], maxlen: size_t): CInt = extern

  /** Initialize the HIDAPI library.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_init(): CInt = extern

  /** Open a HID device using a Vendor ID (VID), Product ID (PID) and optionally a serial number.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_open(
      vendor_id: CUnsignedShort,
      product_id: CUnsignedShort,
      serial_number: Ptr[wchar_t]
  ): Ptr[SDL_hid_device] = extern

  /** Open a HID device by its path name.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_open_path(path: CString, bExclusive: CInt): Ptr[SDL_hid_device] = extern

  /** Read an Input report from a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_read(dev: Ptr[SDL_hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

  /** Read an Input report from a HID device with timeout.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_read_timeout(
      dev: Ptr[SDL_hid_device],
      data: Ptr[CUnsignedChar],
      length: size_t,
      milliseconds: CInt
  ): CInt = extern

  /** Send a Feature report to the device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_send_feature_report(dev: Ptr[SDL_hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

  /** Set the device handle to be non-blocking.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_set_nonblocking(dev: Ptr[SDL_hid_device], nonblock: CInt): CInt = extern

  /** Write an Output report to a HID device.
    *
    * [bindgen] header: ./SDL_hidapi.h
    */
  def SDL_hid_write(dev: Ptr[SDL_hid_device], data: Ptr[CUnsignedChar], length: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_iconv(
      cd: SDL_iconv_t,
      inbuf: Ptr[CString],
      inbytesleft: Ptr[size_t],
      outbuf: Ptr[CString],
      outbytesleft: Ptr[size_t]
  ): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_iconv_close(cd: SDL_iconv_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_iconv_open(tocode: CString, fromcode: CString): SDL_iconv_t = extern

  /** This function converts a buffer or string between encodings in one pass, returning a string that must be freed with SDL_free() or NULL on error.
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_iconv_string(tocode: CString, fromcode: CString, inbuf: CString, inbytesleft: size_t): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isalnum(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isalpha(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isblank(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_iscntrl(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isdigit(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isgraph(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_islower(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isprint(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ispunct(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isspace(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isupper(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_isxdigit(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_itoa(value: CInt, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_lltoa(value: Sint64, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_log(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_log10(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_log10f(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_logf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_lround(x: Double): CLongInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_lroundf(x: Float): CLongInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ltoa(value: CLongInt, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_main.h
    */
  def SDL_main(argc: CInt, argv: Ptr[CString]): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_malloc(size: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memcmp(s1: Ptr[Byte], s2: Ptr[Byte], len: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memcpy(dst: Ptr[Byte], src: Ptr[Byte], len: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memcpy4(dst: Ptr[Byte], src: Ptr[Byte], dwords: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memmove(dst: Ptr[Byte], src: Ptr[Byte], len: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memset(dst: Ptr[Byte], c: CInt, len: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_memset4(dst: Ptr[Byte], `val`: Uint32, dwords: size_t): Unit = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_pow(x: Double, y: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_powf(x: Float, y: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_qsort(base: Ptr[Byte], nmemb: size_t, size: size_t, compare: CFuncPtr2[Ptr[Byte], Ptr[Byte], CInt]): Unit =
    extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_realloc(mem: Ptr[Byte], size: size_t): Ptr[Byte] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_round(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_roundf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_scalbn(x: Double, n: CInt): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_scalbnf(x: Float, n: CInt): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_setenv(name: CString, value: CString, overwrite: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_sin(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_sinf(x: Float): Float = extern

  /** If a + b would overflow, return -1. Otherwise store a + b via ret and return 0.
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_size_add_overflow(a: size_t, b: size_t, ret: Ptr[size_t]): CInt = extern

  /** If a * b would overflow, return -1. Otherwise store a * b via ret and return 0.
    *
    * [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_size_mul_overflow(a: size_t, b: size_t, ret: Ptr[size_t]): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_snprintf(text: CString, maxlen: size_t, fmt: CString, rest: Any*): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_sqrt(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_sqrtf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_sscanf(text: CString, fmt: CString, rest: Any*): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strcasecmp(str1: CString, str2: CString): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strcasestr(haystack: CString, needle: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strchr(str: CString, c: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strcmp(str1: CString, str2: CString): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strdup(str: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strlcat(dst: CString, src: CString, maxlen: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strlcpy(dst: CString, src: CString, maxlen: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strlen(str: CString): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strlwr(str: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strncasecmp(str1: CString, str2: CString, len: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strncmp(str1: CString, str2: CString, maxlen: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strrchr(str: CString, c: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strrev(str: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strstr(haystack: CString, needle: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtod(str: CString, endp: Ptr[CString]): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtokr(s1: CString, s2: CString, saveptr: Ptr[CString]): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtol(str: CString, endp: Ptr[CString], base: CInt): CLongInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtoll(str: CString, endp: Ptr[CString], base: CInt): Sint64 = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtoul(str: CString, endp: Ptr[CString], base: CInt): CUnsignedLongInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strtoull(str: CString, endp: Ptr[CString], base: CInt): Uint64 = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_strupr(str: CString): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_tan(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_tanf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_tolower(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_toupper(x: CInt): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_trunc(x: Double): Double = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_truncf(x: Float): Float = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_uitoa(value: CUnsignedInt, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ulltoa(value: Uint64, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_ultoa(value: CUnsignedLongInt, str: CString, radix: CInt): CString = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_utf8strlcpy(dst: CString, src: CString, dst_bytes: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_utf8strlen(str: CString): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_utf8strnlen(str: CString, bytes: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_vasprintf(strp: Ptr[CString], fmt: CString, ap: va_list): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_vsnprintf(text: CString, maxlen: size_t, fmt: CString, ap: va_list): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_vsscanf(text: CString, fmt: CString, ap: va_list): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcscasecmp(str1: Ptr[wchar_t], str2: Ptr[wchar_t]): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcscmp(str1: Ptr[wchar_t], str2: Ptr[wchar_t]): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcsdup(wstr: Ptr[wchar_t]): Ptr[wchar_t] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcslcat(dst: Ptr[wchar_t], src: Ptr[wchar_t], maxlen: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcslcpy(dst: Ptr[wchar_t], src: Ptr[wchar_t], maxlen: size_t): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcslen(wstr: Ptr[wchar_t]): size_t = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcsncasecmp(str1: Ptr[wchar_t], str2: Ptr[wchar_t], len: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcsncmp(str1: Ptr[wchar_t], str2: Ptr[wchar_t], maxlen: size_t): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def SDL_wcsstr(haystack: Ptr[wchar_t], needle: Ptr[wchar_t]): Ptr[wchar_t] = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def _SDL_size_add_overflow_builtin(a: size_t, b: size_t, ret: Ptr[size_t]): CInt = extern

  /** [bindgen] header: ./SDL_stdinc.h
    */
  def _SDL_size_mul_overflow_builtin(a: size_t, b: size_t, ret: Ptr[size_t]): CInt = extern

  /** [bindgen] header: ./SDL_assert.h
    */
  def __debugbreak(): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GUIDFromString(pchGUID: CString, __return: Ptr[SDL_GUID]): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GUIDToString(guid: Ptr[SDL_GUID], pszGUID: CString, cbGUID: CInt): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GameControllerGetBindForAxis(
      gamecontroller: Ptr[SDL_GameController],
      axis: SDL_GameControllerAxis,
      __return: Ptr[SDL_GameControllerButtonBind]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GameControllerGetBindForButton(
      gamecontroller: Ptr[SDL_GameController],
      button: SDL_GameControllerButton,
      __return: Ptr[SDL_GameControllerButtonBind]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GameControllerMappingForGUID(guid: Ptr[SDL_JoystickGUID]): CString = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_GetJoystickGUIDInfo(
      guid: Ptr[SDL_JoystickGUID],
      vendor: Ptr[Uint16],
      product: Ptr[Uint16],
      version: Ptr[Uint16],
      crc16: Ptr[Uint16]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_JoystickGetDeviceGUID(
      device_index: CInt,
      __return: Ptr[SDL_JoystickGUID]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_JoystickGetGUID(
      joystick: Ptr[SDL_Joystick],
      __return: Ptr[SDL_JoystickGUID]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_JoystickGetGUIDFromString(
      pchGUID: CString,
      __return: Ptr[SDL_JoystickGUID]
  ): Unit = extern

  private[sdl2] def __sn_wrap_sdl2_SDL_JoystickGetGUIDString(
      guid: Ptr[SDL_JoystickGUID],
      pszGUID: CString,
      cbGUID: CInt
  ): Unit = extern

  /** [bindgen] header: ./SDL_endian.h
    */
  def _m_prefetch(__P: Ptr[Byte]): Unit = extern

object functions:
  import _root_.sdl2.enumerations.*
  import _root_.sdl2.predef.*
  import _root_.sdl2.aliases.*
  import _root_.sdl2.structs.*
  import _root_.sdl2.unions.*
  import extern_functions.*
  export extern_functions.*

  /** Convert a GUID string into a ::SDL_GUID structure.
    *
    * [bindgen] header: ./SDL_guid.h
    */
  def SDL_GUIDFromString(pchGUID: CString)(using Zone): SDL_GUID =
    val __ptr_0: Ptr[SDL_GUID] = alloc[SDL_GUID](1)
    __sn_wrap_sdl2_SDL_GUIDFromString(pchGUID, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Convert a GUID string into a ::SDL_GUID structure.
    *
    * [bindgen] header: ./SDL_guid.h
    */
  def SDL_GUIDFromString(pchGUID: CString)(__return: Ptr[SDL_GUID]): Unit =
    __sn_wrap_sdl2_SDL_GUIDFromString(pchGUID, __return)

  /** Get an ASCII string representation for a given ::SDL_GUID.
    *
    * [bindgen] header: ./SDL_guid.h
    */
  def SDL_GUIDToString(guid: Ptr[SDL_GUID], pszGUID: CString, cbGUID: CInt): Unit =
    __sn_wrap_sdl2_SDL_GUIDToString(guid, pszGUID, cbGUID)

  /** Get an ASCII string representation for a given ::SDL_GUID.
    *
    * [bindgen] header: ./SDL_guid.h
    */
  def SDL_GUIDToString(guid: SDL_GUID, pszGUID: CString, cbGUID: CInt)(using Zone): Unit =
    val __ptr_0: Ptr[SDL_GUID] = alloc[SDL_GUID](1)
    !(__ptr_0 + 0) = guid
    __sn_wrap_sdl2_SDL_GUIDToString((__ptr_0 + 0), pszGUID, cbGUID)

  /** Get the SDL joystick layer binding for a controller axis mapping.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetBindForAxis(gamecontroller: Ptr[SDL_GameController], axis: SDL_GameControllerAxis)(
      __return: Ptr[SDL_GameControllerButtonBind]
  ): Unit =
    __sn_wrap_sdl2_SDL_GameControllerGetBindForAxis(gamecontroller, axis, __return)

  /** Get the SDL joystick layer binding for a controller axis mapping.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetBindForAxis(gamecontroller: Ptr[SDL_GameController], axis: SDL_GameControllerAxis)(using
      Zone
  ): SDL_GameControllerButtonBind =
    val __ptr_0: Ptr[SDL_GameControllerButtonBind] = alloc[SDL_GameControllerButtonBind](1)
    __sn_wrap_sdl2_SDL_GameControllerGetBindForAxis(gamecontroller, axis, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Get the SDL joystick layer binding for a controller button mapping.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetBindForButton(gamecontroller: Ptr[SDL_GameController], button: SDL_GameControllerButton)(
      using Zone
  ): SDL_GameControllerButtonBind =
    val __ptr_0: Ptr[SDL_GameControllerButtonBind] = alloc[SDL_GameControllerButtonBind](1)
    __sn_wrap_sdl2_SDL_GameControllerGetBindForButton(gamecontroller, button, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Get the SDL joystick layer binding for a controller button mapping.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerGetBindForButton(gamecontroller: Ptr[SDL_GameController], button: SDL_GameControllerButton)(
      __return: Ptr[SDL_GameControllerButtonBind]
  ): Unit =
    __sn_wrap_sdl2_SDL_GameControllerGetBindForButton(gamecontroller, button, __return)

  /** Get the game controller mapping string for a given GUID.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerMappingForGUID(guid: Ptr[SDL_JoystickGUID]): CString =
    __sn_wrap_sdl2_SDL_GameControllerMappingForGUID(guid)

  /** Get the game controller mapping string for a given GUID.
    *
    * [bindgen] header: ./SDL_gamecontroller.h
    */
  def SDL_GameControllerMappingForGUID(guid: SDL_JoystickGUID)(using Zone): CString =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    !(__ptr_0 + 0) = guid
    __sn_wrap_sdl2_SDL_GameControllerMappingForGUID((__ptr_0 + 0))

  /** Get the device information encoded in a SDL_JoystickGUID structure
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_GetJoystickGUIDInfo(
      guid: Ptr[SDL_JoystickGUID],
      vendor: Ptr[Uint16],
      product: Ptr[Uint16],
      version: Ptr[Uint16],
      crc16: Ptr[Uint16]
  ): Unit =
    __sn_wrap_sdl2_SDL_GetJoystickGUIDInfo(guid, vendor, product, version, crc16)

  /** Get the device information encoded in a SDL_JoystickGUID structure
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_GetJoystickGUIDInfo(
      guid: SDL_JoystickGUID,
      vendor: Ptr[Uint16],
      product: Ptr[Uint16],
      version: Ptr[Uint16],
      crc16: Ptr[Uint16]
  )(using Zone): Unit =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    !(__ptr_0 + 0) = guid
    __sn_wrap_sdl2_SDL_GetJoystickGUIDInfo((__ptr_0 + 0), vendor, product, version, crc16)

  /** Get the implementation-dependent GUID for the joystick at a given device index.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceGUID(device_index: CInt)(using Zone): SDL_JoystickGUID =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    __sn_wrap_sdl2_SDL_JoystickGetDeviceGUID(device_index, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Get the implementation-dependent GUID for the joystick at a given device index.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetDeviceGUID(device_index: CInt)(__return: Ptr[SDL_JoystickGUID]): Unit =
    __sn_wrap_sdl2_SDL_JoystickGetDeviceGUID(device_index, __return)

  /** Get the implementation-dependent GUID for the joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUID(joystick: Ptr[SDL_Joystick])(using Zone): SDL_JoystickGUID =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    __sn_wrap_sdl2_SDL_JoystickGetGUID(joystick, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Get the implementation-dependent GUID for the joystick.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUID(joystick: Ptr[SDL_Joystick])(__return: Ptr[SDL_JoystickGUID]): Unit =
    __sn_wrap_sdl2_SDL_JoystickGetGUID(joystick, __return)

  /** Convert a GUID string into a SDL_JoystickGUID structure.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUIDFromString(pchGUID: CString)(using Zone): SDL_JoystickGUID =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    __sn_wrap_sdl2_SDL_JoystickGetGUIDFromString(pchGUID, (__ptr_0 + 0))
    !(__ptr_0 + 0)

  /** Convert a GUID string into a SDL_JoystickGUID structure.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUIDFromString(pchGUID: CString)(__return: Ptr[SDL_JoystickGUID]): Unit =
    __sn_wrap_sdl2_SDL_JoystickGetGUIDFromString(pchGUID, __return)

  /** Get an ASCII string representation for a given SDL_JoystickGUID.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUIDString(guid: Ptr[SDL_JoystickGUID], pszGUID: CString, cbGUID: CInt): Unit =
    __sn_wrap_sdl2_SDL_JoystickGetGUIDString(guid, pszGUID, cbGUID)

  /** Get an ASCII string representation for a given SDL_JoystickGUID.
    *
    * [bindgen] header: ./SDL_joystick.h
    */
  def SDL_JoystickGetGUIDString(guid: SDL_JoystickGUID, pszGUID: CString, cbGUID: CInt)(using Zone): Unit =
    val __ptr_0: Ptr[SDL_JoystickGUID] = alloc[SDL_JoystickGUID](1)
    !(__ptr_0 + 0) = guid
    __sn_wrap_sdl2_SDL_JoystickGetGUIDString((__ptr_0 + 0), pszGUID, cbGUID)

object types:
  export _root_.sdl2.structs.*
  export _root_.sdl2.aliases.*
  export _root_.sdl2.unions.*
  export _root_.sdl2.enumerations.*

object all:
  export _root_.sdl2.enumerations.SDL_ArrayOrder
  export _root_.sdl2.enumerations.SDL_AssertState
  export _root_.sdl2.enumerations.SDL_AudioFormat
  export _root_.sdl2.enumerations.SDL_AudioStatus
  export _root_.sdl2.enumerations.SDL_BitmapOrder
  export _root_.sdl2.enumerations.SDL_BlendFactor
  export _root_.sdl2.enumerations.SDL_BlendMode
  export _root_.sdl2.enumerations.SDL_BlendOperation
  export _root_.sdl2.enumerations.SDL_DUMMY_ENUM
  export _root_.sdl2.enumerations.SDL_DisplayEventID
  export _root_.sdl2.enumerations.SDL_DisplayOrientation
  export _root_.sdl2.enumerations.SDL_EventType
  export _root_.sdl2.enumerations.SDL_FlashOperation
  export _root_.sdl2.enumerations.SDL_GLContextResetNotification
  export _root_.sdl2.enumerations.SDL_GLattr
  export _root_.sdl2.enumerations.SDL_GLcontextFlag
  export _root_.sdl2.enumerations.SDL_GLcontextReleaseFlag
  export _root_.sdl2.enumerations.SDL_GLprofile
  export _root_.sdl2.enumerations.SDL_GameControllerAxis
  export _root_.sdl2.enumerations.SDL_GameControllerBindType
  export _root_.sdl2.enumerations.SDL_GameControllerButton
  export _root_.sdl2.enumerations.SDL_GameControllerType
  export _root_.sdl2.enumerations.SDL_HintPriority
  export _root_.sdl2.enumerations.SDL_HitTestResult
  export _root_.sdl2.enumerations.SDL_InitFlag
  export _root_.sdl2.enumerations.SDL_JoystickPowerLevel
  export _root_.sdl2.enumerations.SDL_JoystickType
  export _root_.sdl2.enumerations.SDL_KeyCode
  export _root_.sdl2.enumerations.SDL_Keymod
  export _root_.sdl2.enumerations.SDL_LogCategory
  export _root_.sdl2.enumerations.SDL_LogPriority
  export _root_.sdl2.enumerations.SDL_MessageBoxButtonFlags
  export _root_.sdl2.enumerations.SDL_MessageBoxColorType
  export _root_.sdl2.enumerations.SDL_MessageBoxFlags
  export _root_.sdl2.enumerations.SDL_MouseWheelDirection
  export _root_.sdl2.enumerations.SDL_PackedLayout
  export _root_.sdl2.enumerations.SDL_PackedOrder
  export _root_.sdl2.enumerations.SDL_PixelFormatEnum
  export _root_.sdl2.enumerations.SDL_PixelType
  export _root_.sdl2.enumerations.SDL_PowerState
  export _root_.sdl2.enumerations.SDL_RendererFlags
  export _root_.sdl2.enumerations.SDL_RendererFlip
  export _root_.sdl2.enumerations.SDL_ScaleMode
  export _root_.sdl2.enumerations.SDL_Scancode
  export _root_.sdl2.enumerations.SDL_SensorType
  export _root_.sdl2.enumerations.SDL_SystemCursor
  export _root_.sdl2.enumerations.SDL_TextureAccess
  export _root_.sdl2.enumerations.SDL_TextureModulate
  export _root_.sdl2.enumerations.SDL_ThreadPriority
  export _root_.sdl2.enumerations.SDL_TouchDeviceType
  export _root_.sdl2.enumerations.SDL_WindowEventID
  export _root_.sdl2.enumerations.SDL_WindowFlags
  export _root_.sdl2.enumerations.SDL_YUV_CONVERSION_MODE
  export _root_.sdl2.enumerations.SDL_bool
  export _root_.sdl2.enumerations.SDL_errorcode
  export _root_.sdl2.enumerations.SDL_eventaction
  export _root_.sdl2.enumerations.WindowShapeMode
  export _root_.sdl2.aliases.SDL_AssertionHandler
  export _root_.sdl2.aliases.SDL_AudioCallback
  export _root_.sdl2.aliases.SDL_AudioDeviceID
  export _root_.sdl2.aliases.SDL_AudioFilter
  export _root_.sdl2.aliases.SDL_EventFilter
  export _root_.sdl2.aliases.SDL_FingerID
  export _root_.sdl2.aliases.SDL_GLContext
  export _root_.sdl2.aliases.SDL_GestureID
  export _root_.sdl2.aliases.SDL_HintCallback
  export _root_.sdl2.aliases.SDL_HitTest
  export _root_.sdl2.aliases.SDL_JoystickGUID
  export _root_.sdl2.aliases.SDL_JoystickID
  export _root_.sdl2.aliases.SDL_Keycode
  export _root_.sdl2.aliases.SDL_LogOutputFunction
  export _root_.sdl2.aliases.SDL_MetalView
  export _root_.sdl2.aliases.SDL_SensorID
  export _root_.sdl2.aliases.SDL_SpinLock
  export _root_.sdl2.aliases.SDL_TLSID
  export _root_.sdl2.aliases.SDL_ThreadFunction
  export _root_.sdl2.aliases.SDL_TimerCallback
  export _root_.sdl2.aliases.SDL_TimerID
  export _root_.sdl2.aliases.SDL_TouchID
  export _root_.sdl2.aliases.SDL_WindowsMessageHook
  export _root_.sdl2.aliases.SDL_blit
  export _root_.sdl2.aliases.SDL_calloc_func
  export _root_.sdl2.aliases.SDL_free_func
  export _root_.sdl2.aliases.SDL_iconv_t
  export _root_.sdl2.aliases.SDL_main_func
  export _root_.sdl2.aliases.SDL_malloc_func
  export _root_.sdl2.aliases.SDL_realloc_func
  export _root_.sdl2.aliases.SDL_threadID
  export _root_.sdl2.aliases.Sint16
  export _root_.sdl2.aliases.Sint32
  export _root_.sdl2.aliases.Sint64
  export _root_.sdl2.aliases.Sint8
  export _root_.sdl2.aliases.Uint16
  export _root_.sdl2.aliases.Uint32
  export _root_.sdl2.aliases.Uint64
  export _root_.sdl2.aliases.Uint8
  export _root_.sdl2.aliases.int16_t
  export _root_.sdl2.aliases.int32_t
  export _root_.sdl2.aliases.int64_t
  export _root_.sdl2.aliases.int8_t
  export _root_.sdl2.aliases.pfnSDL_CurrentBeginThread
  export _root_.sdl2.aliases.pfnSDL_CurrentEndThread
  export _root_.sdl2.aliases.size_t
  export _root_.sdl2.aliases.uint16_t
  export _root_.sdl2.aliases.uint32_t
  export _root_.sdl2.aliases.uint64_t
  export _root_.sdl2.aliases.uint8_t
  export _root_.sdl2.aliases.uintptr_t
  export _root_.sdl2.aliases.va_list
  export _root_.sdl2.aliases.wchar_t
  export _root_.sdl2.structs.ID3D11Device
  export _root_.sdl2.structs.ID3D12Device
  export _root_.sdl2.structs.IDirect3DDevice9
  export _root_.sdl2.structs.SDL_AssertData
  export _root_.sdl2.structs.SDL_AudioCVT
  export _root_.sdl2.structs.SDL_AudioDeviceEvent
  export _root_.sdl2.structs.SDL_AudioSpec
  export _root_.sdl2.structs.SDL_AudioStream
  export _root_.sdl2.structs.SDL_BlitMap
  export _root_.sdl2.structs.SDL_Color
  export _root_.sdl2.structs.SDL_CommonEvent
  export _root_.sdl2.structs.SDL_ControllerAxisEvent
  export _root_.sdl2.structs.SDL_ControllerButtonEvent
  export _root_.sdl2.structs.SDL_ControllerDeviceEvent
  export _root_.sdl2.structs.SDL_ControllerSensorEvent
  export _root_.sdl2.structs.SDL_ControllerTouchpadEvent
  export _root_.sdl2.structs.SDL_Cursor
  export _root_.sdl2.structs.SDL_DisplayEvent
  export _root_.sdl2.structs.SDL_DisplayMode
  export _root_.sdl2.structs.SDL_DollarGestureEvent
  export _root_.sdl2.structs.SDL_DropEvent
  export _root_.sdl2.structs.SDL_FPoint
  export _root_.sdl2.structs.SDL_FRect
  export _root_.sdl2.structs.SDL_Finger
  export _root_.sdl2.structs.SDL_GUID
  export _root_.sdl2.structs.SDL_GameController
  export _root_.sdl2.structs.SDL_GameControllerButtonBind
  export _root_.sdl2.structs.SDL_Haptic
  export _root_.sdl2.structs.SDL_HapticCondition
  export _root_.sdl2.structs.SDL_HapticConstant
  export _root_.sdl2.structs.SDL_HapticCustom
  export _root_.sdl2.structs.SDL_HapticDirection
  export _root_.sdl2.structs.SDL_HapticLeftRight
  export _root_.sdl2.structs.SDL_HapticPeriodic
  export _root_.sdl2.structs.SDL_HapticRamp
  export _root_.sdl2.structs.SDL_JoyAxisEvent
  export _root_.sdl2.structs.SDL_JoyBallEvent
  export _root_.sdl2.structs.SDL_JoyBatteryEvent
  export _root_.sdl2.structs.SDL_JoyButtonEvent
  export _root_.sdl2.structs.SDL_JoyDeviceEvent
  export _root_.sdl2.structs.SDL_JoyHatEvent
  export _root_.sdl2.structs.SDL_Joystick
  export _root_.sdl2.structs.SDL_KeyboardEvent
  export _root_.sdl2.structs.SDL_Keysym
  export _root_.sdl2.structs.SDL_Locale
  export _root_.sdl2.structs.SDL_MessageBoxButtonData
  export _root_.sdl2.structs.SDL_MessageBoxColor
  export _root_.sdl2.structs.SDL_MessageBoxColorScheme
  export _root_.sdl2.structs.SDL_MessageBoxData
  export _root_.sdl2.structs.SDL_MouseButtonEvent
  export _root_.sdl2.structs.SDL_MouseMotionEvent
  export _root_.sdl2.structs.SDL_MouseWheelEvent
  export _root_.sdl2.structs.SDL_MultiGestureEvent
  export _root_.sdl2.structs.SDL_Palette
  export _root_.sdl2.structs.SDL_PixelFormat
  export _root_.sdl2.structs.SDL_Point
  export _root_.sdl2.structs.SDL_QuitEvent
  export _root_.sdl2.structs.SDL_RWops
  export _root_.sdl2.structs.SDL_Rect
  export _root_.sdl2.structs.SDL_Renderer
  export _root_.sdl2.structs.SDL_RendererInfo
  export _root_.sdl2.structs.SDL_Sensor
  export _root_.sdl2.structs.SDL_SensorEvent
  export _root_.sdl2.structs.SDL_Surface
  export _root_.sdl2.structs.SDL_SysWMEvent
  export _root_.sdl2.structs.SDL_SysWMmsg
  export _root_.sdl2.structs.SDL_TextEditingEvent
  export _root_.sdl2.structs.SDL_TextEditingExtEvent
  export _root_.sdl2.structs.SDL_TextInputEvent
  export _root_.sdl2.structs.SDL_Texture
  export _root_.sdl2.structs.SDL_Thread
  export _root_.sdl2.structs.SDL_TouchFingerEvent
  export _root_.sdl2.structs.SDL_UserEvent
  export _root_.sdl2.structs.SDL_Vertex
  export _root_.sdl2.structs.SDL_VirtualJoystickDesc
  export _root_.sdl2.structs.SDL_Window
  export _root_.sdl2.structs.SDL_WindowEvent
  export _root_.sdl2.structs.SDL_WindowShapeMode
  export _root_.sdl2.structs.SDL_atomic_t
  export _root_.sdl2.structs.SDL_cond
  export _root_.sdl2.structs.SDL_hid_device
  export _root_.sdl2.structs.SDL_hid_device_
  export _root_.sdl2.structs.SDL_hid_device_info
  export _root_.sdl2.structs.SDL_mutex
  export _root_.sdl2.structs.SDL_sem
  export _root_.sdl2.structs.SDL_semaphore
  export _root_.sdl2.structs.SDL_version
  export _root_.sdl2.structs._SDL_AudioStream
  export _root_.sdl2.structs._SDL_GameController
  export _root_.sdl2.structs._SDL_Haptic
  export _root_.sdl2.structs._SDL_Joystick
  export _root_.sdl2.structs._SDL_Sensor
  export _root_.sdl2.structs._SDL_iconv_t
  export _root_.sdl2.unions.SDL_Event
  export _root_.sdl2.unions.SDL_HapticEffect
  export _root_.sdl2.unions.SDL_WindowShapeParams
  export _root_.sdl2.functions.SDL_AddEventWatch
  export _root_.sdl2.functions.SDL_AddHintCallback
  export _root_.sdl2.functions.SDL_AddTimer
  export _root_.sdl2.functions.SDL_AllocFormat
  export _root_.sdl2.functions.SDL_AllocPalette
  export _root_.sdl2.functions.SDL_AllocRW
  export _root_.sdl2.functions.SDL_AtomicAdd
  export _root_.sdl2.functions.SDL_AtomicCAS
  export _root_.sdl2.functions.SDL_AtomicCASPtr
  export _root_.sdl2.functions.SDL_AtomicGet
  export _root_.sdl2.functions.SDL_AtomicGetPtr
  export _root_.sdl2.functions.SDL_AtomicLock
  export _root_.sdl2.functions.SDL_AtomicSet
  export _root_.sdl2.functions.SDL_AtomicSetPtr
  export _root_.sdl2.functions.SDL_AtomicTryLock
  export _root_.sdl2.functions.SDL_AtomicUnlock
  export _root_.sdl2.functions.SDL_AudioInit
  export _root_.sdl2.functions.SDL_AudioQuit
  export _root_.sdl2.functions.SDL_AudioStreamAvailable
  export _root_.sdl2.functions.SDL_AudioStreamClear
  export _root_.sdl2.functions.SDL_AudioStreamFlush
  export _root_.sdl2.functions.SDL_AudioStreamGet
  export _root_.sdl2.functions.SDL_AudioStreamPut
  export _root_.sdl2.functions.SDL_BuildAudioCVT
  export _root_.sdl2.functions.SDL_CalculateGammaRamp
  export _root_.sdl2.functions.SDL_CaptureMouse
  export _root_.sdl2.functions.SDL_ClearComposition
  export _root_.sdl2.functions.SDL_ClearError
  export _root_.sdl2.functions.SDL_ClearHints
  export _root_.sdl2.functions.SDL_ClearQueuedAudio
  export _root_.sdl2.functions.SDL_CloseAudio
  export _root_.sdl2.functions.SDL_CloseAudioDevice
  export _root_.sdl2.functions.SDL_ComposeCustomBlendMode
  export _root_.sdl2.functions.SDL_CondBroadcast
  export _root_.sdl2.functions.SDL_CondSignal
  export _root_.sdl2.functions.SDL_CondWait
  export _root_.sdl2.functions.SDL_CondWaitTimeout
  export _root_.sdl2.functions.SDL_ConvertAudio
  export _root_.sdl2.functions.SDL_ConvertPixels
  export _root_.sdl2.functions.SDL_ConvertSurface
  export _root_.sdl2.functions.SDL_ConvertSurfaceFormat
  export _root_.sdl2.functions.SDL_CreateColorCursor
  export _root_.sdl2.functions.SDL_CreateCond
  export _root_.sdl2.functions.SDL_CreateCursor
  export _root_.sdl2.functions.SDL_CreateMutex
  export _root_.sdl2.functions.SDL_CreateRGBSurface
  export _root_.sdl2.functions.SDL_CreateRGBSurfaceFrom
  export _root_.sdl2.functions.SDL_CreateRGBSurfaceWithFormat
  export _root_.sdl2.functions.SDL_CreateRGBSurfaceWithFormatFrom
  export _root_.sdl2.functions.SDL_CreateRenderer
  export _root_.sdl2.functions.SDL_CreateSemaphore
  export _root_.sdl2.functions.SDL_CreateShapedWindow
  export _root_.sdl2.functions.SDL_CreateSoftwareRenderer
  export _root_.sdl2.functions.SDL_CreateSystemCursor
  export _root_.sdl2.functions.SDL_CreateTexture
  export _root_.sdl2.functions.SDL_CreateTextureFromSurface
  export _root_.sdl2.functions.SDL_CreateThread
  export _root_.sdl2.functions.SDL_CreateThreadWithStackSize
  export _root_.sdl2.functions.SDL_CreateWindow
  export _root_.sdl2.functions.SDL_CreateWindowAndRenderer
  export _root_.sdl2.functions.SDL_CreateWindowFrom
  export _root_.sdl2.functions.SDL_DXGIGetOutputInfo
  export _root_.sdl2.functions.SDL_DelEventWatch
  export _root_.sdl2.functions.SDL_DelHintCallback
  export _root_.sdl2.functions.SDL_Delay
  export _root_.sdl2.functions.SDL_DequeueAudio
  export _root_.sdl2.functions.SDL_DestroyCond
  export _root_.sdl2.functions.SDL_DestroyMutex
  export _root_.sdl2.functions.SDL_DestroyRenderer
  export _root_.sdl2.functions.SDL_DestroySemaphore
  export _root_.sdl2.functions.SDL_DestroyTexture
  export _root_.sdl2.functions.SDL_DestroyWindow
  export _root_.sdl2.functions.SDL_DestroyWindowSurface
  export _root_.sdl2.functions.SDL_DetachThread
  export _root_.sdl2.functions.SDL_Direct3D9GetAdapterIndex
  export _root_.sdl2.functions.SDL_DisableScreenSaver
  export _root_.sdl2.functions.SDL_DuplicateSurface
  export _root_.sdl2.functions.SDL_EnableScreenSaver
  export _root_.sdl2.functions.SDL_EncloseFPoints
  export _root_.sdl2.functions.SDL_EnclosePoints
  export _root_.sdl2.functions.SDL_Error
  export _root_.sdl2.functions.SDL_EventState
  export _root_.sdl2.functions.SDL_FRectEmpty
  export _root_.sdl2.functions.SDL_FRectEquals
  export _root_.sdl2.functions.SDL_FRectEqualsEpsilon
  export _root_.sdl2.functions.SDL_FillRect
  export _root_.sdl2.functions.SDL_FillRects
  export _root_.sdl2.functions.SDL_FilterEvents
  export _root_.sdl2.functions.SDL_FlashWindow
  export _root_.sdl2.functions.SDL_FlushEvent
  export _root_.sdl2.functions.SDL_FlushEvents
  export _root_.sdl2.functions.SDL_FreeAudioStream
  export _root_.sdl2.functions.SDL_FreeCursor
  export _root_.sdl2.functions.SDL_FreeFormat
  export _root_.sdl2.functions.SDL_FreePalette
  export _root_.sdl2.functions.SDL_FreeRW
  export _root_.sdl2.functions.SDL_FreeSurface
  export _root_.sdl2.functions.SDL_FreeWAV
  export _root_.sdl2.functions.SDL_GL_BindTexture
  export _root_.sdl2.functions.SDL_GL_CreateContext
  export _root_.sdl2.functions.SDL_GL_DeleteContext
  export _root_.sdl2.functions.SDL_GL_ExtensionSupported
  export _root_.sdl2.functions.SDL_GL_GetAttribute
  export _root_.sdl2.functions.SDL_GL_GetCurrentContext
  export _root_.sdl2.functions.SDL_GL_GetCurrentWindow
  export _root_.sdl2.functions.SDL_GL_GetDrawableSize
  export _root_.sdl2.functions.SDL_GL_GetProcAddress
  export _root_.sdl2.functions.SDL_GL_GetSwapInterval
  export _root_.sdl2.functions.SDL_GL_LoadLibrary
  export _root_.sdl2.functions.SDL_GL_MakeCurrent
  export _root_.sdl2.functions.SDL_GL_ResetAttributes
  export _root_.sdl2.functions.SDL_GL_SetAttribute
  export _root_.sdl2.functions.SDL_GL_SetSwapInterval
  export _root_.sdl2.functions.SDL_GL_SwapWindow
  export _root_.sdl2.functions.SDL_GL_UnbindTexture
  export _root_.sdl2.functions.SDL_GL_UnloadLibrary
  export _root_.sdl2.functions.SDL_GameControllerAddMapping
  export _root_.sdl2.functions.SDL_GameControllerAddMappingsFromRW
  export _root_.sdl2.functions.SDL_GameControllerClose
  export _root_.sdl2.functions.SDL_GameControllerEventState
  export _root_.sdl2.functions.SDL_GameControllerFromInstanceID
  export _root_.sdl2.functions.SDL_GameControllerFromPlayerIndex
  export _root_.sdl2.functions.SDL_GameControllerGetAppleSFSymbolsNameForAxis
  export _root_.sdl2.functions.SDL_GameControllerGetAppleSFSymbolsNameForButton
  export _root_.sdl2.functions.SDL_GameControllerGetAttached
  export _root_.sdl2.functions.SDL_GameControllerGetAxis
  export _root_.sdl2.functions.SDL_GameControllerGetAxisFromString
  export _root_.sdl2.functions.SDL_GameControllerGetButton
  export _root_.sdl2.functions.SDL_GameControllerGetButtonFromString
  export _root_.sdl2.functions.SDL_GameControllerGetFirmwareVersion
  export _root_.sdl2.functions.SDL_GameControllerGetJoystick
  export _root_.sdl2.functions.SDL_GameControllerGetNumTouchpadFingers
  export _root_.sdl2.functions.SDL_GameControllerGetNumTouchpads
  export _root_.sdl2.functions.SDL_GameControllerGetPlayerIndex
  export _root_.sdl2.functions.SDL_GameControllerGetProduct
  export _root_.sdl2.functions.SDL_GameControllerGetProductVersion
  export _root_.sdl2.functions.SDL_GameControllerGetSensorData
  export _root_.sdl2.functions.SDL_GameControllerGetSensorDataRate
  export _root_.sdl2.functions.SDL_GameControllerGetSensorDataWithTimestamp
  export _root_.sdl2.functions.SDL_GameControllerGetSerial
  export _root_.sdl2.functions.SDL_GameControllerGetSteamHandle
  export _root_.sdl2.functions.SDL_GameControllerGetStringForAxis
  export _root_.sdl2.functions.SDL_GameControllerGetStringForButton
  export _root_.sdl2.functions.SDL_GameControllerGetTouchpadFinger
  export _root_.sdl2.functions.SDL_GameControllerGetType
  export _root_.sdl2.functions.SDL_GameControllerGetVendor
  export _root_.sdl2.functions.SDL_GameControllerHasAxis
  export _root_.sdl2.functions.SDL_GameControllerHasButton
  export _root_.sdl2.functions.SDL_GameControllerHasLED
  export _root_.sdl2.functions.SDL_GameControllerHasRumble
  export _root_.sdl2.functions.SDL_GameControllerHasRumbleTriggers
  export _root_.sdl2.functions.SDL_GameControllerHasSensor
  export _root_.sdl2.functions.SDL_GameControllerIsSensorEnabled
  export _root_.sdl2.functions.SDL_GameControllerMapping
  export _root_.sdl2.functions.SDL_GameControllerMappingForDeviceIndex
  export _root_.sdl2.functions.SDL_GameControllerMappingForIndex
  export _root_.sdl2.functions.SDL_GameControllerName
  export _root_.sdl2.functions.SDL_GameControllerNameForIndex
  export _root_.sdl2.functions.SDL_GameControllerNumMappings
  export _root_.sdl2.functions.SDL_GameControllerOpen
  export _root_.sdl2.functions.SDL_GameControllerPath
  export _root_.sdl2.functions.SDL_GameControllerPathForIndex
  export _root_.sdl2.functions.SDL_GameControllerRumble
  export _root_.sdl2.functions.SDL_GameControllerRumbleTriggers
  export _root_.sdl2.functions.SDL_GameControllerSendEffect
  export _root_.sdl2.functions.SDL_GameControllerSetLED
  export _root_.sdl2.functions.SDL_GameControllerSetPlayerIndex
  export _root_.sdl2.functions.SDL_GameControllerSetSensorEnabled
  export _root_.sdl2.functions.SDL_GameControllerTypeForIndex
  export _root_.sdl2.functions.SDL_GameControllerUpdate
  export _root_.sdl2.functions.SDL_GetAssertionHandler
  export _root_.sdl2.functions.SDL_GetAssertionReport
  export _root_.sdl2.functions.SDL_GetAudioDeviceName
  export _root_.sdl2.functions.SDL_GetAudioDeviceSpec
  export _root_.sdl2.functions.SDL_GetAudioDeviceStatus
  export _root_.sdl2.functions.SDL_GetAudioDriver
  export _root_.sdl2.functions.SDL_GetAudioStatus
  export _root_.sdl2.functions.SDL_GetBasePath
  export _root_.sdl2.functions.SDL_GetCPUCacheLineSize
  export _root_.sdl2.functions.SDL_GetCPUCount
  export _root_.sdl2.functions.SDL_GetClipRect
  export _root_.sdl2.functions.SDL_GetClipboardText
  export _root_.sdl2.functions.SDL_GetClosestDisplayMode
  export _root_.sdl2.functions.SDL_GetColorKey
  export _root_.sdl2.functions.SDL_GetCurrentAudioDriver
  export _root_.sdl2.functions.SDL_GetCurrentDisplayMode
  export _root_.sdl2.functions.SDL_GetCurrentVideoDriver
  export _root_.sdl2.functions.SDL_GetCursor
  export _root_.sdl2.functions.SDL_GetDefaultAssertionHandler
  export _root_.sdl2.functions.SDL_GetDefaultAudioInfo
  export _root_.sdl2.functions.SDL_GetDefaultCursor
  export _root_.sdl2.functions.SDL_GetDesktopDisplayMode
  export _root_.sdl2.functions.SDL_GetDisplayBounds
  export _root_.sdl2.functions.SDL_GetDisplayDPI
  export _root_.sdl2.functions.SDL_GetDisplayMode
  export _root_.sdl2.functions.SDL_GetDisplayName
  export _root_.sdl2.functions.SDL_GetDisplayOrientation
  export _root_.sdl2.functions.SDL_GetDisplayUsableBounds
  export _root_.sdl2.functions.SDL_GetError
  export _root_.sdl2.functions.SDL_GetErrorMsg
  export _root_.sdl2.functions.SDL_GetEventFilter
  export _root_.sdl2.functions.SDL_GetGlobalMouseState
  export _root_.sdl2.functions.SDL_GetGrabbedWindow
  export _root_.sdl2.functions.SDL_GetHint
  export _root_.sdl2.functions.SDL_GetHintBoolean
  export _root_.sdl2.functions.SDL_GetKeyFromName
  export _root_.sdl2.functions.SDL_GetKeyFromScancode
  export _root_.sdl2.functions.SDL_GetKeyName
  export _root_.sdl2.functions.SDL_GetKeyboardFocus
  export _root_.sdl2.functions.SDL_GetKeyboardState
  export _root_.sdl2.functions.SDL_GetMemoryFunctions
  export _root_.sdl2.functions.SDL_GetModState
  export _root_.sdl2.functions.SDL_GetMouseFocus
  export _root_.sdl2.functions.SDL_GetMouseState
  export _root_.sdl2.functions.SDL_GetNumAllocations
  export _root_.sdl2.functions.SDL_GetNumAudioDevices
  export _root_.sdl2.functions.SDL_GetNumAudioDrivers
  export _root_.sdl2.functions.SDL_GetNumDisplayModes
  export _root_.sdl2.functions.SDL_GetNumRenderDrivers
  export _root_.sdl2.functions.SDL_GetNumTouchDevices
  export _root_.sdl2.functions.SDL_GetNumTouchFingers
  export _root_.sdl2.functions.SDL_GetNumVideoDisplays
  export _root_.sdl2.functions.SDL_GetNumVideoDrivers
  export _root_.sdl2.functions.SDL_GetOriginalMemoryFunctions
  export _root_.sdl2.functions.SDL_GetPerformanceCounter
  export _root_.sdl2.functions.SDL_GetPerformanceFrequency
  export _root_.sdl2.functions.SDL_GetPixelFormatName
  export _root_.sdl2.functions.SDL_GetPlatform
  export _root_.sdl2.functions.SDL_GetPointDisplayIndex
  export _root_.sdl2.functions.SDL_GetPowerInfo
  export _root_.sdl2.functions.SDL_GetPrefPath
  export _root_.sdl2.functions.SDL_GetPreferredLocales
  export _root_.sdl2.functions.SDL_GetPrimarySelectionText
  export _root_.sdl2.functions.SDL_GetQueuedAudioSize
  export _root_.sdl2.functions.SDL_GetRGB
  export _root_.sdl2.functions.SDL_GetRGBA
  export _root_.sdl2.functions.SDL_GetRectDisplayIndex
  export _root_.sdl2.functions.SDL_GetRelativeMouseMode
  export _root_.sdl2.functions.SDL_GetRelativeMouseState
  export _root_.sdl2.functions.SDL_GetRenderDrawBlendMode
  export _root_.sdl2.functions.SDL_GetRenderDrawColor
  export _root_.sdl2.functions.SDL_GetRenderDriverInfo
  export _root_.sdl2.functions.SDL_GetRenderTarget
  export _root_.sdl2.functions.SDL_GetRenderer
  export _root_.sdl2.functions.SDL_GetRendererInfo
  export _root_.sdl2.functions.SDL_GetRendererOutputSize
  export _root_.sdl2.functions.SDL_GetRevision
  export _root_.sdl2.functions.SDL_GetRevisionNumber
  export _root_.sdl2.functions.SDL_GetScancodeFromKey
  export _root_.sdl2.functions.SDL_GetScancodeFromName
  export _root_.sdl2.functions.SDL_GetScancodeName
  export _root_.sdl2.functions.SDL_GetShapedWindowMode
  export _root_.sdl2.functions.SDL_GetSurfaceAlphaMod
  export _root_.sdl2.functions.SDL_GetSurfaceBlendMode
  export _root_.sdl2.functions.SDL_GetSurfaceColorMod
  export _root_.sdl2.functions.SDL_GetSystemRAM
  export _root_.sdl2.functions.SDL_GetTextureAlphaMod
  export _root_.sdl2.functions.SDL_GetTextureBlendMode
  export _root_.sdl2.functions.SDL_GetTextureColorMod
  export _root_.sdl2.functions.SDL_GetTextureScaleMode
  export _root_.sdl2.functions.SDL_GetTextureUserData
  export _root_.sdl2.functions.SDL_GetThreadID
  export _root_.sdl2.functions.SDL_GetThreadName
  export _root_.sdl2.functions.SDL_GetTicks
  export _root_.sdl2.functions.SDL_GetTicks64
  export _root_.sdl2.functions.SDL_GetTouchDevice
  export _root_.sdl2.functions.SDL_GetTouchDeviceType
  export _root_.sdl2.functions.SDL_GetTouchFinger
  export _root_.sdl2.functions.SDL_GetTouchName
  export _root_.sdl2.functions.SDL_GetVersion
  export _root_.sdl2.functions.SDL_GetVideoDriver
  export _root_.sdl2.functions.SDL_GetWindowBordersSize
  export _root_.sdl2.functions.SDL_GetWindowBrightness
  export _root_.sdl2.functions.SDL_GetWindowData
  export _root_.sdl2.functions.SDL_GetWindowDisplayIndex
  export _root_.sdl2.functions.SDL_GetWindowDisplayMode
  export _root_.sdl2.functions.SDL_GetWindowFlags
  export _root_.sdl2.functions.SDL_GetWindowFromID
  export _root_.sdl2.functions.SDL_GetWindowGammaRamp
  export _root_.sdl2.functions.SDL_GetWindowGrab
  export _root_.sdl2.functions.SDL_GetWindowICCProfile
  export _root_.sdl2.functions.SDL_GetWindowID
  export _root_.sdl2.functions.SDL_GetWindowKeyboardGrab
  export _root_.sdl2.functions.SDL_GetWindowMaximumSize
  export _root_.sdl2.functions.SDL_GetWindowMinimumSize
  export _root_.sdl2.functions.SDL_GetWindowMouseGrab
  export _root_.sdl2.functions.SDL_GetWindowMouseRect
  export _root_.sdl2.functions.SDL_GetWindowOpacity
  export _root_.sdl2.functions.SDL_GetWindowPixelFormat
  export _root_.sdl2.functions.SDL_GetWindowPosition
  export _root_.sdl2.functions.SDL_GetWindowSize
  export _root_.sdl2.functions.SDL_GetWindowSizeInPixels
  export _root_.sdl2.functions.SDL_GetWindowSurface
  export _root_.sdl2.functions.SDL_GetWindowTitle
  export _root_.sdl2.functions.SDL_GetYUVConversionMode
  export _root_.sdl2.functions.SDL_GetYUVConversionModeForResolution
  export _root_.sdl2.functions.SDL_HapticClose
  export _root_.sdl2.functions.SDL_HapticDestroyEffect
  export _root_.sdl2.functions.SDL_HapticEffectSupported
  export _root_.sdl2.functions.SDL_HapticGetEffectStatus
  export _root_.sdl2.functions.SDL_HapticIndex
  export _root_.sdl2.functions.SDL_HapticName
  export _root_.sdl2.functions.SDL_HapticNewEffect
  export _root_.sdl2.functions.SDL_HapticNumAxes
  export _root_.sdl2.functions.SDL_HapticNumEffects
  export _root_.sdl2.functions.SDL_HapticNumEffectsPlaying
  export _root_.sdl2.functions.SDL_HapticOpen
  export _root_.sdl2.functions.SDL_HapticOpenFromJoystick
  export _root_.sdl2.functions.SDL_HapticOpenFromMouse
  export _root_.sdl2.functions.SDL_HapticOpened
  export _root_.sdl2.functions.SDL_HapticPause
  export _root_.sdl2.functions.SDL_HapticQuery
  export _root_.sdl2.functions.SDL_HapticRumbleInit
  export _root_.sdl2.functions.SDL_HapticRumblePlay
  export _root_.sdl2.functions.SDL_HapticRumbleStop
  export _root_.sdl2.functions.SDL_HapticRumbleSupported
  export _root_.sdl2.functions.SDL_HapticRunEffect
  export _root_.sdl2.functions.SDL_HapticSetAutocenter
  export _root_.sdl2.functions.SDL_HapticSetGain
  export _root_.sdl2.functions.SDL_HapticStopAll
  export _root_.sdl2.functions.SDL_HapticStopEffect
  export _root_.sdl2.functions.SDL_HapticUnpause
  export _root_.sdl2.functions.SDL_HapticUpdateEffect
  export _root_.sdl2.functions.SDL_Has3DNow
  export _root_.sdl2.functions.SDL_HasARMSIMD
  export _root_.sdl2.functions.SDL_HasAVX
  export _root_.sdl2.functions.SDL_HasAVX2
  export _root_.sdl2.functions.SDL_HasAVX512F
  export _root_.sdl2.functions.SDL_HasAltiVec
  export _root_.sdl2.functions.SDL_HasClipboardText
  export _root_.sdl2.functions.SDL_HasColorKey
  export _root_.sdl2.functions.SDL_HasEvent
  export _root_.sdl2.functions.SDL_HasEvents
  export _root_.sdl2.functions.SDL_HasIntersection
  export _root_.sdl2.functions.SDL_HasIntersectionF
  export _root_.sdl2.functions.SDL_HasLASX
  export _root_.sdl2.functions.SDL_HasLSX
  export _root_.sdl2.functions.SDL_HasMMX
  export _root_.sdl2.functions.SDL_HasNEON
  export _root_.sdl2.functions.SDL_HasPrimarySelectionText
  export _root_.sdl2.functions.SDL_HasRDTSC
  export _root_.sdl2.functions.SDL_HasSSE
  export _root_.sdl2.functions.SDL_HasSSE2
  export _root_.sdl2.functions.SDL_HasSSE3
  export _root_.sdl2.functions.SDL_HasSSE41
  export _root_.sdl2.functions.SDL_HasSSE42
  export _root_.sdl2.functions.SDL_HasScreenKeyboardSupport
  export _root_.sdl2.functions.SDL_HasSurfaceRLE
  export _root_.sdl2.functions.SDL_HasWindowSurface
  export _root_.sdl2.functions.SDL_HideWindow
  export _root_.sdl2.functions.SDL_Init
  export _root_.sdl2.functions.SDL_InitSubSystem
  export _root_.sdl2.functions.SDL_IntersectFRect
  export _root_.sdl2.functions.SDL_IntersectFRectAndLine
  export _root_.sdl2.functions.SDL_IntersectRect
  export _root_.sdl2.functions.SDL_IntersectRectAndLine
  export _root_.sdl2.functions.SDL_IsGameController
  export _root_.sdl2.functions.SDL_IsScreenKeyboardShown
  export _root_.sdl2.functions.SDL_IsScreenSaverEnabled
  export _root_.sdl2.functions.SDL_IsShapedWindow
  export _root_.sdl2.functions.SDL_IsTablet
  export _root_.sdl2.functions.SDL_IsTextInputActive
  export _root_.sdl2.functions.SDL_IsTextInputShown
  export _root_.sdl2.functions.SDL_JoystickAttachVirtual
  export _root_.sdl2.functions.SDL_JoystickAttachVirtualEx
  export _root_.sdl2.functions.SDL_JoystickClose
  export _root_.sdl2.functions.SDL_JoystickCurrentPowerLevel
  export _root_.sdl2.functions.SDL_JoystickDetachVirtual
  export _root_.sdl2.functions.SDL_JoystickEventState
  export _root_.sdl2.functions.SDL_JoystickFromInstanceID
  export _root_.sdl2.functions.SDL_JoystickFromPlayerIndex
  export _root_.sdl2.functions.SDL_JoystickGetAttached
  export _root_.sdl2.functions.SDL_JoystickGetAxis
  export _root_.sdl2.functions.SDL_JoystickGetAxisInitialState
  export _root_.sdl2.functions.SDL_JoystickGetBall
  export _root_.sdl2.functions.SDL_JoystickGetButton
  export _root_.sdl2.functions.SDL_JoystickGetDeviceInstanceID
  export _root_.sdl2.functions.SDL_JoystickGetDevicePlayerIndex
  export _root_.sdl2.functions.SDL_JoystickGetDeviceProduct
  export _root_.sdl2.functions.SDL_JoystickGetDeviceProductVersion
  export _root_.sdl2.functions.SDL_JoystickGetDeviceType
  export _root_.sdl2.functions.SDL_JoystickGetDeviceVendor
  export _root_.sdl2.functions.SDL_JoystickGetFirmwareVersion
  export _root_.sdl2.functions.SDL_JoystickGetHat
  export _root_.sdl2.functions.SDL_JoystickGetPlayerIndex
  export _root_.sdl2.functions.SDL_JoystickGetProduct
  export _root_.sdl2.functions.SDL_JoystickGetProductVersion
  export _root_.sdl2.functions.SDL_JoystickGetSerial
  export _root_.sdl2.functions.SDL_JoystickGetType
  export _root_.sdl2.functions.SDL_JoystickGetVendor
  export _root_.sdl2.functions.SDL_JoystickHasLED
  export _root_.sdl2.functions.SDL_JoystickHasRumble
  export _root_.sdl2.functions.SDL_JoystickHasRumbleTriggers
  export _root_.sdl2.functions.SDL_JoystickInstanceID
  export _root_.sdl2.functions.SDL_JoystickIsHaptic
  export _root_.sdl2.functions.SDL_JoystickIsVirtual
  export _root_.sdl2.functions.SDL_JoystickName
  export _root_.sdl2.functions.SDL_JoystickNameForIndex
  export _root_.sdl2.functions.SDL_JoystickNumAxes
  export _root_.sdl2.functions.SDL_JoystickNumBalls
  export _root_.sdl2.functions.SDL_JoystickNumButtons
  export _root_.sdl2.functions.SDL_JoystickNumHats
  export _root_.sdl2.functions.SDL_JoystickOpen
  export _root_.sdl2.functions.SDL_JoystickPath
  export _root_.sdl2.functions.SDL_JoystickPathForIndex
  export _root_.sdl2.functions.SDL_JoystickRumble
  export _root_.sdl2.functions.SDL_JoystickRumbleTriggers
  export _root_.sdl2.functions.SDL_JoystickSendEffect
  export _root_.sdl2.functions.SDL_JoystickSetLED
  export _root_.sdl2.functions.SDL_JoystickSetPlayerIndex
  export _root_.sdl2.functions.SDL_JoystickSetVirtualAxis
  export _root_.sdl2.functions.SDL_JoystickSetVirtualButton
  export _root_.sdl2.functions.SDL_JoystickSetVirtualHat
  export _root_.sdl2.functions.SDL_JoystickUpdate
  export _root_.sdl2.functions.SDL_LoadBMP_RW
  export _root_.sdl2.functions.SDL_LoadDollarTemplates
  export _root_.sdl2.functions.SDL_LoadFile
  export _root_.sdl2.functions.SDL_LoadFile_RW
  export _root_.sdl2.functions.SDL_LoadFunction
  export _root_.sdl2.functions.SDL_LoadObject
  export _root_.sdl2.functions.SDL_LoadWAV_RW
  export _root_.sdl2.functions.SDL_LockAudio
  export _root_.sdl2.functions.SDL_LockAudioDevice
  export _root_.sdl2.functions.SDL_LockJoysticks
  export _root_.sdl2.functions.SDL_LockMutex
  export _root_.sdl2.functions.SDL_LockSensors
  export _root_.sdl2.functions.SDL_LockSurface
  export _root_.sdl2.functions.SDL_LockTexture
  export _root_.sdl2.functions.SDL_LockTextureToSurface
  export _root_.sdl2.functions.SDL_Log
  export _root_.sdl2.functions.SDL_LogCritical
  export _root_.sdl2.functions.SDL_LogDebug
  export _root_.sdl2.functions.SDL_LogError
  export _root_.sdl2.functions.SDL_LogGetOutputFunction
  export _root_.sdl2.functions.SDL_LogGetPriority
  export _root_.sdl2.functions.SDL_LogInfo
  export _root_.sdl2.functions.SDL_LogMessage
  export _root_.sdl2.functions.SDL_LogMessageV
  export _root_.sdl2.functions.SDL_LogResetPriorities
  export _root_.sdl2.functions.SDL_LogSetAllPriority
  export _root_.sdl2.functions.SDL_LogSetOutputFunction
  export _root_.sdl2.functions.SDL_LogSetPriority
  export _root_.sdl2.functions.SDL_LogVerbose
  export _root_.sdl2.functions.SDL_LogWarn
  export _root_.sdl2.functions.SDL_LowerBlit
  export _root_.sdl2.functions.SDL_LowerBlitScaled
  export _root_.sdl2.functions.SDL_MapRGB
  export _root_.sdl2.functions.SDL_MapRGBA
  export _root_.sdl2.functions.SDL_MasksToPixelFormatEnum
  export _root_.sdl2.functions.SDL_MaximizeWindow
  export _root_.sdl2.functions.SDL_MemoryBarrierAcquireFunction
  export _root_.sdl2.functions.SDL_MemoryBarrierReleaseFunction
  export _root_.sdl2.functions.SDL_Metal_CreateView
  export _root_.sdl2.functions.SDL_Metal_DestroyView
  export _root_.sdl2.functions.SDL_Metal_GetDrawableSize
  export _root_.sdl2.functions.SDL_Metal_GetLayer
  export _root_.sdl2.functions.SDL_MinimizeWindow
  export _root_.sdl2.functions.SDL_MixAudio
  export _root_.sdl2.functions.SDL_MixAudioFormat
  export _root_.sdl2.functions.SDL_MouseIsHaptic
  export _root_.sdl2.functions.SDL_NewAudioStream
  export _root_.sdl2.functions.SDL_NumHaptics
  export _root_.sdl2.functions.SDL_NumJoysticks
  export _root_.sdl2.functions.SDL_NumSensors
  export _root_.sdl2.functions.SDL_OnApplicationDidBecomeActive
  export _root_.sdl2.functions.SDL_OnApplicationDidEnterBackground
  export _root_.sdl2.functions.SDL_OnApplicationDidReceiveMemoryWarning
  export _root_.sdl2.functions.SDL_OnApplicationWillEnterForeground
  export _root_.sdl2.functions.SDL_OnApplicationWillResignActive
  export _root_.sdl2.functions.SDL_OnApplicationWillTerminate
  export _root_.sdl2.functions.SDL_OpenAudio
  export _root_.sdl2.functions.SDL_OpenAudioDevice
  export _root_.sdl2.functions.SDL_OpenURL
  export _root_.sdl2.functions.SDL_PauseAudio
  export _root_.sdl2.functions.SDL_PauseAudioDevice
  export _root_.sdl2.functions.SDL_PeepEvents
  export _root_.sdl2.functions.SDL_PixelFormatEnumToMasks
  export _root_.sdl2.functions.SDL_PointInFRect
  export _root_.sdl2.functions.SDL_PointInRect
  export _root_.sdl2.functions.SDL_PollEvent
  export _root_.sdl2.functions.SDL_PremultiplyAlpha
  export _root_.sdl2.functions.SDL_PumpEvents
  export _root_.sdl2.functions.SDL_PushEvent
  export _root_.sdl2.functions.SDL_QueryTexture
  export _root_.sdl2.functions.SDL_QueueAudio
  export _root_.sdl2.functions.SDL_Quit
  export _root_.sdl2.functions.SDL_QuitSubSystem
  export _root_.sdl2.functions.SDL_RWFromConstMem
  export _root_.sdl2.functions.SDL_RWFromFP
  export _root_.sdl2.functions.SDL_RWFromFile
  export _root_.sdl2.functions.SDL_RWFromMem
  export _root_.sdl2.functions.SDL_RWclose
  export _root_.sdl2.functions.SDL_RWread
  export _root_.sdl2.functions.SDL_RWseek
  export _root_.sdl2.functions.SDL_RWsize
  export _root_.sdl2.functions.SDL_RWtell
  export _root_.sdl2.functions.SDL_RWwrite
  export _root_.sdl2.functions.SDL_RaiseWindow
  export _root_.sdl2.functions.SDL_ReadBE16
  export _root_.sdl2.functions.SDL_ReadBE32
  export _root_.sdl2.functions.SDL_ReadBE64
  export _root_.sdl2.functions.SDL_ReadLE16
  export _root_.sdl2.functions.SDL_ReadLE32
  export _root_.sdl2.functions.SDL_ReadLE64
  export _root_.sdl2.functions.SDL_ReadU8
  export _root_.sdl2.functions.SDL_RecordGesture
  export _root_.sdl2.functions.SDL_RectEmpty
  export _root_.sdl2.functions.SDL_RectEquals
  export _root_.sdl2.functions.SDL_RegisterApp
  export _root_.sdl2.functions.SDL_RegisterEvents
  export _root_.sdl2.functions.SDL_RemoveTimer
  export _root_.sdl2.functions.SDL_RenderClear
  export _root_.sdl2.functions.SDL_RenderCopy
  export _root_.sdl2.functions.SDL_RenderCopyEx
  export _root_.sdl2.functions.SDL_RenderCopyExF
  export _root_.sdl2.functions.SDL_RenderCopyF
  export _root_.sdl2.functions.SDL_RenderDrawLine
  export _root_.sdl2.functions.SDL_RenderDrawLineF
  export _root_.sdl2.functions.SDL_RenderDrawLines
  export _root_.sdl2.functions.SDL_RenderDrawLinesF
  export _root_.sdl2.functions.SDL_RenderDrawPoint
  export _root_.sdl2.functions.SDL_RenderDrawPointF
  export _root_.sdl2.functions.SDL_RenderDrawPoints
  export _root_.sdl2.functions.SDL_RenderDrawPointsF
  export _root_.sdl2.functions.SDL_RenderDrawRect
  export _root_.sdl2.functions.SDL_RenderDrawRectF
  export _root_.sdl2.functions.SDL_RenderDrawRects
  export _root_.sdl2.functions.SDL_RenderDrawRectsF
  export _root_.sdl2.functions.SDL_RenderFillRect
  export _root_.sdl2.functions.SDL_RenderFillRectF
  export _root_.sdl2.functions.SDL_RenderFillRects
  export _root_.sdl2.functions.SDL_RenderFillRectsF
  export _root_.sdl2.functions.SDL_RenderFlush
  export _root_.sdl2.functions.SDL_RenderGeometry
  export _root_.sdl2.functions.SDL_RenderGeometryRaw
  export _root_.sdl2.functions.SDL_RenderGetClipRect
  export _root_.sdl2.functions.SDL_RenderGetD3D11Device
  export _root_.sdl2.functions.SDL_RenderGetD3D12Device
  export _root_.sdl2.functions.SDL_RenderGetD3D9Device
  export _root_.sdl2.functions.SDL_RenderGetIntegerScale
  export _root_.sdl2.functions.SDL_RenderGetLogicalSize
  export _root_.sdl2.functions.SDL_RenderGetMetalCommandEncoder
  export _root_.sdl2.functions.SDL_RenderGetMetalLayer
  export _root_.sdl2.functions.SDL_RenderGetScale
  export _root_.sdl2.functions.SDL_RenderGetViewport
  export _root_.sdl2.functions.SDL_RenderGetWindow
  export _root_.sdl2.functions.SDL_RenderIsClipEnabled
  export _root_.sdl2.functions.SDL_RenderLogicalToWindow
  export _root_.sdl2.functions.SDL_RenderPresent
  export _root_.sdl2.functions.SDL_RenderReadPixels
  export _root_.sdl2.functions.SDL_RenderSetClipRect
  export _root_.sdl2.functions.SDL_RenderSetIntegerScale
  export _root_.sdl2.functions.SDL_RenderSetLogicalSize
  export _root_.sdl2.functions.SDL_RenderSetScale
  export _root_.sdl2.functions.SDL_RenderSetVSync
  export _root_.sdl2.functions.SDL_RenderSetViewport
  export _root_.sdl2.functions.SDL_RenderTargetSupported
  export _root_.sdl2.functions.SDL_RenderWindowToLogical
  export _root_.sdl2.functions.SDL_ReportAssertion
  export _root_.sdl2.functions.SDL_ResetAssertionReport
  export _root_.sdl2.functions.SDL_ResetHint
  export _root_.sdl2.functions.SDL_ResetHints
  export _root_.sdl2.functions.SDL_ResetKeyboard
  export _root_.sdl2.functions.SDL_RestoreWindow
  export _root_.sdl2.functions.SDL_SIMDAlloc
  export _root_.sdl2.functions.SDL_SIMDFree
  export _root_.sdl2.functions.SDL_SIMDGetAlignment
  export _root_.sdl2.functions.SDL_SIMDRealloc
  export _root_.sdl2.functions.SDL_SaveAllDollarTemplates
  export _root_.sdl2.functions.SDL_SaveBMP_RW
  export _root_.sdl2.functions.SDL_SaveDollarTemplate
  export _root_.sdl2.functions.SDL_SemPost
  export _root_.sdl2.functions.SDL_SemTryWait
  export _root_.sdl2.functions.SDL_SemValue
  export _root_.sdl2.functions.SDL_SemWait
  export _root_.sdl2.functions.SDL_SemWaitTimeout
  export _root_.sdl2.functions.SDL_SensorClose
  export _root_.sdl2.functions.SDL_SensorFromInstanceID
  export _root_.sdl2.functions.SDL_SensorGetData
  export _root_.sdl2.functions.SDL_SensorGetDataWithTimestamp
  export _root_.sdl2.functions.SDL_SensorGetDeviceInstanceID
  export _root_.sdl2.functions.SDL_SensorGetDeviceName
  export _root_.sdl2.functions.SDL_SensorGetDeviceNonPortableType
  export _root_.sdl2.functions.SDL_SensorGetDeviceType
  export _root_.sdl2.functions.SDL_SensorGetInstanceID
  export _root_.sdl2.functions.SDL_SensorGetName
  export _root_.sdl2.functions.SDL_SensorGetNonPortableType
  export _root_.sdl2.functions.SDL_SensorGetType
  export _root_.sdl2.functions.SDL_SensorOpen
  export _root_.sdl2.functions.SDL_SensorUpdate
  export _root_.sdl2.functions.SDL_SetAssertionHandler
  export _root_.sdl2.functions.SDL_SetClipRect
  export _root_.sdl2.functions.SDL_SetClipboardText
  export _root_.sdl2.functions.SDL_SetColorKey
  export _root_.sdl2.functions.SDL_SetCursor
  export _root_.sdl2.functions.SDL_SetError
  export _root_.sdl2.functions.SDL_SetEventFilter
  export _root_.sdl2.functions.SDL_SetHint
  export _root_.sdl2.functions.SDL_SetHintWithPriority
  export _root_.sdl2.functions.SDL_SetMainReady
  export _root_.sdl2.functions.SDL_SetMemoryFunctions
  export _root_.sdl2.functions.SDL_SetModState
  export _root_.sdl2.functions.SDL_SetPaletteColors
  export _root_.sdl2.functions.SDL_SetPixelFormatPalette
  export _root_.sdl2.functions.SDL_SetPrimarySelectionText
  export _root_.sdl2.functions.SDL_SetRelativeMouseMode
  export _root_.sdl2.functions.SDL_SetRenderDrawBlendMode
  export _root_.sdl2.functions.SDL_SetRenderDrawColor
  export _root_.sdl2.functions.SDL_SetRenderTarget
  export _root_.sdl2.functions.SDL_SetSurfaceAlphaMod
  export _root_.sdl2.functions.SDL_SetSurfaceBlendMode
  export _root_.sdl2.functions.SDL_SetSurfaceColorMod
  export _root_.sdl2.functions.SDL_SetSurfacePalette
  export _root_.sdl2.functions.SDL_SetSurfaceRLE
  export _root_.sdl2.functions.SDL_SetTextInputRect
  export _root_.sdl2.functions.SDL_SetTextureAlphaMod
  export _root_.sdl2.functions.SDL_SetTextureBlendMode
  export _root_.sdl2.functions.SDL_SetTextureColorMod
  export _root_.sdl2.functions.SDL_SetTextureScaleMode
  export _root_.sdl2.functions.SDL_SetTextureUserData
  export _root_.sdl2.functions.SDL_SetThreadPriority
  export _root_.sdl2.functions.SDL_SetWindowAlwaysOnTop
  export _root_.sdl2.functions.SDL_SetWindowBordered
  export _root_.sdl2.functions.SDL_SetWindowBrightness
  export _root_.sdl2.functions.SDL_SetWindowData
  export _root_.sdl2.functions.SDL_SetWindowDisplayMode
  export _root_.sdl2.functions.SDL_SetWindowFullscreen
  export _root_.sdl2.functions.SDL_SetWindowGammaRamp
  export _root_.sdl2.functions.SDL_SetWindowGrab
  export _root_.sdl2.functions.SDL_SetWindowHitTest
  export _root_.sdl2.functions.SDL_SetWindowIcon
  export _root_.sdl2.functions.SDL_SetWindowInputFocus
  export _root_.sdl2.functions.SDL_SetWindowKeyboardGrab
  export _root_.sdl2.functions.SDL_SetWindowMaximumSize
  export _root_.sdl2.functions.SDL_SetWindowMinimumSize
  export _root_.sdl2.functions.SDL_SetWindowModalFor
  export _root_.sdl2.functions.SDL_SetWindowMouseGrab
  export _root_.sdl2.functions.SDL_SetWindowMouseRect
  export _root_.sdl2.functions.SDL_SetWindowOpacity
  export _root_.sdl2.functions.SDL_SetWindowPosition
  export _root_.sdl2.functions.SDL_SetWindowResizable
  export _root_.sdl2.functions.SDL_SetWindowShape
  export _root_.sdl2.functions.SDL_SetWindowSize
  export _root_.sdl2.functions.SDL_SetWindowTitle
  export _root_.sdl2.functions.SDL_SetWindowsMessageHook
  export _root_.sdl2.functions.SDL_SetYUVConversionMode
  export _root_.sdl2.functions.SDL_ShowCursor
  export _root_.sdl2.functions.SDL_ShowMessageBox
  export _root_.sdl2.functions.SDL_ShowSimpleMessageBox
  export _root_.sdl2.functions.SDL_ShowWindow
  export _root_.sdl2.functions.SDL_SoftStretch
  export _root_.sdl2.functions.SDL_SoftStretchLinear
  export _root_.sdl2.functions.SDL_StartTextInput
  export _root_.sdl2.functions.SDL_StopTextInput
  export _root_.sdl2.functions.SDL_SwapFloat
  export _root_.sdl2.functions.SDL_TLSCleanup
  export _root_.sdl2.functions.SDL_TLSCreate
  export _root_.sdl2.functions.SDL_TLSGet
  export _root_.sdl2.functions.SDL_TLSSet
  export _root_.sdl2.functions.SDL_ThreadID
  export _root_.sdl2.functions.SDL_TryLockMutex
  export _root_.sdl2.functions.SDL_UnionFRect
  export _root_.sdl2.functions.SDL_UnionRect
  export _root_.sdl2.functions.SDL_UnloadObject
  export _root_.sdl2.functions.SDL_UnlockAudio
  export _root_.sdl2.functions.SDL_UnlockAudioDevice
  export _root_.sdl2.functions.SDL_UnlockJoysticks
  export _root_.sdl2.functions.SDL_UnlockMutex
  export _root_.sdl2.functions.SDL_UnlockSensors
  export _root_.sdl2.functions.SDL_UnlockSurface
  export _root_.sdl2.functions.SDL_UnlockTexture
  export _root_.sdl2.functions.SDL_UnregisterApp
  export _root_.sdl2.functions.SDL_UpdateNVTexture
  export _root_.sdl2.functions.SDL_UpdateTexture
  export _root_.sdl2.functions.SDL_UpdateWindowSurface
  export _root_.sdl2.functions.SDL_UpdateWindowSurfaceRects
  export _root_.sdl2.functions.SDL_UpdateYUVTexture
  export _root_.sdl2.functions.SDL_UpperBlit
  export _root_.sdl2.functions.SDL_UpperBlitScaled
  export _root_.sdl2.functions.SDL_VideoInit
  export _root_.sdl2.functions.SDL_VideoQuit
  export _root_.sdl2.functions.SDL_WaitEvent
  export _root_.sdl2.functions.SDL_WaitEventTimeout
  export _root_.sdl2.functions.SDL_WaitThread
  export _root_.sdl2.functions.SDL_WarpMouseGlobal
  export _root_.sdl2.functions.SDL_WarpMouseInWindow
  export _root_.sdl2.functions.SDL_WasInit
  export _root_.sdl2.functions.SDL_WriteBE16
  export _root_.sdl2.functions.SDL_WriteBE32
  export _root_.sdl2.functions.SDL_WriteBE64
  export _root_.sdl2.functions.SDL_WriteLE16
  export _root_.sdl2.functions.SDL_WriteLE32
  export _root_.sdl2.functions.SDL_WriteLE64
  export _root_.sdl2.functions.SDL_WriteU8
  export _root_.sdl2.functions.SDL_abs
  export _root_.sdl2.functions.SDL_acos
  export _root_.sdl2.functions.SDL_acosf
  export _root_.sdl2.functions.SDL_asin
  export _root_.sdl2.functions.SDL_asinf
  export _root_.sdl2.functions.SDL_asprintf
  export _root_.sdl2.functions.SDL_atan
  export _root_.sdl2.functions.SDL_atan2
  export _root_.sdl2.functions.SDL_atan2f
  export _root_.sdl2.functions.SDL_atanf
  export _root_.sdl2.functions.SDL_atof
  export _root_.sdl2.functions.SDL_atoi
  export _root_.sdl2.functions.SDL_bsearch
  export _root_.sdl2.functions.SDL_calloc
  export _root_.sdl2.functions.SDL_ceil
  export _root_.sdl2.functions.SDL_ceilf
  export _root_.sdl2.functions.SDL_copysign
  export _root_.sdl2.functions.SDL_copysignf
  export _root_.sdl2.functions.SDL_cos
  export _root_.sdl2.functions.SDL_cosf
  export _root_.sdl2.functions.SDL_crc16
  export _root_.sdl2.functions.SDL_crc32
  export _root_.sdl2.functions.SDL_exp
  export _root_.sdl2.functions.SDL_expf
  export _root_.sdl2.functions.SDL_fabs
  export _root_.sdl2.functions.SDL_fabsf
  export _root_.sdl2.functions.SDL_floor
  export _root_.sdl2.functions.SDL_floorf
  export _root_.sdl2.functions.SDL_fmod
  export _root_.sdl2.functions.SDL_fmodf
  export _root_.sdl2.functions.SDL_free
  export _root_.sdl2.functions.SDL_getenv
  export _root_.sdl2.functions.SDL_hid_ble_scan
  export _root_.sdl2.functions.SDL_hid_close
  export _root_.sdl2.functions.SDL_hid_device_change_count
  export _root_.sdl2.functions.SDL_hid_enumerate
  export _root_.sdl2.functions.SDL_hid_exit
  export _root_.sdl2.functions.SDL_hid_free_enumeration
  export _root_.sdl2.functions.SDL_hid_get_feature_report
  export _root_.sdl2.functions.SDL_hid_get_indexed_string
  export _root_.sdl2.functions.SDL_hid_get_manufacturer_string
  export _root_.sdl2.functions.SDL_hid_get_product_string
  export _root_.sdl2.functions.SDL_hid_get_serial_number_string
  export _root_.sdl2.functions.SDL_hid_init
  export _root_.sdl2.functions.SDL_hid_open
  export _root_.sdl2.functions.SDL_hid_open_path
  export _root_.sdl2.functions.SDL_hid_read
  export _root_.sdl2.functions.SDL_hid_read_timeout
  export _root_.sdl2.functions.SDL_hid_send_feature_report
  export _root_.sdl2.functions.SDL_hid_set_nonblocking
  export _root_.sdl2.functions.SDL_hid_write
  export _root_.sdl2.functions.SDL_iconv
  export _root_.sdl2.functions.SDL_iconv_close
  export _root_.sdl2.functions.SDL_iconv_open
  export _root_.sdl2.functions.SDL_iconv_string
  export _root_.sdl2.functions.SDL_isalnum
  export _root_.sdl2.functions.SDL_isalpha
  export _root_.sdl2.functions.SDL_isblank
  export _root_.sdl2.functions.SDL_iscntrl
  export _root_.sdl2.functions.SDL_isdigit
  export _root_.sdl2.functions.SDL_isgraph
  export _root_.sdl2.functions.SDL_islower
  export _root_.sdl2.functions.SDL_isprint
  export _root_.sdl2.functions.SDL_ispunct
  export _root_.sdl2.functions.SDL_isspace
  export _root_.sdl2.functions.SDL_isupper
  export _root_.sdl2.functions.SDL_isxdigit
  export _root_.sdl2.functions.SDL_itoa
  export _root_.sdl2.functions.SDL_lltoa
  export _root_.sdl2.functions.SDL_log
  export _root_.sdl2.functions.SDL_log10
  export _root_.sdl2.functions.SDL_log10f
  export _root_.sdl2.functions.SDL_logf
  export _root_.sdl2.functions.SDL_lround
  export _root_.sdl2.functions.SDL_lroundf
  export _root_.sdl2.functions.SDL_ltoa
  export _root_.sdl2.functions.SDL_main
  export _root_.sdl2.functions.SDL_malloc
  export _root_.sdl2.functions.SDL_memcmp
  export _root_.sdl2.functions.SDL_memcpy
  export _root_.sdl2.functions.SDL_memcpy4
  export _root_.sdl2.functions.SDL_memmove
  export _root_.sdl2.functions.SDL_memset
  export _root_.sdl2.functions.SDL_memset4
  export _root_.sdl2.functions.SDL_pow
  export _root_.sdl2.functions.SDL_powf
  export _root_.sdl2.functions.SDL_qsort
  export _root_.sdl2.functions.SDL_realloc
  export _root_.sdl2.functions.SDL_round
  export _root_.sdl2.functions.SDL_roundf
  export _root_.sdl2.functions.SDL_scalbn
  export _root_.sdl2.functions.SDL_scalbnf
  export _root_.sdl2.functions.SDL_setenv
  export _root_.sdl2.functions.SDL_sin
  export _root_.sdl2.functions.SDL_sinf
  export _root_.sdl2.functions.SDL_size_add_overflow
  export _root_.sdl2.functions.SDL_size_mul_overflow
  export _root_.sdl2.functions.SDL_snprintf
  export _root_.sdl2.functions.SDL_sqrt
  export _root_.sdl2.functions.SDL_sqrtf
  export _root_.sdl2.functions.SDL_sscanf
  export _root_.sdl2.functions.SDL_strcasecmp
  export _root_.sdl2.functions.SDL_strcasestr
  export _root_.sdl2.functions.SDL_strchr
  export _root_.sdl2.functions.SDL_strcmp
  export _root_.sdl2.functions.SDL_strdup
  export _root_.sdl2.functions.SDL_strlcat
  export _root_.sdl2.functions.SDL_strlcpy
  export _root_.sdl2.functions.SDL_strlen
  export _root_.sdl2.functions.SDL_strlwr
  export _root_.sdl2.functions.SDL_strncasecmp
  export _root_.sdl2.functions.SDL_strncmp
  export _root_.sdl2.functions.SDL_strrchr
  export _root_.sdl2.functions.SDL_strrev
  export _root_.sdl2.functions.SDL_strstr
  export _root_.sdl2.functions.SDL_strtod
  export _root_.sdl2.functions.SDL_strtokr
  export _root_.sdl2.functions.SDL_strtol
  export _root_.sdl2.functions.SDL_strtoll
  export _root_.sdl2.functions.SDL_strtoul
  export _root_.sdl2.functions.SDL_strtoull
  export _root_.sdl2.functions.SDL_strupr
  export _root_.sdl2.functions.SDL_tan
  export _root_.sdl2.functions.SDL_tanf
  export _root_.sdl2.functions.SDL_tolower
  export _root_.sdl2.functions.SDL_toupper
  export _root_.sdl2.functions.SDL_trunc
  export _root_.sdl2.functions.SDL_truncf
  export _root_.sdl2.functions.SDL_uitoa
  export _root_.sdl2.functions.SDL_ulltoa
  export _root_.sdl2.functions.SDL_ultoa
  export _root_.sdl2.functions.SDL_utf8strlcpy
  export _root_.sdl2.functions.SDL_utf8strlen
  export _root_.sdl2.functions.SDL_utf8strnlen
  export _root_.sdl2.functions.SDL_vasprintf
  export _root_.sdl2.functions.SDL_vsnprintf
  export _root_.sdl2.functions.SDL_vsscanf
  export _root_.sdl2.functions.SDL_wcscasecmp
  export _root_.sdl2.functions.SDL_wcscmp
  export _root_.sdl2.functions.SDL_wcsdup
  export _root_.sdl2.functions.SDL_wcslcat
  export _root_.sdl2.functions.SDL_wcslcpy
  export _root_.sdl2.functions.SDL_wcslen
  export _root_.sdl2.functions.SDL_wcsncasecmp
  export _root_.sdl2.functions.SDL_wcsncmp
  export _root_.sdl2.functions.SDL_wcsstr
  export _root_.sdl2.functions._SDL_size_add_overflow_builtin
  export _root_.sdl2.functions._SDL_size_mul_overflow_builtin
  export _root_.sdl2.functions.__debugbreak
  export _root_.sdl2.functions._m_prefetch
  export _root_.sdl2.functions.SDL_GUIDFromString
  export _root_.sdl2.functions.SDL_GUIDToString
  export _root_.sdl2.functions.SDL_GameControllerGetBindForAxis
  export _root_.sdl2.functions.SDL_GameControllerGetBindForButton
  export _root_.sdl2.functions.SDL_GameControllerMappingForGUID
  export _root_.sdl2.functions.SDL_GetJoystickGUIDInfo
  export _root_.sdl2.functions.SDL_JoystickGetDeviceGUID
  export _root_.sdl2.functions.SDL_JoystickGetGUID
  export _root_.sdl2.functions.SDL_JoystickGetGUIDFromString
  export _root_.sdl2.functions.SDL_JoystickGetGUIDString
