package eu.joaocosta.minart.backend

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.graphics.{Color, Surface}

/** Mutabe surface backed by an SDL surface.
  *
  * This class assumes to be the only owner of the surface, and will free the surface when garbage collected.
  */
final class SdlSurface(val data: Ptr[SDL_Surface]) extends Surface.MutableSurface with AutoCloseable {

  val width: Int       = data.w
  val height: Int      = data.h
  private val lines    = 0 until height
  private val columns  = 0 until width
  private val renderer = SDL_CreateSoftwareRenderer(data)

  def getPixel(x: Int, y: Int): Option[Color] =
    if (data.pixels != null && x >= 0 && y >= 0 && x < width && y < height) {
      // Assuming a BGRA surface
      val baseAddr =
        4 * (y * width + x)
      Some(
        Color(
          (data.pixels(baseAddr + 2) & 0xff),
          (data.pixels(baseAddr + 1) & 0xff),
          (data.pixels(baseAddr + 0) & 0xff)
        )
      )
    } else None

  def getPixels(): Vector[Array[Color]] = {
    lines.map { y =>
      val lineBase = y * width
      columns.map { x =>
        val baseAddr = 4 * (lineBase + x)
        Color(
          (data.pixels(baseAddr + 2) & 0xff),
          (data.pixels(baseAddr + 1) & 0xff),
          (data.pixels(baseAddr + 0) & 0xff)
        )
      }.toArray
    }.toVector
  }

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (data.pixels != null && x >= 0 && y >= 0 && x < width && y < height) {
      // Assuming a BGRA surface
      val lineBase = y * width
      val baseAddr = 4 * (lineBase + x)
      data.pixels(baseAddr + 0) = color.b.toByte
      data.pixels(baseAddr + 1) = color.g.toByte
      data.pixels(baseAddr + 2) = color.r.toByte
      data.pixels(baseAddr + 3) = 255.toByte
    }

  def fill(color: Color): Unit = {
    SDL_SetRenderDrawColor(renderer, color.r.toUByte, color.g.toUByte, color.b.toUByte, 0.toUByte)
    SDL_RenderClear(renderer)
  }

  override def blit(
      that: Surface
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = that match {
    case img: SdlSurface =>
      val srcRect = stackalloc[SDL_Rect]().init(cx, cy, cw, ch)
      val dstRect = stackalloc[SDL_Rect]().init(x, y, cw, ch)
      SDL_UpperBlit(img.data, srcRect, this.data, dstRect)
    case _ =>
      super.blit(that)(x, y, cx, cy, cw, ch)
  }

  def close(): Unit = {
    SDL_FreeSurface(data)
  }
}
