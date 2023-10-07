package eu.joaocosta.minart.runtime

/** Entrypoint for Minart applications. */
trait MinartApp[State, Subsystem] {
  def createSubsystem: () => Subsystem
  def loopRunner: LoopRunner
  def appLoop: AppLoop[State, Subsystem]

  final def main(args: Array[String]): Unit = {
    appLoop
      .run(
        loopRunner,
        createSubsystem
      )
  }
}
