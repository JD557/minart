package eu.joaocosta.minart.core

object Loop {
  trait StatelessLoop {
    def apply(): Unit
  }

  trait StatefulLoop[S] { self =>
    def apply(initialState: S): Unit
    def withInitialState(initialState: S): StatelessLoop = new StatelessLoop {
      def apply(): Unit = self.apply(initialState)
    }
  }
}
