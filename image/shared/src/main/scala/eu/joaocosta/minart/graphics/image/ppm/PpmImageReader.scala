package eu.joaocosta.minart.graphics.image.ppm

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image reader for PGM/PPM files.
  *
  * Supports P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range.
  */
trait PpmImageReader extends ImageReader {
  import PpmImageReader.*
  import ByteReader.*
  import ByteStringOps.*

  // P1
  private val loadStringBWPixel: ParseState[String, Color] =
    (
      for {
        value <- readNextNonWhitespaceChar
          .validate(
            _.exists(x => x == '0' || x == '1'),
            x => s"Bitmap value $x needs to be either 0 or 1"
          )
      } yield if (value.contains('1')) Color.grayscale(0) else Color.grayscale(255)
    )

  // P2
  private val loadStringGrayscalePixel: ParseState[String, Color] =
    (
      for {
        value <- parseNextInt("Invalid value")
      } yield Color.grayscale(value)
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

  // P4 - Custom logic due to bit packing

  @tailrec
  private def loadBits(
      data: CustomInputStream,
      remainingLines: Int,
      width: Int,
      lineBytes: Int,
      acc: Vector[Array[Boolean]] = Vector()
  ): ParseResult[Vector[Array[Boolean]]] = {
    if (isEmpty(data) || remainingLines == 0) Right(data -> acc)
    else {
      readPaddedBits(width, lineBytes).run(data) match {
        case Left(error) => Left(error)
        case Right((remaining, line)) =>
          loadBits(remaining, remainingLines - 1, width, lineBytes, acc :+ line)
      }
    }
  }

  // P5
  private val loadBinaryGrayscalePixel: ParseState[String, Color] =
    readByte.collect(
      { case Some(byte) => Color.grayscale(byte) },
      _ => "Not enough data to read Grayscale pixel"
    )

  // P6
  private val loadBinaryRgbPixel: ParseState[String, Color] =
    readBytes(3).collect(
      { case bytes if bytes.size == 3 => Color(bytes(0), bytes(1), bytes(2)) },
      _ => "Not enough data to read RGB pixel"
    )

  private def loadColor(magic: String): Either[String, ParseState[String, Color]] = magic match {
    case "P1" => Right(loadStringBWPixel)
    case "P2" => Right(loadStringGrayscalePixel)
    case "P3" => Right(loadStringRgbPixel)
    // case "P4" => // P4 requires special logic
    case "P5" => Right(loadBinaryGrayscalePixel)
    case "P6" => Right(loadBinaryRgbPixel)
    case fmt  => Left(s"Invalid pixel format: $fmt")
  }

  @tailrec
  private def loadPixelLine(
      loadColor: ParseState[String, Color],
      data: CustomInputStream,
      remainingPixels: Int,
      acc: List[Color] = Nil
  ): ParseResult[Array[Color]] = {
    if (isEmpty(data) || remainingPixels == 0)
      Right(data -> acc.reverseIterator.toArray)
    else {
      loadColor.run(data) match {
        case Left(error) => Left(error)
        case Right((remaining, color)) =>
          loadPixelLine(loadColor, remaining, remainingPixels - 1, color :: acc)
      }
    }
  }

  @tailrec
  private def loadPixels(
      loadColor: ParseState[String, Color],
      data: CustomInputStream,
      remainingLines: Int,
      width: Int,
      acc: Vector[Array[Color]] = Vector()
  ): ParseResult[Vector[Array[Color]]] = {
    if (isEmpty(data) || remainingLines == 0) Right(data -> acc)
    else {
      loadPixelLine(loadColor, data, width) match {
        case Left(error) => Left(error)
        case Right((remaining, line)) =>
          loadPixels(loadColor, remaining, remainingLines - 1, width, acc :+ line)
      }
    }
  }

  private def loadHeader(bytes: CustomInputStream): ParseResult[Header] = {
    (
      for {
        magic  <- readNextString.validate(PpmImageFormat.supportedFormats, m => s"Unsupported format: $m")
        width  <- parseNextInt(s"Invalid width")
        height <- parseNextInt(s"Invalid height")
        colorRange <-
          if (magic == "P1" || magic == "P4") State.pure(1)
          else
            parseNextInt(s"Invalid color range").validate(
              _ == 255,
              range => s"Unsupported color range: $range"
            )
      } yield Header(magic, width, height, colorRange)
    ).run(bytes)
  }

  final def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).flatMap { case (data, header) =>
      val pixels = header.magic match {
        case "P4" =>
          loadBits(data, header.height, header.width, math.ceil(header.width / 8.0).toInt).map((state, bits) =>
            (state, bits.map(_.map(b => if (b) Color.grayscale(0) else Color.grayscale(255))))
          )
        case _ =>
          loadColor(header.magic).flatMap(loadColor => loadPixels(loadColor, data, header.height, header.width))
      }
      pixels.flatMap { case (_, pixelMatrix) =>
        if (pixelMatrix.size != header.height)
          Left(s"Invalid number of lines: Got ${pixelMatrix.size}, expected ${header.height}")
        else if (pixelMatrix.nonEmpty && pixelMatrix.last.size != header.width)
          Left(s"Invalid number of pixels in the last line: Got ${pixelMatrix.last.size}, expected ${header.width}")
        else Right(new RamSurface(pixelMatrix))
      }
    }
  }
}

object PpmImageReader {
  private object ByteStringOps {
    import ByteReader.*
    private val space   = ' '.toInt
    private val newLine = '\n'.toInt
    private val comment = '#'.toInt

    val skipLine: ParseState[Nothing, Unit] =
      skipWhile(_ != newLine).flatMap(_ => skipBytes(1))

    val readNextString: ParseState[String, String] =
      readByte.flatMap {
        case None => State.pure("")
        case Some(c) =>
          if (c == comment) skipLine.flatMap(_ => readNextString)
          else if (c == newLine || c == space) readNextString
          else
            readWhile(char => char != space && char != newLine)
              .map(chars => (c.toChar :: chars.map(_.toChar)).mkString(""))
              .flatMap(str => skipBytes(1).map(_ => str))
      }

    val readNextNonWhitespaceChar: ParseState[String, Option[Char]] =
      readByte.flatMap {
        case None => State.pure(None)
        case Some(c) =>
          if (c == comment) skipLine.flatMap(_ => readNextNonWhitespaceChar)
          else if (c == newLine || c == space) readNextNonWhitespaceChar
          else State.pure(Some(c.toChar))
      }

    def parseNextInt(errorMessage: String): ParseState[String, Int] =
      readNextString.flatMap { str =>
        val intEither = str.toIntOption.map(int => Right(int)).getOrElse(Left(s"Failed to parse int: $str"))
        State.fromEither(intEither)
      }
  }
}
