package eu.joaocosta.minart.audio.sound.qoa

import java.io.InputStream

import scala.annotation.tailrec

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.audio.sound._
import eu.joaocosta.minart.internal._

/** Audio reader for QOA files.
  *
  * https://qoaformat.org/qoa-specification.pdf
  */
trait QoaAudioReader[ByteSeq] extends AudioClipReader {

  val byteReader: ByteReader[ByteSeq]
  import byteReader._

  @tailrec
  private def extractResiduals(res: Long, acc: List[Int] = Nil): List[Int] =
    if (acc.size >= 20) acc
    else extractResiduals(res >> 3, (res & 0x07).toInt :: acc)

  @tailrec
  private def predict(residuals: List[Int], state: QoaState, acc: List[Short] = Nil): (QoaState, List[Short]) =
    residuals match {
      case Nil => (state, acc.reverse)
      case r :: rs =>
        val sample = Math.min(Math.max(Short.MinValue, state.prediction + r), Short.MaxValue).toShort
        predict(rs, state.update(sample, r), sample :: acc)
    }

  private def loadSlice(state: QoaState): ParseState[String, (QoaState, List[Short])] = readBytes(8).map { bytes =>
    val sfQuant          = (bytes(0) & 0xf0) >> 4
    val dequantizedScale = Math.round(Math.pow(sfQuant + 1, 2.75))
    val longResiduals =
      (bytes(0) & 0x0f).toLong << (8 * 7) |
        (bytes(1) & 0xff).toLong << (8 * 6) |
        (bytes(2) & 0xff).toLong << (8 * 5) |
        (bytes(3) & 0xff).toLong << (8 * 4) |
        (bytes(4) & 0xff).toLong << (8 * 3) |
        (bytes(5) & 0xff).toLong << (8 * 2) |
        (bytes(6) & 0xff).toLong << (8 * 1) |
        (bytes(7) & 0xff).toLong
    val residuals = extractResiduals(longResiduals)
    val dequantizedResiduals = residuals.map { res =>
      Vector(0.75, -0.75, 2.5, -2.5, 4.5, -4.5, 7, -7)(res)
    }
    val scaledResiduals = dequantizedResiduals.map { res =>
      val scaled = dequantizedScale * res
      (if (scaled < 0) Math.ceil(scaled - 0.5) else Math.floor(scaled + 0.5)).toInt
    }
    predict(scaledResiduals, state)
  }

  private def loadSlices(
      state: QoaState,
      remainingSlices: Int = 256,
      acc: List[Short] = Nil
  ): ParseState[String, (QoaState, List[Short])] =
    if (remainingSlices == 0) State.pure((state, acc.reverse))
    else
      loadSlice(state).flatMap { case (newState, slice) =>
        loadSlices(newState, remainingSlices - 1, slice.reverse ::: acc)
      }

  private val loadState: ParseState[String, QoaState] = for {
    history0 <- readBENumber(2)
    history1 <- readBENumber(2)
    history2 <- readBENumber(2)
    history3 <- readBENumber(2)
    weight0  <- readBENumber(2)
    weight1  <- readBENumber(2)
    weight2  <- readBENumber(2)
    weight3  <- readBENumber(2)
  } yield QoaState(
    history = Vector(history0, history1, history2, history3).map(_.toShort),
    weights = Vector(weight0, weight1, weight2, weight3).map(_.toShort)
  )

  private def loadFrames(
      remainingFrames: Int,
      acc: Vector[Double] = Vector.empty,
      sampleRate: Int = 0
  ): ParseState[String, AudioClip] = {
    if (remainingFrames == 0)
      State.pure(AudioClip.fromIndexedSeq(acc, sampleRate))
    else {
      val frameData = for {
        numChannels <- readBENumber(1).validate(_ == 1, c => s"Expected a Mono QOA file, got $c channels")
        newSampleRate <- readBENumber(3).validate(
          s => sampleRate == 0 || s == sampleRate,
          s => s"Sample rate changed mid file. Expected $sampleRate, got $s"
        )
        samples <- readBENumber(2)
        numSlices = Math.min(Math.ceil(samples / 20.0).toInt, 256)
        frameSize <- readBENumber(2)
        state     <- loadState
        slices    <- loadSlices(state, numSlices)
        newSamples = slices._2.map(_.toDouble / Short.MaxValue)
      } yield (acc ++ newSamples, newSampleRate)
      frameData.flatMap { case (nextClip, newSampleRate) => loadFrames(remainingFrames - 1, nextClip, newSampleRate) }
    }
  }

  private val loadQoaHeader: ParseState[String, Long] = for {
    _       <- readString(4).validate(_ == "qoaf", m => s"Unsupported magic value: $m. Expected qoaf.")
    samples <- readBENumber(4).validate(_ > 0, _ => "Streaming QOA files are not supported.")
  } yield samples

  def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      samples <- loadQoaHeader
      frames = Math.ceil(samples / (256.0 * 20)).toInt
      clip <- loadFrames(frames)
    } yield clip).run(bytes).map(_._2)
  }

}
