package eu.joaocosta.minart.audio.sound.wav

/** Audio format WAV files.
  */
final class WavAudioFormat(sampleRate: Int, bitRate: Int)
    extends WavAudioReader
    with WavAudioWriter(sampleRate, bitRate)

object WavAudioFormat {
  val defaultFormat =
    new WavAudioFormat(sampleRate = 44100, bitRate = 16)
}
