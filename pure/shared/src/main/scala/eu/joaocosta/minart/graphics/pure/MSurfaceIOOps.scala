package eu.joaocosta.minart.graphics.pure

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime.pure._

/** Representation of a mutable surface operation, with the common Monad operations.
  */
trait MSurfaceIOOps extends SurfaceIOOps {

  /** Wrap mutable surface operations in a  [[MSurfaceIO]]. */
  def accessMSurface[A](f: MutableSurface => A): MSurfaceIO[A] = RIO.access[MutableSurface, A](f)

  /** Put a pixel in the surface with a certain color.
    *
    * @param x pixel x position
    * @param y pixel y position
    * @param color `Color` to apply to the pixel
    */
  def putPixel(x: Int, y: Int, color: Color): MSurfaceIO[Unit] = accessMSurface(_.putPixel(x, y, color))

  /** Fill part of the surface with a certain color
    *
    * @param color `Color` to fill the surface with
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param w region width
    * @param h region height
    */
  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): MSurfaceIO[Unit] = accessMSurface(
    _.fillRegion(x, y, w, h, color)
  )

  /** Fill the surface with a certain color
    *
    * @param color `Color` to fill the surface with
    */
  def fill(color: Color): MSurfaceIO[Unit] = accessMSurface(_.fill(color))

  /** Draws a surface on top of this surface.
    *
    * @param that surface to draw
    * @param mask color to use as a mask (pixels with this color won't be merged)
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param cx leftmost pixel on the source surface
    * @param cy topmost pixel on the source surface
    * @param cw clip width of the source surface
    * @param ch clip height of the source surface
    */
  @deprecated("Use blendMode instead of mask")
  def blit(
      that: Surface,
      mask: Option[Color]
  )(x: Int, y: Int, cx: Int, cy: Int, cw: Int, ch: Int): MSurfaceIO[Unit] =
    accessMSurface(_.blit(that, mask)(x, y, cx, cy, cw, ch))

  /** Draws a surface on top of this surface.
    *
    * @param that surface to draw
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination surface
    * @param y topmost pixel on the destination surface
    * @param cx leftmost pixel on the source surface
    * @param cy topmost pixel on the source surface
    * @param cw clip width of the source surface
    * @param ch clip height of the source surface
    */
  def blit(
      that: Surface,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): MSurfaceIO[Unit] =
    accessMSurface(_.blit(that, blendMode)(x, y, cx, cy, cw, ch))
}
