package eu.joaocosta.minart.backend

import scala.scalajs.js

import org.scalajs.dom._

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.runtime._

class JsAudioPlayer() extends LowLevelAudioPlayer {
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

  private val callback: (Double, Int) => () => Unit = (startTime: Double, consumed: Int) =>
    () => {
      if (playQueue.nonEmpty) {
        val batchSize   = math.min(settings.bufferSize, playQueue.size)
        val duration    = (1000.0 * batchSize) / settings.sampleRate
        val audioSource = audioCtx.createBufferSource()
        val buffer      = audioCtx.createBuffer(1, batchSize, settings.sampleRate)
        val channelData = buffer.getChannelData(0)
        (0 until batchSize).foreach { i =>
          channelData(i) = playQueue.dequeue().toFloat
        }
        audioSource.buffer = buffer
        audioSource.connect(audioCtx.destination)
        if (consumed == 0) {
          audioSource.start()
          window.setTimeout(callback(audioCtx.currentTime, batchSize), duration - preemptiveCallback)
        } else {
          audioSource.start(startTime + consumed.toDouble / settings.sampleRate)
          val nextTarget    = (startTime + (consumed + batchSize).toDouble / settings.sampleRate)
          val sleepDuration = (nextTarget - audioCtx.currentTime) * 1000 - preemptiveCallback
          window.setTimeout(callback(startTime, consumed + batchSize), sleepDuration)
        }
      } else {
        callbackRegistered = false
      }
    }

  def play(clip: AudioClip, channel: Int): Unit = {
    playQueue.enqueue(clip, channel)
    if (!callbackRegistered) {
      callbackRegistered = true
      callback(0.0, 0)()
    }
  }

  def isPlaying(): Boolean = playQueue.nonEmpty

  def stop(): Unit = playQueue.clear()

  def stop(channel: Int): Unit = playQueue.clear(channel)
}
