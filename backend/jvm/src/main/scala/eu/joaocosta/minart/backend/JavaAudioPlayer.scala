package eu.joaocosta.minart.backend

import javax.sound.sampled.*

import scala.concurrent.*

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.runtime.*

final class JavaAudioPlayer() extends LowLevelAudioPlayer {
  private val preemptiveCallback             = LoopFrequency.hz15.millis
  private var sourceDataLine: SourceDataLine = _

  private var playQueue: AudioQueue.MultiChannelAudioQueue = _

  protected def unsafeInit(): Unit = {}

  protected def unsafeApplySettings(settings: AudioPlayer.Settings): AudioPlayer.Settings = {
    // TODO this should probably stop the running audio
    val format = new AudioFormat(settings.sampleRate.toFloat, 16, 1, true, false)
    playQueue = new AudioQueue.MultiChannelAudioQueue(settings.sampleRate)
    sourceDataLine = AudioSystem.getSourceDataLine(format)
    sourceDataLine.open(format, settings.bufferSize)
    sourceDataLine.start()
    settings
  }

  protected def unsafeDestroy(): Unit = {
    stop()
    sourceDataLine.close()
  }

  given ExecutionContext               = ExecutionContext.global
  private def callback(): Future[Unit] = Future {
    while (playQueue.nonEmpty()) {
      val available = sourceDataLine.available()
      if (available > 0) {
        val samples = Math.min(playQueue.size, available / 2)
        val buf     = Iterator
          .fill(samples) {
            val next  = playQueue.dequeue()
            val short = (Math.min(Math.max(-1.0, next), 1.0) * Short.MaxValue).toInt
            List((short & 0xff).toByte, ((short >> 8) & 0xff).toByte)
          }
          .flatten
          .toArray
        sourceDataLine.write(buf, 0, samples * 2)
        val bufferedMillis = (1000 * samples) / settings.sampleRate
        blocking {
          Thread.sleep(Math.max(0, bufferedMillis - preemptiveCallback))
        }
      }
    }
    ()
  }

  def play(clip: AudioClip, channel: Int): Unit = {
    val alreadyPlaying = isPlaying()
    playQueue.enqueue(clip, channel)
    if (!alreadyPlaying) callback()
  }

  def isPlaying(): Boolean = playQueue.nonEmpty()

  def isPlaying(channel: Int): Boolean = playQueue.nonEmpty(channel)

  def stop(): Unit = playQueue.clear()

  def stop(channel: Int): Unit = playQueue.clear(channel)

  def getChannelMix(channel: Int): AudioMix =
    playQueue.getChannelMix(channel)

  def setChannelMix(mix: AudioMix, channel: Int): Unit =
    playQueue.setChannelMix(mix, channel)
}
