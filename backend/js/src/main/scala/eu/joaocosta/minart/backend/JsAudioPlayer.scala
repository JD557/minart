package eu.joaocosta.minart.backend

import org.scalajs.dom._

import eu.joaocosta.minart.audio._

object JsAudioPlayer extends AudioPlayer {
  private val audioCtx = new AudioContext();
  def play(sample: AudioSample): Unit = {
    var buffer      = audioCtx.createBuffer(1, sample.data.length, sample.sampleRate.toInt)
    val channelData = buffer.getChannelData(0)
    sample.data.zipWithIndex.foreach { case (x, i) => channelData(i) = x }
    val source = audioCtx.createBufferSource()
    source.buffer = buffer
    source.connect(audioCtx.destination)
    source.start()
  }
}
