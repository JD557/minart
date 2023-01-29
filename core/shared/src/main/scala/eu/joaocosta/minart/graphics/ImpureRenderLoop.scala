package eu.joaocosta.minart.graphics

import scala.concurrent.Future

import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.runtime._

/** A render loop that takes a side-effectful renderFrame operation. */
object ImpureRenderLoop extends RenderLoop.Builder[Function1, Function2] {
  def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: (Subsystem, State) => State,
      terminateWhen: State => Boolean = (_: State) => false
  ): RenderLoop.Definition[State, Settings, Subsystem] = {
    new RenderLoop.Definition[State, Settings, Subsystem] {
      def configure(
          initialSettings: Settings,
          frameRate: LoopFrequency,
          initialState: State
      ): RenderLoop[State, Subsystem] = new RenderLoop[State, Subsystem] {
        def run(
            runner: LoopRunner,
            createSubsystem: () => Subsystem
        ): Future[State] = {
          val subsystem = createSubsystem().init(initialSettings)
          runner
            .finiteLoop(
              (state: State) => renderFrame(subsystem, state),
              (newState: State) => terminateWhen(newState) || !subsystem.isCreated(),
              frameRate,
              () => if (subsystem.isCreated()) subsystem.close()
            )
            .run(initialState)
        }
      }
    }
  }

  def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: Subsystem => Unit
  ): RenderLoop.Definition[Unit, Settings, Subsystem] =
    statefulLoop[Unit, Settings, Subsystem]((s: Subsystem, _: Unit) => renderFrame(s))
}
