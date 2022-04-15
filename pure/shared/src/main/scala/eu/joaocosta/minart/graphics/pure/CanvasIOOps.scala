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
