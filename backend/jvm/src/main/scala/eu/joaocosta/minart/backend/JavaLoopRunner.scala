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
    def run(initialState: S) = operation(initialState)
    def runAsync(initialState: S)(implicit ec: ExecutionContext) =
      Future(operation(initialState))
  }

  final class UncappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      cleanup: () => Unit
  ) extends Loop[S] {
    @tailrec
    def finiteLoopAux(state: S): Unit = {
      val newState = operation(state)
      if (!terminateWhen(newState)) finiteLoopAux(newState)
      else ()
    }
    def run(initialState: S) = {
      finiteLoopAux(initialState)
      cleanup()
    }
    def runAsync(initialState: S)(implicit ec: ExecutionContext): Future[S] =
      Future(operation(initialState)).flatMap { newState =>
        if (!terminateWhen(newState)) runAsync(newState)
        else
          Future {
            cleanup()
            newState
          }
      }
  }

  final class CappedRenderLoop[S](
      operation: S => S,
      terminateWhen: S => Boolean,
      iterationMillis: Long,
      cleanup: () => Unit
  ) extends Loop[S] {
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
    def finiteLoopAsyncAux(state: S, startTime: Long)(implicit ec: ExecutionContext): Future[S] =
      Future {
        val endTime  = System.currentTimeMillis()
        val waitTime = iterationMillis - (endTime - startTime)
        if (waitTime > 0) blocking { Thread.sleep(waitTime) }
        (System.currentTimeMillis(), operation(state))
      }.flatMap { case (startTime, newState) =>
        if (!terminateWhen(newState)) finiteLoopAsyncAux(newState, startTime)
        else Future.successful(newState)
      }
    def runAsync(initialState: S)(implicit ec: ExecutionContext) =
      finiteLoopAsyncAux(initialState, System.currentTimeMillis()).map { res =>
        cleanup()
        res
      }
  }
}
