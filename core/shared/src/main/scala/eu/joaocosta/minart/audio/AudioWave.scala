package eu.joaocosta.minart.audio

/** Audio Wave represented by a continuous function and a duration.
  *
  * @param wave continuous function representing the wave
  * @param duration duration of this audio wave in seconds
  */
final case class AudioWave(
    wave: Double => Double,
    duration: Double
) extends (Double => Double) {

  def apply(t: Double): Double =
    if (t < 0 || t > duration) 0.0
    else (wave(t))

  /** Returns the number of samples required to store this wave at a certain
    * sample rate.
    */
  def numSamples(sampleRate: Double): Int = {
    (duration * sampleRate).toInt
  }

  /** Returns a new Audio Wave with the first `time` seconds of this audio wave
    */
  def take(time: Double) = {
    val newDuration = math.max(0.0, math.min(time, duration))
    AudioWave(wave, newDuration)
  }

  /** Returns a new Audio Wave without the first `time` seconds of this audio wave
    */
  def drop(time: Double) = {
    val delta = math.max(0.0, math.min(time, duration))
    AudioWave(t => wave(t + delta), duration - delta)
  }

  /** Maps the values of this wave.
    */
  def map(f: Double => Double): AudioWave =
    AudioWave(t => f(wave(t)), duration)

  /** Flatmaps this wave with another wave. The duration stays unchanged */
  def flatMap(f: Double => Double => Double): AudioWave =
    AudioWave(t => f(wave(t))(t), duration)

  /** Contramaps the values of this wave. The duration stays unchanged */
  def contramap(f: Double => Double): AudioWave =
    AudioWave(t => wave(f(t)), duration)

  /** Combines this wave with another by combining their values using the given function.
    */
  def zipWith(that: AudioWave, f: (Double, Double) => Double): AudioWave = {
    val newDuration = math.min(this.duration, that.duration)
    AudioWave(t => f(this.wave(t), that.wave(t)), newDuration)
  }

  /** Appends an AudioWave to this one */
  def append(that: AudioWave): AudioWave =
    AudioWave(
      t =>
        if (t <= this.duration) this.wave(t)
        else that.wave(t - this.duration),
      this.duration + that.duration
    )

  /** Returns a reversed version of this wave */
  def reverse: AudioWave =
    AudioWave(t => wave(duration - t), duration)

  /** Samples this wave at the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
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

  /** Samples this wave at the specified sample rate and returns an iterator of Bytes
    * in the [-127, 127] range.
    */
  def byteIterator(sampleRate: Double): Iterator[Byte] = {
    iterator(sampleRate)
      .map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)
  }
}

object AudioWave {

  /** Empty audio wave */
  val empty = silence(0.0)

  /** Audio wave with just silence for the specified duration */
  def silence(duration: Double) = AudioWave(_ => 0.0, duration)

  /** Generates an audio wave for a sequence of samples.
    *
    * @param data indexed sequence of samples
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave = {
    val duration = data.size / sampleRate
    AudioWave(t => data.applyOrElse((t * sampleRate).toInt, _ => 0.0), duration)
  }
}
