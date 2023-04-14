package eu.joaocosta.minart.audio

import scala.collection.JavaConverters._

/** Internal AudioQueue abstraction.
  *
  *  This is not expected to be used by user code, but it's helpful to implement custom backends
  */
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

object AudioQueue {

  private val maxBufferSize: Double = 10.0

  class SingleChannelAudioQueue(sampleRate: Int) extends AudioQueue {
    private val valueQueue = scala.collection.mutable.Queue[Double]()
    private val clipQueue  = new java.util.ArrayDeque[AudioClip]() // Use scala's ArrayDeque on 2.13+

    def isEmpty = synchronized { valueQueue.isEmpty && clipQueue.isEmpty }
    def size = clipQueue.iterator.asScala.foldLeft(valueQueue.size) { case (acc, clip) =>
      if (acc == Int.MaxValue || clip.duration.isInfinite) Int.MaxValue
      else {
        val newValue = acc + clip.numSamples(sampleRate)
        if (newValue < 0) Int.MaxValue
        else newValue
      }
    }

    def enqueue(clip: AudioClip): this.type = synchronized {
      clipQueue.addLast(clip)
      this
    }
    def dequeue(): Double = synchronized {
      if (valueQueue.nonEmpty) {
        valueQueue.dequeue()
      } else if (!clipQueue.isEmpty()) {
        val nextClip = clipQueue.removeFirst()
        if (nextClip.duration > maxBufferSize) {
          valueQueue ++= nextClip.take(maxBufferSize).iterator(sampleRate)
          clipQueue.addFirst(nextClip.drop(maxBufferSize))
        } else {
          valueQueue ++= nextClip.iterator(sampleRate)
        }
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
      else channels.values.maxBy(_.size).size

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
