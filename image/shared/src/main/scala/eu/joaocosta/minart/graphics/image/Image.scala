package eu.joaocosta.minart.graphics.image

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime.Resource

object Image {
  def invert(surface: RamSurface): RamSurface =
    new RamSurface(surface.data.map(_.map(_.invert)))

  def flipH(surface: RamSurface): RamSurface =
    new RamSurface(surface.data.map(_.reverse))

  def flipV(surface: RamSurface): RamSurface =
    new RamSurface(surface.data.reverse)

  def transpose(surface: RamSurface): RamSurface =
    new RamSurface(surface.data.transpose.map(_.toArray))

  def loadPpmImage(resource: Resource): Try[RamSurface] = {
    resource
      .withInputStream { inputStream =>
        PpmImageLoader.loadImage(inputStream)
      }
      .flatMap {
        case Left(error)   => Failure(new Exception(error))
        case Right(result) => Success(result)
      }
  }

  def loadBmpImage(resource: Resource): Try[RamSurface] = {
    resource
      .withInputStream { inputStream =>
        BmpImageLoader.loadImage(inputStream)
      }
      .flatMap {
        case Left(error)   => Failure(new Exception(error))
        case Right(result) => Success(result)
      }
  }
}
