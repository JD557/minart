package eu.joaocosta.minart.audio.sound.aiff

import java.io.OutputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio writer for AIFF files.
  *
  * https://mmsp.ece.mcgill.ca/Documents/AudioFormats/AIFF/Docs/AIFF-1.3.pdf
  */
trait AiffAudioWriter[ByteSeq] extends AudioClipWriter {
  val byteWriter: ByteWriter[ByteSeq]

  val sampleRate = 44100
  val chunkSize  = 128

  import AiffAudioWriter._
  private val byteFloatOps = new ByteFloatOps(byteWriter)
  import byteWriter._
  import byteFloatOps._

  @tailrec
  private def storeData(
      iterator: Iterator[Seq[Byte]],
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (!iterator.hasNext) acc
    else {
      val chunk = iterator.next().map(java.lang.Byte.toUnsignedInt)
      storeData(iterator, acc.flatMap(_ => writeBytes(chunk)))
    }
  }

  private def storeSsndChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("SSND")
      numSamples = clip.numSamples(sampleRate)
      paddedSize = numSamples + (numSamples % 2)
      _ <- writeBENumber(paddedSize + 8, 4)
      _ <- writeBENumber(0, 4)
      _ <- writeBENumber(0, 4)
      _ <- storeData(clip.byteIterator(sampleRate).grouped(chunkSize))
    } yield ()

  private def storeCommChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("COMM")
      _ <- writeBENumber(18, 4)
      _ <- writeBENumber(1, 2)
      numSamples = clip.numSamples(sampleRate)
      paddedSize = numSamples + (numSamples % 2)
      _ <- writeBENumber(paddedSize, 4)
      _ <- writeBENumber(8, 2)
      _ <- writeExtended(sampleRate)
    } yield ()

  private def storeFormChunk(clip: AudioClip): ByteStreamState[String] = for {
    _ <- writeString("FORM")
    numSamples = clip.numSamples(sampleRate)
    paddedSize = numSamples + (numSamples % 2)
    _ <- writeBENumber(4 + 18 + paddedSize + 8, 4)
    _ <- writeString("AIFF")
  } yield ()

  def storeClip(clip: AudioClip, os: OutputStream): Either[String, Unit] = {
    val state = for {
      _ <- storeFormChunk(clip)
      _ <- storeCommChunk(clip)
      _ <- storeSsndChunk(clip)
    } yield ()
    toOutputStream(state, os)
  }
}

object AiffAudioWriter {
  private final class ByteFloatOps[ByteSeq](val byteWriter: ByteWriter[ByteSeq]) {
    import byteWriter._

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
        val exp         = java.lang.Math.getExponent(x)
        val mantissa    = (java.lang.Double.doubleToLongBits(x) & 0x000fffffffffffffL) | 0x0010000000000000L
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
