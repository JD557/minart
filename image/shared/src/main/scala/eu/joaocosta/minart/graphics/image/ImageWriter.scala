package eu.joaocosta.minart.graphics.image

import java.io.OutputStream

import eu.joaocosta.minart.graphics._

/** Image writer with a low-level implementation on how to store an image.
  */
trait ImageWriter {

  /** Stores a surface to an OutputStream.
    *
    * @param surface Surface to store
    * @param os OutputStream where to store the data
    */
  def storeImage(surface: Surface, os: OutputStream): Either[String, Unit]
}
