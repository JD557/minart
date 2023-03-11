package eu.joaocosta.minart.graphics

/** Representation of a RGB Color.
  * @param argb this color packed as a 32 bit integer in ARGB.
  */
final class Color private (val argb: Int) {

  /** The red channel value. */
  @inline def r: Int = (argb & 0x00ff0000) >> 16

  /** The green channel value. */
  @inline def g: Int = (argb & 0x0000ff00) >> 8

  /** The green channel value. */
  @inline def b: Int = (argb & 0x000000ff)

  /** This color packed as a 32 bit integer in RGB (with the first byte set to 0). */
  @inline def rgb: Int = (argb & 0x00ffffff)

  /** This color packed as a 32 bit integer in ABGR. */
  @inline def abgr: Int =
    (argb & 0xff000000) |   // A
      (b << 16) |           // B
      (argb & 0x0000ff00) | // G
      r                     // R

  /** Combines this with another color by summing each RGB value.
    * Values are clamped on overflow.
    */
  def +(that: Color): Color =
    Color(
      math.min(this.r + that.r, 255).toInt,
      math.min(this.g + that.g, 255).toInt,
      math.min(this.b + that.b, 255).toInt
    )

  /** Combines this with another color by subtracting each RGB value.
    * Values are clamped on underflow.
    */
  def -(that: Color): Color =
    Color(
      math.max(this.r - that.r, 0).toInt,
      math.max(this.g - that.g, 0).toInt,
      math.max(this.b - that.b, 0).toInt
    )

  /** Combines this with another color by multiplying each RGB value (on the [0.0, 1.0] range).
    * Values are clamped on overflow.
    */
  def *(that: Color): Color =
    Color(
      (this.r * that.r) / 255,
      (this.g * that.g) / 255,
      (this.b * that.b) / 255
    )

  /** Inverts this color by inverting every RGB channel.
    */
  def invert: Color = Color(255 - r, 255 - g, 255 - b)

  override def toString: String = s"Color($r,$g,$b)"
  override def hashCode(): Int  = argb.hashCode()
  override def equals(that: Any): Boolean =
    (that.isInstanceOf[Color] && this.argb == that.asInstanceOf[Color].argb)
}

object Color {

  /** Creates a new color from RGB values (on the [0-255] range).
    *  Overflow/Underflow will wrap around.
    */
  def apply(r: Int, g: Int, b: Int): Color =
    new Color((255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255))

  /** Creates a new color from RGB values (assuming unsinged bytes on the [0-255] range).
    */
  def apply(r: Byte, g: Byte, b: Byte): Color =
    new Color((255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255))

  /** Creates a new color from a grayscale value (on the [0-255] range).
    * Overflow/Underflow will wrap around.
    */
  def grayscale(gray: Int): Color =
    new Color((255 << 24) | ((gray & 255) << 16) | ((gray & 255) << 8) | (gray & 255))

  /** Creates a new color from a grayscale value (assuming unsigned bytes on the [0-255] range).
    */
  def grayscale(gray: Byte): Color =
    new Color((255 << 24) | ((gray & 255) << 16) | ((gray & 255) << 8) | (gray & 255))

  /** Creates a new color from a 24bit backed RGB integer.
    * Ignores the first byte of a 32bit number.
    */
  @inline def fromRGB(rgb: Int): Color =
    new Color(0xff000000 | rgb)

  /** Creates a new color from a 24bit backed BGR integer.
    * Ignores the first byte of a 32bit number.
    */
  @inline def fromBGR(bgr: Int): Color =
    new Color(
      0xff000000 |
        ((bgr & 0x00ff0000) >> 16) |
        (bgr & 0x0000ff00) |
        ((bgr & 0x000000ff)) << 16
    )

  /** Extracts the RGB channels of a color.
    */
  def unapply(color: Color): Some[(Int, Int, Int)] =
    Some(color.r, color.g, color.b)
}
