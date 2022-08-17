package eu.joaocosta.minart.graphics.image

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageWriterSpec extends BasicTestSuite {

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Write a PPM image") {
      val originalImage = Image.loadPpmImage(Resource("scala.ppm")).get
      val os            = new ByteArrayOutputStream()
      ppm.PpmImageFormat.defaultFormat.storeImage(originalImage, os)
      val is       = new ByteArrayInputStream(os.toByteArray)
      val newImage = ppm.PpmImageFormat.defaultFormat.loadImage(is).toOption.get
      assert(newImage.getPixels().map(_.toVector) == originalImage.getPixels().map(_.toVector))
    }

    test("Write a BMP image") {
      val originalImage = Image.loadBmpImage(Resource("scala.bmp")).get
      val os            = new ByteArrayOutputStream()
      bmp.BmpImageFormat.defaultFormat.storeImage(originalImage, os)
      val is       = new ByteArrayInputStream(os.toByteArray)
      val newImage = bmp.BmpImageFormat.defaultFormat.loadImage(is).toOption.get
      assert(newImage.getPixels().map(_.toVector) == originalImage.getPixels().map(_.toVector))
    }
  }
}
