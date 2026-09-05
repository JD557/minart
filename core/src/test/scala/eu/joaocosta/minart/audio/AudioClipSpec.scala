package eu.joaocosta.minart.audio

class AudioClipSpec extends munit.FunSuite {

  test("An audio clip is correctly sampled") {
    val clip = AudioClip.fromFunction(x => x / 2.0, 2.0)
    assertEquals(Sampler.numSamples(clip, 1), 2)
    assertEquals(Sampler.sampleClip(clip, 1).toList, List(0.0, 0.5))

    assertEquals(Sampler.numSamples(clip, 2), 4)
    assertEquals(Sampler.sampleClip(clip, 2).toList, List(0.0, 0.25, 0.5, 0.75))
  }

  test("The take and drop operations correctly update the durations") {
    val clip = AudioClip.fromFunction(x => x / 2.0, 2.0)
    assertEquals(clip.take(0.5).duration, 0.5)
    assertEquals(clip.take(5).duration, 2.0)

    assertEquals(clip.drop(0.5).duration, 1.5)
    assertEquals(clip.drop(5).duration, 0.0)
  }

  test("The take and drop operations correctly split the clip") {
    val clip = AudioClip.fromFunction(x => x / 2.0, 2.0)

    val clipA = clip.take(0.5)
    val clipB = clip.drop(0.5)

    assertEquals(clipA.getAmplitude(0.0), Some(0.0))
    assertEquals(clipA.getAmplitude(0.1), Some(0.1 / 2.0))
    assertEquals(clipA.getAmplitude(0.5), None)

    assertEquals(clipB.getAmplitude(0.0), Some(0.5 / 2.0))
    assertEquals(clipB.getAmplitude(0.1), Some(0.6 / 2.0))
    assertEquals(clipB.getAmplitude(1.5), None)
  }

  test("The map operation maps all values") {
    val clip = AudioClip.fromFunction(x => x / 2.0, 2.0)
    val f    = (x: Double) => x / 2.0
    assertEquals(Sampler.sampleClip(clip.map(f), 100).toList, Sampler.sampleClip(clip, 100).toList.map(f))
  }

  test("The zipWith operation combines two clips") {
    val clipA = AudioClip.fromFunction(x => x / 4.0, 2.0)
    val clipB = AudioClip.fromFunction(x => x / 8.0, 3.0)

    val newClip = clipA.zipWith(clipB, _ + _)
    assertEquals(newClip.duration, 2.0)
    assertEquals(
      Sampler
        .sampleClip(newClip, 1)
        .toList,
      Sampler.sampleClip(clipA, 1).zip(Sampler.sampleClip(clipB, 1)).map { case (x, y) => x + y }.toList
    )
  }

  test("Appending two audio clips correcty updates the duration") {
    val clipA = AudioClip.fromFunction(x => x / 2.0, 2.0)
    val clipB = AudioClip.fromFunction(x => x / 4.0, 3.0)
    assertEquals((clipA.append(clipB)).duration, 5.0)
  }

  test("Appending two audio clips correcty merges at the boundaries") {
    val clipA = AudioClip.fromFunction(_ => 0.5, 2.0)
    val clipB = AudioClip.fromFunction(_ => 1.0, 3.0)

    val clip = clipA.append(clipB)

    assertEquals(clip.getAmplitude(0.0), Some(0.5))
    assertEquals(clip.getAmplitude(1.9), Some(0.5))
    assertEquals(clip.getAmplitude(2.0), Some(1.0))
    assertEquals(clip.getAmplitude(4.9), Some(1.0))
    assertEquals(clip.getAmplitude(5.0), None)
  }

  test("Repeating a clip correcty updates the duration") {
    val clipA = AudioClip.fromFunction(x => x / 2.0, 2.0)
    assertEquals(clipA.repeating(5).duration, 10.0)
  }

  test("The mix operation combines multiple clips") {
    val clipA = AudioClip.fromFunction(x => x / 4.0, 2.0)
    val clipB = AudioClip.fromFunction(x => x / 8.0, 3.0)

    val newClip = AudioClip.mix(List(clipA, clipB))
    assertEquals(newClip.duration, 2.0)
    assertEquals(
      Sampler
        .sampleClip(newClip, 1)
        .toList,
      Sampler.sampleClip(clipA, 1).zip(Sampler.sampleClip(clipB, 1)).map { case (x, y) => x + y }.toList
    )
  }

  test("A sampled AudioClip returns all samples") {
    val clip = AudioClip.fromIndexedSeq(Vector(0.25, 0.5, 0.75, 1.0), 2.0)
    assertEquals(clip.getAmplitude(0), Some(0.25))
    assertEquals(clip.getAmplitude(0.49), Some(0.25))
    assertEquals(clip.getAmplitude(0.5), Some(0.5))
    assertEquals(clip.getAmplitude(0.99), Some(0.5))
    assertEquals(clip.getAmplitude(1.0), Some(0.75))
    assertEquals(clip.getAmplitude(1.49), Some(0.75))
    assertEquals(clip.getAmplitude(1.5), Some(1.0))
    assertEquals(clip.getAmplitude(1.99), Some(1.0))
    assertEquals(clip.getAmplitude(2.0), None)
  }
}
