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
    * Note that this won't work as expected for binary files.
    */
  def asSource(): Source

  /** Loads the resource asynchronously and returns the contents as a [[scala.io.Source]].
    * Note that this won't work as expected for binary files.
    */
  def asSourceAsync(): Future[Source]

  /** Loads the resource synchronously and returns the contents as a [[java.io.InputStream]].
    */
  def asInputStream(): InputStream

  /** Loads the resource asynchronously and returns the contents as a [[java.io.InputStream]].
    */
  def asInputStreamAsync(): Future[InputStream]
}
