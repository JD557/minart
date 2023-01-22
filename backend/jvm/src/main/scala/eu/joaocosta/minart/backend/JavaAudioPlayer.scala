package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import scala.concurrent._

import eu.joaocosta.minart.audio._

class JavaAudioPlayer() extends LowLevelAudioPlayer {
  private var playQueue: AudioPlayer.MultiChannelAudioQueue = _
  private var sourceDataLine: SourceDataLine                = _

  private implicit val ec: ExecutionContext = ExecutionContext.global

  protected def unsafeInit(settings: AudioPlayer.Settings): AudioPlayer.Settings = {
    val format = new AudioFormat(settings.sampleRate.toFloat, 8, 1, true, false)
    playQueue = new AudioPlayer.MultiChannelAudioQueue(settings.sampleRate)
    sourceDataLine = AudioSystem.getSourceDataLine(format)
    sourceDataLine.open(format, settings.bufferSize)
    sourceDataLine.start()
    settings
  }

  protected def unsafeDestroy(): Unit = {
    stop()
    sourceDataLine.close()
  }

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
    if (!alreadyPlaying) callback()
  }

  def isPlaying(): Boolean = playQueue.nonEmpty

  def stop(): Unit = playQueue.clear()

  def stop(channel: Int): Unit = playQueue.clear(channel)
}
