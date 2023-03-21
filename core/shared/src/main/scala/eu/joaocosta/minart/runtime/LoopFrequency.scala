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
    * @param millis duration in millis
    */
  final case class LoopDuration(millis: Long) extends LoopFrequency {
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
  final val hz60: LoopDuration = fromHz(60).asInstanceOf[LoopDuration]

  /** 50 Hz. */
  final val hz50: LoopDuration = fromHz(50).asInstanceOf[LoopDuration]

  /** 30 Hz. */
  final val hz30: LoopDuration = fromHz(30).asInstanceOf[LoopDuration]

  /** 24 Hz. */
  final val hz24: LoopDuration = fromHz(24).asInstanceOf[LoopDuration]

  /** 15 Hz. */
  final val hz15: LoopDuration = fromHz(15).asInstanceOf[LoopDuration]

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
    else LoopDuration(1000 / hz)
  }
}
