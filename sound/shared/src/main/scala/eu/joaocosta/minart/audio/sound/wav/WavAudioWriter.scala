package eu.joaocosta.minart.audio.sound.wav

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio writer for WAV files.
  *
  * http://tiny.systems/software/soundProgrammer/WavFormatDocs.pdf
  */
trait WavAudioWriter[ByteSeq] extends AudioClipWriter {
  val byteWriter: ByteWriter[ByteSeq]

  val sampleRate = 44100
  val chunkSize  = 128
  def bitRate: Int
  require(Set(8, 16, 32).contains(bitRate))

  import byteWriter._

  private def convertSample(x: Double): List[Int] = bitRate match {
    case 8 =>
      List((math.min(math.max(-1.0, x), 1.0) * Byte.MaxValue).toInt + 127)
    case 16 =>
      val short = (math.min(math.max(-1.0, x), 1.0) * Short.MaxValue).toInt
      List(short & 0xff, (short >> 8) & 0xff)
    case 32 =>
      val int = (math.min(math.max(-1.0, x), 1.0) * Int.MaxValue).toInt
      List(int & 0xff, (int >> 8) & 0xff, (int >> 16) & 0xff, (int >> 24) & 0xff)
  }

  @tailrec
  private def storeData(
      iterator: Iterator[Seq[Double]],
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (!iterator.hasNext) acc
    else {
      val chunk = iterator.next().flatMap(convertSample)
      storeData(iterator, acc.flatMap(_ => writeBytes(chunk)))
    }
  }

  private def storeDataChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("data")
      _ <- writeLENumber(clip.numSamples(sampleRate) * bitRate / 8, 4)
      _ <- storeData(clip.iterator(sampleRate).grouped(chunkSize))
    } yield ()

  private def storeFmtChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("fmt ")
      _ <- writeLENumber(16, 4)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(sampleRate, 4)
      _ <- writeLENumber(sampleRate * bitRate / 8, 4)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(bitRate, 2)
    } yield ()

  private def storeRiffHeader(clip: AudioClip): ByteStreamState[String] = for {
    _ <- writeString("RIFF")
    _ <- writeLENumber(36 + clip.numSamples(sampleRate) * bitRate / 8, 4)
    _ <- writeString("WAVE")
  } yield ()

  def storeClip(clip: AudioClip, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeRiffHeader(clip)
      _ <- storeFmtChunk(clip)
      _ <- storeDataChunk(clip)
    } yield ()
    toOutputStream(state, os)
  }
}
