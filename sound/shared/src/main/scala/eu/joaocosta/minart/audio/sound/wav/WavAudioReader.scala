package eu.joaocosta.minart.audio.sound.wav

import java.io.InputStream

import scala.annotation.tailrec
import scala.io.Source
import scala.math.BigDecimal.apply

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio reader for WAV files.
  *
  * http://tiny.systems/software/soundProgrammer/WavFormatDocs.pdf
  */
trait WavAudioReader[ByteSeq] extends AudioClipReader {

  val byteReader: ByteReader[ByteSeq]
  import byteReader._

  private val readId = readString(4)

  private def assembleChunks(header: Header, data: Vector[Byte]): AudioClip = {
    AudioClip.fromIndexedSeq(
      data.map(byte => (java.lang.Byte.toUnsignedInt(byte) - 127) / 127.0),
      header.sampleRate.toDouble
    )
  }

  private def loadChunks(
      header: Option[Header] = None,
      data: Vector[Byte] = Vector.empty
  ): ParseState[String, AudioClip] = {
    if (header.isDefined && data.nonEmpty)
      State.pure(assembleChunks(header.get, data))
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
              bitsPerSample <- readLENumber(2)
              _             <- skipBytes(size - 16)
            } yield Header(numChannels, sampleRate, byteRate, blockAlign, bitsPerSample)
            fmt.flatMap { h =>
              if (data.nonEmpty) State.pure(assembleChunks(h, data))
              else loadChunks(Some(h), data)
            }
          case "data" =>
            readLENumber(4).flatMap { size =>
              readRawBytes(size).flatMap { newData =>
                if (header.isDefined) State.pure(assembleChunks(header.get, newData.toVector))
                else loadChunks(header, newData.toVector)
              }
            }
          case "" => State.error("Reached end of file without all required chunks")
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

  def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      _    <- loadRiffHeader
      clip <- loadChunks()
    } yield clip).run(bytes).right.map(_._2)
  }

}
