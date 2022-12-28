package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

/** Abstraction that provides an `init` operation to create a new audio player.
  *
  * This is helpful to deal with the fact that creating a new audio player is a
  * side-effectful operation.
  */
trait AudioPlayerManager {

  /** Returns a low-level audio player object.
    *
    * @return low-level audio player object
    */
  def init(settings: AudioPlayer.Settings): LowLevelAudioPlayer
}

object AudioPlayerManager {

  def apply(audioPlayerBuilder: () => LowLevelAudioPlayer): AudioPlayerManager = new AudioPlayerManager {
    def init(settings: AudioPlayer.Settings): LowLevelAudioPlayer = {
      val player = audioPlayerBuilder()
      player.init(settings)
      player
    }
  }

  /** Returns [[AudioPlayerManager]] for the default backend for the target platform.
    *
    * @return [[AudioPlayerManager]] using the default backend for the target platform
    */
  def apply()(implicit backend: DefaultBackend[Any, LowLevelAudioPlayer]): AudioPlayerManager =
    AudioPlayerManager(() => LowLevelAudioPlayer.create())
}
