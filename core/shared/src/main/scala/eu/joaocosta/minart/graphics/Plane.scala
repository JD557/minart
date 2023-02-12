package eu.joaocosta.minart.graphics

/* A procedurally generated infinite surface.
 *
 * Can be clipped to create a surface.
 */
trait Plane extends Function2[Int, Int, Color] { outer =>
  def apply(x: Int, y: Int): Color = getPixel(x, y)
  def getPixel(x: Int, y: Int): Color

  /** Maps the colors from this plane. */
  final def map(f: Color => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = f(outer.getPixel(x, y))
  }

  /** Flatmaps this plane */
  final def flatMap(f: Color => (Int, Int) => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = f(outer.getPixel(x, y)).apply(x, y)
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
  final def zipWith(that: Surface, f: (Color, Color) => Color): SurfaceView = SurfaceView(
    new Plane {
      def getPixel(x: Int, y: Int): Color = {
        val c1 = outer.getPixel(x, y)
        that.getPixel(x, y).fold(c1)(c2 => f(c1, c2))
      }
    },
    width = that.width,
    height = that.height
  )

  final def clip(cx: Int, cy: Int, cw: Int, ch: Int): SurfaceView =
    if (cx == 0 && cy == 0) toSurfaceView(cw, ch)
    else
      new Plane {
        def getPixel(x: Int, y: Int): Color = {
          outer.getPixel(x + cx, y + cy)
        }
      }.toSurfaceView(cw, ch)

  /** Inverts a plane color. */
  def invertColor: Plane = map(_.invert)

  /** Contramaps this plane using a matrix instead of a function.
    *
    *  This method can be chained multiple times efficiently.
    *
    * Note that this is *contramaping*. The operation is applied as
    * [a b c] [dx] = [sx]
    * [d e f] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Where (sx,sy) are the positions in the original plane and (dx, dy) are the positions in the new plane.
    *
    * This means that you need to invert the transformations to use the common transformation matrices.
    *
    * For example, the matrix:
    *
    * [2 0 0] [dx] = [sx]
    * [0 2 0] [dy]   [sy]
    * [0 0 1] [ 1]   [ 1]
    *
    * Will *scale down* the image, not scale up.
    */
  def contramapMatrix(matrix: Matrix) =
    Plane.MatrixPlane(matrix, this)

  /** Translates a plane. */
  def translate(dx: Double, dy: Double): Plane = contramapMatrix(Matrix(1, 0, -dx, 0, 1, -dy))

  /** Flips a plane horizontally. */
  def flipH: Plane = contramapMatrix(Matrix(-1, 0, 0, 0, 1, 0))

  /** Flips a plane vertically. */
  def flipV: Plane = contramapMatrix(Matrix(1, 0, 0, 0, -1, 0))

  /** Scales a plane. */
  def scale(sx: Double, sy: Double): Plane = contramapMatrix(Matrix(1.0 / sx, 0, 0, 0, 1.0 / sy, 0))

  /** Scales a plane. */
  def scale(s: Double): Plane = scale(s, s)

  /** Rotates a plane. */
  def rotate(theta: Double): Plane = {
    val ct = math.cos(theta)
    val st = math.sin(theta)
    contramapMatrix(Matrix(ct, -st, 0, st, ct, 0))
  }

  /** Shears a plane. */
  def shear(sx: Double, sy: Double): Plane = contramapMatrix(Matrix(1.0, -sx, 0, -sy, 1.0, 0))

  /** Transposes a plane. */
  def transpose: Plane = contramapMatrix(Matrix(0, 1, 0, 1, 0, 1))

  /** Converts this plane to a surface view, assuming (0, 0) as the top-left corner
    *
    * @param width surface view width
    * @param height surface view height
    */
  final def toSurfaceView(width: Int, height: Int): SurfaceView =
    SurfaceView(this, width, height)

  /** Converts this plane to a RAM surface, assuming (0, 0) as the top-left corner
    *
    * @param width surface width
    * @param height surface height
    */
  final def toRamSurface(width: Int, height: Int): RamSurface =
    toSurfaceView(width, height).toRamSurface()
}

object Plane {
  private val defaultColor: Color = Color(0, 0, 0) // Fallback color used for safety
  private def floorMod(x: Int, y: Int): Int = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }
  private[Plane] final case class MatrixPlane(matrix: Matrix, plane: Plane) extends Plane {
    def getPixel(x: Int, y: Int): Color = {
      val (xx, yy) = matrix(x, y)
      plane.getPixel(xx, yy)
    }

    override def contramapMatrix(matrix: Matrix) =
      MatrixPlane(this.matrix.multiply(matrix), plane)
  }

  /** Creates a plane from a constant color
    *
    * @param constant constant color
    */
  def fromConstant(color: Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = color
  }

  /** Creates a plane from a generator function
    *
    * @param generator generator function
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
      surface.getPixel(x, y).getOrElse(fallback)
    }
  }

  /** Creates a plane from a surface, by repeating that surface accross both axis.
    * @param surface reference surface
    */
  def fromSurfaceWithRepetition(surface: Surface): Plane =
    if (surface.width <= 0 || surface.height <= 0)
      new Plane {
        def getPixel(x: Int, y: Int): Color = defaultColor
      }
    else
      new Plane {
        def getPixel(x: Int, y: Int): Color = {
          surface
            .getPixel(
              floorMod(x, surface.width),
              floorMod(y, surface.height)
            )
            .getOrElse(defaultColor)
        }
      }
}
