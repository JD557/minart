package eu.joaocosta.minart.graphics

import scala.annotation.nowarn

/** Representation of a RGBA Color optimized for mixing.
  *  All operations:
  *  - Have SWAR optimizations
  *  - Require an explicit underflow/overflow behavior
  *  - Handle all channels the same way
  */

opaque type LongColor = Long

object LongColor {
  private final val mask: Long         = 0x00ff_00ff_00ff_00ffL
  private final val aMask: Long        = 0x00ff_0000_0000_0000L
  private final val rMask: Long        = 0x0000_00ff_0000_0000L
  private final val gMask: Long        = 0x0000_0000_00ff_0000L
  private final val bMask: Long        = 0x0000_0000_0000_00ffL
  private final val aaMask: Long       = 0xffff_0000_0000_0000L
  private final val rrMask: Long       = 0x0000_ffff_0000_0000L
  private final val ggMask: Long       = 0x0000_0000_ffff_0000L
  private final val bbMask: Long       = 0x0000_0000_0000_ffffL
  private final val rgbMask: Long      = 0x0000_00ff_00ff_00ffL
  private final val overflowMask: Long = 0x0100_0100_0100_0100L

  extension (color: LongColor) {

    /** The alpha channel value. */
    inline def a: Long = (color >> 48) & 0x000000ff

    /** The red channel value. */
    inline def r: Long = (color >> 32) & 0x000000ff

    /** The green channel value. */
    inline def g: Long = (color >> 16) & 0x000000ff

    /** The green channel value. */
    inline def b: Long = (color & 0x000000ff)

    inline def toColor: Color = Color(r.toInt, g.toInt, b.toInt, a.toInt)
  }

  /** Sums two colors.
    * Values are clamped on overflow.
    */
  inline def sumClamp(c1: LongColor, c2: LongColor): LongColor = {
    val res      = c1 + c2
    val overflow = ((res & overflowMask) >> 8) * 255
    (res | overflow) & mask
  }

  /** Sums two colors.
    * Values are wrapped around on overflow.
    */
  inline def sumWrapAround(c1: LongColor, c2: LongColor): LongColor = {
    val res = (c1: Long) + (c2: Long)
    res & mask
  }

  /** Multiplies all components by a weight from 0 to 255.
    *  The behavior is undefined for values outside of that range.
    */
  inline def weight(c: LongColor, w: Byte): LongColor = {
    val ww = java.lang.Byte.toUnsignedLong(w)
    val cc = c * ww
    (java.lang.Long.divideUnsigned(cc & aaMask, 255) & aMask) |
      (((cc & rrMask) / 255) & rMask) |
      (((cc & ggMask) / 255) & gMask) |
      (((cc & bbMask) / 255) & bMask)
  }

  /** Creates a new color from RGB values (on the [0-255] range).
    *  Overflow/Underflow will wrap around.
    */
  def apply(r: Long, g: Long, b: Long): LongColor =
    (255L << 48) | ((r & 255) << 32) | ((g & 255) << 16) | (b & 255)

  /** Creates a new color from RGBA values (on the [0-255] range).
    *  Overflow/Underflow will wrap around.
    */
  def apply(r: Long, g: Long, b: Long, a: Long): LongColor =
    (a << 48) | ((r & 255) << 32) | ((g & 255) << 16) | (b & 255)

  def apply(color: Color): LongColor = LongColor(color.r, color.g, color.b, color.a)
}
