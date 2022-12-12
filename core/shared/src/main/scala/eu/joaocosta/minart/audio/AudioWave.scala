package eu.joaocosta.minart.audio

/** Infinite audio Wave represented by a continuous function.
  *
  * @param wave continuous function representing the wave
  */
final case class AudioWave(wave: Double => Double) extends (Double => Double) {

  def apply(t: Double): Double = wave(t)

  /** Maps the values of this wave. */
  def map(f: Double => Double): AudioWave = AudioWave(t => f(wave(t)))

  /** Flatmaps this wave with another wave. */
  def flatMap(f: Double => Double => Double): AudioWave = AudioWave(t => f(wave(t))(t))

  /** Contramaps the values of this wave. */
  def contramap(f: Double => Double): AudioWave = AudioWave(t => wave(f(t)))

  /** Combines this wave with another by combining their values using the given function.
    */
  def zipWith(that: AudioWave, f: (Double, Double) => Double): AudioWave = AudioWave(t => f(this.wave(t), that.wave(t)))

  /** Returns an AudioClip from this wave */
  def clip(duration: Double): AudioClip =
    AudioClip(this, duration)

  /** Samples this wave at the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
  def iterator(sampleRate: Double): Iterator[Double] = {
    val stepSize = 1.0 / sampleRate
    new Iterator[Double] {
      var position  = 0
      def hasNext() = true
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

  override def toString = s"AudioWave(<function1>)"
}

object AudioWave {

  /** Audio wave with just silence */
  val silence = AudioWave(_ => 0.0)

  /** Generates an audio wave for a sequence of samples.
    * Every value outside of the sequence is zero.
    *
    * @param data indexed sequence of samples
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave =
    AudioWave(t => data.applyOrElse((t * sampleRate).toInt, (_: Int) => 0.0))
}