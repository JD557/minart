package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime.pure._

/** Representation of a surface operation, with the common Monad operations.
  */
trait SurfaceIOOps {

  /** Wrap surface operations in a  [[SurfaceIO]]. */
  def accessSurface[A](f: Surface => A): SurfaceIO[A] = RIO.access[Surface, A](f)

  /** Gets the color from the this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to either use `getPixels` to fetch multiple pixels at the same time or
    * to implement this operation on the application code.
    *
    * @param x pixel x position
    * @param y pixel y position
    */
  def getPixel(x: Int, y: Int): SurfaceIO[Option[Color]] = accessSurface(_.getPixel(x, y))

  /** Returns the pixels from this surface.
    * This operation can be perfomance intensive, so it might be worthwile
    * to implement this operation on the application code.
    *
    * @return color matrix
    */
  val getPixels: SurfaceIO[Vector[Array[Color]]] = accessSurface(_.getPixels())
}
