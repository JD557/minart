package eu.joaocosta.minart.audio.sound.aiff

import java.io.InputStream

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio reader for AIFF files.
  *
  * https://mmsp.ece.mcgill.ca/Documents/AudioFormats/AIFF/Docs/AIFF-1.3.pdf
  */
trait AiffAudioReader extends AudioClipReader {

  import AiffAudioReader._
  import ByteReader._
  import ByteFloatOps._

  private val readId = readString(4)

  private val loadChunkHeader: ParseState[String, ChunkHeader] = for {
    id   <- readId
    size <- readBENumber(4)
  } yield ChunkHeader(id, size)

  private def assembleChunks(commHeader: Header, data: Vector[Byte]): Either[String, AudioClip] = {
    val eitherSeq: Either[String, Vector[Double]] = commHeader.sampleSize match {
      case 8 =>
        Right(data.map(byte => byte / Byte.MaxValue.toDouble))
      case 16 =>
        Right(
          data
            .grouped(2)
            .collect { case Vector(high, low) =>
              (((high & 0xff) << 8) |
                (low & 0xff)).toShort
            }
            .map(_ / Short.MaxValue.toDouble)
            .toVector
        )
      case 32 =>
        Right(
          data
            .grouped(4)
            .collect { case vec @ Vector(high, midH, midL, low) =>
              (((high & 0xff) << 24) |
                ((midH & 0xff) << 16) |
                ((midL & 0xff) << 8) |
                ((low & 0xff)))
            }
            .map(_ / Int.MaxValue.toDouble)
            .toVector
        )
      case bitrate =>
        Left(s"Unsupported sample size: $bitrate")
    }
    eitherSeq.map { (seq: Vector[Double]) =>
      AudioClip.fromIndexedSeq(
        seq,
        commHeader.sampleRate
      )
    }
  }

  private def loadChunks(
      commHeader: Option[Header] = None,
      data: Vector[Byte] = Vector.empty
  ): ParseState[String, AudioClip] = {
    if (commHeader.exists(comm => data.size >= comm.numSampleFrames * comm.sampleSize / 8))
      State.fromEither(assembleChunks(commHeader.get, data))
    else
      loadChunkHeader.flatMap { header =>
        header.id match {
          case "COMM" =>
            val comm = for {
              _               <- State.check(header.size == 18, s"Invalid COMM chunk size: ${header.size}")
              numChannels     <- readBENumber(2).validate(_ == 1, c => s"Expected a Mono AIFF file, got $c channels")
              numSampleFrames <- readBENumber(4)
              sampleSize <- readBENumber(2).validate(
                Set(8, 16, 32),
                b => s"Expected a 8, 16 or 32 bit AIFF file, got $b bit"
              )
              sampleRate <- readExtended
            } yield Header(numChannels, numSampleFrames, sampleSize, sampleRate)
            comm.flatMap { c =>
              if (data.size >= c.numSampleFrames * c.sampleSize / 8)
                State.fromEither(assembleChunks(c, data))
              else loadChunks(Some(c), data)
            }
          case "SSND" =>
            val ssnd = for {
              offset    <- readBENumber(4).validate(_ == 0, off => s"Unsupported offset: $off")
              blockSize <- readBENumber(4).validate(_ == 0, blk => s"Unsupported block size: $blk")
              data      <- readRawBytes(header.size - 8)
            } yield data
            ssnd.flatMap { s =>
              val newData = data ++ s
              if (commHeader.exists(c => newData.size >= c.numSampleFrames * c.sampleSize / 8))
                State.fromEither(assembleChunks(commHeader.get, newData))
              else loadChunks(commHeader, newData)
            }
          case "" =>
            State.error("Reached end of file without all required chunks")
          case other =>
            if (header.paddedSize == 0) State.error(s"Received invalid chunk: $header")
            else skipBytes(header.paddedSize).flatMap(_ => loadChunks(commHeader, data))
        }
      }
  }

  final def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      formChunk <- loadChunkHeader.validate(_.id == "FORM", c => s"Invalid FORM chunk id: ${c.id}")
      formType  <- readId.validate(_ == "AIFF", t => s"Unsupported formType: $t. Only AIFF is supported.")
      clip      <- loadChunks()
    } yield clip).run(bytes).map(_._2)
  }

}

object AiffAudioReader {
  case class ChunkHeader(id: String, size: Int) {
    val paddedSize = size + (size % 2)
  }

  private object ByteFloatOps {
    import ByteReader._
    // Ported from https://github.com/python/cpython/blob/dcb342b5f9d931b030ca310bf3e175bbc54df5aa/Lib/aifc.py#L184-L199
    val readExtended: ParseState[String, Double] = for {
      head <- readBENumber(2)
      sign  = if ((head & 0x8000) != 0) -1 else 1
      expon = head & 0x7fff
      rawMant <- readBENumberLong(8)
      f =
        if (expon == 0 && rawMant == 0) 0.0
        else if (expon == 0x7fff) Double.MaxValue
        else {
          val mant =
            if (rawMant < 0) rawMant.toDouble + math.pow(2, 64)
            else rawMant.toDouble
          mant * math.pow(2.0, (expon - 16383 - 63))
        }
    } yield sign * f
  }
}
