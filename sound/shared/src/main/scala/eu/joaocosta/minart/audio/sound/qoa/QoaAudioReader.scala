package eu.joaocosta.minart.audio.sound.qoa

import java.io.InputStream

import scala.annotation.tailrec
import scala.io.Source
import scala.math.BigDecimal.apply

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
        val sample = math.min(math.max(Short.MinValue, state.prediction + r), Short.MaxValue).toShort
        predict(rs, state.update(sample, r), sample :: acc)
    }

  private def loadSlice(state: QoaState): ParseState[String, (QoaState, List[Short])] = readBytes(8).map { bytes =>
    val sfQuant          = (bytes(0) & 0xf0) >> 4
    val dequantizedScale = math.round(math.pow(sfQuant + 1, 2.75))
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
      (if (scaled < 0) math.ceil(scaled - 0.5) else math.floor(scaled + 0.5)).toInt
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

  private def loadChunks(
      remainingFrames: Int,
      clip: AudioClip = AudioClip.empty
  ): ParseState[String, AudioClip] = {
    if (remainingFrames == 0)
      State.pure(clip)
    else {
      val shortClip = for {
        numChannels <- readBENumber(1).validate(_ == 1, c => s"Expected a Mono QOA file, got $c channels")
        sampleRate  <- readBENumber(3)
        samples     <- readBENumber(2)
        frameSize   <- readBENumber(2)
        state       <- loadState
        slice       <- loadSlices(state)
      } yield AudioClip.fromIndexedSeq(slice._2.map(_.toDouble / Short.MaxValue).toVector, sampleRate)
      shortClip.flatMap(c => loadChunks(remainingFrames - 1, clip.append(c)))
    }
  }

  private val loadQoaHeader: ParseState[String, Long] = for {
    _       <- readString(4).validate(_ == "qoaf", m => s"Unsupported magic value: $m. Expected qoaf")
    samples <- readBENumber(4)
  } yield samples

  def loadClip(is: InputStream): Either[String, AudioClip] = {
    val bytes = fromInputStream(is)
    (for {
      samples <- loadQoaHeader
      frames = math.ceil(samples / (256.0 * 20)).toInt
      clip <- loadChunks(frames)
    } yield clip).run(bytes).map(_._2)
  }

}
