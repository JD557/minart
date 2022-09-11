package eu.joaocosta.minart.runtime

import scala.concurrent._

/** Provides abstractions for a loop that can be executed, keeping a state on every iteration.
  *
  * @tparam S State
  */
trait Loop[S] { self =>

  /** Runs this loop.
    *
    * @param initalState initial loop state
    */
  def run(initialState: S): Unit

  /** Runs this loop
    */
  final def run()(implicit ev: Unit =:= S): Unit = run(ev(()))

  /** Runs this loop and returns the result in a future.
    *
    * @param initalState initial loop state
    * @return Final state
    */
  def runAsync(initialState: S)(implicit ec: ExecutionContext): Future[S]

  /** Runs this loop and returns the result in a future.
    *
    * @param initalState initial loop state
    * @return Final state
    */
  final def runAsync()(implicit ev: Unit =:= S, ec: ExecutionContext): Future[S] = runAsync(ev(()))

  /** Converts this loop to a stateless loop, with a predefined initial state.
    *
    * @param initalState initial loop state
    */
  final def withInitialState(state: S): Loop[Unit] = new Loop[Unit] {
    def run(initialState: Unit): Unit                                             = self.run(state)
    def runAsync(initialState: Unit)(implicit ec: ExecutionContext): Future[Unit] = self.runAsync(state).map(_ => ())
  }
}
