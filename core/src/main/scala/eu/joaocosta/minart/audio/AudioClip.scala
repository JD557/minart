package eu.joaocosta.minart.audio

/** Audio Clip represented by a wave and a duration.
  *
  * @param wave audio wave storing this clip
  * @param duration duration of this audio wave in seconds
  */
final case class AudioClip(
    wave: AudioWave,
    duration: Double
) { outer =>

  /** Gets the amplitude at a certain point in time, defined between [0, duration).
    *  @param t time in seconds
    *  @return amplitude
    */
  def getAmplitude(t: Double): Option[Double] =
    if (t < 0 || t >= duration) None
    else Some(wave(t))

  /** Gets the amplitude at a certain point in time, falling back to a default value when out of bounds.
    * Similar to `getAmplitude(t).getOrElse(fallback)`, but avoids an allocation.
    *  @param t time in seconds
    *  @return amplitude
    */
  def getAmplitudeOrElse(t: Double, fallback: Double = 0.0): Double =
    if (t < 0 || t >= duration) fallback
    else wave(t)

  /** Returns a new Audio Clip with the first `time` seconds of this audio clip
    */
  def take(time: Double): AudioClip = {
    val newDuration = Math.max(0.0, Math.min(time, duration))
    AudioClip(wave, newDuration)
  }

  /** Returns a new Audio Clip without the first `time` seconds of this audio clip
    */
  def drop(time: Double): AudioClip = {
    val delta = Math.max(0.0, Math.min(time, duration))
    AudioClip(wave.drop(delta), duration - delta)
  }

  /** Maps the values of this wave.
    */
  def map(f: Double => Double): AudioClip =
    AudioClip(wave.map(f), duration)

  /** Flatmaps the wave of this clip. The duration stays unchanged */
  def flatMap(f: Double => AudioWave): AudioClip =
    AudioClip(wave.flatMap(f), duration)

  /** Contramaps the values of the wave of this clip */
  def contramap(f: Double => Double): AudioWave =
    wave.contramap(f)

  /** Combines this clip with another by combining their values using the given function.
    */
  def zipWith(that: AudioClip, f: (Double, Double) => Double): AudioClip = {
    val newDuration = Math.min(this.duration, that.duration)
    AudioClip(this.wave.zipWith(that.wave, f), newDuration)
  }

  /** Combines this clip with a wave by combining their values using the given function.
    */
  def zipWith(that: AudioWave, f: (Double, Double) => Double): AudioClip =
    AudioClip(this.wave.zipWith(that, f), duration)

  /** Coflatmaps this clip with a AudioClip => Double function.
    * Effectively, each sample of the new clip is computed from a translated clip, which can be used to
    * implement convolutions.
    */
  def coflatMap(f: AudioClip => Double): AudioClip =
    AudioClip(
      new AudioWave {
        def getAmplitude(t: Double): Double = f(outer.drop(t))
      },
      duration
    )

  /** Appends an AudioClip to this one */
  def append(that: AudioClip): AudioClip =
    AudioClip(
      AudioWave.fromFunction(t =>
        if (t < this.duration) this.wave(t)
        else that.wave(t - this.duration)
      ),
      this.duration + that.duration
    )

  /** Returns a reversed version of this wave */
  def reverse: AudioClip =
    contramap(t => duration - t).take(duration)

  /** Speeds up/down this clip according to a multiplier */
  def changeSpeed(multiplier: Double): AudioClip =
    contramap(t => multiplier * t).take(duration / multiplier)

  /** Returns an audio wave that repeats this clip forever */
  def repeating: AudioWave =
    if (duration <= 0) AudioWave.silence
    else
      new AudioWave {
        def getAmplitude(t: Double): Double = wave.getAmplitude(AudioClip.floorMod(t, duration))
      }

  /** Returns an audio wave that repeats this clip a certain number of times */
  def repeating(times: Int): AudioClip =
    repeating.take(duration * times)

  /** Returns an audio wave that clamps this clip when out of bounds */
  def clamped: AudioWave =
    if (duration <= 0) AudioWave.silence
    else contramap(t => AudioClip.clamp(0.0, t, duration))

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

  /** Creates an audio clip from a generator function.
    *
    * @param generator generator function from a time t to an amplitude
    * @param duration clip duration
    */
  def fromFunction(generator: Double => Double, duration: Double): AudioClip =
    AudioClip(AudioWave.fromFunction(generator), duration)

  /** Generates an audio clip by mixing a sequence of clips.
    *
    *  The duration is defined by the smallest clip.
    *
    * @param clips clips to mix
    */
  def mix(clips: Seq[AudioClip]): AudioClip =
    if (clips.isEmpty) AudioClip.empty
    else AudioWave.mix(clips.map(_.wave)).take(clips.map(_.duration).min)

  private def floorMod(x: Double, y: Double): Double = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }

  private def clamp(minValue: Double, value: Double, maxValue: Double): Double =
    Math.max(minValue, Math.min(value, maxValue))
}
