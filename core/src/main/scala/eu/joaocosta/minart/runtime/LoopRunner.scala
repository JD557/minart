package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.backend.defaults.*

/** Abstraction that allows to run loops at a certain frequency in a platform agnostic way.
  *
  * This is useful for interop with backends that do nor provide `Thread.sleep` or that require
  * that an event loop keeps being consumed.
  */
trait LoopRunner[F[_]] {

  /** Creates a loop that terminates once a certain condition is met.
    *
    * @tparam S State
    * @param initialState initial loop state
    * @param operation operation to perform at each iteration
    * @param terminateWhen stopping condition
    * @param cleanup cleanup procedure to run when the loop is finished
    * @param frequency frequency cap for this loop
    */
  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean = (_: S) => false,
      cleanup: () => Unit = () => (),
      frequency: LoopFrequency = LoopFrequency.Uncapped
  ): F[S]
}

object LoopRunner {

  /** Get the default loop runner */
  def apply[F[_]]()(using backend: DefaultBackend[Any, LoopRunner[F]]): LoopRunner[F] =
    backend.defaultValue()
}
