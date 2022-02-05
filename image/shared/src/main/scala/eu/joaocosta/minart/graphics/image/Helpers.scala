package eu.joaocosta.minart.graphics.image

object Helpers {

  def skipBytes(n: Int): ParseState[Nothing, Unit] =
    State.modify(_.drop(n))
  val readByte: ParseState[String, Option[Int]] = State { bytes =>
    bytes.tail -> bytes.headOption
  }
  def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
    bytes.drop(n) -> bytes.take(n).toArray
  }
  def readString(n: Int): ParseState[Nothing, String] =
    readBytes(n).map { bytes => bytes.map(_.toChar).mkString("") }
  def readLENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }
  def readBENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.reverse.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }

  type ParseResult[T]   = Either[String, (LazyList[Int], T)]
  type ParseState[E, T] = State[LazyList[Int], E, T]

  sealed trait State[S, +E, +A] {
    def run(initial: S): Either[E, (S, A)]
    def map[B](f: A => B): State[S, E, B]                             = flatMap(x => State.pure(f(x)))
    def flatMap[EE >: E, B](f: A => State[S, EE, B]): State[S, EE, B] = State.FlatMap[S, EE, A, B](this, f)
    def validate[EE >: E](test: A => Boolean, failure: A => EE): State[S, EE, A] =
      flatMap(x => if (test(x)) State.pure(x) else State.error(failure(x)))
    def collect[EE >: E, B](f: PartialFunction[A, B], failure: A => EE): State[S, EE, B] =
      flatMap(x => f.lift(x).map(State.pure).getOrElse(State.error(failure(x))))
  }
  object State {
    private final case class Point[S, +A](f: S => (S, A)) extends State[S, Nothing, A] {
      def run(initial: S): Either[Nothing, (S, A)] = Right(f(initial))
    }
    private final case class Error[S, +E](error: E) extends State[S, E, Nothing] {
      def run(initial: S): Either[E, Nothing] = Left(error)
    }
    private final case class FlatMap[S, +E, A, +B](st: State[S, E, A], andThen: A => State[S, E, B])
        extends State[S, E, B] {
      def run(initial: S): Either[E, (S, B)] = {
        st.run(initial) match {
          case Right((nextState, nextValue)) => andThen(nextValue).run(nextState)
          case Left(err)                     => Left(err)
        }
      }
    }

    def apply[S, A](f: S => (S, A)): State[S, Nothing, A] = Point(f)
    def pure[S, A](a: A): State[S, Nothing, A]            = Point(s => (s, a))
    def error[S, E](e: E): State[S, E, Nothing]           = Error(e)
    def modify[S](f: S => S): State[S, Nothing, Unit]     = Point(s => (f(s), ()))
    def set[S](s: S): State[S, Nothing, Unit]             = Point(_ => (s, ()))
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
}
