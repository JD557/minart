package eu.joaocosta.minart.graphics.image

//import scala.collection.compat.immutable.LazyList

/** Common operations for image decoding
  */
package object helpers {

  trait HelpersF[F[_]] {
    type ParseResult[T]   = Either[String, (F[Int], T)]
    type ParseState[E, T] = State[F[Int], E, T]

    /** Skip N Bytes */
    def skipBytes(n: Int): ParseState[Nothing, Unit]

    /** Read 1 Byte */
    val readByte: ParseState[String, Option[Int]]

    /** Read N Byte */
    def readBytes(n: Int): ParseState[Nothing, Array[Int]]

    /** Reads data while a predicate is true */
    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]]

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

  object LazyListHelpers extends HelpersF[LazyList] {
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

  object IteratorHelpers extends HelpersF[Iterator] {
    def skipBytes(n: Int): ParseState[Nothing, Unit] =
      State.modify(_.drop(n))

    val readByte: ParseState[String, Option[Int]] = State { bytes =>
      val hd = bytes.nextOption
      bytes -> hd
    }

    def readBytes(n: Int): ParseState[Nothing, Array[Int]] = State { bytes =>
      val hd = bytes.take(n).toArray
      bytes -> hd
    }

    def readWhile(p: Int => Boolean): ParseState[Nothing, List[Int]] = State { bytes =>
      val buffer = List.newBuilder[Int]
      var head   = bytes.nextOption
      while (head.exists(p)) {
        buffer += head.get
        head = bytes.nextOption
      }
      (head.iterator ++ bytes) -> buffer.result()
    }
  }

}
