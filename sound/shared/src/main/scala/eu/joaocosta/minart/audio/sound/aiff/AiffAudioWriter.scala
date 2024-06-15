package eu.joaocosta.minart.audio.sound.aiff

import java.io.OutputStream

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.audio.sound.*
import eu.joaocosta.minart.internal.*

/** Audio writer for AIFF files.
  *
  * https://mmsp.ece.mcgill.ca/Documents/AudioFormats/AIFF/Docs/AIFF-1.3.pdf
  */
trait AiffAudioWriter(sampleRate: Int, bitRate: Int) extends AudioClipWriter {
  private val chunkSize = 128
  require(Set(8, 16, 32).contains(bitRate))

  import AiffAudioWriter.*
  import ByteWriter.*
  import ByteFloatOps.*

  private def convertSample(x: Double): Array[Byte] = bitRate match {
    case 8 =>
      val byte = Math.min(Math.max(-1.0, x), 1.0) * Byte.MaxValue
      Array(byte.toByte)
    case 16 =>
      val short = (Math.min(Math.max(-1.0, x), 1.0) * Short.MaxValue).toInt
      Array(((short >> 8) & 0xff).toByte, (short & 0xff).toByte)
    case 32 =>
      val int = (Math.min(Math.max(-1.0, x), 1.0) * Int.MaxValue).toInt
      Array(((int >> 24) & 0xff).toByte, ((int >> 16) & 0xff).toByte, ((int >> 8) & 0xff).toByte, (int & 0xff).toByte)
  }

  private def storeSsndChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("SSND")
      numBytes   = Sampler.numSamples(clip, sampleRate) * bitRate / 8
      paddedSize = numBytes + (numBytes % 2)
      _ <- writeBENumber(8 + numBytes, 4) // The padding is not included in the chunk size
      _ <- writeBENumber(0, 4)
      _ <- writeBENumber(0, 4)
      _ <- append(
        Sampler.sampleClip(clip, sampleRate).grouped(chunkSize).map(_.iterator.flatMap(convertSample).toArray)
      )
      _ <- writeBytes(List.fill(numBytes % 2)(0))
    } yield ()

  private def storeCommChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("COMM")
      _ <- writeBENumber(18, 4)
      _ <- writeBENumber(1, 2)
      _ <- writeBENumber(Sampler.numSamples(clip, sampleRate), 4)
      _ <- writeBENumber(bitRate, 2)
      _ <- writeExtended(sampleRate)
    } yield ()

  private def storeFormChunk(clip: AudioClip): ByteStreamState[String] = for {
    _ <- writeString("FORM")
    numBytes   = Sampler.numSamples(clip, sampleRate) * bitRate / 8
    paddedSize = numBytes + (numBytes % 2)
    _ <- writeBENumber(
      4 +                  // FORM TYPE
        8 + 18 +           // COMM
        8 + 8 + paddedSize // SSND
      ,
      4
    )
    _ <- writeString("AIFF")
  } yield ()

  final def storeClip(clip: AudioClip, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeFormChunk(clip)
      _ <- storeCommChunk(clip)
      _ <- storeSsndChunk(clip)
    } yield ()
    toOutputStream(state, os)
  }
}

object AiffAudioWriter {
  private object ByteFloatOps {
    import ByteWriter.*

    def writeExtended(x: Double): ByteStreamState[String] = {
      val (sign, absX) = if (x < 0) (0x8000, -x) else (0, x)
      if (x.isNaN) {
        for {
          _ <- writeBENumber(sign | 0x7fff, 2)
          _ <- writeByte(0x40)
          _ <- writeBytes(List.fill(7)(0))
        } yield ()
      } else if (x.isInfinity) {
        for {
          _ <- writeBENumber(sign | 0x7fff, 2)
          _ <- writeBytes(List.fill(8)(0))
        } yield ()
      } else if (absX == 0) {
        writeBytes(List.fill(10)(0))
      } else {
        val bits        = java.lang.Double.doubleToLongBits(x)
        val exp         = ((bits & 0x7ff0000000000000L) >> 52).toInt - 1023
        val mantissa    = (bits & 0x000fffffffffffffL) | 0x0010000000000000L
        val newExp      = exp + 16383
        val newMantissa = mantissa << 11
        val hiMant      = ((newMantissa >> 32) & 0x00000000ffffffffL).toInt
        val loMant      = ((newMantissa) & 0x00000000ffffffffL).toInt
        for {
          _ <- writeBENumber(sign | newExp, 2)
          _ <- writeBENumber(hiMant, 4)
          _ <- writeBENumber(loMant, 4)
        } yield ()
      }
    }
  }
}
