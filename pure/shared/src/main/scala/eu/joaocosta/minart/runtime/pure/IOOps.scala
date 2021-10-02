package eu.joaocosta.minart.runtime.pure

import scala.util.Try

/** Operations for an IO with a fixed R */
trait IOOps[R] extends IOOps.IOBaseOps[R] {

  /** Returns a operation that requires this resource. */
  def access[A](f: R => A): RIO[R, A] = RIO.access[R, A](f)

  /** Runs a computation only if the predicate is true, otherwise does nothing */
  def when(predicate: Boolean)(io: => RIO[R, Unit]): RIO[R, Unit] =
    RIO.when[R](predicate)(io)

  /** Converts an `Iterable[RIO[R, A]]` into a `RIO[R, List[A]]`. */
  def sequence[A](it: Iterable[RIO[R, A]]): RIO[R, List[A]] =
    RIO.sequence[R, A](it)

  /** Converts an `Iterable[RIO[R, A]]` into a `RIO[R, Unit]`. */
  def sequence_(it: Iterable[RIO[R, Any]]): RIO[R, Unit] =
    RIO.sequence_[R](it)

  /** Converts an `Iterable[A]` into a `RIO[R, List[B]]` by applying an operation to each element. */
  def traverse[A, B](it: Iterable[A])(f: A => RIO[R, B]): RIO[R, List[B]] =
    RIO.traverse[R, A, B](it)(f)

  /** Applies an operation to each element of a `Iterable[A]` and discards the result. */
  def foreach[A](it: Iterable[A])(f: A => RIO[R, Any]): RIO[R, Unit] =
    RIO.foreach[R, A](it)(f)

  /** Applies an operation to each element of a `Iterator[A]` and discards the result. */
  def foreach[A](it: () => Iterator[A])(f: A => RIO[R, Any]): RIO[R, Unit] =
    RIO.foreach[R, A](it)(f)
}

/** Basic IO operations to quicly create RIO type aliases
  */
object IOOps {

  /** Basic operations for an IO with a fixed R */
  trait IOBaseOps[R] {

    /** An operation that does nothing. */
    val noop: RIO[R, Unit] = RIO.Suspend[R, Unit](_ => ())

    /** Lifts a value into a [[RIO]]. */
    def pure[A](x: A): RIO[R, A] = RIO.Suspend[R, A](_ => x)

    /** Suspends a computation into a [[RIO]]. */
    def suspend[A](x: => A): RIO[R, A] = RIO.Suspend[R, A](_ => x)

    /** Returns a [[Poll]] from a function that receives a callback */
    def fromCallback[A](operation: (Try[A] => Unit) => Unit): RIO[R, Poll[A]] = {
      val promise = scala.concurrent.Promise[A]()
      RIO.suspend(operation(promise.complete)).as(Poll.fromFuture(promise.future))
    }
  }
}
