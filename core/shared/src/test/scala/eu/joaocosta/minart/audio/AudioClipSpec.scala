package eu.joaocosta.minart.audio

import verify._

object AudioClipSpec extends BasicTestSuite {

  test("An audio clip is correctly sampled") {
    val clip = AudioClip(x => x / 2.0, 2.0)
    assert(clip.numSamples(1) == 2)
    assert(clip.iterator(1).toList == List(0.0, 0.5))

    assert(clip.numSamples(2) == 4)
    assert(clip.iterator(2).toList == List(0.0, 0.25, 0.5, 0.75))
  }

  test("take and drop correctly update the durations") {
    val clip = AudioClip(x => x / 2.0, 2.0)
    assert(clip.take(0.5).duration == 0.5)
    assert(clip.take(5).duration == 2.0)

    assert(clip.drop(0.5).duration == 1.5)
    assert(clip.drop(5).duration == 0.0)
  }

  test("The map operation maps all values") {
    val clip = AudioClip(x => x / 2.0, 2.0)
    val f    = (x: Double) => x / 2.0
    assert(clip.map(f).iterator(100).toList == clip.iterator(100).toList.map(f))
  }

  test("The zipWith operation combines two clips") {
    val clipA = AudioClip(x => x / 4.0, 2.0)
    val clipB = AudioClip(x => x / 8.0, 3.0)

    val newClip = clipA.zipWith(clipB, _ + _)
    assert(newClip.duration == 2.0)
    assert(
      newClip.iterator(1).toList == clipA.iterator(1).zip(clipB.iterator(1)).map { case (x, y) => x + y }.toList
    )
  }

  test("Appending two audio clips correcty updates the duration") {
    val clipA = AudioClip(x => x / 2.0, 2.0)
    val clipB = AudioClip(x => x / 4.0, 3.0)
    assert((clipA.append(clipB)).duration == 5.0)
  }
}
