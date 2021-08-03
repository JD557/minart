package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.runtime.pure.RIO

package object pure {
  type CanvasIO[+A] = RIO[Canvas, A]

  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]
}
