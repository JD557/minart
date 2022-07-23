package eu.joaocosta.minart.backend

import java.io.{FileInputStream, FileOutputStream, InputStream, OutputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.Using.Releasable
import scala.util.{Try, Using}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from the executable resources.
  * If that fails, it tries to fetch the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
final case class NativeResource(resourcePath: String) extends Resource {

  // Required for scala 2.11
  private implicit val sourceReleasable: Releasable[Source] = new Releasable[Source] {
    def release(source: Source) = source.close()
  }

  def path = "./" + resourcePath
  def withSource[A](f: Source => A): Try[A] = {
    Using[Source, A](
      Source.fromInputStream(unsafeInputStream())
    )(f)
  }
  def withSourceAsync[A](f: Source => A): Future[A] =
    Future.fromTry(withSource(f))
  def withInputStream[A](f: InputStream => A): Try[A] = Using[InputStream, A](unsafeInputStream())(f)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] =
    Future.fromTry(withInputStream(f))

  // TODO use Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)) on scala 2.12+
  def unsafeInputStream(): InputStream =
    Try(new FileInputStream(path)).orElse(Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)).get

  def withOutputStream(f: OutputStream => Unit): Unit =
    Using[OutputStream, Unit](new FileOutputStream(path))(f)
}
