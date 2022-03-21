package eu.joaocosta.minart.runtime

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

  /** Converts this loop to a stateless loop, with a predefined initial state.
    *
    * @param initalState initial loop state
    */
  final def withInitialState(state: S): Loop[Unit] = new Loop[Unit] {
    def run(initialState: Unit): Unit = self.run(state)
  }
}
