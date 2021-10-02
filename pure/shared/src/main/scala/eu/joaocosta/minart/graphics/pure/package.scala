package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.runtime.pure.{IOOps, RIO}

package object pure {
  type SurfaceIO[+A] = RIO[Surface, A]
  object SurfaceIO extends SurfaceIOOps with IOOps[Surface]
  type MSurfaceIO[+A] = RIO[Surface.MutableSurface, A]
  object MSurfaceIO extends MSurfaceIOOps with IOOps[Surface.MutableSurface]
  type CanvasIO[+A] = RIO[Canvas, A]
  object CanvasIO extends CanvasIOOps with IOOps[Canvas]

  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]
}
