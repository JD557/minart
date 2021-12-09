package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.input._
import eu.joaocosta.minart.runtime.pure._

/** Representation of a canvas operation, with the common Monad operations.
  */
trait CanvasIOOps extends MSurfaceIOOps {

  /** Store an unsafe canvas operation in a [[CanvasIO]]. */
  def accessCanvas[A](f: Canvas => A): CanvasIO[A] = RIO.access[Canvas, A](f)

  /** Fetches the canvas settings. */
  val getSettings: CanvasIO[Canvas.Settings] = accessCanvas(_.settings)

  /** Changes the settings applied to the canvas.
    *
    *  @param newSettings new canvas settings
    */
  def changeSettings(newSettings: Canvas.Settings): CanvasIO[Unit] = accessCanvas(_.changeSettings(newSettings))

  /** Gets the color from the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getBackbuffer` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    */
  @deprecated("Use CanvasIO.getPixel instead")
  def getBackbufferPixel(x: Int, y: Int): CanvasIO[Color] = accessCanvas(_.getBackbufferPixel(x, y))

  /** Returns the backbuffer.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    */
  @deprecated("Use CanvasIO.getPixels instead")
  val getBackbuffer: CanvasIO[Vector[Vector[Color]]] = accessCanvas(_.getBackbuffer())

  /** Gets the current keyboard input. */
  val getKeyboardInput: CanvasIO[KeyboardInput] = accessCanvas(_.getKeyboardInput())

  /** Gets the current pointer input. */
  val getPointerInput: CanvasIO[PointerInput] = accessCanvas(_.getPointerInput())

  /** Clears buffers, such as the backbuffer and keyboard inputs.
    *
    * @param buffers set of [[Canvas.Buffer]]s to be cleared
    */
  def clear(buffers: Set[Canvas.Buffer] = Canvas.Buffer.all): CanvasIO[Unit] =
    accessCanvas(_.clear(buffers))

  /** Flips buffers and redraws the screen. */
  val redraw: CanvasIO[Unit] = accessCanvas(_.redraw())
}
