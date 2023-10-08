package eu.joaocosta.minart.backend

import scala.concurrent.*
import scala.scalanative.runtime.ByteArray
import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.*

import sdl2.all.*
import sdl2.enumerations.SDL_AudioFormat.*
import sdl2.enumerations.SDL_InitFlag.*

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.runtime.*

final class SdlAudioPlayer() extends LowLevelAudioPlayer {
  private val preemptiveCallback        = LoopFrequency.hz15.millis
  private var device: SDL_AudioDeviceID = _

  private var playQueue: AudioQueue.MultiChannelAudioQueue = _
  private var callbackRegistered                           = false

  protected def unsafeInit(): Unit = {
    SDL_InitSubSystem(SDL_INIT_AUDIO)
  }

  protected def unsafeApplySettings(settings: AudioPlayer.Settings): AudioPlayer.Settings = {
    // TODO this should probably stop the running audio
    playQueue = new AudioQueue.MultiChannelAudioQueue(settings.sampleRate)
    val want = stackalloc[SDL_AudioSpec]()
    val have = stackalloc[SDL_AudioSpec]()
    (!want).freq = settings.sampleRate
    (!want).format = AUDIO_S16LSB
    (!want).channels = 1.toUByte
    (!want).samples = settings.bufferSize.toUShort
    (!want).callback = SDL_AudioCallback(null) // Ideally this should use a SDL callback
    device = SDL_OpenAudioDevice(null, 0, want, have, 0)
    settings
  }

  protected def unsafeDestroy(): Unit = {
    stop()
    SDL_CloseAudioDevice(device)
    SDL_QuitSubSystem(SDL_INIT_AUDIO)
  }

  // TODO: Ideally this should use a callback like this or it's own callback
  // Try this once scala native supports multi threading (or manually schedule futures to enqueue data)
  /*val callback: SDL_AudioCallback = (userdata: Ptr[Byte], stream: Ptr[UByte], len: CInt) => {
    var i = 0
    while (i < len) {
      stream(i) = playQueue.dequeueByte().toUByte
      i = i + 1
    }
  }*/
  given ExecutionContext = ExecutionContext.global
  private def callback(nextSchedule: Long): Future[Unit] = Future {
    if (playQueue.nonEmpty()) {
      if (
        System.currentTimeMillis() > nextSchedule && SDL_GetQueuedAudioSize(device).toInt < (settings.bufferSize * 2)
      ) {
        val samples = Math.min(settings.bufferSize, playQueue.size)
        val buf = Iterator
          .fill(samples) {
            val next  = playQueue.dequeue()
            val short = (Math.min(Math.max(-1.0, next), 1.0) * Short.MaxValue).toInt
            List((short & 0xff).toByte, ((short >> 8) & 0xff).toByte)
          }
          .flatten
          .toArray
        if (SDL_QueueAudio(device, buf.asInstanceOf[ByteArray].at(0), (samples * 2).toUInt) == 0) {
          val bufferedMillis = (1000 * samples) / settings.sampleRate
          Some(System.currentTimeMillis() + bufferedMillis - preemptiveCallback)
        } else None
      } else Some(nextSchedule)
    } else None
  }.flatMap {
    case Some(next) =>
      callback(next)
    case None =>
      callbackRegistered = false
      Future.successful(())
  }

  def play(clip: AudioClip, channel: Int): Unit = {
    // SDL_LockAudioDevice(device)
    playQueue.enqueue(clip, channel)
    if (!callbackRegistered) {
      callbackRegistered = true
      callback(0)
    }
    // SDL_UnlockAudioDevice(device)
    SDL_PauseAudioDevice(device, 0)
  }

  def isPlaying(): Boolean =
    playQueue.nonEmpty() || SDL_GetQueuedAudioSize(device).toInt > 0

  def isPlaying(channel: Int): Boolean =
    playQueue.nonEmpty(channel)

  def stop(): Unit = {
    playQueue.clear()
    SDL_ClearQueuedAudio(device)
  }

  def stop(channel: Int): Unit = {
    playQueue.clear(channel)
  }

  def getChannelMix(channel: Int): AudioMix =
    playQueue.getChannelMix(channel)

  def setChannelMix(mix: AudioMix, channel: Int): Unit =
    playQueue.setChannelMix(mix, channel)
}
