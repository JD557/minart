package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._

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
  private def readString(bytes: LazyList[Int]): (String, LazyList[Int]) = {
    val (line, remaining) = readLine(bytes)
    val chars             = line.map(_.toChar).takeWhile(c => c != ' ')
    val remainingLine     = line.drop(chars.size + 1)
    lazy val string       = chars.mkString("").trim
    if (remainingLine.isEmpty)
      string -> remaining
    else
      string -> (remainingLine ++ ('\n'.toInt +: remaining))
  }

  case class Header(
      magic: String,
      width: Int,
      height: Int,
      colorRange: Int
  )

  object Header {
    def fromBytes(bytes: LazyList[Int]): ParseResult[Header] = {
      lazy val (magic, rem1)           = readString(bytes)
      lazy val (width, rem2)           = readString(rem1)
      lazy val (height, rem3)          = readString(rem2)
      lazy val (colorRange, remaining) = readString(rem3)

      for {
        validatedMagic <- Either.cond(supportedFormats(magic), magic, s"Unsupported format: $magic")
        numWidth       <- width.toIntOption.toRight(s"Invalid width: $width")
        numHeight      <- height.toIntOption.toRight(s"Invalid height: $height")
        numColorRange  <- colorRange.toIntOption.filter(_ == 255).toRight(s"Invalid color range: $colorRange")
      } yield Header(validatedMagic, numWidth, numHeight, numColorRange) -> remaining
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

  def loadStringPixel(data: LazyList[Int]): ParseResult[Color] = {
    lazy val (red, rem1)       = readString(data)
    lazy val (green, rem2)     = readString(rem1)
    lazy val (blue, remaining) = readString(rem2)
    for {
      numRed   <- red.toIntOption.toRight(s"Invalid red channel: $red")
      numGreen <- green.toIntOption.toRight(s"Invalid green channel: $green")
      numBlue  <- blue.toIntOption.toRight(s"Invalid blue channel: $blue")
    } yield Color(numRed, numGreen, numBlue) -> remaining
  }

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
