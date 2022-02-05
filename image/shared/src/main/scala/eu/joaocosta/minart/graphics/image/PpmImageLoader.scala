package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.Helpers._

object PpmImageLoader extends ImageLoader {

  private val supportedFormats = Set("P3", "P6")

  private def readNextLine(bytes: LazyList[Int]): (LazyList[Int], LazyList[Int]) = {
    val chars     = bytes.takeWhile(_.toChar != '\n') :+ '\n'.toInt
    val remaining = bytes.drop(chars.size)
    if (chars.map(_.toChar).headOption.exists(c => c == '#' || c == '\n'))
      readNextLine(remaining)
    else
      remaining -> chars
  }
  private val readNextString: ParseState[Nothing, String] = State { bytes =>
    val (remaining, line) = readNextLine(bytes)
    val chars             = line.map(_.toChar).takeWhile(c => c != ' ')
    val remainingLine     = line.drop(chars.size + 1)
    lazy val string       = chars.mkString("").trim
    if (remainingLine.isEmpty)
      remaining -> string
    else
      (remainingLine ++ ('\n'.toInt +: remaining)) -> string
  }
  private def parseNextInt(errorMessage: String): ParseState[String, Int] =
    readNextString.flatMap { str =>
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
        magic  <- readNextString.validate(supportedFormats, m => s"Unsupported format: $m")
        width  <- parseNextInt(s"Invalid width")
        height <- parseNextInt(s"Invalid height")
        colorRange <- parseNextInt(s"Invalid color range").validate(
          _ == 255,
          range => s"Unsupported color range: $range"
        )
      } yield Header(magic, width, height, colorRange)
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

  def loadStringPixel(data: LazyList[Int]): ParseResult[Color] = (
    for {
      red   <- parseNextInt("Invalid red channel")
      green <- parseNextInt("Invalid green channel")
      blue  <- parseNextInt("Invalid blue channel")
    } yield Color(red, green, blue)
  ).run(data)

  def loadBinaryPixel(data: LazyList[Int]): ParseResult[Color] =
    readBytes(3)
      .collect(
        { case bytes if bytes.size == 3 => Color(bytes(0), bytes(1), bytes(2)) },
        _ => "Not enough data to read RGB pixel"
      )
      .run(data)

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes: LazyList[Int] = LazyList.continually(is.read()).takeWhile(_ != -1)
    Header.fromBytes(bytes).flatMap { case (data, header) =>
      val pixels = header.magic match {
        case "P3" =>
          loadPixels(loadStringPixel _, data)
        case "P6" =>
          loadPixels(loadBinaryPixel _, data)
        case fmt =>
          Left(s"Invalid pixel format: $fmt")
      }
      pixels.map { case (_, flatPixels) =>
        new RamSurface(flatPixels.take(header.width * header.height).sliding(header.width, header.width).toSeq)
      }
    }
  }
}
