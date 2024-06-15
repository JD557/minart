package eu.joaocosta.minart.graphics.image

import eu.joaocosta.minart.backend.defaults.given
import eu.joaocosta.minart.runtime.*

class ImageWriterSpec extends munit.FunSuite {

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
      roundtripTest(Resource("lausanne/ppm-p3.ppm"), ppm.PpmImageFormat.defaultFormat)
    }

    test("Write a BMP image") {
      roundtripTest(Resource("scala/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
      roundtripTest(Resource("scala-rect/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
      roundtripTest(Resource("lausanne/bmp-24bit.bmp"), bmp.BmpImageFormat.defaultFormat)
    }

    test("Write a QOI image") {
      roundtripTest(Resource("scala/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
      roundtripTest(Resource("scala-rect/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
      roundtripTest(Resource("lausanne/qoi-24bit.qoi"), qoi.QoiImageFormat.defaultFormat)
    }

    test("Write a PDI image") {
      roundtripTest(Resource("alpha/pdi-2bit.pdi"), pdi.PdiImageFormat.defaultFormat)
    }
  }
}
