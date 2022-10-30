package eu.joaocosta.minart.audio

/** Oscilator used to create periodic waves
  *
  * @param generator function used to generate a wave based on a frequency
  */
final case class Oscilator(generator: Double => AudioWave) extends (Double => AudioWave) {
  def apply(frequency: Double) = generate(frequency)

  /** Generates an AudioWave with a certain frequency and amplitude
    */
  def generate(frequency: Double, amplitude: Double = 1.0): AudioWave =
    if (frequency == 0.0) AudioWave.silence
    else if (amplitude == 1.0) generator(frequency)
    else generate(frequency).map(_ * amplitude)

  /** Maps the waves generated by this oscilator
    */
  def map(f: Double => Double): Oscilator =
    Oscilator(frequency => generator(frequency).map(f))
}

object Oscilator {
  val sin: Oscilator =
    Oscilator { frequency =>
      val k = frequency * 2 * math.Pi
      AudioWave(t => math.sin(k * t))
    }

  val square: Oscilator = sin.map(math.signum)

  private def floorMod(x: Double, y: Double): Double = {
    val rem = x % y
    if (rem >= 0) rem
    else rem + y
  }
  val sawtooth: Oscilator =
    Oscilator(frequency => AudioWave(t => 2 * floorMod(t * frequency, 1) - 1))
}