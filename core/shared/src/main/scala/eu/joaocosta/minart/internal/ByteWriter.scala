package eu.joaocosta.minart.internal

import java.io.OutputStream

/** Helper methods to write binary data to an output stream.
  */
private[minart] trait ByteWriter[ByteStream] {
  type ByteStreamState[E] = State[ByteStream, E, Unit]

  /** Writes a ByteStream to a output stream */
  def toOutputStream[E](data: ByteStreamState[E], os: OutputStream): Either[E, Unit]

  /** Empty state */
  final def emptyStream: ByteStreamState[Nothing] = State.pure(())

  /** Appends this byte stream to the current accumulator */
  def append(stream: ByteStream): ByteStreamState[Nothing]

  /** Adds a sequence of bytes to the tail of the byte stream */
  def writeBytes(bytes: Seq[Int]): ByteStreamState[String]

  /** Write 1 Byte */
  final def writeByte(byte: Int): ByteStreamState[String] = writeBytes(Seq(byte))

  /** Writes a String */
  final def writeString(string: String): ByteStreamState[String] =
    writeBytes(string.map(_.toInt))

  /** Writes a String Line */
  final def writeStringLn(string: String, delimiter: String = "\n"): ByteStreamState[String] =
    writeString(string + delimiter)

  /** Writes a Integer in N Bytes (Little Endian) */
  final def writeLENumber(value: Int, bytes: Int): ByteStreamState[String] =
    writeBytes((0 until bytes).map { idx => (value >> (idx * 8)) & 0x000000ff })

  /** Writes a Integer in N Bytes (Big Endian) */
  final def writeBENumber(value: Int, bytes: Int): ByteStreamState[String] =
    writeBytes((0 until bytes).reverse.map { idx => (value >> (idx * 8)) & 0x000000ff })
}

private[minart] object ByteWriter {
  object IteratorByteWriter extends ByteWriter[Iterator[Array[Byte]]] {
    def toOutputStream[E](data: ByteStreamState[E], os: OutputStream): Either[E, Unit] =
      data.run(Iterator.empty).map { case (s, _) =>
        s.foreach(bytes => os.write(bytes))
      }

    def append(stream: Iterator[Array[Byte]]): ByteStreamState[Nothing] =
      State.modify[Iterator[Array[Byte]]](s => s ++ stream)

    def writeBytes(bytes: Seq[Int]): ByteStreamState[String] =
      if (bytes.forall(b => b >= 0 && b <= 255)) append(Iterator(bytes.map(_.toByte).toArray))
      else State.error(s"Sequence $bytes contains invalid bytes")
  }
}
