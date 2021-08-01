package eu.joaocosta.minart.backend

import scala.scalajs.js.{isUndefined, timers}

import org.scalajs.dom

import eu.joaocosta.minart.core.Loop._
import eu.joaocosta.minart.core._

object JsLoopRunner extends LoopRunner {
  lazy val hasWindow = !isUndefined(dom.window)

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
        def finiteLoopAux(state: S): Unit = {
          val startTime = System.currentTimeMillis()
          val newState  = operation(state)
          if (!terminateWhen(newState)) {
            val endTime  = System.currentTimeMillis()
            val waitTime = frameMillis - (endTime - startTime)
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
