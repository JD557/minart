package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers.IteratorHelpers._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for PPM files.
  *
  * Supports P3/P6 PPM files with a 8 bit color range.
  */
object PpmImageLoader extends ImageLoader {

  private val supportedFormats = Set("P3", "P6")

  private val readNextLine: ParseState[Nothing, List[Int]] = State[Iterator[Int], List[Int]] { bytes =>
    @tailrec
    def aux(b: Iterator[Int]): (Iterator[Int], List[Int]) = {
      val chars = b.takeWhile(_.toChar != '\n').toList :+ '\n'.toInt
      // val remaining = b.drop(chars.size)
      if (chars.map(_.toChar).headOption.exists(c => c == '#' || c == '\n'))
        aux(b)
      else
        b -> chars
    }
    aux(bytes)
  }

  private val readNextString: ParseState[Nothing, String] =
    readNextLine.flatMap { line =>
      val chars         = line.map(_.toChar).takeWhile(c => c != ' ')
      val remainingLine = line.drop(chars.size + 1)
      lazy val string   = chars.mkString("").trim
      if (remainingLine.isEmpty)
        State.pure(string)
      else
        State
          .pure(string)
          .modify { remaining =>
            (remainingLine.iterator ++ Iterator('\n'.toInt) ++ remaining)
          }
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
    def fromBytes(bytes: Iterator[Int]): ParseResult[Header] = (
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
      loadColor: ParseState[String, Color],
      data: Iterator[Int],
      acc: List[Color] = Nil
  ): ParseResult[List[Color]] = {
    if (data.isEmpty) Right(data -> acc.reverse)
    else {
      loadColor.run(data) match {
        case Left(error)               => Left(error)
        case Right((remaining, color)) => loadPixels(loadColor, remaining, color :: acc)
      }
    }
  }

  val loadStringPixel: ParseState[String, Color] =
    (
      for {
        red   <- parseNextInt("Invalid red channel")
        green <- parseNextInt("Invalid green channel")
        blue  <- parseNextInt("Invalid blue channel")
      } yield Color(red, green, blue)
    )

  val loadBinaryPixel: ParseState[String, Color] =
    readBytes(3).collect(
      { case bytes if bytes.size == 3 => Color(bytes(0), bytes(1), bytes(2)) },
      _ => "Not enough data to read RGB pixel"
    )

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    Header.fromBytes(Iterator.continually(is.read()).takeWhile(_ != -1)).flatMap { case (data, header) =>
      val pixels = header.magic match {
        case "P3" =>
          loadPixels(loadStringPixel, data)
        case "P6" =>
          loadPixels(loadBinaryPixel, data)
        case fmt =>
          Left(s"Invalid pixel format: $fmt")
      }
      pixels.map { case (_, flatPixels) =>
        new RamSurface(flatPixels.take(header.width * header.height).sliding(header.width, header.width).toSeq)
      }
    }
  }
}
