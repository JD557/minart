package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.Loop._

object JavaLoopRunner extends LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frequency: LoopFrequency,
      cleanup: () => Unit
  ): StatefulLoop[S] = {
    val iterationMillis = frequency match {
      case LoopFrequency.Uncapped         => 0
      case LoopFrequency.LoopDuration(ms) => ms
    }
    new StatefulLoop[S] {
      def apply(initialState: S) = {
        @tailrec
        def finiteLoopAux(state: S): Unit = {
          val startTime = System.currentTimeMillis()
          val newState  = operation(state)
          if (!terminateWhen(newState)) {
            val endTime  = System.currentTimeMillis()
            val waitTime = iterationMillis - (endTime - startTime)
            if (waitTime > 0) Thread.sleep(waitTime)
            finiteLoopAux(newState)
          } else ()
        }
        finiteLoopAux(initialState)
        cleanup()
      }
    }
  }

  def singleRun(operation: () => Unit, cleanup: () => Unit): StatelessLoop = new StatelessLoop {
    def apply() = operation()
  }
}
