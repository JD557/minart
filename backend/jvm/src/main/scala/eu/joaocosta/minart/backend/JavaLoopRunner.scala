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
    frequency match {
      case LoopFrequency.Never =>
        new Loop[S] {
          def run(initialState: S) = operation(initialState)
        }
      case LoopFrequency.Uncapped =>
        @tailrec
        def finiteLoopAux(state: S): Unit = {
          val newState = operation(state)
          if (!terminateWhen(newState)) finiteLoopAux(newState)
          else ()
        }
        new Loop[S] {
          def run(initialState: S) = {
            finiteLoopAux(initialState)
            cleanup()
          }
        }
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new Loop[S] {
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
          def run(initialState: S) = {
            finiteLoopAux(initialState)
            cleanup()
          }
        }
    }
  }
}
