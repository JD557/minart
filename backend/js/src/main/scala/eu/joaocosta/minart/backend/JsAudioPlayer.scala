package eu.joaocosta.minart.backend

import org.scalajs.dom._

import eu.joaocosta.minart.audio._

object JsAudioPlayer extends AudioPlayer {
  private val audioCtx   = new AudioContext();
  private val sampleRate = 44100

  def play(wave: AudioWave): Unit = {
    val samples     = wave.numSamples(sampleRate)
    var buffer      = audioCtx.createBuffer(1, samples, sampleRate)
    val channelData = buffer.getChannelData(0)
    wave.iterator(sampleRate).zipWithIndex.foreach { case (x, i) => channelData(i) = x.toFloat }
    val source = audioCtx.createBufferSource()
    source.buffer = buffer
    source.connect(audioCtx.destination)
    source.start()
  }
}
