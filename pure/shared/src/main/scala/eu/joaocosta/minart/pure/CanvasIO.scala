package eu.joaocosta.minart.pure

import scala.util.Try

import eu.joaocosta.minart.core._

/** Representation of a canvas operation, with the common Monad operations.
  */
object CanvasIO {

  /** An operation that does nothing. */
  val noop: CanvasIO[Unit] = RIO.noop

  /** Lifts a value into a [[CanvasIO]]. */
  def pure[A](x: A): CanvasIO[A] = RIO.pure(x)

  /** Suspends a computation into a [[CanvasIO]]. */
  def suspend[A](x: => A): CanvasIO[A] = RIO.suspend(x)

  /** Store an unsafe canvas operation in a [[CanvasIO]]. */
  def accessCanvas[A](f: Canvas => A): CanvasIO[A] = RIO.access[Canvas, A](f)

  /** Returns a [[Poll]] from a function that receives a callback */
  def fromCallback[A](operation: (Try[A] => Unit) => Unit): CanvasIO[Poll[A]] =
    RIO.fromCallback(operation)

  /** Runs a computation only if the predicate is true, otherwise does nothing */
  def when(predicate: Boolean)(io: => CanvasIO[Unit]): CanvasIO[Unit] =
    RIO.when(predicate)(io)

  /** Fetches the canvas settings. */
  val getSettings: CanvasIO[Canvas.Settings] = accessCanvas(_.settings)

  /** Changes the settings applied to the canvas.
    *
    * @param newSettings
    *   new canvas settings
    */
  def changeSettings(newSettings: Canvas.Settings): CanvasIO[Unit] = accessCanvas(_.changeSettings(newSettings))

  /** Puts a pixel in the back buffer with a certain color.
    *
    * @param x
    *   pixel x position
    * @param y
    *   pixel y position
    * @param color
    *   `Color` to apply to the pixel
    */
  def putPixel(x: Int, y: Int, color: Color): CanvasIO[Unit] = accessCanvas(_.putPixel(x, y, color))

  /** Gets the color from the backbuffer. This operation can be perfomance intensive, so it might be worthwile to either
    * use `getBackbuffer` to fetch multiple pixels at the same time or to implement this operation on the application
    * code.
    *
    * @param x
    *   pixel x position
    * @param y
    *   pixel y position
    */
  def getBackbufferPixel(x: Int, y: Int): CanvasIO[Color] = accessCanvas(_.getBackbufferPixel(x, y))

  /** Returns the backbuffer. This operation can be perfomance intensive, so it might be worthwile to implement this
    * operation on the application code.
    */
  val getBackbuffer: CanvasIO[Vector[Vector[Color]]] = accessCanvas(_.getBackbuffer())

  /** Gets the current keyboard input. */
  val getKeyboardInput: CanvasIO[KeyboardInput] = accessCanvas(_.getKeyboardInput())

  /** Gets the current pointer input. */
  val getPointerInput: CanvasIO[PointerInput] = accessCanvas(_.getPointerInput())

  /** Clears resources, such as the backbuffer and keyboard inputs.
    *
    * @param resources
    *   set of [[Canvas.Resource]] s to be cleared
    */
  def clear(resources: Set[Canvas.Resource] = Canvas.Resource.all): CanvasIO[Unit] =
    accessCanvas(_.clear(resources))

  /** Flips buffers and redraws the screen. */
  val redraw: CanvasIO[Unit] = accessCanvas(_.redraw())

  /** Converts an `Iterable[CanvasIO[A]]` into a `CanvasIO[List[A]]`. */
  def sequence[A](it: Iterable[CanvasIO[A]]): CanvasIO[List[A]] =
    RIO.sequence[Canvas, A](it)

  /** Converts an `Iterable[CanvasIO[A]]` into a `CanvasIO[Unit]`. */
  def sequence_(it: Iterable[CanvasIO[Any]]): CanvasIO[Unit] =
    RIO.sequence_[Canvas](it)

  /** Converts an `Iterable[A]` into a `CanvasIO[List[B]]` by applying an operation to each element. */
  def traverse[A, B](it: Iterable[A])(f: A => CanvasIO[B]): CanvasIO[List[B]] =
    RIO.traverse[Canvas, A, B](it)(f)

  /** Applies an operation to each element of a `Iterable[A]` and discards the result. */
  def foreach[A](it: Iterable[A])(f: A => CanvasIO[Any]): CanvasIO[Unit] =
    RIO.foreach[Canvas, A](it)(f)

  /** Applies an operation to each element of a `Iterator[A]` and discards the result. */
  def foreach[A](it: () => Iterator[A])(f: A => CanvasIO[Any]): CanvasIO[Unit] =
    RIO.foreach[Canvas, A](it)(f)
}
