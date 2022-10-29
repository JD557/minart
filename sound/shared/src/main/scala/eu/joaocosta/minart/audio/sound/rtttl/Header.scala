package eu.joaocosta.minart.audio.sound.rtttl

private[rtttl] final case class Header(
    name: String,
    duration: Int,
    octave: Int,
    beat: Int
)
