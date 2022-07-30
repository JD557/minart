package eu.joaocosta.minart.graphics.image

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.graphics.image.helpers._

/** Image loader for BMP files.
  *
  * Supports uncompressed 24/32bit Windows BMPs.
  */
final class BmpImageLoader[F[_]](byteReader: ByteReader[F]) extends ImageLoader {
  import BmpImageLoader._
  import byteReader._

  private val loadRgbPixel: ParseState[String, Color] =
    readBytes(3)
      .collect(
        { case bytes if bytes.size == 3 => Color(bytes(2), bytes(1), bytes(0)) },
        _ => "Not enough data to read RGB pixel"
      )

  private val loadRgbaPixel: ParseState[String, Color] =
    readBytes(4)
      .collect(
        { case bytes if bytes.size == 4 => Color(bytes(2), bytes(1), bytes(0)) },
        _ => "Not enough data to read RGBA pixel"
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
      val pixels = header.bitsPerPixel match {
        case 24 =>
          loadPixels(loadRgbPixel, data, numPixels)
        case 32 =>
          loadPixels(loadRgbaPixel, data, numPixels)
        case bpp =>
          Left(s"Invalid bits per pixel: $bpp")
      }
      pixels.right.flatMap { case (_, flatPixels) =>
        if (flatPixels.size != numPixels) Left(s"Invalid number of pixels: Got ${flatPixels.size}, expected $numPixels")
        else Right(new RamSurface(flatPixels.sliding(header.width, header.width).toSeq.reverse))
      }
    }
  }
}

object BmpImageLoader {
  val defaultLoader = new BmpImageLoader[Iterator](ByteReader.IteratorByteReader)

  val supportedFormats = Set("BM")

  final case class Header(
      magic: String,
      size: Int,
      offset: Int,
      width: Int,
      height: Int,
      bitsPerPixel: Int
  )
  object Header {
    def fromBytes[F[_]](bytes: F[Int])(byteReader: ByteReader[F]): byteReader.ParseResult[Header] = {
      import byteReader._
      (for {
        magic <- readString(2).validate(
          supportedFormats,
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
          c => c == 0 || c == 3 || c == 6,
          _ => "Compression is not supported"
        )
        loadColorMask = compressionMethod == 3 || compressionMethod == 6
        _         <- if (loadColorMask) skipBytes(20) else noop
        redMask   <- if (loadColorMask) readLENumber(4) else State.pure[F[Int], Int](0x00ff0000)
        greenMask <- if (loadColorMask) readLENumber(4) else State.pure[F[Int], Int](0x0000ff00)
        blueMask  <- if (loadColorMask) readLENumber(4) else State.pure[F[Int], Int](0x000000ff)
        _         <- if (loadColorMask) skipBytes(4) else noop // Skip alpha mask (or color space)
        _ <- State.check(
          redMask == 0x00ff0000 && greenMask == 0x0000ff00 && blueMask == 0x000000ff,
          "Unsupported color format (must be either RGB or ARGB)"
        )
        header = Header(magic, size, offset, width, height, bitsPerPixel)
        _ <- skipBytes(offset - (if (loadColorMask) 70 else 34))
      } yield header).run(bytes)
    }
  }
}
