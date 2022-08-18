package eu.joaocosta.minart.graphics.image.helpers

import java.io.OutputStream

import scala.collection.compat.immutable.LazyList

/** Helper methods to write binary data to an output stream.
  */
trait ByteWriter[F[_]] {
  type ByteStream         = F[Array[Byte]]
  type ByteStreamState[E] = State[ByteStream, E, Unit]

  /** Writes a ByteStream to a output stream */
  def toOutputStream[E](data: ByteStreamState[E], os: OutputStream): Either[E, Unit]

  /** Empty state */
  def emptyStream: ByteStreamState[Nothing] = State.pure(())

  /** Appends this byte stream to the current accumulator */
  def append(stream: ByteStream): ByteStreamState[Nothing]

  /** Adds a sequence of bytes to the tail of the byte stream */
  def writeBytes(bytes: Seq[Int]): ByteStreamState[String]

  /** Write 1 Byte */
  def writeByte(byte: Int): ByteStreamState[String] = writeBytes(Seq(byte))

  /** Writes a String */
  def writeString(string: String): ByteStreamState[String] =
    writeBytes(string.map(_.toInt))

  /** Writes a String Line */
  def writeStringLn(string: String, delimiter: String = "\n"): ByteStreamState[String] =
    writeString(string + delimiter)

  /** Writes a Integer in N Bytes (Little Endian) */
  def writeLENumber(value: Int, bytes: Int): ByteStreamState[String] =
    writeBytes((0 until bytes).map { idx => (value >> (idx * 8)) & 0x000000ff })

  /** Writes a Integer in N Bytes (Big Endian) */
  def writeBENumber(value: Int, bytes: Int): ByteStreamState[String] =
    writeBytes((0 until bytes).reverse.map { idx => (value >> (idx * 8)) & 0x000000ff })
}

object ByteWriter {
  object LazyListByteWriter extends ByteWriter[LazyList] {
    def toOutputStream[E](data: ByteStreamState[E], os: OutputStream): Either[E, Unit] =
      data.run(LazyList.empty[Array[Byte]]).right.map { case (s, _) =>
        s.foreach(bytes => os.write(bytes))
      }

    def append(stream: ByteStream): ByteStreamState[Nothing] =
      State.modify[ByteStream](s => s ++ stream)

    def writeBytes(bytes: Seq[Int]): ByteStreamState[String] =
      if (bytes.forall(b => b >= 0 && b <= 255)) append(LazyList(bytes.map(_.toByte).toArray))
      else State.error(s"Sequence $bytes contains invalid bytes")
  }

  object IteratorByteWriter extends ByteWriter[Iterator] {
    def toOutputStream[E](data: ByteStreamState[E], os: OutputStream): Either[E, Unit] =
      data.run(Iterator.empty).right.map { case (s, _) =>
        s.foreach(bytes => os.write(bytes))
      }

    def append(stream: ByteStream): ByteStreamState[Nothing] =
      State.modify[ByteStream](s => s ++ stream)

    def writeBytes(bytes: Seq[Int]): ByteStreamState[String] =
      if (bytes.forall(b => b >= 0 && b <= 255)) append(Iterator(bytes.map(_.toByte).toArray))
      else State.error(s"Sequence $bytes contains invalid bytes")
  }
}
