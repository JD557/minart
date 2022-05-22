package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import eu.joaocosta.minart.audio._

object JavaAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100

  def play(wave: AudioWave): Unit = {
    val clip   = AudioSystem.getClip()
    val format = new AudioFormat(sampleRate.toFloat, 8, 1, true, false)
    val is = new InputStream {
      val it          = wave.byteIterator(sampleRate)
      def read(): Int = if (it.isEmpty) -1 else (it.next() & 0xFF).toInt
    }
    val stream = new AudioInputStream(
      is,
      format,
      wave.numSamples(sampleRate)
    )
    clip.open(stream)
    clip.setMicrosecondPosition(0)
    clip.loop(0)
    clip.start()
  }
}
