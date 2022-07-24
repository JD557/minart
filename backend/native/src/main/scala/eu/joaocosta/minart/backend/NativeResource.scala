package eu.joaocosta.minart.backend

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from the executable resources.
  * If that fails, it tries to fetch the data from a file.
  *
  *  Due to scala-native limitations, the async methods are actually synchronous.
  */
final case class NativeResource(resourcePath: String) extends Resource {
  override def exists(): Boolean =
    this.getClass().getResource("/" + resourcePath) != null ||
      new File(path).exists()

  def path = "./" + resourcePath

  // TODO use Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)) on scala 2.12+
  def unsafeInputStream(): InputStream =
    Try(new FileInputStream(path)).orElse(Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)).get
  def unsafeOutputStream(): OutputStream = new FileOutputStream(path)

  def withSourceAsync[A](f: Source => A): Future[A] =
    Future.fromTry(withSource(f))
  def withInputStreamAsync[A](f: InputStream => A): Future[A] =
    Future.fromTry(withInputStream(f))
}
