package eu.joaocosta.minart.backend

import org.scalajs.dom

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.{isUndefined, timers}

import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.Loop._

object JsLoopRunner extends LoopRunner {
  lazy val hasWindow = !isUndefined(dom.window)

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
        def finiteLoopAux(state: S): Unit = {
          val startTime = System.currentTimeMillis()
          val newState  = operation(state)
          if (!terminateWhen(newState)) {
            val endTime  = System.currentTimeMillis()
            val waitTime = iterationMillis - (endTime - startTime)
            if (waitTime > 0 || !hasWindow) timers.setTimeout(waitTime.toDouble)(finiteLoopAux(newState))
            else dom.window.requestAnimationFrame((_: Double) => finiteLoopAux(newState))
          } else { cleanup() }
        }
        finiteLoopAux(initialState)
      }
    }
  }

  def singleRun(operation: () => Unit, cleanup: () => Unit): StatelessLoop = new StatelessLoop {
    def apply() = operation()
  }
}
