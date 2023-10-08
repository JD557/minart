package eu.joaocosta.minart.runtime

import java.io.{InputStream, OutputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Try, Using}

import eu.joaocosta.minart.backend.defaults.*

/** Resource that can be loaded.
  */
trait Resource {

  /** Path to the resource
    */
  def path: String

  /** Loads the resource synchronously, and returns an [[java.io.InputStream]].
    * The InputStream is NOT closed in the end.
    *
    * This method should only be used if for some reason the input stream must stay open (e.g. for data streaming)
    */
  def unsafeInputStream(): InputStream

  /** Loads the resource synchronously, and returns an [[java.io.OutputStream]].
    * The OutputStream is NOT closed in the end.
    *
    * This method should only be used if for some reason the output stream must stay open (e.g. for data streaming)
    */
  def unsafeOutputStream(): OutputStream

  /** Checks if the resource exists */
  def exists(): Boolean = withInputStream(_ => ()).isSuccess

  /** Loads the resource synchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    *  The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStream]] instead.
    */
  def withSource[A](f: Source => A): Try[A] =
    Using[Source, A](
      Source.fromInputStream(unsafeInputStream())
    )(f)

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def withSourceAsync[A](f: Source => A): Future[A]

  /** Loads the resource synchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStream[A](f: InputStream => A): Try[A] =
    Using[InputStream, A](unsafeInputStream())(f)

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStreamAsync[A](f: InputStream => A): Future[A]

  /** Provides a [[java.io.OutputStream]] to write data to this resource location.
    * The OutputStream is closed in the end, so it should not escape this call.
    */
  def withOutputStream[A](f: OutputStream => A): Try[A] =
    Using[OutputStream, A](unsafeOutputStream())(f)
}

object Resource {
  def apply(resourcePath: String)(using backend: DefaultBackend[String, Resource]): Resource =
    backend.defaultValue(resourcePath)
}
