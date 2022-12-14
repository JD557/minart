package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayOutputStream, OutputStream}

import scala.util.Try

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime.Resource

/** Image writer with a low-level implementation on how to store an image.
  */
trait ImageWriter {

  /** Stores a surface to an OutputStream.
    *
    * @param surface Surface to store
    * @param os OutputStream where to store the data
    * @return Either unit or an error string
    */
  def storeImage(surface: Surface, os: OutputStream): Either[String, Unit]

  /** Stores a surface to a Resource.
    *
    * @param surface Surface to store
    * @param resource Resource where to store the data
    * @return Either unit or an error string, inside a Try capturing the IO exceptions
    */
  def storeImage(surface: Surface, resource: Resource): Try[Either[String, Unit]] =
    resource.withOutputStream(os => storeImage(surface, os))

  /** Returns the image data as a byte array.
    *
    * @param surface Surface to convert
    * @return Either an array with the image data or an error string
    */
  def toByteArray(surface: Surface): Either[String, Array[Byte]] = {
    val os = new ByteArrayOutputStream()
    storeImage(surface, os).map(_ => os.toByteArray)
  }
}
