package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {

  /** Enqueues an audio wave to be played later.
    */
  def play(wave: AudioWave): Unit

  /** Checks if this player still has data to be played.
    */
  def isPlaying(): Boolean

  /** Stops playback and removes all enqueued waves.
    */
  def stop(): Unit
}

object AudioPlayer {
  def apply()(implicit backend: DefaultBackend[Any, AudioPlayer]): AudioPlayer =
    backend.defaultValue()

  class AudioQueue(sampleRate: Int) {
    private val valueQueue = scala.collection.mutable.Queue[Double]()
    private val waveQueue  = scala.collection.mutable.Queue[AudioWave]()

    def isEmpty  = synchronized { valueQueue.isEmpty && waveQueue.isEmpty }
    def nonEmpty = !isEmpty
    def size     = valueQueue.size + waveQueue.map(_.numSamples(sampleRate)).sum

    def enqueue(wave: AudioWave): this.type = synchronized {
      waveQueue.enqueue(wave)
      this
    }
    def dequeue(): Double = synchronized {
      if (valueQueue.nonEmpty) {
        valueQueue.dequeue()
      } else if (waveQueue.nonEmpty) {
        val nextWave = waveQueue.dequeue()
        valueQueue ++= nextWave.iterator(sampleRate)
        valueQueue.dequeue()
      } else {
        0.0
      }
    }
    def dequeueByte(): Byte = {
      (math.min(math.max(-1.0, dequeue()), 1.0) * 127).toByte
    }

    def clear(): this.type = synchronized {
      waveQueue.clear()
      valueQueue.clear()
      this
    }
  }
}
