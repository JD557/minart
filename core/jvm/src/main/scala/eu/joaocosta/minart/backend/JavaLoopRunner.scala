package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration

import eu.joaocosta.minart.core._
import eu.joaocosta.minart.core.Loop._

object JavaLoopRunner extends LoopRunner {
  def finiteLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      frameRate: FrameRate,
      cleanup: () => Unit
  ): StatefulLoop[S] = {
    val frameMillis = frameRate match {
      case FrameRate.Uncapped          => 0
      case FrameRate.FrameDuration(ms) => ms
    }
    new StatefulLoop[S] {
      def apply(initialState: S) = {
        @tailrec
        def finiteLoopAux(state: S): Unit = {
          val startTime = System.currentTimeMillis()
          val newState  = operation(state)
          if (!terminateWhen(newState)) {
            val endTime  = System.currentTimeMillis()
            val waitTime = frameMillis - (endTime - startTime)
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
