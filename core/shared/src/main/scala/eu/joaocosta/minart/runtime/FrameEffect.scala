package eu.joaocosta.minart.runtime

/** Typeclass for effect types for computations that run on each frame.
  *
  * `F1[Subsystem, NewState]` represents a computation that takes a subsystem and returns a new state.
  * `F2[Subsystem, OldState, NewState]` represents a computation that takes a subsystem and an old state returns a
  * new state.
  */
trait FrameEffect[F[-_, +_]] {
  def contramap[A, AA, B](f: F[A, B], g: AA => A): F[AA, B]
  def unsafeRun[A, B](f: F[A, B], subsystem: A): B
}

object FrameEffect {
  implicit val functionFrameEffect: FrameEffect[Function1] = new FrameEffect[Function1] {
    def contramap[A, AA, B](f: A => B, g: AA => A): AA => B =
      g.andThen(f)
    def unsafeRun[A, B](f: A => B, subsystem: A): B =
      f(subsystem)
  }
}
