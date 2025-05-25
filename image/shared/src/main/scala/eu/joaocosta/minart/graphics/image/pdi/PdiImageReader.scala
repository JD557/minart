package eu.joaocosta.minart.graphics.image.pdi

import java.io.InputStream

import scala.annotation.tailrec
import scala.collection.immutable.ArraySeq

import eu.joaocosta.minart.graphics.*
import eu.joaocosta.minart.graphics.image.*
import eu.joaocosta.minart.internal.*

/** Image reader for PDI files.
  *
  * Supports uncompressed Playdate PDIs.
  */
trait PdiImageReader extends ImageReader {
  import ByteReader.*

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
    loadHeader(bytes).flatMap(_ => loadCellHeader(bytes)).flatMap { case (data, cellHeader) =>
      val emptyColor = Color(0, 0, 0, 0)
      val width      = cellHeader.clipLeft + cellHeader.clipWidth + cellHeader.clipRight
      val leftPad    = Array.fill(cellHeader.clipLeft)(emptyColor)
      val rightPad   = Array.fill(cellHeader.clipRight)(emptyColor)
      for {
        centerColors <- loadBits(data, cellHeader.clipHeight, cellHeader.clipWidth, cellHeader.stride)
        centerMask   <- loadBits(data, cellHeader.clipHeight, cellHeader.clipWidth, cellHeader.stride)
        centerPixels = centerColors._2.zip(centerMask._2).map { (colorLine, maskLine) =>
          colorLine.zip(maskLine).map {
            case (_, false)    => Color(0, 0, 0, 0)
            case (false, true) => Color(0, 0, 0)
            case (true, true)  => Color(255, 255, 255)
          }
        }
        pixels =
          Vector.fill(cellHeader.clipTop)(Array.fill(width)(emptyColor)) ++
            centerPixels.map(center => leftPad ++ center ++ rightPad) ++
            Vector.fill(cellHeader.clipBottom)(Array.fill(width)(emptyColor))
      } yield (new RamSurface(pixels.map(ArraySeq.unsafeWrapArray)))
    }
  }
}
