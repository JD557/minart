package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.Helpers._

object PpmImageLoader extends ImageLoader {

  private val supportedFormats = Set("P3", "P6")

  private def readLine(bytes: LazyList[Int]): (LazyList[Int], LazyList[Int]) = {
    val chars     = bytes.takeWhile(_.toChar != '\n') :+ '\n'.toInt
    val remaining = bytes.drop(chars.size)
    if (chars.map(_.toChar).headOption.exists(c => c == '#' || c == '\n'))
      readLine(remaining)
    else
      chars -> remaining
  }
  private val readString: State[LazyList[Int], Nothing, String] = State { bytes =>
    val (line, remaining) = readLine(bytes)
    val chars             = line.map(_.toChar).takeWhile(c => c != ' ')
    val remainingLine     = line.drop(chars.size + 1)
    lazy val string       = chars.mkString("").trim
    if (remainingLine.isEmpty)
      remaining -> string
    else
      (remainingLine ++ ('\n'.toInt +: remaining)) -> string
  }
  private def parseInt(errorMessage: String): State[LazyList[Int], String, Int] =
    readString.flatMap { str =>
      State.fromEither(str.toIntOption.toRight(s"$errorMessage: $str"))
    }

  case class Header(
      magic: String,
      width: Int,
      height: Int,
      colorRange: Int
  )

  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = (
      for {
        magic      <- readString
        _          <- State.check(supportedFormats(magic), s"Unsupported format: $magic")
        width      <- parseInt(s"Invalid width")
        height     <- parseInt(s"Invalid height")
        colorRange <- parseInt(s"Invalid color range")
        _          <- State.check(colorRange == 255, s"Unsupported color range: $colorRange")
      } yield Header(magic, width, height, colorRange)
    ).run(bytes).map(_.swap)
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

  def loadStringPixel(data: LazyList[Int]): ParseResult[Color] = (
    for {
      red   <- parseInt("Invalid red channel")
      green <- parseInt("Invalid green channel")
      blue  <- parseInt("Invalid blue channel")
    } yield Color(red, green, blue)
  ).run(data).map(_.swap)

  def loadBinaryPixel(data: LazyList[Int]): ParseResult[Color] = {
    val parsed = data.take(3).toVector
    Either.cond(
      parsed.size == 3,
      Color(parsed(0), parsed(1), parsed(2)) -> data.drop(3),
      "Not enough data to fetch pixel"
    )
  }

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes: LazyList[Int] = LazyList.continually(is.read()).takeWhile(_ != -1)
    Header.fromBytes(bytes).flatMap { case (header, data) =>
      val pixels = header.magic match {
        case "P3" =>
          loadPixels(loadStringPixel _, data)
        case "P6" =>
          loadPixels(loadBinaryPixel _, data)
        case fmt =>
          Left(s"Invalid pixel format: $fmt")
      }
      pixels.map { case (flatPixels, _) =>
        new RamSurface(flatPixels.take(header.width * header.height).sliding(header.width, header.width).toSeq)
      }
    }
  }
}
