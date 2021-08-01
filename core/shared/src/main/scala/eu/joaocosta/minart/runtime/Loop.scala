package eu.joaocosta.minart.runtime

object Loop {
  trait StatelessLoop extends StatefulLoop[Unit] {
    def apply(): Unit
    def apply(initialState: Unit): Unit                          = apply()
    override def withInitialState(initialState: Unit): this.type = this
  }

  trait StatefulLoop[S] { self =>
    def apply(initialState: S): Unit
    def withInitialState(initialState: S): StatelessLoop = new StatelessLoop {
      def apply(): Unit = self.apply(initialState)
    }
  }
}
