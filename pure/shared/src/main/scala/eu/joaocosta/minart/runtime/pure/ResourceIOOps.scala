package eu.joaocosta.minart.runtime.pure

import java.io.InputStream

import scala.io.Source

import eu.joaocosta.minart.runtime.Resource

/** Representation of a resource operation, with the common Monad operations.
  */
trait ResourceIOOps {

  /** Store an unsafe resource operation in a [[ResourceIO]]. */
  def accessResource[A](f: Resource => A): ResourceIO[A] = RIO.access[Resource, A](f)

  /** Path to the resource
    */
  val path: ResourceIO[String] = accessResource(_.path)

  /** Loads the resource synchronously and returns the contents as a [[scala.io.Source]].
    * For working with binary files, it is recommended to use [[asInputStream]] instead.
    */
  val asSource: ResourceIO[Source] = accessResource(_.asSource())

  /** Loads the resource asynchronously and returns the contents as a [[scala.io.Source]].
    * For working with binary files, it is recommended to use [[asInputStreamAsync]] instead.
    * Due to the semantics of [[Poll]], it is recommended to use [[withSourceAsync]] instead.
    */
  val asSourceAsync: ResourceIO[Poll[Source]] =
    accessResource(_.asSourceAsync()).map(Poll.fromFuture)

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def withSourceAsync[A](f: Source => A): ResourceIO[Poll[A]] =
    accessResource(_.withSourceAsync(f)).map(Poll.fromFuture)

  /** Loads the resource synchronously and returns the contents as a [[java.io.InputStream]].
    */
  val asInputStream: ResourceIO[InputStream] = accessResource(_.asInputStream())

  /** Loads the resource asynchronously and returns the contents as a [[java.io.InputStream]].
    * Due to the semantics of [[Poll]], it is recommended to use [[withInputStreamAsync]] instead.
    */
  val asInputStreamAsync: ResourceIO[Poll[InputStream]] =
    accessResource(_.asInputStreamAsync()).map(Poll.fromFuture)

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    */
  def withInputStreamAsync[A](f: InputStream => A): ResourceIO[Poll[A]] =
    accessResource(_.withInputStreamAsync(f)).map(Poll.fromFuture)
}
