package eu.joaocosta.minart.audio

/** Infinite audio wave represented by a continuous function from time (in seconds) to amplitude (in [-1, 1]).
  *
  * @param wave continuous function representing the wave
  */
trait AudioWave extends (Double => Double) { outer =>

  /** Returns the amplitude at time t. */
  final def apply(t: Double): Double = getAmplitude(t)

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
  def drop(time: Double): AudioWave = new AudioWave.DropAudioWave(this, time)

  /** Returns an AudioClip from this wave with just the first `time` seconds */
  final def take(time: Double): AudioClip =
    AudioClip(this, time)

  /** Returns an AudioClip from this wave from `start` to `end` */
  final def clip(start: Double, end: Double): AudioClip =
    if (end <= start) AudioClip.empty
    else AudioClip(this.drop(start), end - start)

  override def toString = s"AudioWave(<function1>)"
}

object AudioWave {
  private[AudioWave] final class DropAudioWave(inner: AudioWave, shift: Double) extends AudioWave {
    def getAmplitude(t: Double): Double = inner.getAmplitude(t + shift)
    override def drop(time: Double)     = new DropAudioWave(inner, shift + time)
    override def toString               = s"DropAudioWave($inner, $shift)"
  }

  private[AudioWave] final class SampledAudioWave(data: IndexedSeq[Double], sampleRate: Double) extends AudioWave {
    def getAmplitude(t: Double): Double =
      data.applyOrElse((t * sampleRate).toInt, (_: Int) => 0.0)
    override def toString = s"SampledAudioWave(<${data.size} samples>, $sampleRate)"
  }

  private[AudioWave] object EmptyAudioWave extends AudioWave {
    def getAmplitude(t: Double)     = 0.0
    override def drop(time: Double) = this
    override def toString           = "<silence>"
  }

  /** Audio wave with just silence */
  val silence = EmptyAudioWave

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
      audioClip.getAmplitudeOrElse(t)
    }
  }

  /** Generates an audio wave for a sequence of samples.
    * Every value outside of the sequence is 0.
    *
    * @param data indexed sequence of samples (with amplitude between [-1.0, 1.0])
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioWave = new SampledAudioWave(data, sampleRate)

  /** Generates an audio wave by mixing a sequence of waves.
    *
    * @param waves waves to mix
    */
  def mix(waves: Seq[AudioWave]): AudioWave = new AudioWave {
    private val waveArray               = waves.toArray
    def getAmplitude(t: Double): Double = {
      var res: Double = 0.0
      var i: Int      = 0
      while (i < waveArray.size) {
        res += waveArray(i).getAmplitude(t)
        i += 1
      }
      Math.max(-1.0, Math.min(res, 1.0))
    }
  }
}
