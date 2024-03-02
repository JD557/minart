package eu.joaocosta.minart.backend

import java.io.{BufferedInputStream, File, FileInputStream, FileOutputStream, InputStream, OutputStream}

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from a file.
  *  If that fails, it tries to fetch the data from the executable resources.
  *
  * Currently in scala-native limitations, the async methods are actually synchronous.
  */
final case class NativeResource(resourcePath: String) extends Resource {
  override def exists(): Boolean =
    new File(path).exists() || this.getClass().getResource("/" + resourcePath) != null

  def path = "./" + resourcePath

  def unsafeInputStream(): InputStream =
    Try(new BufferedInputStream(new FileInputStream(path)))
      .orElse(
        Option(this.getClass().getResourceAsStream("/" + resourcePath))
          .fold[Try[InputStream]](Failure(new Exception(s"Couldn't open resource: $resourcePath")))(Success.apply)
      )
      .get
  def unsafeOutputStream(): OutputStream = new FileOutputStream(path)

  def withSourceAsync[A](f: Source => A): Future[A] =
    Future.fromTry(withSource(f))
  def withInputStreamAsync[A](f: InputStream => A): Future[A] =
    Future.fromTry(withInputStream(f))
}
