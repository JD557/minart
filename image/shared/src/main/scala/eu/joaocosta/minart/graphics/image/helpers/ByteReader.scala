package eu.joaocosta.minart.graphics.image.helpers

import java.io.InputStream

import scala.collection.compat.immutable.LazyList

/** Helper methods to read binary data from an input stream.
  */
trait ByteReader[F[_]] {
  type ParseResult[T]   = Either[String, (F[Int], T)]
  type ParseState[E, T] = State[F[Int], E, T]

  /** Generates a sequence of bytes from a byte stream */
  def fromInputStream(is: InputStream): F[Int]

  /** Checks if a byte sequence is empty */
  def isEmpty[A](seq: F[A]): Boolean

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
  object LazyListByteReader extends ByteReader[LazyList] {
    def fromInputStream(is: InputStream): LazyList[Int] =
      LazyList.continually(is.read()).takeWhile(_ != -1)

    def isEmpty[A](seq: LazyList[A]): Boolean = seq.isEmpty

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

  object IteratorByteReader extends ByteReader[Iterator] {
    // Ported from 2.13 stdlib
    private def nextOption[Int](it: Iterator[Int]): Option[Int] =
      if (it.hasNext) Some(it.next()) else None

    def fromInputStream(is: InputStream): Iterator[Int] =
      Iterator.continually(is.read()).takeWhile(_ != -1)

    def isEmpty[A](seq: Iterator[A]): Boolean = seq.isEmpty

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
        bufferedBytes.next
      }
      bufferedBytes -> buffer.result()
    }
  }
}
