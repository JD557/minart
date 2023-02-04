package eu.joaocosta.minart.runtime

/** Typeclass for effect types for computations that run on each frame.
  *
  * `F1[Subsystem, NewState]` represents a computation that takes a subsystem and returns a new state.
  * `F2[Subsystem, OldState, NewState]` represents a computation that takes a subsystem and an old state returns a
  * new state.
  */
trait FrameEffect[F1[-_, +_], F2[-_, -_, +_]] {
  def contramap[A, AA, B](f: F1[A, B], g: AA => A): F1[AA, B]
  def contramapSubsystem[A, AA, B, C](f: F2[A, B, C], g: AA => A): F2[AA, B, C]
  def addState[A, B](f: F1[A, B]): F2[A, Unit, B]
  def unsafeRun[A, B, C](f: F2[A, B, C], subsystem: A, state: B): C
}

object FrameEffect {
  implicit val functionFrameEffect: FrameEffect[Function1, Function2] = new FrameEffect[Function1, Function2] {
    def contramap[A, AA, B](f: A => B, g: AA => A): AA => B =
      g.andThen(f)
    def contramapSubsystem[A, AA, B, C](f: (A, B) => C, g: AA => A): (AA, B) => C =
      (subsystem: AA, state: B) => f(g(subsystem), state)
    def addState[A, B](f: A => B): (A, Unit) => B =
      (subsystem: A, _: Unit) => f(subsystem)
    def unsafeRun[A, B, C](f: (A, B) => C, subsystem: A, state: B): C =
      f(subsystem, state)
  }
}
