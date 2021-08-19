package eu.joaocosta.minart.core

import scala.concurrent.duration._

/** Frame rate definition. */
sealed trait FrameRate

object FrameRate {

  /** Frame rate defined by a frame duration.
    *
    * @param millis
    *   minimum frame duration
    */
  final case class FrameDuration(millis: Long) extends FrameRate

  /** Uncapped frame rate. */
  final case object Uncapped extends FrameRate

  /** 60 FPS. */
  final val fps60 = fromFps(60)

  /** 50 FPS. */
  final val fps50 = fromFps(50)

  /** 30 FPS. */
  final val fps30 = fromFps(30)

  /** 24 FPS. */
  final val fps24 = fromFps(24)

  /** 15 FPS. */
  final val fps15 = fromFps(15)

  /** Builds a [[FrameRate]] from a Scala duration.
    *
    * @param duration
    *   minimum frame duration
    */
  def fromDuration(duration: FiniteDuration): FrameRate =
    if (duration.toMillis <= 0) Uncapped
    else FrameDuration(duration.toMillis)

  /** Builds a [[FrameRate]] according to a set of FPS.
    */
  def fromFps(fps: Int): FrameRate = {
    if (fps <= 0) Uncapped
    else FrameDuration(1000 / fps)
  }
}
