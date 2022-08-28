package eu.joaocosta.minart.graphics.image.ppm

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image reader for PGM/PPM files.
  *
  * Supports P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range.
  */
trait PpmImageReader[F[_]] extends ImageReader {
  val byteReader: ByteReader[F]

  import PpmImageFormat._
  import PpmImageReader._
  private val byteStringOps = new ByteStringOps(byteReader)
  import byteReader._
  import byteStringOps._

  // P2
  private val loadStringGrayscalePixel: ParseState[String, Color] =
    (
      for {
        value <- parseNextInt("Invalid value")
      } yield Color(value, value, value)
    )

  // P3
  private val loadStringRgbPixel: ParseState[String, Color] =
    (
      for {
        red   <- parseNextInt("Invalid red channel")
        green <- parseNextInt("Invalid green channel")
        blue  <- parseNextInt("Invalid blue channel")
      } yield Color(red, green, blue)
    )

  // P5
  private val loadBinaryGrayscalePixel: ParseState[String, Color] =
    readByte.collect(
      { case Some(byte) => Color(byte, byte, byte) },
      _ => "Not enough data to read Grayscale pixel"
    )

  // P6
  private val loadBinaryRgbPixel: ParseState[String, Color] =
    readBytes(3).collect(
      { case bytes if bytes.size == 3 => Color(bytes(0), bytes(1), bytes(2)) },
      _ => "Not enough data to read RGB pixel"
    )

  @tailrec
  private def loadPixels(
      loadColor: ParseState[String, Color],
      data: F[Int],
      remainingPixels: Int,
      acc: List[Color] = Nil
  ): ParseResult[List[Color]] = {
    if (isEmpty(data) || remainingPixels == 0) Right(data -> acc.reverse)
    else {
      loadColor.run(data) match {
        case Left(error)               => Left(error)
        case Right((remaining, color)) => loadPixels(loadColor, remaining, remainingPixels - 1, color :: acc)
      }
    }
  }

  def loadHeader(bytes: F[Int]): ParseResult[Header] = {
    val byteStringOps = new PpmImageReader.ByteStringOps(byteReader)
    import byteStringOps._
    (
      for {
        magic  <- readNextString.validate(PpmImageFormat.supportedFormats, m => s"Unsupported format: $m")
        width  <- parseNextInt(s"Invalid width")
        height <- parseNextInt(s"Invalid height")
        colorRange <- parseNextInt(s"Invalid color range").validate(
          _ == 255,
          range => s"Unsupported color range: $range"
        )
      } yield Header(magic, width, height, colorRange)
    ).run(bytes)
  }

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).right.flatMap { case (data, header) =>
      val numPixels = header.width * header.height
      val pixels = header.magic match {
        case "P2" =>
          loadPixels(loadStringGrayscalePixel, data, numPixels)
        case "P3" =>
          loadPixels(loadStringRgbPixel, data, numPixels)
        case "P5" =>
          loadPixels(loadBinaryGrayscalePixel, data, numPixels)
        case "P6" =>
          loadPixels(loadBinaryRgbPixel, data, numPixels)
        case fmt =>
          Left(s"Invalid pixel format: $fmt")
      }
      pixels.right.flatMap { case (_, flatPixels) =>
        if (flatPixels.size != numPixels) Left(s"Invalid number of pixels: Got ${flatPixels.size}, expected $numPixels")
        else Right(new RamSurface(flatPixels.sliding(header.width, header.width).toSeq))
      }
    }
  }
}

object PpmImageReader {
  private final class ByteStringOps[F[_]](val byteReader: ByteReader[F]) {
    import byteReader._
    private val newLine = '\n'.toInt
    private val comment = '#'.toInt
    private val space   = ' '.toInt

    val readNextLine: ParseState[Nothing, List[Int]] = State[F[Int], List[Int]] { bytes =>
      @tailrec
      def aux(b: F[Int]): (F[Int], List[Int]) = {
        val (remaining, line) = (for {
          chars <- readWhile(_ != newLine)
          fullChars = chars :+ newLine
          _ <- skipBytes(1)
        } yield fullChars).run(b).merge
        if (line.headOption.exists(c => c == comment || c == newLine))
          aux(remaining)
        else
          remaining -> line
      }
      aux(bytes)
    }

    val readNextString: ParseState[Nothing, String] =
      readNextLine.flatMap { line =>
        val chars         = line.takeWhile(c => c != space).map(_.toChar)
        val remainingLine = line.drop(chars.size + 1)
        val string        = chars.mkString("").trim
        if (remainingLine.isEmpty)
          State.pure(string)
        else
          pushBytes(remainingLine :+ newLine).map(_ => string)
      }

    def parseNextInt(errorMessage: String): ParseState[String, Int] =
      readNextString.flatMap { str =>
        val intEither =
          try {
            Right(str.toInt)
          } catch {
            case _: Throwable =>
              Left(s"$errorMessage: $str")
          }
        State.fromEither(intEither)
      }
  }
}
