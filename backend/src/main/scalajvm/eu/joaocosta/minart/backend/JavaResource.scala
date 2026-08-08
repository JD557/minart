package eu.joaocosta.minart.backend

import java.io.{BufferedInputStream, File, FileInputStream, FileOutputStream, InputStream, OutputStream}

import scala.concurrent.*
import scala.io.Source
import scala.util.{Failure, Success, Try}

import eu.joaocosta.minart.runtime.Resource

/** Resource loader that fetches the data from a file or stdin/stdout.
  *  If that fails, it tries to fetch the data from the executable resources.
  */
final case class JavaResource(resourcePath: Option[String]) extends Resource {
  given ExecutionContext = ExecutionContext.global

  def path = resourcePath.map(p => s"./$p")

  override def exists(): Boolean =
    resourcePath match {
      case Some(p) =>
        new File(s"./$p").exists() || this.getClass().getResource(s"/$p") != null
      case None => true
    }

  def unsafeInputStream(): InputStream =
    resourcePath match {
      case Some(p) =>
        Try(new BufferedInputStream(new FileInputStream(s"./$p")))
          .orElse(
            Option(this.getClass().getResourceAsStream(s"/$p"))
              .fold[Try[InputStream]](Failure(new Exception(s"Couldn't open resource: $resourcePath")))(Success.apply)
          )
          .get
      case None => System.in
    }
  def unsafeOutputStream(): OutputStream =
    resourcePath match {
      case Some(p) => new FileOutputStream(s"./$p")
      case None    => System.out
    }

  def withSourceAsync[A](f: Source => A): Future[A]           = Future(blocking(withSource(f)).get)
  def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)
}
