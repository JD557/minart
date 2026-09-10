package eu.joaocosta.minart.audio.sound.wav

import java.io.InputStream

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.internal.*

/** Audio reader for WAV files.
  *
  * http://tiny.systems/software/soundProgrammer/WavFormatDocs.pdf
  */
trait WavAudioReader extends AudioClipReader {

  import ByteReader.*

  private val readId = readString(4)

  private def assembleChunks(header: Header, data: Vector[Byte]): Either[String, AudioClip] = {
    val eitherSeq: Either[String, Vector[Double]] = header.bitsPerSample match {
      case 8 =>
        Right(data.map(byte => (java.lang.Byte.toUnsignedInt(byte) - 127) / Byte.MaxValue.toDouble))
      case 16 =>
        Right(
          data
            .grouped(2)
            .collect { case Vector(low, high) =>
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
            .collect { case vec @ Vector(low, midL, midH, high) =>
              (((high & 0xff) << 24) |
                ((midH & 0xff) << 16) |
                ((midL & 0xff) << 8) |
                ((low & 0xff)))
            }
            .map(_ / Int.MaxValue.toDouble)
            .toVector
        )
      case bitrate =>
        Left(s"Unsupported bits per sample: $bitrate")
    }
    eitherSeq.map { (seq: Vector[Double]) =>
      AudioClip.fromIndexedSeq(
        seq,
        header.sampleRate.toDouble
      )
    }
  }

  private def loadChunks(
      header: Option[Header] = None,
      data: Vector[Byte] = Vector.empty
  ): ParseState[String, AudioClip] = {
    if (header.isDefined && data.nonEmpty)
      State.fromEither(assembleChunks(header.get, data))
    else
      readId.flatMap { id =>
        id match {
          case "fmt " =>
            val fmt = for {
              size          <- readLENumber(4)
              audioFormat   <- readLENumber(2).validate(_ == 1, f => s"Expected PCM audio (format = 1), got format $f")
              numChannels   <- readLENumber(2).validate(_ == 1, c => s"Expected a Mono WAV file, got $c channels")
              sampleRate    <- readLENumber(4)
              byteRate      <- readLENumber(4)
              blockAlign    <- readLENumber(2)
              bitsPerSample <- readLENumber(2).validate(
                Set(8, 16, 32),
                b => s"Expected 8, 16 or 32 bit sound, got $b bit sound"
              )
              _ <- skipBytes(size - 16)
            } yield Header(numChannels, sampleRate, byteRate, blockAlign, bitsPerSample)
            fmt.flatMap { h =>
              if (data.nonEmpty) State.fromEither(assembleChunks(h, data))
              else loadChunks(Some(h), data)
            }
          case "data" =>
            readLENumber(4).flatMap { size =>
              readRawBytes(size).flatMap { newData =>
                if (header.isDefined) State.fromEither(assembleChunks(header.get, newData.toVector))
                else loadChunks(header, newData.toVector)
              }
            }
          case ""    => State.error("Reached end of file without all required chunks")
          case other =>
            readLENumber(4).flatMap(size => skipBytes(size)).flatMap(_ => loadChunks(header, data))
        }
      }
  }

  private val loadRiffHeader: ParseState[String, Unit] = for {
    _ <- readString(4).validate(_ == "RIFF", m => s"Unsupported container format: $m. Expected RIFF")
    _ <- readLENumber(4)
    _ <- readString(4).validate(_ == "WAVE", m => s"Unsupported format: $m. Expected WAVE")
  } yield ()

  final def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      _    <- loadRiffHeader
      clip <- loadChunks()
    } yield clip).run(bytes).map(_._2)
  }

}
