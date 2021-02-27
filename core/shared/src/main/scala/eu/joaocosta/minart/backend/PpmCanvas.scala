package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

/** A low level Canvas implementation that outputs the image in the PPM format to the stdout.
  * This canvas doesn't support fetching the keyboard input.
  */
class PpmCanvas() extends LowLevelCanvas {
  private[this] var buffer: Array[Array[Color]]  = _
  private[this] val keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set())  // Keyboard input not supported
  private[this] val pointerInput: PointerInput   = PointerInput(None, Nil, Nil, false) // Pointer input not supported

  def unsafeInit(newSettings: Canvas.Settings): Unit = {
    buffer = Array.fill(newSettings.height)(Array.fill(newSettings.width)(newSettings.clearColor))
    extendedSettings = LowLevelCanvas.ExtendedSettings(newSettings)
  }
  def unsafeDestroy(): Unit                        = ()
  def changeSettings(newSettings: Canvas.Settings) = init(newSettings)

  def putPixel(x: Int, y: Int, color: Color): Unit = buffer(y)(x) = color

  def getBackbufferPixel(x: Int, y: Int): Color = buffer(y)(x)

  def getBackbuffer() = buffer.map(_.toVector).toVector

  def clear(resources: Set[Canvas.Resource]): Unit = {
    if (resources.contains(Canvas.Resource.Backbuffer)) {
      buffer.foreach(_.transform(_ => settings.clearColor))
    }
  }

  def redraw(): Unit = {
    println("P3")
    println(s"${extendedSettings.scaledWidth} ${extendedSettings.scaledHeight}")
    println("255")
    for {
      line           <- buffer
      _              <- extendedSettings.pixelSize
      Color(r, g, b) <- line
      _              <- extendedSettings.pixelSize
    } println(s"$r $g $b")
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
  def getPointerInput(): PointerInput   = pointerInput
}
