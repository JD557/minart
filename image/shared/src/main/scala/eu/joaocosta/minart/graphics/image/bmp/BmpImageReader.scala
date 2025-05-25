package eu.joaocosta.minart.graphics.image.bmp

import java.io.InputStream

import scala.annotation.tailrec
import scala.collection.immutable.ArraySeq

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image reader for BMP files.
  *
  * Supports uncompressed 24/32bit Windows BMPs.
  */
trait BmpImageReader extends ImageReader {
  import ByteReader.*

  private val loadRgbPixel: ParseState[String, Color] =
    readRawBytes(3)
      .collect(
        { case bytes if bytes.size == 3 => Color(bytes(2), bytes(1), bytes(0)) },
        _ => "Not enough data to read RGB pixel"
      )

  private val loadRgbaPixel: ParseState[String, Color] =
    readRawBytes(4)
      .collect(
        { case bytes if bytes.size == 4 => Color(bytes(2), bytes(1), bytes(0), bytes(3)) },
        _ => "Not enough data to read RGBA pixel"
      )

  private def loadColor(bitsPerPixel: Int): Either[String, ParseState[String, Color]] =
    bitsPerPixel match {
      case 24 =>
        Right(loadRgbPixel)
      case 32 =>
        Right(loadRgbaPixel)
      case bpp =>
        Left(s"Invalid bits per pixel: $bpp")
    }

  @tailrec
  private def loadPixelLine(
      loadColor: ParseState[String, Color],
      data: CustomInputStream,
      remainingPixels: Int,
      padding: Int,
      acc: List[Color] = Nil
  ): ParseResult[Array[Color]] = {
    if (isEmpty(data) || remainingPixels == 0)
      skipBytes(padding).map(_ => acc.reverseIterator.toArray).run(data)
    else {
      loadColor.run(data) match {
        case Left(error) => Left(error)
        case Right((remaining, color)) =>
          loadPixelLine(loadColor, remaining, remainingPixels - 1, padding, color :: acc)
      }
    }
  }

  @tailrec
  private def loadPixels(
      loadColor: ParseState[String, Color],
      data: CustomInputStream,
      remainingLines: Int,
      width: Int,
      padding: Int,
      acc: Vector[Array[Color]] = Vector()
  ): ParseResult[Vector[Array[Color]]] = {
    if (isEmpty(data) || remainingLines == 0) Right(data -> acc)
    else {
      loadPixelLine(loadColor, data, width, padding) match {
        case Left(error) => Left(error)
        case Right((remaining, line)) =>
          loadPixels(loadColor, remaining, remainingLines - 1, width, padding, line +: acc)
      }
    }
  }

  private def loadHeader(bytes: CustomInputStream): ParseResult[Header] = {
    (for {
      magic <- readString(2).validate(
        BmpImageFormat.supportedFormats,
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
      compressionMethod <- readLENumber(4).validate(
        Set(0, 3, 6),
        _ => "Compression is not supported"
      )
      loadColorMask = compressionMethod == 3 || compressionMethod == 6
      _         <- if (loadColorMask) skipBytes(20) else noop
      redMask   <- if (loadColorMask) readLENumber(4) else State.pure[CustomInputStream, Int](0x00ff0000)
      greenMask <- if (loadColorMask) readLENumber(4) else State.pure[CustomInputStream, Int](0x0000ff00)
      blueMask  <- if (loadColorMask) readLENumber(4) else State.pure[CustomInputStream, Int](0x000000ff)
      _         <- if (loadColorMask) skipBytes(4) else noop // Skip alpha mask (or color space)
      _ <- State.check(
        redMask == 0x00ff0000 && greenMask == 0x0000ff00 && blueMask == 0x000000ff,
        "Unsupported color format (must be either RGB or ARGB)"
      )
      header = Header(magic, size, offset, width, height, bitsPerPixel)
      _ <- skipBytes(offset - (if (loadColorMask) 70 else 34))
    } yield header).run(bytes)
  }

  final def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).flatMap { case (data, header) =>
      val pixels = loadColor(header.bitsPerPixel).flatMap { loadColor =>
        loadPixels(
          loadColor,
          data,
          header.height,
          header.width,
          BmpImageFormat.linePadding(header.width, header.bitsPerPixel)
        )
      }
      pixels.flatMap { case (_, pixelMatrix) =>
        if (pixelMatrix.size != header.height)
          Left(s"Invalid number of lines: Got ${pixelMatrix.size}, expected ${header.height}")
        else if (pixelMatrix.nonEmpty && pixelMatrix.last.size != header.width)
          Left(s"Invalid number of pixels in the last line: Got ${pixelMatrix.last.size}, expected ${header.width}")
        else Right(new RamSurface(pixelMatrix.map(ArraySeq.unsafeWrapArray)))
      }
    }
  }
}
