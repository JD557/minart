package eu.joaocosta.minart.audio

case class AudioSample(
    data: Array[Float],
    sampleRate: Float = 44100
) {
  def to8BitArray = data.map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)

  def ++(that: AudioSample): AudioSample = {
    require(this.sampleRate == that.sampleRate)
    AudioSample(this.data ++ that.data, sampleRate)
  }

}

object AudioSample {
  def fromFunction(f: Double => Double, duration: Double, sampleRate: Float = 44100) = {
    val numSamples = (duration * sampleRate).toInt
    AudioSample(Array.tabulate(numSamples)(s => f(s / sampleRate).toFloat))
  }
}
