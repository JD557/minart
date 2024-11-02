package eu.joaocosta.minart.graphics

import eu.joaocosta.minart.geometry.*

/** A procedurally generated infinite surface.
  *
  * Can be clipped to create a surface.
  */
trait Plane extends Function2[Int, Int, Color] { outer =>

  /** Returns the color at position (x, y). */
  def apply(x: Int, y: Int): Color = getPixel(x, y)

  /** Returns the color at position (x, y). */
  def getPixel(x: Int, y: Int): Color

  /** Maps the colors from this plane. */
  final def map(f: Color => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = f(outer.getPixel(x, y))
  }

  /** Flatmaps this plane */
  final def flatMap(f: Color => Plane): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = f(outer.getPixel(x, y)).getPixel(x, y)
  }

  /** Contramaps the positions from this plane. */
  final def contramap(f: (Int, Int) => (Int, Int)): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = {
      val res = f(x, y)
      outer.getPixel(res._1, res._2)
    }
  }

  /** Combines this plane with another by combining their colors with the given function. */
  final def zipWith(that: Plane, f: (Color, Color) => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = {
      val c1 = outer.getPixel(x, y)
      val c2 = that.getPixel(x, y)
      f(c1, c2)
    }
  }

  /** Combines this plane with a surface by combining their colors with the given function. */
  final def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView = SurfaceView.PlaneSurfaceView(
    new Plane {
      def getPixel(x: Int, y: Int): Color = {
        val c1 = outer.getPixel(x, y)
        if (x >= 0 && y >= 0 && x < that.width && y < that.height) {
          val c2 = that.unsafeGetPixel(x, y)
          f(c1, c2)
        } else c1
      }
    },
    width = that.width,
    height = that.height
  )

  /** Coflatmaps this plane with a Plane => Color function.
    * Effectively, each pixel of the new plane is computed from a translated plane, which can be used to
    * implement convolutions.
    */
  final def coflatMap(f: Plane => Color): Plane =
    new Plane {
      def getPixel(x: Int, y: Int): Color =
        f((dx: Int, dy: Int) => outer.getPixel(x + dx, y + dy))
    }

  /** Coflatmaps this plane with a convolution kernel.
    */
  final def coflatMap(kernel: Kernel): Plane =
    new Plane {
      def getPixel(x: Int, y: Int): Color =
        kernel.apply((dx: Int, dy: Int) => outer.getPixel(x + dx, y + dy))
    }

  /** Clips this plane to a chosen rectangle
    *
    * @param cx leftmost pixel on the surface
    * @param cy topmost pixel on the surface
    * @param cw clip width
    * @param ch clip height
    */
  def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    if (cx == 0 && cy == 0) toSurfaceView(cw, ch)
    else
      new Plane {
        def getPixel(x: Int, y: Int): Color = {
          outer.getPixel(x + cx, y + cy)
        }
      }.toSurfaceView(cw, ch)

  /** Clips this plane to a chosen rectangle
    *
    * @param region chosen region
    */
  def clip(region: AxisAlignedBoundingBox): SurfaceView = clip(region.x, region.y, region.width, region.height)

  /** Overlays a surface on top of this plane.
    *
    * Similar to MutableSurface#blit, but for surface views and planes.
    *
    * @param that surface to overlay
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination plane
    * @param y topmost pixel on the destination plane
    */
  final def overlay(that: Surface, blendMode: BlendMode = BlendMode.Copy)(x: Int, y: Int): Plane =
    new Plane {
      def getPixel(dx: Int, dy: Int): Color = {
        if (dx >= x && dx < x + that.width && dy >= y && dy < y + that.height)
          blendMode.blend(that.unsafeGetPixel(dx - x, dy - y), outer.getPixel(dx, dy))
        else
          outer.getPixel(dx, dy)
      }
    }

  /** Overlays a shape on top of this plane.
    *
    * Similar to MutableSurface#rasterize, but for shapes and planes.
    *
    * This API is *experimental* and might change in the near future.
    *
    * @param that shape to overlay
    * @param frontfaceColor color of the front face
    * @param backfaceColor color of the back face
    * @param blendMode blend strategy to use
    * @param x leftmost pixel on the destination plane
    * @param y topmost pixel on the destination plane
    */
  final def overlayShape(
      that: Shape,
      frontfaceColor: Option[Color],
      backfaceColor: Option[Color] = None,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int): Plane =
    if (
      that.knownFace.isDefined && ((frontfaceColor.isEmpty && that.knownFace == Some(Shape.Face.Front)) ||
        (backfaceColor.isEmpty && that.knownFace == Some(Shape.Face.Back)))
    ) this
    else
      val finalShape = that.translate(x, y)
      new Plane {
        def getPixel(dx: Int, dy: Int): Color = {
          if (finalShape.aabb.contains(dx, dy))
            finalShape
              .faceAt(dx, dy)
              .flatMap {
                case Shape.Face.Front => frontfaceColor
                case Shape.Face.Back  => backfaceColor
              }
              .fold(outer.getPixel(dx, dy)) { color =>
                blendMode.blend(color, outer.getPixel(dx, dy))
              }
          else
            outer.getPixel(dx, dy)
        }
      }

  /** Inverts a plane color. */
  final def invertColor: Plane = map(_.invert)

  /** Contramaps this plane using a matrix instead of a function.
    *
    * This method can be chained multiple times efficiently.
    *
    * Note that this is *contramapping*. The operation is applied as
    * [a b c] [dx] = [sx]
    * [d e f] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Where (sx,sy) are the positions in the original plane and (dx, dy) are the positions in the new plane.
    *
    * This means that you need to invert the transformations to use the common transformation matrices.
    *
    * For example, the matrix Matrix.scaling(2, 2):
    *
    * [2 0 0] [dx] = [sx]
    * [0 2 0] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Will *scale down* the image, not scale up.
    */
  def contramapMatrix(matrix: Matrix) =
    if (matrix == Matrix.identity) this
    else Plane.MatrixPlane(matrix, this)

  /** Translates a plane. */
  final def translate(dx: Double, dy: Double): Plane =
    contramapMatrix(Matrix.translation(-dx, -dy))

  /** Flips a plane horizontally. */
  final def flipH: Plane = contramapMatrix(Matrix.flipH)

  /** Flips a plane vertically. */
  final def flipV: Plane = contramapMatrix(Matrix.flipV)

  /** Scales a plane. */
  final def scale(sx: Double, sy: Double): Plane =
    contramapMatrix(Matrix.scaling(1.0 / sx, 1.0 / sy))

  /** Scales a plane. */
  final def scale(s: Double): Plane = scale(s, s)

  /** Rotates a plane by a certain angle (clockwise). */
  final def rotate(theta: Double): Plane =
    contramapMatrix(Matrix.rotation(-theta))

  /** Shears a plane. */
  final def shear(sx: Double, sy: Double): Plane =
    contramapMatrix(Matrix.shear(-sx, -sy))

  /** Transposes a plane (switches the x and y coordinates). */
  final def transpose: Plane = contramapMatrix(Matrix.transpose)

  /** Converts this plane to a surface view, assuming (0, 0) as the top-left corner.
    *
    * @param width surface view width
    * @param height surface view height
    */
  final def toSurfaceView(width: Int, height: Int): SurfaceView =
    SurfaceView.PlaneSurfaceView(this, width, height)

  /** Converts this plane to a RAM surface, assuming (0, 0) as the top-left corner.
    *
    * @param width surface width
    * @param height surface height
    */
  final def toRamSurface(width: Int, height: Int): RamSurface =
    toSurfaceView(width, height).toRamSurface()
}

object Plane {
  private[Plane] final case class MatrixPlane(invMatrix: Matrix, plane: Plane) extends Plane {
    def getPixel(x: Int, y: Int): Color = {
      plane.getPixel(invMatrix.applyX(x, y).toInt, invMatrix.applyY(x, y).toInt)
    }

    override def contramapMatrix(matrix: Matrix) =
      if (matrix == Matrix.identity) this
      else MatrixPlane(this.invMatrix.multiply(matrix), plane)

    override def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
      translate(-cx, -cy).toSurfaceView(cw, ch)
  }

  /** Creates a plane from a constant color.
    *
    * @param constant constant color
    */
  def fromConstant(color: Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = color
  }

  /** Creates a plane from a generator function.
    *
    * @param generator generator function from (x, y) to a color
    */
  def fromFunction(generator: (Int, Int) => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = {
      generator(x, y)
    }
  }

  /** Creates a plane from a surface, filling the remaining pixels with a fallback color.
    * @param surface reference surface
    * @param fallback fallback color
    */
  def fromSurfaceWithFallback(surface: Surface, fallback: Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = {
      surface.getPixelOrElse(x, y, fallback)
    }
  }

  /** Creates a plane from a surface, by repeating that surface accross both axis.
    * @param surface reference surface
    */
  @deprecated("Use surface.view.repeating")
  def fromSurfaceWithRepetition(surface: Surface): Plane =
    surface.view.repeating
}
