package eu.joaocosta.minart.backend

import scala.concurrent._
import scala.scalajs.js.{isUndefined, timers}
import scala.util.Try

import org.scalajs.dom

import eu.joaocosta.minart.runtime._

/** Loop runner for the JavaScript backend.
  */
object JsLoopRunner extends LoopRunner {
  lazy val hasWindow = Try(!isUndefined(dom.window)).getOrElse(false)

  def finiteLoop[S](
      initialState: S,
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit,
      frequency: LoopFrequency
  ): Future[S] = {
    frequency match {
      case LoopFrequency.Never =>
        new NeverLoop(operation).run(initialState)
      case LoopFrequency.Uncapped =>
        new UncappedLoop(operation, terminateWhen, cleanup).run(initialState)
      case freq @ LoopFrequency.LoopDuration(_) =>
        new CappedLoop(operation, terminateWhen, freq.millis, cleanup).run(initialState)
    }
  }

  final class NeverLoop[S](operation: S => S) {
    def run(initialState: S): Future[S] =
      Future.fromTry(Try(operation(initialState)))
  }

  final class UncappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) {
    def finiteLoopAsyncAux(state: S, promise: Promise[S]): Unit = try {
      val newState = operation(state)
      if (!terminateWhen(newState)) {
        if (!hasWindow) timers.setTimeout(0)(finiteLoopAsyncAux(newState, promise))
        else dom.window.requestAnimationFrame((_: Double) => finiteLoopAsyncAux(newState, promise))
      } else {
        cleanup()
        promise.success(state)
      }
    } catch {
      case e: Throwable =>
        promise.failure(e)
    }
    def run(initialState: S): Future[S] = {
      val promise = Promise[S]
      finiteLoopAsyncAux(initialState, promise)
      promise.future
    }
  }

  final class CappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) {
    def finiteLoopAux(state: S, promise: Promise[S]): Unit = try {
      val startTime = System.currentTimeMillis()
      val newState  = operation(state)
      if (!terminateWhen(newState)) {
        val endTime  = System.currentTimeMillis()
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0 || !hasWindow) timers.setTimeout(waitTime.toDouble)(finiteLoopAux(newState, promise))
        else dom.window.requestAnimationFrame((_: Double) => finiteLoopAux(newState, promise))
      } else {
        cleanup()
        promise.success(newState)
      }
    } catch {
      case e: Throwable =>
        promise.failure(e)
    }
    def run(initialState: S): Future[S] = {
      val promise = Promise[S]
      finiteLoopAux(initialState, promise)
      promise.future
    }
  }
}
