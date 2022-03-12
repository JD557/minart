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
    private def nextOption(it: Iterator[Int]): Option[Int] =
      if (it.hasNext) Some(it.next())
      else None

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
      (bytes, nextOption(bytes))
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
      val buffer = List.newBuilder[Int]
      var head   = nextOption(bytes)
      while (head.exists(p)) {
        buffer += head.get
        head = nextOption(bytes)
      }
      (head.iterator ++ bytes) -> buffer.result()
    }
  }

}
