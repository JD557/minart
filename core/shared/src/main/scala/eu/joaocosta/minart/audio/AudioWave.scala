package eu.joaocosta.minart.audio

/** Infinite audio wave represented by a continuous function from time (in seconds) to amplitude (in [-1, 1]).
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

  /** Coflatmaps this wave with a AudioWave => Double function.
    * Effectively, each sample of the new wave is computed from a translated wave, which can be used to
    * implement convolutions.
    */
  def coflatMap(f: AudioWave => Double): AudioWave =
    AudioWave(t => f(AudioWave((dt: Double) => wave(t + dt))))

  /** Returns a new AudioWave without the first `time` seconds
    */
  def drop(time: Double): AudioWave =
    this.contramap(_ + time)

  /** Returns an AudioClip from this wave with just the first `time` seconds */
  def take(time: Double): AudioClip =
    AudioClip(this, time)

  /** Returns an AudioClip from this wave from `start` to `end` */
  def clip(start: Double, end: Double): AudioClip =
    if (end <= start) AudioClip.empty
    else AudioClip(this.drop(start), end - start)

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
    * @param data indexed sequence of samples (with amplitude between [-1.0, 1.0])
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave =
    AudioWave(t => data.applyOrElse((t * sampleRate).toInt, (_: Int) => 0.0))
}
