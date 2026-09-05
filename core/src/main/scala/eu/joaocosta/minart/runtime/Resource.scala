package eu.joaocosta.minart.runtime

import java.io.{InputStream, OutputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Try, Using}

import eu.joaocosta.minart.backend.defaults.*

/** Resource that can be loaded.
  */
trait Resource {

  /** Path to the resource. None if the resource doesn't have an associated path (stdin and stdout)
    */
  def path: Option[String]

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
  def exists(): Boolean = path.isEmpty || withInputStream(_ => ()).isSuccess

  /** Loads the resource synchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    *  The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStream]] instead.
    */
  def withSource[A](f: Source => A): Try[A] =
    if (path.isDefined) Using[Source, A](Source.fromInputStream(unsafeInputStream()))(f)
    else Try(f(Source.fromInputStream(unsafeInputStream()))) // Don't close stdin/stdout

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def withSourceAsync[A](f: Source => A): Future[A]

  /** Loads the resource synchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStream[A](f: InputStream => A): Try[A] =
    if (path.isDefined) Using[InputStream, A](unsafeInputStream())(f)
    else Try(f(unsafeInputStream())) // Don't close stdin/stdout

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStreamAsync[A](f: InputStream => A): Future[A]

  /** Provides a [[java.io.OutputStream]] to write data to this resource location.
    * The OutputStream is closed in the end, so it should not escape this call.
    */
  def withOutputStream[A](f: OutputStream => A): Try[A] =
    if (path.isDefined) Using[OutputStream, A](unsafeOutputStream())(f)
    else Try(f(unsafeOutputStream())) // Don't close stdin/stdout
}

object Resource {

  /** Define a resource from a path or pointing to Stdin/Stdout */
  def apply(resourcePath: Option[String])(using backend: DefaultBackend[Option[String], Resource]): Resource =
    backend.defaultValue(resourcePath)

  /** Define a resource from a path */
  def apply(resourcePath: String)(using backend: DefaultBackend[Option[String], Resource]): Resource =
    apply(Some(resourcePath))

  /** Define a resource pointing to Stdin/Stdout */
  def standardStreams()(using backend: DefaultBackend[Option[String], Resource]): Resource =
    apply(None)
}
