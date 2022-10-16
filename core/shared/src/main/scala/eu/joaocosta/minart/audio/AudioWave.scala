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
    val stepSize = duration / samples
    new Iterator[Double] {
      var position  = 0
      def hasNext() = position < samples
      def next() = {
        val res = wave(position * stepSize)
        position += 1
        res
      }
    }
  }

  def byteIterator(sampleRate: Double): Iterator[Byte] = {
    iterator(sampleRate)
      .map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)
  }
}

object AudioWave {
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave = {
    val duration = data.size / sampleRate
    AudioWave(t => data.applyOrElse((t * sampleRate).toInt, _ => 0.0), duration)
  }
}
