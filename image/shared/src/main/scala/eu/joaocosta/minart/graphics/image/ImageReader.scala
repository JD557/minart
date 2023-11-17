package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, InputStream}

import scala.util.Try

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.Resource

/** Image reader with a low-level implementation on how to load an image.
  */
trait ImageReader {

  /** Loads an image from an InputStream.
    *
    * @param is InputStream with the image data
    * @return Either a RamSurface with the image data or an error string
    */
  def loadImage(is: InputStream): Either[String, RamSurface]

  /** Loads an image from a Resource.
    *
    * @param resource Resource with the image data
    * @return Either a RamSurface with the image data or an error string, inside a Try capturing the IO exceptions
    */
  def loadImage(resource: Resource): Try[Either[String, RamSurface]] =
    resource.withInputStream(is => loadImage(is))

  /** Loads an image from a byte array.
    *
    * @param data Byte array
    * @return Either a RamSurface with the image data or an error string
    */
  def fromByteArray(data: Array[Byte]): Either[String, RamSurface] = {
    val is = new ByteArrayInputStream(data)
    loadImage(is)
  }
}
