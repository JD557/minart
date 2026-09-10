package eu.joaocosta.minart.audio

class AudioWaveSpec extends munit.FunSuite {

  test("A sampled AudioWave returns all samples") {
    val wave = AudioWave.fromIndexedSeq(Vector(0.25, 0.5, 0.75, 1.0), 2.0)
    assertEquals(wave.getAmplitude(0), 0.25)
    assertEquals(wave.getAmplitude(0.49), 0.25)
    assertEquals(wave.getAmplitude(0.5), 0.5)
    assertEquals(wave.getAmplitude(0.99), 0.5)
    assertEquals(wave.getAmplitude(1.0), 0.75)
    assertEquals(wave.getAmplitude(1.49), 0.75)
    assertEquals(wave.getAmplitude(1.5), 1.0)
    assertEquals(wave.getAmplitude(1.99), 1.0)
    assertEquals(wave.getAmplitude(2.0), 0.0)
  }

}
