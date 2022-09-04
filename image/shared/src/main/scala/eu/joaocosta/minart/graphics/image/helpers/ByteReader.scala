package eu.joaocosta.minart.graphics.image.helpers

import java.io.InputStream

import scala.collection.compat.immutable.LazyList

/** Helper methods to read binary data from an input stream.
  */
trait ByteReader[Container] {
  type ParseResult[T]   = Either[String, (Container, T)]
  type ParseState[E, T] = State[Container, E, T]

  /** Generates a sequence of bytes from a byte stream */
  def fromInputStream(is: InputStream): Container

  /** Checks if a byte sequence is empty */
  def isEmpty(seq: Container): Boolean

  /** Adds a sequence of bytes to the head of the byte stream */
  def pushBytes(bytes: Seq[Int]): ParseState[Nothing, Unit]

  /** Skip N Bytes */
  def skipBytes(n: Int): ParseState[Nothing, Unit]

  /** Read 1 Byte */
  val readByte: ParseState[String, Option[Int]]

  /** Read N Byte */
  def readBytes(n: Int): ParseState[Nothing, Array[Int]]

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

    def pushBytes(bytes: Seq[Int]): ParseState[Nothing, Unit] =
      State.modify(s => bytes.to(LazyList) ++ s)

    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify(_.drop(n))

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      bytes.tail -> bytes.headOption
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      bytes.drop(n) -> bytes.take(n).toArray
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

    def pushBytes(bytes: Seq[Int]): ParseState[Nothing, Unit] =
      State.modify(s => bytes.iterator ++ s)

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

  class ModifiableInputStream(inner: InputStream) extends InputStream {
    val buffer                              = new collection.mutable.Stack[Int]()
    override def available(): Int           = buffer.size + inner.available()
    override def close(): Unit              = inner.close()
    override def mark(readLimit: Int): Unit = ()
    override def markSupported(): Boolean   = false
    override def read() = {
      if (buffer.isEmpty) inner.read()
      else buffer.pop()
    }
    override def read(b: Array[Byte]): Int = {
      if (buffer.isEmpty || b.isEmpty) inner.read(b)
      else {
        val toPop    = math.min(b.size, buffer.size).toInt
        val toStream = b.size - toPop
        (0 until toPop).foreach(idx => b(idx) = buffer.pop().toByte)
        inner.read(b, toPop, toStream) + toPop
      }
    }
    override def reset(): Unit = ()
    override def skip(n: Long): Long = {
      if (buffer.isEmpty || n == 0) inner.skip(n)
      else {
        val toPop    = math.min(n, buffer.size).toInt
        val toStream = n - toPop
        (0 until toPop).foreach(_ => buffer.pop().toByte)
        inner.skip(toStream) + toPop
      }
    }

    // Extra methods
    def isEmpty(): Boolean =
      if (buffer.isEmpty) {
        val next = inner.read()
        if (next == -1) true
        else {
          buffer.push(next)
          false
        }
      } else false

    def pushBytes(bytes: Seq[Int]): this.type = {
      bytes.reverseIterator.foreach(byte => buffer.push(byte))
      this
    }
  }

  object InputStreamByteReader extends ByteReader[ModifiableInputStream] {
    def fromInputStream(is: InputStream): ModifiableInputStream = new ModifiableInputStream(is)

    def isEmpty(seq: ModifiableInputStream): Boolean = seq.isEmpty()

    def pushBytes(bytes: Seq[Int]): ParseState[Nothing, Unit] =
      State.modify(s => s.pushBytes(bytes))

    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify { bytes =>
        bytes.skip(n)
        bytes
      }

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      val value = bytes.read()
      bytes -> Option.when(value >= -1)(value)
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      val byteArr    = Array.ofDim[Byte](n)
      val resultSize = bytes.read(byteArr)
      bytes -> byteArr.iterator
        .take(resultSize)
        .map { byte => java.lang.Byte.toUnsignedInt(byte) }
        .toArray
    }

    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]] = State { bytes =>
      val buffer = List.newBuilder[Int]
      var value  = bytes.read()
      while (value != -1 && p(value)) {
        buffer += value
        value = bytes.read()
      }
      if (value != -1) bytes.pushBytes(List(value))
      bytes -> buffer.result()
    }
  }
}
