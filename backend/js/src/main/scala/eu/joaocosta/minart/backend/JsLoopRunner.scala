package eu.joaocosta.minart.backend

import scala.scalajs.js.{isUndefined, timers}

import org.scalajs.dom

import eu.joaocosta.minart.runtime._

/** Loop runner for the JavaScript backend.
  */
object JsLoopRunner extends LoopRunner {
  lazy val hasWindow = !isUndefined(dom.window)

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
        new Loop[S] {
          def finiteLoopAux(state: S): Unit = {
            val newState = operation(state)
            if (!terminateWhen(newState)) {
              if (!hasWindow) timers.setTimeout(0)(finiteLoopAux(newState))
              else dom.window.requestAnimationFrame((_: Double) => finiteLoopAux(newState))
            } else { cleanup() }
          }
          def run(initialState: S) = {
            finiteLoopAux(initialState)
          }
        }
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new Loop[S] {
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
          def run(initialState: S) = {
            finiteLoopAux(initialState)
          }
        }
    }
  }
}
