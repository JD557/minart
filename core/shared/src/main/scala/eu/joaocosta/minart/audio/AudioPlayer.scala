package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {

  /** Enqueues an audio clip to be played later.
    */
  def play(wave: AudioClip): Unit

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
    private val clipQueue  = scala.collection.mutable.Queue[AudioClip]()

    def isEmpty  = synchronized { valueQueue.isEmpty && clipQueue.isEmpty }
    def nonEmpty = !isEmpty
    def size     = valueQueue.size + clipQueue.map(_.numSamples(sampleRate)).sum

    def enqueue(clip: AudioClip): this.type = synchronized {
      clipQueue.enqueue(clip)
      this
    }
    def dequeue(): Double = synchronized {
      if (valueQueue.nonEmpty) {
        valueQueue.dequeue()
      } else if (clipQueue.nonEmpty) {
        val nextWave = clipQueue.dequeue()
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
      clipQueue.clear()
      valueQueue.clear()
      this
    }
  }
}
