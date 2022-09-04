package eu.joaocosta.minart.graphics.image.helpers

import java.io.InputStream

import scala.collection.compat.immutable.LazyList

/** Helper methods to read binary data from an input stream.
  */
trait ByteReader[ByteSeq] {
  type ParseResult[T]   = Either[String, (ByteSeq, T)]
  type ParseState[E, T] = State[ByteSeq, E, T]

  /** Generates a sequence of bytes from a byte stream */
  def fromInputStream(is: InputStream): ByteSeq

  /** Checks if a byte sequence is empty */
  def isEmpty(seq: ByteSeq): Boolean

  /** Skip N Bytes */
  def skipBytes(n: Int): ParseState[Nothing, Unit]

  /** Read 1 Byte */
  val readByte: ParseState[String, Option[Int]]

  /** Read N Bytes */
  def readBytes(n: Int): ParseState[Nothing, Array[Int]]

  /** Read N Bytes */
  def readRawBytes(n: Int): ParseState[Nothing, Array[Byte]]

  /** Reads data while a predicate is true */
  def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]]

  /** Does nothing */
  val noop: ParseState[Nothing, Unit] = skipBytes(0)

  /** Read a String from N Bytes */
  def readString(n: Int): ParseState[Nothing, String] =
    readBytes(n).map { bytes => bytes.map(_.toChar).mkString("") }

  /** Read a Integer N Bytes (Little Endian) */
  def readLENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }

  /** Read a Integer N Bytes (Big Endian) */
  def readBENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.reverse.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }
}

object ByteReader {
  object LazyListByteReader extends ByteReader[LazyList[Int]] {
    def fromInputStream(is: InputStream): LazyList[Int] =
      LazyList.continually(is.read()).takeWhile(_ != -1)

    def isEmpty(seq: LazyList[Int]): Boolean = seq.isEmpty

    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify(_.drop(n))

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      bytes.tail -> bytes.headOption
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      bytes.drop(n) -> bytes.take(n).toArray
    }

    def readRawBytes(n: Int): ParseState[Nothing, Array[Byte]] = State { bytes =>
      bytes.drop(n) -> bytes.take(n).map(_.toByte).toArray
    }

    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]] = State { bytes =>
      val (isTrue, isFalse) = bytes.span(p)
      isFalse -> isTrue.toList
    }
  }

  object IteratorByteReader extends ByteReader[Iterator[Int]] {
    // Ported from 2.13 stdlib
    private def nextOption[Int](it: Iterator[Int]): Option[Int] =
      if (it.hasNext) Some(it.next()) else None

    def fromInputStream(is: InputStream): Iterator[Int] =
      Iterator.continually(is.read()).takeWhile(_ != -1)

    def isEmpty(seq: Iterator[Int]): Boolean = seq.isEmpty

    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify { bytes =>
        var count = n
        while (count > 0 && bytes.hasNext) {
          bytes.next()
          count -= 1
        }
        bytes
      }

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      bytes -> nextOption(bytes)
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      val buffer = Array.newBuilder[Int]
      var count  = n
      while (count > 0 && bytes.hasNext) {
        buffer += bytes.next()
        count -= 1
      }
      bytes -> buffer.result()
    }

    def readRawBytes(n: Int): ParseState[Nothing, Array[Byte]] = State { bytes =>
      val buffer = Array.newBuilder[Byte]
      var count  = n
      while (count > 0 && bytes.hasNext) {
        buffer += bytes.next().toByte
        count -= 1
      }
      bytes -> buffer.result()
    }

    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]] = State { bytes =>
      val bufferedBytes = bytes.buffered
      val buffer        = List.newBuilder[Int]
      while (bufferedBytes.hasNext && p(bufferedBytes.head)) {
        buffer += bufferedBytes.head
        bufferedBytes.next()
      }
      bufferedBytes -> buffer.result()
    }
  }

  class CustomInputStream(inner: InputStream) extends InputStream {
    var hasBuffer: Boolean                  = false
    var buffer: Int                         = 0
    override def available(): Int           = inner.available() + (if (hasBuffer) 1 else 0)
    override def close(): Unit              = inner.close()
    override def mark(readLimit: Int): Unit = ()
    override def markSupported(): Boolean   = false
    override def read() = {
      if (!hasBuffer) inner.read()
      else {
        hasBuffer = false
        buffer
      }
    }
    override def read(b: Array[Byte]): Int = {
      if (!hasBuffer || b.isEmpty) inner.read(b)
      else {
        hasBuffer = false
        b(0) = buffer.toByte
        inner.read(b, 1, b.size - 1)
      }
    }
    override def reset(): Unit = ()
    override def skip(n: Long): Long = {
      if (!hasBuffer || n == 0) inner.skip(n)
      else {
        hasBuffer = false
        inner.skip(n - 1) + 1
      }
    }

    // Extra methods
    def isEmpty(): Boolean =
      if (!hasBuffer) {
        val next = inner.read()
        if (next == -1) true
        else {
          buffer = next
          hasBuffer = true
          false
        }
      } else false

    def setBuffer(value: Int): Unit = {
      buffer = value
      hasBuffer = true
    }

  }

  object InputStreamByteReader extends ByteReader[CustomInputStream] {
    def fromInputStream(is: InputStream): CustomInputStream = new CustomInputStream(is)

    def isEmpty(seq: CustomInputStream): Boolean = seq.isEmpty()

    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify { bytes =>
        bytes.skip(n)
        bytes
      }

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      val value = bytes.read()
      bytes -> (if (value >= -1) Some(value) else None)
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      val byteArr    = Array.ofDim[Byte](n)
      val resultSize = bytes.read(byteArr)
      bytes -> byteArr.map(b => java.lang.Byte.toUnsignedInt(b))
    }

    def readRawBytes(n: Int): ParseState[Nothing, Array[Byte]] = State { bytes =>
      val byteArr    = Array.ofDim[Byte](n)
      val resultSize = bytes.read(byteArr)
      bytes -> byteArr
    }

    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]] = State { bytes =>
      val buffer = List.newBuilder[Int]
      var value  = bytes.read()
      while (value != -1 && p(value)) {
        buffer += value
        value = bytes.read()
      }
      if (value != -1) bytes.setBuffer(value)
      bytes -> buffer.result()
    }
  }
}
