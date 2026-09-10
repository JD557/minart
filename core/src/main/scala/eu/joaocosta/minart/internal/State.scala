package eu.joaocosta.minart.internal

import scala.annotation.tailrec

/** State monad implementation to use when loading/storing images.
  */
private[minart] sealed trait State[S, +E, +A] {
  @tailrec
  final def run(initial: S): Either[E, (S, A)] = this match {
    case State.Point(f)             => Right(f(initial))
    case State.Error(error)         => Left(error)
    case State.FlatMap(st, andThen) =>
      st match {
        case State.Point(f) =>
          val (nextState, nextValue) = f(initial)
          andThen(nextValue).run(nextState)
        case State.Error(error)                 => Left(error)
        case State.FlatMap(nextSt, nextAndThen) =>
          nextSt.flatMap(next => nextAndThen(next).flatMap(andThen)).run(initial)
      }
  }
  final def map[B](f: A => B): State[S, E, B]                             = flatMap(x => State.pure(f(x)))
  final def flatMap[EE >: E, B](f: A => State[S, EE, B]): State[S, EE, B] = State.FlatMap[S, EE, A, B](this, f)
  final def validate[EE >: E](test: A => Boolean, failure: A => EE): State[S, EE, A] =
    flatMap(x => if (test(x)) State.pure(x) else State.error(failure(x)))
  final def collect[EE >: E, B](f: PartialFunction[A, B], failure: A => EE): State[S, EE, B] = {
    val pf =
      f.andThen((x: B) => State.pure[S, B](x)).orElse[A, State[S, EE, B]] { case x =>
        State.error[S, EE](failure(x))
      }
    flatMap(pf)
  }
  final def modify(f: S => S): State[S, E, A] =
    flatMap(x => State(s => (f(s), x)))
}
private[minart] object State {
  private final case class Point[S, +A](f: S => (S, A)) extends State[S, Nothing, A]
  private final case class Error[S, +E](error: E)       extends State[S, E, Nothing]
  private final case class FlatMap[S, +E, A, +B](st: State[S, E, A], andThen: A => State[S, E, B])
      extends State[S, E, B]

  def apply[S, A](f: S => (S, A)): State[S, Nothing, A]         = Point(f)
  def pure[S, A](a: A): State[S, Nothing, A]                    = Point(s => (s, a))
  def error[S, E](e: E): State[S, E, Nothing]                   = Error(e)
  def modify[S](f: S => S): State[S, Nothing, Unit]             = Point(s => (f(s), ()))
  def get[S]: State[S, Nothing, S]                              = Point(s => (s, s))
  def set[S](s: S): State[S, Nothing, Unit]                     = Point(_ => (s, ()))
  def fromEither[S, A, E](either: Either[E, A]): State[S, E, A] = either match {
    case Right(a) => pure[S, A](a)
    case Left(e)  => error[S, E](e)
  }
  def cond[S, A, E](test: Boolean, success: => A, failure: => E): State[S, E, A] =
    pure(test).flatMap { result =>
      if (result) pure(success) else error(failure)
    }
  def check[S, E](test: Boolean, failure: => E): State[S, E, Unit] = cond(test, (), failure)
}
