package eu.joaocosta.minart.graphics

/* A procedurally generated infinite surface.
 *
 * Can be clipped to create a surface.
 */
trait Plane { outer =>
  def getPixel(x: Int, y: Int): Color

  /** Maps the colors from this plane. */
  final def map(f: Color => Color): Plane = new Plane {
    def getPixel(x: Int, y: Int): Color = f(outer.getPixel(x, y))
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

  /** Transposes a surface. */
  def transpose: Plane = contramap((x, y) => (y, x))

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
