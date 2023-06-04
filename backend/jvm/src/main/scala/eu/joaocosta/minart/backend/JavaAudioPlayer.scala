package eu.joaocosta.minart.backend

import javax.sound.sampled._

import scala.concurrent._

import eu.joaocosta.minart.audio._

class JavaAudioPlayer() extends LowLevelAudioPlayer {
  private var playQueue: AudioQueue.MultiChannelAudioQueue = _
  private var sourceDataLine: SourceDataLine               = _

  private implicit val ec: ExecutionContext = ExecutionContext.global

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

  private def callback(): Future[Unit] = Future {
    if (playQueue.nonEmpty()) {
      val available = sourceDataLine.available()
      if (available > 0) {
        val buf = Iterator
          .fill(available / 2) {
            val next  = playQueue.dequeue()
            val short = (math.min(math.max(-1.0, next), 1.0) * Short.MaxValue).toInt
            List((short & 0xff).toByte, ((short >> 8) & 0xff).toByte)
          }
          .flatten
          .toArray
        sourceDataLine.write(buf, 0, available)
      }
      true
    } else false
  }.flatMap {
    case true  => callback()
    case false => Future.successful(())
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
}
