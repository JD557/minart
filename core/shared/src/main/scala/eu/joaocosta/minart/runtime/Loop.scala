package eu.joaocosta.minart.runtime

import scala.concurrent._

/** Provides abstractions for a loop that can be executed, keeping a state on every iteration.
  *
  * @tparam S State
  */
trait Loop[S] { self =>

  /** Runs this loop and returns the result in a future.
    *
    * @param initalState initial loop state
    * @return Final state
    */
  def run(initialState: S): Future[S]

  /** Runs this loop and returns the result in a future.
    *
    * @param initalState initial loop state
    * @return Final state
    */
  final def run()(implicit ev: Unit =:= S): Future[S] = run(ev(()))

  /** Converts this loop to a stateless loop, with a predefined initial state.
    *
    * @param initalState initial loop state
    */
  final def withInitialState(state: S): Loop[Unit] = new Loop[Unit] {
    def run(initialState: Unit): Future[Unit] =
      self.run(state).map(_ => ())(ExecutionContext.global) // TODO this should probably be parasitic
  }
}
