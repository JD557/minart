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

  import byteWriter._

  @tailrec
  private def storeData(
      iterator: Iterator[Seq[Byte]],
      acc: ByteStreamState[String] = emptyStream
  ): ByteStreamState[String] = {
    if (!iterator.hasNext) acc
    else {
      val chunk = iterator.next().map(_.toInt + 127)
      storeData(iterator, acc.flatMap(_ => writeBytes(chunk)))
    }
  }

  private def storeDataChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("data")
      _ <- writeLENumber(clip.numSamples(44100), 4)
      _ <- storeData(clip.byteIterator(44100).grouped(128))
    } yield ()

  private def storeFmtChunk(clip: AudioClip): ByteStreamState[String] =
    for {
      _ <- writeString("fmt ")
      _ <- writeLENumber(16, 4)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(44100, 4)
      _ <- writeLENumber(44100, 4)
      _ <- writeLENumber(1, 2)
      _ <- writeLENumber(8, 2)
    } yield ()

  private def storeRiffHeader(clip: AudioClip): ByteStreamState[String] = for {
    _ <- writeString("RIFF")
    _ <- writeLENumber(36 + clip.numSamples(44100), 4)
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
