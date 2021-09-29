package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults.DefaultBackend
import eu.joaocosta.minart.input._

/** Canvas that can be painted.
  *
  * The Canvas is the main concept behind minart.
  *
  * A canvas represents a window with a `width * height` pixels.
  * There's also a `scale` variable that controls the integer scaling
  * and a `clearColor` that is applied to the whole canvas when it's cleared.
  */
trait Canvas extends Surface.MutableSurface {

  /** The settings applied to this canvas.
    */
  def settings: Canvas.Settings

  /** Changes the settings applied to this canvas.
    *
    *  @param newSettings new canvas settings
    */
  def changeSettings(newSettings: Canvas.Settings): Unit

  def width: Int  = settings.width
  def height: Int = settings.height

  /** Gets the color from the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getBackbuffer` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @return pixel color
    */
  @deprecated("use getPixel(x, y) instead")
  def getBackbufferPixel(x: Int, y: Int): Color =
    getPixel(x, y).get

  /** Returns the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return backbuffer
    */
  @deprecated("use getPixels() instead")
  def getBackbuffer(): Vector[Vector[Color]] =
    getPixels().map(_.toVector).toVector

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

  /** Returns a new [[Canvas]] for the default backend for the target platform.
    *
    * @return [[Canvas]] using the default backend for the target platform
    */
  def create(settings: Canvas.Settings): Canvas =
    CanvasManager().init(settings)

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
    * @param fullscreen Whether the canvas should be rendered in a full screen window
    * @param clearColor The color to be used when the canvas is cleared
    */
  case class Settings(
      width: Int,
      height: Int,
      scale: Int = 1,
      fullScreen: Boolean = false,
      clearColor: Color = Color(255, 255, 255)
  )

}
