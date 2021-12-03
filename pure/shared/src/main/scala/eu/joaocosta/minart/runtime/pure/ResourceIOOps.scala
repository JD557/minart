package eu.joaocosta.minart.runtime.pure

import java.io.InputStream

import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.Resource

/** Representation of a resource operation, with the common Monad operations.
  */
trait ResourceIOOps {

  /** Store an unsafe resource operation in a [[ResourceIO]]. */
  def accessResource[A](f: Resource => A): ResourceIO[A] = RIO.access[Resource, A](f)

  /** Path to the resource
    */
  val path: ResourceIO[String] = accessResource(_.path)

  /** Loads the resource synchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    *  The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStream]] instead.
    */
  def withSource[A](f: Source => A): ResourceIO[Try[A]] =
    accessResource(_.withSource(f))

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def withSourceAsync[A](f: Source => A): ResourceIO[Poll[A]] =
    accessResource(res => Poll.fromFuture(res.withSourceAsync(f)))

  /** Loads the resource synchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStream[A](f: InputStream => A): ResourceIO[Try[A]] =
    accessResource(_.withInputStream(f))

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStreamAsync[A](f: InputStream => A): ResourceIO[Poll[A]] =
    accessResource(res => Poll.fromFuture(res.withInputStreamAsync(f)))
}
