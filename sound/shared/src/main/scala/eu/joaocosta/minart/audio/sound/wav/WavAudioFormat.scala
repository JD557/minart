package eu.joaocosta.minart.audio.sound.wav

/** Audio format WAV files.
  */
final class WavAudioFormat(val bitRate: Int) extends WavAudioReader with WavAudioWriter

object WavAudioFormat {
  val defaultFormat =
    new WavAudioFormat(
      16
    )
}
