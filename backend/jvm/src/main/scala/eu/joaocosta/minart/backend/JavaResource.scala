package eu.joaocosta.minart.backend

import java.io.{BufferedInputStream, File, FileInputStream, FileOutputStream, InputStream, OutputStream}

import scala.concurrent.*
import scala.io.Source
import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from a file.
  *  If that fails, it tries to fetch the data from the executable resources.
  */
final case class JavaResource(resourcePath: String) extends Resource {
  given ExecutionContext = ExecutionContext.global

  def path = "./" + resourcePath

  override def exists(): Boolean =
    new File(path).exists() || this.getClass().getResource("/" + resourcePath) != null

  def unsafeInputStream(): InputStream =
    Try(new BufferedInputStream(new FileInputStream(path)))
      .orElse(
        Option(this.getClass().getResourceAsStream("/" + resourcePath))
          .fold[Try[InputStream]](Failure(new Exception(s"Couldn't open resource: $resourcePath")))(Success.apply)
      )
      .get
  def unsafeOutputStream(): OutputStream = new FileOutputStream(path)

  def withSourceAsync[A](f: Source => A): Future[A]           = Future(blocking(withSource(f)).get)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)

}
