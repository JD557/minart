package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {
  def play(sample: AudioSample): Unit
}

object AudioPlayer {
  def apply()(implicit backend: DefaultBackend[Any, AudioPlayer]): AudioPlayer =
    backend.defaultValue()
}
