package eu.joaocosta.minart.backend

import org.scalajs.dom.*

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.runtime.*

final class JsAudioPlayer() extends LowLevelAudioPlayer {
  private lazy val audioCtx      = new AudioContext();
  private val preemptiveCallback = LoopFrequency.hz15.millis

  private var playQueue: AudioQueue.MultiChannelAudioQueue = _
  private var callbackRegistered                           = false

  protected def unsafeInit() = {}

  protected def unsafeApplySettings(settings: AudioPlayer.Settings): AudioPlayer.Settings = {
    // TODO this should probably stop the running audio
    playQueue = new AudioQueue.MultiChannelAudioQueue(settings.sampleRate)
    settings
  }

  protected def unsafeDestroy(): Unit = {
    stop()
  }

  private val callback: (Double) => () => Unit = (startTime: Double) =>
    () => {
      if (playQueue.nonEmpty()) {
        val batchSize   = Math.min(settings.bufferSize, playQueue.size)
        val duration    = batchSize.toDouble / settings.sampleRate
        val audioSource = audioCtx.createBufferSource()
        val buffer      = audioCtx.createBuffer(1, batchSize, settings.sampleRate)
        val channelData = buffer.getChannelData(0)
        (0 until batchSize).foreach { i =>
          channelData(i) = playQueue.dequeue().toFloat
        }
        audioSource.buffer = buffer
        audioSource.connect(audioCtx.destination)
        val clampedStart = Math.max(audioCtx.currentTime, startTime)
        audioSource.start(clampedStart)
        val nextTarget    = clampedStart + duration
        val sleepDuration = 1000 * (nextTarget - audioCtx.currentTime) - preemptiveCallback
        window.setTimeout(callback(nextTarget), sleepDuration)
      } else {
        callbackRegistered = false
      }
    }

  def play(clip: AudioClip, channel: Int): Unit = {
    playQueue.enqueue(clip, channel)
    if (!callbackRegistered) {
      callbackRegistered = true
      callback(0.0)()
    }
  }

  def isPlaying(): Boolean = playQueue.nonEmpty()

  def isPlaying(channel: Int): Boolean = playQueue.nonEmpty(channel)

  def stop(): Unit = playQueue.clear()

  def stop(channel: Int): Unit = playQueue.clear(channel)
}
