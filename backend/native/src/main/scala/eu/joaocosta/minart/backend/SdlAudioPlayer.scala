package eu.joaocosta.minart.backend

import scala.scalanative.libc._
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.audio._

object SdlAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100

  var currentlyPlaying: Iterator[Byte] = Iterator.empty

  val callback: SDL_AudioCallback = (userdata: Ptr[Byte], stream: Ptr[UByte], len: CInt) => {
    (0 until len).foreach { i =>
      stream(i) = currentlyPlaying.nextOption().getOrElse(0.toByte).toUByte
    }
  }

  def play(wave: AudioWave): Unit = {
    val samples = wave.numSamples(sampleRate)
    currentlyPlaying = wave.byteIterator(sampleRate)
    SDL_InitSubSystem(SDL_INIT_AUDIO)
    val want = stackalloc[SDL_AudioSpec]()
    val have = stackalloc[SDL_AudioSpec]()
    want.freq = sampleRate
    want.format = AUDIO_S8
    want.channels = 1.toUByte
    want.samples = 4096.toUShort // FIXME
    want.callback = callback
    val device: SDL_AudioDeviceID = SDL_OpenAudioDevice(null, 0, want, have, 0)
    SDL_PauseAudioDevice(device, 0)
  }
}
