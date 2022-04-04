package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for PPM files.
  *
  * Supports P3/P6 PPM files with a 8 bit color range.
  */
final class PpmImageLoader[F[_]](byteReader: ByteReader[F]) extends ImageLoader {
  import PpmImageLoader._
  private val byteStringOps = new ByteStringOps(byteReader)
  import byteReader._
  import byteStringOps._

  private val loadStringPixel: ParseState[String, Color] =
    (
      for {
        red   <- parseNextInt("Invalid red channel")
        green <- parseNextInt("Invalid green channel")
        blue  <- parseNextInt("Invalid blue channel")
      } yield Color(red, green, blue)
    )

  private val loadBinaryPixel: ParseState[String, Color] =
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

  def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    Header.fromBytes(bytes)(byteReader).right.flatMap { case (data, header) =>
      val numPixels = header.width * header.height
      val pixels = header.magic match {
        case "P3" =>
          loadPixels(loadStringPixel, data, numPixels)
        case "P6" =>
          loadPixels(loadBinaryPixel, data, numPixels)
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

object PpmImageLoader {
  val defaultLoader = new PpmImageLoader[Iterator](ByteReader.IteratorByteReader)

  val supportedFormats = Set("P3", "P6")

  final case class Header(
      magic: String,
      width: Int,
      height: Int,
      colorRange: Int
  )

  object Header {
    def fromBytes[F[_]](bytes: F[Int])(byteReader: ByteReader[F]): byteReader.ParseResult[Header] = {
      val byteStringOps = new PpmImageLoader.ByteStringOps(byteReader)
      import byteReader._
      import byteStringOps._
      (
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
  }

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
