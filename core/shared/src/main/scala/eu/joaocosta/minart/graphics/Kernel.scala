package eu.joaocosta.minart.graphics

/** A kernel used for convolution.
  * It simplifies the usage of Plane#coflatMap in a performant way.
  */
sealed trait Kernel {

  def width: Int

  def height: Int

  /** Convolution function to use.
    *
    * Use as `plane.coflatMap(kernel)`
    */
  def apply(getPixel: (Int, Int) => Color): Color
}

object Kernel {

  def apply(matrix: Seq[Seq[Int]] = Seq(Seq(1)), normalization: Int = 1): SingleKernel =
    new SingleKernel(IArray.from(matrix.iterator.map(IArray.from)), normalization)

  /** Identity kernel, does nothing.
    */
  val identity: SingleKernel = SingleKernel()

  /** Average blur kernel. Averages the pixels in a window.
    *
    *  Both the width and height must be odd.
    */
  def averageBlur(width: Int, height: Int): SingleKernel = {
    val line = IArray.fill(width)(1)
    SingleKernel(IArray.fill(height)(line), width * height)
  }

  /** Convolution kernel using a single matrix., where all color chanels are handled the same way.
    * The alpha channel is discarded (forced to 255).
    *
    * The center of the matrix maps to the target position. As such, all dimensions must be odd.
    *
    * For performance reasons, all entries are integers, but are then divided by normalization
    * constant.
    */
  final case class SingleKernel(matrix: IArray[IArray[Int]] = IArray(IArray(1)), normalization: Int = 1)
      extends Kernel {
    val width: Int  = matrix.headOption.map(_.size).getOrElse(0)
    val height: Int = matrix.size
    require(matrix.forall(_.size == width), "Invalid kernel, not all rows have the same size")
    require(width % 2 == 1 && height % 2 == 1, s"Invalid kernel, width ($width) and height ($height) must be odd")
    require(normalization != 0, "Invalid kernel normalization constant")

    private val minX = -width / 2
    private val minY = -height / 2

    /** Convolution function to use.
      *  All color channels are handled the same way, with the alpha channel being forced to 255.
      *
      * Use as `plane.coflatMap(kernel)`
      */
    def apply(getPixel: (Int, Int) => Color): Color = {
      var accR: Int = 0
      var accG: Int = 0
      var accB: Int = 0

      var dMY = 0
      while (dMY < height) {
        val dy  = minY + dMY
        var dMX = 0
        while (dMX < width) {
          val weight = matrix(dMY)(dMX)
          if (weight != 0) {
            val dx    = minX + dMX
            val color = getPixel(dx, dy)
            accR += weight * color.r
            accG += weight * color.g
            accB += weight * color.b
          }
          dMX += 1
        }
        dMY += 1
      }

      if (normalization != 1)
        Color((accR / normalization).toInt, (accG / normalization).toInt, (accB / normalization).toInt)
      else
        Color(accR, accG, accB)
    }

    /** Creates a kernel equivalent to this one, but resized (keeping the center unchanged).
      * The new width and height must be odd.
      *
      * Increases in size add a padding of 0s, while decreases just crop the values.
      * The normalization constant stays unchanged.
      */
    def resize(newWidth: Int, newHeight: Int): SingleKernel = {
      val dw = newWidth - width
      val dh = newHeight - height
      if (dw == 0 && dh == 0) {
        this
      } else {
        val lines = matrix.map(line =>
          if (dw > 0) {
            IArray.fill(dw / 2)(0) ++ line ++ IArray.fill(dw / 2)(0)
          } else if (dw < 0) {
            line.drop(dw / 2).dropRight(dw / 2)
          } else {
            line
          }
        )
        new SingleKernel(
          if (dh > 0) {
            val emptyLine = IArray.fill(newWidth)(0)
            IArray.fill(dh / 2)(emptyLine) ++ lines ++ IArray.fill(dh / 2)(emptyLine)
          } else if (dh < 0) lines.drop(dh / 2).dropRight(dh / 2)
          else lines,
          normalization
        )
      }
    }

    override def equals(that: Any): Boolean =
      super.equals(that) ||
        (that.isInstanceOf[SingleKernel] &&
          this.matrix.toVector.map(_.toVector) == that.asInstanceOf[SingleKernel].matrix.toVector.map(_.toVector) &&
          this.normalization == that.asInstanceOf[SingleKernel].normalization)

    override def toString(): String =
      s"SingleKernel(1 / ${normalization} * ${matrix.iterator.map(_.mkString("[", ",", "]")).mkString("[", ",", "]")})"

    override def hashCode(): Int =
      (this.matrix.toVector.map(_.toVector), this.normalization).hashCode()
  }

  /** Convolution kernel using a matrix per color channel.
    *
    * The center of the matrix maps to the target position. As such, all dimensions must be odd.
    *
    * For performance reasons, all entries are integers, but are then divided by normalization
    * constant.
    */
  final case class MultiKernel(
      kernelR: SingleKernel = Kernel.identity,
      kernelG: SingleKernel = Kernel.identity,
      kernelB: SingleKernel = Kernel.identity,
      kernelA: SingleKernel = Kernel.identity
  ) extends Kernel {
    val width: Int  = Iterator(kernelR, kernelG, kernelB, kernelA).map(_.width).max
    val height: Int = Iterator(kernelR, kernelG, kernelB, kernelA).map(_.height).max

    private val matrixR = kernelR.resize(width, height).matrix
    private val matrixG = kernelG.resize(width, height).matrix
    private val matrixB = kernelB.resize(width, height).matrix
    private val matrixA = kernelA.resize(width, height).matrix

    private val minX = -width / 2
    private val minY = -height / 2

    def apply(getPixel: (Int, Int) => Color): Color = {
      var accR: Int = 0
      var accG: Int = 0
      var accB: Int = 0
      var accA: Int = 0

      var dMY = 0
      while (dMY < height) {
        val dy  = minY + dMY
        var dMX = 0
        while (dMX < width) {
          val weightR = matrixR(dMY)(dMX)
          val weightG = matrixG(dMY)(dMX)
          val weightB = matrixB(dMY)(dMX)
          val weightA = matrixA(dMY)(dMX)
          if (weightR != 0 || weightG != 0 || weightB != 0 || weightA != 0) {
            val dx    = minX + dMX
            val color = getPixel(dx, dy)
            accR += weightR * color.r
            accG += weightG * color.g
            accB += weightB * color.b
            accA += weightA * color.a
          }
          dMX += 1
        }
        dMY += 1
      }

      Color(
        (accR / kernelR.normalization).toInt,
        (accG / kernelG.normalization).toInt,
        (accB / kernelB.normalization).toInt,
        (accA / kernelA.normalization).toInt
      )
    }
  }
}
