package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.pure._

object PureRenderLoop extends RenderLoop.Builder[RIO, StateRIO] {
  def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: State => RIO[Subsystem, State],
      terminateWhen: State => Boolean = (_: State) => false
  ): RenderLoop.Definition[State, Settings, Subsystem] =
    ImpureRenderLoop.statefulLoop[State, Settings, Subsystem]((canvas, state) => renderFrame(state).run(canvas))

  def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: RIO[Subsystem, Unit]
  ): RenderLoop.Definition[Unit, Settings, Subsystem] =
    ImpureRenderLoop.statelessLoop(subsystem => renderFrame.run(subsystem))
}
