package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime.Loop._

/** Abstraction that allows to run loops at a certain frequency in a platforrm agnostic way.
  *
  * This is useful for interop with backends that do nor provide `Thread.sleep` or that require
  * that an event loop keeps being consumed.
  */
trait LoopRunner {

  /** Creates a loop that terminates once a certain condition is met.
    *
    * @tparam S State
    * @param operation operation to perform at each iteration
    * @param terminateWhen stopping condition
    * @param frequency frequency cap for this loop
    * @param cleanup cleanup procedure to run when the loop is finished
    */
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frequency: LoopFrequency,
      cleanup: () => Unit
  ): StatefulLoop[S]

  /** Runs a single operation.
    *
    * @param operation operation to perform
    * @param cleanup cleanup procedure to run when the operation is finished
    */
  def singleRun(operation: () => Unit, cleanup: () => Unit): StatelessLoop
}

object LoopRunner {

  /** Get the default loop runner */
  def apply(): LoopRunner =
    DefaultBackend[Any, LoopRunner].defaultValue()
}
