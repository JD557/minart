package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import eu.joaocosta.minart.audio._

object JavaAudioPlayer extends AudioPlayer {
  private val sampleRate = 44100

  def play(wave: AudioWave): Unit = {
    val clip    = AudioSystem.getClip()
    val format  = new AudioFormat(sampleRate.toFloat, 8, 1, true, false)
    val samples = wave.byteIterator(sampleRate).toArray[Byte]
    val stream = new AudioInputStream(
      ByteArrayInputStream(samples),
      format,
      samples.size
    )
    clip.open(stream)
    clip.setMicrosecondPosition(0)
    clip.loop(0)
    clip.start()
  }
}
