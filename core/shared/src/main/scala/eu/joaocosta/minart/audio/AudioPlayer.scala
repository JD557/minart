package eu.joaocosta.minart.audio

import eu.joaocosta.minart.backend.defaults._

trait AudioPlayer {

  /** Enqueues an audio clip to be played later.
    */
  def play(wave: AudioClip): Unit

  /** Enqueues an audio clip to be played later in a certain channel.
    */
  def play(wave: AudioClip, channel: Int): Unit

  /** Checks if this player still has data to be played.
    */
  def isPlaying(): Boolean

  /** Stops playback and removes all enqueued waves.
    */
  def stop(): Unit

  /** Stops playback and removes all enqueued waves in a certain channel.
    */
  def stop(channel: Int): Unit
}

object AudioPlayer {

  /** Returns a new [[AudioPlayer]] for the default backend for the target platform.
    *
    * @return [[AudioPlayer]] using the default backend for the target platform
    */
  def create(settings: AudioPlayer.Settings)(implicit backend: DefaultBackend[Any, LowLevelAudioPlayer]): AudioPlayer =
    AudioPlayerManager().init(settings)

  case class Settings(sampleRate: Int = 44100, bufferSize: Int = 4096)

  sealed trait AudioQueue {
    def isEmpty: Boolean
    def nonEmpty: Boolean = !isEmpty
    def size: Int

    def enqueue(clip: AudioClip): this.type
    def dequeue(): Double
    def dequeueByte(): Byte = {
      (math.min(math.max(-1.0, dequeue()), 1.0) * 127).toByte
    }
    def clear(): this.type
  }

  class SingleChannelAudioQueue(sampleRate: Int) extends AudioQueue {
    private val valueQueue = scala.collection.mutable.Queue[Double]()
    private val clipQueue  = scala.collection.mutable.Queue[AudioClip]()

    def isEmpty = synchronized { valueQueue.isEmpty && clipQueue.isEmpty }
    def size    = valueQueue.size + clipQueue.map(_.numSamples(sampleRate)).sum

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

    def clear(): this.type = synchronized {
      clipQueue.clear()
      valueQueue.clear()
      this
    }
  }

  class MultiChannelAudioQueue(sampleRate: Int) extends AudioQueue {
    private val channels = scala.collection.mutable.Map[Int, AudioQueue]()

    def isEmpty = channels.values.forall(_.isEmpty)
    def size =
      if (channels.isEmpty) 0
      else channels.values.map(_.size).max

    def enqueue(clip: AudioClip): this.type = enqueue(clip, 0)
    def enqueue(clip: AudioClip, channel: Int): this.type = synchronized {
      val queue = channels.getOrElseUpdate(channel, new SingleChannelAudioQueue(sampleRate))
      queue.enqueue(clip)
      this
    }
    def dequeue(): Double = synchronized {
      math.max(-1.0, math.min(channels.values.map(_.dequeue()).sum, 1.0))
    }

    def clear(): this.type = synchronized {
      channels.clear()
      this
    }
    def clear(channel: Int): this.type = synchronized {
      channels.get(channel).foreach(_.clear())
      this
    }
  }
}
