package eu.joaocosta.minart.audio

/** Helper methods to sample an Audio Wave or Clip.
  */
object Sampler {

  /** Returns the number of samples required to store a clip with a certain
    * sample rate.
    */
  def numSamples(clip: AudioClip, sampleRate: Double): Int = (clip.duration * sampleRate).toInt

  /** Samples this wave with the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
  def sampleWave(wave: AudioWave, sampleRate: Double): Iterator[Double] = {
    val stepSize = 1.0 / sampleRate
    new Iterator[Double] {
      var position = 0
      def hasNext  = true
      def next() = {
        val res = wave.getAmplitude(position * stepSize)
        position += 1
        res
      }
    }
  }

  /** Samples this clip with the specified sample rate and returns an iterator of Doubles
    * in the [-1, 1] range.
    */
  def sampleClip(clip: AudioClip, sampleRate: Double): Iterator[Double] =
    sampleWave(clip.wave, sampleRate).take(numSamples(clip, sampleRate))

  /** Resamples this audio clip with a specified frame rate.
    * This can be useful, for example, to avoid recomputing transformations
    * every time the clip is played.
    */
  def resample(clip: AudioClip, sampleRate: Double): AudioClip =
    AudioClip.fromIndexedSeq(sampleClip(clip, sampleRate).toVector, sampleRate)
}
