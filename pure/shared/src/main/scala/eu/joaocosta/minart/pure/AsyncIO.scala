package eu.joaocosta.minart.pure

import scala.concurrent.Future
import scala.util.{Try, Failure, Success}

import eu.joaocosta.minart.core._

/** Representation of an asyncronous operation that can be polled.
  *
  *  Note that, unlike Futures, operations on an AsyncIO don't require an execution context and
  *  might be applied sequentially. This is by design, to simplify multiplatform development.
  */
case class AsyncIO[+A](poll: RIO[Any, Option[Try[A]]]) extends AnyVal {

  /** Transforms the result of this operation. */
  def transform[B](f: Try[A] => Try[B]): AsyncIO[B] =
    AsyncIO[B](poll.map(_.map(f)))

  /** Maps the result of this operation. */
  def map[B](f: A => B): AsyncIO[B] =
    this.transform(_.map(f))

  /** Combines two operations by applying a function to the result of the first operation. */
  def flatMap[B](f: A => AsyncIO[B]): AsyncIO[B] = AsyncIO[B](
    poll.flatMap {
      case Some(Success(x)) => f(x).poll
      case Some(Failure(t)) => RIO.pure(Some(Failure(t)))
      case None             => RIO.pure(None)
    }
  )

  /** Combines two operations by combining their results with the given function. */
  def zipWith[B, C](that: AsyncIO[B])(f: (A, B) => C): AsyncIO[C] = this.flatMap(x => that.map(y => f(x, y)))

  /** Combines two operations by combining their results into a tuple. */
  def zip[B](that: AsyncIO[B]): AsyncIO[(A, B)] = this.zipWith(that)((x, y) => x -> y)

  /** Changes the result of this operation to another value. */
  def as[B](x: B): AsyncIO[B] = this.map(_ => x)

  /** Changes the result of this operation unit. */
  def unit: AsyncIO[Unit] = this.as(())
}

object AsyncIO {

  /** Lifts a value into a successful [[AsyncIO]]. */
  def successful[A](x: A): AsyncIO[A] = AsyncIO(RIO.pure(Some(Success(x))))

  /** Lifts a value into a failed [[AsyncIO]]. */
  def failed(t: Throwable): AsyncIO[Nothing] = AsyncIO(RIO.pure(Some(Failure(t))))

  /** Creates an [[AsyncIO]] that never returns a value. */
  val never: AsyncIO[Nothing] = AsyncIO(RIO.pure(None))

  /** Builds an [[AsyncIO]] from a running future */
  def fromFuture[A](future: Future[A]): AsyncIO[A] =
    AsyncIO(RIO.suspend(future.value))

  /** Converts an `Iterable[AsyncIO[A]]` into a `AsyncIO[List[A]]`. */
  def sequence[A](it: Iterable[AsyncIO[A]]): AsyncIO[List[A]] =
    it.foldLeft[AsyncIO[List[A]]](AsyncIO.successful(Nil)) { case (acc, next) =>
      acc.zipWith(next) { case (xs, x) => x :: xs }
    }.map(_.reverse)

  /** Converts an `Iterable[A]` into a `AsyncIO[List[B]]` by applying an operation to each element. */
  def traverse[A, B](it: Iterable[A])(f: A => AsyncIO[B]): AsyncIO[List[B]] =
    it.foldLeft[AsyncIO[List[B]]](AsyncIO.successful(Nil)) { case (acc, next) =>
      acc.zipWith(f(next)) { case (xs, x) => x :: xs }
    }.map(_.reverse)

}
