package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.Using.Releasable
import scala.util.{Try, Using}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
final case class NativeResource(resourcePath: String) extends Resource {

  // Required for scala 2.11
  private implicit val sourceReleasable: Releasable[Source] = new Releasable[Source] {
    def release(source: Source) = source.close()
  }

  def path                                  = "./" + resourcePath
  def withSource[A](f: Source => A): Try[A] = Using[Source, A](Source.fromFile(path))(f)
  def withSourceAsync[A](f: Source => A): Future[A] =
    Future.fromTry(withSource(f))
  def withInputStream[A](f: InputStream => A): Try[A] = Using[InputStream, A](unsafeInputStream())(f)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] =
    Future.fromTry(withInputStream(f))
  def unsafeInputStream(): InputStream =
    new FileInputStream(path)
}
