package eu.joaocosta.minart.audio.sound.wav

import java.io.OutputStream

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.internal.*

/** Audio writer for WAV files.
  *
  * http://tiny.systems/software/soundProgrammer/WavFormatDocs.pdf
  */
trait WavAudioWriter(sampleRate: Int, bitRate: Int) extends AudioClipWriter {
  final val chunkSize = 128
  require(Set(8, 16, 32).contains(bitRate), "Unsupported bit rate")

  import ByteWriter.*

  private def convertSample(x: Double): Array[Byte] = bitRate match {
    case 8 =>
      val byte = (Math.min(Math.max(-1.0, x), 1.0) * Byte.MaxValue).toInt + 127
      Array(byte.toByte)
    case 16 =>
      val short = (Math.min(Math.max(-1.0, x), 1.0) * Short.MaxValue).toInt
      Array((short & 0xff).toByte, ((short >> 8) & 0xff).toByte)
    case 32 =>
      val int = (Math.min(Math.max(-1.0, x), 1.0) * Int.MaxValue).toInt
      Array((int & 0xff).toByte, ((int >> 8) & 0xff).toByte, ((int >> 16) & 0xff).toByte, ((int >> 24) & 0xff).toByte)
  }

  private def storeDataChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("data")
      _ <- writeLENumber(Sampler.numSamples(clip, sampleRate) * bitRate / 8, 4)
      _ <- append(
        Sampler.sampleClip(clip, sampleRate).grouped(chunkSize).map(_.iterator.flatMap(convertSample).toArray)
      )
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
