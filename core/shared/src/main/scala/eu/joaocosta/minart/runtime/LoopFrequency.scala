package eu.joaocosta.minart.runtime

import scala.concurrent.duration._

/** Definition of a loop iteration frequency.
  *
  *  This is commonly used to represent the desired framerate.
  */
sealed trait LoopFrequency {

  /** Returns the duration of each loop interval */
  def toDuration: Duration
}

object LoopFrequency {

  /** Frequency defined by duration.
    *
    * @param nanos duration in nanoseconds
    */
  final case class LoopDuration(nanos: Long) extends LoopFrequency {
    val millis                     = nanos / 1000000
    def toDuration: FiniteDuration = millis.milliseconds
  }

  /** Uncapped loop frequency. */
  case object Uncapped extends LoopFrequency {
    def toDuration: FiniteDuration = 0.milliseconds
  }

  /** Run a single iteration */
  case object Never extends LoopFrequency {
    def toDuration: Duration = Duration.Inf
  }

  /** 60 Hz. */
  val hz60: LoopDuration = fromHz(60).asInstanceOf[LoopDuration]

  /** 50 Hz. */
  val hz50: LoopDuration = fromHz(50).asInstanceOf[LoopDuration]

  /** 30 Hz. */
  val hz30: LoopDuration = fromHz(30).asInstanceOf[LoopDuration]

  /** 24 Hz. */
  val hz24: LoopDuration = fromHz(24).asInstanceOf[LoopDuration]

  /** 15 Hz. */
  val hz15: LoopDuration = fromHz(15).asInstanceOf[LoopDuration]

  /** Builds a [[LoopFrequency]] from a Scala duration.
    *
    * @param duration minimum loop iteration duration
    */
  def fromDuration(duration: Duration): LoopFrequency =
    if (!duration.isFinite) Never
    else if (duration.toMillis <= 0) Uncapped
    else LoopDuration(duration.toMillis)

  /** Builds a [[LoopFrequency]] in Hz.
    */
  def fromHz(hz: Int): LoopFrequency = {
    if (hz <= 0) Never
    else if (1000 / hz == 0) Uncapped
    else LoopDuration(1000000000 / hz)
  }
}
