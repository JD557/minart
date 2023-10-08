package eu.joaocosta.minart.graphics.image

import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.runtime.Resource

/** Object containing user-friendly functions to images. */
object Image {

  /** Loads an image using a custom ImageReader.
    *
    * @param loader ImageReader to use
    * @param resource Resource pointing to the image
    */
  def loadImage(loader: ImageReader, resource: Resource): Try[RamSurface] = {
    loader.loadImage(resource).flatMap {
      case Left(error)   => Failure(new Exception(error))
      case Right(result) => Success(result)
    }
  }

  /** Loads an image in the PPM format.
    */
  def loadPpmImage(resource: Resource): Try[RamSurface] =
    loadImage(ppm.PpmImageFormat.defaultFormat, resource)

  /** Loads an image in the BMP format.
    */
  def loadBmpImage(resource: Resource): Try[RamSurface] =
    loadImage(bmp.BmpImageFormat.defaultFormat, resource)

  /** Loads an image in the QOI format.
    */
  def loadQoiImage(resource: Resource): Try[RamSurface] =
    loadImage(qoi.QoiImageFormat.defaultFormat, resource)

  /** Stores an image using a custom ImageWriter.
    *
    * @param writer ImageWriter to use
    * @param surface Surface to store
    * @param resource Resource pointing to the output destination
    */
  def storeImage(writer: ImageWriter, surface: Surface, resource: Resource): Try[Unit] = {
    writer.storeImage(surface, resource).flatMap {
      case Left(error)   => Failure(new Exception(error))
      case Right(result) => Success(result)
    }
  }

  /** Stores an image in the PPM format.
    */
  def storePpmImage(surface: Surface, resource: Resource): Try[Unit] =
    storeImage(ppm.PpmImageFormat.defaultFormat, surface, resource)

  /** Stores an image in the BMP format.
    */
  def storeBmpImage(surface: Surface, resource: Resource): Try[Unit] =
    storeImage(bmp.BmpImageFormat.defaultFormat, surface, resource)

  /** Stores an image in the QOI format.
    */
  def storeQoiImage(surface: Surface, resource: Resource): Try[Unit] =
    storeImage(qoi.QoiImageFormat.defaultFormat, surface, resource)

}
