package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.input._

/** Window with a canvas that can be painted.
  *
  * The Canvas is the main concept behind minart, providing access to both
  * rendering and input functionallity.
  *
  * A canvas represents a window with a `width * height` pixels.
  * There's also a `scale` variable that controls the integer scaling
  * and a `clearColor` that is applied to the whole canvas when it's cleared.
  */
trait Canvas extends MutableSurface {

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

  /** Clears buffers, such as the backbuffer and keyboard inputs.
    *
    * @param buffers set of [[Canvas.Buffer]]s to be cleared
    */
  def clear(buffers: Set[Canvas.Buffer] = Canvas.Buffer.all): Unit

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
  def create(settings: Canvas.Settings)(implicit backend: DefaultBackend[Any, LowLevelCanvas]): Canvas =
    CanvasManager().init(settings)

  /** A system resource used by the Canvas.
    */
  sealed trait Buffer
  object Buffer {
    case object Backbuffer     extends Buffer
    case object KeyboardBuffer extends Buffer
    case object PointerBuffer  extends Buffer

    val all: Set[Buffer] = Set(Backbuffer, KeyboardBuffer, PointerBuffer)
  }

  /** The canvas settings.
    *
    * @param width The canvas width
    * @param height The canvas height
    * @param scale The canvas integer scaling factor
    * @param fullscreen Whether the canvas should be rendered in a full screen window
    * @param clearColor The color to be used when the canvas is cleared
    * @param title The title to use if this Canvas needs to create a window
    */
  case class Settings(
      width: Int,
      height: Int,
      scale: Int = 1,
      fullScreen: Boolean = false,
      clearColor: Color = Color(255, 255, 255),
      title: String = "Minart"
  )

}
