package eu.joaocosta.minart.audio

/** Internal AudioQueue abstraction.
  *
  *  This is not expected to be used by user code, but it's helpful to implement custom backends
  */
sealed trait AudioQueue {
  def isEmpty(): Boolean
  final def nonEmpty(): Boolean = !isEmpty()
  def size: Int

  def enqueue(clip: AudioClip): this.type
  def dequeue(): Double
  final def dequeueByte(): Byte =
    (Math.min(Math.max(-1.0, dequeue()), 1.0) * 127).toByte
  def clear(): this.type
}

object AudioQueue {

  private val maxBufferSize: Double = 10.0

  final class SingleChannelAudioQueue(sampleRate: Int) extends AudioQueue {
    private val valueQueue = scala.collection.mutable.Queue[Double]()
    private val clipQueue  = scala.collection.mutable.ArrayDeque[AudioClip]()
    var mix                = AudioMix()

    def isEmpty() = synchronized { valueQueue.isEmpty && clipQueue.isEmpty }
    def size      = clipQueue.foldLeft(valueQueue.size) { case (acc, clip) =>
      if (acc == Int.MaxValue || clip.duration.isInfinite) Int.MaxValue
      else {
        val newValue = acc + Sampler.numSamples(clip, sampleRate)
        if (newValue < 0) Int.MaxValue
        else newValue
      }
    }

    def enqueue(clip: AudioClip): this.type = synchronized {
      clipQueue.append(clip)
      this
    }
    def dequeue(): Double = synchronized {
      if (valueQueue.nonEmpty) {
        valueQueue.dequeue() * mix.volume
      } else if (!clipQueue.isEmpty) {
        val nextClip = clipQueue.removeHead()
        if (nextClip.duration > maxBufferSize) {
          valueQueue ++= Sampler.sampleClip(nextClip.take(maxBufferSize), sampleRate)
          clipQueue.prepend(nextClip.drop(maxBufferSize))
        } else {
          valueQueue ++= Sampler.sampleClip(nextClip, sampleRate)
        }
        valueQueue.dequeue() * mix.volume
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

  final class MultiChannelAudioQueue(sampleRate: Int) extends AudioQueue {
    private val channels = scala.collection.mutable.Map[Int, SingleChannelAudioQueue]()

    private val channelMixes                  = scala.collection.mutable.Map[Int, AudioMix]()
    def getChannelMix(channel: Int): AudioMix =
      channelMixes.getOrElse(channel, AudioMix())
    def setChannelMix(mix: AudioMix, channel: Int): Unit =
      channelMixes.update(channel, mix)
      channels.get(channel).foreach(_.mix = mix)

    def isEmpty()              = channels.values.forall(_.isEmpty())
    def isEmpty(channel: Int)  = channels.get(channel).map(_.isEmpty()).getOrElse(true)
    def nonEmpty(channel: Int) = !isEmpty(channel)
    def size                   =
      if (channels.isEmpty) 0
      else channels.values.maxBy(_.size).size

    def enqueue(clip: AudioClip): this.type               = enqueue(clip, 0)
    def enqueue(clip: AudioClip, channel: Int): this.type = synchronized {
      val queue = channels.getOrElseUpdate(channel, new SingleChannelAudioQueue(sampleRate))
      queue.mix = getChannelMix(channel)
      queue.enqueue(clip)
      this
    }
    def dequeue(): Double = synchronized {
      Math.max(-1.0, Math.min(channels.values.map(_.dequeue()).sum, 1.0))
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
