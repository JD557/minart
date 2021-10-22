package eu.joaocosta.minart.runtime

/** Provides abstractions for a loop that can be executed */
object Loop {

  /** Render loop that does not store any state.
    */
  trait StatelessLoop extends StatefulLoop[Unit] {

    /** Runs this loop
      */
    def apply(): Unit
    def apply(initialState: Unit): Unit                          = apply()
    override def withInitialState(initialState: Unit): this.type = this
  }

  /** Loop that keeps an internal state that is passed to every iteration.
    *
    * @tparam S State
    */
  trait StatefulLoop[S] { self =>

    /** Runs this loop.
      *
      * @param initalState initial loop state
      */
    def apply(initialState: S): Unit

    /** Converts this loop to a stateless loop, with a predefined initial state.
      *
      * @param initalState initial loop state
      */
    def withInitialState(initialState: S): StatelessLoop = new StatelessLoop {
      def apply(): Unit = self.apply(initialState)
    }
  }
}
