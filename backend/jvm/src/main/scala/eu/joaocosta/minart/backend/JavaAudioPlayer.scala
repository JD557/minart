package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import scala.concurrent._

import eu.joaocosta.minart.audio._

object JavaAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100
  private val bufferSize = 4096

  private val playQueue      = new AudioPlayer.MultiChannelAudioQueue(sampleRate)
  private val format         = new AudioFormat(sampleRate.toFloat, 8, 1, true, false)
  private val sourceDataLine = AudioSystem.getSourceDataLine(format)
  private var init           = false

  private implicit val ec: ExecutionContext = ExecutionContext.global

  private def callback(): Future[Unit] = Future {
    if (playQueue.nonEmpty) {
      val available = sourceDataLine.available()
      if (available > 0) {
        val buf = Array.fill(available)(playQueue.dequeueByte())
        sourceDataLine.write(buf, 0, available)
      }
      true
    } else false
  }.flatMap {
    case true  => callback()
    case false => Future.successful(())
  }

  def play(clip: AudioClip): Unit = play(clip, 0)

  def play(clip: AudioClip, channel: Int): Unit = {
    val alreadyPlaying = isPlaying()
    playQueue.enqueue(clip, channel)
    if (!init) {
      sourceDataLine.open(format, bufferSize)
      sourceDataLine.start()
      init = true
    }
    if (!alreadyPlaying) {
      callback()
    }
  }

  def isPlaying(): Boolean = playQueue.nonEmpty

  def stop(): Unit = playQueue.clear()

  def stop(channel: Int): Unit = playQueue.clear(channel)
}
