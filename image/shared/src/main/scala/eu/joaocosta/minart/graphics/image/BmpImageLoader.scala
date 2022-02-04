package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.Helpers._

object BmpImageLoader extends ImageLoader {

  private val supportedFormats = Set("BM")

  private def readString(n: Int): State[LazyList[Int], Nothing, String] = State { bytes =>
    bytes.drop(n) -> bytes.take(n).map(_.toChar).mkString("")
  }

  private def readNumber(n: Int): State[LazyList[Int], Nothing, Int] = State { bytes =>
    bytes.drop(n) -> bytes.take(n).zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }

  private def skip(n: Int): State[LazyList[Int], Nothing, Unit] = State { bytes =>
    bytes.drop(n) -> ()
  }

  case class Header(
      magic: String,
      size: Int,
      offset: Int,
      width: Int,
      height: Int,
      bitsPerPixel: Int
  )

  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = (
      for {
        magic  <- readString(2)
        _      <- State.check(supportedFormats(magic), s"Unsupported format: $magic. Only windows BMPs are supported")
        size   <- readNumber(4)
        _      <- skip(4)
        offset <- readNumber(4)
        dibHeaderSize <- readNumber(4)
        _             <- State.check(dibHeaderSize >= 40, s"Unsupported DIB header size: $dibHeaderSize")
        width         <- readNumber(4)
        height        <- readNumber(4)
        colorPlanes   <- readNumber(2)
        _             <- State.check(colorPlanes == 1, s"Invalid number of color planes (must be 1): $colorPlanes")
        bitsPerPixel  <- readNumber(2)
        _ <- State.check(
          bitsPerPixel == 24 || bitsPerPixel == 32,
          s"Unsupported bits per pixel (must be 24 or 32): $bitsPerPixel"
        )
        compressionMethod <- readNumber(4)
        _                 <- State.check(compressionMethod == 0, "Compression is not supported")
      } yield Header(magic, size, offset, width, height, bitsPerPixel)
    ).run(bytes).map { case (_, header) => header -> bytes.drop(header.offset) }
  }

  @tailrec
  def loadPixels(
      loadColor: LazyList[Int] => ParseResult[Color],
      data: LazyList[Int],
      acc: List[Color] = Nil
  ): ParseResult[List[Color]] = {
    if (data.isEmpty) Right(acc.reverse -> data)
    else {
      loadColor(data) match {
        case Left(error)               => Left(error)
        case Right((color, remaining)) => loadPixels(loadColor, remaining, color :: acc)
      }
    }
  }

  def loadRgbPixel(data: LazyList[Int]): ParseResult[Color] = {
    val parsed = data.take(3).toVector
    Either.cond(
      parsed.size == 3,
      Color(parsed(2), parsed(1), parsed(0)) -> data.drop(3),
      "Not enough data to fetch pixel"
    )
  }

  def loadRgbaPixel(data: LazyList[Int]): ParseResult[Color] = {
    val parsed = data.take(4).toVector
    Either.cond(
      parsed.size == 4,
      Color(parsed(2), parsed(1), parsed(0)) -> data.drop(4),
      "Not enough data to fetch pixel"
    )
  }

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes: LazyList[Int] = LazyList.continually(is.read()).takeWhile(_ != -1)
    Header.fromBytes(bytes).flatMap { case (header, data) =>
      val pixels = header.bitsPerPixel match {
        case 24 =>
          loadPixels(loadRgbPixel _, data)
        case 32 =>
          loadPixels(loadRgbaPixel _, data)
        case bpp =>
          Left(s"Invalid bits per pixel: $bpp")
      }
      pixels.map { case (flatPixels, _) =>
        new RamSurface(flatPixels.take(header.width * header.height).sliding(header.width, header.width).toSeq.reverse)
      }
    }
  }
}
