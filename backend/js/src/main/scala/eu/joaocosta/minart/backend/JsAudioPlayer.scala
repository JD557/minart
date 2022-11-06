package eu.joaocosta.minart.backend

import scala.scalajs.js

import org.scalajs.dom._

import eu.joaocosta.minart.audio._

object JsAudioPlayer extends AudioPlayer {
  private lazy val audioCtx = new AudioContext();
  private val sampleRate    = 44100
  private val bufferSize    = 4096

  private val playQueue = new AudioPlayer.MultiChannelAudioQueue(sampleRate)

  private var callbackRegistered = false
  private val callback: (Double, Int) => () => Unit = (startTime: Double, consumed: Int) =>
    () => {
      if (playQueue.nonEmpty) {
        val batchSize   = math.min(bufferSize, playQueue.size)
        val duration    = (1000.0 * batchSize) / sampleRate
        val audioSource = audioCtx.createBufferSource()
        val buffer      = audioCtx.createBuffer(1, batchSize, sampleRate)
        val channelData = buffer.getChannelData(0)
        (0 until batchSize).foreach { i =>
          channelData(i) = playQueue.dequeue().toFloat
        }
        audioSource.buffer = buffer
        audioSource.connect(audioCtx.destination)
        if (consumed == 0) {
          audioSource.start()
          window.setTimeout(callback(audioCtx.currentTime, batchSize), duration - 25)
        } else {
          audioSource.start(startTime + consumed.toDouble / sampleRate)
          val nextTarget    = (startTime + (consumed + batchSize).toDouble / sampleRate)
          val sleepDuration = (nextTarget - audioCtx.currentTime) * 1000 - 25
          window.setTimeout(callback(startTime, consumed + batchSize), sleepDuration)
        }
      } else {
        callbackRegistered = false
      }
    }

  def play(clip: AudioClip): Unit = play(clip, 0)

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
