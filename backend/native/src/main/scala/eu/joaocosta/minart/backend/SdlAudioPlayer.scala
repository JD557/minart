package eu.joaocosta.minart.backend

import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.audio._

object SdlAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100

  def play(wave: AudioWave): Unit = {
    val samples = wave.numSamples(sampleRate)
    SDL_InitSubSystem(SDL_INIT_AUDIO)
    val want = stackalloc[SDL_AudioSpec]()
    val have = stackalloc[SDL_AudioSpec]()
    want.freq = sampleRate
    want.format = AUDIO_S8
    want.channels = 1.toUByte
    want.samples = 4096.toUShort // FIXME
    val device: SDL_AudioDeviceID = SDL_OpenAudioDevice(null, 0, want, have, 0)
    Zone { implicit z =>
      val arr = alloc[Byte](samples)
      wave.byteIterator(sampleRate).zipWithIndex.foreach { case (x, i) =>
        arr(i) = x
      }
      SDL_QueueAudio(device, arr, samples.toUInt)
    }
    SDL_PauseAudioDevice(device, 0)
  }
}
