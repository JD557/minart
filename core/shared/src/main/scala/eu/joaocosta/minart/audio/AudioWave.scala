package eu.joaocosta.minart.audio

case class AudioWave(
    wave: Double => Double,
    duration: Double
) {

  def numSamples(sampleRate: Double): Int = {
    (duration * sampleRate).toInt
  }

  def iterator(sampleRate: Double): Iterator[Double] = {
    val samples  = numSamples(sampleRate)
    val stepSize = 1.0 / samples
    Iterator.from(0).take(samples).map { i =>
      wave(i * stepSize)
    }
  }

  def byteIterator(sampleRate: Double): Iterator[Byte] = {
    iterator(sampleRate)
      .map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)
  }
}
