package eu.joaocosta.minart.audio.sound

import scala.util.Try

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

class AudioClipReaderSpec extends munit.FunSuite {

  def approx(x: Double, y: Double, epsilon: Double = 0.01): Boolean =
    x >= y - epsilon && x <= y + epsilon

  def similarClip(results: List[AudioClip], epsilon: Double = 0.01): Unit = {
    results.sliding(2).foreach {
      case clip1 :: clip2 :: _ =>
        val zippedSamples = Sampler.sampleClip(clip1, 44100).zip(Sampler.sampleClip(clip2, 44100)).toList
        val mse           = zippedSamples.map { case (x, y) => (x - y) * (x - y) }.sum / zippedSamples.size
        assert(mse <= epsilon)
      case _ => ()
    }
  }

  def sameClip(results: List[AudioClip]): Unit = {
    results.sliding(2).foreach {
      case clip1 :: clip2 :: _ =>
        assert(Sampler.sampleClip(clip1, 44100).toList == Sampler.sampleClip(clip2, 44100).toList)
      case _ => ()
    }
  }

  def testDuration(clip: AudioClip, duration: Double, epsilon: Double = 0.01): Unit = {
    assert(approx(clip.duration, duration, epsilon))
  }

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Load an AIFF file") {
      val clip = Sound.loadAiffClip(Resource("sample-32bit.aiff"))
      testDuration(clip.get, 0.3975)
    }

    test("Load a WAV file") {
      val clip = Sound.loadWavClip(Resource("sample-32bit.wav"))
      testDuration(clip.get, 0.3975)
    }

    test("Load a QOA file") {
      val clip = Sound.loadQoaClip(Resource("sample.qoa"))
      testDuration(clip.get, 0.3975)
    }

    test("Load the same data from different lossless formats") {
      sameClip(
        List(
          Sound.loadWavClip(Resource("sample-32bit.wav")).get,
          Sound.loadAiffClip(Resource("sample-32bit.aiff")).get
        )
      )
    }

    test("Load the similar data from different formats") {
      similarClip(
        List(
          Sound.loadWavClip(Resource("sample-32bit.wav")).get,
          Sound.loadAiffClip(Resource("sample-32bit.aiff")).get,
          Sound.loadQoaClip(Resource("sample.qoa")).get
        )
      )
    }
  }
}
