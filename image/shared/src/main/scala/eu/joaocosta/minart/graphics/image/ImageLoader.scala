package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics._

trait ImageLoader {
  type ParseResult[T] = Either[String, (T, LazyList[Int])]

  def loadImage(is: InputStream): Either[String, RamSurface]
}
