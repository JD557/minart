package eu.joaocosta.minart.audio.sound

import scala.util.Try

import verify._

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object AudioClipReaderSpec extends BasicTestSuite {

  def sameClip(results: List[AudioClip]): Unit = {
    results.sliding(2).foreach {
      case clip1 :: clip2 :: _ =>
        assert(Sampler.sampleClip(clip1, 44100).toList == Sampler.sampleClip(clip2, 44100).toList)
      case _ => ()
    }
  }

  def testDuration(clip: AudioClip, duration: Double, epsilon: Double = 0.01): Unit = {
    assert(clip.duration >= duration - epsilon && clip.duration <= duration + epsilon)
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

    test("Load the same data from different formats") {
      sameClip(
        List(
          Sound.loadWavClip(Resource("sample-32bit.wav")).get,
          Sound.loadAiffClip(Resource("sample-32bit.aiff")).get
        )
      )
    }
  }
}
