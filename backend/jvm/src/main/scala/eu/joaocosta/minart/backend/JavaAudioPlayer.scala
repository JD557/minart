package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import eu.joaocosta.minart.audio._

object JavaAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100
  private val bufferSize = 4096

  private val playQueue      = new AudioPlayer.AudioQueue(sampleRate)
  private val format         = new AudioFormat(sampleRate.toFloat, 8, 1, true, false)
  private val sourceDataLine = AudioSystem.getSourceDataLine(format)
  private var init           = false

  def play(wave: AudioWave): Unit = {
    playQueue.enqueue(wave)
    if (!init) {
      sourceDataLine.open(format, bufferSize)
      scala.concurrent.Future {
        while (true) {
          val available = sourceDataLine.available()
          if (available > 0) {
            val buf = Array.fill(available)(playQueue.dequeueByte())
            sourceDataLine.write(buf, 0, available)
          }
        }
      }(scala.concurrent.ExecutionContext.global)
      init = true
    }
    sourceDataLine.start()
  }
}
