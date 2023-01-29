package eu.joaocosta.minart.graphics

import scala.concurrent.Future

import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.runtime._

/** A render loop that takes a side-effectful renderFrame operation. */
object ImpureRenderLoop extends AppLoop.Builder[Function1, Function2] {
  protected val effect = new FrameEffect[Function1, Function2] {
    def contramap[A, AA, B](f: A => B, g: AA => A): AA => B =
      g.andThen(f)
    def contramapSubsystem[A, AA, B, C](f: (A, B) => C, g: AA => A): (AA, B) => C =
      (subsystem: AA, state: B) => f(g(subsystem), state)
    def addState[A, B](f: A => B): (A, Unit) => B =
      (subsystem: A, _: Unit) => f(subsystem)
    def unsafeRun[A, B, C](f: (A, B) => C, subsystem: A, state: B): C =
      f(subsystem, state)
  }
}
