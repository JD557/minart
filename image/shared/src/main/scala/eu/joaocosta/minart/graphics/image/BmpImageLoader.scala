package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.Helpers._

object BmpImageLoader extends ImageLoader {

  private val supportedFormats = Set("BM")

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
        magic <- readString(2).validate(
          supportedFormats,
          m => s"Unsupported format: $m. Only windows BMPs are supported"
        )
        size          <- readLENumber(4)
        _             <- skipBytes(4)
        offset        <- readLENumber(4)
        dibHeaderSize <- readLENumber(4).validate[String](_ >= 40, dib => s"Unsupported DIB header size: $dib")
        width         <- readLENumber(4)
        height        <- readLENumber(4)
        colorPlanes <- readLENumber(2).validate[String](
          _ == 1,
          planes => s"Invalid number of color planes (must be 1): $planes"
        )
        bitsPerPixel <- readLENumber(2).validate(
          Set(24, 32),
          bpp => s"Unsupported bits per pixel (must be 24 or 32): $bpp"
        )
        compressionMethod <- readLENumber(4).validate(_ == 0, _ => "Compression is not supported")
      } yield Header(magic, size, offset, width, height, bitsPerPixel)
    ).run(bytes).map { case (_, header) => bytes.drop(header.offset) -> header }
  }

  @tailrec
  def loadPixels(
      loadColor: LazyList[Int] => ParseResult[Color],
      data: LazyList[Int],
      acc: List[Color] = Nil
  ): ParseResult[List[Color]] = {
    if (data.isEmpty) Right(data -> acc.reverse)
    else {
      loadColor(data) match {
        case Left(error)               => Left(error)
        case Right((remaining, color)) => loadPixels(loadColor, remaining, color :: acc)
      }
    }
  }

  def loadRgbPixel(data: LazyList[Int]): ParseResult[Color] = {
    val parsed = data.take(3).toVector
    Either.cond(
      parsed.size == 3,
      data.drop(3) -> Color(parsed(2), parsed(1), parsed(0)),
      "Not enough data to fetch pixel"
    )
  }

  def loadRgbaPixel(data: LazyList[Int]): ParseResult[Color] = {
    val parsed = data.take(4).toVector
    Either.cond(
      parsed.size == 4,
      data.drop(4) -> Color(parsed(2), parsed(1), parsed(0)),
      "Not enough data to fetch pixel"
    )
  }

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes: LazyList[Int] = LazyList.continually(is.read()).takeWhile(_ != -1)
    Header.fromBytes(bytes).flatMap { case (data, header) =>
      val pixels = header.bitsPerPixel match {
        case 24 =>
          loadPixels(loadRgbPixel _, data)
        case 32 =>
          loadPixels(loadRgbaPixel _, data)
        case bpp =>
          Left(s"Invalid bits per pixel: $bpp")
      }
      pixels.map { case (_, flatPixels) =>
        new RamSurface(flatPixels.take(header.width * header.height).sliding(header.width, header.width).toSeq.reverse)
      }
    }
  }
}
