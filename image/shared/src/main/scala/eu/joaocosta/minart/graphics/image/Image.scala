package eu.joaocosta.minart.graphics.image

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime.Resource

/** Object containing user-friendly functions to load and manipulate images.
  */
object Image {

  /** Inverts an image color.
    */
  def invert(surface: Surface): RamSurface =
    new RamSurface(surface.getPixels().map(_.map(_.invert)))

  /** Flips an image horizontally.
    */
  def flipH(surface: Surface): RamSurface =
    new RamSurface(surface.getPixels().map(_.reverse))

  /** Flips an image vertically.
    */
  def flipV(surface: Surface): RamSurface =
    new RamSurface(surface.getPixels().reverse)

  /** Transposes an image.
    */
  def transpose(surface: Surface): RamSurface =
    new RamSurface(surface.getPixels().transpose.map(_.toArray))

  /** Loads an image using a custom ImageLoader.
    *
    * @param loader ImageLoader to use
    * @param resource Resource pointing to the image
    */
  def loadImage(loader: ImageLoader, resource: Resource): Try[RamSurface] = {
    resource
      .withInputStream { inputStream =>
        loader.loadImage(inputStream)
      }
      .flatMap {
        case Left(error)   => Failure(new Exception(error))
        case Right(result) => Success(result)
      }
  }

  /** Loads an image in the PPM format.
    */
  def loadPpmImage(resource: Resource): Try[RamSurface] =
    loadImage(PpmImageLoader, resource)

  /** Loads an image in the BMP format.
    */
  def loadBmpImage(resource: Resource): Try[RamSurface] =
    loadImage(BmpImageLoader, resource)

  /** Loads an image in the QOI format.
    */
  def loadQoiImage(resource: Resource): Try[RamSurface] =
    loadImage(QoiImageLoader, resource)
}
