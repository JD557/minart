package eu.joaocosta.minart.audio.sound.aiff

/** Audio format AIFF files.
  */
final class AiffAudioFormat(val bitRate: Int) extends AiffAudioReader with AiffAudioWriter

object AiffAudioFormat {
  val defaultFormat =
    new AiffAudioFormat(16)
}
