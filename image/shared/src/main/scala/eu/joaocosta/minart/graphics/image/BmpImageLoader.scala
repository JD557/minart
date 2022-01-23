package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._

object BmpImageLoader extends ImageLoader {

  private val supportedFormats = Set("BM")

  private def readString(bytes: LazyList[Int], n: Int): (String, LazyList[Int]) = {
    bytes.take(n).map(_.toChar).mkString("") -> bytes.drop(n)
  }
  private def readNumber(bytes: LazyList[Int], n: Int): (Int, LazyList[Int]) =
    bytes.take(n).zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum -> bytes.drop(n)

  case class Header(
      magic: String,
      size: Int,
      offset: Int,
      width: Int,
      height: Int,
      bitsPerPixel: Int
  )

  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = {
      lazy val magic             = readString(bytes, 2)._1
      lazy val size              = readNumber(bytes.drop(2), 4)._1
      lazy val offset            = readNumber(bytes.drop(10), 4)._1
      lazy val dibHeaderSize     = readNumber(bytes.drop(14), 4)._1
      lazy val width             = readNumber(bytes.drop(18), 4)._1
      lazy val height            = readNumber(bytes.drop(22), 4)._1
      lazy val colorPlanes       = readNumber(bytes.drop(26), 2)._1
      lazy val bitsPerPixel      = readNumber(bytes.drop(28), 2)._1
      lazy val compressionMethod = readNumber(bytes.drop(30), 4)._1

      for {
        validatedMagic <- Either.cond(
          supportedFormats(magic),
          magic,
          s"Unsupported format: $magic. Only windows BMPs are supported"
        )
        _ <- Either.cond(dibHeaderSize >= 40, (), s"Unsupported DIB header size: $dibHeaderSize")
        _ <- Either.cond(colorPlanes == 1, (), s"Invalid number of color planes (must be 1): $colorPlanes")
        validatedBpp <- Either.cond(
          bitsPerPixel == 24 || bitsPerPixel == 32,
          bitsPerPixel,
          s"Unsupported bits per pixel (must be 24 or 32): $bitsPerPixel"
        )
        _ <- Either.cond(compressionMethod == 0, (), "Compression is not supported")
      } yield Header(validatedMagic, size, offset, width, height, validatedBpp) -> bytes.drop(offset)
    }
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
