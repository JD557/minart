package eu.joaocosta.minart.backend

import eu.joaocosta.minart.core._

class PpmCanvas(
  val width: Int,
  val height: Int,
  val scale: Int = 1,
  val clearColor: Color = Color(255, 255, 255)) extends LowLevelCanvas {
  private[this] val buffer: Array[Array[Color]] =
    Array.fill(height)(Array.fill(width)(clearColor))
  private[this] val keyboardInput: KeyboardInput = KeyboardInput(Set(), Set(), Set()) // Keyboard input not supported

  def unsafeInit(): Unit = ()
  def unsafeDestroy(): Unit = ()

  def putPixel(x: Int, y: Int, color: Color): Unit = buffer(y)(x) = color

  def getBackbufferPixel(x: Int, y: Int): Color = buffer(y)(x)

  def clear(): Unit = buffer.foreach(_.transform(_ => clearColor))

  def redraw(): Unit = {
    println("P3")
    println(s"$scaledWidth $scaledHeight")
    println("255")
    for {
      line <- buffer
      _ <- (0 until scale)
      Color(r, g, b) <- line
      _ <- (0 until scale)
    } println(s"$r $g $b")
  }

  def getKeyboardInput(): KeyboardInput = keyboardInput
}
