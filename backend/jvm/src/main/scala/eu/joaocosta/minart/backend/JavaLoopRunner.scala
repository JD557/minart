package eu.joaocosta.minart.backend

import scala.annotation.tailrec
import scala.concurrent._

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
        new NeverRenderLoop(operation)
      case LoopFrequency.Uncapped =>
        new UncappedRenderLoop(operation, terminateWhen, cleanup)
      case LoopFrequency.LoopDuration(iterationMillis) =>
        new CappedRenderLoop(operation, terminateWhen, iterationMillis, cleanup)
    }
  }

  final class NeverRenderLoop[S](operation: S => S) extends Loop[S] {
    def run(initialState: S) =
      Future(operation(initialState))(ExecutionContext.global)
  }

  final class UncappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) extends Loop[S] {
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

  final class CappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) extends Loop[S] {
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
