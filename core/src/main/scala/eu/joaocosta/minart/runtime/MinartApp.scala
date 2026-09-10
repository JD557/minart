package eu.joaocosta.minart.runtime

import scala.concurrent.Future

/** Entrypoint for Minart applications. */
trait MinartApp[State, Subsystem] {
  def createSubsystem: () => Subsystem
  def loopRunner: LoopRunner[Future]
  def appLoop: AppLoop[State, Subsystem]

  final def main(args: Array[String]): Unit = {
    appLoop
      .run(
        loopRunner,
        createSubsystem
      )
  }
}
