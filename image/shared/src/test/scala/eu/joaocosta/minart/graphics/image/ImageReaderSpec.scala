package eu.joaocosta.minart.graphics.image

import scala.util.Try

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

object ImageReaderSpec extends BasicTestSuite {

  def sameImage(results: List[RamSurface]): Unit = {
    results.sliding(2).foreach {
      case img1 :: img2 :: _ => assert(img1.getPixels().map(_.toVector) == img2.getPixels().map(_.toVector))
      case _                 => ()
    }
  }

  def testSize(results: List[Try[RamSurface]], expectedWidth: Int, expectedHeight: Int): Unit = {
    assert(results.map(_.get).size != 0)
    assert(results.head.get.width == expectedWidth)
    assert(results.head.get.height == expectedHeight)
    sameImage(results.map(_.get))
  }

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Load a BMP image") {
      testSize(
        List(Image.loadBmpImage(Resource("scala/bmp-24bit.bmp")), Image.loadBmpImage(Resource("scala/bmp-32bit.bmp"))),
        128,
        128
      )
      testSize(
        List(
          Image.loadBmpImage(Resource("scala-rect/bmp-24bit.bmp")),
          Image.loadBmpImage(Resource("scala-rect/bmp-32bit.bmp"))
        ),
        77,
        119
      )
      testSize(
        List(
          Image.loadBmpImage(Resource("lausanne/bmp-24bit.bmp")),
          Image.loadBmpImage(Resource("lausanne/bmp-32bit.bmp"))
        ),
        640,
        480
      )
    }

    test("Load a PPM image") {
      testSize(
        List(Image.loadPpmImage(Resource("scala/ppm-p3.ppm")), Image.loadPpmImage(Resource("scala/ppm-p6.ppm"))),
        128,
        128
      )
      testSize(
        List(
          Image.loadPpmImage(Resource("scala-rect/ppm-p3.ppm")),
          Image.loadPpmImage(Resource("scala-rect/ppm-p6.ppm"))
        ),
        77,
        119
      )
      testSize(
        List(
          Image.loadPpmImage(Resource("lausanne/ppm-p3.ppm")),
          Image.loadPpmImage(Resource("lausanne/ppm-p6.ppm"))
        ),
        640,
        480
      )
    }

    test("Load a PGM image") {
      testSize(
        List(Image.loadPpmImage(Resource("scala/pgm-p2.pgm")), Image.loadPpmImage(Resource("scala/pgm-p5.pgm"))),
        128,
        128
      )
      testSize(
        List(
          Image.loadPpmImage(Resource("scala-rect/pgm-p2.pgm")),
          Image.loadPpmImage(Resource("scala-rect/pgm-p5.pgm"))
        ),
        77,
        119
      )
      testSize(
        List(
          Image.loadPpmImage(Resource("lausanne/pgm-p2.pgm")),
          Image.loadPpmImage(Resource("lausanne/pgm-p5.pgm"))
        ),
        640,
        480
      )
    }

    test("Load a QOI image") {
      testSize(
        List(Image.loadQoiImage(Resource("scala/qoi-24bit.qoi")), Image.loadQoiImage(Resource("scala/qoi-32bit.qoi"))),
        128,
        128
      )
      testSize(
        List(
          Image.loadQoiImage(Resource("scala-rect/qoi-24bit.qoi")),
          Image.loadQoiImage(Resource("scala-rect/qoi-32bit.qoi"))
        ),
        77,
        119
      )
      testSize(
        List(
          Image.loadQoiImage(Resource("lausanne/qoi-24bit.qoi")),
          Image.loadQoiImage(Resource("lausanne/qoi-32bit.qoi"))
        ),
        640,
        480
      )
    }

    test("Load the same data from different formats (square image)") {
      sameImage(
        List(
          Image.loadBmpImage(Resource("scala/bmp-24bit.bmp")).get,
          Image.loadPpmImage(Resource("scala/ppm-p3.ppm")).get,
          Image.loadQoiImage(Resource("scala/qoi-24bit.qoi")).get
        )
      )
    }

    test("Load the same data from different formats (non-square image)") {
      sameImage(
        List(
          Image.loadBmpImage(Resource("scala-rect/bmp-24bit.bmp")).get,
          Image.loadPpmImage(Resource("scala-rect/ppm-p3.ppm")).get,
          Image.loadQoiImage(Resource("scala-rect/qoi-24bit.qoi")).get
        )
      )
    }

    test("Load the same data from different formats (large image)") {
      sameImage(
        List(
          Image.loadBmpImage(Resource("lausanne/bmp-24bit.bmp")).get,
          Image.loadPpmImage(Resource("lausanne/ppm-p3.ppm")).get,
          Image.loadQoiImage(Resource("lausanne/qoi-24bit.qoi")).get
        )
      )
    }
  }
}
