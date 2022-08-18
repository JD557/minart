package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageWriterSpec extends BasicTestSuite {

  def roundtripTest(baseResource: Resource, imageFormat: ImageLoader with ImageWriter) = {
    val originalImage = baseResource.withInputStream(is => imageFormat.loadImage(is).toOption.get).get
    val os            = new ByteArrayOutputStream()
    imageFormat.storeImage(originalImage, os)
    val is       = new ByteArrayInputStream(os.toByteArray)
    val newImage = imageFormat.loadImage(is).toOption.get
    assert(newImage.getPixels().map(_.toVector) == originalImage.getPixels().map(_.toVector))
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
