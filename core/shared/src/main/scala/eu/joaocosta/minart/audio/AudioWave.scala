package eu.joaocosta.minart.audio

/** Infinite audio wave represented by a continuous function from time (in seconds) to amplitude (in [-1, 1]).
  *
  * @param wave continuous function representing the wave
  */
trait AudioWave extends (Double => Double) { outer =>

  /** Returns the amplitude at time t. */
  def apply(t: Double): Double = getAmplitude(t)

  /** Returns the amplitude at time t. */
  def getAmplitude(t: Double): Double

  /** Maps the values of this wave. */
  final def map(f: Double => Double): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = f(outer.getAmplitude(t))
  }

  /** Flatmaps this wave with another wave. */
  final def flatMap(f: Double => AudioWave): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = f(outer.getAmplitude(t)).getAmplitude(t)
  }

  /** Contramaps the values of this wave. */
  final def contramap(f: Double => Double): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = outer.getAmplitude(f(t))
  }

  /** Combines this wave with another by combining their values using the given function.
    */
  final def zipWith(that: AudioWave, f: (Double, Double) => Double): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = f(outer.getAmplitude(t), that.getAmplitude(t))
  }

  /** Coflatmaps this wave with a AudioWave => Double function.
    * Effectively, each sample of the new wave is computed from a translated wave, which can be used to
    * implement convolutions.
    */
  final def coflatMap(f: AudioWave => Double): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = f((dt: Double) => outer.getAmplitude(t + dt))
  }

  /** Returns a new AudioWave without the first `time` seconds
    */
  final def drop(time: Double): AudioWave =
    this.contramap(_ + time)

  /** Returns an AudioClip from this wave with just the first `time` seconds */
  final def take(time: Double): AudioClip =
    AudioClip(this, time)

  /** Returns an AudioClip from this wave from `start` to `end` */
  final def clip(start: Double, end: Double): AudioClip =
    if (end <= start) AudioClip.empty
    else AudioClip(this.drop(start), end - start)

  /** Samples this wave at the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
  final def iterator(sampleRate: Double): Iterator[Double] = {
    val stepSize = 1.0 / sampleRate
    new Iterator[Double] {
      var position  = 0
      def hasNext() = true
      def next() = {
        val res = getAmplitude(position * stepSize)
        position += 1
        res
      }
    }
  }

  /** Samples this wave at the specified sample rate and returns an iterator of Bytes
    * in the [-127, 127] range.
    */
  final def byteIterator(sampleRate: Double): Iterator[Byte] = {
    iterator(sampleRate)
      .map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)
  }

  override def toString = s"AudioWave(<function1>)"
}

object AudioWave {

  /** Audio wave with just silence */
  val silence = new AudioWave {
    def getAmplitude(t: Double) = 0.0
  }

  /** Creates an audio wave from a generator function.
    *
    * @param generator generator function from a time t to an amplitude
    */
  def fromFunction(generator: Double => Double): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = {
      generator(t)
    }
  }

  /** Creates an audio wave from an audio clip.
    * Every amplitude outside of the clip duration is set to 0.
    *
    * @param audioClip reference clip
    */
  def fromAudioClip(audioClip: AudioClip): AudioWave = new AudioWave {
    def getAmplitude(t: Double): Double = {
      audioClip.getAmplitude(t)
    }
  }

  /** Generates an audio wave for a sequence of samples.
    * Every value outside of the sequence is 0.
    *
    * @param data indexed sequence of samples (with amplitude between [-1.0, 1.0])
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave =
    new AudioWave {
      def getAmplitude(t: Double): Double =
        data.applyOrElse((t * sampleRate).toInt, (_: Int) => 0.0)
    }
}
