package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._
import eu.joaocosta.minart.runtime.pure._

object PureRenderLoop extends AppLoop.Builder[RIO, StateRIO] {
  protected val effect = new FrameEffect[RIO, StateRIO] {
    def contramap[A, AA, B](f: RIO[A, B], g: AA => A): RIO[AA, B] =
      f.contramap(g)
    def contramapSubsystem[A, AA, B, C](f: B => RIO[A, C], g: AA => A): B => RIO[AA, C] =
      (state: B) => f(state).contramap(g)
    def addState[A, B](f: RIO[A, B]): (Unit) => RIO[A, B] =
      (_: Unit) => f
    def unsafeRun[A, B, C](f: B => RIO[A, C], subsystem: A, state: B): C =
      f(state).run(subsystem)
  }
}
