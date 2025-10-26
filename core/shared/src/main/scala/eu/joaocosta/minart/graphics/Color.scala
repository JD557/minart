package eu.joaocosta.minart.graphics

import scala.annotation.nowarn

/** Representation of a RGB Color. */
opaque type Color = Int

object Color {
  extension (color: Color) {

    /** The alpha channel value. */
    inline def a: Int = (color >> 24) & 0x000000ff

    /** The red channel value. */
    inline def r: Int = (color >> 16) & 0x000000ff

    /** The green channel value. */
    inline def g: Int = (color >> 8) & 0x000000ff

    /** The green channel value. */
    inline def b: Int = (color & 0x000000ff)

    /** This color packed as a 24 bit integer in RGB (with the first byte set to 0). */
    inline def rgb: Int = (color & 0x00ffffff)

    /** this color packed as a 32 bit integer in ARGB. */
    inline def argb: Int = color

    /** This color packed as a 32 bit integer in ABGR. */
    inline def abgr: Int =
      (color & 0xff00ff00) | (b << 16) | r

    /** Combines this with another color by summing each RGB value.
      * Values are clamped on overflow.
      *
      * The resulting alpha is set to 255.
      */
    def +(that: Color): Color =
      Color(
        Math.min(color.r + that.r, 255).toInt,
        Math.min(color.g + that.g, 255).toInt,
        Math.min(color.b + that.b, 255).toInt
      )

    /** Combines this with another color by summing each RGB value.
      * Values are clamped on overflow.
      *
      * The alpha of the left-side argument is kept.
      */
    def :+(that: Color): Color =
      Color(
        Math.min(color.r + that.r, 255).toInt,
        Math.min(color.g + that.g, 255).toInt,
        Math.min(color.b + that.b, 255).toInt,
        color.a
      )

    /** Combines this with another color by summing each RGB value.
      * Values are clamped on overflow.
      *
      * The alpha of the right-side argument is kept.
      */
    def +:(that: Color): Color =
      Color(
        Math.min(color.r + that.r, 255).toInt,
        Math.min(color.g + that.g, 255).toInt,
        Math.min(color.b + that.b, 255).toInt,
        that.a
      )

    /** Combines this with another color by subtracting each RGB value.
      * Values are clamped on underflow.
      *
      * The resulting alpha is set to 255.
      */
    def -(that: Color): Color =
      Color(
        Math.max(color.r - that.r, 0).toInt,
        Math.max(color.g - that.g, 0).toInt,
        Math.max(color.b - that.b, 0).toInt
      )

    /** Combines this with another color by subtracting each RGB value.
      * Values are clamped on underflow.
      *
      * The alpha of the left-side argument is kept.
      */
    def :-(that: Color): Color =
      Color(
        Math.max(color.r - that.r, 0).toInt,
        Math.max(color.g - that.g, 0).toInt,
        Math.max(color.b - that.b, 0).toInt,
        color.a
      )

    /** Combines this with another color by subtracting each RGB value.
      * Values are clamped on underflow.
      *
      * The alpha of the right-side argument is kept.
      */
    def -:(that: Color): Color =
      Color(
        Math.max(color.r - that.r, 0).toInt,
        Math.max(color.g - that.g, 0).toInt,
        Math.max(color.b - that.b, 0).toInt,
        that.a
      )

    /** Combines this with another color by multiplying each RGB value (on the [0.0, 1.0] range).
      * Values are clamped on overflow.
      *
      * The resulting alpha is set to 255.
      */
    def *(that: Color): Color =
      Color(
        (color.r * that.r) / 255,
        (color.g * that.g) / 255,
        (color.b * that.b) / 255
      )

    /** Combines this with another color by multiplying each RGB value (on the [0.0, 1.0] range).
      * Values are clamped on overflow.
      *
      * The alpha of the left-side argument is kept.
      */
    def :*(that: Color): Color =
      Color(
        (color.r * that.r) / 255,
        (color.g * that.g) / 255,
        (color.b * that.b) / 255,
        color.a
      )

    /** Combines this with another color by multiplying each RGB value (on the [0.0, 1.0] range).
      * Values are clamped on overflow.
      *
      * The alpha of the right-side argument is kept.
      */
    def *:(that: Color): Color =
      Color(
        (color.r * that.r) / 255,
        (color.g * that.g) / 255,
        (color.b * that.b) / 255,
        that.a
      )

    /** Inverts this color by inverting every RGB channel.
      *
      *  The alpha is preserved
      */
    def invert: Color = Color(255 - r, 255 - g, 255 - b, a)

    /** Multiplies all channels by the alpha.
      */
    def premultiplyAlpha: Color =
      color :* Color.grayscale(a)

    def copy(r: Int = color.r, g: Int = color.g, b: Int = color.b, a: Int = color.a) =
      Color(r, g, b, a)

    @nowarn
    def toString: String =
      if (a == 255) s"Color($r,$g,$b)"
      else s"Color($r,$g,$b,$a)"
  }

  /** Creates a new color from RGB values (on the [0-255] range).
    *  Overflow/Underflow will wrap around.
    */
  def apply(r: Int, g: Int, b: Int): Color =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  /** Creates a new color from RGBA values (on the [0-255] range).
    *  Overflow/Underflow will wrap around.
    */
  def apply(r: Int, g: Int, b: Int, a: Int): Color =
    (a << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  /** Creates a new color from RGB values (assuming unsinged bytes on the [0-255] range).
    */
  def apply(r: Byte, g: Byte, b: Byte): Color =
    (255 << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  /** Creates a new color from RGBA values (assuming unsinged bytes on the [0-255] range).
    */
  def apply(r: Byte, g: Byte, b: Byte, a: Byte): Color =
    ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255)

  /** Creates a new color from a grayscale value (on the [0-255] range).
    * Overflow/Underflow will wrap around.
    */
  def grayscale(gray: Int): Color =
    (255 << 24) | ((gray & 255) << 16) | ((gray & 255) << 8) | (gray & 255)

  /** Creates a new color from a grayscale value (assuming unsigned bytes on the [0-255] range).
    */
  def grayscale(gray: Byte): Color =
    (255 << 24) | ((gray & 255) << 16) | ((gray & 255) << 8) | (gray & 255)

  /** Creates a new color from a 24bit backed RGB integer.
    * Ignores the first byte of a 32bit number.
    */
  inline def fromRGB(rgb: Int): Color =
    0xff000000 | rgb

  /** Creates a new color from a 32bit backed ARGB integer.
    */
  inline def fromARGB(argb: Int): Color = argb

  /** Creates a new color from a 24bit backed BGR integer.
    * Ignores the first byte of a 32bit number.
    */
  inline def fromBGR(bgr: Int): Color =
    0xff000000 |
      ((bgr & 0x00ff0000) >> 16) |
      (bgr & 0x0000ff00) |
      ((bgr & 0x000000ff)) << 16

  /** Creates a new color from a 32bit backed ABGR integer.
    */
  inline def fromABGR(abgr: Int): Color =
    (abgr & 0xff000000) |
      ((abgr & 0x00ff0000) >> 16) |
      (abgr & 0x0000ff00) |
      ((abgr & 0x000000ff)) << 16

  /** Extracts the RGB channels of a color.
    */
  def unapply(color: Color): Some[(Int, Int, Int)] =
    Some(color.r, color.g, color.b)
}
