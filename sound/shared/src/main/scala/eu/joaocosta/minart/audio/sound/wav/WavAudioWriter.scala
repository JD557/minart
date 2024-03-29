package eu.joaocosta.minart.audio.sound.wav

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.internal.*

/** Audio writer for WAV files.
  *
  * http://tiny.systems/software/soundProgrammer/WavFormatDocs.pdf
  */
trait WavAudioWriter(sampleRate: Int, bitRate: Int) extends AudioClipWriter {
  final val chunkSize = 128
  require(Set(8, 16, 32).contains(bitRate))

  import ByteWriter.*

  private def convertSample(x: Double): List[Int] = bitRate match {
    case 8 =>
      List((Math.min(Math.max(-1.0, x), 1.0) * Byte.MaxValue).toInt + 127)
    case 16 =>
      val short = (Math.min(Math.max(-1.0, x), 1.0) * Short.MaxValue).toInt
      List(short & 0xff, (short >> 8) & 0xff)
    case 32 =>
      val int = (Math.min(Math.max(-1.0, x), 1.0) * Int.MaxValue).toInt
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
      _ <- writeLENumber(Sampler.numSamples(clip, sampleRate) * bitRate / 8, 4)
      _ <- storeData(Sampler.sampleClip(clip, sampleRate).grouped(chunkSize))
    } yield ()

  private val storeFmtChunk: ByteStreamState[String] =
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
    _ <- writeLENumber(36 + Sampler.numSamples(clip, sampleRate) * bitRate / 8, 4)
    _ <- writeString("WAVE")
  } yield ()

  final def storeClip(clip: AudioClip, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeRiffHeader(clip)
      _ <- storeFmtChunk
      _ <- storeDataChunk(clip)
    } yield ()
    toOutputStream(state, os)
  }
}
