package eu.joaocosta.minart.graphics.image

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object ImageSpec extends BasicTestSuite {

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Load a BMP image") {
      val imageRgb = Image.loadBmpImage(Resource("scala.bmp"))
      assert(imageRgb.isSuccess)
      assert(imageRgb.get.width == 128)
      assert(imageRgb.get.height == 128)
      val imageArgb = Image.loadBmpImage(Resource("scala-argb.bmp"))
      assert(imageArgb.isSuccess)
      assert(imageArgb.get.width == 128)
      assert(imageArgb.get.height == 128)

      val imageRect = Image.loadBmpImage(Resource("scala-rect.bmp"))
      assert(imageRect.isSuccess)
      assert(imageRect.get.width == 77)
      assert(imageRect.get.height == 119)
    }

    test("Load a PGM/PPM image") {
      val imageGrayscaleBin = Image.loadPpmImage(Resource("scala.pgm"))
      assert(imageGrayscaleBin.isSuccess)
      assert(imageGrayscaleBin.get.width == 128)
      assert(imageGrayscaleBin.get.height == 128)
      val imageGrayscaleTxt = Image.loadPpmImage(Resource("scala-txt.pgm"))
      assert(imageGrayscaleTxt.isSuccess)
      assert(imageGrayscaleTxt.get.width == 128)
      assert(imageGrayscaleTxt.get.height == 128)

      val imageBin = Image.loadPpmImage(Resource("scala.ppm"))
      assert(imageBin.isSuccess)
      assert(imageBin.get.width == 128)
      assert(imageBin.get.height == 128)
      val imageTxt = Image.loadPpmImage(Resource("scala-txt.ppm"))
      assert(imageTxt.isSuccess)
      assert(imageTxt.get.width == 128)
      assert(imageTxt.get.height == 128)

      val imageRectBin = Image.loadPpmImage(Resource("scala-rect.ppm"))
      assert(imageRectBin.isSuccess)
      assert(imageRectBin.get.width == 77)
      assert(imageRectBin.get.height == 119)
      val imageRectTxt = Image.loadPpmImage(Resource("scala-rect-txt.ppm"))
      assert(imageRectTxt.isSuccess)
      assert(imageRectTxt.get.width == 77)
      assert(imageRectTxt.get.height == 119)
    }

    test("Load a QOI image") {
      val image = Image.loadQoiImage(Resource("scala.qoi"))
      assert(image.isSuccess)
      assert(image.get.width == 128)
      assert(image.get.height == 128)

      val imageRect = Image.loadQoiImage(Resource("scala-rect.qoi"))
      assert(imageRect.isSuccess)
      assert(imageRect.get.width == 77)
      assert(imageRect.get.height == 119)
    }

    test("Load the same data from different formats") {
      val bmpRgb  = Image.loadBmpImage(Resource("scala.bmp")).get.getPixels().map(_.toVector)
      val bmpArgb = Image.loadBmpImage(Resource("scala-argb.bmp")).get.getPixels().map(_.toVector)
      val ppmP2   = Image.loadPpmImage(Resource("scala-txt.pgm")).get.getPixels().map(_.toVector)
      val ppmP3   = Image.loadPpmImage(Resource("scala-txt.ppm")).get.getPixels().map(_.toVector)
      val ppmP5   = Image.loadPpmImage(Resource("scala.pgm")).get.getPixels().map(_.toVector)
      val ppmP6   = Image.loadPpmImage(Resource("scala.ppm")).get.getPixels().map(_.toVector)
      val qoi     = Image.loadQoiImage(Resource("scala.qoi")).get.getPixels().map(_.toVector)

      assert(ppmP2 == ppmP5)

      assert(bmpRgb == bmpArgb)
      assert(bmpRgb == ppmP6)
      assert(bmpRgb == ppmP3)
      assert(bmpRgb == qoi)
    }
  }
}
