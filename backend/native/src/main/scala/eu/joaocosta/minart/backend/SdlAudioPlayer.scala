package eu.joaocosta.minart.backend

import scala.scalanative.libc._
import scala.scalanative.libc.stdlib.malloc
import scala.scalanative.runtime.ByteArray
import scala.scalanative.unsafe._
import scala.scalanative.unsigned._

import sdl2.Extras._
import sdl2.SDL._

import eu.joaocosta.minart.audio._

object SdlAudioPlayer extends AudioPlayer {
  private val sampleRate                        = 44100
  private val bufferSize                        = 4096.toUShort
  private var device: Option[SDL_AudioDeviceID] = None

  private val playQueue = new AudioPlayer.AudioQueue(sampleRate)

  // TODO: Ideally this should use a callback like this or it's own callback
  // Try this once scala native supports multi threading (or manually schedule futures to enqueue data)
  /*val callback: SDL_AudioCallback = (userdata: Ptr[Byte], stream: Ptr[UByte], len: CInt) => {
    var i = 0
    while (i < len) {
      stream(i) = playQueue.dequeueByte().toUByte
      i = i + 1
    }
  }*/

  def play(wave: AudioWave): Unit = {
    if (device == None) {
      SDL_InitSubSystem(SDL_INIT_AUDIO)
      val want = stackalloc[SDL_AudioSpec]()
      val have = stackalloc[SDL_AudioSpec]()
      want.freq = sampleRate
      want.format = AUDIO_S8
      want.channels = 1.toUByte
      want.samples = bufferSize
      want.callback = null // Ideally this should use a callback
      device = Some(SDL_OpenAudioDevice(null, 0, want, have, 0))
    }
    // SDL_LockAudioDevice(device.get)
    playQueue.enqueue(wave)
    val arr = Array.fill(playQueue.size)(playQueue.dequeueByte())
    SDL_QueueAudio(device.get, arr.asInstanceOf[ByteArray].at(0), arr.size.toUInt)
    // SDL_UnlockAudioDevice(device.get)
    SDL_PauseAudioDevice(device.get, 0)
  }

  def isPlaying(): Boolean =
    playQueue.nonEmpty &&
      device.forall(dev => SDL_GetQueuedAudioSize(dev).toInt == 0)

  def stop(): Unit = {
    playQueue.clear()
    device.foreach(dev => SDL_ClearQueuedAudio(dev))
  }
}
