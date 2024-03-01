package eu.joaocosta.minart.internal

import java.io.InputStream

/** Helper methods to read binary data from an input stream.
  */
private[minart] object ByteReader {
  type ParseResult[T]   = Either[String, (CustomInputStream, T)]
  type ParseState[E, T] = State[CustomInputStream, E, T]

  /** Generates a sequence of bytes from a byte stream */
  def fromInputStream(is: InputStream): CustomInputStream = new CustomInputStream(is)

  /** Checks if a byte sequence is empty */
  def isEmpty(seq: CustomInputStream): Boolean = seq.isEmpty()

  /** Skip N Bytes */
  def skipBytes(n: Int): ParseState[Nothing, Unit] =
    State.modify { bytes =>
      bytes.skip(n)
      bytes
    }

  /** Read 1 Byte */
  val readByte: ParseState[String, Option[Int]] = State { bytes =>
    val value = bytes.read()
    bytes -> (if (value >= -1) Some(value) else None)
  }

  /** Read N Bytes */
  def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
    val byteArr = Array.ofDim[Byte](n)
    var read    = bytes.read(byteArr)
    while (read >= 0 && read < n) {
      byteArr(read) = bytes.read().toByte
      read += 1
    }
    bytes -> byteArr.map(b => java.lang.Byte.toUnsignedInt(b))
  }

  /** Read N Bytes */
  def readRawBytes(n: Int): ParseState[Nothing, Array[Byte]] = State { bytes =>
    val byteArr = Array.ofDim[Byte](n)
    var read    = bytes.read(byteArr)
    while (read >= 0 && read < n) {
      byteArr(read) = bytes.read().toByte
      read += 1
    }
    bytes -> byteArr
  }

  /** Reads data while a predicate is true */
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

  /** Does nothing */
  val noop: ParseState[Nothing, Unit] = skipBytes(0)

  /** Read a String from N Bytes */
  def readString(n: Int): ParseState[Nothing, String] =
    readBytes(n).map { bytes => bytes.map(_.toChar).mkString("") }

  /** Read a Integer N Bytes (Little Endian) */
  def readLENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }

  /** Read a Integer N Bytes as a Long (Little Endian) */
  def readLENumberLong(n: Int): ParseState[Nothing, Long] = readBytes(n).map { bytes =>
    bytes.zipWithIndex.map { case (num, idx) => num.toLong << (idx * 8) }.sum
  }

  /** Read a Integer N Bytes (Big Endian) */
  def readBENumber(n: Int): ParseState[Nothing, Int] = readBytes(n).map { bytes =>
    bytes.reverse.zipWithIndex.map { case (num, idx) => num.toInt << (idx * 8) }.sum
  }

  /** Read a Integer N Bytes as a Long (Big Endian) */
  def readBENumberLong(n: Int): ParseState[Nothing, Long] = readBytes(n).map { bytes =>
    bytes.reverse.zipWithIndex.map { case (num, idx) => num.toLong << (idx * 8) }.sum
  }

  final class CustomInputStream(inner: InputStream) extends InputStream {
    private var hasBuffer: Boolean          = false
    private var buffer: Int                 = 0
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
}
