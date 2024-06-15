package eu.joaocosta.minart.graphics.image.pdi

import java.io.InputStream

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image reader for PDI files.
  *
  * Supports uncompressed Playdate PDIs.
  */
trait PdiImageReader extends ImageReader {
  import ByteReader.*

  private def bitsFromByte(byte: Byte, entries: Int): List[Boolean] =
    if (entries > 0) {
      val colorBit = (byte & 0x80) >> 7
      (colorBit == 1) :: bitsFromByte(((byte << 1) & 0xff).toByte, entries - 1)
    } else Nil

  private def loadBitLine(
      data: CustomInputStream,
      width: Int,
      lineBytes: Int
  ): Array[Boolean] = {
    val bytes = data.readNBytes(lineBytes)
    bytes.flatMap(b => bitsFromByte(b, 8)).take(width)
  }

  private def loadHeader(bytes: CustomInputStream): ParseResult[Header] = {
    (for {
      magic <- readString(12).validate(
        PdiImageFormat.supportedFormats,
        m => s"Unsupported format: $m."
      )
      flags       <- readLENumber(4)
      compression <- State.cond((flags & 0x80000000) == 0, false, "Unsuported compression")
      header = Header(magic, compression)
    } yield header).run(bytes)
  }

  private def loadCellHeader(bytes: CustomInputStream): ParseResult[CellHeader] = {
    (for {
      clipWidth  <- readLENumber(2)
      clipHeight <- readLENumber(2)
      cellStride <- readLENumber(2)
      clipLeft   <- readLENumber(2)
      clipRight  <- readLENumber(2)
      clipTop    <- readLENumber(2)
      clipBottom <- readLENumber(2)
      flags      <- readLENumber(2)
      transparency = (flags & 0x0003) != 0
      header = CellHeader(clipWidth, clipHeight, cellStride, clipLeft, clipRight, clipTop, clipBottom, transparency)
    } yield header).run(bytes)
  }

  final def loadImage(is: InputStream): Either[String, RamSurface] = {
    val bytes = fromInputStream(is)
    loadHeader(bytes).flatMap(_ => loadCellHeader(bytes)).map { case (data, cellHeader) =>
      val emptyColor = Color(0, 0, 0, 0)
      val width      = cellHeader.clipLeft + cellHeader.clipWidth + cellHeader.clipRight
      val leftPad    = Array.fill(cellHeader.clipLeft)(emptyColor)
      val rightPad   = Array.fill(cellHeader.clipRight)(emptyColor)
      val centerColors =
        (0 until cellHeader.clipHeight).map { _ =>
          loadBitLine(data, cellHeader.clipWidth, cellHeader.stride)
        }.toVector
      val centerMask =
        (0 until cellHeader.clipHeight).map { _ =>
          loadBitLine(data, cellHeader.clipWidth, cellHeader.stride)
        }.toVector
      val centerPixels = centerColors.zip(centerMask).map { (colorLine, maskLine) =>
        colorLine.zip(maskLine).map {
          case (_, false)    => Color(0, 0, 0, 0)
          case (false, true) => Color(0, 0, 0)
          case (true, true)  => Color(255, 255, 255)
        }
      }
      val pixels =
        Vector.fill(cellHeader.clipTop)(Array.fill(width)(emptyColor)) ++
          centerPixels.map(center => leftPad ++ center ++ rightPad) ++
          Vector.fill(cellHeader.clipBottom)(Array.fill(width)(emptyColor))
      new RamSurface(pixels)
    }
  }
}
