package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {
  def play(wave: AudioWave): Unit
}

object AudioPlayer {
  def apply()(implicit backend: DefaultBackend[Any, AudioPlayer]): AudioPlayer =
    backend.defaultValue()
}
