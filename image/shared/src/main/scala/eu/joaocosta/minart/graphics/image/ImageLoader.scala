package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import eu.joaocosta.minart.graphics._

/** Image loader with a low-level implementation on how to load an image.
  */
trait ImageLoader {

  /** Loads an image from an InputStream.
    *
    * @param is InputStream with the image data
    * @return Either a RamSurface with the image data or an error string
    */
  def loadImage(is: InputStream): Either[String, RamSurface]
}
