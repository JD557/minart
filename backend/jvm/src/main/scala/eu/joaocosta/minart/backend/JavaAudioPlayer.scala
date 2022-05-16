package eu.joaocosta.minart.backend

import java.io._
import javax.sound.sampled._

import eu.joaocosta.minart.audio._

object JavaAudioPlayer extends AudioPlayer {
  def play(sample: AudioSample): Unit = {
    val clip   = AudioSystem.getClip()
    val format = new AudioFormat(sample.sampleRate, 8, 1, true, false)
    val stream = new AudioInputStream(
      ByteArrayInputStream(sample.to8BitArray),
      format,
      sample.data.size
    )
    clip.open(stream)
    clip.setMicrosecondPosition(0)
    clip.loop(0)
    clip.start()
  }
}
