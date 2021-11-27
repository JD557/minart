package eu.joaocosta.minart.runtime

import java.io.InputStream

import scala.concurrent.Future
import scala.io.Source

/** Resource that can be loaded
  */
trait Resource {

  /** Path to the resource
    */
  def path: String

  /** Loads the resource synchronously and returns the contents as a [[scala.io.Source]].
    * For working with binary files, it is recommended to use [[asInputStream]] instead.
    */
  def asSource(): Source

  /** Loads the resource asynchronously and returns the contents as a [[scala.io.Source]].
    * For working with binary files, it is recommended to use [[asInputStreamAsync]] instead.
    * On environments with limited execution contexts, it is recommended to use [[withSourceAsync]] instead.
    */
  def asSourceAsync(): Future[Source] = withSourceAsync(identity)

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    * This can be more helpful than [[asSourceAsync]] in platforms with limited execution contexts.
    */
  def withSourceAsync[A](f: Source => A): Future[A]

  /** Loads the resource synchronously and returns the contents as a [[java.io.InputStream]].
    */
  def asInputStream(): InputStream

  /** Loads the resource asynchronously and returns the contents as a [[java.io.InputStream]].
    * On environments with limited execution contexts, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def asInputStreamAsync(): Future[InputStream] = withInputStreamAsync(identity)

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    * This can be more helpful than [[asInputStreamAsync]] in platforms with limited execution contexts.
    */
  def withInputStreamAsync[A](f: InputStream => A): Future[A]
}
