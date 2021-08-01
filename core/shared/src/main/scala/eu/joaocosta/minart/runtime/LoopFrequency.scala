package eu.joaocosta.minart.runtime

import scala.concurrent.duration._

/** Definition of a loop iteration frequency. */
sealed trait LoopFrequency

object LoopFrequency {

  /** Frequency defined by duration.
    *
    * @param millis duration in millis
    */
  final case class LoopDuration(millis: Long) extends LoopFrequency

  /** Uncapped loop frequency. */
  final case object Uncapped extends LoopFrequency

  /** 60 Hz. */
  final val hz60 = fromHz(60)

  /** 50 Hz. */
  final val hz50 = fromHz(50)

  /** 30 Hz. */
  final val hz30 = fromHz(30)

  /** 24 Hz. */
  final val hz24 = fromHz(24)

  /** 15 Hz. */
  final val hz15 = fromHz(15)

  /** Builds a [[LoopFrequency]] from a Scala duration.
    *
    * @param duration minimum loop iteration duration
    */
  def fromDuration(duration: FiniteDuration): LoopFrequency =
    if (duration.toMillis <= 0) Uncapped
    else LoopDuration(duration.toMillis)

  /** Builds a [[LoopFrequency]] in Hz.
    */
  def fromHz(hz: Int): LoopFrequency = {
    if (hz <= 0 || 1000 / hz == 0) Uncapped
    else LoopDuration(1000 / hz)
  }
}
