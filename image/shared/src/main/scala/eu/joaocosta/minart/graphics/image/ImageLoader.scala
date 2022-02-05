package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.Helpers._

trait ImageLoader {
  type ParseResult[T]   = Either[String, (LazyList[Int], T)]
  type ParseState[E, T] = State[LazyList[Int], E, T]

  def loadImage(is: InputStream): Either[String, RamSurface]
}
