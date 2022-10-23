package eu.joaocosta.minart.audio.sound.aiff

private[aiff] final case class Header(
    numChannels: Int,
    numSampleFrames: Long,
    sampleSize: Int,
    sampleRate: Double
)
