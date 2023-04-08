package eu.joaocosta.minart.audio

/** Audio Clip represented by a wave and a duration.
  *
  * @param wave audio wave storing this clip
  * @param duration duration of this audio wave in seconds
  */
final case class AudioClip(
    wave: AudioWave,
    duration: Double
) {

  def getAmplitude(t: Double): Double =
    if (t < 0 || t > duration) 0.0
    else (wave(t))

  /** Returns the number of samples required to store this wave at a certain
    * sample rate.
    */
  def numSamples(sampleRate: Double): Int = (duration * sampleRate).toInt

  /** Returns a new Audio Clip with the first `time` seconds of this audio clip
    */
  def take(time: Double): AudioClip = {
    val newDuration = math.max(0.0, math.min(time, duration))
    AudioClip(wave, newDuration)
  }

  /** Returns a new Audio Clip without the first `time` seconds of this audio clip
    */
  def drop(time: Double): AudioClip = {
    val delta = math.max(0.0, math.min(time, duration))
    AudioClip(wave.contramap(_ + delta), duration - delta)
  }

  /** Maps the values of this wave.
    */
  def map(f: Double => Double): AudioClip =
    AudioClip(wave.map(f), duration)

  /** Flatmaps the wave of this clip. The duration stays unchanged */
  def flatMap(f: Double => AudioWave): AudioClip =
    AudioClip(wave.flatMap(f), duration)

  /** Contramaps the values of the wave of this clip. The duration stays unchanged */
  def contramap(f: Double => Double): AudioClip =
    AudioClip(wave.contramap(f), duration)

  /** Combines this clip with another by combining their values using the given function.
    */
  def zipWith(that: AudioClip, f: (Double, Double) => Double): AudioClip = {
    val newDuration = math.min(this.duration, that.duration)
    AudioClip(this.wave.zipWith(that.wave, f), newDuration)
  }

  /** Combines this clip with a wave by combining their values using the given function.
    */
  def zipWith(that: AudioWave, f: (Double, Double) => Double): AudioClip =
    AudioClip(this.wave.zipWith(that, f), duration)

  /** Appends an AudioClip to this one */
  def append(that: AudioClip): AudioClip =
    AudioClip(
      AudioWave.fromFunction(t =>
        if (t <= this.duration) this.wave(t)
        else that.wave(t - this.duration)
      ),
      this.duration + that.duration
    )

  /** Returns a reversed version of this wave */
  def reverse: AudioClip =
    contramap(t => duration - t)

  /** Speeds up/down this clip according to a multiplier */
  def changeSpeed(multiplier: Double): AudioClip =
    wave.contramap(t => multiplier * t).take(duration / multiplier)

  /** Returns an audio wave that repeats this clip forever */
  def repeating: AudioWave =
    wave.contramap(t => AudioClip.floorMod(t, duration))

  /** Returns an audio wave that repeats this clip a certain number of times */
  def repeating(times: Int): AudioClip =
    repeating.take(duration * times)

  /** Samples this wave at the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
  def iterator(sampleRate: Double): Iterator[Double] =
    wave.iterator(sampleRate).take(numSamples(sampleRate))

  /** Samples this wave at the specified sample rate and returns an iterator of Bytes
    * in the [-127, 127] range.
    */
  def byteIterator(sampleRate: Double): Iterator[Byte] =
    iterator(sampleRate)
      .map(x => (math.min(math.max(-1.0, x), 1.0) * 127).toByte)

  override def toString = s"AudioClip($wave,$duration)"
}

object AudioClip {

  /** Empty audio clip */
  val empty = silence(0.0)

  /** Audio clip with just silence for the specified duration */
  def silence(duration: Double) = AudioWave.silence.take(duration)

  /** Generates an audio clip for a sequence of samples.
    *
    * @param data indexed sequence of samples
    * @param sampleRate sample rate used in the sequence
    */
  def fromIndexedSeq(data: IndexedSeq[Double], sampleRate: Double): AudioClip = {
    val duration = data.size / sampleRate
    AudioWave.fromIndexedSeq(data, sampleRate).take(duration)
  }

  private def floorMod(x: Double, y: Double): Double = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }
}
