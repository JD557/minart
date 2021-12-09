package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
final case class NativeResource(resourcePath: String) extends Resource {

  // TODO use scala.util.Using on scala 2.13+
  // Or at least force R to be a Autocloseable on 2.12+
  private[this] def using[R, A](open: => R, close: R => Unit)(f: R => A): Try[A] = {
    Try(open).flatMap { resource =>
      val result = Try(f(resource))
      Try(close(resource)).flatMap(_ => result)
    }
  }

  def path                                  = "./" + resourcePath
  def withSource[A](f: Source => A): Try[A] = using[Source, A](Source.fromFile(path), _.close())(f)
  def withSourceAsync[A](f: Source => A): Future[A] =
    Future.fromTry(withSource(f))
  def withInputStream[A](f: InputStream => A): Try[A] = using[InputStream, A](unsafeInputStream(), _.close)(f)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] =
    Future.fromTry(withInputStream(f))
  def unsafeInputStream(): InputStream =
    new FileInputStream(path)
}
