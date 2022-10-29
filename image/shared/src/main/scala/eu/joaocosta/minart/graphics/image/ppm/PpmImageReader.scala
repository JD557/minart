package eu.joaocosta.minart.graphics.image.ppm

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image._
import eu.joaocosta.minart.internal._

/** Image reader for PGM/PPM files.
  *
  * Supports P2, P3, P5 and P6 PGM/PPM files with a 8 bit color range.
  */
trait PpmImageReader[ByteSeq] extends ImageReader {
  val byteReader: ByteReader[ByteSeq]

  import PpmImageReader._
  private val byteStringOps = new ByteStringOps(byteReader)
  import byteReader._
  import byteStringOps._

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

  @tailrec
  private def loadPixelLine(
      loadColor: ParseState[String, Color],
      data: ByteSeq,
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
      data: ByteSeq,
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

  private def loadHeader(bytes: ByteSeq): ParseResult[Header] = {
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
          loadPixels(loadStringGrayscalePixel, data, header.height, header.width)
        case "P3" =>
          loadPixels(loadStringRgbPixel, data, header.height, header.width)
        case "P5" =>
          loadPixels(loadBinaryGrayscalePixel, data, header.height, header.width)
        case "P6" =>
          loadPixels(loadBinaryRgbPixel, data, header.height, header.width)
        case fmt =>
          Left(s"Invalid pixel format: $fmt")
      }
      pixels.right.flatMap { case (_, pixelMatrix) =>
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
  private final class ByteStringOps[ByteSeq](val byteReader: ByteReader[ByteSeq]) {
    import byteReader._
    private val space   = ' '.toInt
    private val newLine = '\n'.toInt
    private val comment = '#'.toInt

    val skipLine: ParseState[Nothing, Unit] =
      readWhile(_ != newLine).flatMap(_ => skipBytes(1))

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
