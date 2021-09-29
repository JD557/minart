package eu.joaocosta.minart.backend

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._

/** A low level Canvas implementation that outputs the image in the PPM format to the stdout.
  * This canvas doesn't support fetching the keyboard input.
  */
class PpmCanvas() extends SurfaceBackedCanvas {

  def this(settings: Canvas.Settings) = {
    this()
    this.init(settings)
  }

  protected var surface: RamSurface              = _
  private[this] val keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())  // Keyboard input not supported
  private[this] val pointerInput: PointerInput   = PointerInput(None, Nil, Nil, false) // Pointer input not supported

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    surface = new RamSurface(Vector.fill(newSettings.height)(Vector.fill(newSettings.width)(newSettings.clearColor)))
    extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
  }
  def unsafeDestroy(): Unit                        = ()
  def changeSettings(newSettings: Canvas.Settings) = init(newSettings)

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) { fill(settings.clearColor) }
  }

  def redraw(): Unit = {
    println("P3")
    println(s"${extendedSettings.scaledWidth} ${extendedSettings.scaledHeight}")
    println("255")
    for {
      line  <- surface.data
      _     <- extendedSettings.pixelSize
      color <- line
      Color(r, g, b) = Color.fromRGB(color)
      _ <- extendedSettings.pixelSize
    } println(s"$r $g $b")
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput
}
