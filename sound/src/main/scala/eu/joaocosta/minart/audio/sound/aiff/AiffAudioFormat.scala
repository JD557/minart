package eu.joaocosta.minart.audio.sound.aiff

/** Audio format AIFF files.
  */
final class AiffAudioFormat(sampleRate: Int, bitRate: Int)
    extends AiffAudioReader
    with AiffAudioWriter(sampleRate, bitRate)

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat(sampleRate = 44100, bitRate = 16)
}
