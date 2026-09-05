package eu.joaocosta.minart.audio

/**  Definitions of how a channel should be mixed.
  *
  *  @param volume the channel volume from 0.0 to 1.0
  */
final case class AudioMix(volume: Double = 1.0)
