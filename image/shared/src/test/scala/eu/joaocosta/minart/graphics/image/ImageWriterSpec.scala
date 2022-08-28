package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageWriterSpec extends BasicTestSuite {

  def roundtripTest(baseResource: Resource, imageFormat: ImageReader with ImageWriter) = {
    val (oldPixels, newPixels) = (for {
      original <- imageFormat.loadImage(baseResource).get.right.toOption
      originalPixels = original.getPixels().map(_.toVector)
      stored <- imageFormat.toByteArray(original).right.toOption
      loaded <- imageFormat.fromByteArray(stored).right.toOption
      loadedPixels = loaded.getPixels().map(_.toVector)
    } yield (originalPixels, loadedPixels)).get

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

    test("Write a QOI image") {
      roundtripTest(Resource("scala.qoi"), qoi.QoiImageFormat.defaultFormat)
    }
  }
}
