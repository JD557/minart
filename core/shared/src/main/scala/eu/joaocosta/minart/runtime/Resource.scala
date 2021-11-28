package eu.joaocosta.minart.runtime

import java.io.InputStream

import scala.concurrent.Future
import scala.io.Source
import scala.util.Try

/** Resource that can be loaded
  */
trait Resource {

  /** Path to the resource
    */
  def path: String

  /** Loads the resource synchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    *  The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStream]] instead.
    */
  def withSource[A](f: Source => A): Try[A]

  /** Loads the resource asynchronously, processes the contents using a [[scala.io.Source]] and returns the result.
    * The Source is closed in the end, so it should not escape this call.
    * For working with binary files, it is recommended to use [[withInputStreamAsync]] instead.
    */
  def withSourceAsync[A](f: Source => A): Future[A]

  /** Loads the resource synchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStream[A](f: InputStream => A): Try[A]

  /** Loads the resource asynchronously, processes the contents using a [[java.io.InputStream]] and returns the result.
    *  The InputStream is closed in the end, so it should not escape this call.
    */
  def withInputStreamAsync[A](f: InputStream => A): Future[A]

  /** Loads the resource synchronously, and returns an [[java.io.InputStream]].
    * The InputStream is NOT closed in the end.
    *
    * This method should only be used if for some reason the input stream must stay open (e.g. for data streaming)
    */
  def unsafeInputStream(): InputStream
}
