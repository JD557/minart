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
    * Note that this won't work as expected for binary files.
    */
  val asSource: ResourceIO[Source] = accessResource(_.asSource())

  /** Loads the resource asynchronously and returns the contents as a [[scala.io.Source]].
    * Note that this won't work as expected for binary files.
    */
  val asSourceAsync: ResourceIO[Poll[Source]] =
    accessResource(_.asSourceAsync()).map(Poll.fromFuture)

  /** Loads the resource synchronously and returns the contents as a [[java.io.InputStream]].
    */
  val asInputStream: ResourceIO[InputStream] = accessResource(_.asInputStream())

  /** Loads the resource asynchronously and returns the contents as a [[java.io.InputStream]].
    */
  val asInputStreamAsync: ResourceIO[Poll[InputStream]] =
    accessResource(_.asInputStreamAsync()).map(Poll.fromFuture)
}
