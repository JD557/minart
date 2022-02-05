package eu.joaocosta.minart.graphics.image

object Helpers {

  sealed trait State[S, +E, +A] {
    def run(initial: S): Either[E, (S, A)]
    def map[B](f: A => B): State[S, E, B]                             = flatMap(x => State(s => (s, f(x))))
    def flatMap[EE >: E, B](f: A => State[S, EE, B]): State[S, EE, B] = State.FlatMap[S, EE, A, B](this, f)
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
    def fromEither[S, A, E](either: Either[E, A]): State[S, E, A] = either match {
      case Right(a) => pure[S, A](a)
      case Left(e)  => error[S, E](e)
    }
    def cond[S, A, E](test: Boolean, success: => A, failure: => E): State[S, E, A] =
      pure(test).flatMap {
        case true  => pure(success)
        case false => error(failure)
      }
    def check[S, E](test: Boolean, failure: => E): State[S, E, Unit] = cond(test, (), failure)
  }
}
