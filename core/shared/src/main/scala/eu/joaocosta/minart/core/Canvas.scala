package eu.joaocosta.minart.core

/** Canvas that can be painted.
  *
  * The Canvas is the main concept behind minart.
  *
  * A canvas represents a window with a `width * height` pixels.
  * There's also a `scale` variable that controls the integer scaling
  * and a `clearColor` that is applied to the whole canvas when it's cleared.
  */
trait Canvas {

  /** The settings applied to this canvas.
    */
  def settings: Option[Canvas.Settings]

  /** Puts a pixel in the back buffer with a certain color.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @param color `Color` to apply to the pixel
    */
  def putPixel(x: Int, y: Int, color: Color): Unit

  /** Gets the color from the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getBackbuffer` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  def getBackbufferPixel(x: Int, y: Int): Color

  /** Returns the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return backbuffer
    */
  def getBackbuffer(): Vector[Vector[Color]]

  /** Clears resources, such as the backbuffer and keyboard inputs.
    *
    * @param resources set of [[Canvas.Resource]]s to be cleared
    */
  def clear(resources: Set[Canvas.Resource] = Canvas.Resource.all): Unit

  /** Flips buffers and redraws the screen.
    */
  def redraw(): Unit

  /** Gets the current keyboard input.
    *
    * @return current keyboard input
    */
  def getKeyboardInput(): KeyboardInput

  /** Gets the current pointer input.
    *
    * @return current pointer input
    */
  def getPointerInput(): PointerInput
}

object Canvas {

  /** A system resource used by the Canvas.
    */
  sealed trait Resource
  object Resource {
    case object Backbuffer extends Resource
    case object Keyboard   extends Resource
    case object Pointer    extends Resource

    val all: Set[Resource] = Set(Backbuffer, Keyboard, Pointer)
  }

  /** The canvas settings.
    *
    * @param width The canvas width
    * @param height The canvas height
    * @param scale The canvas integer scaling factor
    * @param clearColor The color to be used when the canvas is cleared
    */
  case class Settings(width: Int, height: Int, scale: Int = 1, clearColor: Color = Color(255, 255, 255)) {

    /** The canvas width with the integer scaling applied. */
    lazy val scaledWidth = width * scale

    /** The canvas height with the integer scaling applied. */
    lazy val scaledHeight = height * scale
  }
}
