package eu.joaocosta.minart.audio.sound.wav

private[wav] final case class Header(
    numChannels: Int,
    sampleRate: Long,
    byteRate: Long,
    blockAlign: Int,
    bitsPerSample: Int
)
