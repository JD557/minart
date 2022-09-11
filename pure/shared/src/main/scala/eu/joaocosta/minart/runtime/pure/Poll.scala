package eu.joaocosta.minart.runtime.pure

import scala.concurrent._
import scala.util.{Failure, Success, Try}

/** Representation of a running asyncronous computation that can be polled.
  *
  *  Note that, unlike Futures, operations on an Poll don't require an execution context and
  *  might be applied sequentially every time `poll` is called.
  *  While this might be inneficient, this is by design, to simplify multiplatform development.
  */
case class Poll[+A](poll: RIO[Any, Option[Try[A]]]) {

  /** Transforms the result of this operation. */
  def transform[B](f: Try[A] => Try[B]): Poll[B] =
    Poll[B](poll.map(_.map(f)))

  /** Maps the result of this operation. */
  def map[B](f: A => B): Poll[B] =
    this.transform(_.map(f))

  /** Combines two operations by applying a function to the result of the first operation. */
  def flatMap[B](f: A => Poll[B]): Poll[B] = Poll[B](
    poll.flatMap {
      case Some(Success(x)) => f(x).poll
      case Some(Failure(t)) => RIO.pure(Some(Failure(t)))
      case None             => RIO.pure(None)
    }
  )

  /** Combines two operations by combining their results with the given function. */
  def zipWith[B, C](that: Poll[B])(f: (A, B) => C): Poll[C] = this.flatMap(x => that.map(y => f(x, y)))

  /** Combines two operations by combining their results into a tuple. */
  def zip[B](that: Poll[B]): Poll[(A, B)] = this.zipWith(that)((x, y) => x -> y)

  /** Changes the result of this operation to another value. */
  def as[B](x: B): Poll[B] = this.map(_ => x)

  /** Changes the result of this operation unit. */
  def unit: Poll[Unit] = this.as(())
}

object Poll {

  /** Lifts a value into a successful [[Poll]]. */
  def successful[A](x: A): Poll[A] = Poll(RIO.pure(Some(Success(x))))

  /** Lifts a value into a failed [[Poll]]. */
  def failed(t: Throwable): Poll[Nothing] = Poll(RIO.pure(Some(Failure(t))))

  /** Creates an [[Poll]] that never returns a value. */
  val never: Poll[Nothing] = Poll(RIO.pure(None))

  /** Builds an [[Poll]] from a running future */
  def fromFuture[A](future: Future[A]): Poll[A] =
    Poll(RIO.suspend(future.value))

  /** Converts an `Iterable[Poll[A]]` into a `Poll[List[A]]`. */
  def sequence[A](it: Iterable[Poll[A]]): Poll[List[A]] =
    it.foldLeft[Poll[List[A]]](Poll.successful(Nil)) { case (acc, next) =>
      acc.zipWith(next) { case (xs, x) => x :: xs }
    }.map(_.reverse)

  /** Converts an `Iterable[A]` into a `Poll[List[B]]` by applying an operation to each element. */
  def traverse[A, B](it: Iterable[A])(f: A => Poll[B]): Poll[List[B]] =
    it.foldLeft[Poll[List[B]]](Poll.successful(Nil)) { case (acc, next) =>
      acc.zipWith(f(next)) { case (xs, x) => x :: xs }
    }.map(_.reverse)

}
