package eu.joaocosta.minart.pure

import eu.joaocosta.minart.core._

/**
 * Representation of a canvas operation, with the common Monad operations.
 */
sealed trait CanvasIO[+A] {
  /** Runs this operation on a specified canvas. */
  def run(canvas: Canvas): A
  /** Maps the result of this operation. */
  def map[B](f: A => B): CanvasIO[B]
  /** Combines two operations by applying a function to the result of the first operation. */
  def flatMap[B](f: A => CanvasIO[B]): CanvasIO[B] = CanvasIO.FlatMap[A, B](this, f)
  /** Combines two operations by discarding the result of the first operation. */
  def andThen[B](that: CanvasIO[B]): CanvasIO[B] = CanvasIO.FlatMap[A, B](this, _ => that)
  /** Combines two operations by discarding the result of the second operation. */
  def andFinally[B](that: CanvasIO[B]): CanvasIO[A] = CanvasIO.FlatMap[A, A](this, x => that.as(x))
  /** Combines two operations by combining their results with the given function. */
  def zipWith[B, C](that: CanvasIO[B])(f: (A, B) => C): CanvasIO[C] = this.flatMap(x => that.map(y => f(x, y)))
  /** Combines two operations by combining their results into a tuple. */
  def zip[B](that: CanvasIO[B]): CanvasIO[(A, B)] = this.zipWith(that)((x, y) => x -> y)
  /** Changes the result of this operation to another value */
  def as[B](x: B): CanvasIO[B] = this.map(_ => x)
  /** Changes the result of this operation unit */
  lazy val unit: CanvasIO[Unit] = this.as(())
}

object CanvasIO {
  private case class Suspend[A](thunk: Canvas => A) extends CanvasIO[A] {
    def run(canvas: Canvas): A = thunk(canvas)
    def map[B](f: A => B): CanvasIO[B] = Suspend(thunk.andThen(f))
  }
  private case class FlatMap[A, B](io: CanvasIO[A], andThen: A => CanvasIO[B]) extends CanvasIO[B] {
    def run(canvas: Canvas): B = andThen(io.run(canvas)).run(canvas)
    def map[C](f: B => C): CanvasIO[C] = FlatMap[B, C](this, x => Suspend(_ => f(x)))
  }

  /** Lifts a value into a [[CanvasIO]]. */
  def pure[A](x: A): CanvasIO[A] = Suspend[A](_ => x)

  /** Fetches the canvas settings. */
  val getSettings: CanvasIO[Canvas.Settings] = Suspend[Canvas.Settings](_.settings)

  /**
   * Puts a pixel in the back buffer with a certain color.
   *
   * @param x pixel x position
   * @param y pixel y position
   * @param color `Color` to apply to the pixel
   */
  def putPixel(x: Int, y: Int, color: Color): CanvasIO[Unit] = Suspend[Unit](_.putPixel(x, y, color))

  /**
   * Gets the color from the backbuffer.
   * This operation can be perfomance intensive, so it might be worthwile
   * to either use `getBackbuffer` to fetch multiple pixels at the same time or
   * to implement this operation on the application code.
   *
   * @param x pixel x position
   * @param y pixel y position
   */
  def getBackbufferPixel(x: Int, y: Int): CanvasIO[Color] = Suspend[Color](_.getBackbufferPixel(x, y))

  /**
   * Returns the backbuffer.
   * This operation can be perfomance intensive, so it might be worthwile
   * to implement this operation on the application code.
   */
  val getBackbuffer: CanvasIO[Vector[Vector[Color]]] = Suspend[Vector[Vector[Color]]](_.getBackbuffer)

  /** Gets the current keyboard input. */
  val getKeyboardInput: CanvasIO[KeyboardInput] = Suspend[KeyboardInput](_.getKeyboardInput())

  /** Gets the current pointer input. */
  val getPointerInput: CanvasIO[PointerInput] = Suspend[PointerInput](_.getPointerInput())

  /**
   * Clears resources, such as the backbuffer and keyboard inputs.
   *
   * @param resources set of [[Canvas.Resource]]s to be cleared
   */
  def clear(resources: Set[Canvas.Resource] = Canvas.Resource.all): CanvasIO[Unit] =
    Suspend[Unit](_.clear(resources))

  /** Flips buffers and redraws the screen. */
  val redraw: CanvasIO[Unit] = Suspend[Unit](_.redraw())

  /** Converts an `Iterable[CanvasIO[A]]` into a `CanvasIO[List[A]]`. */
  def sequence[A](it: Iterable[CanvasIO[A]]): CanvasIO[List[A]] =
    Suspend(canvas => it.map(_.run(canvas)).toList)

  /** Converts an `Iterable[CanvasIO[A]]` into a `CanvasIO[Unit]`. */
  def sequence_(it: Iterable[CanvasIO[Any]]): CanvasIO[Unit] =
    Suspend(canvas => it.foreach(_.run(canvas)))

  /** Converts an `Iterable[A]` into a `CanvasIO[List[B]]` by applying an operation to each element. */
  def traverse[A, B](it: Iterable[A])(f: A => CanvasIO[B]): CanvasIO[List[B]] =
    sequence(it.map(f))

  /** Applies an operation to each element of a `Iterable[A]` and discards the result. */
  def foreach[A](it: Iterable[A])(f: A => CanvasIO[Any]): CanvasIO[Unit] =
    sequence_(it.map(f))
}
