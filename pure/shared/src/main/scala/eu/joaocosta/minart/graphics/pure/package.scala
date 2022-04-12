package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.runtime.pure.{IOOps, RIO}

package object pure {

  /** Representation of a operation on that requires a Surface, with the common Monad operations.
    *  This is the same as `RIO[Surface, A]`.
    */
  type SurfaceIO[+A] = RIO[Surface, A]

  /** Object containing the operations that act on a Surface.
    */
  object SurfaceIO extends SurfaceIOOps with IOOps[Surface]

  /** Representation of a operation on that requires a Mutable Surface, with the common Monad operations.
    *  This is the same as `RIO[MutableSurface, A]`.
    */
  type MSurfaceIO[+A] = RIO[MutableSurface, A]

  /** Object containing the operations that act on a Mutable Surface.
    */
  object MSurfaceIO extends MSurfaceIOOps with IOOps[MutableSurface]

  /** Representation of a operation that requires a Canvas, with the common Monad operations.
    *  This is the same as `RIO[Canvas, A]`.
    */
  type CanvasIO[+A] = RIO[Canvas, A]

  /** Object containing the operations that act on a Canvas.
    */
  object CanvasIO extends CanvasIOOps with IOOps[Canvas]

  /** Alias for `State => RIO[Canvas, A]` */
  type StateCanvasIO[-Canvas, -State, +A] = Function1[State, RIO[Canvas, A]]
}
