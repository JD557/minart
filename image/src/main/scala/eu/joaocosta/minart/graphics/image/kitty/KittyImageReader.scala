package eu.joaocosta.minart.graphics.image.kitty

import java.io.InputStream

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image reader for Kitty images.
  *
  * Supports 24 and 32 bit raw pixel data, using a=T or a=t and the direct transmission medium
  */
trait KittyImageReader extends ImageReader {
  import ByteReader.*

  private val ESC = '\u001b'
  private val b64 = java.util.Base64.getDecoder()

  private def loadHeader(bytes: CustomInputStream): ParseResult[Header] = {
    (
      for {
        _          <- readByte.validate(_.contains(ESC.toInt), _ => "Missing escape character")
        _          <- readByte.validate(_.contains('_'.toInt), _ => "Invalid CSI command")
        _          <- readByte.validate(_.contains('G'.toInt), _ => "Invalid control character")
        headerData <- readWhile(_ != ';'.toInt)
        _          <- skipBytes(1) // Skip ;
        headerMap = headerData
          .map(_.toChar)
          .mkString("")
          .split(",")
          .map(_.split("="))
          .map(arr => arr(0) -> arr(1))
          .toMap
        header = Header.fromKeyMap(headerMap)
        _ <- State.check(header.action == "t" || header.action == "T", s"Unsupported action: ${header.action}")
        _ <- State.check(header.medium == "d", s"Unsupported medium: ${header.medium}")
      } yield header
    ).run(bytes)
  }

  final def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).flatMap { case (data, header) =>
      readWhile(_ != ESC)
        .map(_.map(_.toByte).toArray)
        .map(b64.decode)
        .flatMap { bytes =>
          header.colorRange match {
            case 24 =>
              State.pure(bytes.sliding(3, 3).map { case Array(r, g, b) =>
                Color(r, g, b)
              })
            case 32 =>
              State.pure(bytes.sliding(4, 4).map { case Array(r, g, b, a) =>
                Color(r, g, b, a)
              })
            case f => State.error(s"Unsupported color range $f")
          }
        }
        .run(data)
        .map((_, buffer) => RamSurface(buffer.toArray, header.height))
    }
  }
}
