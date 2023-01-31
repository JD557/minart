package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent._

import eu.joaocosta.minart.runtime._

/** Loop Runner for the Java platform.
  */
object JavaLoopRunner extends LoopRunner {
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
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new CappedLoop(operation, terminateWhen, iterationMillis, cleanup).run(initialState)
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
      iterationMillis: Long,
      cleanup: () => Unit
  ) {
    @tailrec
    def finiteLoopAux(state: S): S = {
      val startTime = System.currentTimeMillis()
      val newState  = operation(state)
      if (!terminateWhen(newState)) {
        val endTime  = System.currentTimeMillis()
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { Thread.sleep(waitTime) }
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
