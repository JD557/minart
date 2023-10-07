package eu.joaocosta.minart.audio

class AudioQueueSpec extends munit.FunSuite {

  test("An empty single channel audio queue returns 0") {
    val queue = new AudioQueue.SingleChannelAudioQueue(1)
    assert(queue.size == 0)
    assert(queue.isEmpty() == true)
    assert(queue.dequeue() == 0)
  }

  test("An single channel audio queue has size Int.MaxValue") {
    val queue = new AudioQueue.SingleChannelAudioQueue(1)
    queue.enqueue(AudioWave.silence.take(Double.PositiveInfinity))
    assert(queue.size == Int.MaxValue)
    assert(queue.isEmpty() == false)
    queue.dequeue()
    assert(queue.size == Int.MaxValue)
    assert(queue.isEmpty() == false)
  }

  test("A single channel audio queue correctly samples audio") {
    val clip = AudioClip(x => x / 2.0, 2.0)

    val queueA = new AudioQueue.SingleChannelAudioQueue(1)

    queueA.enqueue(clip)
    assert(queueA.size == 2)
    assert(queueA.isEmpty() == false)
    assert(queueA.dequeue() == 0.00)
    assert(queueA.dequeue() == 0.50)
    assert(queueA.isEmpty() == true)

    val queueB = new AudioQueue.SingleChannelAudioQueue(2)

    queueB.enqueue(clip)
    assert(queueB.size == 4)
    assert(queueB.isEmpty() == false)
    assert(queueB.dequeue() == 0.00)
    assert(queueB.dequeue() == 0.25)
    assert(queueB.dequeue() == 0.50)
    assert(queueB.dequeue() == 0.75)
    assert(queueB.isEmpty() == true)
  }

  test("A single channel audio queue correctly samples audio as bytes") {
    val clip = AudioClip(x => x - 1.0, 2.0)

    val queue = new AudioQueue.SingleChannelAudioQueue(2)

    queue.enqueue(clip)
    assert(queue.size == 4)
    assert(queue.isEmpty() == false)
    assert(queue.dequeueByte() == -127)
    assert(queue.dequeueByte() == -63)
    assert(queue.dequeueByte() == 0)
    assert(queue.dequeueByte() == 63)
    assert(queue.isEmpty() == true)
  }

  test("A single channel audio queue can be cleared") {
    val clip = AudioClip(_ => 1.0, 2.0)

    val queue = new AudioQueue.SingleChannelAudioQueue(2)

    queue.enqueue(clip)
    assert(queue.size == 4)
    assert(queue.isEmpty() == false)
    queue.clear()
    assert(queue.isEmpty() == true)
  }

  test("A multi channel audio queue correctly mixes audio from two clips with the same duration") {
    val clipA = AudioClip(x => x / 4.0, 2.0)
    val clipB = AudioClip(_ => 0.25, 2.0)

    val queue = new AudioQueue.MultiChannelAudioQueue(1)

    queue.enqueue(clipA, 0)
    queue.enqueue(clipB, 1)
    assert(queue.size == 2)
    assert(queue.isEmpty() == false)
    assert(queue.dequeue() == 0.00 + 0.25)
    assert(queue.dequeue() == 0.25 + 0.25)
    assert(queue.isEmpty() == true)
  }

  test("A multi channel audio queue correctly mixes audio from two clips with different durations") {
    val clipA = AudioClip(x => x / 4.0, 2.0)
    val clipB = AudioClip(_ => 0.25, 1.0)

    val queue = new AudioQueue.MultiChannelAudioQueue(1)

    queue.enqueue(clipA, 0)
    queue.enqueue(clipB, 1)
    assert(queue.size == 2)
    assert(queue.isEmpty() == false)
    assert(queue.dequeue() == 0.00 + 0.25)
    assert(queue.dequeue() == 0.25)
    assert(queue.isEmpty() == true)
  }

  test("A multi channel audio queue correctly clips audio") {
    val clipA = AudioClip(x => (x * 10) - 5, 2.0)
    val clipB = AudioClip(x => (x * 10) - 5, 1.0)

    val queue = new AudioQueue.MultiChannelAudioQueue(2)

    queue.enqueue(clipA, 0)
    queue.enqueue(clipB, 1)
    assert(queue.size == 4)
    assert(queue.isEmpty() == false)
    assert(queue.dequeue() == -1.0)
    assert(queue.dequeue() == 0.0)
    assert(queue.dequeue() == 1.0)
    assert(queue.dequeue() == 1.0)
    assert(queue.isEmpty() == true)
  }

  test("A single channel audio queue can be cleared") {
    val clip = AudioClip(_ => 1.0, 2.0)

    val queue = new AudioQueue.MultiChannelAudioQueue(2)

    queue.enqueue(clip, 0)
    queue.enqueue(clip, 1)
    assert(queue.size == 4)
    assert(queue.isEmpty() == false)
    queue.clear(0)
    assert(queue.isEmpty() == false)
    queue.clear(1)
    assert(queue.isEmpty() == true)

    queue.enqueue(clip, 0)
    queue.enqueue(clip, 1)
    assert(queue.isEmpty() == false)
    queue.clear()
    assert(queue.isEmpty() == true)
  }
}
