package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent._
import scala.io.Source
import scala.util.Using.Releasable
import scala.util.{Try, Using}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader by first trying to access the jar's resources.
  * If that fails, it tries to fetch the data from a file.
  */
final case class JavaResource(resourcePath: String) extends Resource {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  // Required for scala 2.11
  private implicit val sourceReleasable: Releasable[Source] = new Releasable[Source] {
    def release(source: Source) = source.close()
  }

  def path = "./" + resourcePath
  def withSource[A](f: Source => A): Try[A] = {
    Using[Source, A](
      Source.fromInputStream(
        Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)
          .getOrElse(new FileInputStream(path))
      )
    )(f)
  }
  def withSourceAsync[A](f: Source => A): Future[A] = Future(blocking(withSource(f)).get)
  def withInputStream[A](f: InputStream => A): Try[A] =
    Using[InputStream, A](unsafeInputStream())(f)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)

  // TODO use Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)) on scala 2.12+
  def unsafeInputStream(): InputStream =
    Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get).getOrElse(new FileInputStream(path))
}
