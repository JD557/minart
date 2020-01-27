package eu.joaocosta.minart

import scala.concurrent.duration._

/** Frame rate definition */
sealed trait FrameRate

object FrameRate {
  /**
   * Frame rate defined by a frame duration
   *
   * @param millis minimum frame duration
   */
  final case class FrameDuration(millis: Long) extends FrameRate

  /** Uncapped frame rate */
  final case object Uncapped extends FrameRate

  /** 60 FPS */
  final val fps60 = FrameDuration(1000 / 60)
  /** 30 FPS */
  final val fps30 = FrameDuration(1000 / 30)
  /** 24 FPS */
  final val fps24 = FrameDuration(1000 / 24)

  /**
   * Builds a [[FrameRate]] from a scala duration
   *
   * @param duration minimum frame duration
   */
  def fromDuration(duration: FiniteDuration): FrameRate =
    if (duration.toMillis <= 0) Uncapped
    else (FrameDuration(duration.toMillis))
}
