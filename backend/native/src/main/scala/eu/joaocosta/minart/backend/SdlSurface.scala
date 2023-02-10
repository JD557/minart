package eu.joaocosta.minart.backend

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.graphics.{Color, MutableSurface, Surface}

/** Mutabe surface backed by an SDL surface.
  *
  * This class assumes to be the only owner of the surface, and will free the surface when garbage collected.
  */
final class SdlSurface(val data: Ptr[SDL_Surface]) extends MutableSurface with AutoCloseable {

  val width: Int         = data.w
  val height: Int        = data.h
  private val renderer   = SDL_CreateSoftwareRenderer(data)
  private val dataBuffer = data.pixels.asInstanceOf[Ptr[Int]]

  def unsafeGetPixel(x: Int, y: Int): Color = {
    Color.fromRGB(dataBuffer(y * width + x))
  }

  def putPixel(x: Int, y: Int, color: Color): Unit =
    if (data.pixels != null && x >= 0 && y >= 0 && x < width && y < height) {
      dataBuffer(y * width + x) = color.argb
    }

  override def fill(color: Color): Unit = {
    SDL_SetRenderDrawColor(renderer, color.r.toUByte, color.g.toUByte, color.b.toUByte, 0.toUByte)
    SDL_RenderClear(renderer)
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    SDL_SetRenderDrawColor(renderer, color.r.toUByte, color.g.toUByte, color.b.toUByte, 0.toUByte)
    val rect = stackalloc[SDL_Rect]().init(x, y, w, h)
    SDL_RenderFillRect(renderer, rect)
  }

  override def blit(
      that: Surface,
      mask: Option[Color] = None
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit = that match {
    case img: SdlSurface if mask.isEmpty =>
      val srcRect = stackalloc[SDL_Rect]().init(cx, cy, cw, ch)
      val dstRect = stackalloc[SDL_Rect]().init(x, y, cw, ch)
      SDL_UpperBlit(img.data, srcRect, this.data, dstRect)
    case _ =>
      super.blit(that, mask)(x, y, cx, cy, cw, ch)
  }

  def close(): Unit = {
    SDL_FreeSurface(data)
  }
}
