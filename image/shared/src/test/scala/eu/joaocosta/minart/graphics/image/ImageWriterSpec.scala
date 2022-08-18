package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageWriterSpec extends BasicTestSuite {

  def roundtripTest(baseResource: Resource, imageFormat: ImageLoader with ImageWriter) = {
    val (oldPixels, newPixels) = (for {
      original <- imageFormat.loadImage(baseResource).get
      originalPixels = original.getPixels().map(_.toVector)
      stored <- imageFormat.toByteArray(original)
      loaded <- imageFormat.fromByteArray(stored)
      loadedPixels = loaded.getPixels().map(_.toVector)
    } yield (originalPixels, loadedPixels)).toOption.get

    assert(oldPixels == newPixels)
  }

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Write a PPM image") {
      roundtripTest(Resource("scala.ppm"), ppm.PpmImageFormat.defaultFormat)
    }

    test("Write a BMP image") {
      roundtripTest(Resource("scala.bmp"), bmp.BmpImageFormat.defaultFormat)
    }
  }
}
