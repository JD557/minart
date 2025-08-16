package eu.joaocosta.minart.backend

import scala.concurrent.*
import scala.concurrent.duration.*
import scala.scalanative.meta.LinktimeInfo.isMultithreadingEnabled
import scala.scalanative.runtime.ByteArray
import scala.scalanative.unsafe.{blocking as _, *}
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

  given ExecutionContext                                  = ExecutionContext.global
  private var threadRunning: Boolean                      = false
  private var currentBackgroundThread: Future[Unit]       = Future.successful(())
  private def eventLoop(nextSchedule: Long): Future[Unit] = Future {
    if ((settings.threadFrequency != LoopFrequency.Never || playQueue.nonEmpty()) && SDL_WasInit(SDL_INIT_AUDIO) != 0) {
      val filled = SDL_GetQueuedAudioSize(device).toInt
      if (System.currentTimeMillis() > nextSchedule && playQueue.nonEmpty() && filled < (settings.bufferSize * 2)) {
        val samples = Math.min(settings.bufferSize, playQueue.size)
        val buf     = Iterator
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
        } else None // abort
      } else Some(nextSchedule)
    } else None
  }.flatMap {
    case Some(next) =>
      eventLoop(next)
    case None =>
      threadRunning = false
      Future.successful(())
  }

  private def backgroundThread(): Future[Unit] = Future {
    println("Launching thread")
    var abort = false
    while (
      !abort &&
      (settings.threadFrequency != LoopFrequency.Never || playQueue.nonEmpty()) &&
      SDL_WasInit(SDL_INIT_AUDIO) != 0
    ) {
      val filled = SDL_GetQueuedAudioSize(device).toInt
      if (playQueue.nonEmpty() && filled < (settings.bufferSize * 2)) {
        val samples = Math.min(settings.bufferSize, playQueue.size)
        val buf     = Iterator
          .fill(samples) {
            val next  = playQueue.dequeue()
            val short = (Math.min(Math.max(-1.0, next), 1.0) * Short.MaxValue).toInt
            List((short & 0xff).toByte, ((short >> 8) & 0xff).toByte)
          }
          .flatten
          .toArray
        if (SDL_QueueAudio(device, buf.asInstanceOf[ByteArray].at(0), (samples * 2).toUInt) == 0) {
          val bufferedMillis = (1000 * samples) / settings.sampleRate
          blocking {
            Thread.sleep(Math.max(0, bufferedMillis - preemptiveCallback))
          }
        } else { abort = true }
      } else {
        settings.threadFrequency match {
          case ld: LoopFrequency.LoopDuration => blocking(Thread.sleep(ld.millis))
          case _                              => ()
        }
      }
    }
    println("Stopping thread")
    threadRunning = false
    ()
  }

  protected def unsafeDestroy(): Unit = {
    stop()
    if (isMultithreadingEnabled) { // Let the callback stop
      val maxWaitMillis = (settings.bufferSize * 2 * 1000) / settings.sampleRate
      Thread.sleep(maxWaitMillis)
    }
    SDL_CloseAudioDevice(device)
    SDL_QuitSubSystem(SDL_INIT_AUDIO)
  }

  def play(clip: AudioClip, channel: Int): Unit = {
    // SDL_LockAudioDevice(device)
    val shouldLaunchThread = settings.threadFrequency match {
      case LoopFrequency.Never => !isPlaying()
      case _                   => !threadRunning
    }
    if (shouldLaunchThread) {
      if (isMultithreadingEnabled) {
        // Make sure that the backgroundThread is stopped before creating a new one
        try { Await.result(currentBackgroundThread, (settings.bufferSize / settings.sampleRate).seconds) }
        catch { _ => () }
      }
    }
    playQueue.enqueue(clip, channel)
    if (shouldLaunchThread) {
      threadRunning = true
      if (isMultithreadingEnabled) currentBackgroundThread = backgroundThread()
      else currentBackgroundThread = eventLoop(0)
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
