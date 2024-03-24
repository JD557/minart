package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent.*

import eu.joaocosta.minart.runtime.*

/** Loop Runner for the Java platform.
  */
object JavaAsyncLoopRunner extends LoopRunner[Future] {
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
      case LoopFrequency.LoopDuration(iterationNanos) =>
        new CappedLoop(operation, terminateWhen, iterationNanos, cleanup).run(initialState)
    }
  }

  final class NeverLoop[S](operation: S => S) {
    def run(initialState: S) =
      Future(operation(initialState))(ExecutionContext.global)
  }

  final class UncappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) {
    @tailrec
    def finiteLoopAux(state: S): S = {
      val newState = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else newState
    }
    def run(initialState: S): Future[S] = Future {
      val res = finiteLoopAux(initialState)
      cleanup()
      res
    }(ExecutionContext.global)
  }

  final class CappedLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationNanos: Long,
      cleanup: () => Unit
  ) {

    private val busyLoopNanos = 10 * 1000000 // 10 ms

    @tailrec
    def finiteLoopAux(state: S): S = {
      val startTime = System.nanoTime()
      val newState  = operation(state)
      if (!terminateWhen(newState)) {
        val goalNanos  = startTime + iterationNanos
        val sleepNanos = goalNanos - startTime - busyLoopNanos
        if (sleepNanos > 0) {
          blocking { Thread.sleep(sleepNanos / 1000000) }
        }
        while (System.nanoTime() - goalNanos < 0) {}
        finiteLoopAux(newState)
      } else newState
    }
    def run(initialState: S): Future[S] = Future {
      val res = finiteLoopAux(initialState)
      cleanup()
      res
    }(ExecutionContext.global)
  }
}
