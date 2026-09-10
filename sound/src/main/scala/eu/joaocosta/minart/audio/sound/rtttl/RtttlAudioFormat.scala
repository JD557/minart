package eu.joaocosta.minart.audio.sound.rtttl

import eu.joaocosta.minart.audio.*

/** Audio format RTTTL files.
  */
final class RtttlAudioFormat(val oscilator: Oscillator) extends RtttlAudioReader

object RtttlAudioFormat {
  val defaultFormat =
    new RtttlAudioFormat(Oscillator.sin)
}
