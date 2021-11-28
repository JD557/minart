package eu.joaocosta.minart.backend

import java.io.{FileInputStream, InputStream}

import scala.concurrent._
import scala.io.Source
import scala.util.Try

import eu.joaocosta.minart.runtime.{Resource, ResourceLoader}

/** Resource loader by first trying to access the jar's resources.
  * If that fail, it tries to fetch the data from a file.
  */
object JavaResourceLoader extends ResourceLoader {
  def createResource(resourcePath: String): Resource = {
    implicit val ec: ExecutionContext = ExecutionContext.global
    new Resource {

      // TODO use scala.util.Using on scala 2.13+
      // Or at least force R to be a Autocloseable on 2.12+
      private[this] def using[R, A](open: => R, close: R => Unit)(f: R => A): Try[A] = {
        Try(open).flatMap { resource =>
          val result = Try(f(resource))
          Try(close(resource)).flatMap(_ => result)
        }
      }

      def path = "./" + resourcePath
      def withSource[A](f: Source => A): Try[A] = {
        using[Source, A](
          Source.fromInputStream(
            Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get)
              .getOrElse(new FileInputStream(path))
          ),
          _.close()
        )(f)
      }
      def withSourceAsync[A](f: Source => A): Future[A] = Future(blocking(withSource(f)).get)
      def withInputStream[A](f: InputStream => A): Try[A] =
        using[InputStream, A](
          unsafeInputStream(),
          _.close()
        )(f)
      def withInputStreamAsync[A](f: InputStream => A): Future[A] = Future(blocking(withInputStream(f)).get)

      // TODO use Try(Source.fromResource(resourcePath)).getOrElse(Source.fromFile(path)) on scala 2.12+
      def unsafeInputStream(): InputStream =
        Try(Option(this.getClass().getResourceAsStream("/" + resourcePath)).get).getOrElse(new FileInputStream(path))
    }
  }
}
