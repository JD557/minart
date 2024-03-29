package eu.joaocosta.minart.backend

import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_BlendMode.*

import eu.joaocosta.minart.graphics.{BlendMode, Color, MutableSurface, Surface}

/** Mutabe surface backed by an SDL surface.
  *
  * This class assumes to be the only owner of the surface, and will free the surface when garbage collected.
  */
final class SdlSurface(val data: Ptr[SDL_Surface]) extends MutableSurface {

  val width: Int       = (!data).w
  val height: Int      = (!data).h
  private val renderer = SDL_CreateSoftwareRenderer(data)
  SDL_SetRenderDrawBlendMode(renderer, SDL_BLENDMODE_NONE)
  private val dataBuffer = (!data).pixels.asInstanceOf[Ptr[Int]]

  def unsafeGetPixel(x: Int, y: Int): Color = {
    Color.fromARGB(dataBuffer(y * width + x))
  }

  def unsafePutPixel(x: Int, y: Int, color: Color): Unit = dataBuffer(y * width + x) = color.argb

  override def fill(color: Color): Unit = {
    SDL_SetRenderDrawColor(renderer, color.r.toUByte, color.g.toUByte, color.b.toUByte, color.a.toUByte)
    SDL_RenderClear(renderer)
  }

  def fillRegion(x: Int, y: Int, w: Int, h: Int, color: Color): Unit = {
    val x1 = Math.max(x, 0)
    val y1 = Math.max(y, 0)
    val x2 = Math.min(x + w, width)
    val y2 = Math.min(y + h, height)
    if (x1 != x2 && y1 != y2) {
      val _w = x2 - x1
      val _h = y2 - y1
      SDL_SetRenderDrawColor(renderer, color.r.toUByte, color.g.toUByte, color.b.toUByte, color.a.toUByte)
      val rect = stackalloc[SDL_Rect]()
      (!rect).x = x1
      (!rect).y = y1
      (!rect).w = _w
      (!rect).h = _h
      SDL_RenderFillRect(renderer, rect)
    }
  }

  override def blit(
      that: Surface,
      blendMode: BlendMode = BlendMode.Copy
  )(x: Int, y: Int, cx: Int = 0, cy: Int = 0, cw: Int = that.width, ch: Int = that.height): Unit =
    (that, blendMode) match {
      case (img: SdlSurface, BlendMode.Copy) =>
        val srcRect = stackalloc[SDL_Rect]()
        (!srcRect).x = cx
        (!srcRect).y = cy
        (!srcRect).w = cw
        (!srcRect).h = ch
        val dstRect = stackalloc[SDL_Rect]()
        (!dstRect).x = x
        (!dstRect).y = y
        (!dstRect).w = cw
        (!dstRect).h = ch
        SDL_UpperBlit(img.data, srcRect, this.data, dstRect)
      case _ =>
        super.blit(that, blendMode)(x, y, cx, cy, cw, ch)
    }

  /** Cleans up the internal datastructures used by this surface.
    *
    *  Note that the underlying data is not freed by this method.
    */
  def cleanup(): Unit = {
    SDL_DestroyRenderer(renderer)
  }
}

object SdlSurface {
  inline def withRawData[T](data: Ptr[SDL_Surface])(inline f: SdlSurface => T): T = {
    val tempSurface = new SdlSurface(data)
    val ret         = f(tempSurface)
    tempSurface.cleanup()
    ret
  }
}
