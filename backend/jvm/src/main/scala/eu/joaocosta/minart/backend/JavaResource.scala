package eu.joaocosta.minart.backend

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}

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

  def path = "./" + resourcePath

  override def exists(): Boolean =
    this.getClass().getResource("/" + resourcePath) != null ||
      new File(path).exists()

  // TODO use Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)) on scala 2.12+
  def unsafeInputStream(): InputStream =
    Try(new FileInputStream(path)).orElse(Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)).get
  def unsafeOutputStream(): OutputStream = new FileOutputStream(path)

  def withSourceAsync[A](f: Source => A): Future[A]           = Future(blocking(withSource(f)).get)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)

}
