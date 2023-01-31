package eu.joaocosta.minart.runtime

import scala.concurrent.Future

import eu.joaocosta.minart.backend.defaults._

/** Abstraction that allows to run loops at a certain frequency in a platforrm agnostic way.
  *
  * This is useful for interop with backends that do nor provide `Thread.sleep` or that require
  * that an event loop keeps being consumed.
  */
trait LoopRunner {

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
      terminateWhen: S => Boolean,
      cleanup: () => Unit = () => (),
      frequency: LoopFrequency = LoopFrequency.Uncapped
  ): Future[S]
}

object LoopRunner {

  /** Get the default loop runner */
  def apply()(implicit backend: DefaultBackend[Any, LoopRunner]): LoopRunner =
    backend.defaultValue()
}
