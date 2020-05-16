package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

class PpmCanvas(val settings: Canvas.Settings) extends LowLevelCanvas {
  private[this] val buffer: Array[Array[Color]] =
    Array.fill(settings.height)(Array.fill(settings.width)(settings.clearColor))
  private[this] val keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set()) // Keyboard input not supported

  def unsafeInit(): Unit = ()
  def unsafeDestroy(): Unit = ()

  def putPixel(x: Int, y: Int, color: Color): Unit = buffer(y)(x) = color

  def getBackbufferPixel(x: Int, y: Int): Color = buffer(y)(x)

  def clear(): Unit = buffer.foreach(_.transform(_ => settings.clearColor))

  def redraw(): Unit = {
    println("P3")
    println(s"${settings.scaledWidth} ${settings.scaledHeight}")
    println("255")
    for {
      line <- buffer
      _ <- (0 until settings.scale)
      Color(r, g, b) <- line
      _ <- (0 until settings.scale)
    } println(s"$r $g $b")
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
}
