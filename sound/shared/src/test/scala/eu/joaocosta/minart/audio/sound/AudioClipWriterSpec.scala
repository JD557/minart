package eu.joaocosta.minart.audio.sound

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import verify._

import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.runtime._

object AudioClipWriterSpec extends BasicTestSuite {

  def roundtripTest(baseResource: Resource, audioClipFormat: AudioClipReader with AudioClipWriter) = {
    val (oldWave, newWave) = (for {
      original <- audioClipFormat.loadClip(baseResource).get
      originalWave = original.byteIterator(44100).toList
      stored <- audioClipFormat.toByteArray(original)
      loaded <- audioClipFormat.fromByteArray(stored)
      loadedWave = loaded.byteIterator(44100).toList
    } yield (originalWave, loadedWave)).toOption.get

    assert(oldWave.size == newWave.size)
    assert(oldWave.zip(newWave).forall { case (oldS, newS) => math.abs(oldS - newS) <= 1 })
  }

  // Can't load resources in JS tests
  if (Platform() != Platform.JS) {
    test("Write a AIFF clip") {
      roundtripTest(Resource("sample-32bit.aiff"), aiff.AiffAudioFormat.defaultFormat)
    }

    test("Write a WAV clip") {
      roundtripTest(Resource("sample-32bit.wav"), wav.WavAudioFormat.defaultFormat)
    }
  }
}
