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
        size   <- readLENumber(4)
        _      <- skipBytes(4)
        offset <- readLENumber(4)
        dibHeaderSize <- readLENumber(4).validate(
          dib => dib >= 40 && dib <= 124,
          dib => s"Unsupported DIB header size: $dib"
        )
        width  <- readLENumber(4)
        height <- readLENumber(4)
        colorPlanes <- readLENumber(2).validate(
          _ == 1,
          planes => s"Invalid number of color planes (must be 1): $planes"
        )
        bitsPerPixel <- readLENumber(2).validate(
          Set(24, 32),
          bpp => s"Unsupported bits per pixel (must be 24 or 32): $bpp"
        )
        compressionMethod <- readLENumber(4).validate(_ == 0, _ => "Compression is not supported")
        header = Header(magic, size, offset, width, height, bitsPerPixel)
        _ <- State.set(bytes.drop(offset))
      } yield header
    ).run(bytes)
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

  def loadRgbPixel(data: LazyList[Int]): ParseResult[Color] =
    readBytes(3)
      .collect(
        { case bytes if bytes.size == 3 => Color(bytes(2), bytes(1), bytes(0)) },
        _ => "Not enough data to read RGB pixel"
      )
      .run(data)

  def loadRgbaPixel(data: LazyList[Int]): ParseResult[Color] =
    readBytes(4)
      .collect(
        { case bytes if bytes.size == 4 => Color(bytes(2), bytes(1), bytes(0)) },
        _ => "Not enough data to read RGBA pixel"
      )
      .run(data)

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
