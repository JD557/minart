package eu.joaocosta.minart.backend

import scala.annotation.tailrec

import eu.joaocosta.minart.runtime._

/** Loop Runner for the Java platform.
  */
object JavaLoopRunner extends LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frequency: LoopFrequency,
      cleanup: () => Unit
  ): Loop[S] = {
    val iterationMillis = frequency match {
      case LoopFrequency.Uncapped         => 0
      case LoopFrequency.LoopDuration(ms) => ms
    }
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
    new Loop[S] {
      def run(initialState: S) = {
        finiteLoopAux(initialState)
        cleanup()
      }
    }
  }

  def singleRun(operation: () => Unit, cleanup: () => Unit): Loop[Unit] = new Loop[Unit] {
    def run(initialState: Unit) = operation()
  }
}
