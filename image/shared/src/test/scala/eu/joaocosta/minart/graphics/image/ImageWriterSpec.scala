package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageWriterSpec extends BasicTestSuite {

  def roundtripTest(baseResource: Resource, imageFormat: ImageReader with ImageWriter) = {
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
      roundtripTest(Resource("scala/ppm-p3.ppm"), ppm.PpmImageFormat.defaultFormat)
      roundtripTest(Resource("scala-rect/ppm-p3.ppm"), ppm.PpmImageFormat.defaultFormat)
      // Native tests in debug require a large heap here
      if (Platform() != Platform.Native)
        roundtripTest(Resource("lausanne/ppm-p3.ppm"), ppm.PpmImageFormat.defaultFormat)
    }

    test("Write a BMP image") {
      roundtripTest(Resource("scala/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
      roundtripTest(Resource("scala-rect/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
      // Native tests in debug require a large heap here
      if (Platform() != Platform.Native)
        roundtripTest(Resource("lausanne/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
    }

    test("Write a QOI image") {
      roundtripTest(Resource("scala/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
      roundtripTest(Resource("scala-rect/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
      // Native tests in debug require a large heap here
      if (Platform() != Platform.Native)
        roundtripTest(Resource("lausanne/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
    }
  }
}
